package com.mobiroller.util.validation.exceptions;

import android.content.Context;

public class RequiredFieldException extends ShowableException {
    private Context context;
    private String fieldName;
    public int localisedErrorMessage;

    public RequiredFieldException(int i, Context context2) {
        this.context = context2;
        this.localisedErrorMessage = i;
    }

    public RequiredFieldException(String str) {
        this.fieldName = str;
    }

    public String toString() {
        int i = this.localisedErrorMessage;
        if (i != 0) {
            return this.context.getString(i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.fieldName);
        sb.append(" cannot be null");
        return sb.toString();
    }
}
