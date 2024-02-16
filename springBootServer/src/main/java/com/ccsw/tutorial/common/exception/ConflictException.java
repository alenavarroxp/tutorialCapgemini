package com.ccsw.tutorial.common.exception;

public class ConflictException extends Exception {

    private static final long serialVersionUID = 1L;
    private final int code;

    public ConflictException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
