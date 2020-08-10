package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzpl;
import com.google.android.gms.internal.gtm.zzpm;
import java.io.IOException;

public abstract class zzpl<MessageType extends zzpl<MessageType, BuilderType>, BuilderType extends zzpm<MessageType, BuilderType>> implements zzsk {
    private static boolean zzavq = false;
    protected int zzavp = 0;

    public final zzps zzmv() {
        try {
            zzqa zzam = zzps.zzam(zzpe());
            zzb(zzam.zznh());
            return zzam.zzng();
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
    public int zzmw() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public void zzag(int i) {
        throw new UnsupportedOperationException();
    }
}
