package com.p6e.germ.jurisdiction.infrastructure.error;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eDataNullRuntimeException extends RuntimeException {

    public P6eDataNullRuntimeException() {
        this("runtime");
    }

    public P6eDataNullRuntimeException(String message) {
        super(message + " executing data is null.");
    }
}
