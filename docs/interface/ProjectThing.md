## Project Thing Interface. Request mapping <em>/projectThings</em>
___
### Get project things - device, sensor and others.
##### Request /projectThings/{id}
Method | Header | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | [AuthenticationUser#token](../model/AuthenticationUser.md) | id | ID of project | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Array of [ProjectThing](../model/ProjectThing.md) | -
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
403 | [ExceptionResponse](../model/ExceptionResponse.md) | Forbidden. Not access for this operation
404 | - | Not found Project
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.