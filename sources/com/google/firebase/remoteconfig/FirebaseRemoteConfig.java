package com.google.firebase.remoteconfig;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.internal.firebase_remote_config.zzeh;
import com.google.android.gms.internal.firebase_remote_config.zzeo;
import com.google.android.gms.internal.firebase_remote_config.zzer;
import com.google.android.gms.internal.firebase_remote_config.zzet;
import com.google.android.gms.internal.firebase_remote_config.zzeu;
import com.google.android.gms.internal.firebase_remote_config.zzex;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.abt.AbtException;
import com.google.firebase.abt.FirebaseABTesting;
import com.google.firebase.auth.FirebaseAuthProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseRemoteConfig {
    public static final boolean DEFAULT_VALUE_FOR_BOOLEAN = false;
    public static final byte[] DEFAULT_VALUE_FOR_BYTE_ARRAY = new byte[0];
    public static final double DEFAULT_VALUE_FOR_DOUBLE = 0.0d;
    public static final long DEFAULT_VALUE_FOR_LONG = 0;
    public static final String DEFAULT_VALUE_FOR_STRING = "";
    public static final int LAST_FETCH_STATUS_FAILURE = 1;
    public static final int LAST_FETCH_STATUS_NO_FETCH_YET = 0;
    public static final int LAST_FETCH_STATUS_SUCCESS = -1;
    public static final int LAST_FETCH_STATUS_THROTTLED = 2;
    public static final int VALUE_SOURCE_DEFAULT = 1;
    public static final int VALUE_SOURCE_REMOTE = 2;
    public static final int VALUE_SOURCE_STATIC = 0;
    private final Context zzja;
    private final FirebaseApp zzjb;
    private final FirebaseABTesting zzjc;
    private final Executor zzjd;
    private final zzeh zzje;
    private final zzeh zzjf;
    private final zzeh zzjg;
    private final zzer zzjh;
    private final zzet zzji;
    private final zzeu zzjj;

    public static FirebaseRemoteConfig getInstance() {
        return ((zzg) FirebaseApp.getInstance().get(zzg.class)).zzbd(FirebaseAuthProvider.PROVIDER_ID);
    }

    FirebaseRemoteConfig(Context context, FirebaseApp firebaseApp, FirebaseABTesting firebaseABTesting, Executor executor, zzeh zzeh, zzeh zzeh2, zzeh zzeh3, zzer zzer, zzet zzet, zzeu zzeu) {
        this.zzja = context;
        this.zzjb = firebaseApp;
        this.zzjc = firebaseABTesting;
        this.zzjd = executor;
        this.zzje = zzeh;
        this.zzjf = zzeh2;
        this.zzjg = zzeh3;
        this.zzjh = zzer;
        this.zzji = zzet;
        this.zzjj = zzeu;
    }

    public boolean activateFetched() {
        zzeo zzco = this.zzje.zzco();
        if (zzco == null) {
            return false;
        }
        zzeo zzco2 = this.zzjf.zzco();
        if (!(zzco2 == null || !zzco.zzcr().equals(zzco2.zzcr()))) {
            return false;
        }
        this.zzjf.zzb(zzco).addOnSuccessListener(this.zzjd, (OnSuccessListener<? super TResult>) new zza<Object>(this));
        return true;
    }

    public boolean activateFetched(String str) {
        return ((zzg) this.zzjb.get(zzg.class)).zzbd(str).activateFetched();
    }

    public Task<Void> fetch() {
        Task zza = this.zzjh.zza(this.zzjj.isDeveloperModeEnabled());
        zza.addOnCompleteListener(this.zzjd, (OnCompleteListener<TResult>) new zzb<TResult>(this));
        return zza.onSuccessTask(zzc.zzjl);
    }

    public Task<Void> fetch(long j) {
        Task zza = this.zzjh.zza(this.zzjj.isDeveloperModeEnabled(), j);
        zza.addOnCompleteListener(this.zzjd, (OnCompleteListener<TResult>) new zzd<TResult>(this));
        return zza.onSuccessTask(zze.zzjl);
    }

    public String getString(String str) {
        return this.zzji.getString(str);
    }

    public String getString(String str, String str2) {
        return ((zzg) this.zzjb.get(zzg.class)).zzbd(str2).getString(str);
    }

    public boolean getBoolean(String str) {
        return this.zzji.getBoolean(str);
    }

    public boolean getBoolean(String str, String str2) {
        return ((zzg) this.zzjb.get(zzg.class)).zzbd(str2).getBoolean(str);
    }

    public byte[] getByteArray(String str) {
        return this.zzji.getByteArray(str);
    }

    public byte[] getByteArray(String str, String str2) {
        return ((zzg) this.zzjb.get(zzg.class)).zzbd(str2).getByteArray(str);
    }

    public double getDouble(String str) {
        return this.zzji.getDouble(str);
    }

    public double getDouble(String str, String str2) {
        return ((zzg) this.zzjb.get(zzg.class)).zzbd(str2).getDouble(str);
    }

    public long getLong(String str) {
        return this.zzji.getLong(str);
    }

    public long getLong(String str, String str2) {
        return ((zzg) this.zzjb.get(zzg.class)).zzbd(str2).getLong(str);
    }

    public FirebaseRemoteConfigValue getValue(String str) {
        return this.zzji.getValue(str);
    }

    public FirebaseRemoteConfigValue getValue(String str, String str2) {
        return getValue(str);
    }

    public Set<String> getKeysByPrefix(String str) {
        return this.zzji.getKeysByPrefix(str);
    }

    public Set<String> getKeysByPrefix(String str, String str2) {
        return getKeysByPrefix(str);
    }

    public FirebaseRemoteConfigInfo getInfo() {
        return this.zzjj.getInfo();
    }

    public void setConfigSettings(FirebaseRemoteConfigSettings firebaseRemoteConfigSettings) {
        this.zzjj.zzb(firebaseRemoteConfigSettings.isDeveloperModeEnabled());
    }

    public void setDefaults(Map<String, Object> map) {
        HashMap hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            hashMap.put((String) entry.getKey(), entry.getValue().toString());
        }
        zzb(hashMap);
    }

    public void setDefaults(Map<String, Object> map, String str) {
        setDefaults(map);
    }

    public void setDefaults(int i) {
        zzb(zzex.zza(this.zzja, i));
    }

    public void setDefaults(int i, String str) {
        setDefaults(i);
    }

    /* access modifiers changed from: 0000 */
    public final void zzcn() {
        this.zzje.zzcp();
        this.zzjf.zzcp();
    }

    private final void zzb(Map<String, String> map) {
        try {
            this.zzjg.zzb(zzeo.zzct().zzc(map).zzcv());
        } catch (JSONException e) {
            Log.e("FirebaseRemoteConfig", "The provided defaults map could not be processed.", e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(zzeo zzeo) {
        this.zzje.clear();
        JSONArray zzcs = zzeo.zzcs();
        String str = "FirebaseRemoteConfig";
        if (zzcs == null) {
            Log.e(str, "Stored ABT experiments are null.");
            return;
        }
        try {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < zzcs.length(); i++) {
                HashMap hashMap = new HashMap();
                JSONObject jSONObject = zzcs.getJSONObject(i);
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str2 = (String) keys.next();
                    hashMap.put(str2, jSONObject.getString(str2));
                }
                arrayList.add(hashMap);
            }
            this.zzjc.replaceAllExperiments(arrayList);
        } catch (JSONException e) {
            Log.e(str, "Could not parse ABT experiments from the JSON response.", e);
        } catch (AbtException e2) {
            Log.e(str, "Could not update ABT experiments.", e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zza(Task task) {
        String str = "FirebaseRemoteConfig";
        if (task.isSuccessful()) {
            this.zzjj.zzm(-1);
            zzeo zzeo = (zzeo) task.getResult();
            if (zzeo != null) {
                this.zzjj.zzd(zzeo.zzcr());
            }
            Log.i(str, "Fetch succeeded!");
            return;
        }
        Exception exception = task.getException();
        if (exception == null) {
            Log.e(str, "Fetch was cancelled... This should never covfefe");
        } else if (exception instanceof FirebaseRemoteConfigFetchThrottledException) {
            this.zzjj.zzm(2);
            Log.w(str, "Fetch was throttled!", exception);
        } else {
            this.zzjj.zzm(1);
            Log.e(str, "Fetch failed!", exception);
        }
    }
}
