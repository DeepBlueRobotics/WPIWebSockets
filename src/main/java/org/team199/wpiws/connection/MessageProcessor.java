package org.team199.wpiws.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.team199.wpiws.devices.*;
import org.team199.wpiws.interfaces.DeviceMessageProcessor;

/**
 * Manages a list of {@link DeviceMessageProcessor} objects which handle messages for different device types
 */
public final class MessageProcessor {
    
    private static final HashMap<String, DeviceMessageProcessor> processors = new HashMap<>();
    private static final ArrayList<String> unknownTypes = new ArrayList<>();
    
    static {
        registerProcessor("Accel", AccelerometerSim::processMessage);
        registerProcessor("AI", AnalogInputSim::processMessage);
        registerProcessor("CANAccel", SimDeviceSim::processMessage);
        registerProcessor("CANAIn", SimDeviceSim::processMessage);
        registerProcessor("CANDIO", SimDeviceSim::processMessage);
        registerProcessor("CANDutyCycle", SimDeviceSim::processMessage);
        registerProcessor("CANEncoder", SimDeviceSim::processMessage);
        registerProcessor("CANGyro", SimDeviceSim::processMessage);
        registerProcessor("CANMotor", SimDeviceSim::processMessage);
        registerProcessor("dPWM", dPWMSim::processMessage);
        registerProcessor("DIO", DIOSim::processMessage);
        registerProcessor("DriverStation", DriverStationSim::processMessage);
        registerProcessor("DutyCycle", DutyCycleSim::processMessage);
        registerProcessor("Encoder", EncoderSim::processMessage);
        registerProcessor("Gyro", GyroSim::processMessage);
        registerProcessor("Joystick", JoystickSim::processMessage);
        registerProcessor("PWM", PWMSim::processMessage);
        registerProcessor("Relay", RelaySim::processMessage);
        registerProcessor("RoboRIO", RoboRIOSim::processMessage);
        registerProcessor("Solenoid", SolenoidSim::processMessage);
        registerProcessor("SimDevice", SimDeviceSim::processMessage);
    }

    /**
     * Registers a method to process messages for a specific device type
     * @param type the device type
     * @param processor the method to be called
     */
    public static void registerProcessor(String type, DeviceMessageProcessor processor) {
        if(type == null || type.isEmpty()) {
            return;
        }
        if(processor == null) {
            processors.remove(type);
            return;
        }
        processors.put(type, processor);
    }

    /**
     * Processes a message from the WPI HALSim and forwards it to the correct {@link MessageProcessor}
     * @param device the device name of the device which sent the message
     * @param type the device type of the device which sent the message
     * @param data the values of that device which have been modified
     */
    public static void process(String device, String type, List<WSValue> data) {
        DeviceMessageProcessor processor = processors.get(type);
        if(processor == null) {
            if(!unknownTypes.contains(type)) {
                unknownTypes.add(type);
                System.err.println("No processor found for device of type: \"" + type + "\" messages for devices of this type will be ignored");
            }
            return;
        }
        processor.processMessage(device, data);
    }
    
    private MessageProcessor() {}

}
