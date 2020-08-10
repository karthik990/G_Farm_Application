package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public final class zzeh {
    private static final Map<String, zzeh> zzjy = new HashMap();
    private static final Executor zzkc = zzel.zzki;
    private final ExecutorService zzjz;
    private final zzew zzka;
    private Task<zzeo> zzkb = null;

    private zzeh(ExecutorService executorService, zzew zzew) {
        this.zzjz = executorService;
        this.zzka = zzew;
    }

    public final Task<zzeo> zzb(zzeo zzeo) {
        zzd(zzeo);
        return zza(zzeo, false);
    }

    public final zzeo zzco() {
        return zzb(5);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r6 = zzcp();
        r0 = java.util.concurrent.TimeUnit.SECONDS;
        r1 = new com.google.android.gms.internal.firebase_remote_config.zzen(null);
        r6.addOnSuccessListener(zzkc, (com.google.android.gms.tasks.OnSuccessListener<? super TResult>) r1);
        r6.addOnFailureListener(zzkc, (com.google.android.gms.tasks.OnFailureListener) r1);
        r6.addOnCanceledListener(zzkc, (com.google.android.gms.tasks.OnCanceledListener) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
        if (r1.await(5, r0) == false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        if (r6.isSuccessful() == false) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0047, code lost:
        return (com.google.android.gms.internal.firebase_remote_config.zzeo) r6.getResult();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0051, code lost:
        throw new java.util.concurrent.ExecutionException(r6.getException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0059, code lost:
        throw new java.util.concurrent.TimeoutException("Task await timed out.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005f, code lost:
        android.util.Log.d("ConfigCacheClient", "Reading from storage file failed.", r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0066, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.firebase_remote_config.zzeo zzb(long r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            com.google.android.gms.tasks.Task<com.google.android.gms.internal.firebase_remote_config.zzeo> r5 = r4.zzkb     // Catch:{ all -> 0x0067 }
            if (r5 == 0) goto L_0x0017
            com.google.android.gms.tasks.Task<com.google.android.gms.internal.firebase_remote_config.zzeo> r5 = r4.zzkb     // Catch:{ all -> 0x0067 }
            boolean r5 = r5.isSuccessful()     // Catch:{ all -> 0x0067 }
            if (r5 == 0) goto L_0x0017
            com.google.android.gms.tasks.Task<com.google.android.gms.internal.firebase_remote_config.zzeo> r5 = r4.zzkb     // Catch:{ all -> 0x0067 }
            java.lang.Object r5 = r5.getResult()     // Catch:{ all -> 0x0067 }
            com.google.android.gms.internal.firebase_remote_config.zzeo r5 = (com.google.android.gms.internal.firebase_remote_config.zzeo) r5     // Catch:{ all -> 0x0067 }
            monitor-exit(r4)     // Catch:{ all -> 0x0067 }
            return r5
        L_0x0017:
            monitor-exit(r4)     // Catch:{ all -> 0x0067 }
            r5 = 0
            com.google.android.gms.tasks.Task r6 = r4.zzcp()     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            com.google.android.gms.internal.firebase_remote_config.zzen r1 = new com.google.android.gms.internal.firebase_remote_config.zzen     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            r1.<init>()     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            java.util.concurrent.Executor r2 = zzkc     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            r6.addOnSuccessListener(r2, r1)     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            java.util.concurrent.Executor r2 = zzkc     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            r6.addOnFailureListener(r2, r1)     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            java.util.concurrent.Executor r2 = zzkc     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            r6.addOnCanceledListener(r2, r1)     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            r2 = 5
            boolean r0 = r1.await(r2, r0)     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            if (r0 == 0) goto L_0x0052
            boolean r0 = r6.isSuccessful()     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            if (r0 == 0) goto L_0x0048
            java.lang.Object r6 = r6.getResult()     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            com.google.android.gms.internal.firebase_remote_config.zzeo r6 = (com.google.android.gms.internal.firebase_remote_config.zzeo) r6     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            return r6
        L_0x0048:
            java.util.concurrent.ExecutionException r0 = new java.util.concurrent.ExecutionException     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            java.lang.Exception r6 = r6.getException()     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            r0.<init>(r6)     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            throw r0     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
        L_0x0052:
            java.util.concurrent.TimeoutException r6 = new java.util.concurrent.TimeoutException     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            java.lang.String r0 = "Task await timed out."
            r6.<init>(r0)     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
            throw r6     // Catch:{ InterruptedException -> 0x005e, ExecutionException -> 0x005c, TimeoutException -> 0x005a }
        L_0x005a:
            r6 = move-exception
            goto L_0x005f
        L_0x005c:
            r6 = move-exception
            goto L_0x005f
        L_0x005e:
            r6 = move-exception
        L_0x005f:
            java.lang.String r0 = "ConfigCacheClient"
            java.lang.String r1 = "Reading from storage file failed."
            android.util.Log.d(r0, r1, r6)
            return r5
        L_0x0067:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0067 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzeh.zzb(long):com.google.android.gms.internal.firebase_remote_config.zzeo");
    }

    public final Task<zzeo> zzc(zzeo zzeo) {
        return zza(zzeo, true);
    }

    private final Task<zzeo> zza(zzeo zzeo, boolean z) {
        return Tasks.call(this.zzjz, new zzei(this, zzeo)).onSuccessTask(this.zzjz, new zzej(this, z, zzeo));
    }

    public final synchronized Task<zzeo> zzcp() {
        if (this.zzkb == null || (this.zzkb.isComplete() && !this.zzkb.isSuccessful())) {
            ExecutorService executorService = this.zzjz;
            zzew zzew = this.zzka;
            zzew.getClass();
            this.zzkb = Tasks.call(executorService, zzek.zza(zzew));
        }
        return this.zzkb;
    }

    public final void clear() {
        synchronized (this) {
            this.zzkb = Tasks.forResult(null);
        }
        this.zzka.zzdc();
    }

    private final synchronized void zzd(zzeo zzeo) {
        this.zzkb = Tasks.forResult(zzeo);
    }

    public static synchronized zzeh zza(ExecutorService executorService, zzew zzew) {
        zzeh zzeh;
        synchronized (zzeh.class) {
            String fileName = zzew.getFileName();
            if (!zzjy.containsKey(fileName)) {
                zzjy.put(fileName, new zzeh(executorService, zzew));
            }
            zzeh = (zzeh) zzjy.get(fileName);
        }
        return zzeh;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Task zza(boolean z, zzeo zzeo, Void voidR) throws Exception {
        if (z) {
            zzd(zzeo);
        }
        return Tasks.forResult(zzeo);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Void zze(zzeo zzeo) throws Exception {
        return this.zzka.zzf(zzeo);
    }
}
