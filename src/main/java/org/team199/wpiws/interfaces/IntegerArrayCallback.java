package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which accepts a integer array as data
 */
@FunctionalInterface
public interface IntegerArrayCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the integer data values
     */
    public void callback(String device, int[] value);

}
