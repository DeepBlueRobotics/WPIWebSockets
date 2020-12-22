package org.team199.wpiws.connection;

/**
 * Represents whether a data value is an input to robot code, an output from robot code, or both
 * @see WSValue
 */
public enum MessageDirection {

    /**
     * Specifies that the data value is an input to robot code
     */
    INPUT(">"),
    /**
     * Specifies that the data value is an output from robot code
     */
    OUTPUT("<"),
    /**
     * Specifies that the data value is both an input from and an output to robot code
     */
    BOTH("<>");

    /**
     * The data value prefix used to indicate this MessageDirection
     */
    public final String identifier;

    private MessageDirection(String identifier) {
        this.identifier = identifier;
    }
    
    /**
     * Determines the MessageDirection of a given data value
     * @param key the name of the data value to check
     * @return the MessageDirection of the given data value
     * @throws IllegalArgumentException if the given data value does not correspond to a known MessageDirection
     */
    public static MessageDirection from(String key) throws IllegalArgumentException {
        if(key.startsWith(BOTH.identifier)) {
            return BOTH;
        } else if(key.startsWith(INPUT.identifier)) {
            return INPUT;
        } else if(key.startsWith(OUTPUT.identifier)) {
            return OUTPUT;
        }
        throw new IllegalArgumentException("Key: " + key + " does not begin with a valid identifier");
    }
    
}
