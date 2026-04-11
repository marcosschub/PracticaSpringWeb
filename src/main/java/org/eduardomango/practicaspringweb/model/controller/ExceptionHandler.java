package org.eduardomango.practicaspringweb.model.controller;

import org.eduardomango.practicaspringweb.model.exceptions.ErrorDetails;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({
            UserNotFoundException.class
    })
    public ResponseEntity<ErrorDetails> HandlerNoSuchElementException(UserNotFoundException ex, WebRequest request){
        System.out.println("aaa");
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}
