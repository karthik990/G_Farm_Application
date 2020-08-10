package com.mobiroller.services;

import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ServerUtilities;

public class FCMInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FCMInstanceIDService";

    public void onTokenRefresh() {
        String str = TAG;
        try {
            String token = FirebaseInstanceId.getInstance(FirebaseApp.getInstance("main")).getToken();
            StringBuilder sb = new StringBuilder();
            sb.append("API ");
            sb.append(token);
            Log.e(str, sb.toString());
            sendRegistrationToAPI(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String token2 = FirebaseInstanceId.getInstance().getToken();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Firebase ");
            sb2.append(token2);
            Log.e(str, sb2.toString());
            sendRegistrationToFirebase(token2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void sendRegistrationToFirebase(String str) {
        new SharedPrefHelper(getApplicationContext()).setFirebaseToken(str);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USERS_FIREBASE_TOKEN).setValue(str);
        }
    }

    private void sendRegistrationToAPI(String str) {
        UtilManager.sharedPrefHelper().setFCMToken(str);
        ServerUtilities.register(getApplicationContext(), getResources().getString(R.string.mobiroller_username), UtilManager.sharedPrefHelper().getDeviceId(), LocaleHelper.getLocale().toUpperCase(), str);
    }
}
