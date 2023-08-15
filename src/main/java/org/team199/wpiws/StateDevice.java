package org.team199.wpiws;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

import com.github.cliftonlabs.json_simple.JsonArray;

/**
 * Represents a device which has a number of internal states based on a given id
 */
public abstract class StateDevice<T> {

    /**
     * An IntFunction which is equivalent to <code>String[]::new</code>
     */
    public static final IntFunction<String[]> CREATE_STRING_ARRAY = String[]::new;
    /**
     * The identifier associated with this StateDevice
     */
    public final String id;
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
     * Casts and forwards the given object to the given process type iff it is assignable from the requested type and sets the second argument of the BiConsumer to <code>false</code>
     * @param <T> the requested type
     * @param receivedObject the object received from the WPI HALSim
     * @param requestedType the type to which to try to cast the object
     * @param processor the processor which should receive the forwarded method call
     * @see Class#isAssignableFrom(Class)
     */
    @SuppressWarnings("unchecked")
    public static <T> void filterMessageAndIgnoreRobotState(Object receivedObject, Class<T> requestedType, BiConsumer<T, Boolean> robotStateProcessor) {
        boolean isInvalidValue = false;
        if(requestedType.isAssignableFrom(receivedObject.getClass())) {
            robotStateProcessor.accept((T)receivedObject, false);
        } else if(receivedObject instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal)receivedObject;
            if (requestedType == Integer.class) {
                ((BiConsumer<Integer, Boolean>)robotStateProcessor).accept(bd.intValue(), false);
            } else if (requestedType == Double.class) {
                ((BiConsumer<Double, Boolean>)robotStateProcessor).accept(bd.doubleValue(), false);
            } else {
                isInvalidValue = true;
            }
        } else if (receivedObject instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray)receivedObject;
            if (requestedType == int[].class) {
                int[] intArray = new int[jsonArray.size()];
                for (int i = 0; i < intArray.length; i++) {
                    intArray[i] = jsonArray.getInteger(i);
                }
                ((BiConsumer<int[], Boolean>)robotStateProcessor).accept(intArray, false);
            } else if (requestedType == double[].class) {
                double[] doubleArray = new double[jsonArray.size()];
                for (int i = 0; i < doubleArray.length; i++) {
                    doubleArray[i] = jsonArray.getDouble(i);
                }
                ((BiConsumer<double[], Boolean>)robotStateProcessor).accept(doubleArray, false);
            } else if (requestedType == boolean[].class) {
                boolean[] booleanArray = new boolean[jsonArray.size()];
                for (int i = 0; i < booleanArray.length; i++) {
                    booleanArray[i] = jsonArray.getBoolean(i);
                }
                ((BiConsumer<boolean[], Boolean>)robotStateProcessor).accept(booleanArray, false);
            } else if (requestedType == String[].class) {
                String[] stringArray = new String[jsonArray.size()];
                for (int i = 0; i < stringArray.length; i++) {
                    stringArray[i] = jsonArray.getString(i);
                }
                ((BiConsumer<String[], Boolean>)robotStateProcessor).accept(stringArray, false);
            } else {
                isInvalidValue = true;
            }
        } else {
            isInvalidValue = true;
        }
        if (isInvalidValue) {
            System.err.println(String.format("Invalid value: %1$s of type: %2$s expected type: %3$s", receivedObject.toString(), receivedObject.getClass().getName(), requestedType.getName()));
        }
    }

}
