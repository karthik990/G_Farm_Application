package com.braintreepayments.browserswitch;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public abstract class BrowserSwitchFragment extends Fragment {
    private static final String EXTRA_REQUEST_CODE = "com.braintreepayments.browserswitch.EXTRA_REQUEST_CODE";
    protected Context mContext;
    protected int mRequestCode;

    public enum BrowserSwitchResult {
        OK,
        CANCELED,
        ERROR;
        
        private String mErrorMessage;

        public String getErrorMessage() {
            return this.mErrorMessage;
        }

        /* access modifiers changed from: private */
        public BrowserSwitchResult setErrorMessage(String str) {
            this.mErrorMessage = str;
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(name());
            sb.append(" ");
            sb.append(getErrorMessage());
            return sb.toString();
        }
    }

    public abstract void onBrowserSwitchResult(int i, BrowserSwitchResult browserSwitchResult, Uri uri);

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mContext == null) {
            this.mContext = getActivity().getApplicationContext();
        }
        if (bundle != null) {
            this.mRequestCode = bundle.getInt(EXTRA_REQUEST_CODE);
        } else {
            this.mRequestCode = Integer.MIN_VALUE;
        }
    }

    public void onResume() {
        super.onResume();
        if (isBrowserSwitching()) {
            Uri returnUri = BrowserSwitchActivity.getReturnUri();
            int i = this.mRequestCode;
            this.mRequestCode = Integer.MIN_VALUE;
            BrowserSwitchActivity.clearReturnUri();
            if (returnUri != null) {
                onBrowserSwitchResult(i, BrowserSwitchResult.OK, returnUri);
            } else {
                onBrowserSwitchResult(i, BrowserSwitchResult.CANCELED, null);
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(EXTRA_REQUEST_CODE, this.mRequestCode);
    }

    public String getReturnUrlScheme() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getPackageName().toLowerCase().replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, ""));
        sb.append(".browserswitch");
        return sb.toString();
    }

    public void browserSwitch(int i, String str) {
        Intent addFlags = new Intent("android.intent.action.VIEW", Uri.parse(str)).addFlags(268435456);
        ChromeCustomTabs.addChromeCustomTabsExtras(this.mContext, addFlags);
        browserSwitch(i, addFlags);
    }

    public void browserSwitch(int i, Intent intent) {
        if (i == Integer.MIN_VALUE) {
            onBrowserSwitchResult(i, BrowserSwitchResult.ERROR.setErrorMessage("Request code cannot be Integer.MIN_VALUE"), null);
        } else if (!isReturnUrlSetup()) {
            onBrowserSwitchResult(i, BrowserSwitchResult.ERROR.setErrorMessage("The return url scheme was not set up, incorrectly set up, or more than one Activity on this device defines the same url scheme in it's Android Manifest. See https://github.com/braintree/browser-switch-android for more information on setting up a return url scheme."), null);
        } else if (availableActivities(intent).size() == 0) {
            onBrowserSwitchResult(i, BrowserSwitchResult.ERROR.setErrorMessage(String.format("No installed activities can open this URL: %s", new Object[]{intent.getData().toString()})), null);
        } else {
            this.mRequestCode = i;
            this.mContext.startActivity(intent);
        }
    }

    private boolean isBrowserSwitching() {
        return this.mRequestCode != Integer.MIN_VALUE;
    }

    private boolean isReturnUrlSetup() {
        Intent intent = new Intent("android.intent.action.VIEW");
        StringBuilder sb = new StringBuilder();
        sb.append(getReturnUrlScheme());
        sb.append("://");
        return availableActivities(intent.setData(Uri.parse(sb.toString())).addCategory("android.intent.category.DEFAULT").addCategory("android.intent.category.BROWSABLE")).size() == 1;
    }

    private List<ResolveInfo> availableActivities(Intent intent) {
        return this.mContext.getPackageManager().queryIntentActivities(intent, 0);
    }
}
