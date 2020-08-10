package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

public final class zzazj implements zzauk {
    private Mac zzdoj;
    private final int zzdok;
    private final String zzdol;
    private final Key zzdom;

    public zzazj(String str, Key key, int i) throws GeneralSecurityException {
        if (i >= 10) {
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1823053428) {
                if (hashCode != 392315118) {
                    if (hashCode == 392317873 && str.equals("HMACSHA512")) {
                        c = 2;
                    }
                } else if (str.equals("HMACSHA256")) {
                    c = 1;
                }
            } else if (str.equals("HMACSHA1")) {
                c = 0;
            }
            String str2 = "tag size too big";
            if (c != 0) {
                if (c != 1) {
                    if (c != 2) {
                        String str3 = "unknown Hmac algorithm: ";
                        String valueOf = String.valueOf(str);
                        throw new NoSuchAlgorithmException(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                    } else if (i > 64) {
                        throw new InvalidAlgorithmParameterException(str2);
                    }
                } else if (i > 32) {
                    throw new InvalidAlgorithmParameterException(str2);
                }
            } else if (i > 20) {
                throw new InvalidAlgorithmParameterException(str2);
            }
            this.zzdol = str;
            this.zzdok = i;
            this.zzdom = key;
            this.zzdoj = (Mac) zzayy.zzdoa.zzek(str);
            this.zzdoj.init(key);
            return;
        }
        throw new InvalidAlgorithmParameterException("tag size too small, need at least 10 bytes");
    }

    public final byte[] zzg(byte[] bArr) throws GeneralSecurityException {
        Mac mac;
        try {
            mac = (Mac) this.zzdoj.clone();
        } catch (CloneNotSupportedException unused) {
            mac = (Mac) zzayy.zzdoa.zzek(this.zzdol);
            mac.init(this.zzdom);
        }
        mac.update(bArr);
        byte[] bArr2 = new byte[this.zzdok];
        System.arraycopy(mac.doFinal(), 0, bArr2, 0, this.zzdok);
        return bArr2;
    }
}
