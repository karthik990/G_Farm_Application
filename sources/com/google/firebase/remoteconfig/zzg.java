package com.google.firebase.remoteconfig;

import android.content.Context;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.firebase_remote_config.zzaa;
import com.google.android.gms.internal.firebase_remote_config.zzat;
import com.google.android.gms.internal.firebase_remote_config.zzbg;
import com.google.android.gms.internal.firebase_remote_config.zzcx;
import com.google.android.gms.internal.firebase_remote_config.zzcy;
import com.google.android.gms.internal.firebase_remote_config.zzdd;
import com.google.android.gms.internal.firebase_remote_config.zzeh;
import com.google.android.gms.internal.firebase_remote_config.zzer;
import com.google.android.gms.internal.firebase_remote_config.zzet;
import com.google.android.gms.internal.firebase_remote_config.zzeu;
import com.google.android.gms.internal.firebase_remote_config.zzew;
import com.google.android.gms.internal.firebase_remote_config.zzfc;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.abt.FirebaseABTesting;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class zzg {
    private static final ExecutorService zzjo = Executors.newCachedThreadPool();
    private static final Clock zzjp = DefaultClock.getInstance();
    private static final Random zzjq = new Random();
    private final String appId;
    private String zzh;
    private final Context zzja;
    private final FirebaseApp zzjb;
    private final FirebaseABTesting zzjc;
    private final Map<String, FirebaseRemoteConfig> zzjr;
    private final FirebaseInstanceId zzjs;
    private final AnalyticsConnector zzjt;
    private Map<String, String> zzju;

    zzg(Context context, FirebaseApp firebaseApp, FirebaseInstanceId firebaseInstanceId, FirebaseABTesting firebaseABTesting, AnalyticsConnector analyticsConnector) {
        this(context, zzjo, firebaseApp, firebaseInstanceId, firebaseABTesting, analyticsConnector, new zzfc(context, firebaseApp.getOptions().getApplicationId()));
    }

    private zzg(Context context, Executor executor, FirebaseApp firebaseApp, FirebaseInstanceId firebaseInstanceId, FirebaseABTesting firebaseABTesting, AnalyticsConnector analyticsConnector, zzfc zzfc) {
        this.zzjr = new HashMap();
        this.zzju = new HashMap();
        this.zzh = "https://firebaseremoteconfig.googleapis.com/";
        this.zzja = context;
        this.zzjb = firebaseApp;
        this.zzjs = firebaseInstanceId;
        this.zzjc = firebaseABTesting;
        this.zzjt = analyticsConnector;
        this.appId = firebaseApp.getOptions().getApplicationId();
        Tasks.call(executor, new zzh(this));
        zzfc.getClass();
        Tasks.call(executor, zzi.zza(zzfc));
    }

    public final synchronized FirebaseRemoteConfig zzbd(String str) {
        FirebaseRemoteConfig zza;
        String str2 = str;
        synchronized (this) {
            zzeh zzd = zzd(str2, "fetch");
            zzeh zzd2 = zzd(str2, "activate");
            zzeh zzd3 = zzd(str2, "defaults");
            zzeu zzeu = new zzeu(this.zzja.getSharedPreferences(String.format("%s_%s_%s_%s", new Object[]{"frc", this.appId, str2, "settings"}), 0));
            FirebaseApp firebaseApp = this.zzjb;
            FirebaseABTesting firebaseABTesting = this.zzjc;
            ExecutorService executorService = zzjo;
            Context context = this.zzja;
            String applicationId = this.zzjb.getOptions().getApplicationId();
            FirebaseInstanceId firebaseInstanceId = this.zzjs;
            zzer zzer = new zzer(context, applicationId, firebaseInstanceId, this.zzjt, str, zzjo, zzjp, zzjq, zzd, zzbe(this.zzjb.getOptions().getApiKey()), zzeu);
            zza = zza(firebaseApp, str, firebaseABTesting, executorService, zzd, zzd2, zzd3, zzer, new zzet(zzd2, zzd3), zzeu);
        }
        return zza;
    }

    private final synchronized FirebaseRemoteConfig zza(FirebaseApp firebaseApp, String str, FirebaseABTesting firebaseABTesting, Executor executor, zzeh zzeh, zzeh zzeh2, zzeh zzeh3, zzer zzer, zzet zzet, zzeu zzeu) {
        FirebaseRemoteConfig firebaseRemoteConfig;
        String str2 = str;
        synchronized (this) {
            if (!this.zzjr.containsKey(str2)) {
                FirebaseRemoteConfig firebaseRemoteConfig2 = new FirebaseRemoteConfig(this.zzja, firebaseApp, firebaseABTesting, executor, zzeh, zzeh2, zzeh3, zzer, zzet, zzeu);
                firebaseRemoteConfig2.zzcn();
                this.zzjr.put(str2, firebaseRemoteConfig2);
            }
            firebaseRemoteConfig = (FirebaseRemoteConfig) this.zzjr.get(str2);
        }
        return firebaseRemoteConfig;
    }

    private final zzeh zzd(String str, String str2) {
        return zza(this.zzja, this.appId, str, str2);
    }

    public static zzeh zza(Context context, String str, String str2, String str3) {
        return zzeh.zza(zzjo, zzew.zza(context, String.format("%s_%s_%s_%s.json", new Object[]{"frc", str, str2, str3})));
    }

    private final zzcx zzbe(String str) {
        zzcx zzcf;
        zzdd zzdd = new zzdd(str);
        synchronized (this) {
            zzcf = ((zzcy) new zzcy(new zzat(), zzbg.zzbr(), new zzj(this)).zzc(this.zzh)).zza(zzdd).zzcf();
        }
        return zzcf;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzc(zzaa zzaa) throws IOException {
        zzaa.zzb(10000);
        zzaa.zza(5000);
        synchronized (this) {
            for (Entry entry : this.zzju.entrySet()) {
                zzaa.zzy().zzb((String) entry.getKey(), entry.getValue());
            }
        }
    }
}
