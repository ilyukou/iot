package by.grsu.iot.model.exception;

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
