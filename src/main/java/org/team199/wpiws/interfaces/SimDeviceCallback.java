package org.team199.wpiws.interfaces;

/**
 * Represents a device callback which only accepts the name of a device
 */
@FunctionalInterface
public interface SimDeviceCallback {

    /**
     * Calls the callback
     * @param name the device identifier of the device calling the callback
     */
    public void callback(String name);

}
