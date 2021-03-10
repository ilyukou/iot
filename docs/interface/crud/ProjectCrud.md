## Project Controller
### Request mapping <em>/crud/project</em>

___
### Create project
##### Request /crud/project
Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | - | - | [ProjectForm](../../model/project/ProjectForm.md) | - 

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [ProjectDto](../../model/project/ProjectDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___
### Update project
##### Request /crud/project/{id}
Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
PUT | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of project | [ProjectFormUpdate](../../model/project/ProjectFormUpdate.md) | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [ProjectDto](../../model/project/ProjectDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get project
##### Request /crud/project/{id}
Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of project | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [ProjectDto](../../model/project/ProjectDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___
### Delete project
##### Request /crud/project/{id}
Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
DELETE | [Authorization](../../model/user/AuthenticationUser.md) | id | ID of project | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.