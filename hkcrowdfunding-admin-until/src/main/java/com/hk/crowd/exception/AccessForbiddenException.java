package com.hk.crowd.exception;

/**
 * @author Dragon
 * @version 1.0.0
 */
public class AccessForbiddenException extends RuntimeException{
    private static final long serialVersionUID = -2115955837780405035L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
