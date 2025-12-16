package com.local_cart.exceptions;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(Long id) {
        super("Cannot find the country " + id);
    }
}
