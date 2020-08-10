package com.google.android.gms.internal.ads;

import java.lang.reflect.InvocationTargetException;

public final class zzdv extends zzei {
    private long zzts = -1;

    public zzdv(zzcz zzcz, String str, String str2, zzba zzba, int i, int i2) {
        super(zzcz, str, str2, zzba, i, 12);
    }

    /* access modifiers changed from: protected */
    public final void zzar() throws IllegalAccessException, InvocationTargetException {
        this.zztq.zzdm = Long.valueOf(-1);
        this.zztq.zzdm = (Long) this.zztz.invoke(null, new Object[]{this.zzps.getContext()});
    }
}
