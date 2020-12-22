package org.team199.wpiws;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.MessageDirection;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.BooleanCallback;
import org.team199.wpiws.interfaces.DoubleCallback;

/**
 * Represents a simulated PWM device
 */
public class PWMSim extends StateDevice<PWMSim.State> {
    
    private static final HashMap<String, PWMSim.State> STATE_MAP = new HashMap<>();
    private static final UniqueCopyOnWriteArrayList<String> INITIALIZED_DEVICES = new UniqueCopyOnWriteArrayList<>();
    private static final UniqueCopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();

    /**
     * Creates a new PWMSim. This is equivalent to calling <code>PWMSim("" + port)</code>
     * @param port the device identifier of this PWMSim
     */
    public PWMSim(int port) {
        this("" + port);
    }

    /**
     * Creates a new PWMSim. This is equivalent to calling <code>PWMSim("" + index)</code>
     * @param port the device identifier of this PWMSim
     */
    public PWMSim(String port) {
        super(port, STATE_MAP);
    }
    
    /**
     * Registers a BooleanCallback to be called whenever a PWMSim device is initialized or uninitialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with the device identifiers of all currently initialized devices
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelStaticInitializedCallback(BooleanCallback)
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelStaticInitializedCallback(BooleanCallback)}
     */
    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = PWMSim::cancelStaticInitializedCallback;
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
        getState().INITIALIZED_CALLBACKS.add(callback);
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
     * @return whether this PWMSim is initialized
     */
    public boolean getInitialized() {
        return getState().init;
    }

    /**
     * Sets the initialized state of this EncoderSim
     * @param initialized the new initialized state of this EncoderSim
     */
    public void setInitialized(boolean initialized) {
        setInitialized(initialized, true);
    }

    /**
     * Sets the initialized state of this EncoderSim
     * @param initialized the new initialized state of this EncoderSim
     */
    public final Consumer<BooleanCallback> CALL_INITIALIZED_CALLBACK = callback -> callback.callback(id, getState().init);
    private void setInitialized(boolean initialized, boolean notifyRobot) {
        getState().init = initialized;
        if(initialized) {
            STATIC_INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            getState().INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            INITIALIZED_DEVICES.add(id);
        } else {
            INITIALIZED_DEVICES.remove(id);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue(MessageDirection.INPUT, "init", initialized));
        }
    }

    /**
     * Registers a IntegerCallback to be called whenever the speed of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current speed
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelSpeedCallback(DoubleCallback)
     */
    public ScopedObject<DoubleCallback> registerSpeedCallback(DoubleCallback callback, boolean initialNotify) {
        getState().SPEED_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().speed);
        }
        return new ScopedObject<>(callback, CANCEL_SPEED_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelSpeedCallback(DoubleCallback)}
     */
    public final Consumer<DoubleCallback> CANCEL_SPEED_CALLBACK = this::cancelSpeedCallback;
    /**
     * Deregisters the given speed callback
     * @param callback the callback to deregister
     * @see #registerSpeedCallback(DoubleCallback, boolean)
     */
    public void cancelSpeedCallback(DoubleCallback callback) {
        getState().SPEED_CALLBACKS.remove(callback);
    }

    /**
     * @return the current speed of this PWMSim
     */
    public double getSpeed() {
        return getState().speed;
    }

    /**
     * Sets the speed of this PWMSim
     * @param speed the new speed value of this PWMSim
     */
    public void setSpeed(double speed) {
        setSpeed(speed, true);
    }

    /**
     * A Consumer which calls the given callback with the current speed of this PWMSim
     */
    public final Consumer<DoubleCallback> CALL_SPEED_CALLBACK = callback -> callback.callback(id, getState().speed);
    private void setSpeed(double speed, boolean notifyRobot) {
        if(speed != getState().speed) {
            getState().speed = speed;
            getState().SPEED_CALLBACKS.forEach(CALL_SPEED_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue(MessageDirection.INPUT, "speed", speed));
        }
    }

    /**
     * Registers a IntegerCallback to be called whenever the position of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current position
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelPositionCallback(DoubleCallback)
     */
    public ScopedObject<DoubleCallback> registerPositionCallback(DoubleCallback callback, boolean initialNotify) {
        getState().POSITION_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().position);
        }
        return new ScopedObject<>(callback, CANCEL_POSITION_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelPositionCallback(DoubleCallback)}
     */
    public final Consumer<DoubleCallback> CANCEL_POSITION_CALLBACK = this::cancelPositionCallback;
    /**
     * Deregisters the given position callback
     * @param callback the callback to deregister
     * @see #registerPositionCallback(DoubleCallback, boolean)
     */
    public void cancelPositionCallback(DoubleCallback callback) {
        getState().POSITION_CALLBACKS.remove(callback);
    }

    /**
     * @return the current position of this PWMSim
     */
    public double getPosition() {
        return getState().position;
    }

    /**
     * Sets the position of this PWMSim
     * @param position the new position of this PWMSim
     */
    public void setPosition(double position) {
        setPosition(position, true);
    }

    /**
     * A Consumer which calls the given callback with the current position of this PWMSim
     */
    public final Consumer<DoubleCallback> CALL_POSITION_CALLBACK = callback -> callback.callback(id, getState().position);
    private void setPosition(double position, boolean notifyRobot) {
        if(position != getState().position) {
            getState().position = position;
            getState().POSITION_CALLBACKS.forEach(CALL_POSITION_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue(MessageDirection.INPUT, "position", position));
        }
    }

    /**
     * @return an array of the identifiers of all currently initialized PWMSims
     */
    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

    /**
     * An implementation of {@link org.team199.wpiws.interfaces.DeviceMessageProcessor} which processes WPI HALSim messages for PWMSims
     * @param device the device identifier of the device sending the message
     * @param data the data associated with the message
     */
    public static void processMessage(String device, List<WSValue> data) {
        PWMSim simDevice = new PWMSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final BiConsumer<Double, Boolean> SET_SPEED = this::setSpeed;
    private final BiConsumer<Double, Boolean> SET_POSITION = this::setPosition;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case "speed": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_SPEED);
                    break;
                }
                case "position": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_POSITION);
                    break;
                }
            }
        }
    }

    @Override
    protected State generateState() {
        return new State();
    }

    /**
     * Contains all information about the state of a PWMSim
     */
    public static class State {
        public boolean init = false;
        public double speed = 0;
        public double position = 0;
        public final UniqueCopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<DoubleCallback> SPEED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<DoubleCallback> POSITION_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
    }
}
