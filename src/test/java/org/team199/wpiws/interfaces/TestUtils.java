package org.team199.wpiws.interfaces;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.team199.wpiws.connection.MessageProcessor;
import org.team199.wpiws.connection.WSValue;

public final class TestUtils {

    public static <T> void assertArrayEqualsIgnoringOrder(T[] expected,
            T[] actual) {
        String message = "Expected: %s but got %s"
                .formatted(Arrays.toString(expected), Arrays.toString(actual));
        assertEquals(message, expected.length, actual.length);
        assertTrue(message,
                Arrays.asList(actual).containsAll(Arrays.asList(expected)));
    }

    public static void setValueFromRobot(String deviceName, String typeName,
            String valueName, Object newValue) {
        MessageProcessor.process(deviceName, typeName,
                Arrays.asList(new WSValue(valueName, newValue)));
    }

    private TestUtils() {}

}
