package com.google.android.gms.tagmanager;

import android.content.Context;

public final class zzbb implements zzbx {
    private static final Object zzadq = new Object();
    private static zzbb zzagj;
    private zzej zzaev;
    private zzby zzagk;

    private zzbb(Context context) {
        this(zzbz.zzm(context), new zzfl());
    }

    private zzbb(zzby zzby, zzej zzej) {
        this.zzagk = zzby;
        this.zzaev = zzej;
    }

    public static zzbx zzg(Context context) {
        zzbb zzbb;
        synchronized (zzadq) {
            if (zzagj == null) {
                zzagj = new zzbb(context);
            }
            zzbb = zzagj;
        }
        return zzbb;
    }

    public final boolean zzay(String str) {
        if (!this.zzaev.zzfm()) {
            zzdi.zzac("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
            return false;
        }
        this.zzagk.zzbd(str);
        return true;
    }
}
