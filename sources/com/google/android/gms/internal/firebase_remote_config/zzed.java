package com.google.android.gms.internal.firebase_remote_config;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

final class zzed {
    private final ConcurrentHashMap<zzee, List<Throwable>> zziw = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzix = new ReferenceQueue<>();

    zzed() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Reference poll = this.zzix.poll();
        while (poll != null) {
            this.zziw.remove(poll);
            poll = this.zzix.poll();
        }
        List<Throwable> list = (List) this.zziw.get(new zzee(th, null));
        if (!z || list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        List<Throwable> list2 = (List) this.zziw.putIfAbsent(new zzee(th, this.zzix), vector);
        return list2 == null ? vector : list2;
    }
}
