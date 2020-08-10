package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class zzayh implements zzazi {
    private final SecretKeySpec zzdmu;
    private final int zzdmv;
    private final int zzdmw = ((Cipher) zzayy.zzdnz.zzek("AES/CTR/NoPadding")).getBlockSize();

    public zzayh(byte[] bArr, int i) throws GeneralSecurityException {
        this.zzdmu = new SecretKeySpec(bArr, "AES");
        if (i < 12 || i > this.zzdmw) {
            throw new GeneralSecurityException("invalid IV size");
        }
        this.zzdmv = i;
    }

    public final byte[] zzk(byte[] bArr) throws GeneralSecurityException {
        int length = bArr.length;
        int i = this.zzdmv;
        if (length <= Integer.MAX_VALUE - i) {
            byte[] bArr2 = new byte[(bArr.length + i)];
            byte[] zzbh = zzazl.zzbh(i);
            System.arraycopy(zzbh, 0, bArr2, 0, this.zzdmv);
            int length2 = bArr.length;
            int i2 = this.zzdmv;
            Cipher cipher = (Cipher) zzayy.zzdnz.zzek("AES/CTR/NoPadding");
            byte[] bArr3 = new byte[this.zzdmw];
            System.arraycopy(zzbh, 0, bArr3, 0, this.zzdmv);
            cipher.init(1, this.zzdmu, new IvParameterSpec(bArr3));
            if (cipher.doFinal(bArr, 0, length2, bArr2, i2) == length2) {
                return bArr2;
            }
            throw new GeneralSecurityException("stored output's length does not match input's length");
        }
        int i3 = Integer.MAX_VALUE - i;
        StringBuilder sb = new StringBuilder(43);
        sb.append("plaintext length can not exceed ");
        sb.append(i3);
        throw new GeneralSecurityException(sb.toString());
    }
}
