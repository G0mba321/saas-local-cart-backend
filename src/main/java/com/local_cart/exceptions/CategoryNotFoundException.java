package com.local_cart.exceptions;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Cannot find the category " + id);
    }
}
