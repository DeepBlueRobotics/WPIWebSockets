








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


public class AISim extends StateDevice<AISim.State> {


    
    private static final HashMap<String, AISim.State> STATE_MAP = new HashMap<>();
    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
    

    
    public AISim(String id) {
        super(id, STATE_MAP);
    }
    

    
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = AISim::cancelStaticInitializedCallback;
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
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue("<init", initialized));
        }
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

    @Override
    protected State generateState() {
        return new State();
    }
    

    
    
    public ScopedObject<IntegerCallback> registerAvgBitsCallback(IntegerCallback callback, boolean initialNotify) {
        getState().AVGBITS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().avgbits);
        }
        return new ScopedObject<>(callback, CANCEL_AVGBITS_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_AVGBITS_CALLBACK = this::cancelAvgBitsCallback;
    public void cancelAvgBitsCallback(IntegerCallback callback) {
        getState().AVGBITS_CALLBACKS.remove(callback);
    }

    public int getAvgBits() {
        return getState().avgbits;
    }

    public void setAvgBits(int avgbits) {
        setAvgBits(avgbits, true);
    }

    public final Consumer<IntegerCallback> CALL_AVGBITS_CALLBACK = callback -> callback.callback(id, getState().avgbits);
    private void setAvgBits(int avgbits, boolean notifyRobot) {
        if(avgbits != getState().avgbits) {
            getState().avgbits = avgbits;
            getState().AVGBITS_CALLBACKS.forEach(CALL_AVGBITS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue("<avg_bits", avgbits));
        }
    }
    
    
    public ScopedObject<IntegerCallback> registerOversampleBitsCallback(IntegerCallback callback, boolean initialNotify) {
        getState().OVERSAMPLEBITS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().oversamplebits);
        }
        return new ScopedObject<>(callback, CANCEL_OVERSAMPLEBITS_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_OVERSAMPLEBITS_CALLBACK = this::cancelOversampleBitsCallback;
    public void cancelOversampleBitsCallback(IntegerCallback callback) {
        getState().OVERSAMPLEBITS_CALLBACKS.remove(callback);
    }

    public int getOversampleBits() {
        return getState().oversamplebits;
    }

    public void setOversampleBits(int oversamplebits) {
        setOversampleBits(oversamplebits, true);
    }

    public final Consumer<IntegerCallback> CALL_OVERSAMPLEBITS_CALLBACK = callback -> callback.callback(id, getState().oversamplebits);
    private void setOversampleBits(int oversamplebits, boolean notifyRobot) {
        if(oversamplebits != getState().oversamplebits) {
            getState().oversamplebits = oversamplebits;
            getState().OVERSAMPLEBITS_CALLBACKS.forEach(CALL_OVERSAMPLEBITS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue("<oversample_bits", oversamplebits));
        }
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
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue(">voltage", voltage));
        }
    }
    
    
    public ScopedObject<BooleanCallback> registerAccumInitCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ACCUMINIT_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().accuminit);
        }
        return new ScopedObject<>(callback, CANCEL_ACCUMINIT_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_ACCUMINIT_CALLBACK = this::cancelAccumInitCallback;
    public void cancelAccumInitCallback(BooleanCallback callback) {
        getState().ACCUMINIT_CALLBACKS.remove(callback);
    }

    public boolean getAccumInit() {
        return getState().accuminit;
    }

    public void setAccumInit(boolean accuminit) {
        setAccumInit(accuminit, true);
    }

    public final Consumer<BooleanCallback> CALL_ACCUMINIT_CALLBACK = callback -> callback.callback(id, getState().accuminit);
    private void setAccumInit(boolean accuminit, boolean notifyRobot) {
        if(accuminit != getState().accuminit) {
            getState().accuminit = accuminit;
            getState().ACCUMINIT_CALLBACKS.forEach(CALL_ACCUMINIT_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue("<accum_init", accuminit));
        }
    }
    
    
    public ScopedObject<DoubleCallback> registerAccumValueCallback(DoubleCallback callback, boolean initialNotify) {
        getState().ACCUMVALUE_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().accumvalue);
        }
        return new ScopedObject<>(callback, CANCEL_ACCUMVALUE_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_ACCUMVALUE_CALLBACK = this::cancelAccumValueCallback;
    public void cancelAccumValueCallback(DoubleCallback callback) {
        getState().ACCUMVALUE_CALLBACKS.remove(callback);
    }

    public double getAccumValue() {
        return getState().accumvalue;
    }

    public void setAccumValue(double accumvalue) {
        setAccumValue(accumvalue, true);
    }

    public final Consumer<DoubleCallback> CALL_ACCUMVALUE_CALLBACK = callback -> callback.callback(id, getState().accumvalue);
    private void setAccumValue(double accumvalue, boolean notifyRobot) {
        if(accumvalue != getState().accumvalue) {
            getState().accumvalue = accumvalue;
            getState().ACCUMVALUE_CALLBACKS.forEach(CALL_ACCUMVALUE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue(">accum_value", accumvalue));
        }
    }
    
    
    public ScopedObject<DoubleCallback> registerAccumCountCallback(DoubleCallback callback, boolean initialNotify) {
        getState().ACCUMCOUNT_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().accumcount);
        }
        return new ScopedObject<>(callback, CANCEL_ACCUMCOUNT_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_ACCUMCOUNT_CALLBACK = this::cancelAccumCountCallback;
    public void cancelAccumCountCallback(DoubleCallback callback) {
        getState().ACCUMCOUNT_CALLBACKS.remove(callback);
    }

    public double getAccumCount() {
        return getState().accumcount;
    }

    public void setAccumCount(double accumcount) {
        setAccumCount(accumcount, true);
    }

    public final Consumer<DoubleCallback> CALL_ACCUMCOUNT_CALLBACK = callback -> callback.callback(id, getState().accumcount);
    private void setAccumCount(double accumcount, boolean notifyRobot) {
        if(accumcount != getState().accumcount) {
            getState().accumcount = accumcount;
            getState().ACCUMCOUNT_CALLBACKS.forEach(CALL_ACCUMCOUNT_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue(">accum_count", accumcount));
        }
    }
    
    
    public ScopedObject<DoubleCallback> registerAccumCenterCallback(DoubleCallback callback, boolean initialNotify) {
        getState().ACCUMCENTER_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().accumcenter);
        }
        return new ScopedObject<>(callback, CANCEL_ACCUMCENTER_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_ACCUMCENTER_CALLBACK = this::cancelAccumCenterCallback;
    public void cancelAccumCenterCallback(DoubleCallback callback) {
        getState().ACCUMCENTER_CALLBACKS.remove(callback);
    }

    public double getAccumCenter() {
        return getState().accumcenter;
    }

    public void setAccumCenter(double accumcenter) {
        setAccumCenter(accumcenter, true);
    }

    public final Consumer<DoubleCallback> CALL_ACCUMCENTER_CALLBACK = callback -> callback.callback(id, getState().accumcenter);
    private void setAccumCenter(double accumcenter, boolean notifyRobot) {
        if(accumcenter != getState().accumcenter) {
            getState().accumcenter = accumcenter;
            getState().ACCUMCENTER_CALLBACKS.forEach(CALL_ACCUMCENTER_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue("<accum_center", accumcenter));
        }
    }
    
    
    public ScopedObject<DoubleCallback> registerAccumDeadbandCallback(DoubleCallback callback, boolean initialNotify) {
        getState().ACCUMDEADBAND_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().accumdeadband);
        }
        return new ScopedObject<>(callback, CANCEL_ACCUMDEADBAND_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_ACCUMDEADBAND_CALLBACK = this::cancelAccumDeadbandCallback;
    public void cancelAccumDeadbandCallback(DoubleCallback callback) {
        getState().ACCUMDEADBAND_CALLBACKS.remove(callback);
    }

    public double getAccumDeadband() {
        return getState().accumdeadband;
    }

    public void setAccumDeadband(double accumdeadband) {
        setAccumDeadband(accumdeadband, true);
    }

    public final Consumer<DoubleCallback> CALL_ACCUMDEADBAND_CALLBACK = callback -> callback.callback(id, getState().accumdeadband);
    private void setAccumDeadband(double accumdeadband, boolean notifyRobot) {
        if(accumdeadband != getState().accumdeadband) {
            getState().accumdeadband = accumdeadband;
            getState().ACCUMDEADBAND_CALLBACKS.forEach(CALL_ACCUMDEADBAND_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "AI", new WSValue("<accum_deadband", accumdeadband));
        }
    }
    

    public static void processMessage(String device, List<WSValue> data) {
        
        AISim simDevice = new AISim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
        
    }

    
    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    
    
    
    private final BiConsumer<Integer, Boolean> SET_AVGBITS = this::setAvgBits;
    
    
    private final BiConsumer<Integer, Boolean> SET_OVERSAMPLEBITS = this::setOversampleBits;
    
    
    private final BiConsumer<Double, Boolean> SET_VOLTAGE = this::setVoltage;
    
    
    private final BiConsumer<Boolean, Boolean> SET_ACCUMINIT = this::setAccumInit;
    
    
    private final BiConsumer<Double, Boolean> SET_ACCUMVALUE = this::setAccumValue;
    
    
    private final BiConsumer<Double, Boolean> SET_ACCUMCOUNT = this::setAccumCount;
    
    
    private final BiConsumer<Double, Boolean> SET_ACCUMCENTER = this::setAccumCenter;
    
    
    private final BiConsumer<Double, Boolean> SET_ACCUMDEADBAND = this::setAccumDeadband;
    
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                
                
                
                case "<avg_bits": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_AVGBITS);
                    
                    break;
                }
                
                
                case "<oversample_bits": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_OVERSAMPLEBITS);
                    
                    break;
                }
                
                
                case ">voltage": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VOLTAGE);
                    
                    break;
                }
                
                
                case "<accum_init": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ACCUMINIT);
                    
                    break;
                }
                
                
                case ">accum_value": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_ACCUMVALUE);
                    
                    break;
                }
                
                
                case ">accum_count": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_ACCUMCOUNT);
                    
                    break;
                }
                
                
                case "<accum_center": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_ACCUMCENTER);
                    
                    break;
                }
                
                
                case "<accum_deadband": {
                    
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_ACCUMDEADBAND);
                    
                    break;
                }
                
            }
        }
    }

    public static class State {
        
        public boolean init = false;
        
        
        
        public int avgbits = 0;
        
        
        public int oversamplebits = 0;
        
        
        public double voltage = 0;
        
        
        public boolean accuminit = false;
        
        
        public double accumvalue = 0;
        
        
        public double accumcount = 0;
        
        
        public double accumcenter = 0;
        
        
        public double accumdeadband = 0;
        
        
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        
        public final CopyOnWriteArrayList<IntegerCallback> AVGBITS_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<IntegerCallback> OVERSAMPLEBITS_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> VOLTAGE_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> ACCUMINIT_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> ACCUMVALUE_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> ACCUMCOUNT_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> ACCUMCENTER_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> ACCUMDEADBAND_CALLBACKS = new CopyOnWriteArrayList<>();
        
    }

}