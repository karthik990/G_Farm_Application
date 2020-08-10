package com.google.android.gms.gcm;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    /* access modifiers changed from: private */
    public ComponentName componentName;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    /* access modifiers changed from: private */
    public int zzt;
    private ExecutorService zzu;
    private Messenger zzv;
    /* access modifiers changed from: private */
    public GcmNetworkManager zzw;

    class zzd extends Handler {
        zzd(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            String str = "GcmTaskService";
            if (!UidVerifier.uidHasPackageName(GcmTaskService.this, message.sendingUid, "com.google.android.gms")) {
                Log.e(str, "unable to verify presence of Google Play Services");
                return;
            }
            int i = message.what;
            if (i == 1) {
                Bundle data = message.getData();
                if (!data.isEmpty()) {
                    Messenger messenger = message.replyTo;
                    if (messenger != null) {
                        String string = data.getString("tag");
                        ArrayList parcelableArrayList = data.getParcelableArrayList("triggered_uris");
                        if (!GcmTaskService.this.zzg(string)) {
                            zze zze = new zze(string, messenger, data.getBundle("extras"), (List<Uri>) parcelableArrayList);
                            GcmTaskService.this.zzd(zze);
                        }
                    }
                }
            } else if (i == 2) {
                if (Log.isLoggable(str, 3)) {
                    String valueOf = String.valueOf(message);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 45);
                    sb.append("ignoring unimplemented stop message for now: ");
                    sb.append(valueOf);
                    Log.d(str, sb.toString());
                }
            } else if (i != 4) {
                String valueOf2 = String.valueOf(message);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 31);
                sb2.append("Unrecognized message received: ");
                sb2.append(valueOf2);
                Log.e(str, sb2.toString());
            } else {
                GcmTaskService.this.onInitializeTasks();
            }
        }
    }

    class zze implements Runnable {
        private final Bundle extras;
        private final String tag;
        private final zzg zzaa;
        private final Messenger zzab;
        private final List<Uri> zzz;

        zze(String str, IBinder iBinder, Bundle bundle, List<Uri> list) {
            zzg zzg;
            this.tag = str;
            if (iBinder == null) {
                zzg = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.gcm.INetworkTaskCallback");
                zzg = queryLocalInterface instanceof zzg ? (zzg) queryLocalInterface : new zzh(iBinder);
            }
            this.zzaa = zzg;
            this.extras = bundle;
            this.zzz = list;
            this.zzab = null;
        }

        zze(String str, Messenger messenger, Bundle bundle, List<Uri> list) {
            this.tag = str;
            this.zzab = messenger;
            this.extras = bundle;
            this.zzz = list;
            this.zzaa = null;
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x005c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void zze(int r6) {
            /*
                r5 = this;
                com.google.android.gms.gcm.GcmTaskService r0 = com.google.android.gms.gcm.GcmTaskService.this
                java.lang.Object r0 = r0.lock
                monitor-enter(r0)
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ RemoteException -> 0x00d0 }
                com.google.android.gms.gcm.GcmNetworkManager r1 = r1.zzw     // Catch:{ RemoteException -> 0x00d0 }
                java.lang.String r2 = r5.tag     // Catch:{ RemoteException -> 0x00d0 }
                com.google.android.gms.gcm.GcmTaskService r3 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ RemoteException -> 0x00d0 }
                android.content.ComponentName r3 = r3.componentName     // Catch:{ RemoteException -> 0x00d0 }
                java.lang.String r3 = r3.getClassName()     // Catch:{ RemoteException -> 0x00d0 }
                boolean r1 = r1.zzf(r2, r3)     // Catch:{ RemoteException -> 0x00d0 }
                if (r1 == 0) goto L_0x005d
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzw     // Catch:{ all -> 0x0167 }
                java.lang.String r1 = r5.tag     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x0167 }
                r6.zze(r1, r2)     // Catch:{ all -> 0x0167 }
                boolean r6 = r5.zzf()     // Catch:{ all -> 0x0167 }
                if (r6 != 0) goto L_0x005b
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzw     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r1 = r1.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r1 = r1.getClassName()     // Catch:{ all -> 0x0167 }
                boolean r6 = r6.zzf(r1)     // Catch:{ all -> 0x0167 }
                if (r6 != 0) goto L_0x005b
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                int r1 = r1.zzt     // Catch:{ all -> 0x0167 }
                r6.stopSelf(r1)     // Catch:{ all -> 0x0167 }
            L_0x005b:
                monitor-exit(r0)     // Catch:{ all -> 0x0167 }
                return
            L_0x005d:
                boolean r1 = r5.zzf()     // Catch:{ RemoteException -> 0x00d0 }
                if (r1 == 0) goto L_0x008c
                android.os.Messenger r1 = r5.zzab     // Catch:{ RemoteException -> 0x00d0 }
                android.os.Message r2 = android.os.Message.obtain()     // Catch:{ RemoteException -> 0x00d0 }
                r3 = 3
                r2.what = r3     // Catch:{ RemoteException -> 0x00d0 }
                r2.arg1 = r6     // Catch:{ RemoteException -> 0x00d0 }
                android.os.Bundle r6 = new android.os.Bundle     // Catch:{ RemoteException -> 0x00d0 }
                r6.<init>()     // Catch:{ RemoteException -> 0x00d0 }
                java.lang.String r3 = "component"
                com.google.android.gms.gcm.GcmTaskService r4 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ RemoteException -> 0x00d0 }
                android.content.ComponentName r4 = r4.componentName     // Catch:{ RemoteException -> 0x00d0 }
                r6.putParcelable(r3, r4)     // Catch:{ RemoteException -> 0x00d0 }
                java.lang.String r3 = "tag"
                java.lang.String r4 = r5.tag     // Catch:{ RemoteException -> 0x00d0 }
                r6.putString(r3, r4)     // Catch:{ RemoteException -> 0x00d0 }
                r2.setData(r6)     // Catch:{ RemoteException -> 0x00d0 }
                r1.send(r2)     // Catch:{ RemoteException -> 0x00d0 }
                goto L_0x0091
            L_0x008c:
                com.google.android.gms.gcm.zzg r1 = r5.zzaa     // Catch:{ RemoteException -> 0x00d0 }
                r1.zzf(r6)     // Catch:{ RemoteException -> 0x00d0 }
            L_0x0091:
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzw     // Catch:{ all -> 0x0167 }
                java.lang.String r1 = r5.tag     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x0167 }
                r6.zze(r1, r2)     // Catch:{ all -> 0x0167 }
                boolean r6 = r5.zzf()     // Catch:{ all -> 0x0167 }
                if (r6 != 0) goto L_0x0128
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzw     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r1 = r1.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r1 = r1.getClassName()     // Catch:{ all -> 0x0167 }
                boolean r6 = r6.zzf(r1)     // Catch:{ all -> 0x0167 }
                if (r6 != 0) goto L_0x0128
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                int r1 = r1.zzt     // Catch:{ all -> 0x0167 }
            L_0x00ca:
                r6.stopSelf(r1)     // Catch:{ all -> 0x0167 }
                goto L_0x0128
            L_0x00ce:
                r6 = move-exception
                goto L_0x012a
            L_0x00d0:
                java.lang.String r6 = "GcmTaskService"
                java.lang.String r1 = "Error reporting result of operation to scheduler for "
                java.lang.String r2 = r5.tag     // Catch:{ all -> 0x00ce }
                java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00ce }
                int r3 = r2.length()     // Catch:{ all -> 0x00ce }
                if (r3 == 0) goto L_0x00e5
                java.lang.String r1 = r1.concat(r2)     // Catch:{ all -> 0x00ce }
                goto L_0x00eb
            L_0x00e5:
                java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00ce }
                r2.<init>(r1)     // Catch:{ all -> 0x00ce }
                r1 = r2
            L_0x00eb:
                android.util.Log.e(r6, r1)     // Catch:{ all -> 0x00ce }
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzw     // Catch:{ all -> 0x0167 }
                java.lang.String r1 = r5.tag     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x0167 }
                r6.zze(r1, r2)     // Catch:{ all -> 0x0167 }
                boolean r6 = r5.zzf()     // Catch:{ all -> 0x0167 }
                if (r6 != 0) goto L_0x0128
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzw     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r1 = r1.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r1 = r1.getClassName()     // Catch:{ all -> 0x0167 }
                boolean r6 = r6.zzf(r1)     // Catch:{ all -> 0x0167 }
                if (r6 != 0) goto L_0x0128
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                int r1 = r1.zzt     // Catch:{ all -> 0x0167 }
                goto L_0x00ca
            L_0x0128:
                monitor-exit(r0)     // Catch:{ all -> 0x0167 }
                return
            L_0x012a:
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r1 = r1.zzw     // Catch:{ all -> 0x0167 }
                java.lang.String r2 = r5.tag     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r3 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r3 = r3.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r3 = r3.getClassName()     // Catch:{ all -> 0x0167 }
                r1.zze(r2, r3)     // Catch:{ all -> 0x0167 }
                boolean r1 = r5.zzf()     // Catch:{ all -> 0x0167 }
                if (r1 != 0) goto L_0x0166
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmNetworkManager r1 = r1.zzw     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x0167 }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x0167 }
                boolean r1 = r1.zzf(r2)     // Catch:{ all -> 0x0167 }
                if (r1 != 0) goto L_0x0166
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x0167 }
                int r2 = r2.zzt     // Catch:{ all -> 0x0167 }
                r1.stopSelf(r2)     // Catch:{ all -> 0x0167 }
            L_0x0166:
                throw r6     // Catch:{ all -> 0x0167 }
            L_0x0167:
                r6 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0167 }
                goto L_0x016b
            L_0x016a:
                throw r6
            L_0x016b:
                goto L_0x016a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmTaskService.zze.zze(int):void");
        }

        private final boolean zzf() {
            return this.zzab != null;
        }

        public final void run() {
            zze(GcmTaskService.this.onRunTask(new TaskParams(this.tag, this.extras, this.zzz)));
        }
    }

    private final void zzd(int i) {
        synchronized (this.lock) {
            this.zzt = i;
            if (!this.zzw.zzf(this.componentName.getClassName())) {
                stopSelf(this.zzt);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zzd(zze zze2) {
        try {
            this.zzu.execute(zze2);
        } catch (RejectedExecutionException e) {
            Log.e("GcmTaskService", "Executor is shutdown. onDestroy was called but main looper had an unprocessed start task message. The task will be retried with backoff delay.", e);
            zze2.zze(1);
        }
    }

    /* access modifiers changed from: private */
    public final boolean zzg(String str) {
        boolean z;
        synchronized (this.lock) {
            z = !this.zzw.zzd(str, this.componentName.getClassName());
            if (z) {
                String packageName = getPackageName();
                StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 44 + String.valueOf(str).length());
                sb.append(packageName);
                sb.append(" ");
                sb.append(str);
                sb.append(": Task already running, won't start another");
                Log.w("GcmTaskService", sb.toString());
            }
        }
        return z;
    }

    public IBinder onBind(Intent intent) {
        if (intent != null && PlatformVersion.isAtLeastLollipop()) {
            if (SERVICE_ACTION_EXECUTE_TASK.equals(intent.getAction())) {
                return this.zzv.getBinder();
            }
        }
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.zzw = GcmNetworkManager.getInstance(this);
        this.zzu = Executors.newFixedThreadPool(2, new zze(this));
        this.zzv = new Messenger(new zzd(Looper.getMainLooper()));
        this.componentName = new ComponentName(this, getClass());
    }

    public void onDestroy() {
        super.onDestroy();
        List shutdownNow = this.zzu.shutdownNow();
        if (!shutdownNow.isEmpty()) {
            int size = shutdownNow.size();
            StringBuilder sb = new StringBuilder(79);
            sb.append("Shutting down, but not all tasks are finished executing. Remaining: ");
            sb.append(size);
            Log.e("GcmTaskService", sb.toString());
        }
    }

    public void onInitializeTasks() {
    }

    public abstract int onRunTask(TaskParams taskParams);

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            zzd(i2);
            return 2;
        }
        try {
            intent.setExtrasClassLoader(PendingCallback.class.getClassLoader());
            String action = intent.getAction();
            String str = "GcmTaskService";
            if (SERVICE_ACTION_EXECUTE_TASK.equals(action)) {
                String stringExtra = intent.getStringExtra("tag");
                Parcelable parcelableExtra = intent.getParcelableExtra("callback");
                Bundle bundleExtra = intent.getBundleExtra("extras");
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("triggered_uris");
                if (!(parcelableExtra instanceof PendingCallback)) {
                    String packageName = getPackageName();
                    StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 47 + String.valueOf(stringExtra).length());
                    sb.append(packageName);
                    sb.append(" ");
                    sb.append(stringExtra);
                    sb.append(": Could not process request, invalid callback.");
                    Log.e(str, sb.toString());
                    return 2;
                } else if (zzg(stringExtra)) {
                    zzd(i2);
                    return 2;
                } else {
                    zze zze2 = new zze(stringExtra, ((PendingCallback) parcelableExtra).zzal, bundleExtra, (List<Uri>) parcelableArrayListExtra);
                    zzd(zze2);
                }
            } else if (SERVICE_ACTION_INITIALIZE.equals(action)) {
                onInitializeTasks();
            } else {
                StringBuilder sb2 = new StringBuilder(String.valueOf(action).length() + 37);
                sb2.append("Unknown action received ");
                sb2.append(action);
                sb2.append(", terminating");
                Log.e(str, sb2.toString());
            }
            zzd(i2);
            return 2;
        } finally {
            zzd(i2);
        }
    }
}
