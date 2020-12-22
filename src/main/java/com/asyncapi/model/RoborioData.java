package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * RoboRIO Data (type: RoboRIO)
 */
public class RoborioData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean fpgaButton;
    
    private @Valid Object vinVoltage;
    
    private @Valid Object vinCurrent;
    
    private @Valid Object Voltage6V;
    
    private @Valid Object Current6V;
    
    private @Valid boolean Active6V;
    
    private @Valid Object Faults6V;
    
    private @Valid Object Voltage5V;
    
    private @Valid Object Current5V;
    
    private @Valid boolean Active5V;
    
    private @Valid Object Faults5V;
    
    private @Valid Object Voltage3V3;
    
    private @Valid Object Current3V3;
    
    private @Valid boolean Active3V3;
    
    private @Valid Object Faults3V3;
    

    

    /**
     * Whether or not the FPGA button on the RoboRIO is active
     */
    @JsonProperty("&gt;fpga_button")
    public boolean getFpgaButton() {
        return fpgaButton;
    }

    public void setFpgaButton(boolean fpgaButton) {
        this.fpgaButton = fpgaButton;
    }
    

    /**
     * Input voltage
     */
    @JsonProperty("&gt;vin_voltage")
    public Object getVinVoltage() {
        return vinVoltage;
    }

    public void setVinVoltage(Object vinVoltage) {
        this.vinVoltage = vinVoltage;
    }
    

    /**
     * Input Current
     */
    @JsonProperty("&gt;vin_current")
    public Object getVinCurrent() {
        return vinCurrent;
    }

    public void setVinCurrent(Object vinCurrent) {
        this.vinCurrent = vinCurrent;
    }
    

    /**
     * Voltage on the 6V rail
     */
    @JsonProperty("&gt;6v_voltage")
    public Object getVoltage6V() {
        return Voltage6V;
    }

    public void setVVoltage6V(Object Voltage6V) {
        this.Voltage6V = Voltage6V;
    }
    

    /**
     * Current on the 6V rail
     */
    @JsonProperty("&gt;6v_current")
    public Object getCurrent6V() {
        return Current6V;
    }

    public void setCurrent6V(Object Current6V) {
        this.Current6V = Current6V;
    }
    

    /**
     * Whether or not the 6V rail is active
     */
    @JsonProperty("&gt;6v_active")
    public boolean getActive6V() {
        return Active6V;
    }

    public void setActive6V(boolean Active6V) {
        this.Active6V = Active6V;
    }
    

    /**
     * Bitmask of faults on the 6V rail
     */
    @JsonProperty("&gt;6v_faults")
    public Object getFaults6V() {
        return Faults6V;
    }

    public void setFaults6(Object Faults6V) {
        this.Faults6V = Faults6V;
    }
    

    /**
     * Voltage on the 5V rail
     */
    @JsonProperty("&gt;5v_voltage")
    public Object getVoltage5V() {
        return Voltage5V;
    }

    public void setVoltage5V(Object Voltage5V) {
        this.Voltage5V = Voltage5V;
    }
    

    /**
     * Current on the 5V rail
     */
    @JsonProperty("&gt;5v_current")
    public Object getCurrent5V() {
        return Current5V;
    }

    public void setVCurrent5(Object Current5V) {
        this.Current5V = Current5V;
    }
    

    /**
     * Whether or not the 5V rail is active
     */
    @JsonProperty("&gt;5v_active")
    public boolean getActive5V() {
        return Active5V;
    }

    public void setActive5(boolean Active5V) {
        this.Active5V = Active5V;
    }
    

    /**
     * Bitmask of faults on the 5V rail
     */
    @JsonProperty("&gt;5v_faults")
    public Object getFaults5V() {
        return Faults5V;
    }

    public void setFaults5(Object Faults5V) {
        this.Faults5V = Faults5V;
    }
    

    /**
     * Voltage on the 3.3V rail
     */
    @JsonProperty("&gt;3v3_voltage")
    public Object getVoltage3V3() {
        return Voltage3V3;
    }

    public void setVoltage3V3(Object V3Voltage3V3) {
        this.Voltage3V3 = V3Voltage3V3;
    }
    

    /**
     * Current on the 3.3V rail
     */
    @JsonProperty("&gt;3v3_current")
    public Object getCurrent3V3() {
        return Current3V3;
    }

    public void setCurrent3V3(Object Current3V3) {
        this.Current3V3 = Current3V3;
    }
    

    /**
     * Whether or not the 3.3V rail is active
     */
    @JsonProperty("&gt;3v3_active")
    public boolean getActive3V3() {
        return Active3V3;
    }

    public void setActive3V3(boolean Active3V3) {
        this.Active3V3 = Active3V3;
    }
    

    /**
     * Bitmask of faults on the 3.3V rail
     */
    @JsonProperty("&gt;3v3_faults")
    public Object getFaults3V3() {
        return Faults3V3;
    }

    public void setFaults3(Object Faults3V3) {
        this.Faults3V3 = Faults3V3;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoborioData roborioData = (RoborioData) o;
        return 
            Objects.equals(this.fpgaButton, roborioData.fpgaButton) &&
            Objects.equals(this.vinVoltage, roborioData.vinVoltage) &&
            Objects.equals(this.vinCurrent, roborioData.vinCurrent) &&
            Objects.equals(this.Voltage6V, roborioData.Voltage6V) &&
            Objects.equals(this.Current6V, roborioData.Current6V) &&
            Objects.equals(this.Active6V, roborioData.Active6V) &&
            Objects.equals(this.Faults6V, roborioData.Faults6V) &&
            Objects.equals(this.Voltage5V, roborioData.Voltage5V) &&
            Objects.equals(this.Current5V, roborioData.Current5V) &&
            Objects.equals(this.Active5V, roborioData.Active5V) &&
            Objects.equals(this.Faults5V, roborioData.Faults5V) &&
            Objects.equals(this.Voltage3V3, roborioData.Voltage3V3) &&
            Objects.equals(this.Current3V3, roborioData.Current3V3) &&
            Objects.equals(this.Active3V3, roborioData.Active3V3) &&
            Objects.equals(this.Faults3V3, roborioData.Faults3V3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fpgaButton, vinVoltage, vinCurrent, Voltage6V, Current6V, Active6V, Faults6V, Voltage5V, Current5V, Active5V, Faults5V, Voltage3V3, Current3V3, Active3V3, Faults3V3);
    }

    @Override
    public String toString() {
        return "class RoborioData {\n" +
        
                "    fpgaButton: " + toIndentedString(fpgaButton) + "\n" +
                "    vinVoltage: " + toIndentedString(vinVoltage) + "\n" +
                "    vinCurrent: " + toIndentedString(vinCurrent) + "\n" +
                "    VVoltage6: " + toIndentedString(Voltage6V) + "\n" +
                "    VCurrent6: " + toIndentedString(Current6V) + "\n" +
                "    VActive6: " + toIndentedString(Active6V) + "\n" +
                "    VFaults6: " + toIndentedString(Faults6V) + "\n" +
                "    VVoltage5: " + toIndentedString(Voltage5V) + "\n" +
                "    VCurrent5: " + toIndentedString(Current5V) + "\n" +
                "    VActive5: " + toIndentedString(Active5V) + "\n" +
                "    VFaults5: " + toIndentedString(Faults5V) + "\n" +
                "    V3Voltage3: " + toIndentedString(Voltage3V3) + "\n" +
                "    V3Current3: " + toIndentedString(Current3V3) + "\n" +
                "    V3Active3: " + toIndentedString(Active3V3) + "\n" +
                "    V3Faults3: " + toIndentedString(Faults3V3) + "\n" +
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