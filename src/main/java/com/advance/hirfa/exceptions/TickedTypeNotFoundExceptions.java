package com.advance.hirfa.exceptions;

public class TickedTypeNotFoundExceptions extends EventTicketException{
    public TickedTypeNotFoundExceptions() {
    }

    public TickedTypeNotFoundExceptions(String message) {
        super(message);
    }

    public TickedTypeNotFoundExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public TickedTypeNotFoundExceptions(Throwable cause) {
        super(cause);
    }

    public TickedTypeNotFoundExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
