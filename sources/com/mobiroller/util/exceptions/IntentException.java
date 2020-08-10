package com.mobiroller.util.exceptions;

import android.content.Intent;

public class IntentException extends MobirollerException {
    Intent intent;

    public IntentException(Intent intent2) {
        this.intent = intent2;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void setIntent(Intent intent2) {
        this.intent = intent2;
    }
}
