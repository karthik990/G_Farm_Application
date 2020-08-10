package com.mobiroller.models.response;

public class ECommerceAPIError {
    private String[] errors;
    private boolean isUserFriendlyMessage;
    private String message;

    public String getMessage() {
        return this.message;
    }

    public boolean isUserFriendlyMessage() {
        return this.isUserFriendlyMessage;
    }

    public String[] getErrors() {
        return this.errors;
    }
}
