// PLEASE DO NOT EDIT THIS FILE!!!
// This file is auto-generated by https://github.com/DeepBlueRobotics/WPIWebSocketsTemplate
// To regenerate please run:
// 'ag -o "WebotsWebSocketsImpl/src/main/org/team199/wpiws/devices" "<path/to/wpilib-ws.yaml>" "https://github.com/DeepBlueRobotics/WPIWebSocketsTemplate.git"'
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

public class dPWMSim extends StateDevice<dPWMSim.State> {

    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
    
    private static final HashMap<String, dPWMSim.State> STATE_MAP = new HashMap<>();

    public dPWMSim(String id) {
        super(id, STATE_MAP);
    }
    
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = dPWMSim::cancelStaticInitializedCallback;
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
            ConnectionProcessor.brodcastMessage(id, "dPWM", new WSValue("<init", initialized));
        }
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }
    
    @Override
    protected State generateState() {
        return new State();
    }
    
    public ScopedObject<DoubleCallback> registerDutyCycleCallback(DoubleCallback callback, boolean initialNotify) {
        getState().DUTYCYCLE_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().dutycycle);
        }
        return new ScopedObject<>(callback, CANCEL_DUTYCYCLE_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_DUTYCYCLE_CALLBACK = this::cancelDutyCycleCallback;
    public void cancelDutyCycleCallback(DoubleCallback callback) {
        getState().DUTYCYCLE_CALLBACKS.remove(callback);
    }

    public double getDutyCycle() {
        return getState().dutycycle;
    }

    public void setDutyCycle(double dutycycle) {
        setDutyCycle(dutycycle, true);
    }

    public final Consumer<DoubleCallback> CALL_DUTYCYCLE_CALLBACK = callback -> callback.callback(id, getState().dutycycle);
    private void setDutyCycle(double dutycycle, boolean notifyRobot) {
        if(dutycycle != getState().dutycycle) {
            getState().dutycycle = dutycycle;
            getState().DUTYCYCLE_CALLBACKS.forEach(CALL_DUTYCYCLE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "dPWM", new WSValue("<duty_cycle", dutycycle));
        }
    }

    public ScopedObject<IntegerCallback> registerDioPinCallback(IntegerCallback callback, boolean initialNotify) {
        getState().DIOPIN_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().diopin);
        }
        return new ScopedObject<>(callback, CANCEL_DIOPIN_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_DIOPIN_CALLBACK = this::cancelDioPinCallback;
    public void cancelDioPinCallback(IntegerCallback callback) {
        getState().DIOPIN_CALLBACKS.remove(callback);
    }

    public int getDioPin() {
        return getState().diopin;
    }

    public void setDioPin(int diopin) {
        setDioPin(diopin, true);
    }

    public final Consumer<IntegerCallback> CALL_DIOPIN_CALLBACK = callback -> callback.callback(id, getState().diopin);
    private void setDioPin(int diopin, boolean notifyRobot) {
        if(diopin != getState().diopin) {
            getState().diopin = diopin;
            getState().DIOPIN_CALLBACKS.forEach(CALL_DIOPIN_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "dPWM", new WSValue("<dio_pin", diopin));
        }
    }

    public static void processMessage(String device, List<WSValue> data) {
        dPWMSim simDevice = new dPWMSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final BiConsumer<Double, Boolean> SET_DUTYCYCLE = this::setDutyCycle;
    private final BiConsumer<Integer, Boolean> SET_DIOPIN = this::setDioPin;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case "<duty_cycle": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_DUTYCYCLE);
                    break;
                }
                case "<dio_pin": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_DIOPIN);
                    break;
                }
            }
        }
    }

    public static class State {
        public boolean init = false;
        public double dutycycle = 0;
        public int diopin = 0;
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleCallback> DUTYCYCLE_CALLBACKS = new CopyOnWriteArrayList<>();public final CopyOnWriteArrayList<IntegerCallback> DIOPIN_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}
