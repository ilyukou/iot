package by.grsu.iot.api.model.exception;

/**
 * Superclass for all exceptions in the application
 *
 * @author Ilyukou Ilya
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
