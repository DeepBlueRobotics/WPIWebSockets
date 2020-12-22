package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Encoder Data (type: Encoder, device: FPGA encoder number)
 */
public class EncoderData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean init;
    
    private @Valid Object channelA;
    
    private @Valid Object channelB;
    
    private @Valid int count;
    
    private @Valid Object period;
    
    private @Valid boolean reset;
    
    private @Valid boolean reverseDirection;
    
    private @Valid int samplesToAvg;
    

    

    /**
     * Whether or not this encoder channel is initialized
     */
    @JsonProperty("&lt;init")
    public boolean getInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
    

    /**
     * The Digital IO channel used for channel A
     */
    @JsonProperty("&lt;channel_a")
    public Object getChannelA() {
        return channelA;
    }

    public void setChannelA(Object channelA) {
        this.channelA = channelA;
    }
    

    /**
     * The Digital IO channel used for channel B
     */
    @JsonProperty("&lt;channel_b")
    public Object getChannelB() {
        return channelB;
    }

    public void setChannelB(Object channelB) {
        this.channelB = channelB;
    }
    

    /**
     * Current count of the encoder
     */
    @JsonProperty("&gt;count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    

    /**
     * Current period of the encoder
     */
    @JsonProperty("&gt;period")
    public Object getPeriod() {
        return period;
    }

    public void setPeriod(Object period) {
        this.period = period;
    }
    

    /**
     * Whether or not this encoder should be reset
     */
    @JsonProperty("&lt;reset")
    public boolean getReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }
    

    /**
     * Whether or not this encoder should reverse its counting direction
     */
    @JsonProperty("&lt;reverse_direction")
    public boolean getReverseDirection() {
        return reverseDirection;
    }

    public void setReverseDirection(boolean reverseDirection) {
        this.reverseDirection = reverseDirection;
    }
    

    /**
     * Number of samples to average over
     */
    @JsonProperty("&lt;samples_to_avg")
    public int getSamplesToAvg() {
        return samplesToAvg;
    }

    public void setSamplesToAvg(int samplesToAvg) {
        this.samplesToAvg = samplesToAvg;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EncoderData encoderData = (EncoderData) o;
        return 
            Objects.equals(this.init, encoderData.init) &&
            Objects.equals(this.channelA, encoderData.channelA) &&
            Objects.equals(this.channelB, encoderData.channelB) &&
            Objects.equals(this.count, encoderData.count) &&
            Objects.equals(this.period, encoderData.period) &&
            Objects.equals(this.reset, encoderData.reset) &&
            Objects.equals(this.reverseDirection, encoderData.reverseDirection) &&
            Objects.equals(this.samplesToAvg, encoderData.samplesToAvg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(init, channelA, channelB, count, period, reset, reverseDirection, samplesToAvg);
    }

    @Override
    public String toString() {
        return "class EncoderData {\n" +
        
                "    init: " + toIndentedString(init) + "\n" +
                "    channelA: " + toIndentedString(channelA) + "\n" +
                "    channelB: " + toIndentedString(channelB) + "\n" +
                "    count: " + toIndentedString(count) + "\n" +
                "    period: " + toIndentedString(period) + "\n" +
                "    reset: " + toIndentedString(reset) + "\n" +
                "    reverseDirection: " + toIndentedString(reverseDirection) + "\n" +
                "    samplesToAvg: " + toIndentedString(samplesToAvg) + "\n" +
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