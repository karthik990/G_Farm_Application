package com.startapp.android.publish.adsCommon;

import android.content.Context;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.adsCommon.h */
/* compiled from: StartAppSDK */
public abstract class C5033h extends C4925Ad {
    private static final long serialVersionUID = 1;
    private List<AdDetails> adsDetails = null;

    public C5033h(Context context, Placement placement) {
        super(context, placement);
    }

    /* renamed from: a */
    public void mo62335a(List<AdDetails> list) {
        this.adsDetails = list;
        mo61344b();
        mo61342a();
    }

    /* renamed from: a */
    private void mo61342a() {
        boolean z = true;
        for (AdDetails isBelowMinCPM : this.adsDetails) {
            if (!isBelowMinCPM.getIsBelowMinCPM()) {
                z = false;
            }
        }
        this.belowMinCPM = z;
    }

    /* renamed from: d */
    public List<AdDetails> mo62336d() {
        return this.adsDetails;
    }

    /* renamed from: b */
    private void mo61344b() {
        List<AdDetails> list = this.adsDetails;
        Long l = null;
        if (list != null) {
            for (AdDetails adDetails : list) {
                if (!(adDetails == null || adDetails.getTtl() == null)) {
                    if (l == null || adDetails.getTtl().longValue() < l.longValue()) {
                        l = adDetails.getTtl();
                    }
                }
            }
        }
        if (l != null) {
            this.adCacheTtl = Long.valueOf(TimeUnit.SECONDS.toMillis(l.longValue()));
        }
    }
}
