package com.valvarez.ws.emailnotification.error;

public class RetryAbleException extends RuntimeException {
    public RetryAbleException(String message) {
        super(message);
    }

    public RetryAbleException(Throwable cause) {
        super(cause);
    }
}