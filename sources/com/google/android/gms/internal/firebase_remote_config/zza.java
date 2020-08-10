package com.google.android.gms.internal.firebase_remote_config;

public final class zza {
    public static final String VERSION;
    public static final Integer zza = Integer.valueOf(1);
    public static final Integer zzb = Integer.valueOf(26);
    private static final Integer zzc = Integer.valueOf(0);

    static {
        String valueOf = String.valueOf(zza);
        String valueOf2 = String.valueOf(zzb);
        String valueOf3 = String.valueOf(zzc);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 11 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
        sb.append(valueOf);
        String str = ".";
        sb.append(str);
        sb.append(valueOf2);
        sb.append(str);
        sb.append(valueOf3);
        sb.append("-SNAPSHOT");
        VERSION = sb.toString().toString();
    }
}
