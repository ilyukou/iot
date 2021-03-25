package by.grsu.iot.service.exception;

/**
 * Bad request exception indicate that user send not valid data
 *
 * @author Ilyukou Ilya
 */
public class BadRequestApplicationException extends ApplicationException {

    private String field;

    public BadRequestApplicationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
