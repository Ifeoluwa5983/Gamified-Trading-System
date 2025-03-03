package com.gts.exception;

public class TradingException extends Exception{
    public TradingException(String message) {
        super(message);
    }

    public TradingException() {
        super();
    }

    public TradingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradingException(Throwable cause) {
        super(cause);
    }

    protected TradingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
