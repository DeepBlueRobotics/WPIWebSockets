package org.carlmontrobotics.wpiws.interfaces;

/**
 * Represents a device callback which accepts a integer value as data
 */
@FunctionalInterface
public interface IntegerCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the integer data value
     */
    public void callback(String device, int value);

}
