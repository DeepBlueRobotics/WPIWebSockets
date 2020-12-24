








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


public class RelaySim {


    
    private static final RelaySim.State STATE = new State();
    

    

    
    protected static RelaySim.State getState() {
        return STATE;
    }
    

    
    
    public static ScopedObject<BooleanCallback> registerInitFwdCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITFWD_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().initfwd);
        }
        return new ScopedObject<>(callback, CANCEL_INITFWD_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_INITFWD_CALLBACK = RelaySim::cancelInitFwdCallback;
    public static void cancelInitFwdCallback(BooleanCallback callback) {
        getState().INITFWD_CALLBACKS.remove(callback);
    }

    public static boolean getInitFwd() {
        return getState().initfwd;
    }

    public static void setInitFwd(boolean initfwd) {
        setInitFwd(initfwd, true);
    }

    public static final Consumer<BooleanCallback> CALL_INITFWD_CALLBACK = callback -> callback.callback("0", getState().initfwd);
    private static void setInitFwd(boolean initfwd, boolean notifyRobot) {
        if(initfwd != getState().initfwd) {
            getState().initfwd = initfwd;
            getState().INITFWD_CALLBACKS.forEach(CALL_INITFWD_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "Relay", new WSValue("<init_fwd", initfwd));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerInitRevCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITREV_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().initrev);
        }
        return new ScopedObject<>(callback, CANCEL_INITREV_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_INITREV_CALLBACK = RelaySim::cancelInitRevCallback;
    public static void cancelInitRevCallback(BooleanCallback callback) {
        getState().INITREV_CALLBACKS.remove(callback);
    }

    public static boolean getInitRev() {
        return getState().initrev;
    }

    public static void setInitRev(boolean initrev) {
        setInitRev(initrev, true);
    }

    public static final Consumer<BooleanCallback> CALL_INITREV_CALLBACK = callback -> callback.callback("0", getState().initrev);
    private static void setInitRev(boolean initrev, boolean notifyRobot) {
        if(initrev != getState().initrev) {
            getState().initrev = initrev;
            getState().INITREV_CALLBACKS.forEach(CALL_INITREV_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "Relay", new WSValue("<init_rev", initrev));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerFwdCallback(BooleanCallback callback, boolean initialNotify) {
        getState().FWD_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().fwd);
        }
        return new ScopedObject<>(callback, CANCEL_FWD_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_FWD_CALLBACK = RelaySim::cancelFwdCallback;
    public static void cancelFwdCallback(BooleanCallback callback) {
        getState().FWD_CALLBACKS.remove(callback);
    }

    public static boolean getFwd() {
        return getState().fwd;
    }

    public static void setFwd(boolean fwd) {
        setFwd(fwd, true);
    }

    public static final Consumer<BooleanCallback> CALL_FWD_CALLBACK = callback -> callback.callback("0", getState().fwd);
    private static void setFwd(boolean fwd, boolean notifyRobot) {
        if(fwd != getState().fwd) {
            getState().fwd = fwd;
            getState().FWD_CALLBACKS.forEach(CALL_FWD_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "Relay", new WSValue("<fwd", fwd));
        }
    }
    
    
    public static ScopedObject<BooleanCallback> registerRevCallback(BooleanCallback callback, boolean initialNotify) {
        getState().REV_CALLBACKS.addIfAbsent(callback);
        if(initialNotify) {
            callback.callback("0", getState().rev);
        }
        return new ScopedObject<>(callback, CANCEL_REV_CALLBACK);
    }

    public static final Consumer<BooleanCallback> CANCEL_REV_CALLBACK = RelaySim::cancelRevCallback;
    public static void cancelRevCallback(BooleanCallback callback) {
        getState().REV_CALLBACKS.remove(callback);
    }

    public static boolean getRev() {
        return getState().rev;
    }

    public static void setRev(boolean rev) {
        setRev(rev, true);
    }

    public static final Consumer<BooleanCallback> CALL_REV_CALLBACK = callback -> callback.callback("0", getState().rev);
    private static void setRev(boolean rev, boolean notifyRobot) {
        if(rev != getState().rev) {
            getState().rev = rev;
            getState().REV_CALLBACKS.forEach(CALL_REV_CALLBACK);
        }
        if(notifyRobot) {
            ConnectionProcessor.brodcastMessage("0", "Relay", new WSValue("<rev", rev));
        }
    }
    

    public static void processMessage(String device, List<WSValue> data) {
        
        for(WSValue value: data) {
            processValue(value);
        }
        
    }

    
    
    
    private static final BiConsumer<Boolean, Boolean> SET_INITFWD = RelaySim::setInitFwd;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_INITREV = RelaySim::setInitRev;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_FWD = RelaySim::setFwd;
    
    
    private static final BiConsumer<Boolean, Boolean> SET_REV = RelaySim::setRev;
    
    private static void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                
                
                
                case "<init_fwd": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITFWD);
                    
                    break;
                }
                
                
                case "<init_rev": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITREV);
                    
                    break;
                }
                
                
                case "<fwd": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_FWD);
                    
                    break;
                }
                
                
                case "<rev": {
                    
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_REV);
                    
                    break;
                }
                
            }
        }
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