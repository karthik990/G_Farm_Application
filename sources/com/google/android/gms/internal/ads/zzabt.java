package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.gms.ads.internal.zzbc;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzadh
public final class zzabt extends zzajx {
    private final Object mLock;
    /* access modifiers changed from: private */
    public final zzabm zzbzd;
    private final zzaji zzbze;
    private final zzaej zzbzf;
    private final zzabv zzbzu;
    private Future<zzajh> zzbzv;

    public zzabt(Context context, zzbc zzbc, zzaji zzaji, zzci zzci, zzabm zzabm, zznx zznx) {
        zzabv zzabv = new zzabv(context, zzbc, new zzalt(context), zzci, zzaji, zznx);
        this(zzaji, zzabm, zzabv);
    }

    private zzabt(zzaji zzaji, zzabm zzabm, zzabv zzabv) {
        this.mLock = new Object();
        this.zzbze = zzaji;
        this.zzbzf = zzaji.zzcos;
        this.zzbzd = zzabm;
        this.zzbzu = zzabv;
    }

    public final void onStop() {
        synchronized (this.mLock) {
            if (this.zzbzv != null) {
                this.zzbzv.cancel(true);
            }
        }
    }

    public final void zzdn() {
        int i;
        zzajh zzajh = null;
        try {
            synchronized (this.mLock) {
                this.zzbzv = zzaki.zza(this.zzbzu);
            }
            zzajh = (zzajh) this.zzbzv.get(DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS, TimeUnit.MILLISECONDS);
            i = -2;
        } catch (TimeoutException unused) {
            zzakb.zzdk("Timed out waiting for native ad.");
            this.zzbzv.cancel(true);
            i = 2;
        } catch (InterruptedException | CancellationException | ExecutionException unused2) {
            i = 0;
        }
        zzakk.zzcrm.post(new zzabu(this, zzajh != null ? zzajh : new zzajh(this.zzbze.zzcgs.zzccv, null, null, i, null, null, this.zzbzf.orientation, this.zzbzf.zzbsu, this.zzbze.zzcgs.zzccy, false, null, null, null, null, null, this.zzbzf.zzcer, this.zzbze.zzacv, this.zzbzf.zzcep, this.zzbze.zzcoh, this.zzbzf.zzceu, this.zzbzf.zzcev, this.zzbze.zzcob, null, null, null, null, this.zzbze.zzcos.zzcfh, this.zzbze.zzcos.zzcfi, null, null, this.zzbzf.zzcfl, this.zzbze.zzcoq, this.zzbze.zzcos.zzzl, false, this.zzbze.zzcos.zzcfp, null, this.zzbze.zzcos.zzzm, this.zzbze.zzcos.zzcfq)));
    }
}
