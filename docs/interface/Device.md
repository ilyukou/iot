## Device Controller. Request mapping <em>/device</em>

___
### Create device
##### Request /device
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
POST | [User authorization token](../model/AuthenticationUser.md) |- | - | - | [DeviceForm](../model/DeviceForm.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | id | Long id – ID of created device
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Bad request
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Update device
##### Request /device/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
PUT | [User authorization token](../model/AuthenticationUser.md) | id | ID of device | - | [DeviceForm](../model/DeviceForm.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | -
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get device
##### Request /device/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [User authorization token](../model/AuthenticationUser.md) | id | ID of device | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Device](../model/Device.md) | -
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Delete device
##### Request /device/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [User authorization token](../model/AuthenticationUser.md) | id | ID of device | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Device](../model/Device.md) | -
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get device state
##### Request /device/state/{token}?state=yourState
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | - | state, token | String state - device state, default "off"; String token - device token | - | - | - | -

If state not equals state in server - return state from server, else wait 30 second (each second take fresh data from the server and compare it again). After 30 second return TimeOutMessage.

##### Response when state not equals state in server
Code | Body | Description
------------ | ------------- | -------------
200 | [HttpMessageWrapper < DeviceState >](../model/HttpMessageWrapper.md) | T body [DeviceState](../model/DeviceState.md), HttpMessageWrapper#status - 'ok', HttpMessageWrapper#message - "ok"
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

##### Response when state not equals state in server
Code | Body | Description
------------ | ------------- | -------------
200 | [HttpMessageWrapper < empty >](../model/HttpMessageWrapper.md) | T body - empty, HttpMessageWrapper#status - 'info', HttpMessageWrapper#message - "Time Out."
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Set device state
##### Request /device/state/{token}?state=off
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | - | token, state | token of device, new device state | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [DeviceState](../model/DeviceState.md) | -
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.
