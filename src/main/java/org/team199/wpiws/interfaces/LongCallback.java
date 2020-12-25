package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which accepts a long value as data
 */
@FunctionalInterface
public interface LongCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the long data value
     */
    public void callback(String device, long value);

}
