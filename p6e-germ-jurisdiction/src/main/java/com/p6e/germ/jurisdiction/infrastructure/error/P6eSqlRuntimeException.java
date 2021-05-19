package com.p6e.germ.jurisdiction.infrastructure.error;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSqlRuntimeException extends RuntimeException {

    public P6eSqlRuntimeException() {
        this("runtime");
    }

    public P6eSqlRuntimeException(String message) {
        super(message + " executing sql exceptions.");
    }
}
