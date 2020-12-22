package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which accepts two long values as data
 */
@FunctionalInterface
public interface BiLongCallback {
    
    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param val1 the first long data value
     * @param val2 the second long data value
     */
    public void callback(String name, long val1, long val2);

}
