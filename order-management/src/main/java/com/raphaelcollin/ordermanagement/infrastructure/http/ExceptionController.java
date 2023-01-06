package com.raphaelcollin.ordermanagement.infrastructure.http;

import com.raphaelcollin.ordermanagement.domain.exception.EntityNotFoundException;
import com.raphaelcollin.ordermanagement.domain.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    private static final String GENERIC_SERVER_ERROR_BODY_MESSAGE = "There's been an unexpected error. Please, check logs or contact support.";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionBody> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        log.warn("An attempt to operate on an unknown entity was made.", entityNotFoundException);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionBody.fromException(entityNotFoundException));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionBody> handleInvalidRequestException(InvalidRequestException exception) {
        log.warn("A validation error happened.", exception);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionBody.fromException(exception));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionBody> handleGeneralError(Throwable throwable) {
        log.error("An unexpected error was thrown when performing the request: " + throwable.getMessage(), throwable);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionBody.fromMessage(GENERIC_SERVER_ERROR_BODY_MESSAGE));
    }
}