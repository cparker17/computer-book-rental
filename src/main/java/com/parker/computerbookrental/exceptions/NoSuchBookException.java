package com.parker.computerbookrental.exceptions;

public class NoSuchBookException extends Exception{
    public NoSuchBookException(String message) {
        super(message);
    }
}
