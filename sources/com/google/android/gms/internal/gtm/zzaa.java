package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Preconditions;
import com.mobiroller.constants.Constants;
import java.util.HashMap;
import java.util.UUID;

public final class zzaa extends zzi<zzaa> {
    private String zzva;
    private int zzvb;
    private int zzvc;
    private String zzvd;
    private String zzve;
    private boolean zzvf;
    private boolean zzvg;

    public zzaa() {
        this(false);
    }

    private zzaa(boolean z) {
        UUID randomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (randomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits == 0) {
            leastSignificantBits = (int) (randomUUID.getMostSignificantBits() & 2147483647L);
            if (leastSignificantBits == 0) {
                Log.e("GAv4", "UUID.randomUUID() returned 0.");
                leastSignificantBits = Integer.MAX_VALUE;
            }
        }
        this(false, leastSignificantBits);
    }

    private zzaa(boolean z, int i) {
        Preconditions.checkNotZero(i);
        this.zzvb = i;
        this.zzvg = false;
    }

    public final String zzca() {
        return this.zzva;
    }

    public final int zzcb() {
        return this.zzvb;
    }

    public final String zzcc() {
        return this.zzve;
    }

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("screenName", this.zzva);
        hashMap.put("interstitial", Boolean.valueOf(this.zzvf));
        hashMap.put("automatic", Boolean.valueOf(this.zzvg));
        hashMap.put(Constants.KEY_SCREEN_ID, Integer.valueOf(this.zzvb));
        hashMap.put("referrerScreenId", Integer.valueOf(this.zzvc));
        hashMap.put("referrerScreenName", this.zzvd);
        hashMap.put("referrerUri", this.zzve);
        return zza((Object) hashMap);
    }

    public final /* synthetic */ void zzb(zzi zzi) {
        zzaa zzaa = (zzaa) zzi;
        if (!TextUtils.isEmpty(this.zzva)) {
            zzaa.zzva = this.zzva;
        }
        int i = this.zzvb;
        if (i != 0) {
            zzaa.zzvb = i;
        }
        int i2 = this.zzvc;
        if (i2 != 0) {
            zzaa.zzvc = i2;
        }
        if (!TextUtils.isEmpty(this.zzvd)) {
            zzaa.zzvd = this.zzvd;
        }
        if (!TextUtils.isEmpty(this.zzve)) {
            String str = this.zzve;
            if (TextUtils.isEmpty(str)) {
                zzaa.zzve = null;
            } else {
                zzaa.zzve = str;
            }
        }
        boolean z = this.zzvf;
        if (z) {
            zzaa.zzvf = z;
        }
        boolean z2 = this.zzvg;
        if (z2) {
            zzaa.zzvg = z2;
        }
    }
}
