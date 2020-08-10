package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zztd extends zztc<FieldDescriptorType, Object> {
    zztd(int i) {
        super(i, null);
    }

    public final void zzmi() {
        if (!isImmutable()) {
            for (int i = 0; i < zzra(); i++) {
                Entry zzbv = zzbv(i);
                if (((zzqv) zzbv.getKey()).zzoz()) {
                    zzbv.setValue(Collections.unmodifiableList((List) zzbv.getValue()));
                }
            }
            for (Entry entry : zzrb()) {
                if (((zzqv) entry.getKey()).zzoz()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzmi();
    }
}
