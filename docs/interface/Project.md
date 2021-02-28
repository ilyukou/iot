## Project Controller
### Request mapping <em>/project</em>

___
### Create project
##### Request /project
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
POST | [AuthenticationUser#token](../model/AuthenticationUser.md) |- | - | - | [ProjectForm](../model/ProjectForm.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Project](../model/Project.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Update project
##### Request /project/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
PUT | [AuthenticationUser#token](../model/AuthenticationUser.md) | id | ID of project | - | [ProjectForm](../model/ProjectForm.md) | All field OPTIONAL. Method will be update only not null field. | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Project](../model/Project.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get project
##### Request /project/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | id | ID of project | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Project](../model/Project.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Delete project
##### Request /project/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
DELETE | [AuthenticationUser#token](../model/AuthenticationUser.md) | id | ID of project | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get pagination info of project in user
##### Request /project/pagination?username=String
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | username |  String username is OPTIONAL field. If not present return your repositories. If present return {username} repositories.  | - | - | - | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [PaginationInfo](../model/PaginationInfo.md) | -
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found User with such username
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get project page
##### Request /project/page?count=1&username=String
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | count, username | Count is a number of required page (Number page start from 1). Page size is const; String username is OPTIONAL field. If not present return your repositories. If present return {username} repositories.  | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Array of [Project](../model/Project.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found User
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get pagination info of things in project
##### Request /project/thing/pagination/{project}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | project | Project - project id | - | - | - | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [PaginationInfo](../model/PaginationInfo.md) | -
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found Project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get device page
##### Request /project/thing/page/{project}?count=1
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | project, count | Project - project id. Count is a number of required page (Number page start from 1). Page size is const  | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Array of [ThingWrapper](../model/ThingWrapper.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../model/ExceptionResponse.md) | Not found Project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.