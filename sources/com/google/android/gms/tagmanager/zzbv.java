package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

final class zzbv extends zzbq {

    /* renamed from: ID */
    private static final String f1558ID = zza.HASH.toString();
    private static final String zzags = zzb.ARG0.toString();
    private static final String zzagu = zzb.INPUT_FORMAT.toString();
    private static final String zzagx = zzb.ALGORITHM.toString();

    public zzbv() {
        super(f1558ID, zzags);
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        String str;
        String str2;
        byte[] bArr;
        zzl zzl = (zzl) map.get(zzags);
        if (zzl == null || zzl == zzgj.zzkc()) {
            return zzgj.zzkc();
        }
        String zzc = zzgj.zzc(zzl);
        zzl zzl2 = (zzl) map.get(zzagx);
        if (zzl2 == null) {
            str = MessageDigestAlgorithms.MD5;
        } else {
            str = zzgj.zzc(zzl2);
        }
        zzl zzl3 = (zzl) map.get(zzagu);
        String str3 = "text";
        if (zzl3 == null) {
            str2 = str3;
        } else {
            str2 = zzgj.zzc(zzl3);
        }
        if (str3.equals(str2)) {
            bArr = zzc.getBytes();
        } else if ("base16".equals(str2)) {
            bArr = zzo.decode(zzc);
        } else {
            String str4 = "Hash: unknown input format: ";
            String valueOf = String.valueOf(str2);
            zzdi.zzav(valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
            return zzgj.zzkc();
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return zzgj.zzi(zzo.encode(instance.digest()));
        } catch (NoSuchAlgorithmException unused) {
            String str5 = "Hash: unknown algorithm: ";
            String valueOf2 = String.valueOf(str);
            zzdi.zzav(valueOf2.length() != 0 ? str5.concat(valueOf2) : new String(str5));
            return zzgj.zzkc();
        }
    }
}
