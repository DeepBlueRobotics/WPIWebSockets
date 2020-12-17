package org.team199.wpiws;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.MessageDirection;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.BiLongCallback;
import org.team199.wpiws.interfaces.BooleanCallback;
import org.team199.wpiws.interfaces.DoubleCallback;
import org.team199.wpiws.interfaces.IntegerCallback;

public class EncoderSim extends StateDevice<EncoderSim.State> {

    private static final HashMap<String, State> STATE_MAP = new HashMap<>();
    private static final UniqueCopyOnWriteArrayList<String> INITIALIZED_DEVICES = new UniqueCopyOnWriteArrayList<>();
    private static final UniqueCopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();

    public EncoderSim(int index) {
        this("" + index);
    }

    public EncoderSim(String name) {
        super(name, STATE_MAP);
    }

    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(name -> callback.callback(name, true));
        }
        return new ScopedObject<BooleanCallback>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = EncoderSim::cancelStaticInitializedCallback;
    public static void cancelStaticInitializedCallback(BooleanCallback callback) {
        STATIC_INITIALIZED_CALLBACKS.remove(callback);
    }

    public ScopedObject<BooleanCallback> registerInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().init);
        }
        return new ScopedObject<BooleanCallback>(callback, CANCEL_INITIALIZED_CALLBACK);
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
            INITIALIZED_DEVICES.add(id);
        } else {
            INITIALIZED_DEVICES.remove(id);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(MessageDirection.INPUT, "init", initialized));
        }
    }

    public ScopedObject<IntegerCallback> registerCountCallback(IntegerCallback callback, boolean initialNotify) {
        getState().COUNT_CALLBACKS.add(callback);
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
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(MessageDirection.INPUT, "count", count));
        }
    }

    public ScopedObject<DoubleCallback> registerPeriodCallback(DoubleCallback callback, boolean initialNotify) {
        getState().PERIOD_CALLBACKS.add(callback);
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
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(MessageDirection.INPUT, "period", period));
        }
    }

    public ScopedObject<BooleanCallback> registerResetCallback(BooleanCallback callback, boolean initialNotify) {
        getState().RESET_CALLBACKS.add(callback);
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
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(MessageDirection.INPUT, "reset", reset));
        }
    }

    public ScopedObject<BooleanCallback> registerReverseDirectionCallback(BooleanCallback callback, boolean initialNotify) {
        getState().REVERSE_DIRECTION_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().reverseDirection);
        }
        return new ScopedObject<>(callback, CANCEL_REVERSE_DIRECTION_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_REVERSE_DIRECTION_CALLBACK = this::cancelReverseDirectionCallback;
    public void cancelReverseDirectionCallback(BooleanCallback callback) {
        getState().REVERSE_DIRECTION_CALLBACKS.remove(callback);
    }

    public boolean getReverseDirection() {
        return getState().reverseDirection;
    }

    public void setReverseDirection(boolean reverseDirection) {
        setReverseDirection(reverseDirection, true);
    }

    public final Consumer<BooleanCallback> CALL_REVERSE_DIRECTION_CALLBACK = callback -> callback.callback(id, getState().reverseDirection);
    private void setReverseDirection(boolean reverseDirection, boolean notifyRobot) {
        if(reverseDirection != getState().reverseDirection) {
            getState().reverseDirection = reverseDirection;
            getState().REVERSE_DIRECTION_CALLBACKS.forEach(CALL_REVERSE_DIRECTION_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(MessageDirection.INPUT, "reverse_direction", reverseDirection));
        }
    }

    public ScopedObject<IntegerCallback> registerSamplesToAverageCallback(IntegerCallback callback, boolean initialNotify) {
        getState().SAMPLES_TO_AVERAGE_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().samplesToAverage);
        }
        return new ScopedObject<>(callback, CANCEL_SAMPLES_TO_AVERAGE_CALLBACK);
    }

    public final Consumer<IntegerCallback> CANCEL_SAMPLES_TO_AVERAGE_CALLBACK = this::cancelSamplesToAverageCallback;
    public void cancelSamplesToAverageCallback(IntegerCallback callback) {
        getState().SAMPLES_TO_AVERAGE_CALLBACKS.remove(callback);
    }

    public int getSamplesToAverage() {
        return getState().samplesToAverage;
    }

    public void setSamplesToAverage(int samplesToAverage) {
        setSamplesToAverage(samplesToAverage, true);
    }

    public final Consumer<IntegerCallback> CALL_SAMPLES_TO_AVERAGE_CALLBACK = callback -> callback.callback(id, getState().samplesToAverage);
    private void setSamplesToAverage(int samplesToAverage, boolean notifyRobot) {
        if(samplesToAverage != getState().samplesToAverage) {
            getState().samplesToAverage = samplesToAverage;
            getState().SAMPLES_TO_AVERAGE_CALLBACKS.forEach(CALL_SAMPLES_TO_AVERAGE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(MessageDirection.INPUT, "samples_to_avg", samplesToAverage));
        }
    }

    public ScopedObject<BiLongCallback> registerChannelInitializedCallback(BiLongCallback callback, boolean initialNotify) {
        getState().CHANEL_INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify && getState().channelA != -1 && getState().channelB != -1) {
            callback.callback(id, getState().channelA, getState().channelB);
        }
        return new ScopedObject<>(callback, CANCEL_CHANNEL_INITIALIZED_CALLBACK);
    }

    public final Consumer<BiLongCallback> CANCEL_CHANNEL_INITIALIZED_CALLBACK = this::cancelChannelInitializedCallback;
    public void cancelChannelInitializedCallback(BiLongCallback callback) {
        getState().CHANEL_INITIALIZED_CALLBACKS.remove(callback);
    }

    public long getAChannel() {
        return getState().channelA;
    }

    public long getBChannel() {
        return getState().channelB;
    }

    @Override
    protected State generateState() {
        return new State();
    }

    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

    public Consumer<BiLongCallback> CALL_CHANNEL_INITIALIZED_CALLBACK = callback -> callback.callback(id, getState().channelA, getState().channelB);
    public void updateChannels() {
        if(getState().channelA != -1 && getState().channelB != -1) {
            getState().CHANEL_INITIALIZED_CALLBACKS.forEach(CALL_CHANNEL_INITIALIZED_CALLBACK);
        }
    }

    public static void processMessage(String device, List<WSValue> data) {
        EncoderSim simDevice = new EncoderSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final Consumer<Long> SET_CHANNEL_A = channelA -> getState().channelA = channelA;
    private final Consumer<Long> SET_CHANNEL_B = channelB -> getState().channelB = channelB;
    private final BiConsumer<Boolean, Boolean> SET_RESET = this::setReset;
    private final BiConsumer<Boolean, Boolean> SET_REVERSE_DIRECTION = this::setReverseDirection;
    private final BiConsumer<Integer, Boolean> SET_SAMPLES_TO_AVERAGE = this::setSamplesToAverage;
    private final BiConsumer<Integer, Boolean> SET_COUNT = this::setCount;
    private final BiConsumer<Double, Boolean> SET_PERIOD = this::setPeriod;
    public void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED);
                    break;
                }
                case "channel_a": {
                    filterMessage(value.getValue(), Long.class, SET_CHANNEL_A);
                    updateChannels();
                    break;
                }
                case "channel_b": {
                    filterMessage(value.getValue(), Long.class, SET_CHANNEL_B);
                    updateChannels();
                    break;
                }
                case "reset": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_RESET);
                    break;
                }
                case "reverse_direction": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_REVERSE_DIRECTION);
                    break;
                }
                case "samples_to_avg": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_SAMPLES_TO_AVERAGE);
                    break;
                }
                case "count": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Integer.class, SET_COUNT);
                    break;
                }
                case "period": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_PERIOD);
                    break;
                }
            }
        }
    }

    public static class State {
        public boolean init = false;
        public long channelA = -1;
        public long channelB = -1;
        public boolean reset = false;
        public boolean reverseDirection = false;
        public int samplesToAverage = 0;
        public int count = 0;
        public double period = 0;
        public final UniqueCopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<BooleanCallback> RESET_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<BooleanCallback> REVERSE_DIRECTION_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<IntegerCallback> SAMPLES_TO_AVERAGE_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<IntegerCallback> COUNT_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<DoubleCallback> PERIOD_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<BiLongCallback> CHANEL_INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
    }

}
