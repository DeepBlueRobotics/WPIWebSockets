package org.team199.wpiws.connection;

/**
 * Represents a device value in the WPIWebSockets protocol
 */
public class WSValue {

    private String key;
    private Object value;

    /**
     * Creates a WSValue from the given information
     * @param key the name of this WSValue without MessageDirection information
     * @param value the value of this WSValue
     */
    public WSValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the name of this WSValue without MessageDirection information
     * @see #setKey(String)
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the name of this WSValue
     * @param key the new name of this WSValue
     * @see #getKey()
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value of this WSValue
     * @see #setValue(Object)
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of this WSValue
     * @param value the new value of this WSValue
     * @see #getValue()
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
}
