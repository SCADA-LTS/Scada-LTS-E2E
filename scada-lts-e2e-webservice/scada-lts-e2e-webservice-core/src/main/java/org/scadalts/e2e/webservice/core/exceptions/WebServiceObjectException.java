package org.scadalts.e2e.webservice.core.exceptions;

public class WebServiceObjectException extends Exception {

    public WebServiceObjectException() {
    }

    public WebServiceObjectException(String message) {
        super(message);
    }

    public WebServiceObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceObjectException(Throwable cause) {
        super(cause);
    }

    public WebServiceObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
