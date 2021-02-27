#### Device
Filed name | Type | Note
------------ | ------------- | -------------
id | Long | Device id
name | String | Device name.
state | String | Current Device state.
states | Array of String | Array of possible states
token | String | Device token.
activity | Long | UTC time last activity
update | Long |  UTC last update time
create | Long | UTC create time

```json
{
  "id" : 1,
  "name" : "name",
  "state" : "off",
  "states" : ["off","on"],
  "token" : "123Qwerty",
  "activity": 1614413325798,
  "update": 1614413325798,
  "create": 1614413325798
}

```
