package com.google.android.gms.internal.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzcz {
    private static final String TAG = zzcz.class.getSimpleName();
    private volatile boolean zzqt = false;
    protected Context zzrt;
    private ExecutorService zzru;
    private DexClassLoader zzrv;
    private zzck zzrw;
    private byte[] zzrx;
    private volatile AdvertisingIdClient zzry = null;
    private Future zzrz = null;
    private boolean zzsa;
    /* access modifiers changed from: private */
    public volatile zzba zzsb = null;
    private Future zzsc = null;
    private zzcc zzsd;
    private boolean zzse = false;
    private boolean zzsf = false;
    private Map<Pair<String, String>, zzeg> zzsg;
    private boolean zzsh = false;
    /* access modifiers changed from: private */
    public boolean zzsi;
    private boolean zzsj;

    final class zza extends BroadcastReceiver {
        private zza() {
        }

        /* synthetic */ zza(zzcz zzcz, zzda zzda) {
            this();
        }

        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                zzcz.this.zzsi = true;
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                zzcz.this.zzsi = false;
            }
        }
    }

    private zzcz(Context context) {
        boolean z = true;
        this.zzsi = true;
        this.zzsj = false;
        Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            z = false;
        }
        this.zzsa = z;
        if (this.zzsa) {
            context = applicationContext;
        }
        this.zzrt = context;
        this.zzsg = new HashMap();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(19:0|1|2|(1:4)|5|6|7|8|(1:10)(1:11)|12|(1:14)(1:15)|16|17|18|(2:20|(1:22)(2:23|24))|25|26|27|(15:28|29|(2:31|(1:33)(2:34|35))|36|(1:38)|39|40|41|42|43|44|45|(1:47)|48|69)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004d */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0056 A[Catch:{ zzcl -> 0x0150, zzcw -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0089 A[Catch:{ all -> 0x011f, FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b4 A[Catch:{ all -> 0x011f, FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00fa A[Catch:{ zzcl -> 0x0150, zzcw -> 0x0157 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.ads.zzcz zza(android.content.Context r9, java.lang.String r10, java.lang.String r11, boolean r12) {
        /*
            java.lang.String r0 = "%s/%s.dex"
            com.google.android.gms.internal.ads.zzcz r1 = new com.google.android.gms.internal.ads.zzcz
            r1.<init>(r9)
            com.google.android.gms.internal.ads.zzda r9 = new com.google.android.gms.internal.ads.zzda     // Catch:{ zzcw -> 0x0157 }
            r9.<init>()     // Catch:{ zzcw -> 0x0157 }
            java.util.concurrent.ExecutorService r9 = java.util.concurrent.Executors.newCachedThreadPool(r9)     // Catch:{ zzcw -> 0x0157 }
            r1.zzru = r9     // Catch:{ zzcw -> 0x0157 }
            r1.zzqt = r12     // Catch:{ zzcw -> 0x0157 }
            if (r12 == 0) goto L_0x0023
            java.util.concurrent.ExecutorService r9 = r1.zzru     // Catch:{ zzcw -> 0x0157 }
            com.google.android.gms.internal.ads.zzdb r12 = new com.google.android.gms.internal.ads.zzdb     // Catch:{ zzcw -> 0x0157 }
            r12.<init>(r1)     // Catch:{ zzcw -> 0x0157 }
            java.util.concurrent.Future r9 = r9.submit(r12)     // Catch:{ zzcw -> 0x0157 }
            r1.zzrz = r9     // Catch:{ zzcw -> 0x0157 }
        L_0x0023:
            java.util.concurrent.ExecutorService r9 = r1.zzru     // Catch:{ zzcw -> 0x0157 }
            com.google.android.gms.internal.ads.zzdd r12 = new com.google.android.gms.internal.ads.zzdd     // Catch:{ zzcw -> 0x0157 }
            r12.<init>(r1)     // Catch:{ zzcw -> 0x0157 }
            r9.execute(r12)     // Catch:{ zzcw -> 0x0157 }
            r9 = 1
            r12 = 0
            com.google.android.gms.common.GoogleApiAvailabilityLight r2 = com.google.android.gms.common.GoogleApiAvailabilityLight.getInstance()     // Catch:{ all -> 0x004d }
            android.content.Context r3 = r1.zzrt     // Catch:{ all -> 0x004d }
            int r3 = r2.getApkVersion(r3)     // Catch:{ all -> 0x004d }
            if (r3 <= 0) goto L_0x003d
            r3 = 1
            goto L_0x003e
        L_0x003d:
            r3 = 0
        L_0x003e:
            r1.zzse = r3     // Catch:{ all -> 0x004d }
            android.content.Context r3 = r1.zzrt     // Catch:{ all -> 0x004d }
            int r2 = r2.isGooglePlayServicesAvailable(r3)     // Catch:{ all -> 0x004d }
            if (r2 != 0) goto L_0x004a
            r2 = 1
            goto L_0x004b
        L_0x004a:
            r2 = 0
        L_0x004b:
            r1.zzsf = r2     // Catch:{ all -> 0x004d }
        L_0x004d:
            r1.zza(r12, r9)     // Catch:{ zzcw -> 0x0157 }
            boolean r2 = com.google.android.gms.internal.ads.zzdg.isMainThread()     // Catch:{ zzcw -> 0x0157 }
            if (r2 == 0) goto L_0x0071
            com.google.android.gms.internal.ads.zzna<java.lang.Boolean> r2 = com.google.android.gms.internal.ads.zznk.zzbaz     // Catch:{ zzcw -> 0x0157 }
            com.google.android.gms.internal.ads.zzni r3 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ zzcw -> 0x0157 }
            java.lang.Object r2 = r3.zzd(r2)     // Catch:{ zzcw -> 0x0157 }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ zzcw -> 0x0157 }
            boolean r2 = r2.booleanValue()     // Catch:{ zzcw -> 0x0157 }
            if (r2 != 0) goto L_0x0069
            goto L_0x0071
        L_0x0069:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch:{ zzcw -> 0x0157 }
            java.lang.String r10 = "Task Context initialization must not be called from the UI thread."
            r9.<init>(r10)     // Catch:{ zzcw -> 0x0157 }
            throw r9     // Catch:{ zzcw -> 0x0157 }
        L_0x0071:
            com.google.android.gms.internal.ads.zzck r2 = new com.google.android.gms.internal.ads.zzck     // Catch:{ zzcw -> 0x0157 }
            r3 = 0
            r2.<init>(r3)     // Catch:{ zzcw -> 0x0157 }
            r1.zzrw = r2     // Catch:{ zzcw -> 0x0157 }
            com.google.android.gms.internal.ads.zzck r2 = r1.zzrw     // Catch:{ zzcl -> 0x0150 }
            byte[] r10 = r2.zzl(r10)     // Catch:{ zzcl -> 0x0150 }
            r1.zzrx = r10     // Catch:{ zzcl -> 0x0150 }
            android.content.Context r10 = r1.zzrt     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.io.File r10 = r10.getCacheDir()     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            if (r10 != 0) goto L_0x009a
            android.content.Context r10 = r1.zzrt     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.lang.String r2 = "dex"
            java.io.File r10 = r10.getDir(r2, r12)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            if (r10 == 0) goto L_0x0094
            goto L_0x009a
        L_0x0094:
            com.google.android.gms.internal.ads.zzcw r9 = new com.google.android.gms.internal.ads.zzcw     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r9.<init>()     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            throw r9     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
        L_0x009a:
            java.lang.String r2 = "1521499837408"
            java.io.File r4 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.lang.String r5 = "%s/%s.jar"
            r6 = 2
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r7[r12] = r10     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r7[r9] = r2     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.lang.String r5 = java.lang.String.format(r5, r7)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r4.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            boolean r5 = r4.exists()     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            if (r5 != 0) goto L_0x00cb
            com.google.android.gms.internal.ads.zzck r5 = r1.zzrw     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            byte[] r7 = r1.zzrx     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            byte[] r11 = r5.zza(r7, r11)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r4.createNewFile()     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r5.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            int r7 = r11.length     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r5.write(r11, r12, r7)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r5.close()     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
        L_0x00cb:
            r1.zzb(r10, r2)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            dalvik.system.DexClassLoader r11 = new dalvik.system.DexClassLoader     // Catch:{ all -> 0x011f }
            java.lang.String r5 = r4.getAbsolutePath()     // Catch:{ all -> 0x011f }
            java.lang.String r7 = r10.getAbsolutePath()     // Catch:{ all -> 0x011f }
            android.content.Context r8 = r1.zzrt     // Catch:{ all -> 0x011f }
            java.lang.ClassLoader r8 = r8.getClassLoader()     // Catch:{ all -> 0x011f }
            r11.<init>(r5, r7, r3, r8)     // Catch:{ all -> 0x011f }
            r1.zzrv = r11     // Catch:{ all -> 0x011f }
            zzb(r4)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r1.zza(r10, r2)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.lang.Object[] r11 = new java.lang.Object[r6]     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r11[r12] = r10     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r11[r9] = r2     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.lang.String r10 = java.lang.String.format(r0, r11)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            zzm(r10)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            boolean r10 = r1.zzsj     // Catch:{ zzcw -> 0x0157 }
            if (r10 != 0) goto L_0x0115
            android.content.IntentFilter r10 = new android.content.IntentFilter     // Catch:{ zzcw -> 0x0157 }
            r10.<init>()     // Catch:{ zzcw -> 0x0157 }
            java.lang.String r11 = "android.intent.action.USER_PRESENT"
            r10.addAction(r11)     // Catch:{ zzcw -> 0x0157 }
            java.lang.String r11 = "android.intent.action.SCREEN_OFF"
            r10.addAction(r11)     // Catch:{ zzcw -> 0x0157 }
            android.content.Context r11 = r1.zzrt     // Catch:{ zzcw -> 0x0157 }
            com.google.android.gms.internal.ads.zzcz$zza r12 = new com.google.android.gms.internal.ads.zzcz$zza     // Catch:{ zzcw -> 0x0157 }
            r12.<init>(r1, r3)     // Catch:{ zzcw -> 0x0157 }
            r11.registerReceiver(r12, r10)     // Catch:{ zzcw -> 0x0157 }
            r1.zzsj = r9     // Catch:{ zzcw -> 0x0157 }
        L_0x0115:
            com.google.android.gms.internal.ads.zzcc r10 = new com.google.android.gms.internal.ads.zzcc     // Catch:{ zzcw -> 0x0157 }
            r10.<init>(r1)     // Catch:{ zzcw -> 0x0157 }
            r1.zzsd = r10     // Catch:{ zzcw -> 0x0157 }
            r1.zzsh = r9     // Catch:{ zzcw -> 0x0157 }
            goto L_0x0157
        L_0x011f:
            r11 = move-exception
            zzb(r4)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r1.zza(r10, r2)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.lang.Object[] r3 = new java.lang.Object[r6]     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r3[r12] = r10     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            r3[r9] = r2     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            java.lang.String r9 = java.lang.String.format(r0, r3)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            zzm(r9)     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
            throw r11     // Catch:{ FileNotFoundException -> 0x0149, IOException -> 0x0142, zzcl -> 0x013b, NullPointerException -> 0x0134 }
        L_0x0134:
            r9 = move-exception
            com.google.android.gms.internal.ads.zzcw r10 = new com.google.android.gms.internal.ads.zzcw     // Catch:{ zzcw -> 0x0157 }
            r10.<init>(r9)     // Catch:{ zzcw -> 0x0157 }
            throw r10     // Catch:{ zzcw -> 0x0157 }
        L_0x013b:
            r9 = move-exception
            com.google.android.gms.internal.ads.zzcw r10 = new com.google.android.gms.internal.ads.zzcw     // Catch:{ zzcw -> 0x0157 }
            r10.<init>(r9)     // Catch:{ zzcw -> 0x0157 }
            throw r10     // Catch:{ zzcw -> 0x0157 }
        L_0x0142:
            r9 = move-exception
            com.google.android.gms.internal.ads.zzcw r10 = new com.google.android.gms.internal.ads.zzcw     // Catch:{ zzcw -> 0x0157 }
            r10.<init>(r9)     // Catch:{ zzcw -> 0x0157 }
            throw r10     // Catch:{ zzcw -> 0x0157 }
        L_0x0149:
            r9 = move-exception
            com.google.android.gms.internal.ads.zzcw r10 = new com.google.android.gms.internal.ads.zzcw     // Catch:{ zzcw -> 0x0157 }
            r10.<init>(r9)     // Catch:{ zzcw -> 0x0157 }
            throw r10     // Catch:{ zzcw -> 0x0157 }
        L_0x0150:
            r9 = move-exception
            com.google.android.gms.internal.ads.zzcw r10 = new com.google.android.gms.internal.ads.zzcw     // Catch:{ zzcw -> 0x0157 }
            r10.<init>(r9)     // Catch:{ zzcw -> 0x0157 }
            throw r10     // Catch:{ zzcw -> 0x0157 }
        L_0x0157:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzcz.zza(android.content.Context, java.lang.String, java.lang.String, boolean):com.google.android.gms.internal.ads.zzcz");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:20|21|22|23|24|25|26|27|28|30) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0091 */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a3 A[SYNTHETIC, Splitter:B:39:0x00a3] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00aa A[SYNTHETIC, Splitter:B:43:0x00aa] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b4 A[SYNTHETIC, Splitter:B:52:0x00b4] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00bb A[SYNTHETIC, Splitter:B:56:0x00bb] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(java.io.File r9, java.lang.String r10) {
        /*
            r8 = this;
            java.io.File r0 = new java.io.File
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r9
            r4 = 1
            r2[r4] = r10
            java.lang.String r5 = "%s/%s.tmp"
            java.lang.String r2 = java.lang.String.format(r5, r2)
            r0.<init>(r2)
            boolean r2 = r0.exists()
            if (r2 == 0) goto L_0x001b
            return
        L_0x001b:
            java.io.File r2 = new java.io.File
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r1[r3] = r9
            r1[r4] = r10
            java.lang.String r9 = "%s/%s.dex"
            java.lang.String r9 = java.lang.String.format(r9, r1)
            r2.<init>(r9)
            boolean r9 = r2.exists()
            if (r9 != 0) goto L_0x0033
            return
        L_0x0033:
            long r4 = r2.length()
            r6 = 0
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 > 0) goto L_0x003e
            return
        L_0x003e:
            int r9 = (int) r4
            byte[] r9 = new byte[r9]
            r1 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b1, all -> 0x009f }
            r4.<init>(r2)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b1, all -> 0x009f }
            int r5 = r4.read(r9)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            if (r5 > 0) goto L_0x0054
            r4.close()     // Catch:{ IOException -> 0x0050 }
        L_0x0050:
            zzb(r2)
            return
        L_0x0054:
            com.google.android.gms.internal.ads.zzbe r5 = new com.google.android.gms.internal.ads.zzbe     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            r5.<init>()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            java.lang.String r6 = android.os.Build.VERSION.SDK     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            byte[] r6 = r6.getBytes()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            r5.zzgs = r6     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            byte[] r10 = r10.getBytes()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            r5.zzgr = r10     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            com.google.android.gms.internal.ads.zzck r10 = r8.zzrw     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            byte[] r6 = r8.zzrx     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            java.lang.String r9 = r10.zzb(r6, r9)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            byte[] r9 = r9.getBytes()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            r5.data = r9     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            byte[] r9 = com.google.android.gms.internal.ads.zzbk.zzb(r9)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            r5.zzgq = r9     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            r0.createNewFile()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            r9.<init>(r0)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b2, all -> 0x009d }
            byte[] r10 = com.google.android.gms.internal.ads.zzbfi.zzb(r5)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x009b, all -> 0x0098 }
            int r0 = r10.length     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x009b, all -> 0x0098 }
            r9.write(r10, r3, r0)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x009b, all -> 0x0098 }
            r9.close()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x009b, all -> 0x0098 }
            r4.close()     // Catch:{ IOException -> 0x0091 }
        L_0x0091:
            r9.close()     // Catch:{ IOException -> 0x0094 }
        L_0x0094:
            zzb(r2)
            return
        L_0x0098:
            r10 = move-exception
            r1 = r9
            goto L_0x00a1
        L_0x009b:
            r1 = r9
            goto L_0x00b2
        L_0x009d:
            r10 = move-exception
            goto L_0x00a1
        L_0x009f:
            r10 = move-exception
            r4 = r1
        L_0x00a1:
            if (r4 == 0) goto L_0x00a8
            r4.close()     // Catch:{ IOException -> 0x00a7 }
            goto L_0x00a8
        L_0x00a7:
        L_0x00a8:
            if (r1 == 0) goto L_0x00ad
            r1.close()     // Catch:{ IOException -> 0x00ad }
        L_0x00ad:
            zzb(r2)
            throw r10
        L_0x00b1:
            r4 = r1
        L_0x00b2:
            if (r4 == 0) goto L_0x00b9
            r4.close()     // Catch:{ IOException -> 0x00b8 }
            goto L_0x00b9
        L_0x00b8:
        L_0x00b9:
            if (r1 == 0) goto L_0x00be
            r1.close()     // Catch:{ IOException -> 0x00be }
        L_0x00be:
            zzb(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzcz.zza(java.io.File, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public static boolean zza(int i, zzba zzba) {
        if (i < 4) {
            if (zzba == null) {
                return true;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbc)).booleanValue() && (zzba.zzcx == null || zzba.zzcx.equals("0000000000000000000000000000000000000000000000000000000000000000"))) {
                return true;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbd)).booleanValue() && (zzba.zzfn == null || zzba.zzfn.zzgl == null || zzba.zzfn.zzgl.longValue() == -2)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public final void zzal() {
        try {
            if (this.zzry == null && this.zzsa) {
                AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(this.zzrt);
                advertisingIdClient.start();
                this.zzry = advertisingIdClient;
            }
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException unused) {
            this.zzry = null;
        }
    }

    private final zzba zzam() {
        try {
            return zzatq.zzl(this.zzrt, this.zzrt.getPackageName(), Integer.toString(this.zzrt.getPackageManager().getPackageInfo(this.zzrt.getPackageName(), 0).versionCode));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static void zzb(File file) {
        if (!file.exists()) {
            Log.d(TAG, String.format("File %s not found. No need for deletion", new Object[]{file.getAbsolutePath()}));
            return;
        }
        file.delete();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:29|30|31|32|33|34|35|36) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x00b1 */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00c7 A[SYNTHETIC, Splitter:B:52:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ce A[SYNTHETIC, Splitter:B:56:0x00ce] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d5 A[SYNTHETIC, Splitter:B:64:0x00d5] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00dc A[SYNTHETIC, Splitter:B:68:0x00dc] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzb(java.io.File r10, java.lang.String r11) {
        /*
            r9 = this;
            java.io.File r0 = new java.io.File
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r10
            r4 = 1
            r2[r4] = r11
            java.lang.String r5 = "%s/%s.tmp"
            java.lang.String r2 = java.lang.String.format(r5, r2)
            r0.<init>(r2)
            boolean r2 = r0.exists()
            if (r2 != 0) goto L_0x001b
            return r3
        L_0x001b:
            java.io.File r2 = new java.io.File
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r1[r3] = r10
            r1[r4] = r11
            java.lang.String r10 = "%s/%s.dex"
            java.lang.String r10 = java.lang.String.format(r10, r1)
            r2.<init>(r10)
            boolean r10 = r2.exists()
            if (r10 == 0) goto L_0x0033
            return r3
        L_0x0033:
            r10 = 0
            long r5 = r0.length()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d2, all -> 0x00c3 }
            r7 = 0
            int r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r1 > 0) goto L_0x0042
            zzb(r0)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d2, all -> 0x00c3 }
            return r3
        L_0x0042:
            int r1 = (int) r5     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d2, all -> 0x00c3 }
            byte[] r1 = new byte[r1]     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d2, all -> 0x00c3 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d2, all -> 0x00c3 }
            r5.<init>(r0)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d2, all -> 0x00c3 }
            int r6 = r5.read(r1)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            if (r6 > 0) goto L_0x005e
            java.lang.String r11 = TAG     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            java.lang.String r1 = "Cannot read the cache data."
            android.util.Log.d(r11, r1)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            zzb(r0)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            r5.close()     // Catch:{ IOException -> 0x005d }
        L_0x005d:
            return r3
        L_0x005e:
            com.google.android.gms.internal.ads.zzbe r6 = new com.google.android.gms.internal.ads.zzbe     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            r6.<init>()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            com.google.android.gms.internal.ads.zzbfi r1 = com.google.android.gms.internal.ads.zzbfi.zza(r6, r1)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            com.google.android.gms.internal.ads.zzbe r1 = (com.google.android.gms.internal.ads.zzbe) r1     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            java.lang.String r6 = new java.lang.String     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            byte[] r7 = r1.zzgr     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            r6.<init>(r7)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            boolean r11 = r11.equals(r6)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            if (r11 == 0) goto L_0x00ba
            byte[] r11 = r1.zzgq     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            byte[] r6 = r1.data     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            byte[] r6 = com.google.android.gms.internal.ads.zzbk.zzb(r6)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            boolean r11 = java.util.Arrays.equals(r11, r6)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            if (r11 == 0) goto L_0x00ba
            byte[] r11 = r1.zzgs     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            java.lang.String r6 = android.os.Build.VERSION.SDK     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            byte[] r6 = r6.getBytes()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            boolean r11 = java.util.Arrays.equals(r11, r6)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            if (r11 != 0) goto L_0x0093
            goto L_0x00ba
        L_0x0093:
            com.google.android.gms.internal.ads.zzck r11 = r9.zzrw     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            byte[] r0 = r9.zzrx     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            java.lang.String r6 = new java.lang.String     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            byte[] r1 = r1.data     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            r6.<init>(r1)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            byte[] r11 = r11.zza(r0, r6)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            r2.createNewFile()     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            r0.<init>(r2)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            int r10 = r11.length     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b8, all -> 0x00b5 }
            r0.write(r11, r3, r10)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00b8, all -> 0x00b5 }
            r5.close()     // Catch:{ IOException -> 0x00b1 }
        L_0x00b1:
            r0.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x00b4:
            return r4
        L_0x00b5:
            r11 = move-exception
            r10 = r0
            goto L_0x00c5
        L_0x00b8:
            r10 = r0
            goto L_0x00d3
        L_0x00ba:
            zzb(r0)     // Catch:{ zzcl | IOException | NoSuchAlgorithmException -> 0x00d3, all -> 0x00c1 }
            r5.close()     // Catch:{ IOException -> 0x00c0 }
        L_0x00c0:
            return r3
        L_0x00c1:
            r11 = move-exception
            goto L_0x00c5
        L_0x00c3:
            r11 = move-exception
            r5 = r10
        L_0x00c5:
            if (r5 == 0) goto L_0x00cc
            r5.close()     // Catch:{ IOException -> 0x00cb }
            goto L_0x00cc
        L_0x00cb:
        L_0x00cc:
            if (r10 == 0) goto L_0x00d1
            r10.close()     // Catch:{ IOException -> 0x00d1 }
        L_0x00d1:
            throw r11
        L_0x00d2:
            r5 = r10
        L_0x00d3:
            if (r5 == 0) goto L_0x00da
            r5.close()     // Catch:{ IOException -> 0x00d9 }
            goto L_0x00da
        L_0x00d9:
        L_0x00da:
            if (r10 == 0) goto L_0x00df
            r10.close()     // Catch:{ IOException -> 0x00df }
        L_0x00df:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzcz.zzb(java.io.File, java.lang.String):boolean");
    }

    private static void zzm(String str) {
        zzb(new File(str));
    }

    public final Context getContext() {
        return this.zzrt;
    }

    public final boolean isInitialized() {
        return this.zzsh;
    }

    public final Method zza(String str, String str2) {
        zzeg zzeg = (zzeg) this.zzsg.get(new Pair(str, str2));
        if (zzeg == null) {
            return null;
        }
        return zzeg.zzaw();
    }

    /* access modifiers changed from: 0000 */
    public final void zza(int i, boolean z) {
        if (this.zzsf) {
            Future submit = this.zzru.submit(new zzdc(this, i, z));
            if (i == 0) {
                this.zzsc = submit;
            }
        }
    }

    public final boolean zza(String str, String str2, Class<?>... clsArr) {
        if (this.zzsg.containsKey(new Pair(str, str2))) {
            return false;
        }
        this.zzsg.put(new Pair(str, str2), new zzeg(this, str, str2, clsArr));
        return true;
    }

    public final ExecutorService zzab() {
        return this.zzru;
    }

    public final DexClassLoader zzac() {
        return this.zzrv;
    }

    public final zzck zzad() {
        return this.zzrw;
    }

    public final byte[] zzae() {
        return this.zzrx;
    }

    public final boolean zzaf() {
        return this.zzse;
    }

    public final zzcc zzag() {
        return this.zzsd;
    }

    public final boolean zzah() {
        return this.zzsf;
    }

    public final boolean zzai() {
        return this.zzsi;
    }

    public final zzba zzaj() {
        return this.zzsb;
    }

    public final Future zzak() {
        return this.zzsc;
    }

    public final AdvertisingIdClient zzan() {
        if (!this.zzqt) {
            return null;
        }
        if (this.zzry != null) {
            return this.zzry;
        }
        Future future = this.zzrz;
        if (future != null) {
            try {
                future.get(AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, TimeUnit.MILLISECONDS);
                this.zzrz = null;
            } catch (InterruptedException | ExecutionException unused) {
            } catch (TimeoutException unused2) {
                this.zzrz.cancel(true);
            }
        }
        return this.zzry;
    }

    /* access modifiers changed from: 0000 */
    public final zzba zzb(int i, boolean z) {
        if (i > 0 && z) {
            try {
                Thread.sleep((long) (i * 1000));
            } catch (InterruptedException unused) {
            }
        }
        return zzam();
    }

    public final int zzx() {
        if (this.zzsd != null) {
            return zzcc.zzx();
        }
        return Integer.MIN_VALUE;
    }
}
