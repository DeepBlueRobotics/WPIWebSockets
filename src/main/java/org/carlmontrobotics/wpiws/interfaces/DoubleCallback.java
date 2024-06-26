package org.carlmontrobotics.wpiws.interfaces;

/**
 * Represents a device callback which accepts a double value as data
 */
@FunctionalInterface
public interface DoubleCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the double data value
     */
    public void callback(String device, double value);

}
