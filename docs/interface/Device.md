## Device API Interface. Request mapping <em>/device</em>

___
### Create device
##### Request /device
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
POST | [AuthenticationUser#token](../model/AuthenticationUser.md) |- | - | - | [DeviceForm](../model/DeviceForm.md) | Device#states size min 2. Device#state includes Device#states. | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | id | Long id – ID of created device
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Bad request
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Update device
##### Request /device/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
PUT | [AuthenticationUser#token](../model/AuthenticationUser.md) | id | ID of device | - | [DeviceForm](../model/DeviceForm.md) | Device#states size min 2. Device#state includes Device#states. | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get device
##### Request /device/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | id | ID of device | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Device](../model/Device.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Delete device
##### Request /device/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | id | ID of device | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Device](../model/Device.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.
