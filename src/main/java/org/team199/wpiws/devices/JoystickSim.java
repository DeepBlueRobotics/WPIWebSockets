








package org.team199.wpiws.devices;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.team199.wpiws.ScopedObject;
import org.team199.wpiws.StateDevice;
import org.team199.wpiws.connection.ConnectionProcessor;
import org.team199.wpiws.connection.WSValue;
import org.team199.wpiws.interfaces.*;


public class JoystickSim {


    
    private static final JoystickSim.State STATE = new State();
    

    

    
    protected static JoystickSim.State getState() {
        return STATE;
    }
    

    
    
    public static ScopedObject<BooleanArrayCallback> registerButtonsCallback(BooleanArrayCallback callback, boolean initialNotify) {
        getState().BUTTONS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().buttons);
        }
        return new ScopedObject<>(callback, CANCEL_BUTTONS_CALLBACK);
    }

    public static final Consumer<BooleanArrayCallback> CANCEL_BUTTONS_CALLBACK = JoystickSim::cancelButtonsCallback;
    public static void cancelButtonsCallback(BooleanArrayCallback callback) {
        getState().BUTTONS_CALLBACKS.remove(callback);
    }

    public static boolean[] getButtons() {
        return getState().buttons;
    }

    public static void setButtons(boolean[] buttons) {
        setButtons(buttons, true);
    }

    public static final Consumer<BooleanArrayCallback> CALL_BUTTONS_CALLBACK = callback -> callback.callback("0", getState().buttons);
    private static void setButtons(boolean[] buttons, boolean notifyRobot) {
        if(buttons != getState().buttons) {
            getState().buttons = buttons;
            getState().BUTTONS_CALLBACKS.forEach(CALL_BUTTONS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "Joystick", new WSValue(">buttons", buttons));
        }
    }
    
    
    public static ScopedObject<DoubleArrayCallback> registerPovsCallback(DoubleArrayCallback callback, boolean initialNotify) {
        getState().POVS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().povs);
        }
        return new ScopedObject<>(callback, CANCEL_POVS_CALLBACK);
    }

    public static final Consumer<DoubleArrayCallback> CANCEL_POVS_CALLBACK = JoystickSim::cancelPovsCallback;
    public static void cancelPovsCallback(DoubleArrayCallback callback) {
        getState().POVS_CALLBACKS.remove(callback);
    }

    public static double[] getPovs() {
        return getState().povs;
    }

    public static void setPovs(double[] povs) {
        setPovs(povs, true);
    }

    public static final Consumer<DoubleArrayCallback> CALL_POVS_CALLBACK = callback -> callback.callback("0", getState().povs);
    private static void setPovs(double[] povs, boolean notifyRobot) {
        if(povs != getState().povs) {
            getState().povs = povs;
            getState().POVS_CALLBACKS.forEach(CALL_POVS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "Joystick", new WSValue(">povs", povs));
        }
    }
    
    
    public static ScopedObject<DoubleArrayCallback> registerAxesCallback(DoubleArrayCallback callback, boolean initialNotify) {
        getState().AXES_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().axes);
        }
        return new ScopedObject<>(callback, CANCEL_AXES_CALLBACK);
    }

    public static final Consumer<DoubleArrayCallback> CANCEL_AXES_CALLBACK = JoystickSim::cancelAxesCallback;
    public static void cancelAxesCallback(DoubleArrayCallback callback) {
        getState().AXES_CALLBACKS.remove(callback);
    }

    public static double[] getAxes() {
        return getState().axes;
    }

    public static void setAxes(double[] axes) {
        setAxes(axes, true);
    }

    public static final Consumer<DoubleArrayCallback> CALL_AXES_CALLBACK = callback -> callback.callback("0", getState().axes);
    private static void setAxes(double[] axes, boolean notifyRobot) {
        if(axes != getState().axes) {
            getState().axes = axes;
            getState().AXES_CALLBACKS.forEach(CALL_AXES_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "Joystick", new WSValue(">axes", axes));
        }
    }
    

    public static void processMessage(String device, List<WSValue> data) {
        
        for(WSValue value: data) {
            processValue(value);
        }
        
    }

    
    
    
    private static final BiConsumer<boolean[], Boolean> SET_BUTTONS = JoystickSim::setButtons;
    
    
    private static final BiConsumer<double[], Boolean> SET_POVS = JoystickSim::setPovs;
    
    
    private static final BiConsumer<double[], Boolean> SET_AXES = JoystickSim::setAxes;
    
    private static void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                
                
                
                case ">buttons": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), boolean[].class, SET_BUTTONS);
                    
                    break;
                }
                
                
                case ">povs": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), double[].class, SET_POVS);
                    
                    break;
                }
                
                
                case ">axes": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), double[].class, SET_AXES);
                    
                    break;
                }
                
            }
        }
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