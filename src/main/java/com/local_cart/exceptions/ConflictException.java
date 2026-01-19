package com.local_cart.exceptions;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        super(message);
    }
}
