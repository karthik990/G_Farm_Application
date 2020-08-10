package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.android.billingclient.util.BillingHelper;

public class ProxyBillingActivity extends Activity {
    static final String KEY_RESULT_RECEIVER = "result_receiver";
    private static final int REQUEST_CODE_LAUNCH_ACTIVITY = 100;
    private static final String TAG = "ProxyBillingActivity";
    private ResultReceiver mResultReceiver;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        PendingIntent pendingIntent;
        super.onCreate(bundle);
        String str = KEY_RESULT_RECEIVER;
        String str2 = TAG;
        if (bundle == null) {
            BillingHelper.logVerbose(str2, "Launching Play Store billing flow");
            this.mResultReceiver = (ResultReceiver) getIntent().getParcelableExtra(str);
            String str3 = "BUY_INTENT";
            if (getIntent().hasExtra(str3)) {
                pendingIntent = (PendingIntent) getIntent().getParcelableExtra(str3);
            } else {
                Intent intent = getIntent();
                String str4 = BillingHelper.RESPONSE_SUBS_MANAGEMENT_INTENT_KEY;
                pendingIntent = intent.hasExtra(str4) ? (PendingIntent) getIntent().getParcelableExtra(str4) : null;
            }
            try {
                startIntentSenderForResult(pendingIntent.getIntentSender(), 100, new Intent(), 0, 0, 0);
            } catch (SendIntentException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Got exception while trying to start a purchase flow: ");
                sb.append(e);
                BillingHelper.logWarn(str2, sb.toString());
                this.mResultReceiver.send(6, null);
                finish();
            }
        } else {
            BillingHelper.logVerbose(str2, "Launching Play Store billing flow from savedInstanceState");
            this.mResultReceiver = (ResultReceiver) bundle.getParcelable(str);
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable(KEY_RESULT_RECEIVER, this.mResultReceiver);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        String str = TAG;
        if (i == 100) {
            int responseCodeFromIntent = BillingHelper.getResponseCodeFromIntent(intent, str);
            if (!(i2 == -1 && responseCodeFromIntent == 0)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Activity finished with resultCode ");
                sb.append(i2);
                sb.append(" and billing's responseCode: ");
                sb.append(responseCodeFromIntent);
                BillingHelper.logWarn(str, sb.toString());
            }
            this.mResultReceiver.send(responseCodeFromIntent, intent == null ? null : intent.getExtras());
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Got onActivityResult with wrong requestCode: ");
            sb2.append(i);
            sb2.append("; skipping...");
            BillingHelper.logWarn(str, sb2.toString());
        }
        finish();
    }
}
