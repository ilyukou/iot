package by.grsu.iot.service.domain;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

    private Long time;
    private String message;

    public ExceptionResponse(Date time, String message) {
        this.time = time.getTime();
        this.message = message;
    }

    public ExceptionResponse(Long time, String message) {
        this.time = time;
        this.message = message;
    }

    public ExceptionResponse() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
