package com.local_cart.exceptions;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(Long id) {
        super("Cannot find the product " + id);
    }
}
