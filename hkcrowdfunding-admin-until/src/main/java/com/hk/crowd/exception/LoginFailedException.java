package com.hk.crowd.exception;

import java.io.Serializable;

/**
 * @author Dragon
 * @version 1.0.0
 */
public class LoginFailedException extends RuntimeException {

    private static final long serialVersionUID = -8725889359906212780L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

}
