{%- if schemaName === "simdeviceData" -%}
{% include "../partials/SimDeviceSim.java" -%}
{%- else -%}
{% set type = schema.property("type").const() -%}
{% set name = type | formatName -%}
{% set hasId = type | hasId -%}
{% set schema = schema.property("data") -%}
{% set hasInit = schema.properties() | contains("<init") -%}
{% set cstatic = " " if hasId else " static " -%}
{% set cthis = "this" if hasId else name + "Sim" -%}
{% set qz = '""' | safe -%}
{% set cid = "id" if hasId else qz -%}
{% set a = name | aAn -%}
{%- set props = schema.properties() | filterProps -%}
// PLEASE DO NOT EDIT THIS FILE!!!
// IT IS AUTOMATICALLY GENERATED BY THE 'generateDeviceFiles' GRADLE TASK
// FROM THE TEMPLATE FILE 'asyncapi-template/template/$$schema$$.java'.
// EDIT THAT FILE INSTEAD.
package org.carlmontrobotics.wpiws.devices;

import java.util.List;
{% if hasId -%}
import java.util.Map;
{% endif -%}
import java.util.Objects;
import java.util.Set;
{% if hasId -%}
import java.util.concurrent.ConcurrentHashMap;
{% endif -%}
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.carlmontrobotics.wpiws.StateDevice;
import org.carlmontrobotics.wpiws.connection.ConnectionProcessor;
import org.carlmontrobotics.wpiws.connection.WSValue;
import org.carlmontrobotics.wpiws.interfaces.*;
{%- if (props | usesCustomTypes(name)) %}
import org.carlmontrobotics.wpiws.types.*;
{%- endif %}

/**
 * Represents a simulated {{ name | lower }}
 */
{% if hasId -%}
public class {{ name }}Sim extends StateDevice<{{ name }}Sim.State> {
{%- else %}
public class {{ name }}Sim {
{%- endif %}

    {% if hasInit -%}
    private static final Set<String> INITIALIZED_DEVICES = new ConcurrentSkipListSet<>();
    private static final Set<BooleanCallback> STATIC_INITIALIZED_CALLBACKS = new CopyOnWriteArraySet<>();
    {% endif -%}
    {% if hasId -%}
    private static final Map<String, {{ name }}Sim.State> STATE_MAP = new ConcurrentHashMap<>();
    private final String type;
    {% else -%}
    private static final {{ name }}Sim.State STATE = new State();
    {% endif -%}
    private static final Set<String> UNKNOWN_KEYS = new ConcurrentSkipListSet<>();

    {% if hasId -%}
    /**
     * Creates a new {{ name }}Sim
     * @param id the device identifier of this {{ name }}Sim
     * @param type the type of this {{ name }}Sim
     */
    public {{ name }}Sim(String id, String type) {
        super(id, STATE_MAP);
        this.type = type;
    }

    public {{ name }}Sim(String id) {
        this(id, "{{ type }}");
    }
    {%- endif -%}

    {%- if hasInit %}

    /**
     * Registers a BooleanCallback to be called whenever {{ a }} {{ name }}Sim device is initialized or uninitialized
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with the device identifiers of all currently initialized {{ name }}Sims
     * @return the callback object that can be used to cancel the callback
     * @see #cancelStaticInitializedCallback(BooleanCallback)
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public static BooleanCallback registerStaticInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        STATIC_INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            INITIALIZED_DEVICES.forEach(device -> callback.callback(device, true));
        }
        return callback;
    }

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
     * @return the callback object that can be used to cancel the callback
     * @see #cancelInitializedCallback(BooleanCallback)
     * @see #registerStaticInitializedCallback(BooleanCallback, boolean)
     */
    public BooleanCallback registerInitializedCallback(BooleanCallback callback, boolean initialNotify) {
        getState().INITIALIZED_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback(id, getState().init);
        }
        return callback;
    }

    /**
     * Deregisters the given initialized callback
     * @param callback the callback to deregister
     * @see #registerInitializedCallback(BooleanCallback, boolean)
     */
    public void cancelInitializedCallback(BooleanCallback callback) {
        getState().INITIALIZED_CALLBACKS.remove(callback);
    }

    /**
     * @return whether this {{ name }}Sim is initialized
     */
    public boolean getInitialized() {
        return getState().init;
    }

    /**
     * Sets the initialized state of this {{ name }}Sim
     * @param initialized the new initialized state of this {{ name }}Sim
     */
    public void setInitialized(boolean initialized) {
        setInitialized(initialized, true);
    }

