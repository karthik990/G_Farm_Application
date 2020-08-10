package com.firebase.p037ui.auth.data.model;

import android.app.PendingIntent;
import com.firebase.p037ui.auth.FirebaseUiException;

/* renamed from: com.firebase.ui.auth.data.model.PendingIntentRequiredException */
public class PendingIntentRequiredException extends FirebaseUiException {
    private final PendingIntent mPendingIntent;
    private final int mRequestCode;

    public PendingIntentRequiredException(PendingIntent pendingIntent, int i) {
        super(0);
        this.mPendingIntent = pendingIntent;
        this.mRequestCode = i;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }
}
