package by.grsu.iot.model.dto.exception;

import by.grsu.iot.model.exception.BadRequestApplicationException;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Exception response dto for {@link BadRequestApplicationException}
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
public class BadRequestApplicationExceptionDto extends ApplicationExceptionDto {

    private String field;

    public BadRequestApplicationExceptionDto(Date time, String message, String field) {
        super(time, message);
        this.field = field;
    }
}
