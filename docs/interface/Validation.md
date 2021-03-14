## Validation Controller
### Request mapping <em>/validation</em>

___
### Validation data for all model
##### Request /validation
Method | Parameter | Description | Body | Description
------------ | ------------- | ------------- | ------------- | ------------- 
GET | - | - | - | -

##### Response
Code | Body | Description
------------ | ------------- | -------------
200 | Map<String, Map<String, [ValidationRule](../model/ValidationRule.md)> | First String key is a Model name, second a field
500 | [ExceptionResponse](../model/ExceptionResponse.md) | Internal server error occurred.

##### Response Body Example
```json
{
  "DeviceForm": {
    "name": {
      "min": 2,
      "max": 16,
      "required": true,
      "spaceAllowed": true
    },
    "state": {
      "min": 2,
      "max": 16,
      "required": true,
      "spaceAllowed": false
    },
    "states": {
      "min": 2,
      "max": 16,
      "required": true,
      "spaceAllowed": false
    }
  },
  "ProjectForm": {
    "name": {
      "min": 4,
      "max": 16,
      "required": true,
      "spaceAllowed": true
    },
    "title": {
      "min": 0,
      "max": 128,
      "required": false,
      "spaceAllowed": true
    }
  }
}

```
