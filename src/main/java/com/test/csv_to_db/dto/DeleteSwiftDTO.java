package com.test.csv_to_db.dto;

public class DeleteSwiftDTO {
    private String message;

    public DeleteSwiftDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
