package org.team199.wpiws.interfaces;

import java.util.Arrays;

import org.team199.wpiws.connection.MessageProcessor;
import org.team199.wpiws.connection.WSValue;

public final class TestUtils {


    public static void setValueFromRobot(String deviceName, String typeName,
            String valueName, Object newValue) {
        MessageProcessor.process(deviceName, typeName,
                Arrays.asList(new WSValue(valueName, newValue)));
    }

    private TestUtils() {}

}
