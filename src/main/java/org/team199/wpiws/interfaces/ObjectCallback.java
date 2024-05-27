package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which accepts an object type as data
 */
@FunctionalInterface
public interface ObjectCallback<T> {

    /**
     * Calls the callback function
     * @param name the device identifier of the device calling the callback
     * @param value the data value
     */
    public void callback(String name, T value);

}
