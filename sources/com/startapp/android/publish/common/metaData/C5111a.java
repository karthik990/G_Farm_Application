package com.startapp.android.publish.common.metaData;

import java.io.Serializable;

/* renamed from: com.startapp.android.publish.common.metaData.a */
/* compiled from: StartAppSDK */
public class C5111a implements Serializable {
    private static final long serialVersionUID = 1;
    private int delay = 3;
    private boolean enabled = true;
    private int minApiLevel = 18;

    public C5111a() {
    }

    public C5111a(int i) {
        this.minApiLevel = i;
    }

    /* renamed from: a */
    public int mo62638a() {
        return this.delay;
    }

    /* renamed from: b */
    public int mo62639b() {
        return this.minApiLevel;
    }

    /* renamed from: c */
    public boolean mo62640c() {
        return this.enabled;
    }
}
