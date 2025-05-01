package com.crispyread.core.advice;

import com.crispyread.core.dto.ErrorDetails;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.naming.AuthenticationException;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDetails badRequestExceptionHandler(BadRequestException ex) {
        return ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(ex.getLocalizedMessage())
                .build();

    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDetails authenticationExceptionException(AuthenticationException ex) {
        return ErrorDetails.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .errorMessage(ex.getLocalizedMessage())
                .build();
    }
}

