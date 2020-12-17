package org.team199.wpiws;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.MessageDirection;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.SimDeviceCallback;
import org.team199.wpiws.interfaces.SimValueCallback;

public class SimDeviceSim extends StateDevice<SimDeviceSim.State> {

    private static final HashMap<String, SimDeviceSim.State> STATE_MAP = new HashMap<>();
    private static final UniqueCopyOnWriteArrayList<String> EXISTING_DEVICES = new UniqueCopyOnWriteArrayList<>();
    private static final UniqueCopyOnWriteArrayList<Pair<String, SimDeviceCallback>> DEVICE_CALLBACKS = new UniqueCopyOnWriteArrayList<>();

    public SimDeviceSim(String id) {
        super(id, STATE_MAP);
    }

    public String get(String name) {
        return getState().values.get(name);
    }

    public void set(String name, String value) {
        set(name, value, true);
    }

    public final Consumer<SimDeviceCallback> CALL_DEVICE_CALLBACK = callback -> callback.callback(id);
    public final Predicate<Pair<String, SimDeviceCallback>> APPLIES_TO_ME = callbackPair -> id.startsWith(callbackPair.val1);
    public static final Function<Pair<String, SimDeviceCallback>, SimDeviceCallback> FETCH_DEVICE_CALLBACK = pair -> pair.val2;
    public static final Function<Pair<String, SimValueCallback>, SimValueCallback> FETCH_VALUE_CALLBACK = pair -> pair.val2;
    private void set(String name, String value, boolean notifyRobot) {
        if(!EXISTING_DEVICES.contains(id)) {
            DEVICE_CALLBACKS.stream().filter(APPLIES_TO_ME).map(FETCH_DEVICE_CALLBACK).forEach(CALL_DEVICE_CALLBACK);
        }
        EXISTING_DEVICES.add(id);
        getState().values.put(name, value);
        Consumer<SimValueCallback> callCallback = callback -> callback.callback(name, value);
        if(!getState().existingValues.contains(name)) {
            getState().existingValues.add(name);
            getState().valueCreatedCallbacks.forEach(callCallback);
        }
        if(get(value) == null || !value.equals(get(value))) {
            getState().valueChangedCallbacks.stream().filter(callbackPair -> callbackPair.val1.equals(name)).map(FETCH_VALUE_CALLBACK).forEach(callCallback);
        }
        if(notifyRobot) {
            Object valueObj;
            if(getState().valueTypes.containsKey(name)) {
                switch(getState().valueTypes.get(name)) {
                    case "b": {
                        valueObj = value.equals("true");
                        break;
                    }
                    case "i": {
                        valueObj = Integer.parseInt(value);
                        break;
                    }
                    case "d": {
                        valueObj = Double.parseDouble(value);
                        break;
                    }
                    default: {
                        valueObj = value;
                        break;
                    }
                }
            } else {
                valueObj = value;
            }
            ConnectionProcessor.brodcastMessage(id, "SimDevices", new WSValue(MessageDirection.BOTH, name, valueObj));
        }
    }

    public ScopedObject<SimValueCallback> registerValueCreatedCallback(SimValueCallback callback, boolean initialNotify) {
        getState().valueCreatedCallbacks.add(callback);
        if(initialNotify) {
            getState().existingValues.forEach(value -> callback.callback(value, get(value)));
        }
        return new ScopedObject<>(callback, CANCEL_VALUE_CREATED_CALLBACK);
    }

    public final Consumer<SimValueCallback> CANCEL_VALUE_CREATED_CALLBACK = this::cancelValueCreatedCallback;
    public void cancelValueCreatedCallback(SimValueCallback callback) {
        getState().valueCreatedCallbacks.remove(callback);
    }
  
    public ScopedObject<Pair<String, SimValueCallback>> registerValueChangedCallback(String value, SimValueCallback callback, boolean initialNotify) {
        Pair<String, SimValueCallback> callbackPair = new Pair<>(value, callback);
        getState().valueChangedCallbacks.add(callbackPair);
        if(initialNotify) {
            callback.callback(value, get(value));
        }
        return new ScopedObject<>(callbackPair, CANCEL_VALUE_CHANGED_CALLBACK);
    }

    public final Consumer<Pair<String, SimValueCallback>> CANCEL_VALUE_CHANGED_CALLBACK = this::cancelValueChangedCallback;
    public void cancelValueChangedCallback(Pair<String, SimValueCallback> callback) {
        getState().valueChangedCallbacks.remove(callback);
    }
    
    public static String[] enumerateDevices(String prefix) {
        return EXISTING_DEVICES.stream().filter(name -> name.startsWith(prefix)).toArray(CREATE_STRING_ARRAY);
    }
  
    public static ScopedObject<Pair<String, SimDeviceCallback>> registerDeviceCreatedCallback(String prefix, SimDeviceCallback callback, boolean initialNotify) {
        Pair<String, SimDeviceCallback> callbackPair = new Pair<>(prefix, callback);
        DEVICE_CALLBACKS.add(callbackPair);
        if(initialNotify) {
            Arrays.stream(enumerateDevices(prefix)).forEach(callback::callback);
        }
        return new ScopedObject<>(callbackPair, CANCEL_DEVICE_CREATED_CALLBACK);
    }

    public static final Consumer<Pair<String, SimDeviceCallback>> CANCEL_DEVICE_CREATED_CALLBACK = SimDeviceSim::cancelDeviceCreatedCallback;
    public static void cancelDeviceCreatedCallback(Pair<String, SimDeviceCallback> callback) {
        DEVICE_CALLBACKS.remove(callback);
    }

    public static void processMessage(String device, List<WSValue> data) {
        SimDeviceSim simDevice = new SimDeviceSim(device);
        data.stream().filter(Objects::nonNull).forEach(value -> {
            if(Boolean.class.isAssignableFrom(value.getValue().getClass())) {
                simDevice.getState().valueTypes.put(value.getKey(), "b");
            } else if(Integer.class.isAssignableFrom(value.getValue().getClass())) {
                simDevice.getState().valueTypes.put(value.getKey(), "i");
            } else if(Double.class.isAssignableFrom(value.getValue().getClass())) {
                simDevice.getState().valueTypes.put(value.getKey(), "d");
            } else {
                simDevice.getState().valueTypes.put(value.getKey(), "s");
            }
            simDevice.set(value.getKey(), value.getValue().toString(), false);
        });
    }
    
    @Override
    public State generateState() {
        return new State();
    }
    
    public static class State {
        public final Map<String, String> values = new HashMap<>();
        public final Map<String, String> valueTypes = new HashMap<>();
        public final UniqueCopyOnWriteArrayList<String> existingValues = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<SimValueCallback> valueCreatedCallbacks = new UniqueCopyOnWriteArrayList<>();
        public final UniqueCopyOnWriteArrayList<Pair<String, SimValueCallback>> valueChangedCallbacks = new UniqueCopyOnWriteArrayList<>();
    }

}
