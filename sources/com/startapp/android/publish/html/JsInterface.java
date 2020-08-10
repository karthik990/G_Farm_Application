package com.startapp.android.publish.html;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p092a.C5155g;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: StartAppSDK */
public class JsInterface {
    private Runnable clickCallback;
    private Runnable closeCallback;
    private Runnable enableScrollCallback;
    protected boolean inAppBrowserEnabled;
    protected Context mContext;
    private C5002b params;
    private boolean processed;

    public JsInterface(Context context, Runnable runnable, C5002b bVar, boolean z) {
        this(context, runnable, bVar);
        this.inAppBrowserEnabled = z;
    }

    public JsInterface(Context context, Runnable runnable, C5002b bVar) {
        this.processed = false;
        this.inAppBrowserEnabled = true;
        this.closeCallback = null;
        this.clickCallback = null;
        this.enableScrollCallback = null;
        this.closeCallback = runnable;
        this.mContext = context;
        this.params = bVar;
    }

    public JsInterface(Context context, Runnable runnable, Runnable runnable2, Runnable runnable3, C5002b bVar, boolean z) {
        this(context, runnable, bVar, z);
        this.clickCallback = runnable2;
        this.enableScrollCallback = runnable3;
    }

    public JsInterface(Context context, Runnable runnable, Runnable runnable2, C5002b bVar) {
        this(context, runnable, bVar);
        this.clickCallback = runnable2;
    }

    @JavascriptInterface
    public void closeAd() {
        if (!this.processed) {
            this.processed = true;
            this.closeCallback.run();
        }
    }

    @JavascriptInterface
    public void openApp(String str, String str2, String str3) {
        if (str != null && !TextUtils.isEmpty(str)) {
            C4988c.m3124b(this.mContext, str, this.params);
        }
        Intent launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(str2);
        if (str3 != null) {
            try {
                JSONObject jSONObject = new JSONObject(str3);
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String valueOf = String.valueOf(keys.next());
                    launchIntentForPackage.putExtra(valueOf, String.valueOf(jSONObject.get(valueOf)));
                }
            } catch (JSONException e) {
                C5155g.m3806a(6, "Couldn't parse intent details json!", (Throwable) e);
            }
        }
        try {
            this.mContext.startActivity(launchIntentForPackage);
        } catch (Exception e2) {
            String str4 = "JsInterface.openApp - Couldn't start activity";
            C5017f.m3256a(this.mContext, C5015d.EXCEPTION, str4, e2.getMessage(), C4988c.m3097a(str, (String) null));
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot find activity to handle url: [");
            sb.append(str);
            sb.append("]");
            C5155g.m3805a(6, sb.toString());
        }
        Runnable runnable = this.clickCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    @JavascriptInterface
    public void externalLinks(String str) {
        if (!this.inAppBrowserEnabled || !C4946i.m2923a(256)) {
            C4988c.m3127c(this.mContext, str);
        } else {
            C4988c.m3125b(this.mContext, str, (String) null);
        }
    }

    @JavascriptInterface
    public void enableScroll(String str) {
        Runnable runnable = this.enableScrollCallback;
        if (runnable != null) {
            runnable.run();
        }
    }
}
