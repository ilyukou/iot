package by.grsu.iot.api.exception;

import by.grsu.iot.api.dto.ExceptionResponse;
import by.grsu.iot.service.security.jwt.InvalidJwtAuthenticationException;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.exception.NotAccessForOperationException;
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
    public ResponseEntity<ExceptionResponse> serverErrors(final IOException exception, final HttpServletRequest request) {
        LOGGER.error(exception.toString(), request);

        return new ResponseEntity<>(
                new ExceptionResponse(new Date(), "Server error", null)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ExceptionResponse> serverErrors(final NullPointerException exception, final HttpServletRequest request) {
        LOGGER.error(exception.toString(), request);

        return new ResponseEntity<>(
                new ExceptionResponse(new Date(), "Server error", null)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler({EntityNotFoundException.class})
    public void notFoundError() {
        // Nothing to do
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> validationErrorException() {
        return new ResponseEntity<>(
                new ExceptionResponse(new Date(), null, null)
                , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler({InvalidJwtAuthenticationException.class})
    public void invalidJwtAuthenticationException() {
        // Nothing to do
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionResponse> badRequestException(final BadRequestException exception, final HttpServletRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(new Date(), exception.getMessage(), exception.getField())
                , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    @ExceptionHandler({NotAccessForOperationException.class})
    public ResponseEntity<ExceptionResponse> notAccessForOperationException(final BadRequestException exception, final HttpServletRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(new Date(), exception.getMessage(), exception.getField())
                , HttpStatus.FORBIDDEN);
    }
}
