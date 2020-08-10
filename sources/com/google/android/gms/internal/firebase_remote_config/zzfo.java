package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzfn;
import com.google.android.gms.internal.firebase_remote_config.zzfo;

public abstract class zzfo<MessageType extends zzfn<MessageType, BuilderType>, BuilderType extends zzfo<MessageType, BuilderType>> implements zzip {
    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    /* renamed from: zzep */
    public abstract BuilderType clone();

    public final /* synthetic */ zzip zza(zzio zzio) {
        if (zzgz().getClass().isInstance(zzio)) {
            return zza((MessageType) (zzfn) zzio);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
