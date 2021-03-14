package by.grsu.iot.service.domain;

import java.util.Date;

public class BadRequestExceptionResponse extends ExceptionResponse {

    private String field;

    public BadRequestExceptionResponse(Date time, String message, String field) {
        super(time, message);
        this.field = field;
    }

    public BadRequestExceptionResponse(Long time, String message, String field) {
        super(time, message);
        this.field = field;
    }

    public BadRequestExceptionResponse() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
