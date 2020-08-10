package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener;

@zzadh
public final class zzsa extends zzrg {
    private final OnCustomTemplateAdLoadedListener zzble;

    public zzsa(OnCustomTemplateAdLoadedListener onCustomTemplateAdLoadedListener) {
        this.zzble = onCustomTemplateAdLoadedListener;
    }

    public final void zzb(zzqs zzqs) {
        this.zzble.onCustomTemplateAdLoaded(zzqv.zza(zzqs));
    }
}
