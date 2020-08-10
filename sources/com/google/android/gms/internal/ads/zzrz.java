package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomClickListener;

@zzadh
public final class zzrz extends zzrd {
    private final OnCustomClickListener zzbld;

    public zzrz(OnCustomClickListener onCustomClickListener) {
        this.zzbld = onCustomClickListener;
    }

    public final void zzb(zzqs zzqs, String str) {
        this.zzbld.onCustomClick(zzqv.zza(zzqs), str);
    }
}
