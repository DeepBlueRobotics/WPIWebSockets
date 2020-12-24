








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


public class DriverStationSim {


    
    private static final DriverStationSim.State STATE = new State();
    

    

    
    protected static DriverStationSim.State getState() {
        return STATE;
    }
    

    
    
    public static ScopedObject<BooleanCallback> registerEnabledCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ENABLED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().enabled);
        }
        return new ScopedObject<>(callback, CANCEL_ENABLED_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_ENABLED_CALLBACK = DriverStationSim::cancelEnabledCallback;
    public static void cancelEnabledCallback(BooleanCallback callback) {
        getState().ENABLED_CALLBACKS.remove(callback);
    }

    public static boolean getEnabled() {
        return getState().enabled;
    }

    public static void setEnabled(boolean enabled) {
        setEnabled(enabled, true);
    }

    public static final Consumer<BooleanCallback> CALL_ENABLED_CALLBACK = callback -> callback.callback("0", getState().enabled);
    private static void setEnabled(boolean enabled, boolean notifyRobot) {
        if(enabled != getState().enabled) {
            getState().enabled = enabled;
            getState().ENABLED_CALLBACKS.forEach(CALL_ENABLED_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">enabled", enabled));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerAutonomousCallback(BooleanCallback callback, boolean initialNotify) {
        getState().AUTONOMOUS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().autonomous);
        }
        return new ScopedObject<>(callback, CANCEL_AUTONOMOUS_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_AUTONOMOUS_CALLBACK = DriverStationSim::cancelAutonomousCallback;
    public static void cancelAutonomousCallback(BooleanCallback callback) {
        getState().AUTONOMOUS_CALLBACKS.remove(callback);
    }

    public static boolean getAutonomous() {
        return getState().autonomous;
    }

    public static void setAutonomous(boolean autonomous) {
        setAutonomous(autonomous, true);
    }

    public static final Consumer<BooleanCallback> CALL_AUTONOMOUS_CALLBACK = callback -> callback.callback("0", getState().autonomous);
    private static void setAutonomous(boolean autonomous, boolean notifyRobot) {
        if(autonomous != getState().autonomous) {
            getState().autonomous = autonomous;
            getState().AUTONOMOUS_CALLBACKS.forEach(CALL_AUTONOMOUS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">autonomous", autonomous));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerTestCallback(BooleanCallback callback, boolean initialNotify) {
        getState().TEST_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().test);
        }
        return new ScopedObject<>(callback, CANCEL_TEST_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_TEST_CALLBACK = DriverStationSim::cancelTestCallback;
    public static void cancelTestCallback(BooleanCallback callback) {
        getState().TEST_CALLBACKS.remove(callback);
    }

    public static boolean getTest() {
        return getState().test;
    }

    public static void setTest(boolean test) {
        setTest(test, true);
    }

    public static final Consumer<BooleanCallback> CALL_TEST_CALLBACK = callback -> callback.callback("0", getState().test);
    private static void setTest(boolean test, boolean notifyRobot) {
        if(test != getState().test) {
            getState().test = test;
            getState().TEST_CALLBACKS.forEach(CALL_TEST_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">test", test));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerEstopCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ESTOP_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().estop);
        }
        return new ScopedObject<>(callback, CANCEL_ESTOP_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_ESTOP_CALLBACK = DriverStationSim::cancelEstopCallback;
    public static void cancelEstopCallback(BooleanCallback callback) {
        getState().ESTOP_CALLBACKS.remove(callback);
    }

    public static boolean getEstop() {
        return getState().estop;
    }

    public static void setEstop(boolean estop) {
        setEstop(estop, true);
    }

    public static final Consumer<BooleanCallback> CALL_ESTOP_CALLBACK = callback -> callback.callback("0", getState().estop);
    private static void setEstop(boolean estop, boolean notifyRobot) {
        if(estop != getState().estop) {
            getState().estop = estop;
            getState().ESTOP_CALLBACKS.forEach(CALL_ESTOP_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">estop", estop));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerFmsCallback(BooleanCallback callback, boolean initialNotify) {
        getState().FMS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().fms);
        }
        return new ScopedObject<>(callback, CANCEL_FMS_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_FMS_CALLBACK = DriverStationSim::cancelFmsCallback;
    public static void cancelFmsCallback(BooleanCallback callback) {
        getState().FMS_CALLBACKS.remove(callback);
    }

    public static boolean getFms() {
        return getState().fms;
    }

    public static void setFms(boolean fms) {
        setFms(fms, true);
    }

    public static final Consumer<BooleanCallback> CALL_FMS_CALLBACK = callback -> callback.callback("0", getState().fms);
    private static void setFms(boolean fms, boolean notifyRobot) {
        if(fms != getState().fms) {
            getState().fms = fms;
            getState().FMS_CALLBACKS.forEach(CALL_FMS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">fms", fms));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerDsCallback(BooleanCallback callback, boolean initialNotify) {
        getState().DS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().ds);
        }
        return new ScopedObject<>(callback, CANCEL_DS_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_DS_CALLBACK = DriverStationSim::cancelDsCallback;
    public static void cancelDsCallback(BooleanCallback callback) {
        getState().DS_CALLBACKS.remove(callback);
    }

    public static boolean getDs() {
        return getState().ds;
    }

    public static void setDs(boolean ds) {
        setDs(ds, true);
    }

    public static final Consumer<BooleanCallback> CALL_DS_CALLBACK = callback -> callback.callback("0", getState().ds);
    private static void setDs(boolean ds, boolean notifyRobot) {
        if(ds != getState().ds) {
            getState().ds = ds;
            getState().DS_CALLBACKS.forEach(CALL_DS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">ds", ds));
        }
    }
    
    
    public static ScopedObject<DoubleCallback> registerMatchTimeCallback(DoubleCallback callback, boolean initialNotify) {
        getState().MATCHTIME_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().matchtime);
        }
        return new ScopedObject<>(callback, CANCEL_MATCHTIME_CALLBACK);
    }

    public static final Consumer<DoubleCallback> CANCEL_MATCHTIME_CALLBACK = DriverStationSim::cancelMatchTimeCallback;
    public static void cancelMatchTimeCallback(DoubleCallback callback) {
        getState().MATCHTIME_CALLBACKS.remove(callback);
    }

    public static double getMatchTime() {
        return getState().matchtime;
    }

    public static void setMatchTime(double matchtime) {
        setMatchTime(matchtime, true);
    }

    public static final Consumer<DoubleCallback> CALL_MATCHTIME_CALLBACK = callback -> callback.callback("0", getState().matchtime);
    private static void setMatchTime(double matchtime, boolean notifyRobot) {
        if(matchtime != getState().matchtime) {
            getState().matchtime = matchtime;
            getState().MATCHTIME_CALLBACKS.forEach(CALL_MATCHTIME_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue("<match_time", matchtime));
        }
    }
    
    
    public static ScopedObject<StringCallback> registerStationCallback(StringCallback callback, boolean initialNotify) {
        getState().STATION_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().station);
        }
        return new ScopedObject<>(callback, CANCEL_STATION_CALLBACK);
    }

    public static final Consumer<StringCallback> CANCEL_STATION_CALLBACK = DriverStationSim::cancelStationCallback;
    public static void cancelStationCallback(StringCallback callback) {
        getState().STATION_CALLBACKS.remove(callback);
    }

    public static String getStation() {
        return getState().station;
    }

    public static void setStation(String station) {
        setStation(station, true);
    }

    public static final Consumer<StringCallback> CALL_STATION_CALLBACK = callback -> callback.callback("0", getState().station);
    private static void setStation(String station, boolean notifyRobot) {
        if(station != getState().station) {
            getState().station = station;
            getState().STATION_CALLBACKS.forEach(CALL_STATION_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">station", station));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerNewDataCallback(BooleanCallback callback, boolean initialNotify) {
        getState().NEWDATA_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().newdata);
        }
        return new ScopedObject<>(callback, CANCEL_NEWDATA_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_NEWDATA_CALLBACK = DriverStationSim::cancelNewDataCallback;
    public static void cancelNewDataCallback(BooleanCallback callback) {
        getState().NEWDATA_CALLBACKS.remove(callback);
    }

    public static boolean getNewData() {
        return getState().newdata;
    }

    public static void setNewData(boolean newdata) {
        setNewData(newdata, true);
    }

    public static final Consumer<BooleanCallback> CALL_NEWDATA_CALLBACK = callback -> callback.callback("0", getState().newdata);
    private static void setNewData(boolean newdata, boolean notifyRobot) {
        if(newdata != getState().newdata) {
            getState().newdata = newdata;
            getState().NEWDATA_CALLBACKS.forEach(CALL_NEWDATA_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "DriverStation", new WSValue(">new_data", newdata));
        }
    }
    

    public static void processMessage(String device, List<WSValue> data) {
        
        for(WSValue value: data) {
            processValue(value);
        }
        
    }

    
    
    
    private static final BiConsumer<Boolean, Boolean> SET_ENABLED = DriverStationSim::setEnabled;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_AUTONOMOUS = DriverStationSim::setAutonomous;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_TEST = DriverStationSim::setTest;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_ESTOP = DriverStationSim::setEstop;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_FMS = DriverStationSim::setFms;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_DS = DriverStationSim::setDs;
    
    
    private static final BiConsumer<Double, Boolean> SET_MATCHTIME = DriverStationSim::setMatchTime;
    
    
    private static final BiConsumer<String, Boolean> SET_STATION = DriverStationSim::setStation;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_NEWDATA = DriverStationSim::setNewData;
    
    private static void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                
                
                
                case ">enabled": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ENABLED);
                    
                    break;
                }
                
                
                case ">autonomous": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_AUTONOMOUS);
                    
                    break;
                }
                
                
                case ">test": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_TEST);
                    
                    break;
                }
                
                
                case ">estop": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ESTOP);
                    
                    break;
                }
                
                
                case ">fms": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_FMS);
                    
                    break;
                }
                
                
                case ">ds": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_DS);
                    
                    break;
                }
                
                
                case "<match_time": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_MATCHTIME);
                    
                    break;
                }
                
                
                case ">station": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), String.class, SET_STATION);
                    
                    break;
                }
                
                
                case ">new_data": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_NEWDATA);
                    
                    break;
                }
                
            }
        }
    }

    public static class State {
        
        
        
        public boolean enabled = false;
        
        
        public boolean autonomous = false;
        
        
        public boolean test = false;
        
        
        public boolean estop = false;
        
        
        public boolean fms = false;
        
        
        public boolean ds = false;
        
        
        public double matchtime = 0;
        
        
        public String station = "";
        
        
        public boolean newdata = false;
        
        
        
        
        public final CopyOnWriteArrayList<BooleanCallback> ENABLED_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> AUTONOMOUS_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> TEST_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> ESTOP_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> FMS_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> DS_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<DoubleCallback> MATCHTIME_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<StringCallback> STATION_CALLBACKS = new CopyOnWriteArrayList<>();
        
        
        public final CopyOnWriteArrayList<BooleanCallback> NEWDATA_CALLBACKS = new CopyOnWriteArrayList<>();
        
    }

}