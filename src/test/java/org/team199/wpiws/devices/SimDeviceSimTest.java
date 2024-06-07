package org.team199.wpiws.devices;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.ArgumentMatcher;
import org.mockito.MockedStatic;
import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.TestUtils;
import org.team199.wpiws.interfaces.TriConsumer;

@RunWith(Enclosed.class)
public class SimDeviceSimTest {

	private static String getDeviceName(String testMethodName) {
		return "SimDeviceSimTest.%s".formatted(testMethodName);
	}

	@RunWith(Parameterized.class)
	public static class DataTests<VarType> {

		@Parameters(name = "{index}: {0}")
		public static Object[] testCases() {
			return Arrays.stream(new Object[][] {
					createTestCase("BooleanValue", SimDeviceSim::set, false,
							true),
					createTestCase("DoubleValue", SimDeviceSim::set, 0.0, 0.1),
					createTestCase("StringValue", SimDeviceSim::set, "",
							"data")})
					.flatMap(Arrays::stream).toArray();
		}

		private static <VarType> Object[][] createTestCase(String valueName,
				TriConsumer<SimDeviceSim, String, VarType> setterFunction,
				VarType defaultValue, VarType alternateValue) {
			return new Object[][] {
					new Object[] {"%s-robotInput".formatted(valueName),
							setterFunction, defaultValue, alternateValue,
							valueName, false},
					new Object[] {"%s-robotOutput".formatted(valueName), null,
							defaultValue, alternateValue, valueName, false},
					new Object[] {"%s-bidirectional".formatted(valueName),
							setterFunction, defaultValue, alternateValue,
							valueName, true}};
		}

		@Before
		public void computeWSValueName() {
			wsValueName =
					bidirectional ? "<>" : setterFunction == null ? "<" : ">";
			wsValueName += valueName;
		}

		@Test
		public void testRobotSetter() {
			// !bidirectional && setterFunction != null => Robot input
			assumeTrue(bidirectional || setterFunction == null);

			String deviceName = getDeviceName("testRobotSetter");
			SimDeviceSim sim = new SimDeviceSim(deviceName);

			try (MockedStatic<ConnectionProcessor> connectionProcessor =
					mockStatic(ConnectionProcessor.class)) {

				assertEquals(null, sim.get(valueName));
				setValueFromRobot(deviceName, alternateValue);
				assertEquals(alternateValue.toString(), sim.get(valueName));

				assertEquals(bidirectional, sim.isBidirectional(valueName));

				// No messages should've been broadcast back to the robot
				connectionProcessor.verifyNoInteractions();
			}
		}

		@Test
		public void testSimSetter() {
			assumeNotNull(setterFunction); // If the setter doesn't exist, we can't test it

			String deviceName = getDeviceName("testSimSetter");
			SimDeviceSim sim = new SimDeviceSim(deviceName);

			try (MockedStatic<ConnectionProcessor> connectionProcessor =
					mockStatic(ConnectionProcessor.class)) {

				// Checks the WSValue passed to ConnectionProcessor.broadcastMessage
				ArgumentMatcher<WSValue> dataValueMatcher =
						arg -> new WSValue(wsValueName, alternateValue)
								.equals(arg);

				if (bidirectional)
					sim.setBidirectional(valueName);

				assertEquals(null, sim.get(valueName));
				setterFunction.accept(sim, valueName, alternateValue);
				assertEquals(alternateValue.toString(), sim.get(valueName));
				connectionProcessor.verify(() -> ConnectionProcessor
						.broadcastMessage(eq(deviceName), eq("SimDevice"),
								argThat(dataValueMatcher)));

				// The robot code should be re-notified every time set is called
				setterFunction.accept(sim, valueName, alternateValue);
				assertEquals(alternateValue.toString(), sim.get(valueName));
				connectionProcessor.verify(() -> ConnectionProcessor
						.broadcastMessage(eq(deviceName), eq("SimDevice"),
								argThat(dataValueMatcher)),
						times(2) // Two times total
				);

				assertEquals(bidirectional, sim.isBidirectional(valueName));

				// No other messages should've been sent for this device
				connectionProcessor.verify(() -> ConnectionProcessor
						.broadcastMessage(eq(deviceName), eq("SimDevice"),
								argThat((WSValue arg) -> !dataValueMatcher
										.matches(arg))),
						never());
			}
		}

		private void setValueFromRobot(String deviceName, VarType newValue) {
			TestUtils.setValueFromRobot(deviceName, "SimDevice", wsValueName,
					newValue);
		}

		@Parameter(0)
		public String testName;
		@Parameter(1)
		public TriConsumer<SimDeviceSim, String, VarType> setterFunction;
		@Parameter(2)
		public VarType defaultValue;
		@Parameter(3)
		public VarType alternateValue;
		@Parameter(4)
		public String valueName;
		@Parameter(5)
		public boolean bidirectional;
		private String wsValueName;

		private String getDeviceName(String testMethodName) {
			return "SimDeviceSimTest.DataTests.%s-%s".formatted(testMethodName,
					testName);
		}

	}

}
