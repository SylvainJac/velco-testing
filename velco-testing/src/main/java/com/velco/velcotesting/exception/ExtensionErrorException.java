package com.velco.velcotesting.exception;


public class ExtensionErrorException extends Exception {

    public ExtensionErrorException(String message) {
        super("Bad extension of file: " + message);
    }
}
