package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzfx;
import com.google.android.gms.internal.firebase_auth.zzga;

public abstract class zzga<MessageType extends zzfx<MessageType, BuilderType>, BuilderType extends zzga<MessageType, BuilderType>> implements zzjb {
    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    /* renamed from: zzfw */
    public abstract BuilderType clone();

    public final /* synthetic */ zzjb zzb(zzjc zzjc) {
        if (zzii().getClass().isInstance(zzjc)) {
            return zza((zzfx) zzjc);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
