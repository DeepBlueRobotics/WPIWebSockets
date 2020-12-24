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

public class EncoderSim extends StateDevice<EncoderSim.State> {

    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
    
    private static final HashMap<String, EncoderSim.State> STATE_MAP = new HashMap<>();

    public EncoderSim(String id) {
        super(id, STATE_MAP);
    }
    
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return new ScopedObject<>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = EncoderSim::cancelStaticInitializedCallback;
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
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<init", initialized));
        }
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }
    
    @Override
    protected State generateState() {
        return new State();
    }
    
    public ScopedObject<IntegerCallback> registerChannelACallback(IntegerCallback callback, boolean initialNotify) {
        getState().CHANNELA_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().channela);
        }
        return new ScopedObject<>(callback, CANCEL_CHANNELA_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_CHANNELA_CALLBACK = this::cancelChannelACallback;
    public void cancelChannelACallback(IntegerCallback callback) {
        getState().CHANNELA_CALLBACKS.remove(callback);
    }

    public int getChannelA() {
        return getState().channela;
    }

    public void setChannelA(int channela) {
        setChannelA(channela, true);
    }

    public final Consumer<IntegerCallback> CALL_CHANNELA_CALLBACK = callback -> callback.callback(id, getState().channela);
    private void setChannelA(int channela, boolean notifyRobot) {
        if(channela != getState().channela) {
            getState().channela = channela;
            getState().CHANNELA_CALLBACKS.forEach(CALL_CHANNELA_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<channel_a", channela));
        }
    }

    public ScopedObject<IntegerCallback> registerChannelBCallback(IntegerCallback callback, boolean initialNotify) {
        getState().CHANNELB_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().channelb);
        }
        return new ScopedObject<>(callback, CANCEL_CHANNELB_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_CHANNELB_CALLBACK = this::cancelChannelBCallback;
    public void cancelChannelBCallback(IntegerCallback callback) {
        getState().CHANNELB_CALLBACKS.remove(callback);
    }

    public int getChannelB() {
        return getState().channelb;
    }

    public void setChannelB(int channelb) {
        setChannelB(channelb, true);
    }

    public final Consumer<IntegerCallback> CALL_CHANNELB_CALLBACK = callback -> callback.callback(id, getState().channelb);
    private void setChannelB(int channelb, boolean notifyRobot) {
        if(channelb != getState().channelb) {
            getState().channelb = channelb;
            getState().CHANNELB_CALLBACKS.forEach(CALL_CHANNELB_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<channel_b", channelb));
        }
    }

    public ScopedObject<IntegerCallback> registerSamplesToAvgCallback(IntegerCallback callback, boolean initialNotify) {
        getState().SAMPLESTOAVG_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().samplestoavg);
        }
        return new ScopedObject<>(callback, CANCEL_SAMPLESTOAVG_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_SAMPLESTOAVG_CALLBACK = this::cancelSamplesToAvgCallback;
    public void cancelSamplesToAvgCallback(IntegerCallback callback) {
        getState().SAMPLESTOAVG_CALLBACKS.remove(callback);
    }

    public int getSamplesToAvg() {
        return getState().samplestoavg;
    }

    public void setSamplesToAvg(int samplestoavg) {
        setSamplesToAvg(samplestoavg, true);
    }

    public final Consumer<IntegerCallback> CALL_SAMPLESTOAVG_CALLBACK = callback -> callback.callback(id, getState().samplestoavg);
    private void setSamplesToAvg(int samplestoavg, boolean notifyRobot) {
        if(samplestoavg != getState().samplestoavg) {
            getState().samplestoavg = samplestoavg;
            getState().SAMPLESTOAVG_CALLBACKS.forEach(CALL_SAMPLESTOAVG_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<samples_to_avg", samplestoavg));
        }
    }

    public ScopedObject<IntegerCallback> registerCountCallback(IntegerCallback callback, boolean initialNotify) {
        getState().COUNT_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().count);
        }
        return new ScopedObject<>(callback, CANCEL_COUNT_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_COUNT_CALLBACK = this::cancelCountCallback;
    public void cancelCountCallback(IntegerCallback callback) {
        getState().COUNT_CALLBACKS.remove(callback);
    }

    public int getCount() {
        return getState().count;
    }

    public void setCount(int count) {
        setCount(count, true);
    }

    public final Consumer<IntegerCallback> CALL_COUNT_CALLBACK = callback -> callback.callback(id, getState().count);
    private void setCount(int count, boolean notifyRobot) {
        if(count != getState().count) {
            getState().count = count;
            getState().COUNT_CALLBACKS.forEach(CALL_COUNT_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(">count", count));
        }
    }

    public ScopedObject<DoubleCallback> registerPeriodCallback(DoubleCallback callback, boolean initialNotify) {
        getState().PERIOD_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().period);
        }
        return new ScopedObject<>(callback, CANCEL_PERIOD_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_PERIOD_CALLBACK = this::cancelPeriodCallback;
    public void cancelPeriodCallback(DoubleCallback callback) {
        getState().PERIOD_CALLBACKS.remove(callback);
    }

    public double getPeriod() {
        return getState().period;
    }

    public void setPeriod(double period) {
        setPeriod(period, true);
    }

    public final Consumer<DoubleCallback> CALL_PERIOD_CALLBACK = callback -> callback.callback(id, getState().period);
    private void setPeriod(double period, boolean notifyRobot) {
        if(period != getState().period) {
            getState().period = period;
            getState().PERIOD_CALLBACKS.forEach(CALL_PERIOD_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(">period", period));
        }
    }

    public static void processMessage(String device, List<WSValue> data) {
        EncoderSim simDevice = new EncoderSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final BiConsumer<Integer, Boolean> SET_CHANNELA = this::setChannelA;
    private final BiConsumer<Integer, Boolean> SET_CHANNELB = this::setChannelB;
    private final BiConsumer<Integer, Boolean> SET_SAMPLESTOAVG = this::setSamplesToAvg;
    private final BiConsumer<Integer, Boolean> SET_COUNT = this::setCount;
    private final BiConsumer<Double, Boolean> SET_PERIOD = this::setPeriod;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case "<channel_a": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_CHANNELA);
                    break;
                }
                case "<channel_b": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_CHANNELB);
                    break;
                }
                case "<samples_to_avg": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_SAMPLESTOAVG);
                    break;
                }
                case ">count": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_COUNT);
                    break;
                }
                case ">period": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_PERIOD);
                    break;
                }
            }
        }
    }

    public static class State {
        public boolean init = false;
        public int channela = 0;
        public int channelb = 0;
        public int samplestoavg = 0;
        public int count = 0;
        public double period = 0;
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<IntegerCallback> CHANNELA_CALLBACKS = new CopyOnWriteArrayList<>();public final CopyOnWriteArrayList<IntegerCallback> CHANNELB_CALLBACKS = new CopyOnWriteArrayList<>();public final CopyOnWriteArrayList<IntegerCallback> SAMPLESTOAVG_CALLBACKS = new CopyOnWriteArrayList<>();public final CopyOnWriteArrayList<IntegerCallback> COUNT_CALLBACKS = new CopyOnWriteArrayList<>();public final CopyOnWriteArrayList<DoubleCallback> PERIOD_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}