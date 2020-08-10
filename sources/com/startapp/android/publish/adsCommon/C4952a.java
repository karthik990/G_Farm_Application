package com.startapp.android.publish.adsCommon;

import android.app.Activity;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.a */
/* compiled from: StartAppSDK */
public class C4952a implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean isActivityFullScreen;

    public C4952a(Activity activity) {
        m2941a(C4988c.m3116a(activity));
    }

    /* renamed from: a */
    public boolean mo62047a() {
        return this.isActivityFullScreen;
    }

    /* renamed from: a */
    private void m2941a(boolean z) {
        this.isActivityFullScreen = z;
    }
}
