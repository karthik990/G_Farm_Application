package com.firebase.p037ui.auth.data.model;

import android.content.Intent;
import com.firebase.p037ui.auth.FirebaseUiException;

/* renamed from: com.firebase.ui.auth.data.model.IntentRequiredException */
public class IntentRequiredException extends FirebaseUiException {
    private final Intent mIntent;
    private final int mRequestCode;

    public IntentRequiredException(Intent intent, int i) {
        super(0);
        this.mIntent = intent;
        this.mRequestCode = i;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }
}
