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

public class PWMSim extends StateDevice<PWMSim.State> {

    private static final HashMap<String, PWMSim.State> STATE_MAP = new HashMap<>();
    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();

    public PWMSim(String id) {
        super(id, STATE_MAP);
    }

    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = PWMSim::cancelStaticInitializedCallback;
    public static void cancelStaticInitializedCallback(BooleanCallback callback) {
        STATIC_INITIALIZED_CALLBACKS.remove(callback);
    }
    
    public ScopedObject<BooleanCallback> registerInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().init);
        }
        return new ScopedObject<>(callback, CANCEL_INITIALIZED_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_INITIALIZED_CALLBACK = this::cancelInitializedCallback;
    public void cancelInitializedCallback(BooleanCallback callback) {
        getState().INITIALIZED_CALLBACKS.remove(callback);
    }

    public boolean getInitialized() {
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
            INITIALIZED_DEVICES.addIfAbsent(id);
        } else {
            INITIALIZED_DEVICES.remove(id);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue("<init", initialized));
        }
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

    public ScopedObject<DoubleCallback> registerSpeedCallback(DoubleCallback callback, boolean initialNotify) {
        getState().SPEED_CALLBACKS.addIfAbsent(callback);
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
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue("<speed", speed));
        }
    }
    public ScopedObject<DoubleCallback> registerPositionCallback(DoubleCallback callback, boolean initialNotify) {
        getState().POSITION_CALLBACKS.addIfAbsent(callback);
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
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue("<position", position));
        }
    }
    public ScopedObject<IntegerCallback> registerRawCallback(IntegerCallback callback, boolean initialNotify) {
        getState().RAW_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().raw);
        }
        return new ScopedObject<>(callback, CANCEL_RAW_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_RAW_CALLBACK = this::cancelRawCallback;
    public void cancelRawCallback(IntegerCallback callback) {
        getState().RAW_CALLBACKS.remove(callback);
    }

    public int getRaw() {
        return getState().raw;
    }

    public void setRaw(int raw) {
        setRaw(raw, true);
    }

    public final Consumer<IntegerCallback> CALL_RAW_CALLBACK = callback -> callback.callback(id, getState().raw);
    private void setRaw(int raw, boolean notifyRobot) {
        if(raw != getState().raw) {
            getState().raw = raw;
            getState().RAW_CALLBACKS.forEach(CALL_RAW_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue("<raw", raw));
        }
    }
    public ScopedObject<DoubleCallback> registerPeriodScaleCallback(DoubleCallback callback, boolean initialNotify) {
        getState().PERIODSCALE_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().periodscale);
        }
        return new ScopedObject<>(callback, CANCEL_PERIODSCALE_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_PERIODSCALE_CALLBACK = this::cancelPeriodScaleCallback;
    public void cancelPeriodScaleCallback(DoubleCallback callback) {
        getState().PERIODSCALE_CALLBACKS.remove(callback);
    }

    public double getPeriodScale() {
        return getState().periodscale;
    }

    public void setPeriodScale(double periodscale) {
        setPeriodScale(periodscale, true);
    }

    public final Consumer<DoubleCallback> CALL_PERIODSCALE_CALLBACK = callback -> callback.callback(id, getState().periodscale);
    private void setPeriodScale(double periodscale, boolean notifyRobot) {
        if(periodscale != getState().periodscale) {
            getState().periodscale = periodscale;
            getState().PERIODSCALE_CALLBACKS.forEach(CALL_PERIODSCALE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue("<period_scale", periodscale));
        }
    }
    public ScopedObject<BooleanCallback> registerZeroLatchCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ZEROLATCH_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().zerolatch);
        }
        return new ScopedObject<>(callback, CANCEL_ZEROLATCH_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_ZEROLATCH_CALLBACK = this::cancelZeroLatchCallback;
    public void cancelZeroLatchCallback(BooleanCallback callback) {
        getState().ZEROLATCH_CALLBACKS.remove(callback);
    }

    public boolean getZeroLatch() {
        return getState().zerolatch;
    }

    public void setZeroLatch(boolean zerolatch) {
        setZeroLatch(zerolatch, true);
    }

    public final Consumer<BooleanCallback> CALL_ZEROLATCH_CALLBACK = callback -> callback.callback(id, getState().zerolatch);
    private void setZeroLatch(boolean zerolatch, boolean notifyRobot) {
        if(zerolatch != getState().zerolatch) {
            getState().zerolatch = zerolatch;
            getState().ZEROLATCH_CALLBACKS.forEach(CALL_ZEROLATCH_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "PWM", new WSValue("<zero_latch", zerolatch));
        }
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
    private final BiConsumer<Integer, Boolean> SET_RAW = this::setRaw;
    private final BiConsumer<Double, Boolean> SET_PERIODSCALE = this::setPeriodScale;
    private final BiConsumer<Boolean, Boolean> SET_ZEROLATCH = this::setZeroLatch;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case "<speed": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_SPEED);
                    break;
                }
                case "<position": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_POSITION);
                    break;
                }
                case "<raw": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_RAW);
                    break;
                }
                case "<period_scale": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_PERIODSCALE);
                    break;
                }
                case "<zero_latch": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ZEROLATCH);
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
        public int raw = 0;
        public double periodscale = 0;
        public boolean zerolatch = false;
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleCallback> SPEED_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleCallback> POSITION_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<IntegerCallback> RAW_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleCallback> PERIODSCALE_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<BooleanCallback> ZEROLATCH_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}
