package org.team199.wpiws.connection;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.team199.wpiws.devices.AccelerometerSim;
import org.team199.wpiws.devices.AddressableLEDSim;
import org.team199.wpiws.devices.AnalogInputSim;
import org.team199.wpiws.devices.AnalogOutputSim;
import org.team199.wpiws.devices.CANEncoderSim;
import org.team199.wpiws.devices.CANMotorSim;
import org.team199.wpiws.devices.DIOSim;
import org.team199.wpiws.devices.DriverStationSim;
import org.team199.wpiws.devices.DutyCycleSim;
import org.team199.wpiws.devices.EncoderSim;
import org.team199.wpiws.devices.GyroSim;
import org.team199.wpiws.devices.HALSim;
import org.team199.wpiws.devices.JoystickSim;
import org.team199.wpiws.devices.PCMSim;
import org.team199.wpiws.devices.PWMSim;
import org.team199.wpiws.devices.RelaySim;
import org.team199.wpiws.devices.RoboRIOSim;
import org.team199.wpiws.devices.SimDeviceSim;
import org.team199.wpiws.devices.SolenoidSim;
import org.team199.wpiws.devices.dPWMSim;
import org.team199.wpiws.interfaces.DeviceMessageProcessor;

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
        registerProcessor("CANDutyCycle", DutyCycleSim::processMessage);
        registerProcessor("CANAccel", AccelerometerSim::processMessage);
        registerProcessor("CANAIn", AnalogInputSim::processMessage);
        registerProcessor("CANDIO", DIOSim::processMessage);
        registerProcessor("CANGyro", GyroSim::processMessage);

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
        // Per the spec, some message with a type of SimDevice have a data
        // format that is identical to hardware devices. In particular a
        // SimDevice message with device=DutyCyle:Name has the same data format
        // as a DutyCycle message, and a SimDevice message with
        // device=CAN{Gyro,AI,Accel,DIO,DutyCycle}:Name has the same data format
        // as a {Gyro,AI,Accel,DIO,DutyCycle} message. The
        // CAN{Gyro,AI,Accel,DIO,DutyCycle} processors are registered as aliases
        // for the the their non CAN counterparts. CANMotor and CANEncoder have
        // their own processors because there is no Motor processor and the
        // CANEncoder data format is different from the Encoder data format.
        String dataType = type;
        if (type.equals("SimDevice")) {
            String[] deviceParts = device.split(":");
            if (deviceParts.length > 1) {
                dataType = deviceParts[0];
            }
        }

        // Use the data type to find the appropriate processor. Fallback on SimDevice if the parsing above returned an invalid type.
        DeviceMessageProcessor processor = processors.getOrDefault(dataType, type.equals("SimDevice") ? processors.get("SimDevice") : null);
        if(processor == null) {
            if(unknownTypes.add(dataType)) {
                System.err.println("No processor found for device with data type: \"" + dataType + "\". Messages with this data type will be ignored.");
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
