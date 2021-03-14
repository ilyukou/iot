## Project Pagination Interface
### Request mapping <em>/pagination/project"</em>
___
### Get pagination info of project in user
##### Request /pagination/project?username=String
Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | username | String username is OPTIONAL field. If not present return your repositories. If present return {username} repositories.| - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [PaginationInfo](../../model/pagination/PaginationInfo.md) | -
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found User with such username
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___
### Get project page
##### Request /pagination/project/{count}?username=USERNAME
Method | Header | Parameter | Description | Body | Description
------------ |  ------------- | ------------- | ------------- | ------------- | -------------
GET | [Authorization](../../model/user/AuthenticationUser.md) | count, username  | Count is a number of required page (Number page start from 1). Page size is const; String username is OPTIONAL field. If not present return your repositories. If present return {username} repositories. | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Array of [ProjectDto](../../model/project/ProjectDto.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed.
403 | [ExceptionResponse](../../model/ExceptionResponse.md) | Forbidden. Not access for this operation.
404 | [ExceptionResponse](../../model/ExceptionResponse.md) | Not found User
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.