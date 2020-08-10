package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.mobiroller.constants.Constants;
import com.twitter.sdk.android.core.internal.VineCardUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import p043io.fabric.sdk.android.services.settings.AppSettingsData;

@zzadh
public final class zzaqc implements zzv<zzapw> {
    private boolean zzdau;

    private static int zza(Context context, Map<String, String> map, String str, int i) {
        String str2 = (String) map.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            zzkb.zzif();
            return zzamu.zza(context, Integer.parseInt(str2));
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34 + String.valueOf(str2).length());
            sb.append("Could not parse ");
            sb.append(str);
            sb.append(" in a video GMSG: ");
            sb.append(str2);
            zzakb.zzdk(sb.toString());
            return i;
        }
    }

    private static void zza(zzapi zzapi, Map<String, String> map) {
        String str = (String) map.get("minBufferMs");
        String str2 = (String) map.get("maxBufferMs");
        String str3 = (String) map.get("bufferForPlaybackMs");
        String str4 = (String) map.get("bufferForPlaybackAfterRebufferMs");
        if (str != null) {
            try {
                Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                zzakb.zzdk(String.format("Could not parse buffer parameters in loadControl video GMSG: (%s, %s)", new Object[]{str, str2}));
                return;
            }
        }
        if (str2 != null) {
            Integer.parseInt(str2);
        }
        if (str3 != null) {
            Integer.parseInt(str3);
        }
        if (str4 != null) {
            Integer.parseInt(str4);
        }
    }

    public final /* synthetic */ void zza(Object obj, Map map) {
        int i;
        int i2;
        int i3;
        String[] split;
        zzapw zzapw = (zzapw) obj;
        String str = (String) map.get("action");
        if (str == null) {
            zzakb.zzdk("Action missing from video GMSG.");
            return;
        }
        if (zzakb.isLoggable(3)) {
            JSONObject jSONObject = new JSONObject(map);
            jSONObject.remove("google.afma.Notify_dt");
            String jSONObject2 = jSONObject.toString();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(jSONObject2).length());
            sb.append("Video GMSG: ");
            sb.append(str);
            sb.append(" ");
            sb.append(jSONObject2);
            zzakb.zzck(sb.toString());
        }
        if ("background".equals(str)) {
            String str2 = (String) map.get(TtmlNode.ATTR_TTS_COLOR);
            if (TextUtils.isEmpty(str2)) {
                zzakb.zzdk("Color parameter missing from color video GMSG.");
                return;
            }
            try {
                zzapw.setBackgroundColor(Color.parseColor(str2));
            } catch (IllegalArgumentException unused) {
                zzakb.zzdk("Invalid color parameter in video GMSG.");
            }
        } else {
            if ("decoderProps".equals(str)) {
                String str3 = (String) map.get("mimeTypes");
                if (str3 == null) {
                    zzakb.zzdk("No MIME types specified for decoder properties inspection.");
                    zzapi.zza(zzapw, "missingMimeTypes");
                } else if (VERSION.SDK_INT < 16) {
                    zzakb.zzdk("Video decoder properties available on API versions >= 16.");
                    zzapi.zza(zzapw, "deficientApiVersion");
                } else {
                    HashMap hashMap = new HashMap();
                    for (String str4 : str3.split(",")) {
                        hashMap.put(str4, zzams.zzdd(str4.trim()));
                    }
                    zzapi.zza(zzapw, (Map<String, List<Map<String, Object>>>) hashMap);
                }
            } else {
                zzapn zztl = zzapw.zztl();
                if (zztl == null) {
                    zzakb.zzdk("Could not get underlay container for a video GMSG.");
                    return;
                }
                boolean equals = AppSettingsData.STATUS_NEW.equals(str);
                boolean equals2 = Constants.KEY_RSS_POSITION.equals(str);
                String str5 = "y";
                String str6 = "x";
                if (equals || equals2) {
                    Context context = zzapw.getContext();
                    int zza = zza(context, map, str6, 0);
                    int zza2 = zza(context, map, str5, 0);
                    int zza3 = zza(context, map, "w", -1);
                    int zza4 = zza(context, map, "h", -1);
                    if (((Boolean) zzkb.zzik().zzd(zznk.zzbca)).booleanValue()) {
                        i2 = Math.min(zza3, zzapw.zzts() - zza);
                        i = Math.min(zza4, zzapw.zztr() - zza2);
                    } else {
                        i2 = zza3;
                        i = zza4;
                    }
                    try {
                        i3 = Integer.parseInt((String) map.get(VineCardUtils.PLAYER_CARD));
                    } catch (NumberFormatException unused2) {
                        i3 = 0;
                    }
                    boolean parseBoolean = Boolean.parseBoolean((String) map.get("spherical"));
                    if (!equals || zztl.zzth() != null) {
                        zztl.zze(zza, zza2, i2, i);
                        return;
                    }
                    zztl.zza(zza, zza2, i2, i, i3, parseBoolean, new zzapv((String) map.get("flags")));
                    zzapi zzth = zztl.zzth();
                    if (zzth != null) {
                        zza(zzth, map);
                    }
                    return;
                }
                zzapi zzth2 = zztl.zzth();
                if (zzth2 == null) {
                    zzapi.zza(zzapw);
                } else if ("click".equals(str)) {
                    Context context2 = zzapw.getContext();
                    int zza5 = zza(context2, map, str6, 0);
                    int zza6 = zza(context2, map, str5, 0);
                    long uptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, (float) zza5, (float) zza6, 0);
                    zzth2.zzf(obtain);
                    obtain.recycle();
                } else if ("currentTime".equals(str)) {
                    String str7 = (String) map.get("time");
                    if (str7 == null) {
                        zzakb.zzdk("Time parameter missing from currentTime video GMSG.");
                        return;
                    }
                    try {
                        zzth2.seekTo((int) (Float.parseFloat(str7) * 1000.0f));
                    } catch (NumberFormatException unused3) {
                        String str8 = "Could not parse time parameter from currentTime video GMSG: ";
                        String valueOf = String.valueOf(str7);
                        zzakb.zzdk(valueOf.length() != 0 ? str8.concat(valueOf) : new String(str8));
                    }
                } else if ("hide".equals(str)) {
                    zzth2.setVisibility(4);
                } else if ("load".equals(str)) {
                    zzth2.zzta();
                } else if ("loadControl".equals(str)) {
                    zza(zzth2, map);
                } else {
                    String str9 = "muted";
                    if (str9.equals(str)) {
                        if (Boolean.parseBoolean((String) map.get(str9))) {
                            zzth2.zztb();
                        } else {
                            zzth2.zztc();
                        }
                    } else if ("pause".equals(str)) {
                        zzth2.pause();
                    } else if ("play".equals(str)) {
                        zzth2.play();
                    } else if ("show".equals(str)) {
                        zzth2.setVisibility(0);
                    } else {
                        String str10 = "src";
                        if (str10.equals(str)) {
                            zzth2.zzdn((String) map.get(str10));
                        } else if ("touchMove".equals(str)) {
                            Context context3 = zzapw.getContext();
                            zzth2.zza((float) zza(context3, map, "dx", 0), (float) zza(context3, map, "dy", 0));
                            if (!this.zzdau) {
                                zzapw.zznp();
                                this.zzdau = true;
                            }
                        } else {
                            String str11 = "volume";
                            if (str11.equals(str)) {
                                String str12 = (String) map.get(str11);
                                if (str12 == null) {
                                    zzakb.zzdk("Level parameter missing from volume video GMSG.");
                                    return;
                                }
                                try {
                                    zzth2.setVolume(Float.parseFloat(str12));
                                } catch (NumberFormatException unused4) {
                                    String str13 = "Could not parse volume parameter from volume video GMSG: ";
                                    String valueOf2 = String.valueOf(str12);
                                    zzakb.zzdk(valueOf2.length() != 0 ? str13.concat(valueOf2) : new String(str13));
                                }
                            } else if ("watermark".equals(str)) {
                                zzth2.zztd();
                            } else {
                                String str14 = "Unknown video action: ";
                                String valueOf3 = String.valueOf(str);
                                zzakb.zzdk(valueOf3.length() != 0 ? str14.concat(valueOf3) : new String(str14));
                            }
                        }
                    }
                }
            }
        }
    }
}
