package com.scalegrid.assignment.exception;

/**
 * <p>This custom exception class handles resource not found exception.</p>
 */
public class ResourceNotFoundException extends RuntimeException {

    private  String message;

    /**
     * Constructs a new {@link ResourceNotFoundException} instance
     * @param message {@link String}
     */
    public ResourceNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
