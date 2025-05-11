
package com.rebelalliance.quasar.domain.exceptions;

public class LocationException extends Exception {
    public LocationException(String message) {
        super(message);
    }

    public LocationException(String message, Throwable cause) {
        super(message, cause);
    }
}