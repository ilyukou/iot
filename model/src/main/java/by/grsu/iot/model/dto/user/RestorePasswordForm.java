package by.grsu.iot.model.dto.user;

import by.grsu.iot.model.annotation.RequiredField;

public class RestorePasswordForm extends AuthenticationRequest {

    @RequiredField
    private Integer code;

    public RestorePasswordForm(String username, String password, Integer code) {
        super(username, password);
        this.code = code;
    }

    public RestorePasswordForm() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
