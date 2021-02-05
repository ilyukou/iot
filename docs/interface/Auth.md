## Authorization Interface. Request mapping <em>/auth</em>
##### That interface serving for sign in and sign up

___
### Sign In
##### Request /auth/signIn
Method | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
POST | - | - | - | [AuthenticationRequest](docs/model/AuthenticationRequest.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [AuthenticationUser](docs/model/AuthenticationUser.md) | Validation error or request body is an invalid JSON or cannot be parsed
400 | [ExceptionResponse](docs/model/exception/ExceptionResponse.md) | Bad request
404 | - | Not found User with such username and password
500 | [ExceptionResponse](docs/model/exception/ExceptionResponse.md) | Internal server error occurred.

___
### Sign Up
##### Request /auth/signUp
Method | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
POST | - | - | - | [RegistrationRequest](docs/model/RegistrationRequest.md) | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | - | OK
400 | [ExceptionResponse](docs/model/exception/ExceptionResponse.md) | Bad request
500 | [ExceptionResponse](docs/model/exception/ExceptionResponse.md) | Internal server error occurred.
