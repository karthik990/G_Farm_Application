package com.google.android.gms.internal.firebase_remote_config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.text.format.DateUtils;
import android.util.Log;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;

public final class zzer {
    private static final long zzks = TimeUnit.HOURS.toSeconds(12);
    private static final int[] zzkt = {2, 4, 8, 16, 32, 64, 128, 256};
    private static final Pattern zzky = Pattern.compile("^[^:]+:([0-9]+):(android|ios|web):([0-9a-f]+)");
    private final String appId;
    private final String namespace;
    private final Context zzja;
    private final Executor zzjd;
    private final zzeh zzje;
    private final zzeu zzjj;
    private final FirebaseInstanceId zzjs;
    private final AnalyticsConnector zzjt;
    private final Clock zzku;
    private final Random zzkv;
    private final zzcx zzkw;
    private final String zzkx;

    public zzer(Context context, String str, FirebaseInstanceId firebaseInstanceId, AnalyticsConnector analyticsConnector, String str2, Executor executor, Clock clock, Random random, zzeh zzeh, zzcx zzcx, zzeu zzeu) {
        this.zzja = context;
        this.appId = str;
        this.zzjs = firebaseInstanceId;
        this.zzjt = analyticsConnector;
        this.namespace = str2;
        this.zzjd = executor;
        this.zzku = clock;
        this.zzkv = random;
        this.zzje = zzeh;
        this.zzkw = zzcx;
        this.zzjj = zzeu;
        Matcher matcher = zzky.matcher(str);
        this.zzkx = matcher.matches() ? matcher.group(1) : null;
    }

    public final Task<zzeo> zza(boolean z) {
        return zza(z, zzks);
    }

