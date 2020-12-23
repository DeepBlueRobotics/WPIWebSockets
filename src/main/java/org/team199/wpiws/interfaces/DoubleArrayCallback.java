package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which accepts a double array as data
 */
@FunctionalInterface
public interface DoubleArrayCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the double data values
     */
    public void callback(String device, double[] value);

}
