package org.team199.wpiws.devices;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Assert;
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
import org.team199.wpiws.interfaces.BooleanCallback;
import org.team199.wpiws.interfaces.ObjectCallback;
import org.team199.wpiws.interfaces.TestUtils;
import org.team199.wpiws.interfaces.TriFunction;
import org.team199.wpiws.types.LEDColor;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;

@RunWith(Enclosed.class)
public class DevicesTest {

    @Test
    public void testStaticInitializationCallback() {
        String deviceName1 = "DevicesTest.testStaticInitializationCallback-1";
        String deviceName2 = "DevicesTest.testStaticInitializationCallback-2";

        ArrayList<BooleanCallback> callbacks = new ArrayList<>();
        try {

            // First, we'll run our test case
            BooleanCallback notifiedCallbackBeforeValueInit = mock();
            callbacks.add(AccelerometerSim.registerStaticInitializedCallback(
                    notifiedCallbackBeforeValueInit, true));
            BooleanCallback nonNotifiedCallbackBeforeValueInit = mock();
            callbacks.add(AccelerometerSim.registerStaticInitializedCallback(
                    nonNotifiedCallbackBeforeValueInit, false));

            // Repeat sets should not trigger the callback
            TestUtils.setValueFromRobot(deviceName1, "Accel", "<init", true);
            TestUtils.setValueFromRobot(deviceName2, "Accel", "<init", true);

            BooleanCallback notifiedCallbackAfterValueInit = mock();
            callbacks.add(AccelerometerSim.registerStaticInitializedCallback(
                    notifiedCallbackAfterValueInit, true));
            BooleanCallback nonNotifiedCallbackAfterValueInit = mock();
            callbacks.add(AccelerometerSim.registerStaticInitializedCallback(
                    nonNotifiedCallbackAfterValueInit, false));

            TestUtils.setValueFromRobot(deviceName1, "Accel", "<init", true);
            TestUtils.setValueFromRobot(deviceName1, "Accel", "<init", false);

            callbacks
                    .forEach(AccelerometerSim::cancelStaticInitializedCallback);

            // Sets after cancellation should not trigger the callback
            TestUtils.setValueFromRobot(deviceName1, "Accel", "<init", true);

            // Now, we'll check that we saw all the correct invocations
            InOrder order;

            order = inOrder(notifiedCallbackBeforeValueInit);
            order.verify(notifiedCallbackBeforeValueInit)
                    .callback(eq(deviceName1), eq(true));
            order.verify(notifiedCallbackBeforeValueInit)
                    .callback(eq(deviceName2), eq(true));
            order.verify(notifiedCallbackBeforeValueInit)
                    .callback(eq(deviceName1), eq(false));
            verifyNoMoreInteractions(notifiedCallbackBeforeValueInit);

            order = inOrder(nonNotifiedCallbackBeforeValueInit);
            order.verify(nonNotifiedCallbackBeforeValueInit)
                    .callback(eq(deviceName1), eq(true));
            order.verify(nonNotifiedCallbackBeforeValueInit)
                    .callback(eq(deviceName2), eq(true));
            order.verify(nonNotifiedCallbackBeforeValueInit)
                    .callback(eq(deviceName1), eq(false));
            verifyNoMoreInteractions(nonNotifiedCallbackBeforeValueInit);

            order = inOrder(notifiedCallbackAfterValueInit);
            order.verify(notifiedCallbackAfterValueInit)
                    .callback(eq(deviceName1), eq(true));
            order.verify(notifiedCallbackAfterValueInit)
                    .callback(eq(deviceName2), eq(true));
            order.verify(notifiedCallbackAfterValueInit)
                    .callback(eq(deviceName1), eq(false));
            verifyNoMoreInteractions(notifiedCallbackAfterValueInit);

            order = inOrder(nonNotifiedCallbackAfterValueInit);
            order.verify(nonNotifiedCallbackAfterValueInit)
                    .callback(eq(deviceName1), eq(false));
            verifyNoMoreInteractions(nonNotifiedCallbackAfterValueInit);

        } finally {
            callbacks
                    .forEach(AccelerometerSim::cancelStaticInitializedCallback);
        }
    }

    @RunWith(Parameterized.class)
    public static class DataTests<SimType, VarType, CallbackType> {

