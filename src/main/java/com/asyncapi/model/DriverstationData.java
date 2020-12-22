package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

/**
 * DriverStation Data (type: DriverStation)
 */
public class DriverstationData implements WpilibwsMsgPayload.OneOfDioDataAinDataAoutDataDriverstationDataJoystickDataEncoderDataPwmDataRelayDataRoborioData {
    
    private @Valid boolean enabled;
    
    private @Valid boolean autonomous;
    
    private @Valid boolean test;
    
    private @Valid boolean estop;
    
    private @Valid boolean fms;
    
    private @Valid boolean ds;
    
    private @Valid Object matchTime;
    
    public enum StationEnum {
        
        RED1(String.valueOf("red1")),
        
        RED2(String.valueOf("red2")),
        
        RED3(String.valueOf("red3")),
        
        BLUE1(String.valueOf("blue1")),
        
        BLUE2(String.valueOf("blue2")),
        
        BLUE3(String.valueOf("blue3"));
        
        private String value;

        StationEnum (String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StationEnum fromValue(String value) {
            for ( StationEnum b :  StationEnum.values()) {
                if (Objects.equals(b.value, value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    private @Valid StationEnum station;
    
    private @Valid boolean newData;
    

    

    /**
     * Whether or not the driver station is enabled
     */
    @JsonProperty("&gt;enabled")
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    

    /**
     * Whether or not to go into autonomous mode (when enabled)
     */
    @JsonProperty("&gt;autonomous")
    public boolean getAutonomous() {
        return autonomous;
    }

    public void setAutonomous(boolean autonomous) {
        this.autonomous = autonomous;
    }
    

    /**
     * Whether or not to go into test mode (when enabled)
     */
    @JsonProperty("&gt;test")
    public boolean getTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
    

    /**
     * Whether or not the robot has been emergency stopped
     */
    @JsonProperty("&gt;estop")
    public boolean getEstop() {
        return estop;
    }

    public void setEstop(boolean estop) {
        this.estop = estop;
    }
    

    /**
     * Whether or not the driver station is connected to FMS
     */
    @JsonProperty("&gt;fms")
    public boolean getFms() {
        return fms;
    }

    public void setFms(boolean fms) {
        this.fms = fms;
    }
    

    /**
     * Whether or not the Driver Station is connected to the robot
     */
    @JsonProperty("&gt;ds")
    public boolean getDs() {
        return ds;
    }

    public void setDs(boolean ds) {
        this.ds = ds;
    }
    

    /**
     * Current elapsed match time
     */
    @JsonProperty("&lt;match_time")
    public Object getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Object matchTime) {
        this.matchTime = matchTime;
    }
    

    /**
     * Driver Station Position identifier
     */
    @JsonProperty("&gt;station")
    public StationEnum getStation() {
        return station;
    }

    public void setStation(StationEnum station) {
        this.station = station;
    }
    

    /**
     * Set whenever there is new DS data available
     */
    @JsonProperty("&gt;new_data")
    public boolean getNewData() {
        return newData;
    }

    public void setNewData(boolean newData) {
        this.newData = newData;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DriverstationData driverstationData = (DriverstationData) o;
        return 
            Objects.equals(this.enabled, driverstationData.enabled) &&
            Objects.equals(this.autonomous, driverstationData.autonomous) &&
            Objects.equals(this.test, driverstationData.test) &&
            Objects.equals(this.estop, driverstationData.estop) &&
            Objects.equals(this.fms, driverstationData.fms) &&
            Objects.equals(this.ds, driverstationData.ds) &&
            Objects.equals(this.matchTime, driverstationData.matchTime) &&
            Objects.equals(this.station, driverstationData.station) &&
            Objects.equals(this.newData, driverstationData.newData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, autonomous, test, estop, fms, ds, matchTime, station, newData);
    }

    @Override
    public String toString() {
        return "class DriverstationData {\n" +
        
                "    enabled: " + toIndentedString(enabled) + "\n" +
                "    autonomous: " + toIndentedString(autonomous) + "\n" +
                "    test: " + toIndentedString(test) + "\n" +
                "    estop: " + toIndentedString(estop) + "\n" +
                "    fms: " + toIndentedString(fms) + "\n" +
                "    ds: " + toIndentedString(ds) + "\n" +
                "    matchTime: " + toIndentedString(matchTime) + "\n" +
                "    station: " + toIndentedString(station) + "\n" +
                "    newData: " + toIndentedString(newData) + "\n" +
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