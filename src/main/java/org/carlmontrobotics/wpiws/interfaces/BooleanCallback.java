package org.carlmontrobotics.wpiws.interfaces;

/**
 * Represents a device callback which accepts a boolean value as data
 */
@FunctionalInterface
public interface BooleanCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the boolean data value
     */
    public void callback(String device, boolean value);

}
