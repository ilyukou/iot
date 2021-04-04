package by.grsu.iot.model.dto.user;

import by.grsu.iot.model.annotation.RequiredField;
import by.grsu.iot.model.dto.DataTransferObject;

public class ActivateUser implements DataTransferObject {

    @RequiredField
    private String username;

    @RequiredField
    private Integer code;

    public ActivateUser(String username, Integer code) {
        this.username = username;
        this.code = code;
    }

    public ActivateUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
