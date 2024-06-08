package org.team199.wpiws.devices;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.team199.wpiws.Pair;
import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.ObjectCallback;
import org.team199.wpiws.interfaces.SimDeviceCallback;
import org.team199.wpiws.interfaces.TestUtils;
import org.team199.wpiws.interfaces.TriConsumer;

@RunWith(Enclosed.class)
public class SimDeviceSimTest {

    @Test
    public void testEnumerateDevices() {
        String prefix = getDeviceName("testEnumerateDevices");
        TestUtils.assertArrayEqualsIgnoringOrder(new String[0],
                SimDeviceSim.enumerateDevices(prefix + "-prefix"));

        String simInitializedSimName = prefix + "-prefix-initializedBySim";
        String robotInitializedSimName = prefix + "-prefix-initializedByRobot";
        String nonPrefixedSimName = prefix + "-noPrefix";

        SimDeviceSim simInitializedSim =
                new SimDeviceSim(simInitializedSimName);
        @SuppressWarnings("unused")
        SimDeviceSim robotInitializedSim =
                new SimDeviceSim(robotInitializedSimName);
        SimDeviceSim nonPrefixedSim = new SimDeviceSim(nonPrefixedSimName);

        simInitializedSim.set("value", "data");
        TestUtils.setValueFromRobot(robotInitializedSimName, "SimDevice",
                "<value", "data");
        nonPrefixedSim.set("value", "data");

        TestUtils.assertArrayEqualsIgnoringOrder(
                new String[] {simInitializedSimName, robotInitializedSimName},
                SimDeviceSim.enumerateDevices(prefix + "-prefix"));
    }

    @Test
    public void testEnumerateValues() {
        SimDeviceSim device =
                new SimDeviceSim(getDeviceName("testEnumerateValues"));

        TestUtils.assertArrayEqualsIgnoringOrder(new String[0],
                device.enumerateValues("prefix"));

        device.set("prefix-value1", "value1");
        device.set("prefix-value2", "value2");
        device.set("noPrefix-value3", "value3");

        TestUtils.assertArrayEqualsIgnoringOrder(
                new String[] {"prefix-value1", "prefix-value2"},
                device.enumerateValues("prefix"));
    }

    @Test
    public void testDeviceCreatedCallback() {
        ArrayList<Pair<String, SimDeviceCallback>> callbacks =
                new ArrayList<>();
        try {

            // First, we'll run our test case
            SimDeviceCallback notifiedCallbackBeforeFirstDeviceInit = mock();
            callbacks.add(SimDeviceSim.registerDeviceCreatedCallback("prefix",
                    notifiedCallbackBeforeFirstDeviceInit, true));
            SimDeviceCallback nonNotifiedCallbackBeforeFirstDeviceInit = mock();
            callbacks.add(SimDeviceSim.registerDeviceCreatedCallback("prefix",
                    nonNotifiedCallbackBeforeFirstDeviceInit, false));

            // Try initializing a device through the API
            new SimDeviceSim("prefix-device1").set("value", "data");

            SimDeviceCallback notifiedCallbackAfterFirstDeviceInit = mock();
            callbacks.add(SimDeviceSim.registerDeviceCreatedCallback("prefix",
                    notifiedCallbackAfterFirstDeviceInit, true));
            SimDeviceCallback nonNotifiedCallbackAfterFirstDeviceInit = mock();
            callbacks.add(SimDeviceSim.registerDeviceCreatedCallback("prefix",
                    nonNotifiedCallbackAfterFirstDeviceInit, false));

            // Try initializing a value through WS
            TestUtils.setValueFromRobot("prefix-device2", "SimDevice",
                    "<value2", "data2");

            // Initializing a device with the wrong prefix should not call the callbacks
            new SimDeviceSim("noPrefix-device").set("value", "data");


            callbacks.forEach(SimDeviceSim::cancelDeviceCreatedCallback);

            new SimDeviceSim("prefix-device3").set("value", "data");

            // Now, we'll check that we saw all the correct invocations
            InOrder order;

            order = inOrder(notifiedCallbackBeforeFirstDeviceInit);
            order.verify(notifiedCallbackBeforeFirstDeviceInit)
                    .callback(eq("prefix-device1"));
            order.verify(notifiedCallbackBeforeFirstDeviceInit)
                    .callback(eq("prefix-device2"));
            verifyNoMoreInteractions(notifiedCallbackBeforeFirstDeviceInit);

            order = inOrder(nonNotifiedCallbackBeforeFirstDeviceInit);
            order.verify(nonNotifiedCallbackBeforeFirstDeviceInit)
                    .callback(eq("prefix-device1"));
            order.verify(nonNotifiedCallbackBeforeFirstDeviceInit)
                    .callback(eq("prefix-device2"));
            verifyNoMoreInteractions(nonNotifiedCallbackBeforeFirstDeviceInit);

            order = inOrder(notifiedCallbackAfterFirstDeviceInit);
            order.verify(notifiedCallbackAfterFirstDeviceInit)
                    .callback(eq("prefix-device1"));
            order.verify(notifiedCallbackAfterFirstDeviceInit)
                    .callback(eq("prefix-device2"));
            verifyNoMoreInteractions(notifiedCallbackAfterFirstDeviceInit);

            order = inOrder(nonNotifiedCallbackAfterFirstDeviceInit);
            order.verify(nonNotifiedCallbackAfterFirstDeviceInit)
                    .callback(eq("prefix-device2"));
            verifyNoMoreInteractions(nonNotifiedCallbackAfterFirstDeviceInit);


        } finally {
            callbacks.forEach(SimDeviceSim::cancelDeviceCreatedCallback);
        }
    }

