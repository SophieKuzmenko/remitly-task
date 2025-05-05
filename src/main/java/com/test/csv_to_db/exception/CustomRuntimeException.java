package com.test.csv_to_db.exception;

import org.springframework.http.HttpStatus;

public class CustomRuntimeException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorCode;
    public CustomRuntimeException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