    public final Task<zzeo> zza(boolean z, long j) {
        return this.zzje.zzcp().continueWithTask(this.zzjd, new zzes(this, z, j));
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0022 A[Catch:{ FirebaseRemoteConfigException -> 0x002d }] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001c A[Catch:{ FirebaseRemoteConfigException -> 0x002d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.tasks.Task<com.google.android.gms.internal.firebase_remote_config.zzeo> zzb(java.util.Date r4) {
        /*
            r3 = this;
            com.google.android.gms.internal.firebase_remote_config.zzdg r0 = r3.zzc(r4)     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            java.lang.String r1 = r0.getState()     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            if (r1 == 0) goto L_0x0019
            java.lang.String r1 = r0.getState()     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            java.lang.String r2 = "NO_CHANGE"
            boolean r1 = r1.equals(r2)     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            if (r1 != 0) goto L_0x0017
            goto L_0x0019
        L_0x0017:
            r1 = 0
            goto L_0x001a
        L_0x0019:
            r1 = 1
        L_0x001a:
            if (r1 != 0) goto L_0x0022
            r4 = 0
            com.google.android.gms.tasks.Task r4 = com.google.android.gms.tasks.Tasks.forResult(r4)     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            return r4
        L_0x0022:
            com.google.android.gms.internal.firebase_remote_config.zzeo r4 = zza(r0, r4)     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            com.google.android.gms.internal.firebase_remote_config.zzeh r0 = r3.zzje     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            com.google.android.gms.tasks.Task r4 = r0.zzc(r4)     // Catch:{ FirebaseRemoteConfigException -> 0x002d }
            return r4
        L_0x002d:
            r4 = move-exception
            com.google.android.gms.tasks.Task r4 = com.google.android.gms.tasks.Tasks.forException(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzer.zzb(java.util.Date):com.google.android.gms.tasks.Task");
    }

    private final zzdg zzc(Date date) throws FirebaseRemoteConfigException {
        String str = "FirebaseRemoteConfig";
        try {
            zzdb zza = new zzda(new zzcz(this.zzkw)).zza(this.zzkx, this.namespace, zzcw());
            zza.zzg().zzr(this.zzjj.zzcx());
            zzdg zzdg = (zzdg) zza.zzi();
            this.zzjj.zzbf(zza.zzh().zzq());
            this.zzjj.zza(0, zzeu.zzlf);
            return zzdg;
        } catch (zzae e) {
            Log.e(str, "Fetch failed! Server responded with an error.", e);
            int statusCode = e.getStatusCode();
            if (statusCode == 429 || statusCode == 503 || statusCode == 504) {
                int zzcz = this.zzjj.zzcy().zzcz() + 1;
                TimeUnit timeUnit = TimeUnit.MINUTES;
                int[] iArr = zzkt;
                long millis = timeUnit.toMillis((long) iArr[Math.min(zzcz, iArr.length) - 1]);
                this.zzjj.zza(zzcz, new Date(date.getTime() + (millis / 2) + ((long) this.zzkv.nextInt((int) millis))));
            }
            int statusCode2 = e.getStatusCode();
            String str2 = statusCode2 != 401 ? statusCode2 != 403 ? statusCode2 != 429 ? statusCode2 != 500 ? (statusCode2 == 503 || statusCode2 == 504) ? "The server is unavailable. Please try again later." : "Server returned an unexpected error." : "There was an internal server error." : "You have reached the throttle limit for your project. Please wait before making more requests." : "The user is not authorized to access the project. Please make sure you are using the API key that corresponds to your Firebase project." : "The request did not have the required credentials. Please make sure your google-services.json is valid.";
            throw new FirebaseRemoteConfigFetchException(String.format("Fetch failed: %s\nCheck logs for details.", new Object[]{str2}));
        } catch (IOException e2) {
            Log.e(str, "Fetch failed due to an unexpected error.", e2);
            throw new FirebaseRemoteConfigFetchException("Fetch failed due to an unexpected error! Check logs for details.");
        }
    }

    private static zzeo zza(zzdg zzdg, Date date) throws FirebaseRemoteConfigFetchException {
        try {
            zzeq zza = zzeo.zzct().zzc(zzdg.getEntries()).zza(date);
            List zzcg = zzdg.zzcg();
            if (zzcg != null) {
                zza.zzb(zzcg);
            }
            return zza.zzcv();
        } catch (JSONException e) {
            throw new FirebaseRemoteConfigFetchException("Fetch failed: fetch response could not be parsed.", e);
        }
    }

    private final zzdf zzcw() throws FirebaseRemoteConfigFetchException {
        String id = this.zzjs.getId();
        if (id != null) {
            String token = this.zzjs.getToken();
            zzdf zzdf = new zzdf();
            zzdf.zzas(id);
            if (token != null) {
                zzdf.zzat(token);
            }
            zzdf.zzar(this.appId);
            Locale locale = this.zzja.getResources().getConfiguration().locale;
            zzdf.zzav(locale.getCountry());
            zzdf.zzaw(locale.toString());
            zzdf.zzay(Integer.toString(VERSION.SDK_INT));
            zzdf.zzba(TimeZone.getDefault().toString());
            try {
                PackageInfo packageInfo = this.zzja.getPackageManager().getPackageInfo(this.zzja.getPackageName(), 0);
                if (packageInfo != null) {
                    zzdf.zzau(packageInfo.versionName);
                }
            } catch (NameNotFoundException unused) {
            }
            zzdf.zzax(this.zzja.getPackageName());
            zzdf.zzaz(BuildConfig.VERSION_NAME);
            HashMap hashMap = new HashMap();
            AnalyticsConnector analyticsConnector = this.zzjt;
            if (analyticsConnector != null) {
                for (Entry entry : analyticsConnector.getUserProperties(false).entrySet()) {
                    hashMap.put((String) entry.getKey(), entry.getValue().toString());
                }
            }
            zzdf.zza(hashMap);
            return zzdf;
        }
        throw new FirebaseRemoteConfigFetchException("Fetch request could not be created: Firebase instance id is null.");
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Task zza(boolean z, long j, Task task) throws Exception {
        Date date = new Date(this.zzku.currentTimeMillis());
        if (task.isSuccessful()) {
            zzeo zzeo = (zzeo) task.getResult();
            if ((zzeo == null || z) ? false : date.before(new Date(zzeo.zzcr().getTime() + TimeUnit.SECONDS.toMillis(j)))) {
                return Tasks.forResult(null);
            }
        }
        Date zzda = this.zzjj.zzcy().zzda();
        if (!date.before(zzda)) {
            zzda = null;
        }
        if (zzda == null) {
            return zzb(date);
        }
        return Tasks.forException(new FirebaseRemoteConfigFetchThrottledException(String.format("Fetch is throttled. Please wait before calling fetch again: %s", new Object[]{DateUtils.formatElapsedTime(TimeUnit.MILLISECONDS.toSeconds(zzda.getTime() - date.getTime()))}), zzda.getTime()));
    }
}
