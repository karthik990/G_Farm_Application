package com.google.android.gms.internal.firebase_remote_config;

final class zzdm extends zzdl {
    private final char zzhe;

    zzdm(char c) {
        this.zzhe = c;
    }

    public final boolean zzb(char c) {
        return c == this.zzhe;
    }

    public final String toString() {
        String zzd = zzdk.zzc(this.zzhe);
        StringBuilder sb = new StringBuilder(String.valueOf(zzd).length() + 18);
        sb.append("CharMatcher.is('");
        sb.append(zzd);
        sb.append("')");
        return sb.toString();
    }
}
