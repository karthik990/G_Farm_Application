package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzpl;
import com.google.android.gms.internal.gtm.zzpm;

public abstract class zzpm<MessageType extends zzpl<MessageType, BuilderType>, BuilderType extends zzpm<MessageType, BuilderType>> implements zzsl {
    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    /* renamed from: zzmx */
    public abstract BuilderType clone();

    public final /* synthetic */ zzsl zza(zzsk zzsk) {
        if (zzpi().getClass().isInstance(zzsk)) {
            return zza((MessageType) (zzpl) zzsk);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
