package by.grsu.iot.api.exception;

import by.grsu.iot.service.domain.ExceptionResponse;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.domain.BadRequestExceptionResponse;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.exception.NotAccessForOperationException;
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

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<BadRequestExceptionResponse> badRequestException(final BadRequestException exception) {
        return new ResponseEntity<>(
                new BadRequestExceptionResponse(new Date(), exception.getMessage(), exception.getField())
                , HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> responseFor401(){
        return new ResponseEntity<>(
                new BadRequestExceptionResponse(new Date(), "Authentication exception", null)
                , HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler({InvalidJwtAuthenticationException.class})
    public ResponseEntity<ExceptionResponse> invalidJwtAuthenticationException() {
        return responseFor401();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ExceptionResponse> authenticationException() {
        return responseFor401();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    @ExceptionHandler({NotAccessForOperationException.class})
    public ResponseEntity<ExceptionResponse> notAccessForOperationException(final BadRequestException exception) {
        return new ResponseEntity<>(
                new BadRequestExceptionResponse(new Date(), exception.getMessage(), exception.getField())
                , HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler({EntityNotFoundException.class})
    public void notFoundError() {
        // ignore
    }

    private ResponseEntity<ExceptionResponse> responseFor500(Exception e, final HttpServletRequest request){
        LOGGER.error("Error while processing Http request; HttpServletRequest={" + request.toString() + "}", e);
        return new ResponseEntity<>(
                new ExceptionResponse(new Date(), "Server error")
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({IOException.class})
    public ResponseEntity<ExceptionResponse> serverErrors(final IOException exception, final HttpServletRequest request) {
        return responseFor500(exception, request);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ExceptionResponse> serverErrors(final NullPointerException exception,
                                                          final HttpServletRequest request) {
        return responseFor500(exception, request);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> validationErrorException(final IOException exception,
                                                                      final HttpServletRequest request) {
        return responseFor500(exception, request);
    }
}
