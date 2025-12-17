package com.local_cart.exceptions;

public class ParentCategoryException extends RuntimeException {
    public ParentCategoryException() {
        super("A category cannot be its own parent");
    }
}
