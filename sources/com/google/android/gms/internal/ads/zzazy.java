package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzazy;
import com.google.android.gms.internal.ads.zzazz;
import java.io.IOException;

public abstract class zzazy<MessageType extends zzazy<MessageType, BuilderType>, BuilderType extends zzazz<MessageType, BuilderType>> implements zzbcu {
    private static boolean zzdpg = false;
    protected int zzdpf = 0;

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzacw()];
            zzbav zzq = zzbav.zzq(bArr);
            zzb(zzq);
            zzq.zzacl();
            return bArr;
        } catch (IOException e) {
            String str = "byte array";
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + str.length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append(str);
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    public final zzbah zzaav() {
        try {
            zzbam zzbo = zzbah.zzbo(zzacw());
            zzb(zzbo.zzabj());
            return zzbo.zzabi();
        } catch (IOException e) {
            String str = "ByteString";
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + str.length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append(str);
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    /* access modifiers changed from: 0000 */
    public int zzaaw() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public void zzbj(int i) {
        throw new UnsupportedOperationException();
    }
}
