package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Relay data (type: Relay, device: channel number)
 */
public class RelayData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean initFwd;
    
    private @Valid boolean initRev;
    
    private @Valid boolean fwd;
    
    private @Valid boolean rev;
    

    

    /**
     * Whether or not the forward direction of this relay is initialized
     */
    @JsonProperty("&lt;init_fwd")
    public boolean getInitFwd() {
        return initFwd;
    }

    public void setInitFwd(boolean initFwd) {
        this.initFwd = initFwd;
    }
    

    /**
     * Whether or not the reverse direction of this relay is initialized
     */
    @JsonProperty("&lt;init_rev")
    public boolean getInitRev() {
        return initRev;
    }

    public void setInitRev(boolean initRev) {
        this.initRev = initRev;
    }
    

    /**
     * Whether or not the forward direction of this relay is active
     */
    @JsonProperty("&lt;fwd")
    public boolean getFwd() {
        return fwd;
    }

    public void setFwd(boolean fwd) {
        this.fwd = fwd;
    }
    

    /**
     * Whether or not the reverse direction of this relay is active
     */
    @JsonProperty("&lt;rev")
    public boolean getRev() {
        return rev;
    }

    public void setRev(boolean rev) {
        this.rev = rev;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelayData relayData = (RelayData) o;
        return 
            Objects.equals(this.initFwd, relayData.initFwd) &&
            Objects.equals(this.initRev, relayData.initRev) &&
            Objects.equals(this.fwd, relayData.fwd) &&
            Objects.equals(this.rev, relayData.rev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initFwd, initRev, fwd, rev);
    }

    @Override
    public String toString() {
        return "class RelayData {\n" +
        
                "    initFwd: " + toIndentedString(initFwd) + "\n" +
                "    initRev: " + toIndentedString(initRev) + "\n" +
                "    fwd: " + toIndentedString(fwd) + "\n" +
                "    rev: " + toIndentedString(rev) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
           return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}