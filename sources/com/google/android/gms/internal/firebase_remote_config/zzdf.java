package com.google.android.gms.internal.firebase_remote_config;

import java.util.Map;

public final class zzdf extends zzaw {
    @zzcb
    private Map<String, String> analyticsUserProperties;
    @zzcb
    private String appId;
    @zzcb
    private String appInstanceId;
    @zzcb
    private String appInstanceIdToken;
    @zzcb
    private String appVersion;
    @zzcb
    private String countryCode;
    @zzcb
    private String languageCode;
    @zzcb
    private String packageName;
    @zzcb
    private String platformVersion;
    @zzcb
    private String sdkVersion;
    @zzcb
    private String timeZone;

    public final zzdf zza(Map<String, String> map) {
        this.analyticsUserProperties = map;
        return this;
    }

    public final zzdf zzar(String str) {
        this.appId = str;
        return this;
    }

    public final zzdf zzas(String str) {
        this.appInstanceId = str;
        return this;
    }

    public final zzdf zzat(String str) {
        this.appInstanceIdToken = str;
        return this;
    }

    public final zzdf zzau(String str) {
        this.appVersion = str;
        return this;
    }

    public final zzdf zzav(String str) {
        this.countryCode = str;
        return this;
    }

    public final zzdf zzaw(String str) {
        this.languageCode = str;
        return this;
    }

    public final zzdf zzax(String str) {
        this.packageName = str;
        return this;
    }

    public final zzdf zzay(String str) {
        this.platformVersion = str;
        return this;
    }

    public final zzdf zzaz(String str) {
        this.sdkVersion = str;
        return this;
    }

    public final zzdf zzba(String str) {
        this.timeZone = str;
        return this;
    }

    public final /* synthetic */ zzaw zza(String str, Object obj) {
        return (zzdf) zzb(str, obj);
    }

    public final /* synthetic */ zzaw zza() {
        return (zzdf) clone();
    }

    public final /* synthetic */ zzbz zzb() {
        return (zzdf) clone();
    }

    public final /* synthetic */ zzbz zzb(String str, Object obj) {
        return (zzdf) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzdf) super.clone();
    }
}
