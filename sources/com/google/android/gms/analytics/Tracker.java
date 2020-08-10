package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzan;
import com.google.android.gms.internal.gtm.zzap;
import com.google.android.gms.internal.gtm.zzcg;
import com.google.android.gms.internal.gtm.zzcy;
import com.google.android.gms.internal.gtm.zzcz;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Tracker extends zzan {
    private boolean zztb;
    private final Map<String, String> zztc = new HashMap();
    private final Map<String, String> zztd = new HashMap();
    /* access modifiers changed from: private */
    public final zzcg zzte;
    /* access modifiers changed from: private */
    public final zza zztf;
    private ExceptionReporter zztg;
    /* access modifiers changed from: private */
    public zzcy zzth;

    class zza extends zzan implements zza {
        private boolean zztq;
        private int zztr;
        private long zzts = -1;
        private boolean zztt;
        private long zztu;

        protected zza(zzap zzap) {
            super(zzap);
        }

        /* access modifiers changed from: protected */
        public final void zzaw() {
        }

        public final void setSessionTimeout(long j) {
            this.zzts = j;
            zzay();
        }

        public final void enableAutoActivityTracking(boolean z) {
            this.zztq = z;
            zzay();
        }

        public final synchronized boolean zzax() {
            boolean z;
            z = this.zztt;
            this.zztt = false;
            return z;
        }

        private final void zzay() {
            if (this.zzts >= 0 || this.zztq) {
                zzcr().zza((zza) Tracker.this.zztf);
            } else {
                zzcr().zzb((zza) Tracker.this.zztf);
            }
        }

        public final void zzc(Activity activity) {
            String str;
            if (this.zztr == 0) {
                if (zzcn().elapsedRealtime() >= this.zztu + Math.max(1000, this.zzts)) {
                    this.zztt = true;
                }
            }
            this.zztr++;
            if (this.zztq) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    Tracker.this.setCampaignParamsOnNextHit(intent.getData());
                }
                HashMap hashMap = new HashMap();
                hashMap.put("&t", "screenview");
                Tracker tracker = Tracker.this;
                if (tracker.zzth != null) {
                    zzcy zzk = Tracker.this.zzth;
                    str = activity.getClass().getCanonicalName();
                    String str2 = (String) zzk.zzacs.get(str);
                    if (str2 != null) {
                        str = str2;
                    }
                } else {
                    str = activity.getClass().getCanonicalName();
                }
                tracker.set("&cd", str);
                String str3 = "&dr";
                if (TextUtils.isEmpty((CharSequence) hashMap.get(str3))) {
                    Preconditions.checkNotNull(activity);
                    Intent intent2 = activity.getIntent();
                    CharSequence charSequence = null;
                    if (intent2 != null) {
                        String stringExtra = intent2.getStringExtra("android.intent.extra.REFERRER_NAME");
                        if (!TextUtils.isEmpty(stringExtra)) {
                            charSequence = stringExtra;
                        }
                    }
                    if (!TextUtils.isEmpty(charSequence)) {
                        hashMap.put(str3, charSequence);
                    }
                }
                Tracker.this.send(hashMap);
            }
        }

        public final void zzd(Activity activity) {
            this.zztr--;
            this.zztr = Math.max(0, this.zztr);
            if (this.zztr == 0) {
                this.zztu = zzcn().elapsedRealtime();
            }
        }
    }

    Tracker(zzap zzap, String str, zzcg zzcg) {
        super(zzap);
        if (str != null) {
            this.zztc.put("&tid", str);
        }
        this.zztc.put("useSecure", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        this.zztc.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.zzte = new zzcg("tracking", zzcn());
        this.zztf = new zza(zzap);
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
        this.zztf.zzag();
        String zzaz = zzcu().zzaz();
        if (zzaz != null) {
            set("&an", zzaz);
        }
        String zzba = zzcu().zzba();
        if (zzba != null) {
            set("&av", zzba);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzcy zzcy) {
        zzq("Loading Tracker config values");
        this.zzth = zzcy;
        boolean z = false;
        if (this.zzth.zzacm != null) {
            String str = this.zzth.zzacm;
            set("&tid", str);
            zza("trackingId loaded", str);
        }
        if (this.zzth.zzacn >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            String d = Double.toString(this.zzth.zzacn);
            set("&sf", d);
            zza("Sample frequency loaded", d);
        }
        if (this.zzth.zzaco >= 0) {
            int i = this.zzth.zzaco;
            setSessionTimeout((long) i);
            zza("Session timeout loaded", Integer.valueOf(i));
        }
        if (this.zzth.zzacp != -1) {
            boolean z2 = this.zzth.zzacp == 1;
            enableAutoActivityTracking(z2);
            zza("Auto activity tracking loaded", Boolean.valueOf(z2));
        }
        if (this.zzth.zzacq != -1) {
            boolean z3 = this.zzth.zzacq == 1;
            if (z3) {
                set("&aip", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
            }
            zza("Anonymize ip loaded", Boolean.valueOf(z3));
        }
        if (this.zzth.zzacr == 1) {
            z = true;
        }
        enableExceptionReporting(z);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0037, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void enableExceptionReporting(boolean r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            com.google.android.gms.analytics.ExceptionReporter r0 = r2.zztg     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0008
        L_0x0007:
            r0 = 0
        L_0x0008:
            if (r0 != r3) goto L_0x000c
            monitor-exit(r2)     // Catch:{ all -> 0x0038 }
            return
        L_0x000c:
            if (r3 == 0) goto L_0x0028
            android.content.Context r3 = r2.getContext()     // Catch:{ all -> 0x0038 }
            java.lang.Thread$UncaughtExceptionHandler r0 = java.lang.Thread.getDefaultUncaughtExceptionHandler()     // Catch:{ all -> 0x0038 }
            com.google.android.gms.analytics.ExceptionReporter r1 = new com.google.android.gms.analytics.ExceptionReporter     // Catch:{ all -> 0x0038 }
            r1.<init>(r2, r0, r3)     // Catch:{ all -> 0x0038 }
            r2.zztg = r1     // Catch:{ all -> 0x0038 }
            com.google.android.gms.analytics.ExceptionReporter r3 = r2.zztg     // Catch:{ all -> 0x0038 }
            java.lang.Thread.setDefaultUncaughtExceptionHandler(r3)     // Catch:{ all -> 0x0038 }
            java.lang.String r3 = "Uncaught exceptions will be reported to Google Analytics"
            r2.zzq(r3)     // Catch:{ all -> 0x0038 }
            goto L_0x0036
        L_0x0028:
            com.google.android.gms.analytics.ExceptionReporter r3 = r2.zztg     // Catch:{ all -> 0x0038 }
            java.lang.Thread$UncaughtExceptionHandler r3 = r3.zzaf()     // Catch:{ all -> 0x0038 }
            java.lang.Thread.setDefaultUncaughtExceptionHandler(r3)     // Catch:{ all -> 0x0038 }
            java.lang.String r3 = "Uncaught exceptions will not be reported to Google Analytics"
            r2.zzq(r3)     // Catch:{ all -> 0x0038 }
        L_0x0036:
            monitor-exit(r2)     // Catch:{ all -> 0x0038 }
            return
        L_0x0038:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0038 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.Tracker.enableExceptionReporting(boolean):void");
    }

    public void setSessionTimeout(long j) {
        this.zztf.setSessionTimeout(j * 1000);
    }

    public void enableAutoActivityTracking(boolean z) {
        this.zztf.enableAutoActivityTracking(z);
    }

    private static String zza(Entry<String, String> entry) {
        String str = (String) entry.getKey();
        if (!(str.startsWith("&") && str.length() >= 2)) {
            return null;
        }
        return ((String) entry.getKey()).substring(1);
    }

    private static void zza(Map<String, String> map, Map<String, String> map2) {
        Preconditions.checkNotNull(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zza2 = zza(entry);
                if (zza2 != null) {
                    map2.put(zza2, (String) entry.getValue());
                }
            }
        }
    }

    public void send(Map<String, String> map) {
        long currentTimeMillis = zzcn().currentTimeMillis();
        if (zzcr().getAppOptOut()) {
            zzr("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean isDryRunEnabled = zzcr().isDryRunEnabled();
        HashMap hashMap = new HashMap();
        zza(this.zztc, hashMap);
        zza(map, hashMap);
        boolean zzb = zzcz.zzb((String) this.zztc.get("useSecure"), true);
        Map<String, String> map2 = this.zztd;
        Preconditions.checkNotNull(hashMap);
        if (map2 != null) {
            for (Entry entry : map2.entrySet()) {
                String zza2 = zza(entry);
                if (zza2 != null && !hashMap.containsKey(zza2)) {
                    hashMap.put(zza2, (String) entry.getValue());
                }
            }
        }
        this.zztd.clear();
        String str = (String) hashMap.get("t");
        if (TextUtils.isEmpty(str)) {
            zzco().zza((Map<String, String>) hashMap, "Missing hit type parameter");
            return;
        }
        String str2 = (String) hashMap.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzco().zza((Map<String, String>) hashMap, "Missing tracking id parameter");
            return;
        }
        boolean z = this.zztb;
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt((String) this.zztc.get("&a")) + 1;
                if (parseInt >= Integer.MAX_VALUE) {
                    parseInt = 1;
                }
                this.zztc.put("&a", Integer.toString(parseInt));
            }
        }
        zzk zzcq = zzcq();
        zzp zzp = new zzp(this, hashMap, z, str, currentTimeMillis, isDryRunEnabled, zzb, str2);
        zzcq.zza((Runnable) zzp);
    }

    public String get(String str) {
        zzdb();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (this.zztc.containsKey(str)) {
            return (String) this.zztc.get(str);
        }
        if (str.equals("&ul")) {
            return zzcz.zza(Locale.getDefault());
        }
        if (str.equals("&cid")) {
            return zzcw().zzeh();
        }
        if (str.equals("&sr")) {
            return zzcz().zzfb();
        }
        if (str.equals("&aid")) {
            return zzcy().zzdv().zzbb();
        }
        if (str.equals("&an")) {
            return zzcy().zzdv().zzaz();
        }
        if (str.equals("&av")) {
            return zzcy().zzdv().zzba();
        }
        if (str.equals("&aiid")) {
            return zzcy().zzdv().zzbc();
        }
        return null;
    }

    public void set(String str, String str2) {
        Preconditions.checkNotNull(str, "Key should be non-null");
        if (!TextUtils.isEmpty(str)) {
            this.zztc.put(str, str2);
        }
    }

    public void setSampleRate(double d) {
        set("&sf", Double.toString(d));
    }

    public void setUseSecure(boolean z) {
        set("useSecure", zzcz.zzc(z));
    }

    public void setScreenName(String str) {
        set("&cd", str);
    }

    public void setLocation(String str) {
        set("&dl", str);
    }

    public void setReferrer(String str) {
        set("&dr", str);
    }

    public void setPage(String str) {
        set("&dp", str);
    }

    public void setHostname(String str) {
        set("&dh", str);
    }

    public void setTitle(String str) {
        set("&dt", str);
    }

    public void setLanguage(String str) {
        set("&ul", str);
    }

    public void setEncoding(String str) {
        set("&de", str);
    }

    public void setScreenColors(String str) {
        set("&sd", str);
    }

    public void setScreenResolution(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            StringBuilder sb = new StringBuilder(23);
            sb.append(i);
            sb.append("x");
            sb.append(i2);
            set("&sr", sb.toString());
            return;
        }
        zzt("Invalid width or height. The values should be non-negative.");
    }

    public void setViewportSize(String str) {
        set("&vp", str);
    }

    public void setClientId(String str) {
        set("&cid", str);
    }

    public void setAppName(String str) {
        set("&an", str);
    }

    public void setAppId(String str) {
        set("&aid", str);
    }

    public void setAppInstallerId(String str) {
        set("&aiid", str);
    }

    public void setAppVersion(String str) {
        set("&av", str);
    }

    public void setAnonymizeIp(boolean z) {
        set("&aip", zzcz.zzc(z));
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null && !uri.isOpaque()) {
            String queryParameter = uri.getQueryParameter("referrer");
            if (!TextUtils.isEmpty(queryParameter)) {
                String str = "http://hostname/?";
                String valueOf = String.valueOf(queryParameter);
                Uri parse = Uri.parse(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                String queryParameter2 = parse.getQueryParameter("utm_id");
                if (queryParameter2 != null) {
                    this.zztd.put("&ci", queryParameter2);
                }
                String queryParameter3 = parse.getQueryParameter("anid");
                if (queryParameter3 != null) {
                    this.zztd.put("&anid", queryParameter3);
                }
                String queryParameter4 = parse.getQueryParameter("utm_campaign");
                if (queryParameter4 != null) {
                    this.zztd.put("&cn", queryParameter4);
                }
                String queryParameter5 = parse.getQueryParameter("utm_content");
                if (queryParameter5 != null) {
                    this.zztd.put("&cc", queryParameter5);
                }
                String queryParameter6 = parse.getQueryParameter("utm_medium");
                if (queryParameter6 != null) {
                    this.zztd.put("&cm", queryParameter6);
                }
                String queryParameter7 = parse.getQueryParameter("utm_source");
                if (queryParameter7 != null) {
                    this.zztd.put("&cs", queryParameter7);
                }
                String queryParameter8 = parse.getQueryParameter("utm_term");
                if (queryParameter8 != null) {
                    this.zztd.put("&ck", queryParameter8);
                }
                String queryParameter9 = parse.getQueryParameter("dclid");
                if (queryParameter9 != null) {
                    this.zztd.put("&dclid", queryParameter9);
                }
                String queryParameter10 = parse.getQueryParameter("gclid");
                if (queryParameter10 != null) {
                    this.zztd.put("&gclid", queryParameter10);
                }
                String queryParameter11 = parse.getQueryParameter(Param.ACLID);
                if (queryParameter11 != null) {
                    this.zztd.put("&aclid", queryParameter11);
                }
            }
        }
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.zztb = z;
    }
}
