## Sensor Value API Interface. Request mapping <em>/sensor/value</em>

___

### Add value

##### Request /sensor/value/{token}

Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
POST | - | token | [SensorDto#token](../model/sensor/SensorDto.md) | [SensorValue](../model/sensor/SensorValue.md) | time is optional field.

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Bad request
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found sensor
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___

### Get last value

##### Request /sensor/value/one/{token}

Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
GET | - | token | [SensorDto#token](../model/sensor/SensorDto.md) | - | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [SensorValue](../model/sensor/SensorValue.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Bad request
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found sensor
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___

### Get piece of values

##### Request /sensor/value/{token}?from=FROM_TIME&to=TO_TIME

Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
GET | - | token, from, to | token - [SensorDto#token](../model/sensor/SensorDto.md), from - optional field, to - optional field  | - | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | Array of [SensorValue](../model/sensor/SensorValue.md) | OK. Array size is ABOUT 100.
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Bad request
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found sensor
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___