## User API Interface. Request mapping <em>/crud/user</em>
##### That interface serving for sign in and sign up

___
### Sign In
##### Request /crud/user
Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
GET | - | - | - | [AuthenticationRequest](../../model/user/AuthenticationRequest.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [AuthenticationUser](../../model/user/AuthenticationUser.md) | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
404 | - | Not found User with such username and password
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.

___
### Sign Up
##### Request /crud/user
Method | Header | Parameter | Description | Body | Description
------------ | -------------  | ------------- | ------------- | ------------- | -------------
POST | - | - | - | [RegistrationRequest](../../model/user/RegistrationRequest.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](../../model/ExceptionResponse.md) | Bad request
500 | [ExceptionResponse](../../model/ExceptionResponse.md) | Internal server error occurred.
