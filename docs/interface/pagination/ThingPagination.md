## Device Pagination Interface

### Request mapping <em>/pagination/device/</em>

___

### Get pagination info of things in project

##### Request /pagination/thing/{project}

Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | project | project - Project ID | - | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | [PaginationInfo](../../model/pagination/PaginationInfo.md) | -
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found Project
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___

### Get thing page

##### Request /pagination/thing/{project}/{count}

Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | project, count | project - Project ID. count - Count is a number of required page (Number page start from 1). Page size is const | - | -

##### Response

Code | Body | Description
------------ | ------------- | -------------
200 | Array of [ThingWrapper](../../model/ThingWrapper.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found Project
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.