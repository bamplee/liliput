package me.nexters.liliput.api.interfaces.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Result Found")
public class NoResultFoundException extends RuntimeException {

    public NoResultFoundException() {
    }

    public NoResultFoundException(String message) {
        super("There Are No Results By " + message);
    }

    public NoResultFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoResultFoundException(Throwable cause) {
        super(cause);
    }

    public NoResultFoundException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
