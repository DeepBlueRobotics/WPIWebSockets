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

public class RelaySim extends StateDevice<RelaySim.State> {

    private static final HashMap<String, RelaySim.State> STATE_MAP = new HashMap<>();

    public RelaySim(String id) {
        super(id, STATE_MAP);
    }


    public ScopedObject<BooleanCallback> registerInitFwdCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITFWD_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().initfwd);
        }
        return new ScopedObject<>(callback, CANCEL_INITFWD_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_INITFWD_CALLBACK = this::cancelInitFwdCallback;
    public void cancelInitFwdCallback(BooleanCallback callback) {
        getState().INITFWD_CALLBACKS.remove(callback);
    }

    public boolean getInitFwd() {
        return getState().initfwd;
    }

    public void setInitFwd(boolean initfwd) {
        setInitFwd(initfwd, true);
    }

    public final Consumer<BooleanCallback> CALL_INITFWD_CALLBACK = callback -> callback.callback(id, getState().initfwd);
    private void setInitFwd(boolean initfwd, boolean notifyRobot) {
        if(initfwd != getState().initfwd) {
            getState().initfwd = initfwd;
            getState().INITFWD_CALLBACKS.forEach(CALL_INITFWD_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Relay", new WSValue("<init_fwd", initfwd));
        }
    }
    public ScopedObject<BooleanCallback> registerInitRevCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITREV_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().initrev);
        }
        return new ScopedObject<>(callback, CANCEL_INITREV_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_INITREV_CALLBACK = this::cancelInitRevCallback;
    public void cancelInitRevCallback(BooleanCallback callback) {
        getState().INITREV_CALLBACKS.remove(callback);
    }

    public boolean getInitRev() {
        return getState().initrev;
    }

    public void setInitRev(boolean initrev) {
        setInitRev(initrev, true);
    }

    public final Consumer<BooleanCallback> CALL_INITREV_CALLBACK = callback -> callback.callback(id, getState().initrev);
    private void setInitRev(boolean initrev, boolean notifyRobot) {
        if(initrev != getState().initrev) {
            getState().initrev = initrev;
            getState().INITREV_CALLBACKS.forEach(CALL_INITREV_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Relay", new WSValue("<init_rev", initrev));
        }
    }
    public ScopedObject<BooleanCallback> registerFwdCallback(BooleanCallback callback, boolean initialNotify) {
        getState().FWD_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().fwd);
        }
        return new ScopedObject<>(callback, CANCEL_FWD_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_FWD_CALLBACK = this::cancelFwdCallback;
    public void cancelFwdCallback(BooleanCallback callback) {
        getState().FWD_CALLBACKS.remove(callback);
    }

    public boolean getFwd() {
        return getState().fwd;
    }

    public void setFwd(boolean fwd) {
        setFwd(fwd, true);
    }

    public final Consumer<BooleanCallback> CALL_FWD_CALLBACK = callback -> callback.callback(id, getState().fwd);
    private void setFwd(boolean fwd, boolean notifyRobot) {
        if(fwd != getState().fwd) {
            getState().fwd = fwd;
            getState().FWD_CALLBACKS.forEach(CALL_FWD_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Relay", new WSValue("<fwd", fwd));
        }
    }
    public ScopedObject<BooleanCallback> registerRevCallback(BooleanCallback callback, boolean initialNotify) {
        getState().REV_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback(id, getState().rev);
        }
        return new ScopedObject<>(callback, CANCEL_REV_CALLBACK);
    }

    public final Consumer<BooleanCallback> CANCEL_REV_CALLBACK = this::cancelRevCallback;
    public void cancelRevCallback(BooleanCallback callback) {
        getState().REV_CALLBACKS.remove(callback);
    }

    public boolean getRev() {
        return getState().rev;
    }

    public void setRev(boolean rev) {
        setRev(rev, true);
    }

    public final Consumer<BooleanCallback> CALL_REV_CALLBACK = callback -> callback.callback(id, getState().rev);
    private void setRev(boolean rev, boolean notifyRobot) {
        if(rev != getState().rev) {
            getState().rev = rev;
            getState().REV_CALLBACKS.forEach(CALL_REV_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage(id, "Relay", new WSValue("<rev", rev));
        }
    }

    public static void processMessage(String device, List<WSValue> data) {
        RelaySim simDevice = new RelaySim(device);
        for(WSValue value: data) {
            simDevice.processValue(value);
        }
    }

    private final BiConsumer<Boolean, Boolean> SET_INITFWD = this::setInitFwd;
    private final BiConsumer<Boolean, Boolean> SET_INITREV = this::setInitRev;
    private final BiConsumer<Boolean, Boolean> SET_FWD = this::setFwd;
    private final BiConsumer<Boolean, Boolean> SET_REV = this::setRev;
    private void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                case "<init_fwd": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITFWD);
                    break;
                }
                case "<init_rev": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITREV);
                    break;
                }
                case "<fwd": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_FWD);
                    break;
                }
                case "<rev": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_REV);
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
        public boolean initfwd = false;
        public boolean initrev = false;
        public boolean fwd = false;
        public boolean rev = false;
        public final CopyOnWriteArrayList<BooleanCallback> INITFWD_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<BooleanCallback> INITREV_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<BooleanCallback> FWD_CALLBACKS = new CopyOnWriteArrayList<>();
        public final CopyOnWriteArrayList<BooleanCallback> REV_CALLBACKS = new CopyOnWriteArrayList<>();
    }

}
