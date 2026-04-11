package org.eduardomango.practicaspringweb.model.controller;

import jakarta.validation.UnexpectedTypeException;
import org.eduardomango.practicaspringweb.model.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityDuplicatedException.class)
    public ResponseEntity<ErrorDetails> HandlerEntityDuplicatedException(EntityDuplicatedException ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }

    @ExceptionHandler(InvalidArgumentsException.class)
    public ResponseEntity<ErrorDetails>  HandlerInvalidArgumentsException(InvalidArgumentsException ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(errorDetails);
    }

    @ExceptionHandler({
            ProductNotFoundException.class,
            UserNotFoundException.class,
            SaleNotFoundException.class
    })
    public ResponseEntity<ErrorDetails>  HandlerNoSuchElementException(NoSuchElementException ex, WebRequest request){
        System.out.println("aaa");
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler({
            UserNotFoundException.class
    })
    public ResponseEntity<ErrorDetails>  HandlerNoSuchElementException(UserNotFoundException ex, WebRequest request){
        System.out.println("aaa");
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorDetails> HandlerUnexpectedTypeException(UnexpectedTypeException ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }
}
