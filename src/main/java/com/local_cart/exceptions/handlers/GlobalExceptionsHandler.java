package com.local_cart.exceptions.handlers;

import com.local_cart.exceptions.ConflictException;
import com.local_cart.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail notFoundExceptions(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                ex.getMessage());

        problemDetail.setTitle("Resource not found");
        problemDetail.setProperty("time: ", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail conflictException(ConflictException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                ex.getMessage());

        problemDetail.setTitle("Operation is failed");
        problemDetail.setProperty("time:", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail catchForgotten(Exception ex) {
        log.error("exception caught: {}", ex.getMessage(), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong");

        problemDetail.setTitle("Server error");
        problemDetail.setProperty("time:", LocalDateTime.now());

        return problemDetail;
    }

}
