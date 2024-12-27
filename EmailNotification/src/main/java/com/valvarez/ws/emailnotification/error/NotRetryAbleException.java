package com.valvarez.ws.emailnotification.error;

public class NotRetryAbleException extends RuntimeException {
    public NotRetryAbleException(String message) {
        super(message);
    }

    public NotRetryAbleException(Throwable cause) {
        super(cause);
    }
}
