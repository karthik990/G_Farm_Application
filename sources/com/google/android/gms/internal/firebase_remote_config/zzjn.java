package com.google.android.gms.internal.firebase_remote_config;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzjn extends zzjm<FieldDescriptorType, Object> {
    zzjn(int i) {
        super(i, null);
    }

    public final void zzer() {
        if (!isImmutable()) {
            for (int i = 0; i < zziv(); i++) {
                Entry zzbo = zzbo(i);
                if (((zzhc) zzbo.getKey()).zzgt()) {
                    zzbo.setValue(Collections.unmodifiableList((List) zzbo.getValue()));
                }
            }
            for (Entry entry : zziw()) {
                if (((zzhc) entry.getKey()).zzgt()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzer();
    }
}
