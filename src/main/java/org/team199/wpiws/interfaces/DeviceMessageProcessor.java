package org.team199.wpiws.interfaces;

import java.util.List;

import org.team199.wpiws.connection.WSValue;

/**
 * Represents a function which processes WPI HALSim messages for a specific device type
 * @see org.team199.wpiws.connection.MessageProcessor
 */
@FunctionalInterface
public interface DeviceMessageProcessor {

    /**
     * Processes a WPI HALSim message
     * @param device the device identifier of the device sending the message
     * @param data the data associated with the message
     */
    public void processMessage(String device, List<WSValue> data);

}
