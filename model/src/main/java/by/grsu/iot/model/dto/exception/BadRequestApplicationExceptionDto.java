package by.grsu.iot.model.dto.exception;

import by.grsu.iot.service.exception.BadRequestApplicationException;

import java.util.Date;

/**
 * Exception response dto for {@link BadRequestApplicationException}
 *
 * @author Ilyukou Ilya
 */
public class BadRequestApplicationExceptionDto extends ApplicationExceptionDto {

    private String field;

    public BadRequestApplicationExceptionDto(Date time, String message, String field) {
        super(time, message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
