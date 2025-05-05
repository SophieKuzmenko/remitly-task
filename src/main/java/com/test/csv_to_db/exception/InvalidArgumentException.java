package com.test.csv_to_db.exception;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends CustomRuntimeException {
    public InvalidArgumentException(String message) {
        super(message);
        setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
        setErrorCode("INVALID_FIELD_VALUE");
    }
}
