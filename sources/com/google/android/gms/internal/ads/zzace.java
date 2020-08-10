package com.google.android.gms.internal.ads;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.gmsg.zzf;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.ads.internal.zzbc;
import com.google.android.gms.ads.internal.zzbv;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

@zzadh
public final class zzace {
    private final Context mContext;
    private final Object mLock = new Object();
    private int zzadx = -1;
    private int zzady = -1;
    private zzamj zzadz;
    private final DisplayMetrics zzagj;
    private final zzci zzbjc;
    private final zzaji zzbze;
    /* access modifiers changed from: private */
    public final zzbc zzcbc;
    private OnGlobalLayoutListener zzcbd;
    private OnScrollChangedListener zzcbe;
    private final zznx zzvr;

    public zzace(Context context, zzci zzci, zzaji zzaji, zznx zznx, zzbc zzbc) {
        this.mContext = context;
        this.zzbjc = zzci;
        this.zzbze = zzaji;
        this.zzvr = zznx;
        this.zzcbc = zzbc;
        this.zzadz = new zzamj(200);
        zzbv.zzek();
        this.zzagj = zzakk.zza((WindowManager) context.getSystemService("window"));
    }

    /* access modifiers changed from: private */
    public final void zza(WeakReference<zzaqw> weakReference, boolean z) {
        if (weakReference != null) {
            zzaqw zzaqw = (zzaqw) weakReference.get();
            if (zzaqw != null && zzaqw.getView() != null) {
                if (!z || this.zzadz.tryAcquire()) {
                    int[] iArr = new int[2];
                    zzaqw.getView().getLocationOnScreen(iArr);
                    zzkb.zzif();
                    boolean z2 = false;
                    int zzb = zzamu.zzb(this.zzagj, iArr[0]);
                    zzkb.zzif();
                    int zzb2 = zzamu.zzb(this.zzagj, iArr[1]);
                    synchronized (this.mLock) {
                        if (!(this.zzadx == zzb && this.zzady == zzb2)) {
                            this.zzadx = zzb;
                            this.zzady = zzb2;
                            zzasc zzuf = zzaqw.zzuf();
                            int i = this.zzadx;
                            int i2 = this.zzady;
                            if (!z) {
                                z2 = true;
                            }
                            zzuf.zza(i, i2, z2);
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(zzaoj zzaoj, zzaqw zzaqw, boolean z) {
        this.zzcbc.zzdw();
        zzaoj.set(zzaqw);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(JSONObject jSONObject, zzaoj zzaoj) {
        try {
            zzbv.zzel();
            zzaqw zza = zzarc.zza(this.mContext, zzasi.zzvq(), "native-video", false, false, this.zzbjc, this.zzbze.zzcgs.zzacr, this.zzvr, null, this.zzcbc.zzbi(), this.zzbze.zzcoq);
            zza.zza(zzasi.zzvr());
            this.zzcbc.zzf(zza);
            WeakReference weakReference = new WeakReference(zza);
            zzasc zzuf = zza.zzuf();
            if (this.zzcbd == null) {
                this.zzcbd = new zzack(this, weakReference);
            }
            OnGlobalLayoutListener onGlobalLayoutListener = this.zzcbd;
            if (this.zzcbe == null) {
                this.zzcbe = new zzacl(this, weakReference);
            }
            zzuf.zza(onGlobalLayoutListener, this.zzcbe);
            zza.zza("/video", zzf.zzblz);
            zza.zza("/videoMeta", zzf.zzbma);
            zza.zza("/precache", (zzv<? super zzaqw>) new zzaql<Object>());
            zza.zza("/delayPageLoaded", zzf.zzbmd);
            zza.zza("/instrument", zzf.zzbmb);
            zza.zza("/log", zzf.zzblu);
            zza.zza("/videoClicked", zzf.zzblv);
            zza.zza("/trackActiveViewUnit", (zzv<? super zzaqw>) new zzaci<Object>(this));
            zza.zza("/untrackActiveViewUnit", (zzv<? super zzaqw>) new zzacj<Object>(this));
            zza.zzuf().zza((zzase) new zzacg(zza, jSONObject));
            zza.zzuf().zza((zzasd) new zzach(this, zzaoj, zza));
            zza.loadUrl((String) zzkb.zzik().zzd(zznk.zzbbs));
        } catch (Exception e) {
            zzakb.zzc("Exception occurred while getting video view", e);
            zzaoj.set(null);
        }
    }
}
