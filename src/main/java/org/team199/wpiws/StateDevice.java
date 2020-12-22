package org.team199.wpiws;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * Represents a device which has a number of internal states based on a given id
 */
public abstract class StateDevice<T> {
    
    /**
     * An IntFunction which is equivilent to <code>String[]::new</code>
     */
    public static final IntFunction<String[]> CREATE_STRING_ARRAY = String[]::new;
    /**
     * The identifier associated with this StateEvent
     */
    protected final String id;
    private final T state;

    /**
     * Creates a new StateDevice
     * @param id the identifier for this StateDevice
     * @param stateMap a Map which maps identifiers to their respective internal states. This should be stored as <code>static</code> and <code>final</code> in the implementing class.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public StateDevice(String id, Map<String, T> stateMap) {
        this.id = id;
        if(!stateMap.containsKey(id)) {
            stateMap.put(id, generateState());
        }
        this.state = stateMap.get(id);
    }
    
    /**
     * Retrieves the state from the state map based on this StateDevice's unique identifier
     * @return the state from the state map based on this StateDevice's unique identifier
     * @see #id
     */
    protected T getState() {
        return state;
    }
    
    /**
     * Generates a blank state to be stored in this device type's state map
     */
    protected abstract T generateState();

    /**
     * Casts and forwards the given object to the given process type iff it is assignable from the requested type
     * @param <T> the requested type
     * @param recievedObject the object recieved from the WPI HALSim
     * @param requestedType the type to which to try to cast the object
     * @param processor the processor which should recieve the forwarded method call
     * @see Class#isAssignableFrom(Class)
     */
    @SuppressWarnings("unchecked")
    public static <T> void filterMessage(Object recievedObject, Class<T> requestedType, Consumer<T> processor) {
        if(requestedType.isAssignableFrom(recievedObject.getClass())) {
            processor.accept((T)recievedObject);
        } else {
            System.err.println(String.format("Invalid value: %1$s of type: %2$s expected type: %3$s", recievedObject.toString(), recievedObject.getClass().getName(), requestedType.getName()));
        }
    }

    /**
     * Casts and forwards the given object to the given process type iff it is assignable from the requested type and sets the second argument of the BiConsumer to <code>false</code>
     * @param <T> the requested type
     * @param recievedObject the object recieved from the WPI HALSim
     * @param requestedType the type to which to try to cast the object
     * @param processor the processor which should recieve the forwarded method call
     * @see Class#isAssignableFrom(Class)
     */
    @SuppressWarnings("unchecked")
    public static <T> void filterMessageAndIgnoreRobotState(Object recievedObject, Class<T> requestedType, BiConsumer<T, Boolean> robotStateProcessor) {
        if(requestedType.isAssignableFrom(recievedObject.getClass())) {
            robotStateProcessor.accept((T)recievedObject, false);
        }
    }

}
