package by.grsu.iot.api.exception;

// 401
public class BadRequestException extends ApiException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String field, String message) {
        super(field, message);
    }
}
