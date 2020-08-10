package com.google.android.gms.tagmanager;

import android.text.TextUtils;

final class zzbw {
    private final long zzabb;
    private final long zzagy;
    private final long zzagz;
    private String zzaha;

    /* access modifiers changed from: 0000 */
    public final long zzih() {
        return this.zzagy;
    }

    /* access modifiers changed from: 0000 */
    public final long zzii() {
        return this.zzagz;
    }

    zzbw(long j, long j2, long j3) {
        this.zzagy = j;
        this.zzabb = j2;
        this.zzagz = j3;
    }

    /* access modifiers changed from: 0000 */
    public final String zzij() {
        return this.zzaha;
    }

    /* access modifiers changed from: 0000 */
    public final void zzbc(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim())) {
            this.zzaha = str;
        }
    }
}
