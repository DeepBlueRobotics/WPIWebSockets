package org.carlmontrobotics.wpiws;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.carlmontrobotics.wpiws.connection.MessageProcessor;
import org.carlmontrobotics.wpiws.connection.WSValue;

/**
 * Provides various utility functions for other test classes
 */
public final class TestUtils {

    /**
     * Asserts that two arrays contain the same elements in any order. The arrays must not contain
     * any duplicate elements.
     *
     * @param <T> the type of the arrays
     * @param expected the expected array
     * @param actual the actual array
     */
    public static <T> void assertArrayEqualsIgnoringOrder(T[] expected,
            T[] actual) {
        assertTrue("Arrays may not contain duplicate elements",
                Arrays.stream(expected).distinct().count() == expected.length);
        assertTrue("Arrays may not contain duplicate elements",
                Arrays.stream(actual).distinct().count() == actual.length);

        String message = "Expected: %s but got %s"
                .formatted(Arrays.toString(expected), Arrays.toString(actual));
        assertEquals(message, expected.length, actual.length);
        assertTrue(message,
                Arrays.asList(actual).containsAll(Arrays.asList(expected)));
    }

    /**
     * Simulates a WebSockets message from the robot setting a value on a device
     *
     * @param deviceName the name of the device to set the value on
     * @param typeName the type of the device
     * @param valueName the name of the value to set
     * @param newValue the new value
     */
    public static void setValueFromRobot(String deviceName, String typeName,
            String valueName, Object newValue) {
        MessageProcessor.process(deviceName, typeName,
                Arrays.asList(new WSValue(valueName, newValue)));
    }

    private TestUtils() {}

}
