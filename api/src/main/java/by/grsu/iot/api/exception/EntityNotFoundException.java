package by.grsu.iot.api.exception;

// 404
public class EntityNotFoundException extends ApiException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String field, String message) {
        super(field, message);
    }
}
