








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


public class AOSim extends StateDevice<AOSim.State> {


    
    private static final HashMap<String, AOSim.State> STATE_MAP = new HashMap<>();
    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
    

    
    public AOSim(String id) {
        super(id, STATE_MAP);
    }
    

    
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = AOSim::cancelStaticInitializedCallback;
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
            ConnectionProcessor.brodcastMessage(id, "AO", new WSValue("<init", initialized));
        }
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

    @Override
    protected State generateState() {
        return new State();
    }
    

    
    
    public ScopedObject<DoubleCallback> registerVoltageCallback(DoubleCallback callback, boolean initialNotify) {
        getState().VOLTAGE_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().voltage);
        }
        return new ScopedObject<>(callback, CANCEL_VOLTAGE_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_VOLTAGE_CALLBACK = this::cancelVoltageCallback;
    public void cancelVoltageCallback(DoubleCallback callback) {
        getState().VOLTAGE_CALLBACKS.remove(callback);
    }

    public double getVoltage() {
        return getState().voltage;
    }

    public void setVoltage(double voltage) {
        setVoltage(voltage, true);
    }

    public final Consumer<DoubleCallback> CALL_VOLTAGE_CALLBACK = callback -> callback.callback(id, getState().voltage);
    private void setVoltage(double voltage, boolean notifyRobot) {
        if(voltage != getState().voltage) {
            getState().voltage = voltage;
            getState().VOLTAGE_CALLBACKS.forEach(CALL_VOLTAGE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AO", new WSValue("<voltage", voltage));
        }
    }
    

    public static void processMessage(String device, List<WSValue> data) {
        
        AOSim simDevice = new AOSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
        
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
                
                
                
                case "<voltage": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VOLTAGE);
                    
                    break;
                }
                
            }
        }
    }

    public static class State {
        
        public boolean init = false;
        
        
        
        public double voltage = 0;
        
        
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        
        public final CopyOnWriteArrayList<DoubleCallback> VOLTAGE_CALLBACKS = new CopyOnWriteArrayList<>();
        
    }

}