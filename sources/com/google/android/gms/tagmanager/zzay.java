package com.google.android.gms.tagmanager;

import java.util.Arrays;

final class zzay {
    final String zzagg;
    final byte[] zzagh;

    zzay(String str, byte[] bArr) {
        this.zzagg = str;
        this.zzagh = bArr;
    }

    public final String toString() {
        String str = this.zzagg;
        int hashCode = Arrays.hashCode(this.zzagh);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 54);
        sb.append("KeyAndSerialized: key = ");
        sb.append(str);
        sb.append(" serialized hash = ");
        sb.append(hashCode);
        return sb.toString();
    }
}
