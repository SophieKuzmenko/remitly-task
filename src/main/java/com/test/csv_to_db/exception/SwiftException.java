package com.test.csv_to_db.exception;

import org.springframework.http.HttpStatus;

public class SwiftException extends CustomRuntimeException {
    public SwiftException(String message) {
        super(message);
        setHttpStatus(HttpStatus.NOT_FOUND);
        setErrorCode("SWIFT_NOT_FOUND");
    }
}
