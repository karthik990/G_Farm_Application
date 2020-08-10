package com.google.android.gms.tagmanager;

import java.util.Map;

final class zzga implements zzb {
    private final /* synthetic */ TagManager zzalh;

    zzga(TagManager tagManager) {
        this.zzalh = tagManager;
    }

    public final void zzc(Map<String, Object> map) {
        Object obj = map.get("event");
        if (obj != null) {
            this.zzalh.zzbl(obj.toString());
        }
    }
}
