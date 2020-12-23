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

public class DriverStationSim extends StateDevice<DriverStationSim.State> {

    private static final HashMap<String, DriverStationSim.State> STATE_MAP = new HashMap<>();

    public DriverStationSim(String id) {
        super(id, STATE_MAP);
    }


    public ScopedObject<BooleanCallback> registerEnabledCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ENABLED_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().enabled);
        }
        return new ScopedObject<>(callback, CANCEL_ENABLED_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_ENABLED_CALLBACK = this::cancelEnabledCallback;
    public void cancelEnabledCallback(BooleanCallback callback) {
        getState().ENABLED_CALLBACKS.remove(callback);
    }

    public boolean getEnabled() {
        return getState().enabled;
    }

    public void setEnabled(boolean enabled) {
        setEnabled(enabled, true);
    }

    public final Consumer<BooleanCallback> CALL_ENABLED_CALLBACK = callback -> callback.callback(id, getState().enabled);
    private void setEnabled(boolean enabled, boolean notifyRobot) {
        if(enabled != getState().enabled) {
            getState().enabled = enabled;
            getState().ENABLED_CALLBACKS.forEach(CALL_ENABLED_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">enabled", enabled));
        }
    }
    public ScopedObject<BooleanCallback> registerAutonomousCallback(BooleanCallback callback, boolean initialNotify) {
        getState().AUTONOMOUS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().autonomous);
        }
        return new ScopedObject<>(callback, CANCEL_AUTONOMOUS_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_AUTONOMOUS_CALLBACK = this::cancelAutonomousCallback;
    public void cancelAutonomousCallback(BooleanCallback callback) {
        getState().AUTONOMOUS_CALLBACKS.remove(callback);
    }

    public boolean getAutonomous() {
        return getState().autonomous;
    }

    public void setAutonomous(boolean autonomous) {
        setAutonomous(autonomous, true);
    }

    public final Consumer<BooleanCallback> CALL_AUTONOMOUS_CALLBACK = callback -> callback.callback(id, getState().autonomous);
    private void setAutonomous(boolean autonomous, boolean notifyRobot) {
        if(autonomous != getState().autonomous) {
            getState().autonomous = autonomous;
            getState().AUTONOMOUS_CALLBACKS.forEach(CALL_AUTONOMOUS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">autonomous", autonomous));
        }
    }
    public ScopedObject<BooleanCallback> registerTestCallback(BooleanCallback callback, boolean initialNotify) {
        getState().TEST_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().test);
        }
        return new ScopedObject<>(callback, CANCEL_TEST_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_TEST_CALLBACK = this::cancelTestCallback;
    public void cancelTestCallback(BooleanCallback callback) {
        getState().TEST_CALLBACKS.remove(callback);
    }

    public boolean getTest() {
        return getState().test;
    }

    public void setTest(boolean test) {
        setTest(test, true);
    }

    public final Consumer<BooleanCallback> CALL_TEST_CALLBACK = callback -> callback.callback(id, getState().test);
    private void setTest(boolean test, boolean notifyRobot) {
        if(test != getState().test) {
            getState().test = test;
            getState().TEST_CALLBACKS.forEach(CALL_TEST_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">test", test));
        }
    }
    public ScopedObject<BooleanCallback> registerEstopCallback(BooleanCallback callback, boolean initialNotify) {
        getState().ESTOP_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().estop);
        }
        return new ScopedObject<>(callback, CANCEL_ESTOP_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_ESTOP_CALLBACK = this::cancelEstopCallback;
    public void cancelEstopCallback(BooleanCallback callback) {
        getState().ESTOP_CALLBACKS.remove(callback);
    }

    public boolean getEstop() {
        return getState().estop;
    }

    public void setEstop(boolean estop) {
        setEstop(estop, true);
    }

    public final Consumer<BooleanCallback> CALL_ESTOP_CALLBACK = callback -> callback.callback(id, getState().estop);
    private void setEstop(boolean estop, boolean notifyRobot) {
        if(estop != getState().estop) {
            getState().estop = estop;
            getState().ESTOP_CALLBACKS.forEach(CALL_ESTOP_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">estop", estop));
        }
    }
    public ScopedObject<BooleanCallback> registerFmsCallback(BooleanCallback callback, boolean initialNotify) {
        getState().FMS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().fms);
        }
        return new ScopedObject<>(callback, CANCEL_FMS_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_FMS_CALLBACK = this::cancelFmsCallback;
    public void cancelFmsCallback(BooleanCallback callback) {
        getState().FMS_CALLBACKS.remove(callback);
    }

    public boolean getFms() {
        return getState().fms;
    }

    public void setFms(boolean fms) {
        setFms(fms, true);
    }

    public final Consumer<BooleanCallback> CALL_FMS_CALLBACK = callback -> callback.callback(id, getState().fms);
    private void setFms(boolean fms, boolean notifyRobot) {
        if(fms != getState().fms) {
            getState().fms = fms;
            getState().FMS_CALLBACKS.forEach(CALL_FMS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">fms", fms));
        }
    }
    public ScopedObject<BooleanCallback> registerDsCallback(BooleanCallback callback, boolean initialNotify) {
        getState().DS_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().ds);
        }
        return new ScopedObject<>(callback, CANCEL_DS_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_DS_CALLBACK = this::cancelDsCallback;
    public void cancelDsCallback(BooleanCallback callback) {
        getState().DS_CALLBACKS.remove(callback);
    }

    public boolean getDs() {
        return getState().ds;
    }

    public void setDs(boolean ds) {
        setDs(ds, true);
    }

    public final Consumer<BooleanCallback> CALL_DS_CALLBACK = callback -> callback.callback(id, getState().ds);
    private void setDs(boolean ds, boolean notifyRobot) {
        if(ds != getState().ds) {
            getState().ds = ds;
            getState().DS_CALLBACKS.forEach(CALL_DS_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">ds", ds));
        }
    }
    public ScopedObject<DoubleCallback> registerMatchTimeCallback(DoubleCallback callback, boolean initialNotify) {
        getState().MATCHTIME_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().matchtime);
        }
        return new ScopedObject<>(callback, CANCEL_MATCHTIME_CALLBACK);
    }

    public final Consumer<DoubleCallback> CANCEL_MATCHTIME_CALLBACK = this::cancelMatchTimeCallback;
    public void cancelMatchTimeCallback(DoubleCallback callback) {
        getState().MATCHTIME_CALLBACKS.remove(callback);
    }

    public double getMatchTime() {
        return getState().matchtime;
    }

    public void setMatchTime(double matchtime) {
        setMatchTime(matchtime, true);
    }

    public final Consumer<DoubleCallback> CALL_MATCHTIME_CALLBACK = callback -> callback.callback(id, getState().matchtime);
    private void setMatchTime(double matchtime, boolean notifyRobot) {
        if(matchtime != getState().matchtime) {
            getState().matchtime = matchtime;
            getState().MATCHTIME_CALLBACKS.forEach(CALL_MATCHTIME_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue("<match_time", matchtime));
        }
    }
    public ScopedObject<StringCallback> registerStationCallback(StringCallback callback, boolean initialNotify) {
        getState().STATION_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().station);
        }
        return new ScopedObject<>(callback, CANCEL_STATION_CALLBACK);
    }

    public final Consumer<StringCallback> CANCEL_STATION_CALLBACK = this::cancelStationCallback;
    public void cancelStationCallback(StringCallback callback) {
        getState().STATION_CALLBACKS.remove(callback);
    }

    public String getStation() {
        return getState().station;
    }

    public void setStation(String station) {
        setStation(station, true);
    }

    public final Consumer<StringCallback> CALL_STATION_CALLBACK = callback -> callback.callback(id, getState().station);
    private void setStation(String station, boolean notifyRobot) {
        if(station != getState().station) {
            getState().station = station;
            getState().STATION_CALLBACKS.forEach(CALL_STATION_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">station", station));
        }
    }
    public ScopedObject<BooleanCallback> registerNewDataCallback(BooleanCallback callback, boolean initialNotify) {
        getState().NEWDATA_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().newdata);
        }
        return new ScopedObject<>(callback, CANCEL_NEWDATA_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_NEWDATA_CALLBACK = this::cancelNewDataCallback;
    public void cancelNewDataCallback(BooleanCallback callback) {
        getState().NEWDATA_CALLBACKS.remove(callback);
    }

    public boolean getNewData() {
        return getState().newdata;
    }

    public void setNewData(boolean newdata) {
        setNewData(newdata, true);
    }

    public final Consumer<BooleanCallback> CALL_NEWDATA_CALLBACK = callback -> callback.callback(id, getState().newdata);
    private void setNewData(boolean newdata, boolean notifyRobot) {
        if(newdata != getState().newdata) {
            getState().newdata = newdata;
            getState().NEWDATA_CALLBACKS.forEach(CALL_NEWDATA_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "DriverStation", new WSValue(">new_data", newdata));
        }
    }

    public static void processMessage(String device, List<WSValue> data) {
        DriverStationSim simDevice = new DriverStationSim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_ENABLED = this::setEnabled;
    private final BiConsumer<Boolean, Boolean> SET_AUTONOMOUS = this::setAutonomous;
    private final BiConsumer<Boolean, Boolean> SET_TEST = this::setTest;
    private final BiConsumer<Boolean, Boolean> SET_ESTOP = this::setEstop;
    private final BiConsumer<Boolean, Boolean> SET_FMS = this::setFms;
    private final BiConsumer<Boolean, Boolean> SET_DS = this::setDs;
    private final BiConsumer<Double, Boolean> SET_MATCHTIME = this::setMatchTime;
    private final BiConsumer<String, Boolean> SET_STATION = this::setStation;
    private final BiConsumer<Boolean, Boolean> SET_NEWDATA = this::setNewData;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case ">enabled": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ENABLED);
                    break;
                }
                case ">autonomous": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_AUTONOMOUS);
                    break;
                }
                case ">test": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_TEST);
                    break;
                }
                case ">estop": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_ESTOP);
                    break;
                }
                case ">fms": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_FMS);
                    break;
                }
                case ">ds": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_DS);
                    break;
                }
                case "<match_time": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Double.class, SET_MATCHTIME);
                    break;
                }
                case ">station": {
                    filterMessageAndIgnoreRobotState(value.getValue(), String.class, SET_STATION);
                    break;
                }
                case ">new_data": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_NEWDATA);
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
