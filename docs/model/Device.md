#### Device
Filed name | Type | Note
------------ | ------------- | -------------
id | Long | Device id
project | Long | [Project#id](Project.md)
name | String | Device name.
state | String | Current Device state.
states | Array of String | Array of possible states
token | String | Device token.

```json
{
  "id" : 1,
  "project" : 1,
  "name" : "name",
  "state" : "off",
  "states" : ["off","on"],
  "token" : "123Qwerty"
}

```
