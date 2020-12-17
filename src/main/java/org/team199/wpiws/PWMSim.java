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

public class PWMSim extends StateDevice<PWMSim.State> {
    
    private static final HashMap<String, PWMSim.State> STATE_MAP = new HashMap<>();
    private static final UniqueCopyOnWriteArrayList<String> INITIALIZED_DEVICES = new UniqueCopyOnWriteArrayList<>();
    private static final UniqueCopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();

    public PWMSim(int port) {
        this("" + port);
    }

    public PWMSim(String port) {
        super(port, STATE_MAP);
    }
    
    public static synchronized ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = PWMSim::cancelStaticInitializedCallback;
    public static synchronized void cancelStaticInitializedCallback(BooleanCallback callback) {
        STATIC_INITIALIZED_CALLBACKS.remove(callback);
    }
    
    public synchronized ScopedObject<BooleanCallback> registerInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().init);
        }
        return new ScopedObject<>(callback, CANCEL_INITIALIZED_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_INITIALIZED_CALLBACK = this::cancelInitializedCallback;
    public synchronized void cancelInitializedCallback(BooleanCallback callback) {
        getState().INITIALIZED_CALLBACKS.remove(callback);
    }

    public synchronized boolean getInitialized() {
        return getState().init;
    }

    public void setInitialized(boolean initialized) {
        setInitialized(initialized, true);
    }

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

    public ScopedObject<DoubleCallback> registerSpeedCallback(DoubleCallback callback, boolean initialNotify) {
        getState().SPEED_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().speed);
        }
        return new ScopedObject<>(callback, CANCEL_SPEED_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_SPEED_CALLBACK = this::cancelSpeedCallback;
    public void cancelSpeedCallback(DoubleCallback callback) {
        getState().SPEED_CALLBACKS.remove(callback);
    }

    public double getSpeed() {
        return getState().speed;
    }

    public void setSpeed(double speed) {
        setSpeed(speed, true);
    }

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

    public ScopedObject<DoubleCallback> registerPositionCallback(DoubleCallback callback, boolean initialNotify) {
        getState().POSITION_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().position);
        }
        return new ScopedObject<>(callback, CANCEL_POSITION_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_POSITION_CALLBACK = this::cancelPositionCallback;
    public void cancelPositionCallback(DoubleCallback callback) {
        getState().POSITION_CALLBACKS.remove(callback);
    }

    public double getPosition() {
        return getState().position;
    }

    public void setPosition(double position) {
        setPosition(position, true);
    }

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

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

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
                case "init": {//System.out.println(value.getValue());
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

    public static class State {
        public boolean init = false;
        public double speed = 0;
        public double position = 0;
        public final UniqueCopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<DoubleCallback> SPEED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<DoubleCallback> POSITION_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
    }
}
