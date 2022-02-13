package com.parker.customerwebsite.exceptions;

public class NoSuchBookException extends Exception{
    public NoSuchBookException(String message) {
        super(message);
    }
}
