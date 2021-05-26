package by.grsu.iot.api.model.exception;

/**
 * User not access for operation, like create/read/update/delete, with entity or resource
 *
 * @author Ilyukou Ilya
 */
public class NotAccessForOperationApplicationException extends ApplicationException {
    public NotAccessForOperationApplicationException(String message) {
        super(message);
    }

    public NotAccessForOperationApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
