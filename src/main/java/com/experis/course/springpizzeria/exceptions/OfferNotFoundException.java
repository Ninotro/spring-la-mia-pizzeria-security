package com.experis.course.springpizzeria.exceptions;

public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(String message) {
        super(message);
    }

}