package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfg;
import com.google.android.gms.internal.firebase_auth.zzfj;
import java.util.ArrayList;
import java.util.List;

final class zzg implements zzez<zzfj> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;
    private final /* synthetic */ zzfg zzlg;
    private final /* synthetic */ zzem zzlh;
    private final /* synthetic */ zzes zzli;
    private final /* synthetic */ zzew zzlj;

    zzg(zzb zzb, zzfg zzfg, zzem zzem, zzdm zzdm, zzes zzes, zzew zzew) {
        this.zzle = zzb;
        this.zzlg = zzfg;
        this.zzlh = zzem;
        this.zzla = zzdm;
        this.zzli = zzes;
        this.zzlj = zzew;
    }

    public final void zzbv(String str) {
        this.zzlj.zzbv(str);
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzfj zzfj = (zzfj) obj;
        if (this.zzlg.zzcp("EMAIL")) {
            this.zzlh.zzcf(null);
        } else if (this.zzlg.getEmail() != null) {
            this.zzlh.zzcf(this.zzlg.getEmail());
        }
        if (this.zzlg.zzcp("DISPLAY_NAME")) {
            this.zzlh.zzcg(null);
        } else if (this.zzlg.getDisplayName() != null) {
            this.zzlh.zzcg(this.zzlg.getDisplayName());
        }
        if (this.zzlg.zzcp("PHOTO_URL")) {
            this.zzlh.zzch(null);
        } else if (this.zzlg.zzam() != null) {
            this.zzlh.zzch(this.zzlg.zzam());
        }
        if (!TextUtils.isEmpty(this.zzlg.getPassword())) {
            this.zzlh.zzci(Base64Utils.encode("redacted".getBytes()));
        }
        List zzes = zzfj.zzes();
        if (zzes == null) {
            zzes = new ArrayList();
        }
        this.zzlh.zzc(zzes);
        this.zzla.zza(zzb.zza(this.zzli, zzfj), this.zzlh);
    }
}
