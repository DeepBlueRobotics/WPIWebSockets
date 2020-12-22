# WPILib WebSocket Remote Endpoint API 1.0.0 documentation

API to route WPILib HAL calls over WebSockets.

## Table of Contents

* [Channels](#channels)

## Channels

### **wpilibws** Channel

General channel for WPILib WebSocket messages

#### `publish` Operation

##### Message

*Message envelope. Note that the "data" field contains a diff of the current state of a particular device. E.g. If only the "value" changes for a DIO device, then only the "<>value" field will be sent.*

###### Payload

| Name | Type | Description | Accepted values |
|-|-|-|-|
| type | string | Device Type (e.g. DIO/AO/AI/PWM/Encoder etc) | _Any_ |
| device | string | Device Identifier (usually channel) | _Any_ |
| data | oneOf | Payload for the message. Note that not all fields for a particular message will get sent. Only the diff of the current state of the particular device will be sent. | _Any_ |
| data.0 | object | DIO Data (type: DIO, device: channel number) | _Any_ |
| data.0.<init | boolean | Whether or not this DIO channel has been initialized | _Any_ |
| data.0.<>value | boolean | Current value of this DIO channel | _Any_ |
| data.0.<pulse_length | integer | - | _Any_ |
| data.0.<input | boolean | Whether or not this DIO channel has been initialized as an input | _Any_ |
| data.1 | object | Analog In Data (type: AI, device: channel number) | _Any_ |
| data.1.<init | boolean | Whether or not this Analog In channel has been initialized | _Any_ |
| data.1.<avg_bits | integer | - | _Any_ |
| data.1.<oversample_bits | integer | - | _Any_ |
| data.1.>voltage | number | The current input voltage of this Analog In channel | _Any_ |
| data.1.<accum_init | boolean | Whether or not the accumulator for this Analog In channel has been initialized | _Any_ |
| data.1.>accum_value | number | Current accumulator value | _Any_ |
| data.1.>accum_count | number | - | _Any_ |
| data.1.<accum_center | number | - | _Any_ |
| data.1.<accum_deadband | number | - | _Any_ |
| data.2 | object | Analog Out Data (type: AO, device: channel number) | _Any_ |
| data.2.<init | boolean | Whether or not this Analog Out channel has been initialized | _Any_ |
| data.2.<voltage | number | Current output voltage of this Analog Out channel | _Any_ |
| data.3 | object | DriverStation Data (type: DriverStation) | _Any_ |
| data.3.>enabled | boolean | Whether or not the driver station is enabled | _Any_ |
| data.3.>autonomous | boolean | Whether or not to go into autonomous mode (when enabled) | _Any_ |
| data.3.>test | boolean | Whether or not to go into test mode (when enabled) | _Any_ |
| data.3.>estop | boolean | Whether or not the robot has been emergency stopped | _Any_ |
| data.3.>fms | boolean | Whether or not the driver station is connected to FMS | _Any_ |
| data.3.>ds | boolean | Whether or not the Driver Station is connected to the robot | _Any_ |
| data.3.<match_time | number | Current elapsed match time | _Any_ |
| data.3.>station | string | Driver Station Position identifier | red1, red2, red3, blue1, blue2, blue3 |
| data.3.>new_data | boolean | Set whenever there is new DS data available | _Any_ |
| data.4 | object | Joystick Properties (type: Joystick, device: joystick number) | _Any_ |
| data.4.>buttons | arrayboolean | State of all buttons on this joystick | _Any_ |
| data.4.>povs | arraynumber | State of all POV switches on this joystick | _Any_ |
| data.4.>axes | arraynumber | State of all axes on this joystick | _Any_ |
| data.5 | object | Encoder Data (type: Encoder, device: FPGA encoder number) | _Any_ |
| data.5.<init | boolean | Whether or not this encoder channel is initialized | _Any_ |
| data.5.<channel_a | number | The Digital IO channel used for channel A | _Any_ |
| data.5.<channel_b | number | The Digital IO channel used for channel B | _Any_ |
| data.5.>count | integer | Current count of the encoder | _Any_ |
| data.5.>period | number | Current period of the encoder | _Any_ |
| data.5.<reset | boolean | Whether or not this encoder should be reset | _Any_ |
| data.5.<reverse_direction | boolean | Whether or not this encoder should reverse its counting direction | _Any_ |
| data.5.<samples_to_avg | integer | Number of samples to average over | _Any_ |
| data.6 | object | PWM Data (type: PWM, device: channel number) | _Any_ |
| data.6.<init | boolean | Whether or not this PWM channel is initialized | _Any_ |
| data.6.<speed | number | Speed value of this PWM channel | _Any_ |
| data.6.<position | number | Position value of this PWM channel | _Any_ |
| data.6.<raw | integer | Raw value of this PWM channel | _Any_ |
| data.6.<period_scale | number | Period scale factor | _Any_ |
| data.6.<zero_latch | boolean | - | _Any_ |
| data.7 | object | Relay data (type: Relay, device: channel number) | _Any_ |
| data.7.<init_fwd | boolean | Whether or not the forward direction of this relay is initialized | _Any_ |
| data.7.<init_rev | boolean | Whether or not the reverse direction of this relay is initialized | _Any_ |
| data.7.<fwd | boolean | Whether or not the forward direction of this relay is active | _Any_ |
| data.7.<rev | boolean | Whether or not the reverse direction of this relay is active | _Any_ |
| data.8 | object | RoboRIO Data (type: RoboRIO) | _Any_ |
| data.8.>fpga_button | boolean | Whether or not the FPGA button on the RoboRIO is active | _Any_ |
| data.8.>vin_voltage | number | Input voltage | _Any_ |
| data.8.>vin_current | number | Input Current | _Any_ |
| data.8.>6v_voltage | number | Voltage on the 6V rail | _Any_ |
| data.8.>6v_current | number | Current on the 6V rail | _Any_ |
| data.8.>6v_active | boolean | Whether or not the 6V rail is active | _Any_ |
| data.8.>6v_faults | number | Bitmask of faults on the 6V rail | _Any_ |
| data.8.>5v_voltage | number | Voltage on the 5V rail | _Any_ |
| data.8.>5v_current | number | Current on the 5V rail | _Any_ |
| data.8.>5v_active | boolean | Whether or not the 5V rail is active | _Any_ |
| data.8.>5v_faults | number | Bitmask of faults on the 5V rail | _Any_ |
| data.8.>3v3_voltage | number | Voltage on the 3.3V rail | _Any_ |
| data.8.>3v3_current | number | Current on the 3.3V rail | _Any_ |
| data.8.>3v3_active | boolean | Whether or not the 3.3V rail is active | _Any_ |
| data.8.>3v3_faults | number | Bitmask of faults on the 3.3V rail | _Any_ |

###### Examples of payload

```json
{
  "type": "PWM",
  "device": "1",
  "data": {
    "&lt;speed": 0.5
  }
}
```


```json
{
  "type": "DIO",
  "device": "3",
  "data": {
    "&lt;init": true
  }
}
```



#### `subscribe` Operation

##### Message

*Message envelope. Note that the "data" field contains a diff of the current state of a particular device. E.g. If only the "value" changes for a DIO device, then only the "<>value" field will be sent.*

###### Payload

| Name | Type | Description | Accepted values |
|-|-|-|-|
| type | string | Device Type (e.g. DIO/AO/AI/PWM/Encoder etc) | _Any_ |
| device | string | Device Identifier (usually channel) | _Any_ |
| data | oneOf | Payload for the message. Note that not all fields for a particular message will get sent. Only the diff of the current state of the particular device will be sent. | _Any_ |
| data.0 | object | DIO Data (type: DIO, device: channel number) | _Any_ |
| data.0.<init | boolean | Whether or not this DIO channel has been initialized | _Any_ |
| data.0.<>value | boolean | Current value of this DIO channel | _Any_ |
| data.0.<pulse_length | integer | - | _Any_ |
| data.0.<input | boolean | Whether or not this DIO channel has been initialized as an input | _Any_ |
| data.1 | object | Analog In Data (type: AI, device: channel number) | _Any_ |
| data.1.<init | boolean | Whether or not this Analog In channel has been initialized | _Any_ |
| data.1.<avg_bits | integer | - | _Any_ |
| data.1.<oversample_bits | integer | - | _Any_ |
| data.1.>voltage | number | The current input voltage of this Analog In channel | _Any_ |
| data.1.<accum_init | boolean | Whether or not the accumulator for this Analog In channel has been initialized | _Any_ |
| data.1.>accum_value | number | Current accumulator value | _Any_ |
| data.1.>accum_count | number | - | _Any_ |
| data.1.<accum_center | number | - | _Any_ |
| data.1.<accum_deadband | number | - | _Any_ |
| data.2 | object | Analog Out Data (type: AO, device: channel number) | _Any_ |
| data.2.<init | boolean | Whether or not this Analog Out channel has been initialized | _Any_ |
| data.2.<voltage | number | Current output voltage of this Analog Out channel | _Any_ |
| data.3 | object | DriverStation Data (type: DriverStation) | _Any_ |
| data.3.>enabled | boolean | Whether or not the driver station is enabled | _Any_ |
| data.3.>autonomous | boolean | Whether or not to go into autonomous mode (when enabled) | _Any_ |
| data.3.>test | boolean | Whether or not to go into test mode (when enabled) | _Any_ |
| data.3.>estop | boolean | Whether or not the robot has been emergency stopped | _Any_ |
| data.3.>fms | boolean | Whether or not the driver station is connected to FMS | _Any_ |
| data.3.>ds | boolean | Whether or not the Driver Station is connected to the robot | _Any_ |
| data.3.<match_time | number | Current elapsed match time | _Any_ |
| data.3.>station | string | Driver Station Position identifier | red1, red2, red3, blue1, blue2, blue3 |
| data.3.>new_data | boolean | Set whenever there is new DS data available | _Any_ |
| data.4 | object | Joystick Properties (type: Joystick, device: joystick number) | _Any_ |
| data.4.>buttons | arrayboolean | State of all buttons on this joystick | _Any_ |
| data.4.>povs | arraynumber | State of all POV switches on this joystick | _Any_ |
| data.4.>axes | arraynumber | State of all axes on this joystick | _Any_ |
| data.5 | object | Encoder Data (type: Encoder, device: FPGA encoder number) | _Any_ |
| data.5.<init | boolean | Whether or not this encoder channel is initialized | _Any_ |
| data.5.<channel_a | number | The Digital IO channel used for channel A | _Any_ |
| data.5.<channel_b | number | The Digital IO channel used for channel B | _Any_ |
| data.5.>count | integer | Current count of the encoder | _Any_ |
| data.5.>period | number | Current period of the encoder | _Any_ |
| data.5.<reset | boolean | Whether or not this encoder should be reset | _Any_ |
| data.5.<reverse_direction | boolean | Whether or not this encoder should reverse its counting direction | _Any_ |
| data.5.<samples_to_avg | integer | Number of samples to average over | _Any_ |
| data.6 | object | PWM Data (type: PWM, device: channel number) | _Any_ |
| data.6.<init | boolean | Whether or not this PWM channel is initialized | _Any_ |
| data.6.<speed | number | Speed value of this PWM channel | _Any_ |
| data.6.<position | number | Position value of this PWM channel | _Any_ |
| data.6.<raw | integer | Raw value of this PWM channel | _Any_ |
| data.6.<period_scale | number | Period scale factor | _Any_ |
| data.6.<zero_latch | boolean | - | _Any_ |
| data.7 | object | Relay data (type: Relay, device: channel number) | _Any_ |
| data.7.<init_fwd | boolean | Whether or not the forward direction of this relay is initialized | _Any_ |
| data.7.<init_rev | boolean | Whether or not the reverse direction of this relay is initialized | _Any_ |
| data.7.<fwd | boolean | Whether or not the forward direction of this relay is active | _Any_ |
| data.7.<rev | boolean | Whether or not the reverse direction of this relay is active | _Any_ |
| data.8 | object | RoboRIO Data (type: RoboRIO) | _Any_ |
| data.8.>fpga_button | boolean | Whether or not the FPGA button on the RoboRIO is active | _Any_ |
| data.8.>vin_voltage | number | Input voltage | _Any_ |
| data.8.>vin_current | number | Input Current | _Any_ |
| data.8.>6v_voltage | number | Voltage on the 6V rail | _Any_ |
| data.8.>6v_current | number | Current on the 6V rail | _Any_ |
| data.8.>6v_active | boolean | Whether or not the 6V rail is active | _Any_ |
| data.8.>6v_faults | number | Bitmask of faults on the 6V rail | _Any_ |
| data.8.>5v_voltage | number | Voltage on the 5V rail | _Any_ |
| data.8.>5v_current | number | Current on the 5V rail | _Any_ |
| data.8.>5v_active | boolean | Whether or not the 5V rail is active | _Any_ |
| data.8.>5v_faults | number | Bitmask of faults on the 5V rail | _Any_ |
| data.8.>3v3_voltage | number | Voltage on the 3.3V rail | _Any_ |
| data.8.>3v3_current | number | Current on the 3.3V rail | _Any_ |
| data.8.>3v3_active | boolean | Whether or not the 3.3V rail is active | _Any_ |
| data.8.>3v3_faults | number | Bitmask of faults on the 3.3V rail | _Any_ |

###### Examples of payload

```json
{
  "type": "PWM",
  "device": "1",
  "data": {
    "&lt;speed": 0.5
  }
}
```


```json
{
  "type": "DIO",
  "device": "3",
  "data": {
    "&lt;init": true
  }
}
```




