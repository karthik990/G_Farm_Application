package com.startapp.android.publish.common.metaData;

import java.io.Serializable;

/* renamed from: com.startapp.android.publish.common.metaData.b */
/* compiled from: StartAppSDK */
public class C5112b implements Serializable {
    private static final long serialVersionUID = 1;
    private int discoveryIntervalInMinutes = 1440;
    private boolean enabled = false;
    private int timeoutInSec = 20;

    /* renamed from: a */
    public int mo62641a() {
        return this.timeoutInSec;
    }

    /* renamed from: b */
    public boolean mo62642b() {
        return this.enabled;
    }

    /* renamed from: c */
    public int mo62643c() {
        return this.discoveryIntervalInMinutes;
    }
}
