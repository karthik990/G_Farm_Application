package com.braintreepayments.api.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.braintreepayments.api.Venmo;
import com.braintreepayments.api.models.BinData;
import com.paypal.android.sdk.onetouch.core.PayPalOneTouchCore;
import org.json.JSONException;
import org.json.JSONObject;
import p043io.reactivex.annotations.SchedulerSupport;

public class AnalyticsEvent {
    private static final String DEVICE_NETWORK_TYPE_KEY = "deviceNetworkType";
    private static final String MERCHANT_APP_VERSION_KEY = "merchantAppVersion";
    private static final String PAYPAL_INSTALLED_KEY = "paypalInstalled";
    private static final String SESSION_ID_KEY = "sessionId";
    private static final String USER_INTERFACE_ORIENTATION_KEY = "userInterfaceOrientation";
    private static final String VENMO_INSTALLED_KEY = "venmoInstalled";
    String event;

    /* renamed from: id */
    int f229id;
    JSONObject metadata;
    long timestamp;

    public AnalyticsEvent(Context context, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("android.");
        sb.append(str2);
        sb.append(".");
        sb.append(str3);
        this.event = sb.toString();
        this.timestamp = System.currentTimeMillis() / 1000;
        this.metadata = new JSONObject();
        try {
            this.metadata.put(SESSION_ID_KEY, str).put(DEVICE_NETWORK_TYPE_KEY, getNetworkType(context)).put(USER_INTERFACE_ORIENTATION_KEY, getUserOrientation(context)).put(MERCHANT_APP_VERSION_KEY, getAppVersion(context)).put(PAYPAL_INSTALLED_KEY, isPayPalInstalled(context)).put(VENMO_INSTALLED_KEY, Venmo.isVenmoInstalled(context));
        } catch (JSONException unused) {
        }
    }

    public AnalyticsEvent() {
        this.metadata = new JSONObject();
    }

    public String getIntegrationType() {
        String[] split = this.event.split("\\.");
        return split.length > 1 ? split[1] : "";
    }

    private String getNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        String typeName = activeNetworkInfo != null ? activeNetworkInfo.getTypeName() : null;
        return typeName == null ? SchedulerSupport.NONE : typeName;
    }

    private String getUserOrientation(Context context) {
        int i = context.getResources().getConfiguration().orientation;
        if (i != 1) {
            return i != 2 ? BinData.UNKNOWN : "Landscape";
        }
        return "Portrait";
    }

    private String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException unused) {
            return "VersionUnknown";
        }
    }

    private boolean isPayPalInstalled(Context context) {
        try {
            Class.forName(PayPalOneTouchCore.class.getName());
            return PayPalOneTouchCore.isWalletAppInstalled(context);
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
            return false;
        }
    }
}
