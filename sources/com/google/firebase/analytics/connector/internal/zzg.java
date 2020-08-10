package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurement.OnEventListener;

final class zzg implements OnEventListener {
    private final /* synthetic */ zzf zzbtj;

    public zzg(zzf zzf) {
        this.zzbtj = zzf;
    }

    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (str != null && !str.equals(AppMeasurement.CRASH_ORIGIN) && zzc.zzfu(str2)) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(PostalAddressParser.USER_ADDRESS_NAME_KEY, str2);
            bundle2.putLong("timestampInMillis", j);
            bundle2.putBundle("params", bundle);
            this.zzbtj.zzbtf.onMessageTriggered(3, bundle2);
        }
    }
}
