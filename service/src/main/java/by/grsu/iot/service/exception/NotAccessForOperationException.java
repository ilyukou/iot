package by.grsu.iot.service.exception;

// 403
public class NotAccessForOperationException extends ApiException {
    public NotAccessForOperationException(String message) {
        super(message);
    }

    public NotAccessForOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAccessForOperationException(String field, String message) {
        super(field, message);
    }
}
