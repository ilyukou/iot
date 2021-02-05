## Validation Controller
### Request mapping <em>/validation</em>

___
### Validation data for entity
##### Request /validation?entity=entityName
Method | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | entity | String entity - entity name | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | [Validation](../model/Validation.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

___
### Validation data for all entity
##### Request /validation/all
Method | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | - | - | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Array of [Validation](../model/Validation.md) | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.


