package com.firebase.p037ui.auth.data.client;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.util.Preconditions;

/* renamed from: com.firebase.ui.auth.data.client.AuthUiInitProvider */
public class AuthUiInitProvider extends ContentProvider {
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        Preconditions.checkNotNull(providerInfo, "AuthUiInitProvider ProviderInfo cannot be null.", new Object[0]);
        if (!"com.firebase.ui.auth.authuiinitprovider".equals(providerInfo.authority)) {
            super.attachInfo(context, providerInfo);
            return;
        }
        throw new IllegalStateException("Incorrect provider authority in manifest. Most likely due to a missing applicationId variable in application's build.gradle.");
    }

    public boolean onCreate() {
        AuthUI.setApplicationContext(getContext());
        return false;
    }
}
