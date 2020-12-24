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

public class DIOSim extends StateDevice<DIOSim.State> {

    private static final HashMap<String, DIOSim.State> STATE_MAP = new HashMap<>();
    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();

    public DIOSim(String id) {
        super(id, STATE_MAP);
    }
    
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = DIOSim::cancelStaticInitializedCallback;
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
            ConnectionProcessor.brodcastMessage(id, "DIO", new WSValue("<init", initialized));
        }
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

    @Override
    protected State generateState() {
        return new State();
    }
    
    public ScopedObject<BooleanCallback> registerValueCallback(BooleanCallback callback, boolean initialNotify) {
        getState().VALUE_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().value);
        }
        return new ScopedObject<>(callback, CANCEL_VALUE_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_VALUE_CALLBACK = this::cancelValueCallback;
    public void cancelValueCallback(BooleanCallback callback) {
        getState().VALUE_CALLBACKS.remove(callback);
    }

    public boolean getValue() {
        return getState().value;
    }

    public void setValue(boolean value) {
        setValue(value, true);
    }

    public final Consumer<BooleanCallback> CALL_VALUE_CALLBACK = callback -> callback.callback(id, getState().value);
    private void setValue(boolean value, boolean notifyRobot) {
        if(value != getState().value) {
            getState().value = value;
            getState().VALUE_CALLBACKS.forEach(CALL_VALUE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DIO", new WSValue("<>value", value));
        }
    }

    public ScopedObject<IntegerCallback> registerPulseLengthCallback(IntegerCallback callback, boolean initialNotify) {
        getState().PULSELENGTH_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().pulselength);
        }
        return new ScopedObject<>(callback, CANCEL_PULSELENGTH_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_PULSELENGTH_CALLBACK = this::cancelPulseLengthCallback;
    public void cancelPulseLengthCallback(IntegerCallback callback) {
        getState().PULSELENGTH_CALLBACKS.remove(callback);
    }

    public int getPulseLength() {
        return getState().pulselength;
    }

    public void setPulseLength(int pulselength) {
        setPulseLength(pulselength, true);
    }

    public final Consumer<IntegerCallback> CALL_PULSELENGTH_CALLBACK = callback -> callback.callback(id, getState().pulselength);
    private void setPulseLength(int pulselength, boolean notifyRobot) {
        if(pulselength != getState().pulselength) {
            getState().pulselength = pulselength;
            getState().PULSELENGTH_CALLBACKS.forEach(CALL_PULSELENGTH_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DIO", new WSValue("<pulse_length", pulselength));
        }
    }

    public ScopedObject<BooleanCallback> registerInputCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INPUT_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().input);
        }
        return new ScopedObject<>(callback, CANCEL_INPUT_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_INPUT_CALLBACK = this::cancelInputCallback;
    public void cancelInputCallback(BooleanCallback callback) {
        getState().INPUT_CALLBACKS.remove(callback);
    }

    public boolean getInput() {
        return getState().input;
    }

    public void setInput(boolean input) {
        setInput(input, true);
    }

    public final Consumer<BooleanCallback> CALL_INPUT_CALLBACK = callback -> callback.callback(id, getState().input);
    private void setInput(boolean input, boolean notifyRobot) {
        if(input != getState().input) {
            getState().input = input;
            getState().INPUT_CALLBACKS.forEach(CALL_INPUT_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DIO", new WSValue("<input", input));
        }
    }

    public static void processMessage(String device, List<WSValue> data) {
        DIOSim simDevice = new DIOSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final BiConsumer<Boolean, Boolean> SET_VALUE = this::setValue;
    private final BiConsumer<Integer, Boolean> SET_PULSELENGTH = this::setPulseLength;
    private final BiConsumer<Boolean, Boolean> SET_INPUT = this::setInput;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case "<>value": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_VALUE);
                    break;
                }
                case "<pulse_length": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_PULSELENGTH);
                    break;
                }
                case "<input": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INPUT);
                    break;
                }
            }
        }
    }

    public static class State {
        public boolean init = false;
        public boolean value = false;
        public int pulselength = 0;
        public boolean input = false;
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<BooleanCallback> VALUE_CALLBACKS = new CopyOnWriteArrayList<>();public final CopyOnWriteArrayList<IntegerCallback> PULSELENGTH_CALLBACKS = new CopyOnWriteArrayList<>();public final CopyOnWriteArrayList<BooleanCallback> INPUT_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}