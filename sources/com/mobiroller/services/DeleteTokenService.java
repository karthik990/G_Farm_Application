package com.mobiroller.services;

import android.app.IntentService;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.SharedPrefHelper;
import java.io.IOException;

public class DeleteTokenService extends IntentService {
    public static final String TAG = DeleteTokenService.class.getSimpleName();

    public DeleteTokenService() {
        super(TAG);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        try {
            getTokenFromPrefs();
            FirebaseInstanceId.getInstance().deleteInstanceId();
            saveTokenToPrefs("");
            getTokenFromPrefs();
            FirebaseInstanceId.getInstance().getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTokenToPrefs(String str) {
        new SharedPrefHelper(getApplicationContext()).setFirebaseToken(str);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_FIREBASE_TOKEN).setValue(str);
        }
    }

    private String getTokenFromPrefs() {
        return new SharedPrefHelper(getApplicationContext()).getFirebaseToken();
    }
}
