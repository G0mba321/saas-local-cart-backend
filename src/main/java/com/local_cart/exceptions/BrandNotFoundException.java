package com.local_cart.exceptions;

public class BrandNotFoundException extends ResourceNotFoundException {
    public BrandNotFoundException(Long id) {
        super("Cannot find the brand " + id);
    }
}
