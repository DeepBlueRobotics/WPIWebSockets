package org.team199.wpiws.connection;

public enum MessageDirection {
        
    INPUT(">"), OUTPUT("<"), BOTH("<>");

    public final String identifier;

    private MessageDirection(String identifier) {
        this.identifier = identifier;
    }
    
    public static MessageDirection from(String key) {
        if(key.startsWith(BOTH.identifier)) {
            return BOTH;
        } else if(key.startsWith(INPUT.identifier)) {
            return INPUT;
        } else if(key.startsWith(OUTPUT.identifier)) {
            return OUTPUT;
        }
        throw new IllegalArgumentException("Key: " + key + " does not have a valid identifier");
    }
    
}
