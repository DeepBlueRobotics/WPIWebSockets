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

    private static final HashMap<String, EncoderSim.State> STATE_MAP = new HashMap<>();
    private static final CopyOnWriteArrayList<String> INITIALIZED_DEVICES = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();

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

    public ScopedObject<LongCallback> registerChannelACallback(LongCallback callback, boolean initialNotify) {
        getState().CHANNELA_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().channela);
        }
        return new ScopedObject<>(callback, CANCEL_CHANNELA_CALLBACK);
    }

    public final Consumer<LongCallback> CANCEL_CHANNELA_CALLBACK = this::cancelChannelACallback;
    public void cancelChannelACallback(LongCallback callback) {
        getState().CHANNELA_CALLBACKS.remove(callback);
    }

    public long getChannelA() {
        return getState().channela;
    }

    public void setChannelA(long channela) {
        setChannelA(channela, true);
    }

    public final Consumer<LongCallback> CALL_CHANNELA_CALLBACK = callback -> callback.callback(id, getState().channela);
    private void setChannelA(long channela, boolean notifyRobot) {
        if(channela != getState().channela) {
            getState().channela = channela;
            getState().CHANNELA_CALLBACKS.forEach(CALL_CHANNELA_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<channel_a", channela));
        }
    }
    public ScopedObject<LongCallback> registerChannelBCallback(LongCallback callback, boolean initialNotify) {
        getState().CHANNELB_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().channelb);
        }
        return new ScopedObject<>(callback, CANCEL_CHANNELB_CALLBACK);
    }

    public final Consumer<LongCallback> CANCEL_CHANNELB_CALLBACK = this::cancelChannelBCallback;
    public void cancelChannelBCallback(LongCallback callback) {
        getState().CHANNELB_CALLBACKS.remove(callback);
    }

    public long getChannelB() {
        return getState().channelb;
    }

    public void setChannelB(long channelb) {
        setChannelB(channelb, true);
    }

    public final Consumer<LongCallback> CALL_CHANNELB_CALLBACK = callback -> callback.callback(id, getState().channelb);
    private void setChannelB(long channelb, boolean notifyRobot) {
        if(channelb != getState().channelb) {
            getState().channelb = channelb;
            getState().CHANNELB_CALLBACKS.forEach(CALL_CHANNELB_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<channel_b", channelb));
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
    public ScopedObject<BooleanCallback> registerResetCallback(BooleanCallback callback, boolean initialNotify) {
        getState().RESET_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().reset);
        }
        return new ScopedObject<>(callback, CANCEL_RESET_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_RESET_CALLBACK = this::cancelResetCallback;
    public void cancelResetCallback(BooleanCallback callback) {
        getState().RESET_CALLBACKS.remove(callback);
    }

    public boolean getReset() {
        return getState().reset;
    }

    public void setReset(boolean reset) {
        setReset(reset, true);
    }

    public final Consumer<BooleanCallback> CALL_RESET_CALLBACK = callback -> callback.callback(id, getState().reset);
    private void setReset(boolean reset, boolean notifyRobot) {
        if(reset != getState().reset) {
            getState().reset = reset;
            getState().RESET_CALLBACKS.forEach(CALL_RESET_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<reset", reset));
        }
    }
    public ScopedObject<BooleanCallback> registerReverseDirectionCallback(BooleanCallback callback, boolean initialNotify) {
        getState().REVERSEDIRECTION_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().reversedirection);
        }
        return new ScopedObject<>(callback, CANCEL_REVERSEDIRECTION_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_REVERSEDIRECTION_CALLBACK = this::cancelReverseDirectionCallback;
    public void cancelReverseDirectionCallback(BooleanCallback callback) {
        getState().REVERSEDIRECTION_CALLBACKS.remove(callback);
    }

    public boolean getReverseDirection() {
        return getState().reversedirection;
    }

    public void setReverseDirection(boolean reversedirection) {
        setReverseDirection(reversedirection, true);
    }

    public final Consumer<BooleanCallback> CALL_REVERSEDIRECTION_CALLBACK = callback -> callback.callback(id, getState().reversedirection);
    private void setReverseDirection(boolean reversedirection, boolean notifyRobot) {
        if(reversedirection != getState().reversedirection) {
            getState().reversedirection = reversedirection;
            getState().REVERSEDIRECTION_CALLBACKS.forEach(CALL_REVERSEDIRECTION_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue("<reverse_direction", reversedirection));
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

    public static void processMessage(String device, List<WSValue> data) {
        EncoderSim simDevice = new EncoderSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final BiConsumer<Long, Boolean> SET_CHANNELA = this::setChannelA;
    private final BiConsumer<Long, Boolean> SET_CHANNELB = this::setChannelB;
    private final BiConsumer<Integer, Boolean> SET_COUNT = this::setCount;
    private final BiConsumer<Double, Boolean> SET_PERIOD = this::setPeriod;
    private final BiConsumer<Boolean, Boolean> SET_RESET = this::setReset;
    private final BiConsumer<Boolean, Boolean> SET_REVERSEDIRECTION = this::setReverseDirection;
    private final BiConsumer<Integer, Boolean> SET_SAMPLESTOAVG = this::setSamplesToAvg;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case "<channel_a": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Long.class, SET_CHANNELA);
                    break;
                }
                case "<channel_b": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Long.class, SET_CHANNELB);
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
                case "<reset": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_RESET);
                    break;
                }
                case "<reverse_direction": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_REVERSEDIRECTION);
                    break;
                }
                case "<samples_to_avg": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_SAMPLESTOAVG);
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
        public long channela = 0;
        public long channelb = 0;
        public int count = 0;
        public double period = 0;
        public boolean reset = false;
        public boolean reversedirection = false;
        public int samplestoavg = 0;
        public final CopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<LongCallback> CHANNELA_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<LongCallback> CHANNELB_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<IntegerCallback> COUNT_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleCallback> PERIOD_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<BooleanCallback> RESET_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<BooleanCallback> REVERSEDIRECTION_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<IntegerCallback> SAMPLESTOAVG_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}
