package com.google.android.gms.internal.ads;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.util.Collections;
import java.util.Map;

public abstract class zzr<T> implements Comparable<zzr<T>> {
    private final Object mLock;
    /* access modifiers changed from: private */
    public final zza zzae;
    private final int zzaf;
    private final String zzag;
    private final int zzah;
    private zzy zzai;
    private Integer zzaj;
    private zzv zzak;
    private boolean zzal;
    private boolean zzam;
    private boolean zzan;
    private boolean zzao;
    private zzab zzap;
    private zzc zzaq;
    private zzt zzar;

    public zzr(int i, String str, zzy zzy) {
        this.zzae = zza.zzbk ? new zza() : null;
        this.mLock = new Object();
        this.zzal = true;
        int i2 = 0;
        this.zzam = false;
        this.zzan = false;
        this.zzao = false;
        this.zzaq = null;
        this.zzaf = i;
        this.zzag = str;
        this.zzai = zzy;
        this.zzap = new zzh();
        if (!TextUtils.isEmpty(str)) {
            Uri parse = Uri.parse(str);
            if (parse != null) {
                String host = parse.getHost();
                if (host != null) {
                    i2 = host.hashCode();
                }
            }
        }
        this.zzah = i2;
    }

    public /* synthetic */ int compareTo(Object obj) {
        zzr zzr = (zzr) obj;
        zzu zzu = zzu.NORMAL;
        zzu zzu2 = zzu.NORMAL;
        return zzu == zzu2 ? this.zzaj.intValue() - zzr.zzaj.intValue() : zzu2.ordinal() - zzu.ordinal();
    }

    public Map<String, String> getHeaders() throws zza {
        return Collections.emptyMap();
    }

    public final int getMethod() {
        return this.zzaf;
    }

    public final String getUrl() {
        return this.zzag;
    }

    public final boolean isCanceled() {
        synchronized (this.mLock) {
        }
        return false;
    }

    public String toString() {
        String valueOf = String.valueOf(Integer.toHexString(this.zzah));
        String str = "0x";
        String concat = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        String str2 = "[ ] ";
        String str3 = this.zzag;
        String valueOf2 = String.valueOf(zzu.NORMAL);
        String valueOf3 = String.valueOf(this.zzaj);
        StringBuilder sb = new StringBuilder(str2.length() + 3 + String.valueOf(str3).length() + String.valueOf(concat).length() + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
        sb.append(str2);
        sb.append(str3);
        String str4 = " ";
        sb.append(str4);
        sb.append(concat);
        sb.append(str4);
        sb.append(valueOf2);
        sb.append(str4);
        sb.append(valueOf3);
        return sb.toString();
    }

    public final zzr<?> zza(int i) {
        this.zzaj = Integer.valueOf(i);
        return this;
    }

    public final zzr<?> zza(zzc zzc) {
        this.zzaq = zzc;
        return this;
    }

    public final zzr<?> zza(zzv zzv) {
        this.zzak = zzv;
        return this;
    }

    /* access modifiers changed from: protected */
    public abstract zzx<T> zza(zzp zzp);

    /* access modifiers changed from: 0000 */
    public final void zza(zzt zzt) {
        synchronized (this.mLock) {
            this.zzar = zzt;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzx<?> zzx) {
        zzt zzt;
        synchronized (this.mLock) {
            zzt = this.zzar;
        }
        if (zzt != null) {
            zzt.zza(this, zzx);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zza(T t);

    public final void zzb(zzae zzae2) {
        zzy zzy;
        synchronized (this.mLock) {
            zzy = this.zzai;
        }
        if (zzy != null) {
            zzy.zzd(zzae2);
        }
    }

    public final void zzb(String str) {
        if (zza.zzbk) {
            this.zzae.zza(str, Thread.currentThread().getId());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzc(String str) {
        zzv zzv = this.zzak;
        if (zzv != null) {
            zzv.zzf(this);
        }
        if (zza.zzbk) {
            long id = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new zzs(this, str, id));
            } else {
                this.zzae.zza(str, id);
                this.zzae.zzc(toString());
            }
        }
    }

    public final int zze() {
        return this.zzah;
    }

    public final zzc zzf() {
        return this.zzaq;
    }

    public byte[] zzg() throws zza {
        return null;
    }

    public final boolean zzh() {
        return this.zzal;
    }

    public final int zzi() {
        return this.zzap.zzc();
    }

    public final zzab zzj() {
        return this.zzap;
    }

    public final void zzk() {
        synchronized (this.mLock) {
            this.zzan = true;
        }
    }

    public final boolean zzl() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzan;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public final void zzm() {
        zzt zzt;
        synchronized (this.mLock) {
            zzt = this.zzar;
        }
        if (zzt != null) {
            zzt.zza(this);
        }
    }
}
