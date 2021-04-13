package by.grsu.iot.model.dto.email;

import by.grsu.iot.model.annotation.RequiredField;
import by.grsu.iot.model.async.EmailCodeType;
import by.grsu.iot.model.dto.DataTransferObject;

import javax.validation.constraints.Email;

public class SendEmailCodeRequest implements DataTransferObject {

    @Email(message = "Email should be valid")
    @RequiredField
    private String address;

    @RequiredField
    private EmailCodeType type;

    public SendEmailCodeRequest(String address, EmailCodeType type) {
        this.address = address;
        this.type = type;
    }

    public SendEmailCodeRequest() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EmailCodeType getType() {
        return type;
    }

    public void setType(EmailCodeType type) {
        this.type = type;
    }
}
