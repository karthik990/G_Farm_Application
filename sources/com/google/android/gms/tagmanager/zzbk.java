package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import com.rometools.rome.feed.atom.Content;
import java.util.Map;

final class zzbk extends zzbq {

    /* renamed from: ID */
    private static final String f1551ID = zza.ENCODE.toString();
    private static final String zzags = zzb.ARG0.toString();
    private static final String zzagt = zzb.NO_PADDING.toString();
    private static final String zzagu = zzb.INPUT_FORMAT.toString();
    private static final String zzagv = zzb.OUTPUT_FORMAT.toString();

    public zzbk() {
        super(f1551ID, zzags);
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        String str;
        String str2;
        byte[] bArr;
        String str3;
        zzl zzl = (zzl) map.get(zzags);
        if (zzl == null || zzl == zzgj.zzkc()) {
            return zzgj.zzkc();
        }
        String zzc = zzgj.zzc(zzl);
        zzl zzl2 = (zzl) map.get(zzagu);
        String str4 = "text";
        if (zzl2 == null) {
            str = str4;
        } else {
            str = zzgj.zzc(zzl2);
        }
        zzl zzl3 = (zzl) map.get(zzagv);
        String str5 = "base16";
        if (zzl3 == null) {
            str2 = str5;
        } else {
            str2 = zzgj.zzc(zzl3);
        }
        int i = 2;
        zzl zzl4 = (zzl) map.get(zzagt);
        if (zzl4 != null && zzgj.zzg(zzl4).booleanValue()) {
            i = 3;
        }
        try {
            boolean equals = str4.equals(str);
            String str6 = "base64url";
            String str7 = Content.BASE64;
            if (equals) {
                bArr = zzc.getBytes();
            } else if (str5.equals(str)) {
                bArr = zzo.decode(zzc);
            } else if (str7.equals(str)) {
                bArr = Base64.decode(zzc, i);
            } else if (str6.equals(str)) {
                bArr = Base64.decode(zzc, i | 8);
            } else {
                String str8 = "Encode: unknown input format: ";
                String valueOf = String.valueOf(str);
                zzdi.zzav(valueOf.length() != 0 ? str8.concat(valueOf) : new String(str8));
                return zzgj.zzkc();
            }
            if (str5.equals(str2)) {
                str3 = zzo.encode(bArr);
            } else if (str7.equals(str2)) {
                str3 = Base64.encodeToString(bArr, i);
            } else if (str6.equals(str2)) {
                str3 = Base64.encodeToString(bArr, i | 8);
            } else {
                String str9 = "Encode: unknown output format: ";
                String valueOf2 = String.valueOf(str2);
                zzdi.zzav(valueOf2.length() != 0 ? str9.concat(valueOf2) : new String(str9));
                return zzgj.zzkc();
            }
            return zzgj.zzi(str3);
        } catch (IllegalArgumentException unused) {
            zzdi.zzav("Encode: invalid input:");
            return zzgj.zzkc();
        }
    }
}
