## Validation Controller
### Request mapping <em>/validation</em>

___
### Validation data for {entity}
##### Request /validation/{entity}
Method | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | entity | String entity - entity name | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Validation](docs/model/Validation.md) | -
400 | [ExceptionResponse](docs/model/exception/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
500 | [ExceptionResponse](docs/model/exception/ExceptionResponse.md) | Internal server error occurred.


