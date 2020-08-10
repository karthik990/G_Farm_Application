package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.ads.internal.zzbv;
import java.util.Map;

@zzadh
public final class zzaqd implements zzv<zzapw> {
    public final /* synthetic */ void zza(Object obj, Map map) {
        zzapw zzapw = (zzapw) obj;
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbae)).booleanValue()) {
            zzarl zztm = zzapw.zztm();
            String str = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
            if (zztm == null) {
                try {
                    zzarl zzarl = new zzarl(zzapw, Float.parseFloat((String) map.get("duration")), str.equals(map.get("customControlsAllowed")), str.equals(map.get("clickToExpandAllowed")));
                    zzapw.zza(zzarl);
                    zztm = zzarl;
                } catch (NullPointerException e) {
                    e = e;
                    zzakb.zzb("Unable to parse videoMeta message.", e);
                    zzbv.zzeo().zza(e, "VideoMetaGmsgHandler.onGmsg");
                } catch (NumberFormatException e2) {
                    e = e2;
                    zzakb.zzb("Unable to parse videoMeta message.", e);
                    zzbv.zzeo().zza(e, "VideoMetaGmsgHandler.onGmsg");
                }
            }
            boolean equals = str.equals(map.get("muted"));
            float parseFloat = Float.parseFloat((String) map.get("currentTime"));
            int parseInt = Integer.parseInt((String) map.get("playbackState"));
            if (parseInt < 0 || 3 < parseInt) {
                parseInt = 0;
            }
            String str2 = (String) map.get("aspectRatio");
            float parseFloat2 = TextUtils.isEmpty(str2) ? 0.0f : Float.parseFloat(str2);
            if (zzakb.isLoggable(3)) {
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 79);
                sb.append("Video Meta GMSG: isMuted : ");
                sb.append(equals);
                sb.append(" , playbackState : ");
                sb.append(parseInt);
                sb.append(" , aspectRatio : ");
                sb.append(str2);
                zzakb.zzck(sb.toString());
            }
            zztm.zza(parseFloat, parseInt, equals, parseFloat2);
        }
    }
}
