package org.team199.wpiws.devices;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.team199.wpiws.Pair;
import org.team199.wpiws.TriFunction;
import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.MessageProcessor;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.ObjectCallback;
import org.team199.wpiws.types.LEDColor;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;

@RunWith(Enclosed.class)
public class DevicesTest {

    public static class InitializationTests {

        @Test
        public void testInitializationCallback() {

        }

        @Test
        public void testStaticInitializationCallback() {

        }

    }

    /**
     * @param <T> the sim type
     * @param <U> the parameter type
     * @param <R> the callback type
     */
    @RunWith(Parameterized.class)
    public static class DataTests<T, U, R> {

        private static <T, U, R> Object[] createTestCase(String typeName,
                String valueName, Function<String, T> constructor,
                TriFunction<T, R, Boolean, R> callbackRegistrationFunction,
                BiConsumer<T, R> callbackCancellationFunction,
                Function<T, U> getterFunction, BiConsumer<T, U> setterFunction,
                U defaultValue, U alternateValue,
                Function<U, Object> serializer,
                Function<ObjectCallback<U>, R> callbackConverter) {
            return new Object[] {
                "%s-'%s'".formatted(typeName, valueName), // testName
                constructor,
                callbackConverter,
                callbackRegistrationFunction,
                callbackCancellationFunction,
                getterFunction,
                setterFunction,
                new Pair<>(defaultValue, serializer.apply(defaultValue)), // defaultValue
                new Pair<>(alternateValue, serializer.apply(alternateValue)), // alternateValue
                typeName,
                valueName
            };
        }

