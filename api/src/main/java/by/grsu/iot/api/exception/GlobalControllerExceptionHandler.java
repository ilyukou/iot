package by.grsu.iot.api.exception;

import by.grsu.iot.api.dto.ExceptionResponse;
import by.grsu.iot.api.security.jwt.InvalidJwtAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({IOException.class})
    public void serverErrors(final IOException exception, final HttpServletRequest request) {
        LOGGER.error(exception.toString(), request);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({NullPointerException.class})
    public void serverErrors(final NullPointerException exception, final HttpServletRequest request) {
        LOGGER.error(exception.toString(), request);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler({EntityNotFoundException.class})
    public void notFoundError() {
        // Nothing to do
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler({IllegalArgumentException.class})
    public void validationErrorException() {
        // Nothing to do
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler({InvalidJwtAuthenticationException.class})
    public void invalidJwtAuthenticationException() {
        // Nothing to do
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity badRequestException(final BadRequestException exception, final HttpServletRequest request) {
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(new Date(), exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(), exception.getField());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    @ExceptionHandler({NotAccessForOperationException.class})
    public void notAccessForOperationException() {
        // Nothing to do
    }
}
