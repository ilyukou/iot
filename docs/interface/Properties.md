## Properties Controller
### Request mapping <em>/properties</em>

___
### Get const field for application
##### Request /properties
Method | Parameter | Description | Body | Description
------------ | ------------- | ------------- | ------------- | ------------- | 
GET | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Map<String, String> | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

Example Map<String, String>:

```
{
    "device.per-page": 25,
    "per-page": 10
}
```