## Device State Interface. Request mapping <em>/device</em>

___
### Get device state
##### Request /deviceStateDto/{token}
Method | Header | Parameter | Description | Body | Description
------------ | ------------- | -------------  | ------------- | ------------- | -------------
GET | - | token | String token - device token | - | - | -

Long-polling method

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
##### Request /deviceStateDto/{token}?state=off
Method | Header | Parameter | Description | Body | Description
------------ | ------------- | -------------  | ------------- | ------------- | -------------
POST | - | state, token | String state - device state, default "off"; String token - device token | - | - | -

Long-polling method

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [HttpMessageWrapper < DeviceState >](../model/HttpMessageWrapper.md) | Response when state changed. T body [DeviceState](../model/DeviceState.md), HttpMessageWrapper#status - 'ok', HttpMessageWrapper#message - "ok"
204 | [HttpMessageWrapper < empty >](../model/HttpMessageWrapper.md) | Response when state not changed. T body - empty, HttpMessageWrapper#status - 'info', HttpMessageWrapper#message - "Time Out."
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.