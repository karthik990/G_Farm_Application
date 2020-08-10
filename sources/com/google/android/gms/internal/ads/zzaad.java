package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.internal.zzbv;
import java.util.Map;

@zzadh
public final class zzaad {
    private final zzaqw zzbnd;
    private final boolean zzbwm;
    private final String zzbwn;

    public zzaad(zzaqw zzaqw, Map<String, String> map) {
        this.zzbnd = zzaqw;
        this.zzbwn = (String) map.get("forceOrientation");
        String str = "allowOrientationChange";
        this.zzbwm = map.containsKey(str) ? Boolean.parseBoolean((String) map.get(str)) : true;
    }

    public final void execute() {
        int i;
        if (this.zzbnd == null) {
            zzakb.zzdk("AdWebView is null");
            return;
        }
        if ("portrait".equalsIgnoreCase(this.zzbwn)) {
            i = zzbv.zzem().zzrm();
        } else {
            i = "landscape".equalsIgnoreCase(this.zzbwn) ? zzbv.zzem().zzrl() : this.zzbwm ? -1 : zzbv.zzem().zzrn();
        }
        this.zzbnd.setRequestedOrientation(i);
    }
}
