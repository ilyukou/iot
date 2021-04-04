package by.grsu.iot.model.async;

import java.io.Serializable;

public class EmailCode implements Serializable {

    private String address;
    private Integer code;
    private EmailCodeType type;

    public EmailCode(String address, Integer code, EmailCodeType type) {

        this.address = address;
        this.code = code;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public Integer getCode() {
        return code;
    }

    public EmailCodeType getType() {
        return type;
    }
}
