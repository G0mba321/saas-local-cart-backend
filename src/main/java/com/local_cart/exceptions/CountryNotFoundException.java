package com.local_cart.exceptions;

public class CountryNotFoundException extends ResourceNotFoundException {
    public CountryNotFoundException(Long id) {
        super("Cannot find the country " + id);
    }
}
