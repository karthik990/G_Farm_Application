package com.google.android.gms.internal.firebase_auth;

final class zzag extends zzad {
    private final char zzgj;

    zzag(char c) {
        this.zzgj = c;
    }

    public final boolean zza(char c) {
        return c == this.zzgj;
    }

    public final String toString() {
        String zzc = zzae.zzb(this.zzgj);
        StringBuilder sb = new StringBuilder(String.valueOf(zzc).length() + 18);
        sb.append("CharMatcher.is('");
        sb.append(zzc);
        sb.append("')");
        return sb.toString();
    }
}
