package com.startapp.android.publish.adsCommon.p083g.p084a;

import android.app.Activity;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p083g.p086c.C5030a;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.net.URLDecoder;
import java.util.Map;

/* renamed from: com.startapp.android.publish.adsCommon.g.a.a */
/* compiled from: StartAppSDK */
public abstract class C5022a implements C5024b {
    private static final String TAG = "BaseMraidController";
    protected C5023a openListener;

    /* renamed from: com.startapp.android.publish.adsCommon.g.a.a$a */
    /* compiled from: StartAppSDK */
    public interface C5023a {
        /* renamed from: a */
        boolean mo61210a(String str);
    }

    public abstract void close();

    public abstract boolean isFeatureSupported(String str);

    public abstract void setOrientationProperties(Map<String, String> map);

    public abstract void useCustomClose(String str);

    public C5022a(C5023a aVar) {
        this.openListener = aVar;
    }

    public void resize() {
        C5155g.m3807a(TAG, 3, "resize");
    }

    public void expand(String str) {
        C5155g.m3807a(TAG, 3, "expand");
    }

    public boolean open(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("open ");
        sb.append(str);
        String sb2 = sb.toString();
        String str2 = TAG;
        C5155g.m3807a(str2, 3, sb2);
        try {
            String trim = URLDecoder.decode(str, "UTF-8").trim();
            if (trim.startsWith("sms")) {
                return openSMS(trim);
            }
            if (trim.startsWith("tel")) {
                return openTel(trim);
            }
            return this.openListener.mo61210a(trim);
        } catch (Exception e) {
            C5155g.m3807a(str2, 6, e.getMessage());
            return this.openListener.mo61210a(str);
        }
    }

    /* access modifiers changed from: protected */
    public void applyOrientationProperties(Activity activity, C5030a aVar) {
        try {
            int i = 0;
            int i2 = activity.getResources().getConfiguration().orientation == 1 ? 1 : 0;
            if (aVar.f3271b == 0) {
                i = 1;
            } else if (aVar.f3271b != 1) {
                i = aVar.f3270a ? -1 : i2;
            }
            C5146c.m3750a(activity, i);
        } catch (Exception e) {
            C5017f.m3256a(activity, C5015d.EXCEPTION, "BaseMraidController.applyOrientationProperties", e.getMessage(), "");
        }
    }

    public void setResizeProperties(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("setResizeProperties ");
        sb.append(map);
        C5155g.m3807a(TAG, 3, sb.toString());
    }

    public void createCalendarEvent(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("createCalendarEvent ");
        sb.append(str);
        C5155g.m3807a(TAG, 3, sb.toString());
        isFeatureSupported("calendar");
    }

    public void playVideo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("playVideo ");
        sb.append(str);
        C5155g.m3807a(TAG, 3, sb.toString());
        isFeatureSupported("inlineVideo");
    }

    public void storePicture(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("storePicture ");
        sb.append(str);
        C5155g.m3807a(TAG, 3, sb.toString());
        isFeatureSupported("storePicture");
    }

    public boolean openSMS(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("openSMS ");
        sb.append(str);
        C5155g.m3807a(TAG, 3, sb.toString());
        isFeatureSupported("sms");
        return true;
    }

    public boolean openTel(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("openTel ");
        sb.append(str);
        C5155g.m3807a(TAG, 3, sb.toString());
        isFeatureSupported("tel");
        return true;
    }
}
