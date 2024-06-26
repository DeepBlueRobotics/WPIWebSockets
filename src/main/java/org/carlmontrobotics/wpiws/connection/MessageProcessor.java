package org.carlmontrobotics.wpiws.connection;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.carlmontrobotics.wpiws.devices.AccelerometerSim;
import org.carlmontrobotics.wpiws.devices.AddressableLEDSim;
import org.carlmontrobotics.wpiws.devices.AnalogInputSim;
import org.carlmontrobotics.wpiws.devices.AnalogOutputSim;
import org.carlmontrobotics.wpiws.devices.CANAccelerometerSim;
import org.carlmontrobotics.wpiws.devices.CANAnalogInputSim;
import org.carlmontrobotics.wpiws.devices.CANDIOSim;
import org.carlmontrobotics.wpiws.devices.CANDutyCycleSim;
import org.carlmontrobotics.wpiws.devices.CANEncoderSim;
import org.carlmontrobotics.wpiws.devices.CANGyroSim;
import org.carlmontrobotics.wpiws.devices.CANMotorSim;
import org.carlmontrobotics.wpiws.devices.DIOSim;
import org.carlmontrobotics.wpiws.devices.DriverStationSim;
import org.carlmontrobotics.wpiws.devices.DutyCycleSim;
import org.carlmontrobotics.wpiws.devices.EncoderSim;
import org.carlmontrobotics.wpiws.devices.GyroSim;
import org.carlmontrobotics.wpiws.devices.HALSim;
import org.carlmontrobotics.wpiws.devices.JoystickSim;
import org.carlmontrobotics.wpiws.devices.PCMSim;
import org.carlmontrobotics.wpiws.devices.PWMSim;
import org.carlmontrobotics.wpiws.devices.RelaySim;
import org.carlmontrobotics.wpiws.devices.RoboRIOSim;
import org.carlmontrobotics.wpiws.devices.SimDeviceSim;
import org.carlmontrobotics.wpiws.devices.SolenoidSim;
import org.carlmontrobotics.wpiws.devices.dPWMSim;
import org.carlmontrobotics.wpiws.interfaces.DeviceMessageProcessor;

/**
 * Manages a list of {@link DeviceMessageProcessor} objects which handle messages for different device types
 */
public final class MessageProcessor {

    private static final Map<String, DeviceMessageProcessor> processors = new ConcurrentHashMap<>();
    private static final Set<String> unknownTypes = new ConcurrentSkipListSet<>();

    static {
        registerProcessor("Accel", AccelerometerSim::processMessage);
        registerProcessor("AddressableLED", AddressableLEDSim::processMessage);
        registerProcessor("AI", AnalogInputSim::processMessage);
        registerProcessor("AO", AnalogOutputSim::processMessage);
        registerProcessor("CTREPCM", PCMSim::processMessage);
        registerProcessor("dPWM", dPWMSim::processMessage);
        registerProcessor("DIO", DIOSim::processMessage);
        registerProcessor("DriverStation", DriverStationSim::processMessage);
        registerProcessor("DutyCycle", DutyCycleSim::processMessage);
        registerProcessor("Encoder", EncoderSim::processMessage);
        registerProcessor("Gyro", GyroSim::processMessage);
        registerProcessor("HAL", HALSim::processMessage);
        registerProcessor("Joystick", JoystickSim::processMessage);
        registerProcessor("PWM", PWMSim::processMessage);
        registerProcessor("Relay", RelaySim::processMessage);
        registerProcessor("RoboRIO", RoboRIOSim::processMessage);
        registerProcessor("Solenoid", SolenoidSim::processMessage);
        registerProcessor("SimDevice", SimDeviceSim::processMessage);
        registerProcessor("CANMotor", CANMotorSim::processMessage);
        registerProcessor("CANEncoder", CANEncoderSim::processMessage);
        registerProcessor("CANDutyCycle", CANDutyCycleSim::processMessage);
        registerProcessor("CANAccel", CANAccelerometerSim::processMessage);
        registerProcessor("CANAIn", CANAnalogInputSim::processMessage);
        registerProcessor("CANDIO", CANDIOSim::processMessage);
        registerProcessor("CANGyro", CANGyroSim::processMessage);

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
        // Use the data type to find the appropriate processor. Fallback on SimDevice if the parsing above returned an invalid type.
        DeviceMessageProcessor processor = processors.get(type);
        if(processor == null) {
            if (unknownTypes.add(type)) {
                System.err.println(
                        "No processor found for device with data type: \""
                                + type
                                + "\". Messages with this data type will be ignored.");
            }
            return;
        }

        // Pass the actual device type to the processor which will pass it on to
        // the *Sim constructor so that the *Sim object creates messages with
        // the correct type.
        processor.processMessage(device, type, data);
    }

    private MessageProcessor() {}

}
