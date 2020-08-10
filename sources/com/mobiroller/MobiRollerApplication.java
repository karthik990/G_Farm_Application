package com.mobiroller;

import android.content.Context;
import androidx.multidex.MultiDex;
import com.applyze.ApplyzeAnalytics;
import com.flurry.android.FlurryAgent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiroller.helpers.AesBase64Wrapper;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.helpers.UtilManager.Builder;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.preview.BuildConfig;

public class MobiRollerApplication extends SharedApplication {
    public MobiRollerApplication getApp() {
        return this;
    }

    public void onCreate() {
        super.onCreate();
        ApplyzeAnalytics.with(this).setApiKey(getString(R.string.applyze_api_key)).setAppKey(getString(R.string.applyze_app_key)).init();
        initFirebaseApp();
        initFlurry();
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        new Builder().setContext(context).build();
        super.attachBaseContext(LocaleHelper.setLocale(context));
        MultiDex.install(context);
    }

    private void initFlurry() {
        new FlurryAgent.Builder().withLogEnabled(true).build(this, BuildConfig.FLURRY_APP_KEY);
    }

    private void initFirebaseApp() {
        initMainFirebaseApp();
        initCustomerFirebaseApp();
    }

    private void initMainFirebaseApp() {
        try {
            FirebaseApp.initializeApp(context, new FirebaseOptions.Builder().setApiKey(AesBase64Wrapper.decryptKey(getString(R.string.fcm_key))).setApplicationId(getString(R.string.google_app_id)).build(), "main").setDataCollectionDefaultEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCustomerFirebaseApp() {
        try {
            FirebaseApp.initializeApp(context, new FirebaseOptions.Builder().setApiKey(getString(R.string.firebase_api_key)).setApplicationId(getString(R.string.firebase_application_id)).setDatabaseUrl(getString(R.string.firebase_database_url)).setGcmSenderId(getString(R.string.firebase_gcm_sender_id)).build());
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            UtilManager.sharedPrefHelper().setChatValidated(true);
        } catch (Exception unused) {
            UtilManager.sharedPrefHelper().setChatValidated(false);
        }
    }
}
