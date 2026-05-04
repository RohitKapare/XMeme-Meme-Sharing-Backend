package com.crio.starter.exception;

/**
 * Exception thrown when a meme is not found
 */
public class MemeNotFoundException extends RuntimeException {
    
    public MemeNotFoundException(String message) {
        super(message);
    }
    
    public MemeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}