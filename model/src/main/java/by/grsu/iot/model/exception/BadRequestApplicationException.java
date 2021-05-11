package by.grsu.iot.model.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Bad request exception indicate that user send not valid data
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
public class BadRequestApplicationException extends ApplicationException {

    private String field;

    public BadRequestApplicationException(String field, String message) {
        super(message);
        this.field = field;
    }
}
