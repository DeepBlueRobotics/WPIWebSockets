package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Examples: {"type":"PWM","device":"1","data":{"&lt;speed":0.5}}, {"type":"DIO","device":"3","data":{"&lt;init":true}}
 */
public class WpilibwsMsgPayload {
    
    private @Valid String type;
    
    private @Valid String device;
    
    public interface OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {

    }
    private @Valid OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData data;
    

    

    /**
     * Device Type (e.g. DIO/AO/AI/PWM/Encoder etc)
     */
    @JsonProperty("type")@NotNull
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    /**
     * Device Identifier (usually channel)
     */
    @JsonProperty("device")@NotNull
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    

    /**
     * Payload for the message. Note that not all fields for a particular message will get sent. Only the diff of the current state of the particular device will be sent.
     */
    @JsonProperty("data")
    public OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData getData() {
        return data;
    }

    public void setData(OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData data) {
        this.data = data;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WpilibwsMsgPayload wpilibwsMsgPayload = (WpilibwsMsgPayload) o;
        return 
            Objects.equals(this.type, wpilibwsMsgPayload.type) &&
            Objects.equals(this.device, wpilibwsMsgPayload.device) &&
            Objects.equals(this.data, wpilibwsMsgPayload.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, device, data);
    }

    @Override
    public String toString() {
        return "class WpilibwsMsgPayload {\n" +
        
                "    type: " + toIndentedString(type) + "\n" +
                "    device: " + toIndentedString(device) + "\n" +
                "    data: " + toIndentedString(data) + "\n" +
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