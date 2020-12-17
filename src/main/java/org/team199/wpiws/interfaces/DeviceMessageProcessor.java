package org.team199.wpiws.interfaces;

import java.util.List;

import org.team199.wpiws.connection.WSValue;

@FunctionalInterface
public interface DeviceMessageProcessor {

    public void processMessage(String device, List<WSValue> data);

}
