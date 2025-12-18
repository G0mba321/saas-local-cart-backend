package com.local_cart.exceptions.globalExceptions;

import com.local_cart.exceptions.ParentCategoryException;
import com.local_cart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

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

    @ExceptionHandler(ParentCategoryException.class)
    public ProblemDetail parentException(ParentCategoryException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                ex.getMessage());

        problemDetail.setTitle("Operation is failed");
        problemDetail.setProperty("time:", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail catchForgotten(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong");

        problemDetail.setTitle("Server error");
        problemDetail.setProperty("time:", LocalDateTime.now());

        return problemDetail;
    }

}
