package com.google.android.gms.internal.firebase_remote_config;

import java.util.ArrayDeque;
import java.util.Arrays;

final class zzjh {
    private final ArrayDeque<zzfw> zzwq;

    private zzjh() {
        this.zzwq = new ArrayDeque<>();
    }

    /* access modifiers changed from: private */
    public final zzfw zzc(zzfw zzfw, zzfw zzfw2) {
        zze(zzfw);
        zze(zzfw2);
        zzfw zzfw3 = (zzfw) this.zzwq.pop();
        while (!this.zzwq.isEmpty()) {
            zzfw3 = new zzjf((zzfw) this.zzwq.pop(), zzfw3, null);
        }
        return zzfw3;
    }

    private final void zze(zzfw zzfw) {
        while (!zzfw.zzey()) {
            if (zzfw instanceof zzjf) {
                zzjf zzjf = (zzjf) zzfw;
                zze(zzjf.zzwj);
                zzfw = zzjf.zzwk;
            } else {
                String valueOf = String.valueOf(zzfw.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
                sb.append("Has a new type of ByteString been created? Found ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        int zzbm = zzbm(zzfw.size());
        int i = zzjf.zzwh[zzbm + 1];
        if (this.zzwq.isEmpty() || ((zzfw) this.zzwq.peek()).size() >= i) {
            this.zzwq.push(zzfw);
            return;
        }
        int i2 = zzjf.zzwh[zzbm];
        zzfw zzfw2 = (zzfw) this.zzwq.pop();
        while (!this.zzwq.isEmpty() && ((zzfw) this.zzwq.peek()).size() < i2) {
            zzfw2 = new zzjf((zzfw) this.zzwq.pop(), zzfw2, null);
        }
        zzjf zzjf2 = new zzjf(zzfw2, zzfw, null);
        while (!this.zzwq.isEmpty()) {
            if (((zzfw) this.zzwq.peek()).size() >= zzjf.zzwh[zzbm(zzjf2.size()) + 1]) {
                break;
            }
            zzjf2 = new zzjf((zzfw) this.zzwq.pop(), zzjf2, null);
        }
        this.zzwq.push(zzjf2);
    }

    private static int zzbm(int i) {
        int binarySearch = Arrays.binarySearch(zzjf.zzwh, i);
        return binarySearch < 0 ? (-(binarySearch + 1)) - 1 : binarySearch;
    }

    /* synthetic */ zzjh(zzjg zzjg) {
        this();
    }
}
