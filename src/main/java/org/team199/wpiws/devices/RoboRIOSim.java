








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


public class RoboRIOSim {


    
    private static final RoboRIOSim.State STATE = new State();
    

    

    
    protected static RoboRIOSim.State getState() {
        return STATE;
    }
    

    
    
    public static ScopedObject<BooleanCallback> registerFpgaButtonCallback(BooleanCallback callback, boolean initialNotify) {
        getState().FPGABUTTON_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().fpgabutton);
        }
        return new ScopedObject<>(callback, CANCEL_FPGABUTTON_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_FPGABUTTON_CALLBACK = RoboRIOSim::cancelFpgaButtonCallback;
    public static void cancelFpgaButtonCallback(BooleanCallback callback) {
        getState().FPGABUTTON_CALLBACKS.remove(callback);
    }

    public static boolean getFpgaButton() {
        return getState().fpgabutton;
    }

    public static void setFpgaButton(boolean fpgabutton) {
        setFpgaButton(fpgabutton, true);
    }

    public static final Consumer<BooleanCallback> CALL_FPGABUTTON_CALLBACK = callback -> callback.callback("0", getState().fpgabutton);
    private static void setFpgaButton(boolean fpgabutton, boolean notifyRobot) {
        if(fpgabutton != getState().fpgabutton) {
            getState().fpgabutton = fpgabutton;
            getState().FPGABUTTON_CALLBACKS.forEach(CALL_FPGABUTTON_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">fpga_button", fpgabutton));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerVinVoltageCallback(DoubleCallback callback, boolean initialNotify) {
        getState().VINVOLTAGE_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().vinvoltage);
        }
        return new ScopedObject<>(callback, CANCEL_VINVOLTAGE_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_VINVOLTAGE_CALLBACK = RoboRIOSim::cancelVinVoltageCallback;
    public static void cancelVinVoltageCallback(DoubleCallback callback) {
        getState().VINVOLTAGE_CALLBACKS.remove(callback);
    }

    public static double getVinVoltage() {
        return getState().vinvoltage;
    }

    public static void setVinVoltage(double vinvoltage) {
        setVinVoltage(vinvoltage, true);
    }

    public static final Consumer<DoubleCallback> CALL_VINVOLTAGE_CALLBACK = callback -> callback.callback("0", getState().vinvoltage);
    private static void setVinVoltage(double vinvoltage, boolean notifyRobot) {
        if(vinvoltage != getState().vinvoltage) {
            getState().vinvoltage = vinvoltage;
            getState().VINVOLTAGE_CALLBACKS.forEach(CALL_VINVOLTAGE_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">vin_voltage", vinvoltage));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerVinCurrentCallback(DoubleCallback callback, boolean initialNotify) {
        getState().VINCURRENT_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().vincurrent);
        }
        return new ScopedObject<>(callback, CANCEL_VINCURRENT_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_VINCURRENT_CALLBACK = RoboRIOSim::cancelVinCurrentCallback;
    public static void cancelVinCurrentCallback(DoubleCallback callback) {
        getState().VINCURRENT_CALLBACKS.remove(callback);
    }

    public static double getVinCurrent() {
        return getState().vincurrent;
    }

    public static void setVinCurrent(double vincurrent) {
        setVinCurrent(vincurrent, true);
    }

    public static final Consumer<DoubleCallback> CALL_VINCURRENT_CALLBACK = callback -> callback.callback("0", getState().vincurrent);
    private static void setVinCurrent(double vincurrent, boolean notifyRobot) {
        if(vincurrent != getState().vincurrent) {
            getState().vincurrent = vincurrent;
            getState().VINCURRENT_CALLBACKS.forEach(CALL_VINCURRENT_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">vin_current", vincurrent));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerVoltage6vCallback(DoubleCallback callback, boolean initialNotify) {
        getState().VOLTAGE6V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().voltage6v);
        }
        return new ScopedObject<>(callback, CANCEL_VOLTAGE6V_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_VOLTAGE6V_CALLBACK = RoboRIOSim::cancelVoltage6vCallback;
    public static void cancelVoltage6vCallback(DoubleCallback callback) {
        getState().VOLTAGE6V_CALLBACKS.remove(callback);
    }

    public static double getVoltage6v() {
        return getState().voltage6v;
    }

    public static void setVoltage6v(double voltage6v) {
        setVoltage6v(voltage6v, true);
    }

    public static final Consumer<DoubleCallback> CALL_VOLTAGE6V_CALLBACK = callback -> callback.callback("0", getState().voltage6v);
    private static void setVoltage6v(double voltage6v, boolean notifyRobot) {
        if(voltage6v != getState().voltage6v) {
            getState().voltage6v = voltage6v;
            getState().VOLTAGE6V_CALLBACKS.forEach(CALL_VOLTAGE6V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">6v_voltage", voltage6v));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerCurrent6vCallback(DoubleCallback callback, boolean initialNotify) {
        getState().CURRENT6V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().current6v);
        }
        return new ScopedObject<>(callback, CANCEL_CURRENT6V_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_CURRENT6V_CALLBACK = RoboRIOSim::cancelCurrent6vCallback;
    public static void cancelCurrent6vCallback(DoubleCallback callback) {
        getState().CURRENT6V_CALLBACKS.remove(callback);
    }

    public static double getCurrent6v() {
        return getState().current6v;
    }

    public static void setCurrent6v(double current6v) {
        setCurrent6v(current6v, true);
    }

    public static final Consumer<DoubleCallback> CALL_CURRENT6V_CALLBACK = callback -> callback.callback("0", getState().current6v);
    private static void setCurrent6v(double current6v, boolean notifyRobot) {
        if(current6v != getState().current6v) {
            getState().current6v = current6v;
            getState().CURRENT6V_CALLBACKS.forEach(CALL_CURRENT6V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">6v_current", current6v));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerActive6vCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ACTIVE6V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().active6v);
        }
        return new ScopedObject<>(callback, CANCEL_ACTIVE6V_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_ACTIVE6V_CALLBACK = RoboRIOSim::cancelActive6vCallback;
    public static void cancelActive6vCallback(BooleanCallback callback) {
        getState().ACTIVE6V_CALLBACKS.remove(callback);
    }

    public static boolean getActive6v() {
        return getState().active6v;
    }

    public static void setActive6v(boolean active6v) {
        setActive6v(active6v, true);
    }

    public static final Consumer<BooleanCallback> CALL_ACTIVE6V_CALLBACK = callback -> callback.callback("0", getState().active6v);
    private static void setActive6v(boolean active6v, boolean notifyRobot) {
        if(active6v != getState().active6v) {
            getState().active6v = active6v;
            getState().ACTIVE6V_CALLBACKS.forEach(CALL_ACTIVE6V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">6v_active", active6v));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerFaults6vCallback(DoubleCallback callback, boolean initialNotify) {
        getState().FAULTS6V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().faults6v);
        }
        return new ScopedObject<>(callback, CANCEL_FAULTS6V_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_FAULTS6V_CALLBACK = RoboRIOSim::cancelFaults6vCallback;
    public static void cancelFaults6vCallback(DoubleCallback callback) {
        getState().FAULTS6V_CALLBACKS.remove(callback);
    }

    public static double getFaults6v() {
        return getState().faults6v;
    }

    public static void setFaults6v(double faults6v) {
        setFaults6v(faults6v, true);
    }

    public static final Consumer<DoubleCallback> CALL_FAULTS6V_CALLBACK = callback -> callback.callback("0", getState().faults6v);
    private static void setFaults6v(double faults6v, boolean notifyRobot) {
        if(faults6v != getState().faults6v) {
            getState().faults6v = faults6v;
            getState().FAULTS6V_CALLBACKS.forEach(CALL_FAULTS6V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">6v_faults", faults6v));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerVoltage5vCallback(DoubleCallback callback, boolean initialNotify) {
        getState().VOLTAGE5V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().voltage5v);
        }
        return new ScopedObject<>(callback, CANCEL_VOLTAGE5V_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_VOLTAGE5V_CALLBACK = RoboRIOSim::cancelVoltage5vCallback;
    public static void cancelVoltage5vCallback(DoubleCallback callback) {
        getState().VOLTAGE5V_CALLBACKS.remove(callback);
    }

    public static double getVoltage5v() {
        return getState().voltage5v;
    }

    public static void setVoltage5v(double voltage5v) {
        setVoltage5v(voltage5v, true);
    }

    public static final Consumer<DoubleCallback> CALL_VOLTAGE5V_CALLBACK = callback -> callback.callback("0", getState().voltage5v);
    private static void setVoltage5v(double voltage5v, boolean notifyRobot) {
        if(voltage5v != getState().voltage5v) {
            getState().voltage5v = voltage5v;
            getState().VOLTAGE5V_CALLBACKS.forEach(CALL_VOLTAGE5V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">5v_voltage", voltage5v));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerCurrent5vCallback(DoubleCallback callback, boolean initialNotify) {
        getState().CURRENT5V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().current5v);
        }
        return new ScopedObject<>(callback, CANCEL_CURRENT5V_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_CURRENT5V_CALLBACK = RoboRIOSim::cancelCurrent5vCallback;
    public static void cancelCurrent5vCallback(DoubleCallback callback) {
        getState().CURRENT5V_CALLBACKS.remove(callback);
    }

    public static double getCurrent5v() {
        return getState().current5v;
    }

    public static void setCurrent5v(double current5v) {
        setCurrent5v(current5v, true);
    }

    public static final Consumer<DoubleCallback> CALL_CURRENT5V_CALLBACK = callback -> callback.callback("0", getState().current5v);
    private static void setCurrent5v(double current5v, boolean notifyRobot) {
        if(current5v != getState().current5v) {
            getState().current5v = current5v;
            getState().CURRENT5V_CALLBACKS.forEach(CALL_CURRENT5V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">5v_current", current5v));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerActive5vCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ACTIVE5V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().active5v);
        }
        return new ScopedObject<>(callback, CANCEL_ACTIVE5V_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_ACTIVE5V_CALLBACK = RoboRIOSim::cancelActive5vCallback;
    public static void cancelActive5vCallback(BooleanCallback callback) {
        getState().ACTIVE5V_CALLBACKS.remove(callback);
    }

    public static boolean getActive5v() {
        return getState().active5v;
    }

    public static void setActive5v(boolean active5v) {
        setActive5v(active5v, true);
    }

    public static final Consumer<BooleanCallback> CALL_ACTIVE5V_CALLBACK = callback -> callback.callback("0", getState().active5v);
    private static void setActive5v(boolean active5v, boolean notifyRobot) {
        if(active5v != getState().active5v) {
            getState().active5v = active5v;
            getState().ACTIVE5V_CALLBACKS.forEach(CALL_ACTIVE5V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">5v_active", active5v));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerFaults5vCallback(DoubleCallback callback, boolean initialNotify) {
        getState().FAULTS5V_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().faults5v);
        }
        return new ScopedObject<>(callback, CANCEL_FAULTS5V_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_FAULTS5V_CALLBACK = RoboRIOSim::cancelFaults5vCallback;
    public static void cancelFaults5vCallback(DoubleCallback callback) {
        getState().FAULTS5V_CALLBACKS.remove(callback);
    }

    public static double getFaults5v() {
        return getState().faults5v;
    }

    public static void setFaults5v(double faults5v) {
        setFaults5v(faults5v, true);
    }

    public static final Consumer<DoubleCallback> CALL_FAULTS5V_CALLBACK = callback -> callback.callback("0", getState().faults5v);
    private static void setFaults5v(double faults5v, boolean notifyRobot) {
        if(faults5v != getState().faults5v) {
            getState().faults5v = faults5v;
            getState().FAULTS5V_CALLBACKS.forEach(CALL_FAULTS5V_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">5v_faults", faults5v));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerVoltage3v3Callback(DoubleCallback callback, boolean initialNotify) {
        getState().VOLTAGE3V3_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().voltage3v3);
        }
        return new ScopedObject<>(callback, CANCEL_VOLTAGE3V3_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_VOLTAGE3V3_CALLBACK = RoboRIOSim::cancelVoltage3v3Callback;
    public static void cancelVoltage3v3Callback(DoubleCallback callback) {
        getState().VOLTAGE3V3_CALLBACKS.remove(callback);
    }

    public static double getVoltage3v3() {
        return getState().voltage3v3;
    }

    public static void setVoltage3v3(double voltage3v3) {
        setVoltage3v3(voltage3v3, true);
    }

    public static final Consumer<DoubleCallback> CALL_VOLTAGE3V3_CALLBACK = callback -> callback.callback("0", getState().voltage3v3);
    private static void setVoltage3v3(double voltage3v3, boolean notifyRobot) {
        if(voltage3v3 != getState().voltage3v3) {
            getState().voltage3v3 = voltage3v3;
            getState().VOLTAGE3V3_CALLBACKS.forEach(CALL_VOLTAGE3V3_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">3v3_voltage", voltage3v3));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerCurrent3v3Callback(DoubleCallback callback, boolean initialNotify) {
        getState().CURRENT3V3_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().current3v3);
        }
        return new ScopedObject<>(callback, CANCEL_CURRENT3V3_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_CURRENT3V3_CALLBACK = RoboRIOSim::cancelCurrent3v3Callback;
    public static void cancelCurrent3v3Callback(DoubleCallback callback) {
        getState().CURRENT3V3_CALLBACKS.remove(callback);
    }

    public static double getCurrent3v3() {
        return getState().current3v3;
    }

    public static void setCurrent3v3(double current3v3) {
        setCurrent3v3(current3v3, true);
    }

    public static final Consumer<DoubleCallback> CALL_CURRENT3V3_CALLBACK = callback -> callback.callback("0", getState().current3v3);
    private static void setCurrent3v3(double current3v3, boolean notifyRobot) {
        if(current3v3 != getState().current3v3) {
            getState().current3v3 = current3v3;
            getState().CURRENT3V3_CALLBACKS.forEach(CALL_CURRENT3V3_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">3v3_current", current3v3));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerActive3v3Callback(BooleanCallback callback, boolean initialNotify) {
        getState().ACTIVE3V3_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().active3v3);
        }
        return new ScopedObject<>(callback, CANCEL_ACTIVE3V3_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_ACTIVE3V3_CALLBACK = RoboRIOSim::cancelActive3v3Callback;
    public static void cancelActive3v3Callback(BooleanCallback callback) {
        getState().ACTIVE3V3_CALLBACKS.remove(callback);
    }

    public static boolean getActive3v3() {
        return getState().active3v3;
    }

    public static void setActive3v3(boolean active3v3) {
        setActive3v3(active3v3, true);
    }

    public static final Consumer<BooleanCallback> CALL_ACTIVE3V3_CALLBACK = callback -> callback.callback("0", getState().active3v3);
    private static void setActive3v3(boolean active3v3, boolean notifyRobot) {
        if(active3v3 != getState().active3v3) {
            getState().active3v3 = active3v3;
            getState().ACTIVE3V3_CALLBACKS.forEach(CALL_ACTIVE3V3_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">3v3_active", active3v3));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerFaults3v3Callback(DoubleCallback callback, boolean initialNotify) {
        getState().FAULTS3V3_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().faults3v3);
        }
        return new ScopedObject<>(callback, CANCEL_FAULTS3V3_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_FAULTS3V3_CALLBACK = RoboRIOSim::cancelFaults3v3Callback;
    public static void cancelFaults3v3Callback(DoubleCallback callback) {
        getState().FAULTS3V3_CALLBACKS.remove(callback);
    }

    public static double getFaults3v3() {
        return getState().faults3v3;
    }

    public static void setFaults3v3(double faults3v3) {
        setFaults3v3(faults3v3, true);
    }

    public static final Consumer<DoubleCallback> CALL_FAULTS3V3_CALLBACK = callback -> callback.callback("0", getState().faults3v3);
    private static void setFaults3v3(double faults3v3, boolean notifyRobot) {
        if(faults3v3 != getState().faults3v3) {
            getState().faults3v3 = faults3v3;
            getState().FAULTS3V3_CALLBACKS.forEach(CALL_FAULTS3V3_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "RoboRIO", new WSValue(">3v3_faults", faults3v3));
        }
    }
    

    public static void processMessage(String device, List<WSValue> data) {
        
        for(WSValue value: data) {
            processValue(value);
        }
        
    }

    
    
    
    private static final BiConsumer<Boolean, Boolean> SET_FPGABUTTON = RoboRIOSim::setFpgaButton;
    
    
    private static final BiConsumer<Double, Boolean> SET_VINVOLTAGE = RoboRIOSim::setVinVoltage;
    
    
    private static final BiConsumer<Double, Boolean> SET_VINCURRENT = RoboRIOSim::setVinCurrent;
    
    
    private static final BiConsumer<Double, Boolean> SET_VOLTAGE6V = RoboRIOSim::setVoltage6v;
    
    
    private static final BiConsumer<Double, Boolean> SET_CURRENT6V = RoboRIOSim::setCurrent6v;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_ACTIVE6V = RoboRIOSim::setActive6v;
    
    
    private static final BiConsumer<Double, Boolean> SET_FAULTS6V = RoboRIOSim::setFaults6v;
    
    
    private static final BiConsumer<Double, Boolean> SET_VOLTAGE5V = RoboRIOSim::setVoltage5v;
    
    
    private static final BiConsumer<Double, Boolean> SET_CURRENT5V = RoboRIOSim::setCurrent5v;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_ACTIVE5V = RoboRIOSim::setActive5v;
    
    
    private static final BiConsumer<Double, Boolean> SET_FAULTS5V = RoboRIOSim::setFaults5v;
    
    
    private static final BiConsumer<Double, Boolean> SET_VOLTAGE3V3 = RoboRIOSim::setVoltage3v3;
    
    
    private static final BiConsumer<Double, Boolean> SET_CURRENT3V3 = RoboRIOSim::setCurrent3v3;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_ACTIVE3V3 = RoboRIOSim::setActive3v3;
    
    
    private static final BiConsumer<Double, Boolean> SET_FAULTS3V3 = RoboRIOSim::setFaults3v3;
    
    private static void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                
                
                
                case ">fpga_button": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_FPGABUTTON);
                    
                    break;
                }
                
                
                case ">vin_voltage": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VINVOLTAGE);
                    
                    break;
                }
                
                
                case ">vin_current": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VINCURRENT);
                    
                    break;
                }
                
                
                case ">6v_voltage": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VOLTAGE6V);
                    
                    break;
                }
                
                
                case ">6v_current": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_CURRENT6V);
                    
                    break;
                }
                
                
                case ">6v_active": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ACTIVE6V);
                    
                    break;
                }
                
                
                case ">6v_faults": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_FAULTS6V);
                    
                    break;
                }
                
                
                case ">5v_voltage": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VOLTAGE5V);
                    
                    break;
                }
                
                
                case ">5v_current": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_CURRENT5V);
                    
                    break;
                }
                
                
                case ">5v_active": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ACTIVE5V);
                    
                    break;
                }
                
                
                case ">5v_faults": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_FAULTS5V);
                    
                    break;
                }
                
                
                case ">3v3_voltage": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_VOLTAGE3V3);
                    
                    break;
                }
                
                
                case ">3v3_current": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_CURRENT3V3);
                    
                    break;
                }
                
                
                case ">3v3_active": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ACTIVE3V3);
                    
                    break;
                }
                
                
                case ">3v3_faults": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_FAULTS3V3);
                    
                    break;
                }
                
            }
        }
    }

    public static class State {
        
        
        
        public boolean fpgabutton = false;
        
        
        public double vinvoltage = 0;
        
        
        public double vincurrent = 0;
        
        
        public double voltage6v = 0;
        
        
        public double current6v = 0;
        
        
        public boolean active6v = false;
        
        
        public double faults6v = 0;
        
        
        public double voltage5v = 0;
        
        
        public double current5v = 0;
        
        
        public boolean active5v = false;
        
        
        public double faults5v = 0;
        
        
        public double voltage3v3 = 0;
        
        
        public double current3v3 = 0;
        
        
        public boolean active3v3 = false;
        
        
        public double faults3v3 = 0;
        
        
        
        
        public final CopyOnWriteArrayList<BooleanCallback> FPGABUTTON_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> VINVOLTAGE_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> VINCURRENT_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> VOLTAGE6V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> CURRENT6V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> ACTIVE6V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> FAULTS6V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> VOLTAGE5V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> CURRENT5V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> ACTIVE5V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> FAULTS5V_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> VOLTAGE3V3_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> CURRENT3V3_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> ACTIVE3V3_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> FAULTS3V3_CALLBACKS = new CopyOnWriteArrayList<>();
        
    }

}