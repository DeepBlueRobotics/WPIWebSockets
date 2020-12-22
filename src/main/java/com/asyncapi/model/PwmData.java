package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * PWM Data (type: PWM, device: channel number)
 */
public class PwmData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean init;
    
    private @Valid Object speed;
    
    private @Valid Object position;
    
    private @Valid int raw;
    
    private @Valid Object periodScale;
    
    private @Valid boolean zeroLatch;
    

    

    /**
     * Whether or not this PWM channel is initialized
     */
    @JsonProperty("&lt;init")
    public boolean getInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
    

    /**
     * Speed value of this PWM channel
     */
    @JsonProperty("&lt;speed")@Min(-1)@Max(1)
    public Object getSpeed() {
        return speed;
    }

    public void setSpeed(Object speed) {
        this.speed = speed;
    }
    

    /**
     * Position value of this PWM channel
     */
    @JsonProperty("&lt;position")@Max(1)
    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }
    

    /**
     * Raw value of this PWM channel
     */
    @JsonProperty("&lt;raw")@Max(255)
    public int getRaw() {
        return raw;
    }

    public void setRaw(int raw) {
        this.raw = raw;
    }
    

    /**
     * Period scale factor
     */
    @JsonProperty("&lt;period_scale")
    public Object getPeriodScale() {
        return periodScale;
    }

    public void setPeriodScale(Object periodScale) {
        this.periodScale = periodScale;
    }
    

    
    @JsonProperty("&lt;zero_latch")
    public boolean getZeroLatch() {
        return zeroLatch;
    }

    public void setZeroLatch(boolean zeroLatch) {
        this.zeroLatch = zeroLatch;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PwmData pwmData = (PwmData) o;
        return 
            Objects.equals(this.init, pwmData.init) &&
            Objects.equals(this.speed, pwmData.speed) &&
            Objects.equals(this.position, pwmData.position) &&
            Objects.equals(this.raw, pwmData.raw) &&
            Objects.equals(this.periodScale, pwmData.periodScale) &&
            Objects.equals(this.zeroLatch, pwmData.zeroLatch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(init, speed, position, raw, periodScale, zeroLatch);
    }

    @Override
    public String toString() {
        return "class PwmData {\n" +
        
                "    init: " + toIndentedString(init) + "\n" +
                "    speed: " + toIndentedString(speed) + "\n" +
                "    position: " + toIndentedString(position) + "\n" +
                "    raw: " + toIndentedString(raw) + "\n" +
                "    periodScale: " + toIndentedString(periodScale) + "\n" +
                "    zeroLatch: " + toIndentedString(zeroLatch) + "\n" +
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