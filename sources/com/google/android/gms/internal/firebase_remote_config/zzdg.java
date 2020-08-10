package com.google.android.gms.internal.firebase_remote_config;

import java.util.List;
import java.util.Map;

public final class zzdg extends zzaw {
    @zzcb
    private String appName;
    @zzcb
    private Map<String, String> entries;
    @zzcb
    private List<zzde> experimentDescriptions;
    @zzcb
    private String state;

    public final Map<String, String> getEntries() {
        return this.entries;
    }

    public final List<zzde> zzcg() {
        return this.experimentDescriptions;
    }

    public final String getState() {
        return this.state;
    }

    public final /* synthetic */ zzaw zza(String str, Object obj) {
        return (zzdg) zzb(str, obj);
    }

    public final /* synthetic */ zzaw zza() {
        return (zzdg) clone();
    }

    public final /* synthetic */ zzbz zzb() {
        return (zzdg) clone();
    }

    public final /* synthetic */ zzbz zzb(String str, Object obj) {
        return (zzdg) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzdg) super.clone();
    }
}
