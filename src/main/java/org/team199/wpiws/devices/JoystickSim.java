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

public class JoystickSim extends StateDevice<JoystickSim.State> {

    private static final HashMap<String, JoystickSim.State> STATE_MAP = new HashMap<>();

    public JoystickSim(String id) {
        super(id, STATE_MAP);
    }


    public ScopedObject<BooleanArrayCallback> registerButtonsCallback(BooleanArrayCallback callback, boolean initialNotify) {
        getState().BUTTONS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().buttons);
        }
        return new ScopedObject<>(callback, CANCEL_BUTTONS_CALLBACK);
    }

    public final Consumer<BooleanArrayCallback> CANCEL_BUTTONS_CALLBACK = this::cancelButtonsCallback;
    public void cancelButtonsCallback(BooleanArrayCallback callback) {
        getState().BUTTONS_CALLBACKS.remove(callback);
    }

    public boolean[] getButtons() {
        return getState().buttons;
    }

    public void setButtons(boolean[] buttons) {
        setButtons(buttons, true);
    }

    public final Consumer<BooleanArrayCallback> CALL_BUTTONS_CALLBACK = callback -> callback.callback(id, getState().buttons);
    private void setButtons(boolean[] buttons, boolean notifyRobot) {
        if(buttons != getState().buttons) {
            getState().buttons = buttons;
            getState().BUTTONS_CALLBACKS.forEach(CALL_BUTTONS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Joystick", new WSValue(">buttons", buttons));
        }
    }
    public ScopedObject<DoubleArrayCallback> registerPovsCallback(DoubleArrayCallback callback, boolean initialNotify) {
        getState().POVS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().povs);
        }
        return new ScopedObject<>(callback, CANCEL_POVS_CALLBACK);
    }

    public final Consumer<DoubleArrayCallback> CANCEL_POVS_CALLBACK = this::cancelPovsCallback;
    public void cancelPovsCallback(DoubleArrayCallback callback) {
        getState().POVS_CALLBACKS.remove(callback);
    }

    public double[] getPovs() {
        return getState().povs;
    }

    public void setPovs(double[] povs) {
        setPovs(povs, true);
    }

    public final Consumer<DoubleArrayCallback> CALL_POVS_CALLBACK = callback -> callback.callback(id, getState().povs);
    private void setPovs(double[] povs, boolean notifyRobot) {
        if(povs != getState().povs) {
            getState().povs = povs;
            getState().POVS_CALLBACKS.forEach(CALL_POVS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Joystick", new WSValue(">povs", povs));
        }
    }
    public ScopedObject<DoubleArrayCallback> registerAxesCallback(DoubleArrayCallback callback, boolean initialNotify) {
        getState().AXES_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().axes);
        }
        return new ScopedObject<>(callback, CANCEL_AXES_CALLBACK);
    }

    public final Consumer<DoubleArrayCallback> CANCEL_AXES_CALLBACK = this::cancelAxesCallback;
    public void cancelAxesCallback(DoubleArrayCallback callback) {
        getState().AXES_CALLBACKS.remove(callback);
    }

    public double[] getAxes() {
        return getState().axes;
    }

    public void setAxes(double[] axes) {
        setAxes(axes, true);
    }

    public final Consumer<DoubleArrayCallback> CALL_AXES_CALLBACK = callback -> callback.callback(id, getState().axes);
    private void setAxes(double[] axes, boolean notifyRobot) {
        if(axes != getState().axes) {
            getState().axes = axes;
            getState().AXES_CALLBACKS.forEach(CALL_AXES_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Joystick", new WSValue(">axes", axes));
        }
    }

    public static void processMessage(String device, List<WSValue> data) {
        JoystickSim simDevice = new JoystickSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<boolean[], Boolean> SET_BUTTONS = this::setButtons;
    private final BiConsumer<double[], Boolean> SET_POVS = this::setPovs;
    private final BiConsumer<double[], Boolean> SET_AXES = this::setAxes;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case ">buttons": {
                    filterMessageAndIgnoreRobotState(value.getValue(), boolean[].class, SET_BUTTONS);
                    break;
                }
                case ">povs": {
                    filterMessageAndIgnoreRobotState(value.getValue(), double[].class, SET_POVS);
                    break;
                }
                case ">axes": {
                    filterMessageAndIgnoreRobotState(value.getValue(), double[].class, SET_AXES);
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
        public boolean[] buttons = new boolean[0];
        public double[] povs = new double[0];
        public double[] axes = new double[0];
        public final CopyOnWriteArrayList<BooleanArrayCallback> BUTTONS_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleArrayCallback> POVS_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<DoubleArrayCallback> AXES_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}
