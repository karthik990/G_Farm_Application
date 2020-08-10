package com.google.android.gms.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CollectionUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GcmNetworkManager {
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_RESCHEDULE = 1;
    public static final int RESULT_SUCCESS = 0;
    private static GcmNetworkManager zzg;
    private final Context zzh;
    private final Map<String, Map<String, Boolean>> zzi = new ArrayMap();

    private GcmNetworkManager(Context context) {
        this.zzh = context;
    }

    public static GcmNetworkManager getInstance(Context context) {
        GcmNetworkManager gcmNetworkManager;
        synchronized (GcmNetworkManager.class) {
            if (zzg == null) {
                zzg = new GcmNetworkManager(context.getApplicationContext());
            }
            gcmNetworkManager = zzg;
        }
        return gcmNetworkManager;
    }

    private final zzn zzd() {
        if (GoogleCloudMessaging.zzf(this.zzh) >= 5000000) {
            return new zzm(this.zzh);
        }
        Log.e("GcmNetworkManager", "Google Play services is not available, dropping all GcmNetworkManager requests");
        return new zzo();
    }

    static void zzd(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Must provide a valid tag.");
        } else if (100 < str.length()) {
            throw new IllegalArgumentException("Tag is larger than max permissible tag length (100)");
        }
    }

    private final boolean zze(String str) {
        List<ResolveInfo> list;
        Intent intent;
        Intent intent2;
        Preconditions.checkNotNull(str, "GcmTaskService must not be null.");
        PackageManager packageManager = this.zzh.getPackageManager();
        if (packageManager == null) {
            list = Collections.emptyList();
        } else {
            String str2 = GcmTaskService.SERVICE_ACTION_EXECUTE_TASK;
            if (str != null) {
                intent = new Intent(str2);
                intent2 = intent.setClassName(this.zzh, str);
            } else {
                intent = new Intent(str2);
                intent2 = intent.setPackage(this.zzh.getPackageName());
            }
            list = packageManager.queryIntentServices(intent2, 0);
        }
        if (CollectionUtils.isEmpty(list)) {
            Log.e("GcmNetworkManager", String.valueOf(str).concat(" is not available. This may cause the task to be lost."));
            return true;
        }
        for (ResolveInfo resolveInfo : list) {
            if (resolveInfo.serviceInfo != null && resolveInfo.serviceInfo.enabled) {
                return true;
            }
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 118);
        sb.append("The GcmTaskService class you provided ");
        sb.append(str);
        sb.append(" does not seem to support receiving com.google.android.gms.gcm.ACTION_TASK_READY");
        throw new IllegalArgumentException(sb.toString());
    }

    public void cancelAllTasks(Class<? extends GcmTaskService> cls) {
        ComponentName componentName = new ComponentName(this.zzh, cls);
        zze(componentName.getClassName());
        zzd().zzd(componentName);
    }

    public void cancelTask(String str, Class<? extends GcmTaskService> cls) {
        ComponentName componentName = new ComponentName(this.zzh, cls);
        zzd(str);
        zze(componentName.getClassName());
        zzd().zzd(componentName, str);
    }

    public synchronized void schedule(Task task) {
        zze(task.getServiceName());
        if (zzd().zzd(task)) {
            Map map = (Map) this.zzi.get(task.getServiceName());
            if (map != null && map.containsKey(task.getTag())) {
                map.put(task.getTag(), Boolean.valueOf(true));
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean zzd(String str, String str2) {
        Map map = (Map) this.zzi.get(str2);
        if (map == null) {
            map = new ArrayMap();
            this.zzi.put(str2, map);
        }
        return map.put(str, Boolean.valueOf(false)) == null;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void zze(String str, String str2) {
        Map map = (Map) this.zzi.get(str2);
        if (map != null) {
            if ((map.remove(str) != null) && map.isEmpty()) {
                this.zzi.remove(str2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean zzf(String str) {
        return this.zzi.containsKey(str);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized boolean zzf(String str, String str2) {
        Map map = (Map) this.zzi.get(str2);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }
}
