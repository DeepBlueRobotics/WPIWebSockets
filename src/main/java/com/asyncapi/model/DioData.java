package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * DIO Data (type: DIO, device: channel number)
 */
public class DioData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean init;
    
    private @Valid boolean value;
    
    private @Valid int pulseLength;
    
    private @Valid boolean input;
    

    

    /**
     * Whether or not this DIO channel has been initialized
     */
    @JsonProperty("&lt;init")
    public boolean getInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
    

    /**
     * Current value of this DIO channel
     */
    @JsonProperty("&lt;&gt;value")
    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
    

    
    @JsonProperty("&lt;pulse_length")
    public int getPulseLength() {
        return pulseLength;
    }

    public void setPulseLength(int pulseLength) {
        this.pulseLength = pulseLength;
    }
    

    /**
     * Whether or not this DIO channel has been initialized as an input
     */
    @JsonProperty("&lt;input")
    public boolean getInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DioData dioData = (DioData) o;
        return 
            Objects.equals(this.init, dioData.init) &&
            Objects.equals(this.value, dioData.value) &&
            Objects.equals(this.pulseLength, dioData.pulseLength) &&
            Objects.equals(this.input, dioData.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(init, value, pulseLength, input);
    }

    @Override
    public String toString() {
        return "class DioData {\n" +
        
                "    init: " + toIndentedString(init) + "\n" +
                "    value: " + toIndentedString(value) + "\n" +
                "    pulseLength: " + toIndentedString(pulseLength) + "\n" +
                "    input: " + toIndentedString(input) + "\n" +
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