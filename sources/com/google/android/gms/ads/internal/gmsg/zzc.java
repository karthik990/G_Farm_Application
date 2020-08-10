package com.google.android.gms.ads.internal.gmsg;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.mobiroller.constants.ChatConstants;
import java.util.Map;

@zzadh
public final class zzc implements zzv<Object> {
    private final zzd zzblm;

    public zzc(zzd zzd) {
        this.zzblm = zzd;
    }

    public final void zza(Object obj, Map<String, String> map) {
        String str = (String) map.get(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        if (str == null) {
            zzakb.zzdk("App event with no name parameter.");
        } else {
            this.zzblm.onAppEvent(str, (String) map.get(ChatConstants.ARG_USER_INFO));
        }
    }
}
