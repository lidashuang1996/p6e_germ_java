package com.p6e.germ.jurisdiction.infrastructure.error;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eDataNoNullRuntimeException extends RuntimeException {

    public P6eDataNoNullRuntimeException() {
        this("runtime");
    }

    public P6eDataNoNullRuntimeException(String message) {
        super(message + " executing data is null.");
    }
}
