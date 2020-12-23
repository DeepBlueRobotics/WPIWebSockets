package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which accepts a boolean array as data
 */
@FunctionalInterface
public interface BooleanArrayCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the boolean data values
     */
    public void callback(String device, boolean[] value);

}
