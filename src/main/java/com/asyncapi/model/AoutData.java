package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Analog Out Data (type: AO, device: channel number)
 */
public class AoutData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean init;
    
    private @Valid Object voltage;
    

    

    /**
     * Whether or not this Analog Out channel has been initialized
     */
    @JsonProperty("&lt;init")
    public boolean getInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
    

    /**
     * Current output voltage of this Analog Out channel
     */
    @JsonProperty("&lt;voltage")
    public Object getVoltage() {
        return voltage;
    }

    public void setVoltage(Object voltage) {
        this.voltage = voltage;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AoutData aoutData = (AoutData) o;
        return 
            Objects.equals(this.init, aoutData.init) &&
            Objects.equals(this.voltage, aoutData.voltage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(init, voltage);
    }

    @Override
    public String toString() {
        return "class AoutData {\n" +
        
                "    init: " + toIndentedString(init) + "\n" +
                "    voltage: " + toIndentedString(voltage) + "\n" +
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