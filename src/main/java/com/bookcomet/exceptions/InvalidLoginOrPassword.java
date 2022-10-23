package com.bookcomet.exceptions;

public class InvalidLoginOrPassword extends RuntimeException {
    public InvalidLoginOrPassword() {
        super("The email or password provided does not match.");
    }
}
