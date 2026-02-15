package com.dinerate.elastic.dinerate_backened.exceptions;

import java.io.IOException;

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }


    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
