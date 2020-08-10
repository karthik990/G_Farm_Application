package com.google.android.gms.internal.firebase_remote_config;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class zzee extends WeakReference<Throwable> {
    private final int zziy;

    public zzee(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        if (th != null) {
            this.zziy = System.identityHashCode(th);
            return;
        }
        throw new NullPointerException("The referent cannot be null");
    }

    public final int hashCode() {
        return this.zziy;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            zzee zzee = (zzee) obj;
            return this.zziy == zzee.zziy && get() == zzee.get();
        }
    }
}
