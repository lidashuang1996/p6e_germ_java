package com.p6e.germ.jurisdiction.infrastructure.error;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eRelationDataException extends RuntimeException {

    public P6eRelationDataException() {
        this("runtime");
    }

    public P6eRelationDataException(String message) {
        super(message + " executing data is null.");
    }
}