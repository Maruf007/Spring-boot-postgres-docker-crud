package com.scalegrid.assignment.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * <p>GlobalExceptionHandler class handles exceptions globally.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * This method handle method not found exception.
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleMethodNotSupported(Exception ex) {
        LOGGER.error(prepareErrorMsg(ex));
        ErrorResponse errorResponse = new ErrorResponse.
                Builder()
                .withMessage(ex.getMessage())
                .withHttpStatus(HttpStatus.METHOD_NOT_ALLOWED)
                .withCreatedAt()
                .build();
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * This method handle resource not found exception.
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex) {
        LOGGER.error(prepareErrorMsg(ex));
        ErrorResponse errorResponse = new ErrorResponse.
                Builder()
                .withMessage(ex.getMessage())
                .withHttpStatus(HttpStatus.NOT_FOUND)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * This method handle method constrain violation exception.
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(Exception ex) {
        String msg = ex.getCause().getCause().getMessage();
        LOGGER.error(prepareErrorMsg(msg));
        ErrorResponse errorResponse = new ErrorResponse.
                Builder()
                .withMessage(msg)
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * This method handle method constrain violation exception.
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(Exception ex) {
        String msg = ex.getCause().getCause().getMessage();
        LOGGER.error(prepareErrorMsg(msg));
        ErrorResponse errorResponse = new ErrorResponse.
                Builder()
                .withMessage(msg)
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    private <T> String prepareErrorMsg(T ex) {
        return new StringBuilder().append(ex).toString();
    }
}
