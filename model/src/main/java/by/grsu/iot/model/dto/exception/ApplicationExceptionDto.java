package by.grsu.iot.model.dto.exception;

import by.grsu.iot.model.dto.DataTransferObject;
import by.grsu.iot.service.exception.ApplicationException;

import java.util.Date;

/**
 * Exception response dto for {@link ApplicationException}
 *
 * @author Ilyukou Ilya
 */
public class ApplicationExceptionDto implements DataTransferObject {

    private Long time;
    private String message;

    public ApplicationExceptionDto(Date time, String message) {
        this.time = time.getTime();
        this.message = message;
    }

    public ApplicationExceptionDto(Long time, String message) {
        this.time = time;
        this.message = message;
    }

    public ApplicationExceptionDto() {
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
