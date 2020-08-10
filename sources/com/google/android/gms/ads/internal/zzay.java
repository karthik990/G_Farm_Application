package com.google.android.gms.ads.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzagr;
import com.google.android.gms.internal.ads.zzaib;
import com.google.android.gms.internal.ads.zzaic;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzald;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzlk;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzwx;
import com.google.android.gms.internal.ads.zzwy;
import com.google.android.gms.internal.ads.zzxq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.ParametersAreNonnullByDefault;

@zzadh
@ParametersAreNonnullByDefault
public final class zzay extends zzlk {
    private static final Object sLock = new Object();
    private static zzay zzzu;
    private final Context mContext;
    private final Object mLock = new Object();
    private boolean zzzv;
    private zzang zzzw;

    private zzay(Context context, zzang zzang) {
        this.mContext = context;
        this.zzzw = zzang;
        this.zzzv = false;
    }

    public static zzay zza(Context context, zzang zzang) {
        zzay zzay;
        synchronized (sLock) {
            if (zzzu == null) {
                zzzu = new zzay(context.getApplicationContext(), zzang);
            }
            zzay = zzzu;
        }
        return zzay;
    }

    public final void setAppMuted(boolean z) {
        zzbv.zzfj().setAppMuted(z);
    }

    public final void setAppVolume(float f) {
        zzbv.zzfj().setAppVolume(f);
    }

    public final void zza() {
        synchronized (sLock) {
            if (this.zzzv) {
                zzakb.zzdk("Mobile ads is initialized already.");
                return;
            }
            this.zzzv = true;
            zznk.initialize(this.mContext);
            zzbv.zzeo().zzd(this.mContext, this.zzzw);
            zzbv.zzeq().initialize(this.mContext);
        }
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Runnable runnable) {
        Context context = this.mContext;
        Preconditions.checkMainThread("Adapters must be initialized on the main thread.");
        Map zzpw = zzbv.zzeo().zzqh().zzra().zzpw();
        if (zzpw != null && !zzpw.isEmpty()) {
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Throwable th) {
                    zzakb.zzc("Could not initialize rewarded ads.", th);
                    return;
                }
            }
            zzagr zzox = zzagr.zzox();
            if (zzox != null) {
                Collection<zzwy> values = zzpw.values();
                HashMap hashMap = new HashMap();
                IObjectWrapper wrap = ObjectWrapper.wrap(context);
                for (zzwy zzwy : values) {
                    for (zzwx zzwx : zzwy.zzbsm) {
                        String str = zzwx.zzbsb;
                        for (String str2 : zzwx.zzbrt) {
                            if (!hashMap.containsKey(str2)) {
                                hashMap.put(str2, new ArrayList());
                            }
                            if (str != null) {
                                ((Collection) hashMap.get(str2)).add(str);
                            }
                        }
                    }
                }
                for (Entry entry : hashMap.entrySet()) {
                    String str3 = (String) entry.getKey();
                    try {
                        zzaib zzca = zzox.zzca(str3);
                        if (zzca != null) {
                            zzxq zzpe = zzca.zzpe();
                            if (!zzpe.isInitialized()) {
                                if (zzpe.zzms()) {
                                    zzpe.zza(wrap, (zzaic) zzca.zzpf(), (List) entry.getValue());
                                    String str4 = "Initialized rewarded video mediation adapter ";
                                    String valueOf = String.valueOf(str3);
                                    zzakb.zzck(valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        StringBuilder sb = new StringBuilder(String.valueOf(str3).length() + 56);
                        sb.append("Failed to initialize rewarded video mediation adapter \"");
                        sb.append(str3);
                        sb.append("\"");
                        zzakb.zzc(sb.toString(), th2);
                    }
                }
            }
        }
    }

    public final void zza(String str, IObjectWrapper iObjectWrapper) {
        if (!TextUtils.isEmpty(str)) {
            zznk.initialize(this.mContext);
            boolean booleanValue = ((Boolean) zzkb.zzik().zzd(zznk.zzbcs)).booleanValue() | ((Boolean) zzkb.zzik().zzd(zznk.zzayd)).booleanValue();
            zzaz zzaz = null;
            if (((Boolean) zzkb.zzik().zzd(zznk.zzayd)).booleanValue()) {
                booleanValue = true;
                zzaz = new zzaz(this, (Runnable) ObjectWrapper.unwrap(iObjectWrapper));
            }
            if (booleanValue) {
                zzbv.zzes().zza(this.mContext, this.zzzw, str, zzaz);
            }
        }
    }

    public final void zzb(IObjectWrapper iObjectWrapper, String str) {
        if (iObjectWrapper == null) {
            zzakb.m1272e("Wrapped context is null. Failed to open debug menu.");
            return;
        }
        Context context = (Context) ObjectWrapper.unwrap(iObjectWrapper);
        if (context == null) {
            zzakb.m1272e("Context is null. Failed to open debug menu.");
            return;
        }
        zzald zzald = new zzald(context);
        zzald.setAdUnitId(str);
        zzald.zzda(this.zzzw.zzcw);
        zzald.showDialog();
    }

    public final float zzdo() {
        return zzbv.zzfj().zzdo();
    }

    public final boolean zzdp() {
        return zzbv.zzfj().zzdp();
    }

    public final void zzt(String str) {
        zznk.initialize(this.mContext);
        if (!TextUtils.isEmpty(str)) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbcs)).booleanValue()) {
                zzbv.zzes().zza(this.mContext, this.zzzw, str, null);
            }
        }
    }
}