        @Parameters(name = "{index}: {0}")
        public static Object[] getTestCases() {
            return new Object[] {
                    // Test initialized callback
                    createTestCase("Accel", "<init", AccelerometerSim::new,
                            AccelerometerSim::registerInitializedCallback,
                            AccelerometerSim::cancelInitializedCallback,
                            AccelerometerSim::getInitialized,
                            AccelerometerSim::setInitialized, false, true,
                            Function.identity(), c -> c::callback),
                    // Boolean
                    createTestCase("DIO", "<>value", DIOSim::new,
                            DIOSim::registerValueCallback,
                            DIOSim::cancelValueCallback, DIOSim::getValue,
                            DIOSim::setValue, false, true, Function.identity(),
                            c -> c::callback),
                    // Double
                    createTestCase("Accel", ">x", AccelerometerSim::new,
                            AccelerometerSim::registerXCallback,
                            AccelerometerSim::cancelXCallback,
                            AccelerometerSim::getX, AccelerometerSim::setX, 0.0,
                            0.1, BigDecimal::new, c -> c::callback),
                    // Integer
                    createTestCase("AI", ">accum_value", AnalogInputSim::new,
                            AnalogInputSim::registerAccumValueCallback,
                            AnalogInputSim::cancelAccumValueCallback,
                            AnalogInputSim::getAccumValue,
                            AnalogInputSim::setAccumValue, 0, 1,
                            BigDecimal::new, c -> c::callback),
                    // String
                    createTestCase("DriverStation", ">game_data",
                            DriverStationSim::registerGameDataCallback,
                            DriverStationSim::cancelGameDataCallback,
                            DriverStationSim::getGameData,
                            DriverStationSim::setGameData, "", "data",
                            Function.identity(), c -> c::callback),
                    // Boolean[]
                    createTestCase("Joystick", ">buttons", JoystickSim::new,
                            JoystickSim::registerButtonsCallback,
                            JoystickSim::cancelButtonsCallback,
                            JoystickSim::getButtons, JoystickSim::setButtons,
                            new boolean[0], new boolean[] {false},
                            (buttons) -> {
                                JsonArray arr = new JsonArray();
                                for (boolean b : buttons)
                                    arr.add(b);
                                return arr;
                            }, c -> c::callback),
                    // Double[]
                    createTestCase("Joystick", ">axes", JoystickSim::new,
                            JoystickSim::registerAxesCallback,
                            JoystickSim::cancelAxesCallback,
                            JoystickSim::getAxes, JoystickSim::setAxes,
                            new double[0], new double[] {0},
                            (axes) -> new JsonArray(Arrays.stream(axes)
                                    .mapToObj(BigDecimal::new).toList()),
                            c -> c::callback),
                    // Integer[]
                    createTestCase("Joystick", ">povs", JoystickSim::new,
                            JoystickSim::registerPovsCallback,
                            JoystickSim::cancelPovsCallback,
                            JoystickSim::getPovs, JoystickSim::setPovs,
                            new int[0], new int[] {0},
                            (povs) -> new JsonArray(Arrays.stream(povs)
                                    .mapToObj(BigDecimal::new).toList()),
                            c -> c::callback),
                    // LEDColor[]
                    createTestCase("AddressableLED", "<data",
                            AddressableLEDSim::new,
                            AddressableLEDSim::registerDataCallback,
                            AddressableLEDSim::cancelDataCallback,
                            AddressableLEDSim::getData, null, new LEDColor[0],
                            new LEDColor[] {new LEDColor(0, 0, 0)},
                            (data) -> new JsonArray(Arrays.stream(data)
                                    .map(LEDColor::toJson).map(jsonString -> {
                                        try {
                                            return Jsoner
                                                    .deserialize(jsonString);
                                        } catch (JsonException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).toList()),
                            c -> c::callback)};
        }

        /**
         * Uses the given information to construct the necessary parameters to run a test.
         *
         * @param <SimType> the type of sim device being tested
         * @param <VarType> the type of variable being tested
         * @param <CallbackType> the type of callbacks being used in the test
         * @param typeName the WS type name of the device being tested
         * @param valueName the WS name of the value being tested
         * @param constructor a function that can be used to construct an instance of the sim device
         *        type being tested
         * @param callbackRegistrationFunction a function that can be used to register a callback on
         *        the variable being tested
         * @param callbackCancellationFunction a function that can be used to cancel the callback
         *        returned by the {@code callbackRegistrationFunction}
         * @param getterFunction a function that can be used to get the value of the value being
         *        tested
         * @param setterFunction a function that can be used to set the value of the value being
         *        tested. If the value cannot be set through the API, {@code null} may be used,
         *        however, this will prevent the {@code testSimSetter} test from being run.
         * @param defaultValue the default value of the variable being tested before it has been set
         *        by the robot or the simulator
         * @param alternateValue an alternative example of this value type that can be used for
         *        testing
         * @param serializer a function that can convert the example values into their
         *        JSON-object-serialized form
         * @param callbackConverter a function that can be used to convert the generalized callbacks
         *        used by the tests into the specific callback types necessary for the supplied
         *        functions. In most cases, passing {@code c => c::callback} will allow Java to
         *        figure it out.
         * @return an Object array containing the parameters necessary to run the test case
         */
        private static <SimType, VarType, CallbackType> Object[] createTestCase(
                String typeName, String valueName,
                Function<String, SimType> constructor,
                TriFunction<SimType, CallbackType, Boolean, CallbackType> callbackRegistrationFunction,
                BiConsumer<SimType, CallbackType> callbackCancellationFunction,
                Function<SimType, VarType> getterFunction,
                BiConsumer<SimType, VarType> setterFunction,
                VarType defaultValue, VarType alternateValue,
                Function<VarType, ?> serializer,
                Function<ObjectCallback<VarType>, CallbackType> callbackConverter) {
            return new Object[] {"%s-'%s'".formatted(typeName, valueName), // testName
                    constructor, callbackConverter,
                    callbackRegistrationFunction, callbackCancellationFunction,
                    getterFunction, setterFunction,
                    new Pair<>(defaultValue, serializer.apply(defaultValue)), // defaultValue
                    new Pair<>(alternateValue,
                            serializer.apply(alternateValue)), // alternateValue
                    typeName, valueName};
        }

        /**
         * A variation of
         * {@link #createTestCase(String, String, BiFunction, Consumer, Supplier, Consumer, Object, Object, Function, Function)}
         * that is targeted towards test cases that analyze a static variable (one which does not
         * require a sim device instance)
         *
         * @param <VarType> the type of variable being tested
         * @param <CallbackType> the type of callbacks being used in the test
         * @param typeName the WS type name of the device being tested
         * @param valueName the WS name of the value being tested
         * @param callbackRegistrationFunction a function that can be used to register a callback on
         *        the variable being tested
         * @param callbackCancellationFunction a function that can be used to cancel the callback
         *        returned by the {@code callbackRegistrationFunction}
         * @param getterFunction a function that can be used to get the value of the value being
         *        tested
         * @param setterFunction a function that can be used to set the value of the value being
         *        tested. If the value cannot be set through the API, {@code null} may be used,
         *        however, this will prevent the {@code testSimSetter} test from being run.
         * @param defaultValue the default value of the variable being tested before it has been set
         *        by the robot or the simulator
         * @param alternateValue an alternative example of this value type that can be used for
         *        testing
         * @param serializer a function that can convert the example values into their
         *        JSON-object-serialized form
         * @param callbackConverter a function that can be used to convert the generalized callbacks
         *        used by the tests into the specific callback types necessary for the supplied
         *        functions. In most cases, passing {@code c => c::callback} will allow Java to
         *        figure it out.
         * @return an Object array containing the parameters necessary to run the test case
         */
        private static <VarType, CallbackType> Object[] createTestCase(
                String typeName, String valueName,
                BiFunction<CallbackType, Boolean, CallbackType> callbackRegistrationFunction,
                Consumer<CallbackType> callbackCancellationFunction,
                Supplier<VarType> getterFunction,
                Consumer<VarType> setterFunction, VarType defaultValue,
                VarType alternateValue, Function<VarType, ?> serializer,
                Function<ObjectCallback<VarType>, CallbackType> callbackConverter) {
            return createTestCase(typeName, valueName, (name) -> null,
                    (sim, callback,
                            initialNotify) -> callbackRegistrationFunction
                                    .apply(callback, initialNotify),
                    (sim, callback) -> callbackCancellationFunction
                            .accept(callback),
                    (sim) -> getterFunction.get(),
                    (sim, value) -> setterFunction.accept(value), defaultValue,
                    alternateValue, serializer, callbackConverter);
        }

        @Test
        public void testCallback() {
            String deviceName = getDeviceName("testCallback");
            SimType sim = constructor.apply(deviceName);
            if (sim == null)
                deviceName = ""; // If a device is static, it's called with deviceName=""

            ArrayList<CallbackType> callbacks = new ArrayList<>();
            try {

                // First, we'll run our test case
                ObjectCallback<VarType> notifiedCallbackBeforeValueInit =
                        mock();
                callbacks.add(registerCallback(sim,
                        notifiedCallbackBeforeValueInit, true));
                ObjectCallback<VarType> nonNotifiedCallbackBeforeValueInit =
                        mock();
                callbacks.add(registerCallback(sim,
                        nonNotifiedCallbackBeforeValueInit, false));

                setValueFromRobot(deviceName, alternateValue);

                ObjectCallback<VarType> notifiedCallbackAfterValueInit = mock();
                callbacks.add(registerCallback(sim,
                        notifiedCallbackAfterValueInit, true));
                ObjectCallback<VarType> nonNotifiedCallbackAfterValueInit =
                        mock();
                callbacks.add(registerCallback(sim,
                        nonNotifiedCallbackAfterValueInit, false));

                // Repeat sets should not trigger the callback
                setValueFromRobot(deviceName, alternateValue);

                if (setterFunction != null) {
                    // Check that we also get callback notifications from the setter function
                    setterFunction.accept(sim, defaultValue.val1);
                } else {
                    // If there is no setter function, don't bother
                    setValueFromRobot(deviceName, defaultValue);
                }

                callbacks.forEach(callback -> callbackCancellationFunction
                        .accept(sim, callback));

                // Sets after cancellation should not trigger the callback
                setValueFromRobot(deviceName, alternateValue);

                // Now, we'll check that we saw all the correct invocations
                InOrder order;

                order = inOrder(notifiedCallbackBeforeValueInit);
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(alternateValue.val1));
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(notifiedCallbackBeforeValueInit);

                order = inOrder(nonNotifiedCallbackBeforeValueInit);
                order.verify(nonNotifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(alternateValue.val1));
                order.verify(nonNotifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(nonNotifiedCallbackBeforeValueInit);

                order = inOrder(notifiedCallbackAfterValueInit);
                order.verify(notifiedCallbackAfterValueInit)
                        .callback(eq(deviceName), eq(alternateValue.val1));
                order.verify(notifiedCallbackAfterValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(notifiedCallbackAfterValueInit);

                order = inOrder(nonNotifiedCallbackAfterValueInit);
                order.verify(nonNotifiedCallbackAfterValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(nonNotifiedCallbackAfterValueInit);

            } finally {
                callbacks.forEach(callback -> callbackCancellationFunction
                        .accept(sim, callback));

                // Reset the value to the default.
                // (The other tests may check this if the sim is static)
                setValueFromRobot(deviceName, defaultValue);
            }
        }

        /**
         * Registers a callback for the variable being tested on a sim device instance
         *
         * @param sim the sim device to register the callback on
         * @param callback the callback to register
         * @param initialNotify whether the callback should be called immediately with the current
         *        value of the variable
         * @return an object that can be passed to the {@link #callbackCancellationFunction} to
         *         cancel the callback
         */
        private CallbackType registerCallback(SimType sim,
                ObjectCallback<VarType> callback, boolean initialNotify) {
            return callbackRegistrationFunction.apply(sim,
                    callbackConverter.apply(callback), initialNotify);
        }

        @Test
        public void testRobotSetter() {
            String deviceName = getDeviceName("testRobotSetter");
            SimType sim = constructor.apply(deviceName);
            if (sim == null)
                deviceName = ""; // If a device is static, it's called with deviceName=""

            try (MockedStatic<ConnectionProcessor> connectionProcessor =
                    mockStatic(ConnectionProcessor.class)) {

                assertDeepEquals(defaultValue.val1, getterFunction.apply(sim));
                setValueFromRobot(deviceName, alternateValue);
                assertDeepEquals(alternateValue.val1,
                        getterFunction.apply(sim));

                // No messages should've been broadcast back to the robot
                connectionProcessor.verifyNoInteractions();
            } finally {
                // Reset the value to the default.
                // (The other tests may check this if the sim is static)
                setValueFromRobot(deviceName, defaultValue);
            }
        }

        @Test
        public void testSimSetter() {
            assumeNotNull(setterFunction); // If the setter doesn't exist, we can't test it

            String deviceName = getDeviceName("testSimSetter");
            SimType sim = constructor.apply(deviceName);
            if (sim == null)
                deviceName = ""; // If a device is static, it's called with deviceName=""
            String finalDeviceName = deviceName; // For lambdas

            try (MockedStatic<ConnectionProcessor> connectionProcessor =
                    mockStatic(ConnectionProcessor.class)) {

                // Checks the WSValue passed to ConnectionProcessor.broadcastMessage
                ArgumentMatcher<WSValue> dataValueMatcher = arg ->
                // Some sims send the raw value, some send a serialized version.
                // If it doesn't throw an exception during serialization, it's
                // probably fine
                Objects.equals(arg, new WSValue(valueName, alternateValue.val1))
                        || Objects.equals(arg,
                                new WSValue(valueName, alternateValue.val2));

                assertDeepEquals(defaultValue.val1, getterFunction.apply(sim));
                setterFunction.accept(sim, alternateValue.val1);
                assertDeepEquals(alternateValue.val1,
                        getterFunction.apply(sim));
                connectionProcessor.verify(() -> ConnectionProcessor
                        .broadcastMessage(eq(finalDeviceName), eq(typeName),
                                (WSValue) argThat(dataValueMatcher)));

                // The robot code should be re-notified every time set is called
                setterFunction.accept(sim, alternateValue.val1);
                assertDeepEquals(alternateValue.val1,
                        getterFunction.apply(sim));
                connectionProcessor.verify(() -> ConnectionProcessor
                        .broadcastMessage(eq(finalDeviceName), eq(typeName),
                                (WSValue) argThat(dataValueMatcher)),
                        times(2) // Two times total
                );

                // No other messages should've been sent for this device
                connectionProcessor.verify(() -> ConnectionProcessor
                        .broadcastMessage(eq(finalDeviceName), eq(typeName),
                                argThat((WSValue arg) -> !dataValueMatcher
                                        .matches(arg))),
                        never());
            } finally {
                // Reset the value to the default.
                // (The other tests may check this if the sim is static)
                setValueFromRobot(deviceName, defaultValue);
            }
        }

        /**
         * A wrapper around {@link TestUtils#setValueFromRobot(String, String, String, Object)} that
         * auto-fills some of the parameters from the members of this class.
         *
         * @param deviceName the name of the device to set the value on
         * @param newValue the new value to set
         */
        private void setValueFromRobot(String deviceName,
                Pair<VarType, Object> newValue) {
            TestUtils.setValueFromRobot(deviceName, typeName, valueName,
                    newValue.val2);
        }

        /**
         * A variation of {@link Assert#assertEquals(Object, Object)} that uses
         * {@link Objects#deepEquals(Object, Object)}
         *
         * @param expected the expected object
         * @param actual the actual object
         */
        private void assertDeepEquals(Object expected, Object actual) {
            assertTrue(
                    "Expected: %s but got %s".formatted(formatObject(expected),
                            formatObject(actual)),
                    Objects.deepEquals(expected, actual));
        }

        /**
         * A utility function used by {@link #assertDeepEquals} to stringify objects in the event of
         * an error
         *
         * @param obj the object to stringify
         * @return the stringified object
         */
        private String formatObject(Object obj) {
            if (obj == null)
                return "<null>";
            if (obj instanceof Object[])
                return Arrays.toString((Object[]) obj);
            if (obj instanceof String)
                return "\"%s\"".formatted(obj);
            return obj.toString();
        }

        @Parameter(0)
        public String testName;
        @Parameter(1)
        public Function<String, SimType> constructor;
        @Parameter(2)
        public Function<ObjectCallback<VarType>, CallbackType> callbackConverter;
        @Parameter(3)
        public TriFunction<SimType, CallbackType, Boolean, CallbackType> callbackRegistrationFunction;
        @Parameter(4)
        public BiConsumer<SimType, CallbackType> callbackCancellationFunction;
        @Parameter(5)
        public Function<SimType, VarType> getterFunction;
        @Parameter(6)
        public BiConsumer<SimType, VarType> setterFunction;
        @Parameter(7)
        public Pair<VarType, Object> defaultValue;
        @Parameter(8)
        public Pair<VarType, Object> alternateValue;
        @Parameter(9)
        public String typeName;
        @Parameter(10)
        public String valueName;

        /**
         * Creates a device name that will not conflict with devices from other tests. Devices names
         * for the same test case will always be equal.
         *
         * @param testMethodName the name of the test being run
         * @return a device name unique to the given test
         */
        private String getDeviceName(String testMethodName) {
            return "DevicesTest.DataTests.%s-%s".formatted(testMethodName,
                    testName);
        }

    }

}