    /**
     * A Consumer which calls the given BooleanCallback with the current initialized state of this {{ name }}Sim
     */
    public final Consumer<BooleanCallback> CALL_INITIALIZED_CALLBACK = callback -> callback.callback(id, getState().init);
    private void setInitialized(boolean initialized, boolean notifyRobot) {
        if(initialized != getState().init) {
            getState().init = initialized;
            STATIC_INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            getState().INITIALIZED_CALLBACKS.forEach(CALL_INITIALIZED_CALLBACK);
            INITIALIZED_DEVICES.add(id);
        } else {
            INITIALIZED_DEVICES.remove(id);
        }
        if(notifyRobot) {
            ConnectionProcessor.broadcastMessage(id, type, new WSValue("<init", initialized));
        }
    }

    /**
     * @return an array of the identifiers of all currently initialized {{ name }}Sims
     */
    public static String[] enumerateDevices() {
        return INITIALIZED_DEVICES.toArray(new String[INITIALIZED_DEVICES.size()]);
    }
    {%- endif -%}

    {%- if hasId %}

    @Override
    protected State generateState() {
        return new State();
    }

    {% else -%}

    protected static {{ name }}Sim.State getState() {
        return STATE;
    }

    {% endif -%}

    {% for propName, prop in props -%}
    {% import "../partials/initVars.java" as varInfo with context -%}
    /**
     * Registers a {{ varInfo.callbackType }} to be called whenever the {{ varInfo.pnamel }} of this device is changed
     * @param callback the callback function to call
     * @param initialNotify if <code>true</code>, calls the callback function with this device's current {{ varInfo.pnamel }} value
     * @return the callback object that can be used to cancel the callback
     * @see #cancel{{ varInfo.pname }}Callback({{ varInfo.callbackType }})
     */
    public{{ cstatic }}{{ varInfo.callbackType }} register{{ varInfo.pname }}Callback({{ varInfo.callbackType }} callback, boolean initialNotify) {
        getState().{{ varInfo.pnameu }}_CALLBACKS.add(callback);
        if(initialNotify) {
            callback.callback({{ cid }}, getState().{{ varInfo.pnamel }});
        }
        return callback;
    }

    /**
     * Deregisters the given {{ varInfo.pnamel }} callback
     * @param callback the callback to deregister
     * @see #register{{ varInfo.pname }}Callback({{ varInfo.ptype }}Callback, boolean)
     */
    public{{ cstatic }}void cancel{{ varInfo.pname }}Callback({{ varInfo.callbackType }} callback) {
        getState().{{ varInfo.pnameu }}_CALLBACKS.remove(callback);
    }

    /**
     * @return {{ prop.description() | lower | trim }}
     * @see #set{{ varInfo.pname }}({{ varInfo.pprimtype }})
     */
    public{{ cstatic }}{{ varInfo.ptype }} get{{ varInfo.pname }}() {
        return getState().{{ varInfo.pnamel }};
    }

    {%- if varInfo.isRobotInput %}

    /**
     * Set {{ prop.description() | lower | trim }}
     * @see #get{{ varInfo.pname }}()
     */
    public{{ cstatic }}void set{{ varInfo.pname }}({{ varInfo.ptype }} {{ varInfo.pnamel }}) {
        set{{ varInfo.pname }}({{ varInfo.pnamel }}, true);
    }
    {%- endif %}

    /**
     * A Consumer which calls the given callback with the current {{ varInfo.pnamel }} value of this PWMSim
     */
    public{{ cstatic }}final Consumer<{{ varInfo.callbackType }}> CALL_{{ varInfo.pnameu }}_CALLBACK = callback -> callback.callback({{ cid }}, getState().{{ varInfo.pnamel }});
    private{{ cstatic }}void set{{ varInfo.pname }}({{ varInfo.ptype }} {{ varInfo.pnamel }}, boolean notifyRobot) {
        if(!Objects.deepEquals({{ varInfo.pnamel }}, getState().{{ varInfo.pnamel }})) {
            getState().{{ varInfo.pnamel }} = {{ varInfo.pnamel }};
            getState().{{ varInfo.pnameu }}_CALLBACKS.forEach(CALL_{{ varInfo.pnameu }}_CALLBACK);
        }
        if(notifyRobot) {
            {%- if not varInfo.isRobotInput %}
            System.err.println("WARNING: {{ name }}Sim#set{{ varInfo.pname }}({{ varInfo.ptype }}, true) was called, but {{ varInfo.pfname }} is not a robot input!");
            {%- endif %}
            {% if hasId -%}
            ConnectionProcessor.broadcastMessage({{ cid }}, type, new WSValue("{{ varInfo.pfname }}", {{ varInfo.pnamel }}));
            {%- else %}
            ConnectionProcessor.broadcastMessage({{ cid }}, "{{ type }}", new WSValue("{{ varInfo.pfname }}", {{ varInfo.pnamel }}));
            {%- endif %}
        }
    }

