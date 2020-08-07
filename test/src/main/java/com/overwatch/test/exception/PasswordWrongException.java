package com.overwatch.test.exception;

public class PasswordWrongException extends RuntimeException {
    PasswordWrongException(){
        super("Password is Wrong");
    }

}