        @Parameters(name = "{index}: {0}")
        public static Object[] callbacksToTest() {
            return new Object[] {
                createTestCase(
                    "AddressableLED",
                    "<data",
                    AddressableLEDSim::new,
                    AddressableLEDSim::registerDataCallback,
                    AddressableLEDSim::cancelDataCallback,
                    AddressableLEDSim::getData,
                    null,
                    new LEDColor[0],
                    new LEDColor[] { new LEDColor(0, 0, 0) },
                    (data) -> new JsonArray(Arrays.stream(data).map(LEDColor::toJson).map(jsonString -> {
                        try {
                            return Jsoner.deserialize(jsonString);
                        } catch(JsonException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList())),
                    c -> c::callback
                )
            };
        }

        @Test
        public void testCallback() {
            String deviceName = getDeviceName("testCallback");
            T sim = constructor.apply(deviceName);

            ArrayList<R> callbacks = new ArrayList<>();
            try {

                // First, we'll run our test case
                ObjectCallback<U> notifiedCallbackBeforeValueInit = mock();
                callbacks.add(registerCallback(sim,
                        notifiedCallbackBeforeValueInit, true));
                ObjectCallback<U> nonNotifiedCallbackBeforeValueInit = mock();
                callbacks.add(registerCallback(sim,
                        nonNotifiedCallbackBeforeValueInit, false));

                setValueFromRobot(deviceName, alternateValue);

                ObjectCallback<U> notifiedCallbackAfterValueInit = mock();
                callbacks.add(registerCallback(sim,
                        notifiedCallbackAfterValueInit, true));
                ObjectCallback<U> nonNotifiedCallbackAfterValueInit = mock();
                callbacks.add(registerCallback(sim,
                        nonNotifiedCallbackAfterValueInit, false));

                setValueFromRobot(deviceName, alternateValue);
                if (setterFunction != null) {
                    // Check that we also get callback notifications from the setter function
                    setterFunction.accept(sim, defaultValue.val1);
                } else {
                    // If there is no setter function, don't bother
                    setValueFromRobot(deviceName, defaultValue);
                }

                boolean isPrimitive = alternateValue.val1.getClass().isPrimitive();

                // Now, we'll check that we saw all the correct invocations
                InOrder order;

                order = inOrder(notifiedCallbackBeforeValueInit);
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                order.verify(notifiedCallbackBeforeValueInit, times(isPrimitive ? 1 : 2))
                        .callback(eq(deviceName), eq(alternateValue.val1));
                // Repeat sets should not trigger the callback
                order.verify(notifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(notifiedCallbackBeforeValueInit);

                order = inOrder(nonNotifiedCallbackBeforeValueInit);
                order.verify(nonNotifiedCallbackBeforeValueInit, times(isPrimitive ? 1 : 2))
                        .callback(eq(deviceName), eq(alternateValue.val1));
                // Repeat sets should not trigger the callback
                order.verify(nonNotifiedCallbackBeforeValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(nonNotifiedCallbackBeforeValueInit);

                order = inOrder(notifiedCallbackAfterValueInit);
                order.verify(notifiedCallbackAfterValueInit, times(isPrimitive ? 1 : 2))
                        .callback(eq(deviceName), eq(alternateValue.val1));
                // Repeat sets should not trigger the callback
                order.verify(notifiedCallbackAfterValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(notifiedCallbackAfterValueInit);

                order = inOrder(nonNotifiedCallbackAfterValueInit);
                // Repeat sets should not trigger the callback
                if(!isPrimitive) order.verify(nonNotifiedCallbackAfterValueInit)
                .callback(eq(deviceName), eq(alternateValue.val1));
                order.verify(nonNotifiedCallbackAfterValueInit)
                        .callback(eq(deviceName), eq(defaultValue.val1));
                verifyNoMoreInteractions(nonNotifiedCallbackAfterValueInit);
            } finally {
                callbacks.forEach(callback -> callbackCancellationFunction
                        .accept(sim, callback));
            }
        }

        private R registerCallback(T sim, ObjectCallback<U> callback,
                boolean initialNotify) {
            return callbackRegistrationFunction.apply(sim,
                    callbackConverter.apply(callback), initialNotify);
        }

        @Test
        public void testRobotSetter() {
            String deviceName = getDeviceName("testRobotSetter");
            T sim = constructor.apply(deviceName);

            try (MockedStatic<ConnectionProcessor> connectionProcessor =
                    mockStatic(ConnectionProcessor.class)) {

                assertEquals(defaultValue.val1, getterFunction.apply(sim));
                setValueFromRobot(deviceName, alternateValue);
                assertEquals(alternateValue.val1, getterFunction.apply(sim));

                // No messages should've been broadcast back to the
                connectionProcessor.verifyNoInteractions();
            }
        }

        @Test
        public void testSimSetter() {
            assumeNotNull(setterFunction); // If the setter doesn't exist, we can't test it

            String deviceName = getDeviceName("testSimSetter");
            T sim = constructor.apply(deviceName);

            try (MockedStatic<ConnectionProcessor> connectionProcessor =
                    mockStatic(ConnectionProcessor.class)) {

                assertEquals(defaultValue, getterFunction.apply(sim));
                setterFunction.accept(sim, alternateValue.val1);
                assertEquals(alternateValue.val1, getterFunction.apply(sim));
                connectionProcessor.verify(() -> ConnectionProcessor
                        .broadcastMessage(deviceName, typeName, eq(
                                new WSValue(valueName, connectionProcessor))));

                // The robot code should be re-notified every time set is called
                setterFunction.accept(sim, alternateValue.val1);
                assertEquals(alternateValue.val1, getterFunction.apply(sim));
                connectionProcessor.verify(() -> ConnectionProcessor
                        .broadcastMessage(eq(deviceName), eq(typeName), eq(
                                new WSValue(valueName, connectionProcessor))));

                connectionProcessor.verifyNoMoreInteractions();
            }
        }

        private void setValueFromRobot(String deviceName,
                Pair<U, Object> newValue) {
            MessageProcessor.process(deviceName, typeName,
                    Arrays.asList(new WSValue(valueName, newValue.val2)));
        }

        // A custom implementation of assertEquals that correctly handles arrays
        private void assertEquals(Object expected, Object actual) {
            if(expected != null && expected.getClass().isArray()) {
                assertArrayEquals((Object[]) expected, (Object[]) actual);
            } else {
                assertEquals(expected, actual);
            }
        }

        @Parameter(0)
        public String testName;
        @Parameter(1)
        public Function<String, T> constructor;
        @Parameter(2)
        public Function<ObjectCallback<U>, R> callbackConverter;
        @Parameter(3)
        public TriFunction<T, R, Boolean, R> callbackRegistrationFunction;
        @Parameter(4)
        public BiConsumer<T, R> callbackCancellationFunction;
        @Parameter(5)
        public Function<T, U> getterFunction;
        @Parameter(6)
        public BiConsumer<T, U> setterFunction;
        @Parameter(7)
        public Pair<U, Object> defaultValue;
        @Parameter(8)
        public Pair<U, Object> alternateValue;
        @Parameter(9)
        public String typeName;
        @Parameter(10)
        public String valueName;

        private String getDeviceName(String testMethodName) {
            return "DevicesTest.%s-%s".formatted(testMethodName, testName);
        }

    }

}
