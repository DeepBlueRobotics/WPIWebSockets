package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which accepts a String array as data
 */
@FunctionalInterface
public interface StringArrayCallback {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the String data values
     */
    public void callback(String name, String value);
    
}
