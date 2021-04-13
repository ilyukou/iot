## Resource Crud API Interface. Request mapping <em>/crud/resource</em>

___

### Add resource

##### Request /crud/resource

Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
POST | [Authorization](../../model/user/AuthenticationUser.md) | - | - | MultipartFile | max file size is 5mb. support types: jpg, png, jpeg

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [ResourceDto](../../model/resource/ResourceDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Get resource

##### Request /crud/resource/{filename}

Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | [ResourceDto#filename](../../model/resource/ResourceDto.md) | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | byte[] | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___