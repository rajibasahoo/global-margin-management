package com.luxoft.globalmanagement.exception;

public class OilBussinessException extends Exception {

    public OilBussinessException(String message) {
        super(message);
    }

    public OilBussinessException(String message,
        Throwable cause) {
        super(message, cause);
    }

    public OilBussinessException(Throwable cause) {
        super(cause);
    }
}