    {% endfor -%}

    /**
     * An implementation of {@link org.carlmontrobotics.wpiws.interfaces.DeviceMessageProcessor} which processes WPI HALSim messages for {{ name }}Sims
     * @param device the device identifier of the device sending the message
     * @param type the type of the device sending the message
     * @param data the data associated with the message
     */
    public static void processMessage(String device, String type, List<WSValue> data) {
        // Process all of the values, but save the "<init" value for last
        // so that the rest of the state has been set when the initialize
        // callback is called.
        WSValue init = null;
        {%- if hasId %}
        {{ name }}Sim simDevice = new {{ name }}Sim(device, type);

        for(WSValue value: data) {
            if (value.getKey().equals("<init"))
                init = value;
            else
                simDevice.processValue(value);
        }
        if (init != null)
            simDevice.processValue(init);
        {%- else %}
        for(WSValue value: data) {
            if (value.getKey().equals("<init"))
                init = value;
            else
                processValue(value);
        }
        if (init != null)
            processValue(init);
        {%- endif %}
    }

    {% if hasInit -%}
    private final BiConsumer<Boolean, Boolean> SET_INITIALIZED = this::setInitialized;
    {% endif -%}
    {% for propName, prop in props -%}
    {% import "../partials/initVars.java" as varInfo with context -%}
    private{{ cstatic }}final BiConsumer<{{ varInfo.pobjtype }}, Boolean> SET_{{ varInfo.pnameu }} = {{ cthis }}::set{{ varInfo.pname }};
    {% endfor -%}
    private{{ cstatic }}void processValue(WSValue value) {
        if(value.getKey() instanceof String && value.getValue() != null) {
            switch((String)value.getKey()) {
                {%- if hasInit %}
                case "<init": {
                    filterMessageAndIgnoreRobotState(value.getValue(), Boolean.class, SET_INITIALIZED, null);
                    break;
                }
                {%- endif -%}
                {% for propName, prop in props -%}
                {% import "../partials/initVars.java" as varInfo with context %}
                case "{{ varInfo.pfname }}": {
                    {% if hasId -%}
                    filterMessageAndIgnoreRobotState(value.getValue(), {{ varInfo.pobjtype }}.class, SET_{{ varInfo.pnameu }}, {{ varInfo.parser }});
                    {% else -%}
                    StateDevice.filterMessageAndIgnoreRobotState(value.getValue(), {{ varInfo.pobjtype }}.class, SET_{{ varInfo.pnameu }}, {{ varInfo.parser }});
                    {% endif -%}
                    break;
                }
                {%- endfor %}
                default: {
                    if (UNKNOWN_KEYS.add(value.getKey())) {
                        System.err.println("{{ name }}Sim received value '" + value.getKey() + ":" + value.getValue() + "' but does not recognize '" + value.getKey() + "'. Values with that key will be ignored.");
                    }
                }
            }
        }
    }

    /**
     * Contains all information about the state of {{ a }} {{ name }}Sim
     */
    public static class State {
        {% if hasInit -%}
        public boolean init = false;
        {%- endif -%}
        {%- for propName, prop in props -%}
        {%- import "../partials/initVars.java" as varInfo with context %}
        public {{ varInfo.ptype }} {{ varInfo.pnamel }} = {{ varInfo.pinit }};
        {%- endfor %}
        {% if hasInit -%}
        public final Set<BooleanCallback> INITIALIZED_CALLBACKS = new CopyOnWriteArraySet<>();
        {%- endif -%}
        {%- for propName, prop in props -%}
        {%- import "../partials/initVars.java" as varInfo with context %}
        public final Set<{{ varInfo.callbackType }}> {{ varInfo.pnameu }}_CALLBACKS = new CopyOnWriteArraySet<>();
        {%- endfor %}
    }

}
{% endif -%}
