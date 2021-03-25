## Device Crud API Interface. Request mapping <em>/crud/device</em>

___

### Create device

##### Request /crud/device

Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
POST | [Authorization](../../model/user/AuthenticationUser.md) | - | - | [DeviceForm](../../model/device/DeviceForm.md)

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [DeviceDto](../../model/device/DeviceDto.md) | -
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Bad request
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found project
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Update device

##### Request /crud/device/{id}

Method | Header  | Parameter | Description | Body | Description
------------ | ------------- | ------------- | ------------- | ------------- | -------------
PUT | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of device | [DeviceFormUpdate](../../model/device/DeviceFormUpdate.md)

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [DeviceDto](../../model/device/DeviceDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Get device

##### Request /crud/device/{id}

Method | Header  | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of device | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [DeviceDto](../../model/device/DeviceDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Delete device

##### Request /crud/device{id}

Method | Header | Parameter | Description | Body | Description
------------ | ------------- | ------------- | ------------- | ------------- | -------------
DELETE | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of device | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Device
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.
