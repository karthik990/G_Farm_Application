package com.braintreepayments.browserswitch;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

public class BrowserSwitchActivity extends Activity {
    private static Uri sReturnUri;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sReturnUri = null;
        if (!(getIntent() == null || getIntent().getData() == null)) {
            sReturnUri = getIntent().getData();
        }
        finish();
    }

    public static Uri getReturnUri() {
        return sReturnUri;
    }

    public static void clearReturnUri() {
        sReturnUri = null;
    }
}
