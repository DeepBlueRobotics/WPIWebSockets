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
    
    private @Valid Object VVoltage6;
    
    private @Valid Object VCurrent6;
    
    private @Valid boolean VActive6;
    
    private @Valid Object VFaults6;
    
    private @Valid Object VVoltage5;
    
    private @Valid Object VCurrent5;
    
    private @Valid boolean VActive5;
    
    private @Valid Object VFaults5;
    
    private @Valid Object V3Voltage3;
    
    private @Valid Object V3Current3;
    
    private @Valid boolean V3Active3;
    
    private @Valid Object V3Faults3;
    

    

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
    public Object getVVoltage6() {
        return VVoltage6;
    }

    public void setVVoltage6(Object VVoltage6) {
        this.VVoltage6 = VVoltage6;
    }
    

    /**
     * Current on the 6V rail
     */
    @JsonProperty("&gt;6v_current")
    public Object getVCurrent6() {
        return VCurrent6;
    }

    public void setVCurrent6(Object VCurrent6) {
        this.VCurrent6 = VCurrent6;
    }
    

    /**
     * Whether or not the 6V rail is active
     */
    @JsonProperty("&gt;6v_active")
    public boolean getVActive6() {
        return VActive6;
    }

    public void setVActive6(boolean VActive6) {
        this.VActive6 = VActive6;
    }
    

    /**
     * Bitmask of faults on the 6V rail
     */
    @JsonProperty("&gt;6v_faults")
    public Object getVFaults6() {
        return VFaults6;
    }

    public void setVFaults6(Object VFaults6) {
        this.VFaults6 = VFaults6;
    }
    

    /**
     * Voltage on the 5V rail
     */
    @JsonProperty("&gt;5v_voltage")
    public Object getVVoltage5() {
        return VVoltage5;
    }

    public void setVVoltage5(Object VVoltage5) {
        this.VVoltage5 = VVoltage5;
    }
    

    /**
     * Current on the 5V rail
     */
    @JsonProperty("&gt;5v_current")
    public Object getVCurrent5() {
        return VCurrent5;
    }

    public void setVCurrent5(Object VCurrent5) {
        this.VCurrent5 = VCurrent5;
    }
    

    /**
     * Whether or not the 5V rail is active
     */
    @JsonProperty("&gt;5v_active")
    public boolean getVActive5() {
        return VActive5;
    }

    public void setVActive5(boolean VActive5) {
        this.VActive5 = VActive5;
    }
    

    /**
     * Bitmask of faults on the 5V rail
     */
    @JsonProperty("&gt;5v_faults")
    public Object getVFaults5() {
        return VFaults5;
    }

    public void setVFaults5(Object VFaults5) {
        this.VFaults5 = VFaults5;
    }
    

    /**
     * Voltage on the 3.3V rail
     */
    @JsonProperty("&gt;3v3_voltage")
    public Object getV3Voltage3() {
        return V3Voltage3;
    }

    public void setV3Voltage3(Object V3Voltage3) {
        this.V3Voltage3 = V3Voltage3;
    }
    

    /**
     * Current on the 3.3V rail
     */
    @JsonProperty("&gt;3v3_current")
    public Object getV3Current3() {
        return V3Current3;
    }

    public void setV3Current3(Object V3Current3) {
        this.V3Current3 = V3Current3;
    }
    

    /**
     * Whether or not the 3.3V rail is active
     */
    @JsonProperty("&gt;3v3_active")
    public boolean getV3Active3() {
        return V3Active3;
    }

    public void setV3Active3(boolean V3Active3) {
        this.V3Active3 = V3Active3;
    }
    

    /**
     * Bitmask of faults on the 3.3V rail
     */
    @JsonProperty("&gt;3v3_faults")
    public Object getV3Faults3() {
        return V3Faults3;
    }

    public void setV3Faults3(Object V3Faults3) {
        this.V3Faults3 = V3Faults3;
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
            Objects.equals(this.VVoltage6, roborioData.VVoltage6) &&
            Objects.equals(this.VCurrent6, roborioData.VCurrent6) &&
            Objects.equals(this.VActive6, roborioData.VActive6) &&
            Objects.equals(this.VFaults6, roborioData.VFaults6) &&
            Objects.equals(this.VVoltage5, roborioData.VVoltage5) &&
            Objects.equals(this.VCurrent5, roborioData.VCurrent5) &&
            Objects.equals(this.VActive5, roborioData.VActive5) &&
            Objects.equals(this.VFaults5, roborioData.VFaults5) &&
            Objects.equals(this.V3Voltage3, roborioData.V3Voltage3) &&
            Objects.equals(this.V3Current3, roborioData.V3Current3) &&
            Objects.equals(this.V3Active3, roborioData.V3Active3) &&
            Objects.equals(this.V3Faults3, roborioData.V3Faults3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fpgaButton, vinVoltage, vinCurrent, VVoltage6, VCurrent6, VActive6, VFaults6, VVoltage5, VCurrent5, VActive5, VFaults5, V3Voltage3, V3Current3, V3Active3, V3Faults3);
    }

    @Override
    public String toString() {
        return "class RoborioData {\n" +
        
                "    fpgaButton: " + toIndentedString(fpgaButton) + "\n" +
                "    vinVoltage: " + toIndentedString(vinVoltage) + "\n" +
                "    vinCurrent: " + toIndentedString(vinCurrent) + "\n" +
                "    VVoltage6: " + toIndentedString(VVoltage6) + "\n" +
                "    VCurrent6: " + toIndentedString(VCurrent6) + "\n" +
                "    VActive6: " + toIndentedString(VActive6) + "\n" +
                "    VFaults6: " + toIndentedString(VFaults6) + "\n" +
                "    VVoltage5: " + toIndentedString(VVoltage5) + "\n" +
                "    VCurrent5: " + toIndentedString(VCurrent5) + "\n" +
                "    VActive5: " + toIndentedString(VActive5) + "\n" +
                "    VFaults5: " + toIndentedString(VFaults5) + "\n" +
                "    V3Voltage3: " + toIndentedString(V3Voltage3) + "\n" +
                "    V3Current3: " + toIndentedString(V3Current3) + "\n" +
                "    V3Active3: " + toIndentedString(V3Active3) + "\n" +
                "    V3Faults3: " + toIndentedString(V3Faults3) + "\n" +
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