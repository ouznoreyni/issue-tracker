package sn.noreyni.issuetrackerbackend.exception.exceptions;

import java.io.Serial;

public class BadRequestFilterException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestFilterException(String message) {
        super(message);
    }

    public BadRequestFilterException(String message, Throwable cause) {
        super(message, cause);
    }
}