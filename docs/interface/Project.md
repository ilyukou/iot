## Project Controller
### Request mapping <em>/project</em>

___
### Create project
##### Request /project
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
POST | [User authorization token](../model/AuthenticationUser.md) |- | - | - | [ProjectForm](../model/ProjectForm.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | id | Long id – ID of created project
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Update project
##### Request /project/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
PUT | [User authorization token](../model/AuthenticationUser.md) | id | ID of project | - | [ProjectForm](../model/ProjectForm.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get project
##### Request /project/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [User authorization token](../model/AuthenticationUser.md) | id | ID of project | - | - | - | -

##### Response
Code | Body | Description
------------ | -------------
200 | [Project](../model/Project.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get project page
##### Request /project/page?count=1&username=String
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [User authorization token](../model/AuthenticationUser.md) | count, username | int count, String username | username is OPTIONAL field. If not present return your repositories. If present return {username} repositories | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Array of [Project](../model/Project.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Delete project
##### Request /project/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [User authorization token](../model/AuthenticationUser.md) | id | ID of project | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
401 | [ExceptionResponse](../model/ExceptionResponse.md) | Unauthorized.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

