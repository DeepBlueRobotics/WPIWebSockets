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

/**
 * Represents a simulated encoder
 */
public class EncoderSim extends StateDevice<EncoderSim.State> {

    private static final HashMap<String, State> STATE_MAP = new HashMap<>();
    private static final UniqueCopyOnWriteArrayList<String> INITIALIZED_DEVICES = new UniqueCopyOnWriteArrayList<>();
    private static final UniqueCopyOnWriteArrayList<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();

    /**
     * Creates a new EncoderSim. This is equivalent to calling <code>EncoderSim("" + index)</code>
     * @param index the device identifier of this EncoderSim
     */
    public EncoderSim(int index) {
        this("" + index);
    }

    /**
     * Creates a new EncoderSim. This is equivalent to calling <code>EncoderSim("" + index)</code>
     * @param name the device identifier of this EncoderSim
     */
    public EncoderSim(String name) {
        super(name, STATE_MAP);
    }

    /**
     * Registers a BooleanCallback to be called whenever an EncoderSim device is initialized or uninitialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with the device identifiers of all currently initialized devices
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelStaticInitializedCallback(BooleanCallback)
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public static ScopedObject<BooleanCallback> registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(name -> callback.callback(name, true));
        }
        return new ScopedObject<BooleanCallback>(callback, CANCEL_STATIC_INITIALIZED_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelStaticInitializedCallback(BooleanCallback)}
     */
    public static final Consumer<BooleanCallback> CANCEL_STATIC_INITIALIZED_CALLBACK = EncoderSim::cancelStaticInitializedCallback;
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
        return new ScopedObject<BooleanCallback>(callback, CANCEL_INITIALIZED_CALLBACK);
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
     * @return whether this EncoderSim is initialized
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
        STATIC_INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
        getState().INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
        if(initialized) {
            INITIALIZED_DEVICES.add(id);
        } else {
            INITIALIZED_DEVICES.remove(id);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Encoder", new WSValue(MessageDirection.INPUT, "init", initialized));
        }
    }

    /**
     * Registers a IntegerCallback to be called whenever the count value of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current count
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelCountCallback(IntegerCallback)
     */
    public ScopedObject<IntegerCallback> registerCountCallback(IntegerCallback callback, boolean initialNotify) {
        getState().COUNT_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().count);
        }
        return new ScopedObject<>(callback, CANCEL_COUNT_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelCountCallback(IntegerCallback)}
     */
    public final Consumer<IntegerCallback> CANCEL_COUNT_CALLBACK = this::cancelCountCallback;
    /**
     * Deregisters the given count callback
     * @param callback the callback to deregister
     * @see #registerCountCallback(IntegerCallback, boolean)
     */
    public void cancelCountCallback(IntegerCallback callback) {
        getState().COUNT_CALLBACKS.remove(callback);
    }

    /**
     * @return the current count value of this EncoderSim
     */
    public int getCount() {
        return getState().count;
    }

    /**
     * Sets the count value of this EncoderSim
     * @param count the new count value of this EncoderSim
     */
    public void setCount(int count) {
        setCount(count, true);
    }

    /**
     * A Consumer which calls the given callback with the current count value of this EncoderSim
     */
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

    /**
     * Registers a IntegerCallback to be called whenever the period value of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current period
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelCountCallback(IntegerCallback)
     */
    public ScopedObject<DoubleCallback> registerPeriodCallback(DoubleCallback callback, boolean initialNotify) {
        getState().PERIOD_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().period);
        }
        return new ScopedObject<>(callback, CANCEL_PERIOD_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelPeriodCallback(DoubleCallback)}
     */
    public final Consumer<DoubleCallback> CANCEL_PERIOD_CALLBACK = this::cancelPeriodCallback;
    /**
     * Deregisters the given period callback
     * @param callback the callback to deregister
     * @see #registerPeriodCallback(DoubleCallback, boolean)
     */
    public void cancelPeriodCallback(DoubleCallback callback) {
        getState().PERIOD_CALLBACKS.remove(callback);
    }

    /**
     * @return the current period value of this EncoderSim
     */
    public double getPeriod() {
        return getState().period;
    }

    /**
     * Sets the period value of this EncoderSim
     * @param period the new period value of this EncoderSim
     */
    public void setPeriod(double period) {
        setPeriod(period, true);
    }

    /**
     * A Consumer which calls the given callback with the current period value of this EncoderSim
     */
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

    /**
     * Registers a IntegerCallback to be called whenever the samples to average value of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current samples to average value
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelSamplesToAverageCallback(IntegerCallback)
     */
    public ScopedObject<IntegerCallback> registerSamplesToAverageCallback(IntegerCallback callback, boolean initialNotify) {
        getState().SAMPLES_TO_AVERAGE_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().samplesToAverage);
        }
        return new ScopedObject<>(callback, CANCEL_SAMPLES_TO_AVERAGE_CALLBACK);
    }

    /**
     * A Consumer which calls {@link #cancelSamplesToAverageCallback(IntegerCallback)}
     */
    public final Consumer<IntegerCallback> CANCEL_SAMPLES_TO_AVERAGE_CALLBACK = this::cancelSamplesToAverageCallback;
    /**
     * Deregisters the given samples to average callback
     * @param callback the callback to deregister
     * @see #registerSamplesToAverageCallback(IntegerCallback, boolean)
     */
    public void cancelSamplesToAverageCallback(IntegerCallback callback) {
        getState().SAMPLES_TO_AVERAGE_CALLBACKS.remove(callback);
    }

    /**
     * @return the current samples to average value of this EncoderSim
     */
    public int getSamplesToAverage() {
        return getState().samplesToAverage;
    }

    /**
     * Sets the samples to average value of this EncoderSim
     * @param samplesToAverage the new samples to average value of this EncoderSim
     */
    public void setSamplesToAverage(int samplesToAverage) {
        setSamplesToAverage(samplesToAverage, true);
    }

    /**
     * A Consumer which calls the given callback with the current samples to average value of this EncoderSim
     */
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

    /**
     * Registers a BiLongCallback to be called whenever both of the channel values of this EncoderSim are initialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current channel values if they have been initialized
     * @return a ScopedObject which can be used to close the callback
     * @see #cancelChannelInitializedCallback(BiLongCallback)
     */
    public ScopedObject<BiLongCallback> registerChannelInitializedCallback(BiLongCallback callback, boolean initialNotify) {
        getState().CHANEL_INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify && getState().channelA != -1 && getState().channelB != -1) {
            callback.callback(id, getState().channelA, getState().channelB);
        }
        return new ScopedObject<>(callback, CANCEL_CHANNEL_INITIALIZED_CALLBACK);
    }

    
    /**
     * A Consumer which calls {@link #cancelChannelInitializedCallback(BiLongCallback)}
     */
    public final Consumer<BiLongCallback> CANCEL_CHANNEL_INITIALIZED_CALLBACK = this::cancelChannelInitializedCallback;
    /**
     * Deregisters the given channel initialized callback
     * @param callback the callback to deregister
     * @see #registerChannelInitializedCallback(BiLongCallback, boolean)
     */
    public void cancelChannelInitializedCallback(BiLongCallback callback) {
        getState().CHANEL_INITIALIZED_CALLBACKS.remove(callback);
    }

    /**
     * @return the a channel of this EncoderSim or <code>-1</code> if it has not yet been set
     */
    public long getAChannel() {
        return getState().channelA;
    }

    /**
     * @return the b channel of this EncoderSim or <code>-1</code> if it has not yet been set
     */
    public long getBChannel() {
        return getState().channelB;
    }

    @Override
    protected State generateState() {
        return new State();
    }

    /**
     * @return an array of the identifiers of all currently initialized EncoderSims
     */
    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(CREATE_STRING_ARRAY);
    }

    /**
     * Calls the given channel initialized callback with {@link #getAChannel()} and {@link #getBChannel()}
     */
    public Consumer<BiLongCallback> CALL_CHANNEL_INITIALIZED_CALLBACK = callback -> callback.callback(id, getState().channelA, getState().channelB);
    /**
     * Calls all channel initialized callbacks if both {@link #getAChannel()} and {@link #getBChannel()} are not <code>-1</code>
     */
    public void updateChannels() {
        if(getState().channelA != -1 && getState().channelB != -1) {
            getState().CHANEL_INITIALIZED_CALLBACKS.forEach(CALL_CHANNEL_INITIALIZED_CALLBACK);
        }
    }

    /**
     * An implementation of {@link org.team199.wpiws.interfaces.DeviceMessageProcessor} which processes WPI HALSim messages for EncoderSims
     * @param device the device identifier of the device sending the message
     * @param data the data associated with the message
     */
    public static void processMessage(String device, List<WSValue> data) {
        EncoderSim simDevice = new EncoderSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    private final Consumer<Long> SET_CHANNEL_A = channelA -> getState().channelA = channelA;
    private final Consumer<Long> SET_CHANNEL_B = channelB -> getState().channelB = channelB;
    private final BiConsumer<Integer, Boolean> SET_SAMPLES_TO_AVERAGE = this::setSamplesToAverage;
    private final BiConsumer<Integer, Boolean> SET_COUNT = this::setCount;
    private final BiConsumer<Double, Boolean> SET_PERIOD = this::setPeriod;
    private void processValue(WSValue value) {
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

    /**
     * Contains all information about the state of an EncoderSim
     */
    public static class State {
        public boolean init = false;
        public long channelA = -1;
        public long channelB = -1;
        public int samplesToAverage = 0;
        public int count = 0;
        public double period = 0;
        public final UniqueCopyOnWriteArrayList<BooleanCallback> INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<IntegerCallback> SAMPLES_TO_AVERAGE_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<IntegerCallback> COUNT_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<DoubleCallback> PERIOD_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<BiLongCallback> CHANEL_INITIALIZED_CALLBACKS = new UniqueCopyOnWriteArrayList<>();
    }

}
