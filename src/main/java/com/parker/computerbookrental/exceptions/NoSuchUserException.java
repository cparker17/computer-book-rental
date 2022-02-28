package com.parker.computerbookrental.exceptions;

public class NoSuchUserException extends Exception{
    public NoSuchUserException(String message) {
        super(message);
    }
}
