package com.mobiroller.models.response;

public class APIError {
    private String[] errors;
    private int statusCode;

    public int status() {
        return this.statusCode;
    }

    public String message() {
        return this.errors[0];
    }
}
