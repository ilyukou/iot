#### Device
Filed name | Type | Note
------------ | ------------- | -------------
id | Long | Device id
name | String | Device name.
state | String | Current Device state.
states | Array of String | Array of possible states
token | String | Device token.
activity | String | Time last activity
update | String | Last update time
create | String | Create time

```json
{
  "id" : 1,
  "name" : "name",
  "state" : "off",
  "states" : ["off","on"],
  "token" : "123Qwerty",
  "activity": "2021-02-24T09:43:12.000+00:00",
  "update": "2021-02-24T09:43:12.000+00:00",
  "create": "2021-02-24T09:43:12.000+00:00"
}

```
