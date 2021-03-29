## Sensor Crud API Interface. Request mapping <em>/crud/sensor</em>

___

### Create sensor

##### Request /crud/sensor

Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
POST | [Authorization](../../model/user/AuthenticationUser.md) | - | - | [SensorForm](../../model/sensor/SensorForm.md)

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [SensorDto](../../model/sensor/SensorDto.md) | -
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Bad request
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found project
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Update sensor

##### Request /crud/sensor/{id}

Method | Header  | Parameter | Description | Body | Description
------------ | ------------- | ------------- | ------------- | ------------- | -------------
PUT | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of device | [SensorFormUpdate](../../model/sensor/SensorFormUpdate.md)

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [SensorDto](../../model/sensor/SensorDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Get sensor

##### Request /crud/sensor/{id}

Method | Header  | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of device | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [SensorDto](../../model/sensor/SensorDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Delete sensor

##### Request /crud/sensor{id}

Method | Header | Parameter | Description | Body | Description
------------ | ------------- | ------------- | ------------- | ------------- | -------------
DELETE | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of sensor | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.
