package by.grsu.iot.service.exception;

/**
 * Not found required entity
 *
 * @author Ilyukou Ilya
 */
public class EntityNotFoundApplicationException extends ApplicationException {
    public EntityNotFoundApplicationException(String message) {
        super(message);
    }
}
