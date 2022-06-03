package com.hk.crowd.exception;

/**
 * @author Dragon
 * @version 1.0.0
 */
public class LoginAcctAlreadyInUseException extends RuntimeException{
    private static final long serialVersionUID = 4479055575728663149L;

    public LoginAcctAlreadyInUseException() {
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

}
