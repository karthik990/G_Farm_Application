package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzfn;
import com.google.android.gms.internal.firebase_remote_config.zzfo;
import java.io.IOException;

public abstract class zzfn<MessageType extends zzfn<MessageType, BuilderType>, BuilderType extends zzfo<MessageType, BuilderType>> implements zzio {
    private static boolean zzoe = false;
    protected int zzod = 0;

    public final zzfw zzen() {
        try {
            zzge zzx = zzfw.zzx(zzgo());
            zzb(zzx.zzfc());
            return zzx.zzfb();
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
    public int zzeo() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public void zzr(int i) {
        throw new UnsupportedOperationException();
    }
}
