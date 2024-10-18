package com.crispyread.core.advice;

import com.crispyread.core.entities.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDetails InValidExceptionHandler(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage("Invalid or bad request data")
                .errorDetails(errorMap)
                .build();

    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDetails handleAuthenticationException(AuthenticationException ex) {
        return ErrorDetails.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .errorMessage("Authentication token is missing")
                .build();
    }
}

