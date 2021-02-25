## Const Controller
### Request mapping <em>/const</em>

___
### Get const field for application
##### Request /const
Method | Parameter | Description | Restriction | Body | Description | Restriction
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
GET | - | - | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Map<String, String> | OK
400 | [ExceptionResponse](../model/ExceptionResponse.md) | Validation error or request body is an invalid JSON or cannot be parsed
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

Example Map<String, String>:

```
{
    "by.grsu.iot.service.device.per-page": "25",
    "by.grsu.iot.service.project.per-page": "10"
}
```