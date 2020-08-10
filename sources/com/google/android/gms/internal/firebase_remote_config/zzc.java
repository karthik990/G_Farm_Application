package com.google.android.gms.internal.firebase_remote_config;

import java.util.List;

public class zzc extends zzaw {
    @zzcb
    private int code;
    @zzcb
    private List<zza> errors;
    @zzcb
    private String message;

    public static class zza extends zzaw {
        @zzcb
        private String domain;
        @zzcb
        private String location;
        @zzcb
        private String locationType;
        @zzcb
        private String message;
        @zzcb
        private String reason;

        public final /* synthetic */ zzaw zza(String str, Object obj) {
            return (zza) zzb(str, obj);
        }

        public final /* synthetic */ zzaw zza() {
            return (zza) clone();
        }

        public final /* synthetic */ zzbz zzb() {
            return (zza) clone();
        }

        public final /* synthetic */ zzbz zzb(String str, Object obj) {
            return (zza) super.zzb(str, obj);
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return (zza) super.clone();
        }
    }

    public final /* synthetic */ zzaw zza(String str, Object obj) {
        return (zzc) zzb(str, obj);
    }

    public final /* synthetic */ zzaw zza() {
        return (zzc) clone();
    }

    public final /* synthetic */ zzbz zzb() {
        return (zzc) clone();
    }

    public final /* synthetic */ zzbz zzb(String str, Object obj) {
        return (zzc) super.zzb(str, obj);
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzc) super.clone();
    }

    static {
        zzbs.zzd(zza.class);
    }
}
