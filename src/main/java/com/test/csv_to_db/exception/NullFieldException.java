package com.test.csv_to_db.exception;

import org.springframework.http.HttpStatus;

public class NullFieldException extends CustomRuntimeException {

    public NullFieldException(String message) {
        super(message);
        setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
        setErrorCode("NULL_FIELD_VALUE_NOT_ALLOWED");
    }
}
