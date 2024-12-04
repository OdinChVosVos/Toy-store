package ru.astradev.toy_store.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HeIsAlreadyThereException.class)
    protected ResponseEntity<AwesomeException> handleHeIsAlreadyThereException() {
        return new ResponseEntity<>(new AwesomeException("This good is already in cart"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TooMuchException.class)
    protected ResponseEntity<AwesomeException> handleTooMuchEception() {
        return new ResponseEntity<>(new AwesomeException("There is no such quantity of good"),
                HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String message;
    }
}
