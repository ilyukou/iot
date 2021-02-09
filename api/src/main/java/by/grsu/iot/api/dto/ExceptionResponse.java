package by.grsu.iot.api.dto;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private int status;
    private String field;

    public ExceptionResponse(Date timestamp, String message, int status, String field) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.field = field;
    }

    public ExceptionResponse() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}