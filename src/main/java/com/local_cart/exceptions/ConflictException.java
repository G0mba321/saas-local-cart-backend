package com.local_cart.exceptions;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ConflictException extends RuntimeException{

    public ProblemDetail conflictException(Exception ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    public @Nullable String getMessage() {
        return "Bad request";
    }
}
