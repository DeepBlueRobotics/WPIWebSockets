package org.team199.wpiws.connection;

/**
 * Represents a device value in the WPIWebSockets protocol
 */
public class WSValue {
    
    private MessageDirection direction;
    private String key;
    private Object value;

    public WSValue(MessageDirection direction, String key, Object value) {
        this.direction = direction;
        this.key = key;
        this.value = value;
    }

    public MessageDirection getDirection() {
        return direction;
    }

    public void setDirection(MessageDirection direction) {
        this.direction = direction;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
}
