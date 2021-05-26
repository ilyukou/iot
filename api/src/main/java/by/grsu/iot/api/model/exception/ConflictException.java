package by.grsu.iot.api.model.exception;

/**
 * Throw when has conflict between two requests requiring one resource
 *
 * @author Ilyukou Ilya
 */
public class ConflictException extends ApplicationException {

    public ConflictException(String message) {
        super(message);
    }
}
