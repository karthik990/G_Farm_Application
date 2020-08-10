package com.google.android.gms.internal.ads;

import android.location.Location;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

@zzadh
public final class zzafs {
    private static final SimpleDateFormat zzcho = new SimpleDateFormat("yyyyMMdd", Locale.US);

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00e1 A[Catch:{ JSONException -> 0x0269 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00e7 A[Catch:{ JSONException -> 0x0269 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0161 A[Catch:{ JSONException -> 0x0269 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x016a A[Catch:{ JSONException -> 0x0269 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.ads.zzaej zza(android.content.Context r54, com.google.android.gms.internal.ads.zzaef r55, java.lang.String r56) {
        /*
            r0 = r55
            java.lang.String r1 = "interstitial_timeout"
            java.lang.String r10 = ""
            r15 = 0
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0269 }
            r2 = r56
            r11.<init>(r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r2 = "ad_base_url"
            r12 = 0
            java.lang.String r2 = r11.optString(r2, r12)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r3 = "ad_url"
            java.lang.String r4 = r11.optString(r3, r12)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r3 = "ad_size"
            java.lang.String r13 = r11.optString(r3, r12)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r3 = "ad_slot_size"
            java.lang.String r40 = r11.optString(r3, r13)     // Catch:{ JSONException -> 0x0269 }
            if (r0 == 0) goto L_0x0030
            int r3 = r0.zzcdb     // Catch:{ JSONException -> 0x0269 }
            if (r3 == 0) goto L_0x0030
            r24 = 1
            goto L_0x0032
        L_0x0030:
            r24 = 0
        L_0x0032:
            java.lang.String r3 = "ad_json"
            java.lang.String r3 = r11.optString(r3, r12)     // Catch:{ JSONException -> 0x0269 }
            if (r3 != 0) goto L_0x0040
            java.lang.String r3 = "ad_html"
            java.lang.String r3 = r11.optString(r3, r12)     // Catch:{ JSONException -> 0x0269 }
        L_0x0040:
            if (r3 != 0) goto L_0x0048
            java.lang.String r3 = "body"
            java.lang.String r3 = r11.optString(r3, r12)     // Catch:{ JSONException -> 0x0269 }
        L_0x0048:
            if (r3 != 0) goto L_0x0056
            java.lang.String r5 = "ads"
            boolean r5 = r11.has(r5)     // Catch:{ JSONException -> 0x0269 }
            if (r5 == 0) goto L_0x0056
            java.lang.String r3 = r11.toString()     // Catch:{ JSONException -> 0x0269 }
        L_0x0056:
            java.lang.String r5 = "debug_dialog"
            java.lang.String r19 = r11.optString(r5, r12)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r5 = "debug_signals"
            java.lang.String r42 = r11.optString(r5, r12)     // Catch:{ JSONException -> 0x0269 }
            boolean r5 = r11.has(r1)     // Catch:{ JSONException -> 0x0269 }
            r8 = -1
            if (r5 == 0) goto L_0x0079
            double r5 = r11.getDouble(r1)     // Catch:{ JSONException -> 0x0269 }
            r16 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r5 = r5 * r16
            long r5 = (long) r5     // Catch:{ JSONException -> 0x0269 }
            r16 = r5
            goto L_0x007b
        L_0x0079:
            r16 = r8
        L_0x007b:
            java.lang.String r1 = "orientation"
            java.lang.String r1 = r11.optString(r1, r12)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r5 = "portrait"
            boolean r5 = r5.equals(r1)     // Catch:{ JSONException -> 0x0269 }
            r7 = -1
            if (r5 == 0) goto L_0x0095
            com.google.android.gms.internal.ads.zzakq r1 = com.google.android.gms.ads.internal.zzbv.zzem()     // Catch:{ JSONException -> 0x0269 }
            int r1 = r1.zzrm()     // Catch:{ JSONException -> 0x0269 }
        L_0x0092:
            r18 = r1
            goto L_0x00a8
        L_0x0095:
            java.lang.String r5 = "landscape"
            boolean r1 = r5.equals(r1)     // Catch:{ JSONException -> 0x0269 }
            if (r1 == 0) goto L_0x00a6
            com.google.android.gms.internal.ads.zzakq r1 = com.google.android.gms.ads.internal.zzbv.zzem()     // Catch:{ JSONException -> 0x0269 }
            int r1 = r1.zzrl()     // Catch:{ JSONException -> 0x0269 }
            goto L_0x0092
        L_0x00a6:
            r18 = -1
        L_0x00a8:
            boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x0269 }
            if (r1 == 0) goto L_0x00d9
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch:{ JSONException -> 0x0269 }
            if (r1 != 0) goto L_0x00d9
            com.google.android.gms.internal.ads.zzang r1 = r0.zzacr     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r3 = r1.zzcw     // Catch:{ JSONException -> 0x0269 }
            r5 = 0
            r6 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r1 = r55
            r2 = r54
            r14 = -1
            r7 = r20
            r8 = r21
            r9 = r22
            com.google.android.gms.internal.ads.zzaej r1 = com.google.android.gms.internal.ads.zzafn.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r2 = r1.zzbyq     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r3 = r1.zzceo     // Catch:{ JSONException -> 0x0269 }
            long r4 = r1.zzceu     // Catch:{ JSONException -> 0x0269 }
            r20 = r4
            r4 = r3
            goto L_0x00de
        L_0x00d9:
            r14 = -1
            r4 = r3
            r1 = r12
            r20 = -1
        L_0x00de:
            r3 = r2
            if (r4 != 0) goto L_0x00e7
            com.google.android.gms.internal.ads.zzaej r0 = new com.google.android.gms.internal.ads.zzaej     // Catch:{ JSONException -> 0x0269 }
            r0.<init>(r15)     // Catch:{ JSONException -> 0x0269 }
            return r0
        L_0x00e7:
            java.lang.String r2 = "click_urls"
            org.json.JSONArray r2 = r11.optJSONArray(r2)     // Catch:{ JSONException -> 0x0269 }
            if (r1 != 0) goto L_0x00f1
            r5 = r12
            goto L_0x00f3
        L_0x00f1:
            java.util.List<java.lang.String> r5 = r1.zzbsn     // Catch:{ JSONException -> 0x0269 }
        L_0x00f3:
            if (r2 == 0) goto L_0x00fa
            java.util.List r2 = zza(r2, r5)     // Catch:{ JSONException -> 0x0269 }
            r5 = r2
        L_0x00fa:
            java.lang.String r2 = "impression_urls"
            org.json.JSONArray r2 = r11.optJSONArray(r2)     // Catch:{ JSONException -> 0x0269 }
            if (r1 != 0) goto L_0x0104
            r6 = r12
            goto L_0x0106
        L_0x0104:
            java.util.List<java.lang.String> r6 = r1.zzbso     // Catch:{ JSONException -> 0x0269 }
        L_0x0106:
            if (r2 == 0) goto L_0x010d
            java.util.List r2 = zza(r2, r6)     // Catch:{ JSONException -> 0x0269 }
            r6 = r2
        L_0x010d:
            java.lang.String r2 = "downloaded_impression_urls"
            org.json.JSONArray r2 = r11.optJSONArray(r2)     // Catch:{ JSONException -> 0x0269 }
            if (r1 != 0) goto L_0x0117
            r7 = r12
            goto L_0x0119
        L_0x0117:
            java.util.List<java.lang.String> r7 = r1.zzbsp     // Catch:{ JSONException -> 0x0269 }
        L_0x0119:
            if (r2 == 0) goto L_0x0122
            java.util.List r2 = zza(r2, r7)     // Catch:{ JSONException -> 0x0269 }
            r48 = r2
            goto L_0x0124
        L_0x0122:
            r48 = r7
        L_0x0124:
            java.lang.String r2 = "manual_impression_urls"
            org.json.JSONArray r2 = r11.optJSONArray(r2)     // Catch:{ JSONException -> 0x0269 }
            if (r1 != 0) goto L_0x012e
            r7 = r12
            goto L_0x0130
        L_0x012e:
            java.util.List<java.lang.String> r7 = r1.zzces     // Catch:{ JSONException -> 0x0269 }
        L_0x0130:
            if (r2 == 0) goto L_0x0139
            java.util.List r2 = zza(r2, r7)     // Catch:{ JSONException -> 0x0269 }
            r22 = r2
            goto L_0x013b
        L_0x0139:
            r22 = r7
        L_0x013b:
            if (r1 == 0) goto L_0x0151
            int r2 = r1.orientation     // Catch:{ JSONException -> 0x0269 }
            if (r2 == r14) goto L_0x0145
            int r2 = r1.orientation     // Catch:{ JSONException -> 0x0269 }
            r18 = r2
        L_0x0145:
            long r7 = r1.zzcep     // Catch:{ JSONException -> 0x0269 }
            r25 = 0
            int r2 = (r7 > r25 ? 1 : (r7 == r25 ? 0 : -1))
            if (r2 <= 0) goto L_0x0151
            long r1 = r1.zzcep     // Catch:{ JSONException -> 0x0269 }
            r7 = r1
            goto L_0x0153
        L_0x0151:
            r7 = r16
        L_0x0153:
            java.lang.String r1 = "active_view"
            java.lang.String r23 = r11.optString(r1)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "ad_is_javascript"
            boolean r25 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            if (r25 == 0) goto L_0x016a
            java.lang.String r1 = "ad_passback_url"
            java.lang.String r1 = r11.optString(r1, r12)     // Catch:{ JSONException -> 0x0269 }
            r26 = r1
            goto L_0x016c
        L_0x016a:
            r26 = r12
        L_0x016c:
            java.lang.String r1 = "mediation"
            boolean r9 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "custom_render_allowed"
            boolean r27 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "content_url_opted_out"
            r2 = 1
            boolean r28 = r11.optBoolean(r1, r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "content_vertical_opted_out"
            boolean r43 = r11.optBoolean(r1, r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "prefetch"
            boolean r29 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "refresh_interval_milliseconds"
            r14 = r13
            r12 = -1
            long r16 = r11.optLong(r1, r12)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "mediation_config_cache_time_milliseconds"
            long r12 = r11.optLong(r1, r12)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "gws_query_id"
            java.lang.String r30 = r11.optString(r1, r10)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "height"
            java.lang.String r2 = "fluid"
            java.lang.String r2 = r11.optString(r2, r10)     // Catch:{ JSONException -> 0x0269 }
            boolean r31 = r1.equals(r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "native_express"
            boolean r32 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "video_start_urls"
            org.json.JSONArray r1 = r11.optJSONArray(r1)     // Catch:{ JSONException -> 0x0269 }
            r2 = 0
            java.util.List r33 = zza(r1, r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "video_complete_urls"
            org.json.JSONArray r1 = r11.optJSONArray(r1)     // Catch:{ JSONException -> 0x0269 }
            java.util.List r34 = zza(r1, r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "rewards"
            org.json.JSONArray r1 = r11.optJSONArray(r1)     // Catch:{ JSONException -> 0x0269 }
            com.google.android.gms.internal.ads.zzaig r35 = com.google.android.gms.internal.ads.zzaig.zza(r1)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "use_displayed_impression"
            boolean r36 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "auto_protection_configuration"
            org.json.JSONObject r1 = r11.optJSONObject(r1)     // Catch:{ JSONException -> 0x0269 }
            com.google.android.gms.internal.ads.zzael r37 = com.google.android.gms.internal.ads.zzael.zzl(r1)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "set_cookie"
            java.lang.String r38 = r11.optString(r1, r10)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "remote_ping_urls"
            org.json.JSONArray r1 = r11.optJSONArray(r1)     // Catch:{ JSONException -> 0x0269 }
            r2 = 0
            java.util.List r39 = zza(r1, r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "safe_browsing"
            org.json.JSONObject r1 = r11.optJSONObject(r1)     // Catch:{ JSONException -> 0x0269 }
            com.google.android.gms.internal.ads.zzaiq r41 = com.google.android.gms.internal.ads.zzaiq.zzo(r1)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "render_in_browser"
            boolean r2 = r0.zzbss     // Catch:{ JSONException -> 0x0269 }
            boolean r44 = r11.optBoolean(r1, r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "custom_close_blocked"
            boolean r45 = r11.optBoolean(r1)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "enable_omid"
            boolean r47 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "omid_settings"
            r2 = 0
            java.lang.String r50 = r11.optString(r1, r2)     // Catch:{ JSONException -> 0x0269 }
            java.lang.String r1 = "disable_closable_area"
            boolean r49 = r11.optBoolean(r1, r15)     // Catch:{ JSONException -> 0x0269 }
            com.google.android.gms.internal.ads.zzaej r51 = new com.google.android.gms.internal.ads.zzaej     // Catch:{ JSONException -> 0x0269 }
            boolean r10 = r0.zzcdd     // Catch:{ JSONException -> 0x0269 }
            boolean r11 = r0.zzcdr     // Catch:{ JSONException -> 0x0269 }
            boolean r2 = r0.zzced     // Catch:{ JSONException -> 0x0269 }
            r46 = 0
            r1 = r51
            r52 = r2
            r2 = r55
            r0 = r10
            r53 = r11
            r10 = r12
            r12 = r22
            r22 = r14
            r13 = r16
            r15 = r18
            r16 = r22
            r17 = r20
            r20 = r25
            r21 = r26
            r22 = r23
            r23 = r27
            r25 = r0
            r26 = r28
            r27 = r29
            r28 = r30
            r29 = r31
            r30 = r32
            r31 = r35
            r32 = r33
            r33 = r34
            r34 = r36
            r35 = r37
            r36 = r53
            r37 = r38
            r38 = r39
            r39 = r44
            r44 = r52
            r1.<init>(r2, r3, r4, r5, r6, r7, r9, r10, r12, r13, r15, r16, r17, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50)     // Catch:{ JSONException -> 0x0269 }
            return r51
        L_0x0269:
            r0 = move-exception
            java.lang.String r1 = "Could not parse the inline ad response: "
            java.lang.String r0 = r0.getMessage()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r2 = r0.length()
            if (r2 == 0) goto L_0x027f
            java.lang.String r0 = r1.concat(r0)
            goto L_0x0284
        L_0x027f:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r1)
        L_0x0284:
            com.google.android.gms.internal.ads.zzakb.zzdk(r0)
            com.google.android.gms.internal.ads.zzaej r0 = new com.google.android.gms.internal.ads.zzaej
            r1 = 0
            r0.<init>(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzafs.zza(android.content.Context, com.google.android.gms.internal.ads.zzaef, java.lang.String):com.google.android.gms.internal.ads.zzaej");
    }

    private static List<String> zza(JSONArray jSONArray, List<String> list) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            list.add(jSONArray.getString(i));
        }
        return list;
    }

    /* JADX WARNING: Removed duplicated region for block: B:137:0x0254 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0261 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:227:0x0605 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x0622 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x062e A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x0650 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x0674 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:254:0x06a0 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:262:0x06c2 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x06d3 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:273:0x06f9 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:280:0x0711 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:283:0x0720 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:295:0x074b A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:298:0x07c3 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:306:0x07ed A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:315:0x0809 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x080c A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x0825 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x0830 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:330:0x084f A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:333:0x0858 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:336:0x0865 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:343:0x0898 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:349:0x08c7 A[Catch:{ JSONException -> 0x091e }] */
    /* JADX WARNING: Removed duplicated region for block: B:357:0x08f0 A[Catch:{ JSONException -> 0x091e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject zza(android.content.Context r22, com.google.android.gms.internal.ads.zzafl r23) {
        /*
            r1 = r23
            com.google.android.gms.internal.ads.zzaef r2 = r1.zzcgs
            android.location.Location r3 = r1.zzaqe
            com.google.android.gms.internal.ads.zzaga r4 = r1.zzcgt
            android.os.Bundle r5 = r1.zzcdc
            org.json.JSONObject r6 = r1.zzcgu
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ JSONException -> 0x091e }
            r8.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "extra_caps"
            com.google.android.gms.internal.ads.zzna<java.lang.String> r10 = com.google.android.gms.internal.ads.zznk.zzbbk     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r11 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r10 = r11.zzd(r10)     // Catch:{ JSONException -> 0x091e }
            r8.put(r9, r10)     // Catch:{ JSONException -> 0x091e }
            java.util.List<java.lang.String> r9 = r1.zzcdj     // Catch:{ JSONException -> 0x091e }
            int r9 = r9.size()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r10 = ","
            if (r9 <= 0) goto L_0x0035
            java.lang.String r9 = "eid"
            java.util.List<java.lang.String> r11 = r1.zzcdj     // Catch:{ JSONException -> 0x091e }
            java.lang.String r11 = android.text.TextUtils.join(r10, r11)     // Catch:{ JSONException -> 0x091e }
            r8.put(r9, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x0035:
            android.os.Bundle r9 = r2.zzccu     // Catch:{ JSONException -> 0x091e }
            if (r9 == 0) goto L_0x0040
            java.lang.String r9 = "ad_pos"
            android.os.Bundle r11 = r2.zzccu     // Catch:{ JSONException -> 0x091e }
            r8.put(r9, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x0040:
            com.google.android.gms.internal.ads.zzjj r9 = r2.zzccv     // Catch:{ JSONException -> 0x091e }
            java.lang.String r11 = com.google.android.gms.internal.ads.zzajw.zzqn()     // Catch:{ JSONException -> 0x091e }
            if (r11 == 0) goto L_0x004d
            java.lang.String r12 = "abf"
            r8.put(r12, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x004d:
            long r11 = r9.zzapw     // Catch:{ JSONException -> 0x091e }
            r13 = -1
            int r15 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r15 == 0) goto L_0x0067
            java.lang.String r11 = "cust_age"
            java.text.SimpleDateFormat r12 = zzcho     // Catch:{ JSONException -> 0x091e }
            java.util.Date r15 = new java.util.Date     // Catch:{ JSONException -> 0x091e }
            long r13 = r9.zzapw     // Catch:{ JSONException -> 0x091e }
            r15.<init>(r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = r12.format(r15)     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r12)     // Catch:{ JSONException -> 0x091e }
        L_0x0067:
            android.os.Bundle r11 = r9.extras     // Catch:{ JSONException -> 0x091e }
            if (r11 == 0) goto L_0x0072
            java.lang.String r11 = "extras"
            android.os.Bundle r12 = r9.extras     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r12)     // Catch:{ JSONException -> 0x091e }
        L_0x0072:
            int r11 = r9.zzapx     // Catch:{ JSONException -> 0x091e }
            r12 = -1
            if (r11 == r12) goto L_0x0082
            java.lang.String r11 = "cust_gender"
            int r13 = r9.zzapx     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x091e }
        L_0x0082:
            java.util.List<java.lang.String> r11 = r9.zzapy     // Catch:{ JSONException -> 0x091e }
            if (r11 == 0) goto L_0x008d
            java.lang.String r11 = "kw"
            java.util.List<java.lang.String> r13 = r9.zzapy     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x091e }
        L_0x008d:
            int r11 = r9.zzaqa     // Catch:{ JSONException -> 0x091e }
            if (r11 == r12) goto L_0x009c
            java.lang.String r11 = "tag_for_child_directed_treatment"
            int r13 = r9.zzaqa     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x091e }
        L_0x009c:
            boolean r11 = r9.zzapz     // Catch:{ JSONException -> 0x091e }
            r13 = 1
            if (r11 == 0) goto L_0x00c2
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r11 = com.google.android.gms.internal.ads.zznk.zzbfp     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r14 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r11 = r14.zzd(r11)     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r11 = (java.lang.Boolean) r11     // Catch:{ JSONException -> 0x091e }
            boolean r11 = r11.booleanValue()     // Catch:{ JSONException -> 0x091e }
            if (r11 == 0) goto L_0x00bd
            java.lang.String r11 = "test_request"
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r13)     // Catch:{ JSONException -> 0x091e }
        L_0x00b9:
            r8.put(r11, r14)     // Catch:{ JSONException -> 0x091e }
            goto L_0x00c2
        L_0x00bd:
            java.lang.String r11 = "adtest"
            java.lang.String r14 = "on"
            goto L_0x00b9
        L_0x00c2:
            int r11 = r9.versionCode     // Catch:{ JSONException -> 0x091e }
            r14 = 2
            if (r11 < r14) goto L_0x00e3
            boolean r11 = r9.zzaqb     // Catch:{ JSONException -> 0x091e }
            if (r11 == 0) goto L_0x00d4
            java.lang.String r11 = "d_imp_hdr"
            java.lang.Integer r15 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r15)     // Catch:{ JSONException -> 0x091e }
        L_0x00d4:
            java.lang.String r11 = r9.zzaqc     // Catch:{ JSONException -> 0x091e }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x091e }
            if (r11 != 0) goto L_0x00e3
            java.lang.String r11 = "ppid"
            java.lang.String r15 = r9.zzaqc     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r15)     // Catch:{ JSONException -> 0x091e }
        L_0x00e3:
            int r11 = r9.versionCode     // Catch:{ JSONException -> 0x091e }
            r15 = 3
            if (r11 < r15) goto L_0x00f3
            java.lang.String r11 = r9.zzaqf     // Catch:{ JSONException -> 0x091e }
            if (r11 == 0) goto L_0x00f3
            java.lang.String r11 = "url"
            java.lang.String r15 = r9.zzaqf     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r15)     // Catch:{ JSONException -> 0x091e }
        L_0x00f3:
            int r11 = r9.versionCode     // Catch:{ JSONException -> 0x091e }
            r15 = 5
            if (r11 < r15) goto L_0x0119
            android.os.Bundle r11 = r9.zzaqh     // Catch:{ JSONException -> 0x091e }
            if (r11 == 0) goto L_0x0103
            java.lang.String r11 = "custom_targeting"
            android.os.Bundle r7 = r9.zzaqh     // Catch:{ JSONException -> 0x091e }
            r8.put(r11, r7)     // Catch:{ JSONException -> 0x091e }
        L_0x0103:
            java.util.List<java.lang.String> r7 = r9.zzaqi     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x010e
            java.lang.String r7 = "category_exclusions"
            java.util.List<java.lang.String> r11 = r9.zzaqi     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x010e:
            java.lang.String r7 = r9.zzaqj     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x0119
            java.lang.String r7 = "request_agent"
            java.lang.String r11 = r9.zzaqj     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x0119:
            int r7 = r9.versionCode     // Catch:{ JSONException -> 0x091e }
            r11 = 6
            if (r7 < r11) goto L_0x0129
            java.lang.String r7 = r9.zzaqk     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x0129
            java.lang.String r7 = "request_pkg"
            java.lang.String r11 = r9.zzaqk     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x0129:
            int r7 = r9.versionCode     // Catch:{ JSONException -> 0x091e }
            r11 = 7
            if (r7 < r11) goto L_0x0139
            java.lang.String r7 = "is_designed_for_families"
            boolean r9 = r9.zzaql     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
        L_0x0139:
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzjn[] r7 = r7.zzard     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "height"
            java.lang.String r11 = "fluid"
            java.lang.String r15 = "format"
            if (r7 != 0) goto L_0x0156
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = r7.zzarb     // Catch:{ JSONException -> 0x091e }
            r8.put(r15, r7)     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            boolean r7 = r7.zzarf     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x0188
            r8.put(r11, r9)     // Catch:{ JSONException -> 0x091e }
            goto L_0x0188
        L_0x0156:
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzjn[] r7 = r7.zzard     // Catch:{ JSONException -> 0x091e }
            int r13 = r7.length     // Catch:{ JSONException -> 0x091e }
            r14 = 0
            r19 = 0
            r20 = 0
        L_0x0160:
            if (r14 >= r13) goto L_0x0188
            r12 = r7[r14]     // Catch:{ JSONException -> 0x091e }
            r21 = r7
            boolean r7 = r12.zzarf     // Catch:{ JSONException -> 0x091e }
            if (r7 != 0) goto L_0x0173
            if (r19 != 0) goto L_0x0173
            java.lang.String r7 = r12.zzarb     // Catch:{ JSONException -> 0x091e }
            r8.put(r15, r7)     // Catch:{ JSONException -> 0x091e }
            r19 = 1
        L_0x0173:
            boolean r7 = r12.zzarf     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x017e
            if (r20 != 0) goto L_0x017e
            r8.put(r11, r9)     // Catch:{ JSONException -> 0x091e }
            r20 = 1
        L_0x017e:
            if (r19 == 0) goto L_0x0182
            if (r20 != 0) goto L_0x0188
        L_0x0182:
            int r14 = r14 + 1
            r7 = r21
            r12 = -1
            goto L_0x0160
        L_0x0188:
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            int r7 = r7.width     // Catch:{ JSONException -> 0x091e }
            r9 = -1
            if (r7 != r9) goto L_0x0196
            java.lang.String r7 = "smart_w"
            java.lang.String r9 = "full"
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
        L_0x0196:
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            int r7 = r7.height     // Catch:{ JSONException -> 0x091e }
            r9 = -2
            if (r7 != r9) goto L_0x01a4
            java.lang.String r7 = "smart_h"
            java.lang.String r11 = "auto"
            r8.put(r7, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x01a4:
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzjn[] r7 = r7.zzard     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x0218
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x091e }
            r7.<init>()     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzjn r11 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzjn[] r11 = r11.zzard     // Catch:{ JSONException -> 0x091e }
            int r12 = r11.length     // Catch:{ JSONException -> 0x091e }
            r13 = 0
            r14 = 0
        L_0x01b6:
            if (r13 >= r12) goto L_0x01fe
            r15 = r11[r13]     // Catch:{ JSONException -> 0x091e }
            boolean r9 = r15.zzarf     // Catch:{ JSONException -> 0x091e }
            if (r9 == 0) goto L_0x01c2
            r20 = r11
            r14 = 1
            goto L_0x01f8
        L_0x01c2:
            int r9 = r7.length()     // Catch:{ JSONException -> 0x091e }
            if (r9 == 0) goto L_0x01cd
            java.lang.String r9 = "|"
            r7.append(r9)     // Catch:{ JSONException -> 0x091e }
        L_0x01cd:
            int r9 = r15.width     // Catch:{ JSONException -> 0x091e }
            r20 = r11
            r11 = -1
            if (r9 != r11) goto L_0x01dc
            int r9 = r15.widthPixels     // Catch:{ JSONException -> 0x091e }
            float r9 = (float) r9     // Catch:{ JSONException -> 0x091e }
            float r11 = r4.zzagu     // Catch:{ JSONException -> 0x091e }
            float r9 = r9 / r11
            int r9 = (int) r9     // Catch:{ JSONException -> 0x091e }
            goto L_0x01de
        L_0x01dc:
            int r9 = r15.width     // Catch:{ JSONException -> 0x091e }
        L_0x01de:
            r7.append(r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "x"
            r7.append(r9)     // Catch:{ JSONException -> 0x091e }
            int r9 = r15.height     // Catch:{ JSONException -> 0x091e }
            r11 = -2
            if (r9 != r11) goto L_0x01f3
            int r9 = r15.heightPixels     // Catch:{ JSONException -> 0x091e }
            float r9 = (float) r9     // Catch:{ JSONException -> 0x091e }
            float r11 = r4.zzagu     // Catch:{ JSONException -> 0x091e }
            float r9 = r9 / r11
            int r9 = (int) r9     // Catch:{ JSONException -> 0x091e }
            goto L_0x01f5
        L_0x01f3:
            int r9 = r15.height     // Catch:{ JSONException -> 0x091e }
        L_0x01f5:
            r7.append(r9)     // Catch:{ JSONException -> 0x091e }
        L_0x01f8:
            int r13 = r13 + 1
            r11 = r20
            r9 = -2
            goto L_0x01b6
        L_0x01fe:
            if (r14 == 0) goto L_0x0213
            int r9 = r7.length()     // Catch:{ JSONException -> 0x091e }
            if (r9 == 0) goto L_0x020d
            java.lang.String r9 = "|"
            r11 = 0
            r7.insert(r11, r9)     // Catch:{ JSONException -> 0x091e }
            goto L_0x020e
        L_0x020d:
            r11 = 0
        L_0x020e:
            java.lang.String r9 = "320x50"
            r7.insert(r11, r9)     // Catch:{ JSONException -> 0x091e }
        L_0x0213:
            java.lang.String r9 = "sz"
            r8.put(r9, r7)     // Catch:{ JSONException -> 0x091e }
        L_0x0218:
            int r7 = r2.zzcdb     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x0288
            java.lang.String r7 = "native_version"
            int r9 = r2.zzcdb     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "native_templates"
            java.util.List<java.lang.String> r9 = r2.zzads     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "native_image_orientation"
            com.google.android.gms.internal.ads.zzpl r9 = r2.zzadj     // Catch:{ JSONException -> 0x091e }
            if (r9 != 0) goto L_0x0237
        L_0x0234:
            java.lang.String r9 = "any"
            goto L_0x0249
        L_0x0237:
            int r9 = r9.zzbjo     // Catch:{ JSONException -> 0x091e }
            if (r9 == 0) goto L_0x0234
            r11 = 1
            if (r9 == r11) goto L_0x0247
            r11 = 2
            if (r9 == r11) goto L_0x0244
            java.lang.String r9 = "not_set"
            goto L_0x0249
        L_0x0244:
            java.lang.String r9 = "landscape"
            goto L_0x0249
        L_0x0247:
            java.lang.String r9 = "portrait"
        L_0x0249:
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.util.List<java.lang.String> r7 = r2.zzcdk     // Catch:{ JSONException -> 0x091e }
            boolean r7 = r7.isEmpty()     // Catch:{ JSONException -> 0x091e }
            if (r7 != 0) goto L_0x025b
            java.lang.String r7 = "native_custom_templates"
            java.util.List<java.lang.String> r9 = r2.zzcdk     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
        L_0x025b:
            int r7 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r9 = 24
            if (r7 < r9) goto L_0x026c
            java.lang.String r7 = "max_num_ads"
            int r9 = r2.zzceg     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
        L_0x026c:
            java.lang.String r7 = r2.zzcee     // Catch:{ JSONException -> 0x091e }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x091e }
            if (r7 != 0) goto L_0x0288
            java.lang.String r7 = "native_advanced_settings"
            org.json.JSONArray r9 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0281 }
            java.lang.String r11 = r2.zzcee     // Catch:{ JSONException -> 0x0281 }
            r9.<init>(r11)     // Catch:{ JSONException -> 0x0281 }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x0281 }
            goto L_0x0288
        L_0x0281:
            r0 = move-exception
            r7 = r0
            java.lang.String r9 = "Problem creating json from native advanced settings"
            com.google.android.gms.internal.ads.zzakb.zzc(r9, r7)     // Catch:{ JSONException -> 0x091e }
        L_0x0288:
            java.util.List<java.lang.Integer> r7 = r2.zzadn     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x02c6
            java.util.List<java.lang.Integer> r7 = r2.zzadn     // Catch:{ JSONException -> 0x091e }
            int r7 = r7.size()     // Catch:{ JSONException -> 0x091e }
            if (r7 <= 0) goto L_0x02c6
            java.util.List<java.lang.Integer> r7 = r2.zzadn     // Catch:{ JSONException -> 0x091e }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ JSONException -> 0x091e }
        L_0x029a:
            boolean r9 = r7.hasNext()     // Catch:{ JSONException -> 0x091e }
            if (r9 == 0) goto L_0x02c6
            java.lang.Object r9 = r7.next()     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ JSONException -> 0x091e }
            int r11 = r9.intValue()     // Catch:{ JSONException -> 0x091e }
            r12 = 2
            if (r11 != r12) goto L_0x02b8
            java.lang.String r9 = "iba"
            r11 = 1
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r11)     // Catch:{ JSONException -> 0x091e }
        L_0x02b4:
            r8.put(r9, r12)     // Catch:{ JSONException -> 0x091e }
            goto L_0x029a
        L_0x02b8:
            int r9 = r9.intValue()     // Catch:{ JSONException -> 0x091e }
            r11 = 1
            if (r9 != r11) goto L_0x029a
            java.lang.String r9 = "ina"
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r11)     // Catch:{ JSONException -> 0x091e }
            goto L_0x02b4
        L_0x02c6:
            com.google.android.gms.internal.ads.zzjn r7 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            boolean r7 = r7.zzarg     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x02d6
            java.lang.String r7 = "ene"
            r9 = 1
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x02d6:
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r7 = com.google.android.gms.internal.ads.zznk.zzaxv     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r9 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r7 = r9.zzd(r7)     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ JSONException -> 0x091e }
            boolean r7 = r7.booleanValue()     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x02f2
            java.lang.String r7 = "xsrve"
            r9 = 1
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r11)     // Catch:{ JSONException -> 0x091e }
        L_0x02f2:
            com.google.android.gms.internal.ads.zzlu r7 = r2.zzadl     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x030d
            java.lang.String r7 = "is_icon_ad"
            r9 = 1
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r11)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "icon_ad_expansion_behavior"
            com.google.android.gms.internal.ads.zzlu r9 = r2.zzadl     // Catch:{ JSONException -> 0x091e }
            int r9 = r9.zzasj     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
        L_0x030d:
            java.lang.String r7 = "slotname"
            java.lang.String r9 = r2.zzacp     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "pn"
            android.content.pm.ApplicationInfo r9 = r2.applicationInfo     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = r9.packageName     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            android.content.pm.PackageInfo r7 = r2.zzccw     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x032e
            java.lang.String r7 = "vc"
            android.content.pm.PackageInfo r9 = r2.zzccw     // Catch:{ JSONException -> 0x091e }
            int r9 = r9.versionCode     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
        L_0x032e:
            java.lang.String r7 = "ms"
            java.lang.String r9 = r1.zzccx     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "seq_num"
            java.lang.String r9 = r2.zzccy     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "session_id"
            java.lang.String r9 = r2.zzccz     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "js"
            com.google.android.gms.internal.ads.zzang r9 = r2.zzacr     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = r9.zzcw     // Catch:{ JSONException -> 0x091e }
            r8.put(r7, r9)     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzagk r7 = r1.zzcgo     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r9 = r2.zzcdw     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r11 = r1.zzcgn     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "am"
            int r13 = r4.zzcjk     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "cog"
            boolean r13 = r4.zzcjl     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r13 = zzv(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "coh"
            boolean r13 = r4.zzcjm     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r13 = zzv(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = r4.zzcjn     // Catch:{ JSONException -> 0x091e }
            boolean r12 = android.text.TextUtils.isEmpty(r12)     // Catch:{ JSONException -> 0x091e }
            if (r12 != 0) goto L_0x0382
            java.lang.String r12 = "carrier"
            java.lang.String r13 = r4.zzcjn     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r13)     // Catch:{ JSONException -> 0x091e }
        L_0x0382:
            java.lang.String r12 = "gl"
            java.lang.String r13 = r4.zzcjo     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r13)     // Catch:{ JSONException -> 0x091e }
            boolean r12 = r4.zzcjp     // Catch:{ JSONException -> 0x091e }
            if (r12 == 0) goto L_0x0397
            java.lang.String r12 = "simulator"
            r13 = 1
            java.lang.Integer r14 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
        L_0x0397:
            boolean r12 = r4.zzcjq     // Catch:{ JSONException -> 0x091e }
            if (r12 == 0) goto L_0x03a6
            java.lang.String r12 = "is_sidewinder"
            r13 = 1
            java.lang.Integer r14 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            goto L_0x03a7
        L_0x03a6:
            r13 = 1
        L_0x03a7:
            java.lang.String r12 = "ma"
            boolean r14 = r4.zzcjr     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = zzv(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "sp"
            boolean r14 = r4.zzcjs     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = zzv(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "hl"
            java.lang.String r14 = r4.zzcjt     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = r4.zzcju     // Catch:{ JSONException -> 0x091e }
            boolean r12 = android.text.TextUtils.isEmpty(r12)     // Catch:{ JSONException -> 0x091e }
            if (r12 != 0) goto L_0x03d3
            java.lang.String r12 = "mv"
            java.lang.String r14 = r4.zzcju     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
        L_0x03d3:
            java.lang.String r12 = "muv"
            int r14 = r4.zzcjw     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            int r12 = r4.zzcjx     // Catch:{ JSONException -> 0x091e }
            r14 = -2
            if (r12 == r14) goto L_0x03ee
            java.lang.String r12 = "cnt"
            int r14 = r4.zzcjx     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
        L_0x03ee:
            java.lang.String r12 = "gnt"
            int r14 = r4.zzcjy     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "pt"
            int r14 = r4.zzcjz     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "rm"
            int r14 = r4.zzcka     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r12 = "riv"
            int r14 = r4.zzckb     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ JSONException -> 0x091e }
            r8.put(r12, r14)     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r12 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r12.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r14 = "build_build"
            java.lang.String r15 = r4.zzckg     // Catch:{ JSONException -> 0x091e }
            r12.putString(r14, r15)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r14 = "build_device"
            java.lang.String r15 = r4.zzckh     // Catch:{ JSONException -> 0x091e }
            r12.putString(r14, r15)     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r14 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r14.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r15 = "is_charging"
            boolean r13 = r4.zzckd     // Catch:{ JSONException -> 0x091e }
            r14.putBoolean(r15, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = "battery_level"
            r15 = r5
            r19 = r6
            double r5 = r4.zzckc     // Catch:{ JSONException -> 0x091e }
            r14.putDouble(r13, r5)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r5 = "battery"
            r12.putBundle(r5, r14)     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r5 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r5.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "active_network_state"
            int r13 = r4.zzckf     // Catch:{ JSONException -> 0x091e }
            r5.putInt(r6, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "active_network_metered"
            boolean r13 = r4.zzcke     // Catch:{ JSONException -> 0x091e }
            r5.putBoolean(r6, r13)     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x047f
            android.os.Bundle r6 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r6.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = "predicted_latency_micros"
            int r14 = r7.zzckq     // Catch:{ JSONException -> 0x091e }
            r6.putInt(r13, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = "predicted_down_throughput_bps"
            r20 = r15
            long r14 = r7.zzckr     // Catch:{ JSONException -> 0x091e }
            r6.putLong(r13, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = "predicted_up_throughput_bps"
            long r14 = r7.zzcks     // Catch:{ JSONException -> 0x091e }
            r6.putLong(r13, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "predictions"
            r5.putBundle(r7, r6)     // Catch:{ JSONException -> 0x091e }
            goto L_0x0481
        L_0x047f:
            r20 = r15
        L_0x0481:
            java.lang.String r6 = "network"
            r12.putBundle(r6, r5)     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r5 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r5.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "is_browser_custom_tabs_capable"
            boolean r7 = r4.zzcki     // Catch:{ JSONException -> 0x091e }
            r5.putBoolean(r6, r7)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "browser"
            r12.putBundle(r6, r5)     // Catch:{ JSONException -> 0x091e }
            if (r9 == 0) goto L_0x0550
            java.lang.String r5 = "android_mem_info"
            android.os.Bundle r6 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r6.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "runtime_free"
            java.lang.String r13 = "runtime_free_memory"
            r14 = -1
            long r16 = r9.getLong(r13, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Long.toString(r16)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r7, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "runtime_max"
            java.lang.String r13 = "runtime_max_memory"
            long r16 = r9.getLong(r13, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Long.toString(r16)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r7, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "runtime_total"
            java.lang.String r13 = "runtime_total_memory"
            long r13 = r9.getLong(r13, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Long.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r7, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "web_view_count"
            java.lang.String r13 = "web_view_count"
            r14 = 0
            int r13 = r9.getInt(r13, r14)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r7, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "debug_memory_info"
            android.os.Parcelable r7 = r9.getParcelable(r7)     // Catch:{ JSONException -> 0x091e }
            android.os.Debug$MemoryInfo r7 = (android.os.Debug.MemoryInfo) r7     // Catch:{ JSONException -> 0x091e }
            if (r7 == 0) goto L_0x054c
            java.lang.String r9 = "debug_info_dalvik_private_dirty"
            int r13 = r7.dalvikPrivateDirty     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_dalvik_pss"
            int r13 = r7.dalvikPss     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_dalvik_shared_dirty"
            int r13 = r7.dalvikSharedDirty     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_native_private_dirty"
            int r13 = r7.nativePrivateDirty     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_native_pss"
            int r13 = r7.nativePss     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_native_shared_dirty"
            int r13 = r7.nativeSharedDirty     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_other_private_dirty"
            int r13 = r7.otherPrivateDirty     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_other_pss"
            int r13 = r7.otherPss     // Catch:{ JSONException -> 0x091e }
            java.lang.String r13 = java.lang.Integer.toString(r13)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r13)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "debug_info_other_shared_dirty"
            int r7 = r7.otherSharedDirty     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = java.lang.Integer.toString(r7)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r9, r7)     // Catch:{ JSONException -> 0x091e }
        L_0x054c:
            r12.putBundle(r5, r6)     // Catch:{ JSONException -> 0x091e }
            goto L_0x0551
        L_0x0550:
            r14 = 0
        L_0x0551:
            android.os.Bundle r5 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r5.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "parental_controls"
            r5.putBundle(r6, r11)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = r4.zzcjv     // Catch:{ JSONException -> 0x091e }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ JSONException -> 0x091e }
            if (r6 != 0) goto L_0x056a
            java.lang.String r6 = "package_version"
            java.lang.String r7 = r4.zzcjv     // Catch:{ JSONException -> 0x091e }
            r5.putString(r6, r7)     // Catch:{ JSONException -> 0x091e }
        L_0x056a:
            java.lang.String r6 = "play_store"
            r12.putBundle(r6, r5)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r5 = "device"
            r8.put(r5, r12)     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r5 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r5.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "doritos"
            java.lang.String r7 = r1.zzcgp     // Catch:{ JSONException -> 0x091e }
            r5.putString(r6, r7)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "doritos_v2"
            java.lang.String r7 = r1.zzcgq     // Catch:{ JSONException -> 0x091e }
            r5.putString(r6, r7)     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r6 = com.google.android.gms.internal.ads.zznk.zzayj     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r7 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r6 = r7.zzd(r6)     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ JSONException -> 0x091e }
            boolean r6 = r6.booleanValue()     // Catch:{ JSONException -> 0x091e }
            if (r6 == 0) goto L_0x05d5
            com.google.android.gms.ads.identifier.AdvertisingIdClient$Info r6 = r1.zzcgr     // Catch:{ JSONException -> 0x091e }
            if (r6 == 0) goto L_0x05aa
            com.google.android.gms.ads.identifier.AdvertisingIdClient$Info r6 = r1.zzcgr     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = r6.getId()     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.ads.identifier.AdvertisingIdClient$Info r6 = r1.zzcgr     // Catch:{ JSONException -> 0x091e }
            boolean r6 = r6.isLimitAdTrackingEnabled()     // Catch:{ JSONException -> 0x091e }
            goto L_0x05ac
        L_0x05aa:
            r6 = 0
            r7 = 0
        L_0x05ac:
            boolean r9 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x091e }
            if (r9 != 0) goto L_0x05c4
            java.lang.String r9 = "rdid"
            r5.putString(r9, r7)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "is_lat"
            r5.putBoolean(r7, r6)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "idtype"
            java.lang.String r7 = "adid"
        L_0x05c0:
            r5.putString(r6, r7)     // Catch:{ JSONException -> 0x091e }
            goto L_0x05d5
        L_0x05c4:
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = com.google.android.gms.internal.ads.zzamu.zzbd(r22)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "pdid"
            r5.putString(r7, r6)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r6 = "pdidtype"
            java.lang.String r7 = "ssaid"
            goto L_0x05c0
        L_0x05d5:
            java.lang.String r6 = "pii"
            r8.put(r6, r5)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r5 = "platform"
            java.lang.String r6 = android.os.Build.MANUFACTURER     // Catch:{ JSONException -> 0x091e }
            r8.put(r5, r6)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r5 = "submodel"
            java.lang.String r6 = android.os.Build.MODEL     // Catch:{ JSONException -> 0x091e }
            r8.put(r5, r6)     // Catch:{ JSONException -> 0x091e }
            if (r3 == 0) goto L_0x05ee
        L_0x05ea:
            zza(r8, r3)     // Catch:{ JSONException -> 0x091e }
            goto L_0x0600
        L_0x05ee:
            com.google.android.gms.internal.ads.zzjj r3 = r2.zzccv     // Catch:{ JSONException -> 0x091e }
            int r3 = r3.versionCode     // Catch:{ JSONException -> 0x091e }
            r5 = 2
            if (r3 < r5) goto L_0x0600
            com.google.android.gms.internal.ads.zzjj r3 = r2.zzccv     // Catch:{ JSONException -> 0x091e }
            android.location.Location r3 = r3.zzaqe     // Catch:{ JSONException -> 0x091e }
            if (r3 == 0) goto L_0x0600
            com.google.android.gms.internal.ads.zzjj r3 = r2.zzccv     // Catch:{ JSONException -> 0x091e }
            android.location.Location r3 = r3.zzaqe     // Catch:{ JSONException -> 0x091e }
            goto L_0x05ea
        L_0x0600:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r5 = 2
            if (r3 < r5) goto L_0x060c
            java.lang.String r3 = "quality_signals"
            android.os.Bundle r5 = r2.zzcda     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x060c:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r5 = 4
            if (r3 < r5) goto L_0x0620
            boolean r3 = r2.zzcdd     // Catch:{ JSONException -> 0x091e }
            if (r3 == 0) goto L_0x0620
            java.lang.String r3 = "forceHttps"
            boolean r5 = r2.zzcdd     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x0620:
            if (r20 == 0) goto L_0x0629
            java.lang.String r3 = "content_info"
            r5 = r20
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x0629:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r5 = 5
            if (r3 < r5) goto L_0x0650
            java.lang.String r3 = "u_sd"
            float r4 = r2.zzagu     // Catch:{ JSONException -> 0x091e }
            java.lang.Float r4 = java.lang.Float.valueOf(r4)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r3 = "sh"
            int r4 = r2.zzcdf     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r3 = "sw"
            int r4 = r2.zzcde     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ JSONException -> 0x091e }
        L_0x064c:
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
            goto L_0x066f
        L_0x0650:
            java.lang.String r3 = "u_sd"
            float r5 = r4.zzagu     // Catch:{ JSONException -> 0x091e }
            java.lang.Float r5 = java.lang.Float.valueOf(r5)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r3 = "sh"
            int r5 = r4.zzcdf     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r3 = "sw"
            int r4 = r4.zzcde     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ JSONException -> 0x091e }
            goto L_0x064c
        L_0x066f:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r4 = 6
            if (r3 < r4) goto L_0x069b
            java.lang.String r3 = r2.zzcdg     // Catch:{ JSONException -> 0x091e }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x091e }
            if (r3 != 0) goto L_0x0690
            java.lang.String r3 = "view_hierarchy"
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0689 }
            java.lang.String r5 = r2.zzcdg     // Catch:{ JSONException -> 0x0689 }
            r4.<init>(r5)     // Catch:{ JSONException -> 0x0689 }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x0689 }
            goto L_0x0690
        L_0x0689:
            r0 = move-exception
            r3 = r0
            java.lang.String r4 = "Problem serializing view hierarchy to JSON"
            com.google.android.gms.internal.ads.zzakb.zzc(r4, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x0690:
            java.lang.String r3 = "correlation_id"
            long r4 = r2.zzcdh     // Catch:{ JSONException -> 0x091e }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
        L_0x069b:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r4 = 7
            if (r3 < r4) goto L_0x06a7
            java.lang.String r3 = "request_id"
            java.lang.String r4 = r2.zzcdi     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
        L_0x06a7:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r4 = 12
            if (r3 < r4) goto L_0x06bc
            java.lang.String r3 = r2.zzcdm     // Catch:{ JSONException -> 0x091e }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x091e }
            if (r3 != 0) goto L_0x06bc
            java.lang.String r3 = "anchor"
            java.lang.String r4 = r2.zzcdm     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
        L_0x06bc:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r4 = 13
            if (r3 < r4) goto L_0x06cd
            java.lang.String r3 = "android_app_volume"
            float r4 = r2.zzcdn     // Catch:{ JSONException -> 0x091e }
            java.lang.Float r4 = java.lang.Float.valueOf(r4)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
        L_0x06cd:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r4 = 18
            if (r3 < r4) goto L_0x06de
            java.lang.String r3 = "android_app_muted"
            boolean r5 = r2.zzcdt     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x06de:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r5 = 14
            if (r3 < r5) goto L_0x06f3
            int r3 = r2.zzcdo     // Catch:{ JSONException -> 0x091e }
            if (r3 <= 0) goto L_0x06f3
            java.lang.String r3 = "target_api"
            int r5 = r2.zzcdo     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x06f3:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r5 = 15
            if (r3 < r5) goto L_0x070b
            java.lang.String r3 = "scroll_index"
            int r5 = r2.zzcdp     // Catch:{ JSONException -> 0x091e }
            r9 = -1
            if (r5 != r9) goto L_0x0701
            goto L_0x0704
        L_0x0701:
            int r12 = r2.zzcdp     // Catch:{ JSONException -> 0x091e }
            r9 = r12
        L_0x0704:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r9)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x070b:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r5 = 16
            if (r3 < r5) goto L_0x071c
            java.lang.String r3 = "_activity_context"
            boolean r5 = r2.zzcdq     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x071c:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            if (r3 < r4) goto L_0x0747
            java.lang.String r3 = r2.zzcdu     // Catch:{ JSONException -> 0x091e }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x091e }
            if (r3 != 0) goto L_0x073c
            java.lang.String r3 = "app_settings"
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0735 }
            java.lang.String r6 = r2.zzcdu     // Catch:{ JSONException -> 0x0735 }
            r5.<init>(r6)     // Catch:{ JSONException -> 0x0735 }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x0735 }
            goto L_0x073c
        L_0x0735:
            r0 = move-exception
            r3 = r0
            java.lang.String r5 = "Problem creating json from app settings"
            com.google.android.gms.internal.ads.zzakb.zzc(r5, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x073c:
            java.lang.String r3 = "render_in_browser"
            boolean r5 = r2.zzbss     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r5)     // Catch:{ JSONException -> 0x091e }
        L_0x0747:
            int r3 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            if (r3 < r4) goto L_0x0756
            java.lang.String r3 = "android_num_video_cache_tasks"
            int r4 = r2.zzcdv     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ JSONException -> 0x091e }
            r8.put(r3, r4)     // Catch:{ JSONException -> 0x091e }
        L_0x0756:
            com.google.android.gms.internal.ads.zzang r3 = r2.zzacr     // Catch:{ JSONException -> 0x091e }
            boolean r4 = r2.zzceh     // Catch:{ JSONException -> 0x091e }
            boolean r1 = r1.zzcgv     // Catch:{ JSONException -> 0x091e }
            boolean r5 = r2.zzcej     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r6 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r6.<init>()     // Catch:{ JSONException -> 0x091e }
            android.os.Bundle r7 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r7.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "cl"
            java.lang.String r11 = "193400285"
            r7.putString(r9, r11)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "rapid_rc"
            java.lang.String r11 = "dev"
            r7.putString(r9, r11)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "rapid_rollup"
            java.lang.String r11 = "HEAD"
            r7.putString(r9, r11)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = "build_meta"
            r6.putBundle(r9, r7)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "mf"
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r9 = com.google.android.gms.internal.ads.zznk.zzbbm     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r11 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r9 = r11.zzd(r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ JSONException -> 0x091e }
            boolean r9 = r9.booleanValue()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r9 = java.lang.Boolean.toString(r9)     // Catch:{ JSONException -> 0x091e }
            r6.putString(r7, r9)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r7 = "instant_app"
            r6.putBoolean(r7, r4)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r4 = "lite"
            boolean r3 = r3.zzcvh     // Catch:{ JSONException -> 0x091e }
            r6.putBoolean(r4, r3)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r3 = "local_service"
            r6.putBoolean(r3, r1)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r1 = "is_privileged_process"
            r6.putBoolean(r1, r5)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r1 = "sdk_env"
            r8.put(r1, r6)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r1 = "cache_state"
            r3 = r19
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
            int r1 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r3 = 19
            if (r1 < r3) goto L_0x07ca
            java.lang.String r1 = "gct"
            java.lang.String r3 = r2.zzcdx     // Catch:{ JSONException -> 0x091e }
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x07ca:
            int r1 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r3 = 21
            if (r1 < r3) goto L_0x07db
            boolean r1 = r2.zzcdy     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x07db
            java.lang.String r1 = "de"
            java.lang.String r3 = "1"
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x07db:
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r1 = com.google.android.gms.internal.ads.zznk.zzayy     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r3 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r1 = r3.zzd(r1)     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ JSONException -> 0x091e }
            boolean r1 = r1.booleanValue()     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x0821
            com.google.android.gms.internal.ads.zzjn r1 = r2.zzacv     // Catch:{ JSONException -> 0x091e }
            java.lang.String r1 = r1.zzarb     // Catch:{ JSONException -> 0x091e }
            java.lang.String r3 = "interstitial_mb"
            boolean r3 = r1.equals(r3)     // Catch:{ JSONException -> 0x091e }
            if (r3 != 0) goto L_0x0804
            java.lang.String r3 = "reward_mb"
            boolean r1 = r1.equals(r3)     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x0802
            goto L_0x0804
        L_0x0802:
            r1 = 0
            goto L_0x0805
        L_0x0804:
            r1 = 1
        L_0x0805:
            android.os.Bundle r3 = r2.zzcdz     // Catch:{ JSONException -> 0x091e }
            if (r3 == 0) goto L_0x080c
            r18 = 1
            goto L_0x080e
        L_0x080c:
            r18 = 0
        L_0x080e:
            if (r1 == 0) goto L_0x0821
            if (r18 == 0) goto L_0x0821
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ JSONException -> 0x091e }
            r1.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String r4 = "interstitial_pool"
            r1.putBundle(r4, r3)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r3 = "counters"
            r8.put(r3, r1)     // Catch:{ JSONException -> 0x091e }
        L_0x0821:
            java.lang.String r1 = r2.zzcea     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x082c
            java.lang.String r1 = "gmp_app_id"
            java.lang.String r3 = r2.zzcea     // Catch:{ JSONException -> 0x091e }
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x082c:
            java.lang.String r1 = r2.zzceb     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x084f
            java.lang.String r1 = "TIME_OUT"
            java.lang.String r3 = r2.zzceb     // Catch:{ JSONException -> 0x091e }
            boolean r1 = r1.equals(r3)     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x084a
            java.lang.String r1 = "sai_timeout"
            com.google.android.gms.internal.ads.zzna<java.lang.Long> r3 = com.google.android.gms.internal.ads.zznk.zzaxt     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r4 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r3 = r4.zzd(r3)     // Catch:{ JSONException -> 0x091e }
        L_0x0846:
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
            goto L_0x0854
        L_0x084a:
            java.lang.String r1 = "fbs_aiid"
            java.lang.String r3 = r2.zzceb     // Catch:{ JSONException -> 0x091e }
            goto L_0x0846
        L_0x084f:
            java.lang.String r1 = "fbs_aiid"
            java.lang.String r3 = ""
            goto L_0x0846
        L_0x0854:
            java.lang.String r1 = r2.zzcec     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x085f
            java.lang.String r1 = "fbs_aeid"
            java.lang.String r3 = r2.zzcec     // Catch:{ JSONException -> 0x091e }
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x085f:
            int r1 = r2.versionCode     // Catch:{ JSONException -> 0x091e }
            r3 = 24
            if (r1 < r3) goto L_0x0870
            java.lang.String r1 = "disable_ml"
            boolean r3 = r2.zzcei     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ JSONException -> 0x091e }
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x0870:
            com.google.android.gms.internal.ads.zzna<java.lang.String> r1 = com.google.android.gms.internal.ads.zznk.zzavo     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r3 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r1 = r3.zzd(r1)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x08b5
            boolean r3 = r1.isEmpty()     // Catch:{ JSONException -> 0x091e }
            if (r3 != 0) goto L_0x08b5
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzna<java.lang.Integer> r4 = com.google.android.gms.internal.ads.zznk.zzavp     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r5 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r4 = r5.zzd(r4)     // Catch:{ JSONException -> 0x091e }
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ JSONException -> 0x091e }
            int r4 = r4.intValue()     // Catch:{ JSONException -> 0x091e }
            if (r3 < r4) goto L_0x08b5
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ JSONException -> 0x091e }
            r3.<init>()     // Catch:{ JSONException -> 0x091e }
            java.lang.String[] r1 = r1.split(r10)     // Catch:{ JSONException -> 0x091e }
            int r4 = r1.length     // Catch:{ JSONException -> 0x091e }
        L_0x08a2:
            if (r14 >= r4) goto L_0x08b0
            r5 = r1[r14]     // Catch:{ JSONException -> 0x091e }
            java.util.List r6 = com.google.android.gms.internal.ads.zzams.zzdd(r5)     // Catch:{ JSONException -> 0x091e }
            r3.put(r5, r6)     // Catch:{ JSONException -> 0x091e }
            int r14 = r14 + 1
            goto L_0x08a2
        L_0x08b0:
            java.lang.String r1 = "video_decoders"
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x08b5:
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r1 = com.google.android.gms.internal.ads.zznk.zzbet     // Catch:{ JSONException -> 0x091e }
            com.google.android.gms.internal.ads.zzni r3 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ JSONException -> 0x091e }
            java.lang.Object r1 = r3.zzd(r1)     // Catch:{ JSONException -> 0x091e }
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ JSONException -> 0x091e }
            boolean r1 = r1.booleanValue()     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x08d6
            java.lang.String r1 = "omid_v"
            com.google.android.gms.internal.ads.zzaan r3 = com.google.android.gms.ads.internal.zzbv.zzfa()     // Catch:{ JSONException -> 0x091e }
            r4 = r22
            java.lang.String r3 = r3.getVersion(r4)     // Catch:{ JSONException -> 0x091e }
            r8.put(r1, r3)     // Catch:{ JSONException -> 0x091e }
        L_0x08d6:
            java.util.ArrayList<java.lang.String> r1 = r2.zzcek     // Catch:{ JSONException -> 0x091e }
            if (r1 == 0) goto L_0x08e9
            java.util.ArrayList<java.lang.String> r1 = r2.zzcek     // Catch:{ JSONException -> 0x091e }
            boolean r1 = r1.isEmpty()     // Catch:{ JSONException -> 0x091e }
            if (r1 != 0) goto L_0x08e9
            java.lang.String r1 = "android_permissions"
            java.util.ArrayList<java.lang.String> r2 = r2.zzcek     // Catch:{ JSONException -> 0x091e }
            r8.put(r1, r2)     // Catch:{ JSONException -> 0x091e }
        L_0x08e9:
            r1 = 2
            boolean r2 = com.google.android.gms.internal.ads.zzakb.isLoggable(r1)     // Catch:{ JSONException -> 0x091e }
            if (r2 == 0) goto L_0x0915
            com.google.android.gms.internal.ads.zzakk r2 = com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ JSONException -> 0x091e }
            org.json.JSONObject r2 = r2.zzn(r8)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r1 = r2.toString(r1)     // Catch:{ JSONException -> 0x091e }
            java.lang.String r2 = "Ad Request JSON: "
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ JSONException -> 0x091e }
            int r3 = r1.length()     // Catch:{ JSONException -> 0x091e }
            if (r3 == 0) goto L_0x090d
            java.lang.String r1 = r2.concat(r1)     // Catch:{ JSONException -> 0x091e }
            goto L_0x0912
        L_0x090d:
            java.lang.String r1 = new java.lang.String     // Catch:{ JSONException -> 0x091e }
            r1.<init>(r2)     // Catch:{ JSONException -> 0x091e }
        L_0x0912:
            com.google.android.gms.internal.ads.zzakb.m1419v(r1)     // Catch:{ JSONException -> 0x091e }
        L_0x0915:
            com.google.android.gms.internal.ads.zzakk r1 = com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ JSONException -> 0x091e }
            org.json.JSONObject r1 = r1.zzn(r8)     // Catch:{ JSONException -> 0x091e }
            return r1
        L_0x091e:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = "Problem serializing ad request to JSON: "
            java.lang.String r1 = r1.getMessage()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            int r3 = r1.length()
            if (r3 == 0) goto L_0x0935
            java.lang.String r1 = r2.concat(r1)
            goto L_0x093a
        L_0x0935:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r2)
        L_0x093a:
            com.google.android.gms.internal.ads.zzakb.zzdk(r1)
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzafs.zza(android.content.Context, com.google.android.gms.internal.ads.zzafl):org.json.JSONObject");
    }

    private static void zza(HashMap<String, Object> hashMap, Location location) {
        HashMap hashMap2 = new HashMap();
        Float valueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long valueOf2 = Long.valueOf(location.getTime() * 1000);
        Long valueOf3 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long valueOf4 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        hashMap2.put("radius", valueOf);
        hashMap2.put("lat", valueOf3);
        hashMap2.put("long", valueOf4);
        hashMap2.put("time", valueOf2);
        hashMap.put("uule", hashMap2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0142  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0160  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject zzb(com.google.android.gms.internal.ads.zzaej r7) throws org.json.JSONException {
        /*
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r1 = r7.zzbyq
            if (r1 == 0) goto L_0x0010
            java.lang.String r1 = r7.zzbyq
            java.lang.String r2 = "ad_base_url"
            r0.put(r2, r1)
        L_0x0010:
            java.lang.String r1 = r7.zzcet
            if (r1 == 0) goto L_0x001b
            java.lang.String r1 = r7.zzcet
            java.lang.String r2 = "ad_size"
            r0.put(r2, r1)
        L_0x001b:
            boolean r1 = r7.zzare
            java.lang.String r2 = "native"
            r0.put(r2, r1)
            boolean r1 = r7.zzare
            if (r1 == 0) goto L_0x002b
            java.lang.String r1 = r7.zzceo
            java.lang.String r2 = "ad_json"
            goto L_0x002f
        L_0x002b:
            java.lang.String r1 = r7.zzceo
            java.lang.String r2 = "ad_html"
        L_0x002f:
            r0.put(r2, r1)
            java.lang.String r1 = r7.zzcev
            if (r1 == 0) goto L_0x003d
            java.lang.String r1 = r7.zzcev
            java.lang.String r2 = "debug_dialog"
            r0.put(r2, r1)
        L_0x003d:
            java.lang.String r1 = r7.zzcfl
            if (r1 == 0) goto L_0x0048
            java.lang.String r1 = r7.zzcfl
            java.lang.String r2 = "debug_signals"
            r0.put(r2, r1)
        L_0x0048:
            long r1 = r7.zzcep
            r3 = -1
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x0061
            long r1 = r7.zzcep
            double r1 = (double) r1
            r5 = 4652007308841189376(0x408f400000000000, double:1000.0)
            java.lang.Double.isNaN(r1)
            double r1 = r1 / r5
            java.lang.String r5 = "interstitial_timeout"
            r0.put(r5, r1)
        L_0x0061:
            int r1 = r7.orientation
            com.google.android.gms.internal.ads.zzakq r2 = com.google.android.gms.ads.internal.zzbv.zzem()
            int r2 = r2.zzrm()
            java.lang.String r5 = "orientation"
            if (r1 != r2) goto L_0x0075
            java.lang.String r1 = "portrait"
        L_0x0071:
            r0.put(r5, r1)
            goto L_0x0084
        L_0x0075:
            int r1 = r7.orientation
            com.google.android.gms.internal.ads.zzakq r2 = com.google.android.gms.ads.internal.zzbv.zzem()
            int r2 = r2.zzrl()
            if (r1 != r2) goto L_0x0084
            java.lang.String r1 = "landscape"
            goto L_0x0071
        L_0x0084:
            java.util.List<java.lang.String> r1 = r7.zzbsn
            if (r1 == 0) goto L_0x0093
            java.util.List<java.lang.String> r1 = r7.zzbsn
            org.json.JSONArray r1 = zzm(r1)
            java.lang.String r2 = "click_urls"
            r0.put(r2, r1)
        L_0x0093:
            java.util.List<java.lang.String> r1 = r7.zzbso
            if (r1 == 0) goto L_0x00a2
            java.util.List<java.lang.String> r1 = r7.zzbso
            org.json.JSONArray r1 = zzm(r1)
            java.lang.String r2 = "impression_urls"
            r0.put(r2, r1)
        L_0x00a2:
            java.util.List<java.lang.String> r1 = r7.zzbsp
            if (r1 == 0) goto L_0x00b1
            java.util.List<java.lang.String> r1 = r7.zzbsp
            org.json.JSONArray r1 = zzm(r1)
            java.lang.String r2 = "downloaded_impression_urls"
            r0.put(r2, r1)
        L_0x00b1:
            java.util.List<java.lang.String> r1 = r7.zzces
            if (r1 == 0) goto L_0x00c0
            java.util.List<java.lang.String> r1 = r7.zzces
            org.json.JSONArray r1 = zzm(r1)
            java.lang.String r2 = "manual_impression_urls"
            r0.put(r2, r1)
        L_0x00c0:
            java.lang.String r1 = r7.zzcey
            if (r1 == 0) goto L_0x00cb
            java.lang.String r1 = r7.zzcey
            java.lang.String r2 = "active_view"
            r0.put(r2, r1)
        L_0x00cb:
            boolean r1 = r7.zzcew
            java.lang.String r2 = "ad_is_javascript"
            r0.put(r2, r1)
            java.lang.String r1 = r7.zzcex
            if (r1 == 0) goto L_0x00dd
            java.lang.String r1 = r7.zzcex
            java.lang.String r2 = "ad_passback_url"
            r0.put(r2, r1)
        L_0x00dd:
            boolean r1 = r7.zzceq
            java.lang.String r2 = "mediation"
            r0.put(r2, r1)
            boolean r1 = r7.zzcez
            java.lang.String r2 = "custom_render_allowed"
            r0.put(r2, r1)
            boolean r1 = r7.zzcfa
            java.lang.String r2 = "content_url_opted_out"
            r0.put(r2, r1)
            boolean r1 = r7.zzcfm
            java.lang.String r2 = "content_vertical_opted_out"
            r0.put(r2, r1)
            boolean r1 = r7.zzcfb
            java.lang.String r2 = "prefetch"
            r0.put(r2, r1)
            long r1 = r7.zzbsu
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x010d
            long r1 = r7.zzbsu
            java.lang.String r5 = "refresh_interval_milliseconds"
            r0.put(r5, r1)
        L_0x010d:
            long r1 = r7.zzcer
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x011a
            long r1 = r7.zzcer
            java.lang.String r3 = "mediation_config_cache_time_milliseconds"
            r0.put(r3, r1)
        L_0x011a:
            java.lang.String r1 = r7.zzamj
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0129
            java.lang.String r1 = r7.zzamj
            java.lang.String r2 = "gws_query_id"
            r0.put(r2, r1)
        L_0x0129:
            boolean r1 = r7.zzarf
            if (r1 == 0) goto L_0x0130
            java.lang.String r1 = "height"
            goto L_0x0132
        L_0x0130:
            java.lang.String r1 = ""
        L_0x0132:
            java.lang.String r2 = "fluid"
            r0.put(r2, r1)
            boolean r1 = r7.zzarg
            java.lang.String r2 = "native_express"
            r0.put(r2, r1)
            java.util.List<java.lang.String> r1 = r7.zzcff
            if (r1 == 0) goto L_0x014d
            java.util.List<java.lang.String> r1 = r7.zzcff
            org.json.JSONArray r1 = zzm(r1)
            java.lang.String r2 = "video_start_urls"
            r0.put(r2, r1)
        L_0x014d:
            java.util.List<java.lang.String> r1 = r7.zzcfg
            if (r1 == 0) goto L_0x015c
            java.util.List<java.lang.String> r1 = r7.zzcfg
            org.json.JSONArray r1 = zzm(r1)
            java.lang.String r2 = "video_complete_urls"
            r0.put(r2, r1)
        L_0x015c:
            com.google.android.gms.internal.ads.zzaig r1 = r7.zzcfe
            if (r1 == 0) goto L_0x0182
            com.google.android.gms.internal.ads.zzaig r1 = r7.zzcfe
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r3 = r1.type
            java.lang.String r4 = "rb_type"
            r2.put(r4, r3)
            int r1 = r1.zzcmk
            java.lang.String r3 = "rb_amount"
            r2.put(r3, r1)
            org.json.JSONArray r1 = new org.json.JSONArray
            r1.<init>()
            r1.put(r2)
            java.lang.String r2 = "rewards"
            r0.put(r2, r1)
        L_0x0182:
            boolean r1 = r7.zzcfh
            java.lang.String r2 = "use_displayed_impression"
            r0.put(r2, r1)
            com.google.android.gms.internal.ads.zzael r1 = r7.zzcfi
            java.lang.String r2 = "auto_protection_configuration"
            r0.put(r2, r1)
            boolean r1 = r7.zzbss
            java.lang.String r2 = "render_in_browser"
            r0.put(r2, r1)
            boolean r7 = r7.zzzm
            java.lang.String r1 = "disable_closable_area"
            r0.put(r1, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzafs.zzb(com.google.android.gms.internal.ads.zzaej):org.json.JSONObject");
    }

    private static JSONArray zzm(List<String> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (String put : list) {
            jSONArray.put(put);
        }
        return jSONArray;
    }

    private static Integer zzv(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }
}
