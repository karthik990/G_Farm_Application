package com.google.android.gms.internal.firebase_remote_config;

public final class zzdb extends zzdc<zzdg> {
    @zzcb
    private String namespace;
    @zzcb
    private String project;

    protected zzdb(zzda zzda, String str, String str2, zzdf zzdf) {
        super(zzda.zzgv.zzgu, "POST", "v1/projects/{project}/namespaces/{namespace}:fetch", zzdf, zzdg.class);
        this.project = (String) zzci.checkNotNull(str, "Required parameter project must be specified.");
        this.namespace = (String) zzci.checkNotNull(str2, "Required parameter namespace must be specified.");
    }

    public final /* synthetic */ zzdc zzg(String str, Object obj) {
        return (zzdb) zzb(str, obj);
    }

    public final /* synthetic */ zzm zzd(String str, Object obj) {
        return (zzdb) zzb(str, obj);
    }

    public final /* synthetic */ zzf zzc(String str, Object obj) {
        return (zzdb) zzb(str, obj);
    }

    public final /* synthetic */ zzbz zzb(String str, Object obj) {
        return (zzdb) super.zzb(str, obj);
    }
}
