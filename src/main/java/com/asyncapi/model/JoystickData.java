package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Joystick Properties (type: Joystick, device: joystick number)
 */
public class JoystickData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean[] buttonsArray;
    
    private @Valid Object[] povsArray;
    
    private @Valid Object[] axesArray;
    

    

    /**
     * State of all buttons on this joystick
     */
    @JsonProperty("&gt;buttons")
    public boolean[] getButtons() {
        return buttonsArray;
    }

    public void setButtons(boolean[] buttonsArray) {
        this.buttonsArray = buttonsArray;
    }
    

    /**
     * State of all POV switches on this joystick
     */
    @JsonProperty("&gt;povs")
    public Object[] getPovs() {
        return povsArray;
    }

    public void setPovs(Object[] povsArray) {
        this.povsArray = povsArray;
    }
    

    /**
     * State of all axes on this joystick
     */
    @JsonProperty("&gt;axes")
    public Object[] getAxes() {
        return axesArray;
    }

    public void setAxes(Object[] axesArray) {
        this.axesArray = axesArray;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JoystickData joystickData = (JoystickData) o;
        return 
            Arrays.equals(this.buttonsArray, joystickData.buttonsArray) &&
            Arrays.equals(this.povsArray, joystickData.povsArray) &&
            Arrays.equals(this.axesArray, joystickData.axesArray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buttonsArray, povsArray, axesArray);
    }

    @Override
    public String toString() {
        return "class JoystickData {\n" +
        
                "    buttonsArray: " + toIndentedString(buttonsArray) + "\n" +
                "    povsArray: " + toIndentedString(povsArray) + "\n" +
                "    axesArray: " + toIndentedString(axesArray) + "\n" +
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