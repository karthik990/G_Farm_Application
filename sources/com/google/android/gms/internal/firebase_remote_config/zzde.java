package com.google.android.gms.internal.firebase_remote_config;

public final class zzde extends zzaw {
    @zzcb
    private String experimentId;
    @zzcb
    private String experimentStartTime;
    @zzbe
    @zzcb
    private Long timeToLiveMillis;
    @zzcb
    private String triggerEvent;
    @zzbe
    @zzcb
    private Long triggerTimeoutMillis;
    @zzcb
    private String variantId;

    public final zzde zzan(String str) {
        this.experimentId = str;
        return this;
    }

    public final zzde zzao(String str) {
        this.experimentStartTime = str;
        return this;
    }

    public final zzde zza(Long l) {
        this.timeToLiveMillis = l;
        return this;
    }

    public final zzde zzap(String str) {
        this.triggerEvent = str;
        return this;
    }

    public final zzde zzb(Long l) {
        this.triggerTimeoutMillis = l;
        return this;
    }

    public final zzde zzaq(String str) {
        this.variantId = str;
        return this;
    }

    public final /* synthetic */ zzaw zza(String str, Object obj) {
        return (zzde) zzb(str, obj);
    }

    public final /* synthetic */ zzaw zza() {
        return (zzde) clone();
    }

    public final /* synthetic */ zzbz zzb() {
        return (zzde) clone();
    }

    public final /* synthetic */ zzbz zzb(String str, Object obj) {
        return (zzde) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzde) super.clone();
    }
}