    @Test
    public void testValueCreatedCallback() {
        SimDeviceSim sim =
                new SimDeviceSim(getDeviceName("testValueCreatedCallback"));

        ArrayList<ObjectCallback<String>> callbacks = new ArrayList<>();
        try {

            // First, we'll run our test case
            ObjectCallback<String> notifiedCallbackBeforeFirstValueInit =
                    mock();
            callbacks.add(sim.registerValueCreatedCallback(
                    notifiedCallbackBeforeFirstValueInit, true));
            ObjectCallback<String> nonNotifiedCallbackBeforeFirstValueInit =
                    mock();
            callbacks.add(sim.registerValueCreatedCallback(
                    nonNotifiedCallbackBeforeFirstValueInit, false));

            // Try initializing a value through the API
            sim.set("value1", "data1");

            ObjectCallback<String> notifiedCallbackAfterFirstValueInit = mock();
            callbacks.add(sim.registerValueCreatedCallback(
                    notifiedCallbackAfterFirstValueInit, true));
            ObjectCallback<String> nonNotifiedCallbackAfterFirstValueInit =
                    mock();
            callbacks.add(sim.registerValueCreatedCallback(
                    nonNotifiedCallbackAfterFirstValueInit, false));

            // Try initializing a value through WS
            TestUtils.setValueFromRobot(sim.id, "SimDevice", "<value2",
                    "data2");

            // Setting new data should not trigger the
            sim.set("value1", "newData1");


            callbacks.forEach(sim::cancelValueCreatedCallback);

            sim.set("value3", "data3");

            // Now, we'll check that we saw all the correct invocations
            InOrder order;

            order = inOrder(notifiedCallbackBeforeFirstValueInit);
            order.verify(notifiedCallbackBeforeFirstValueInit)
                    .callback(eq("value1"), eq("data1"));
            order.verify(notifiedCallbackBeforeFirstValueInit)
                    .callback(eq("value2"), eq("data2"));
            verifyNoMoreInteractions(notifiedCallbackBeforeFirstValueInit);

            order = inOrder(nonNotifiedCallbackBeforeFirstValueInit);
            order.verify(nonNotifiedCallbackBeforeFirstValueInit)
                    .callback(eq("value1"), eq("data1"));
            order.verify(nonNotifiedCallbackBeforeFirstValueInit)
                    .callback(eq("value2"), eq("data2"));
            verifyNoMoreInteractions(nonNotifiedCallbackBeforeFirstValueInit);

            order = inOrder(notifiedCallbackAfterFirstValueInit);
            order.verify(notifiedCallbackAfterFirstValueInit)
                    .callback(eq("value1"), eq("data1"));
            order.verify(notifiedCallbackAfterFirstValueInit)
                    .callback(eq("value2"), eq("data2"));
            verifyNoMoreInteractions(notifiedCallbackAfterFirstValueInit);

            order = inOrder(nonNotifiedCallbackAfterFirstValueInit);
            order.verify(nonNotifiedCallbackAfterFirstValueInit)
                    .callback(eq("value2"), eq("data2"));
            verifyNoMoreInteractions(nonNotifiedCallbackAfterFirstValueInit);


        } finally {
            callbacks.forEach(sim::cancelValueCreatedCallback);
        }
    }

    /**
     * Creates a device name that will not conflict with devices from other tests. Devices names for
     * the same test case will always be equal.
     *
     * @param testMethodName the name of the test being run
     * @return a device name unique to the given test
     */
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

