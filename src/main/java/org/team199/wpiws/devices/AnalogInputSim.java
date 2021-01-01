// PLEASE DO NOT EDIT THIS FILE!!!
// This file is auto-generated.
// To regenerate all of these *Sim.java files, run the following from the WPIWebSockets folder:
// npx -p @asyncapi/generator ag --force-write -o ./src/main/java/org/team199/wpiws/devices asyncapi-template/wpilib-ws.yaml asyncapi-template/
package org.team199.wpiws.devices;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.team199.wpiws.ScopedObject;
import org.team199.wpiws.StateDevice;
import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.*;

/**
 * Represents a simulated analoginput
 */
public class AnalogInputSim extends StateDevice<AnalogInputSim.State> {

    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
    
    private static final HashMap<String, AnalogInputSim.State> STATE_MAP = new HashMap<>();

    /**
     * Creates a new AnalogInputSim
     * @param id the device identifier of this AnalogInputSim 
     */
    public AnalogInputSim(String id) {
        super(id, STATE_MAP);
    }
    
    /**
     * Registers a BooleanCallback to be called whenever true AnalogInputSim device is initialized or uninitialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with the device identifiers of all currently initialized AnalogInputSims
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelStaticInitializedCallback(BooleanCallback)
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelStaticInitializedCallback(BooleanCallback)}
     */
    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = AnalogInputSim::cancelStaticInitializedCallback;
    /**
     * Deregisters the given static initialized callback
     * @param callback the callback to deregister
     * @see #registerStaticInitializedCallback(BooleanCallback, boolean)
     */
    public static void cancelStaticInitializedCallback(BooleanCallback callback) {
        STATIC_INITIALIZED_CALLBACKS.remove(callback);
    }
    
    /**
     * Registers a BooleanCallback to be called whenever this device is initialized or uninitialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current initialized state
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelInitializedCallback(BooleanCallback)
     * @see #registerStaticInitializedCallback(BooleanCallback, boolean)
     */
    public ScopedObject<BooleanCallback> registerInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().init);
        }
        return new ScopedObject<>(callback, CANCEL_INITIALIZED_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelInitializedCallback(BooleanCallback)}
     */
    public final Consumer<BooleanCallback> CANCEL_INITIALIZED_CALLBACK = this::cancelInitializedCallback;
    /**
     * Deregisters the given initialized callback
     * @param callback the callback to deregister
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public void cancelInitializedCallback(BooleanCallback callback) {
        getState().INITIALIZED_CALLBACKS.remove(callback);
    }

    /**
     * @return whether this AnalogInputSim is initialized
     */
    public boolean getInitialized() {
        return getState().init;
    }

    /**
     * Sets the initialized state of this AnalogInputSim
     * @param initialized the new initialized state of this AnalogInputSim
     */
    public void setInitialized(boolean initialized) {
        setInitialized(initialized, true);
    }

    /**
     * A Consumer which calls the given BooleanCallback with the current initialized state of this AnalogInputSim
     */
    public final Consumer<BooleanCallback> CALL_INITIALIZED_CALLBACK = callback -> callback.callback(id, getState().init);
    private void setInitialized(boolean initialized, boolean notifyRobot) {
        getState().init = initialized;
        if(initialized) {
            STATIC_INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            getState().INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            INITIALIZED_DEVICES.addIfAbsent(id);
        } else {
            INITIALIZED_DEVICES.remove(id);
        }
        if(notifyRobot) {
            ConnectionProcessor.broadcastMessage(id, "AI", new WSValue("<init", initialized));
        }
    }

    /**
     * @return an array of the identifiers of all currently initialized AnalogInputSims
     */
    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }
    
    @Override
    protected State generateState() {
        return new State();
    }
    
    /**
     * Registers a DoubleCallback to be called whenever the voltage of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current voltage value
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelVoltageCallback(DoubleCallback)
     */
    public ScopedObject<DoubleCallback> registerVoltageCallback(DoubleCallback callback, boolean initialNotify) {
        getState().VOLTAGE_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().voltage);
        }
        return new ScopedObject<>(callback, CANCEL_VOLTAGE_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelVoltageCallback(DoubleCallback)}
     */
    public final Consumer<DoubleCallback> CANCEL_VOLTAGE_CALLBACK = this::cancelVoltageCallback;
    /**
     * Deregisters the given voltage callback
     * @param callback the callback to deregister
     * @see #registerVoltageCallback(DoubleCallback, boolean)
     */
    public void cancelVoltageCallback(DoubleCallback callback) {
        getState().VOLTAGE_CALLBACKS.remove(callback);
    }

    /**
     * @return input voltage, in volts
     * @see #setVoltage(double)
     */
    public double getVoltage() {
        return getState().voltage;
    }

    /**
     * Set input voltage, in volts
     * @see #getVoltage()
     */
    public void setVoltage(double voltage) {
        setVoltage(voltage, true);
    }

    /**
     * A Consumer which calls the given callback with the current voltage value of this PWMSim
     */
    public final Consumer<DoubleCallback> CALL_VOLTAGE_CALLBACK = callback -> callback.callback(id, getState().voltage);
    private void setVoltage(double voltage, boolean notifyRobot) {
        if(voltage != getState().voltage) {
            getState().voltage = voltage;
            getState().VOLTAGE_CALLBACKS.forEach(CALL_VOLTAGE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.broadcastMessage(id, "AI", new WSValue(">voltage", voltage));
        }
    }

    /**
     * An implementation of {@link org.team199.wpiws.interfaces.DeviceMessageProcessor} which processes WPI HALSim messages for AnalogInputSims
     * @param device the device identifier of the device sending the message
     * @param data the data associated with the message
     */
    public static void processMessage(String device, List<WSValue> data) {
        // Process all of the values, but save the "<init" value for last
        // so that the rest of the state has been set when the initialize
        // callback is called.
        WSValue init = null;
        AnalogInputSim simDevice = new AnalogInputSim(device);

        for(WSValue value: data) {
            if (value.getKey().equals("<init"))
                init = value;
            else
                simDevice.processValue(value);
        }
        if (init != null)
            simDevice.processValue(init);
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final BiConsumer<Double, Boolean> SET_VOLTAGE = this::setVoltage;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case ">voltage": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VOLTAGE);
                    break;
                }
                default: {
                    System.err.println("AnalogInputSim ignored unrecognized WSValue: " + value.getKey() + ":" + value.getValue());
                }
            }
        }
    }

    /**
     * Contains all information about the state of a AnalogInputSim
     */
    public static class State {
        public boolean init = false;
        public double voltage = 0;
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleCallback> VOLTAGE_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}
