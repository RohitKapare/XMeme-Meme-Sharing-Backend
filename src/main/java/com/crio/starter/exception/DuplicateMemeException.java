package com.crio.starter.exception;

/**
 * Exception thrown when attempting to create a duplicate meme
 */
public class DuplicateMemeException extends RuntimeException {
    
    public DuplicateMemeException(String message) {
        super(message);
    }
    
    public DuplicateMemeException(String message, Throwable cause) {
        super(message, cause);
    }
}