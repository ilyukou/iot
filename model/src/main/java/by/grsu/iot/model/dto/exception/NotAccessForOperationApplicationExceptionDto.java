package by.grsu.iot.model.dto.exception;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;

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
