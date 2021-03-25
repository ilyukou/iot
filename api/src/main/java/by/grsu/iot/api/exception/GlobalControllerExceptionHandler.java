package by.grsu.iot.api.exception;

import by.grsu.iot.model.dto.exception.ApplicationExceptionDto;
import by.grsu.iot.model.dto.exception.BadRequestApplicationExceptionDto;
import by.grsu.iot.model.dto.exception.NotAccessForOperationApplicationExceptionDto;
import by.grsu.iot.service.exception.BadRequestApplicationException;
import by.grsu.iot.service.exception.EntityNotFoundApplicationException;
import by.grsu.iot.service.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.service.security.jwt.InvalidJwtAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Controller advice for exception handing
 *
 * @author Ilyukou Ilya
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler({BadRequestApplicationException.class})
    public ResponseEntity<BadRequestApplicationExceptionDto> badRequestException(final BadRequestApplicationException exception) {
        return new ResponseEntity<>(
                new BadRequestApplicationExceptionDto(new Date(), exception.getMessage(), exception.getField())
                , HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApplicationExceptionDto> responseFor401() {
        return new ResponseEntity<>(
                new ApplicationExceptionDto(new Date(), "Authentication exception")
                , HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler({InvalidJwtAuthenticationException.class})
    public ResponseEntity<ApplicationExceptionDto> invalidJwtAuthenticationException(final InvalidJwtAuthenticationException exception) {
        return responseFor401();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApplicationExceptionDto> authenticationException(final AuthenticationException exception) {
        return responseFor401();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    @ExceptionHandler({NotAccessForOperationApplicationException.class})
    public ResponseEntity<ApplicationExceptionDto> notAccessForOperationException(final NotAccessForOperationApplicationException exception) {
        return new ResponseEntity<>(
                new NotAccessForOperationApplicationExceptionDto(new Date(), exception.getMessage())
                , HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler({EntityNotFoundApplicationException.class})
    public void notFoundError() {
        // ignore
    }

    private ResponseEntity<ApplicationExceptionDto> responseFor500(Exception e, final HttpServletRequest request) {
        LOGGER.error("Error while processing Http request; HttpServletRequest={" + request.toString() + "}", e);
        return new ResponseEntity<>(
                new ApplicationExceptionDto(new Date(), "Server error")
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({IOException.class})
    public ResponseEntity<ApplicationExceptionDto> serverErrors(final IOException exception, final HttpServletRequest request) {
        return responseFor500(exception, request);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ApplicationExceptionDto> serverErrors(final NullPointerException exception,
                                                                final HttpServletRequest request) {
        return responseFor500(exception, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApplicationExceptionDto> validationErrorException(final IllegalArgumentException exception,
                                                                            final HttpServletRequest request) {
        return new ResponseEntity<>(
                new ApplicationExceptionDto(new Date(), exception.getMessage())
                , HttpStatus.BAD_REQUEST);
    }
}
