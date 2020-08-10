package com.startapp.android.publish.common.metaData;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5051k;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.common.metaData.h */
/* compiled from: StartAppSDK */
public class C5122h implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean enabled = false;

    /* renamed from: a */
    public boolean mo62662a() {
        return this.enabled;
    }

    /* renamed from: a */
    public boolean mo62663a(Context context) {
        return C5051k.m3335a(context, "userDisabledSimpleToken", Boolean.valueOf(false)).booleanValue();
    }

    /* renamed from: b */
    public boolean mo62664b(Context context) {
        return !mo62663a(context) && mo62662a();
    }

    /* renamed from: a */
    public void mo62661a(Context context, boolean z) {
        C5051k.m3342b(context, "userDisabledSimpleToken", Boolean.valueOf(!z));
    }
}
