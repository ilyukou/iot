package by.grsu.iot.repository.exception;

/**
 * Throw when has conflict between two requests requiring one resource
 *
 * @author Ilyukou Ilya
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
