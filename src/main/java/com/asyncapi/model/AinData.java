package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Analog In Data (type: AI, device: channel number)
 */
public class AinData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean init;
    
    private @Valid int avgBits;
    
    private @Valid int oversampleBits;
    
    private @Valid Object voltage;
    
    private @Valid boolean accumInit;
    
    private @Valid Object accumValue;
    
    private @Valid Object accumCount;
    
    private @Valid Object accumCenter;
    
    private @Valid Object accumDeadband;
    

    

    /**
     * Whether or not this Analog In channel has been initialized
     */
    @JsonProperty("&lt;init")
    public boolean getInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
    

    
    @JsonProperty("&lt;avg_bits")
    public int getAvgBits() {
        return avgBits;
    }

    public void setAvgBits(int avgBits) {
        this.avgBits = avgBits;
    }
    

    
    @JsonProperty("&lt;oversample_bits")
    public int getOversampleBits() {
        return oversampleBits;
    }

    public void setOversampleBits(int oversampleBits) {
        this.oversampleBits = oversampleBits;
    }
    

    /**
     * The current input voltage of this Analog In channel
     */
    @JsonProperty("&gt;voltage")
    public Object getVoltage() {
        return voltage;
    }

    public void setVoltage(Object voltage) {
        this.voltage = voltage;
    }
    

    /**
     * Whether or not the accumulator for this Analog In channel has been initialized
     */
    @JsonProperty("&lt;accum_init")
    public boolean getAccumInit() {
        return accumInit;
    }

    public void setAccumInit(boolean accumInit) {
        this.accumInit = accumInit;
    }
    

    /**
     * Current accumulator value
     */
    @JsonProperty("&gt;accum_value")
    public Object getAccumValue() {
        return accumValue;
    }

    public void setAccumValue(Object accumValue) {
        this.accumValue = accumValue;
    }
    

    
    @JsonProperty("&gt;accum_count")
    public Object getAccumCount() {
        return accumCount;
    }

    public void setAccumCount(Object accumCount) {
        this.accumCount = accumCount;
    }
    

    
    @JsonProperty("&lt;accum_center")
    public Object getAccumCenter() {
        return accumCenter;
    }

    public void setAccumCenter(Object accumCenter) {
        this.accumCenter = accumCenter;
    }
    

    
    @JsonProperty("&lt;accum_deadband")
    public Object getAccumDeadband() {
        return accumDeadband;
    }

    public void setAccumDeadband(Object accumDeadband) {
        this.accumDeadband = accumDeadband;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AinData ainData = (AinData) o;
        return 
            Objects.equals(this.init, ainData.init) &&
            Objects.equals(this.avgBits, ainData.avgBits) &&
            Objects.equals(this.oversampleBits, ainData.oversampleBits) &&
            Objects.equals(this.voltage, ainData.voltage) &&
            Objects.equals(this.accumInit, ainData.accumInit) &&
            Objects.equals(this.accumValue, ainData.accumValue) &&
            Objects.equals(this.accumCount, ainData.accumCount) &&
            Objects.equals(this.accumCenter, ainData.accumCenter) &&
            Objects.equals(this.accumDeadband, ainData.accumDeadband);
    }

    @Override
    public int hashCode() {
        return Objects.hash(init, avgBits, oversampleBits, voltage, accumInit, accumValue, accumCount, accumCenter, accumDeadband);
    }

    @Override
    public String toString() {
        return "class AinData {\n" +
        
                "    init: " + toIndentedString(init) + "\n" +
                "    avgBits: " + toIndentedString(avgBits) + "\n" +
                "    oversampleBits: " + toIndentedString(oversampleBits) + "\n" +
                "    voltage: " + toIndentedString(voltage) + "\n" +
                "    accumInit: " + toIndentedString(accumInit) + "\n" +
                "    accumValue: " + toIndentedString(accumValue) + "\n" +
                "    accumCount: " + toIndentedString(accumCount) + "\n" +
                "    accumCenter: " + toIndentedString(accumCenter) + "\n" +
                "    accumDeadband: " + toIndentedString(accumDeadband) + "\n" +
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