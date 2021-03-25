package by.grsu.iot.api.exception.exception;

import by.grsu.iot.service.exception.NotAccessForOperationApplicationException;

import java.util.Date;

/**
 * Exception response dto for {@link NotAccessForOperationApplicationException}
 *
 * @author Ilyukou Ilya
 */
public class NotAccessForOperationApplicationExceptionDto extends ApplicationExceptionDto {

    public NotAccessForOperationApplicationExceptionDto(Date time, String message) {
        super(time, message);
    }
}