        /**
         * Uses the given information to construct the necessary parameters to run a test. This
         * method creates three test cases that simulate values that are a a robot input, a robot
         * output, and a bidirectional value, respectively.
         *
         * @param <VarType> the type of variable being tested
         * @param valueName a name for the variable. This will be included in the test name. This is
         *        not a WS name.
         * @param setterFunction the specific SimDeviceSim function to use to set the value of the
         *        variable
         * @param defaultValue the "default" value of the variable. For SimDeviceSim, get() always
         *        returns `null` by default, but we still need a specific value for testing.
         *        Ensuring that this is "falsy" will prevent any conflicts.
         * @param alternateValue an alternative example of this value type that can be used for
         *        testing
         * @return an Object array containing the generated test cases
         */
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

        @Test
        public void testValueChangedCallback() {
            String deviceName = getDeviceName("testValueChangedCallback");
            SimDeviceSim sim = new SimDeviceSim(deviceName);

            ArrayList<Pair<String, ObjectCallback<String>>> callbacks =
                    new ArrayList<>();
            try {

                // First, we'll run our test case
                ObjectCallback<String> notifiedCallbackBeforeValueInit = mock();
                callbacks.add(sim.registerValueChangedCallback(valueName,
                        notifiedCallbackBeforeValueInit, true));
                ObjectCallback<String> nonNotifiedCallbackBeforeValueInit =
                        mock();
                callbacks.add(sim.registerValueChangedCallback(valueName,
                        nonNotifiedCallbackBeforeValueInit, false));

                setValueFromRobot(deviceName, alternateValue);

                ObjectCallback<String> notifiedCallbackAfterValueInit = mock();
                callbacks.add(sim.registerValueChangedCallback(valueName,
                        notifiedCallbackAfterValueInit, true));
                ObjectCallback<String> nonNotifiedCallbackAfterValueInit =
                        mock();
                callbacks.add(sim.registerValueChangedCallback(valueName,
                        nonNotifiedCallbackAfterValueInit, false));

                // Repeat sets should not trigger the callback
                setValueFromRobot(deviceName, alternateValue);

                setValueFromRobot(deviceName, defaultValue);

                // Sets on other values should not trigger the callback
                TestUtils.setValueFromRobot(deviceName, "SimDevice",
                        valueName + "-noCallbacks", alternateValue);

                callbacks.forEach(sim::cancelValueChangedCallback);

                // Sets after cancellation should not trigger the callback
                setValueFromRobot(deviceName, alternateValue);

                // Now, we'll check that we saw all the correct invocations
                InOrder order;

                order = inOrder(notifiedCallbackBeforeValueInit);
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(valueName), eq(null));
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(valueName), eq(alternateValue.toString()));
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(valueName), eq(defaultValue.toString()));
                verifyNoMoreInteractions(notifiedCallbackBeforeValueInit);

                order = inOrder(nonNotifiedCallbackBeforeValueInit);
                order.verify(nonNotifiedCallbackBeforeValueInit)
                        .callback(eq(valueName), eq(alternateValue.toString()));
                order.verify(nonNotifiedCallbackBeforeValueInit)
                        .callback(eq(valueName), eq(defaultValue.toString()));
                verifyNoMoreInteractions(nonNotifiedCallbackBeforeValueInit);

                order = inOrder(notifiedCallbackAfterValueInit);
                order.verify(notifiedCallbackAfterValueInit)
                        .callback(eq(valueName), eq(alternateValue.toString()));
                order.verify(notifiedCallbackAfterValueInit)
                        .callback(eq(valueName), eq(defaultValue.toString()));
                verifyNoMoreInteractions(notifiedCallbackAfterValueInit);

                order = inOrder(nonNotifiedCallbackAfterValueInit);
                order.verify(nonNotifiedCallbackAfterValueInit)
                        .callback(eq(valueName), eq(defaultValue.toString()));
                verifyNoMoreInteractions(nonNotifiedCallbackAfterValueInit);

            } finally {
                callbacks.forEach(sim::cancelValueChangedCallback);
            }
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

        /**
         * A wrapper around {@link TestUtils#setValueFromRobot(String, String, String, Object)} that
         * auto-fills some of the parameters from the members of this class.
         *
         * @param deviceName the name of the device to set the value on
         * @param newValue the new value to set
         */
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

        /**
         * Creates a device name that will not conflict with devices from other tests. Devices names
         * for the same test case will always be equal.
         *
         * @param testMethodName the name of the test being run
         * @return a device name unique to the given test
         */
        private String getDeviceName(String testMethodName) {
            return "SimDeviceSimTest.DataTests.%s-%s".formatted(testMethodName,
                    testName);
        }

    }

}
