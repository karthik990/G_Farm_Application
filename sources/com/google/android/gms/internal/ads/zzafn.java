package com.google.android.gms.internal.ads;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.ads.internal.zzbv;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
public final class zzafn extends zzaeo {
    private static final Object sLock = new Object();
    private static zzafn zzchh;
    private final Context mContext;
    private final zzafm zzchi;
    private final ScheduledExecutorService zzchj = Executors.newSingleThreadScheduledExecutor();

    private zzafn(Context context, zzafm zzafm) {
        this.mContext = context;
        this.zzchi = zzafm;
    }

    private static zzaej zza(Context context, zzafm zzafm, zzaef zzaef, ScheduledExecutorService scheduledExecutorService) {
        zznv zznv;
        char c;
        Context context2 = context;
        zzafm zzafm2 = zzafm;
        zzaef zzaef2 = zzaef;
        ScheduledExecutorService scheduledExecutorService2 = scheduledExecutorService;
        zzakb.zzck("Starting ad request from service using: google.afma.request.getAdDictionary");
        zznx zznx = new zznx(((Boolean) zzkb.zzik().zzd(zznk.zzawh)).booleanValue(), "load_ad", zzaef2.zzacv.zzarb);
        if (zzaef2.versionCode > 10 && zzaef2.zzcdl != -1) {
            zznx.zza(zznx.zzd(zzaef2.zzcdl), "cts");
        }
        zznv zzjj = zznx.zzjj();
        zzanz zza = zzano.zza(zzafm2.zzche.zzk(context2), ((Long) zzkb.zzik().zzd(zznk.zzbdf)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService2);
        zzanz zza2 = zzano.zza(zzafm2.zzchd.zzr(context2), ((Long) zzkb.zzik().zzd(zznk.zzbah)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService2);
        zzanz zzcl = zzafm2.zzcgy.zzcl(zzaef2.zzccw.packageName);
        zzanz zzcm = zzafm2.zzcgy.zzcm(zzaef2.zzccw.packageName);
        zzanz zza3 = zzafm2.zzchf.zza(zzaef2.zzccx, zzaef2.zzccw);
        Future zzq = zzbv.zzev().zzq(context2);
        zzanz zzi = zzano.zzi(null);
        Bundle bundle = zzaef2.zzccv.extras;
        String str = "_ad";
        boolean z = (bundle == null || bundle.getString(str) == null) ? false : true;
        if (zzaef2.zzcdr && !z) {
            zzi = zzafm2.zzchb.zza(zzaef2.applicationInfo);
        }
        zznx zznx2 = zznx;
        zzanz zza4 = zzano.zza(zzi, ((Long) zzkb.zzik().zzd(zznk.zzbco)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService2);
        Future zzi2 = zzano.zzi(null);
        if (((Boolean) zzkb.zzik().zzd(zznk.zzayj)).booleanValue()) {
            zznv = zzjj;
            zzi2 = zzano.zza(zzafm2.zzchf.zzae(context2), ((Long) zzkb.zzik().zzd(zznk.zzayk)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService2);
        } else {
            zznv = zzjj;
        }
        Bundle bundle2 = (zzaef2.versionCode < 4 || zzaef2.zzcdc == null) ? null : zzaef2.zzcdc;
        ((Boolean) zzkb.zzik().zzd(zznk.zzawx)).booleanValue();
        zzbv.zzek();
        if (zzakk.zzl(context2, "android.permission.ACCESS_NETWORK_STATE") && ((ConnectivityManager) context2.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
            zzakb.zzck("Device is offline.");
        }
        String uuid = zzaef2.versionCode >= 7 ? zzaef2.zzcdi : UUID.randomUUID().toString();
        new zzaft(context2, uuid, zzaef2.applicationInfo.packageName);
        if (zzaef2.zzccv.extras != null) {
            String string = zzaef2.zzccv.extras.getString(str);
            if (string != null) {
                return zzafs.zza(context2, zzaef2, string);
            }
        }
        List<String> zzf = zzafm2.zzcgz.zzf(zzaef2.zzcdj);
        String str2 = uuid;
        Bundle bundle3 = (Bundle) zzano.zza((Future<T>) zza, null, ((Long) zzkb.zzik().zzd(zznk.zzbdf)).longValue(), TimeUnit.MILLISECONDS);
        zzagk zzagk = (zzagk) zzano.zza((Future<T>) zza2, null);
        Location location = (Location) zzano.zza((Future<T>) zza4, null);
        Info info = (Info) zzano.zza(zzi2, null);
        String str3 = (String) zzano.zza((Future<T>) zza3, null);
        String str4 = (String) zzano.zza((Future<T>) zzcl, null);
        String str5 = (String) zzano.zza((Future<T>) zzcm, null);
        zzaga zzaga = (zzaga) zzano.zza(zzq, null);
        if (zzaga == null) {
            zzakb.zzdk("Error fetching device info. This is not recoverable.");
            return new zzaej(0);
        }
        zzafl zzafl = new zzafl();
        zzafl.zzcgs = zzaef2;
        zzafl.zzcgt = zzaga;
        zzafl.zzcgo = zzagk;
        zzafl.zzaqe = location;
        zzafl.zzcgn = bundle3;
        zzafl.zzccx = str3;
        zzafl.zzcgr = info;
        if (zzf == null) {
            zzafl.zzcdj.clear();
        }
        zzafl.zzcdj = zzf;
        zzafl.zzcdc = bundle2;
        zzafl.zzcgp = str4;
        zzafl.zzcgq = str5;
        Context context3 = context;
        zzafl.zzcgu = zzafm2.zzcgx.zze(context3);
        zzafl.zzcgv = zzafm2.zzcgv;
        JSONObject zza5 = zzafs.zza(context3, zzafl);
        if (zza5 == null) {
            return new zzaej(0);
        }
        if (zzaef2.versionCode < 7) {
            try {
                zza5.put("request_id", str2);
            } catch (JSONException unused) {
            }
        }
        zznx zznx3 = zznx2;
        zznv zznv2 = zznv;
        zznx3.zza(zznv2, "arc");
        zznx3.zzjj();
        ScheduledExecutorService scheduledExecutorService3 = scheduledExecutorService;
        zzanz zza6 = zzano.zza(zzano.zza(zzafm2.zzchg.zzof().zzf(zza5), zzafo.zzxn, (Executor) scheduledExecutorService3), 10, TimeUnit.SECONDS, scheduledExecutorService3);
        zzanz zzop = zzafm2.zzcha.zzop();
        if (zzop != null) {
            zzanm.zza(zzop, "AdRequestServiceImpl.loadAd.flags");
        }
        zzafz zzafz = (zzafz) zzano.zza((Future<T>) zza6, null);
        if (zzafz == null) {
            return new zzaej(0);
        }
        if (zzafz.getErrorCode() != -2) {
            return new zzaej(zzafz.getErrorCode());
        }
        zznx3.zzjm();
        zzaej zza7 = !TextUtils.isEmpty(zzafz.zzom()) ? zzafs.zza(context3, zzaef2, zzafz.zzom()) : null;
        if (zza7 == null && !TextUtils.isEmpty(zzafz.getUrl())) {
            zza7 = zza(zzaef, context, zzaef2.zzacr.zzcw, zzafz.getUrl(), str4, str5, zzafz, zznx3, zzafm);
        }
        if (zza7 == null) {
            c = 0;
            zza7 = new zzaej(0);
        } else {
            c = 0;
        }
        String[] strArr = new String[1];
        strArr[c] = "tts";
        zznx3.zza(zznv2, strArr);
        zza7.zzcfd = zznx3.zzjk();
        return zza7;
    }

    /* JADX INFO: used method not loaded: com.google.android.gms.internal.ads.zzafx.zza(long, boolean):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e9, code lost:
        r0 = r6.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        r6 = new java.io.InputStreamReader(r11.getInputStream());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        com.google.android.gms.ads.internal.zzbv.zzek();
        r10 = com.google.android.gms.internal.ads.zzakk.zza(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        com.google.android.gms.common.util.IOUtils.closeQuietly((java.io.Closeable) r6);
        r3.zzdg(r10);
        zza(r0, r12, r10, r9);
        r5.zza(r0, r12, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0109, code lost:
        if (r1 == null) goto L_0x0116;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x010b, code lost:
        r1.zza(r4, "ufe");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0116, code lost:
        r0 = r5.zza(r7, r24.zzon());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r11.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0121, code lost:
        if (r2 == null) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0123, code lost:
        r2.zzchc.zzor();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0128, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0129, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x012a, code lost:
        r16 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x012d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012e, code lost:
        r16 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        com.google.android.gms.common.util.IOUtils.closeQuietly((java.io.Closeable) r16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0133, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x014e, code lost:
        com.google.android.gms.internal.ads.zzakb.zzdk("No location header to follow redirect.");
        r0 = new com.google.android.gms.internal.ads.zzaej(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        r11.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x015c, code lost:
        if (r2 == null) goto L_0x0163;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x015e, code lost:
        r2.zzchc.zzor();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0163, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x017d, code lost:
        com.google.android.gms.internal.ads.zzakb.zzdk("Too many redirects.");
        r0 = new com.google.android.gms.internal.ads.zzaej(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:?, code lost:
        r11.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x018b, code lost:
        if (r2 == null) goto L_0x0192;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x018d, code lost:
        r2.zzchc.zzor();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0192, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008a A[Catch:{ all -> 0x00c0, all -> 0x01cc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.ads.zzaej zza(com.google.android.gms.internal.ads.zzaef r18, android.content.Context r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, com.google.android.gms.internal.ads.zzafz r24, com.google.android.gms.internal.ads.zznx r25, com.google.android.gms.internal.ads.zzafm r26) {
        /*
            r0 = r18
            r1 = r25
            r2 = r26
            if (r1 == 0) goto L_0x000d
            com.google.android.gms.internal.ads.zznv r4 = r25.zzjj()
            goto L_0x000e
        L_0x000d:
            r4 = 0
        L_0x000e:
            com.google.android.gms.internal.ads.zzafx r5 = new com.google.android.gms.internal.ads.zzafx     // Catch:{ IOException -> 0x01d8 }
            java.lang.String r6 = r24.zzoi()     // Catch:{ IOException -> 0x01d8 }
            r5.<init>(r0, r6)     // Catch:{ IOException -> 0x01d8 }
            java.lang.String r6 = "AdRequestServiceImpl: Sending request: "
            java.lang.String r7 = java.lang.String.valueOf(r21)     // Catch:{ IOException -> 0x01d8 }
            int r8 = r7.length()     // Catch:{ IOException -> 0x01d8 }
            if (r8 == 0) goto L_0x0029
            java.lang.String r6 = r6.concat(r7)     // Catch:{ IOException -> 0x01d8 }
            r7 = r6
            goto L_0x002e
        L_0x0029:
            java.lang.String r7 = new java.lang.String     // Catch:{ IOException -> 0x01d8 }
            r7.<init>(r6)     // Catch:{ IOException -> 0x01d8 }
        L_0x002e:
            com.google.android.gms.internal.ads.zzakb.zzck(r7)     // Catch:{ IOException -> 0x01d8 }
            java.net.URL r6 = new java.net.URL     // Catch:{ IOException -> 0x01d8 }
            r7 = r21
            r6.<init>(r7)     // Catch:{ IOException -> 0x01d8 }
            com.google.android.gms.common.util.Clock r7 = com.google.android.gms.ads.internal.zzbv.zzer()     // Catch:{ IOException -> 0x01d8 }
            long r7 = r7.elapsedRealtime()     // Catch:{ IOException -> 0x01d8 }
            r9 = 0
            r10 = 0
        L_0x0042:
            if (r2 == 0) goto L_0x0049
            com.google.android.gms.internal.ads.zzagi r11 = r2.zzchc     // Catch:{ IOException -> 0x01d8 }
            r11.zzoq()     // Catch:{ IOException -> 0x01d8 }
        L_0x0049:
            java.net.URLConnection r11 = r6.openConnection()     // Catch:{ IOException -> 0x01d8 }
            java.net.HttpURLConnection r11 = (java.net.HttpURLConnection) r11     // Catch:{ IOException -> 0x01d8 }
            com.google.android.gms.internal.ads.zzakk r12 = com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x01cc }
            r13 = r19
            r14 = r20
            r12.zza(r13, r14, r9, r11)     // Catch:{ all -> 0x01cc }
            boolean r12 = r24.zzok()     // Catch:{ all -> 0x01cc }
            if (r12 == 0) goto L_0x007e
            boolean r12 = android.text.TextUtils.isEmpty(r22)     // Catch:{ all -> 0x01cc }
            if (r12 != 0) goto L_0x006e
            java.lang.String r12 = "x-afma-drt-cookie"
            r15 = r22
            r11.addRequestProperty(r12, r15)     // Catch:{ all -> 0x01cc }
            goto L_0x0070
        L_0x006e:
            r15 = r22
        L_0x0070:
            boolean r12 = android.text.TextUtils.isEmpty(r23)     // Catch:{ all -> 0x01cc }
            if (r12 != 0) goto L_0x0080
            java.lang.String r12 = "x-afma-drt-v2-cookie"
            r3 = r23
            r11.addRequestProperty(r12, r3)     // Catch:{ all -> 0x01cc }
            goto L_0x0082
        L_0x007e:
            r15 = r22
        L_0x0080:
            r3 = r23
        L_0x0082:
            java.lang.String r12 = r0.zzcds     // Catch:{ all -> 0x01cc }
            boolean r17 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x01cc }
            if (r17 != 0) goto L_0x0094
            java.lang.String r17 = "Sending webview cookie in ad request header."
            com.google.android.gms.internal.ads.zzakb.zzck(r17)     // Catch:{ all -> 0x01cc }
            java.lang.String r9 = "Cookie"
            r11.addRequestProperty(r9, r12)     // Catch:{ all -> 0x01cc }
        L_0x0094:
            r9 = 1
            if (r24 == 0) goto L_0x00cb
            java.lang.String r12 = r24.zzoj()     // Catch:{ all -> 0x01cc }
            boolean r12 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x01cc }
            if (r12 != 0) goto L_0x00cb
            r11.setDoOutput(r9)     // Catch:{ all -> 0x01cc }
            java.lang.String r12 = r24.zzoj()     // Catch:{ all -> 0x01cc }
            byte[] r12 = r12.getBytes()     // Catch:{ all -> 0x01cc }
            int r9 = r12.length     // Catch:{ all -> 0x01cc }
            r11.setFixedLengthStreamingMode(r9)     // Catch:{ all -> 0x01cc }
            java.io.BufferedOutputStream r9 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00c4 }
            java.io.OutputStream r3 = r11.getOutputStream()     // Catch:{ all -> 0x00c4 }
            r9.<init>(r3)     // Catch:{ all -> 0x00c4 }
            r9.write(r12)     // Catch:{ all -> 0x00c0 }
            com.google.android.gms.common.util.IOUtils.closeQuietly(r9)     // Catch:{ all -> 0x01cc }
            goto L_0x00cc
        L_0x00c0:
            r0 = move-exception
            r16 = r9
            goto L_0x00c7
        L_0x00c4:
            r0 = move-exception
            r16 = 0
        L_0x00c7:
            com.google.android.gms.common.util.IOUtils.closeQuietly(r16)     // Catch:{ all -> 0x01cc }
            throw r0     // Catch:{ all -> 0x01cc }
        L_0x00cb:
            r12 = 0
        L_0x00cc:
            com.google.android.gms.internal.ads.zzamy r3 = new com.google.android.gms.internal.ads.zzamy     // Catch:{ all -> 0x01cc }
            java.lang.String r9 = r0.zzcdi     // Catch:{ all -> 0x01cc }
            r3.<init>(r9)     // Catch:{ all -> 0x01cc }
            r3.zza(r11, r12)     // Catch:{ all -> 0x01cc }
            int r9 = r11.getResponseCode()     // Catch:{ all -> 0x01cc }
            java.util.Map r12 = r11.getHeaderFields()     // Catch:{ all -> 0x01cc }
            r3.zza(r11, r9)     // Catch:{ all -> 0x01cc }
            r0 = 200(0xc8, float:2.8E-43)
            r13 = 300(0x12c, float:4.2E-43)
            if (r9 < r0) goto L_0x0134
            if (r9 >= r13) goto L_0x0134
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x01cc }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ all -> 0x012d }
            java.io.InputStream r10 = r11.getInputStream()     // Catch:{ all -> 0x012d }
            r6.<init>(r10)     // Catch:{ all -> 0x012d }
            com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x0129 }
            java.lang.String r10 = com.google.android.gms.internal.ads.zzakk.zza(r6)     // Catch:{ all -> 0x0129 }
            com.google.android.gms.common.util.IOUtils.closeQuietly(r6)     // Catch:{ all -> 0x01cc }
            r3.zzdg(r10)     // Catch:{ all -> 0x01cc }
            zza(r0, r12, r10, r9)     // Catch:{ all -> 0x01cc }
            r5.zza(r0, r12, r10)     // Catch:{ all -> 0x01cc }
            if (r1 == 0) goto L_0x0116
            r0 = 1
            java.lang.String[] r0 = new java.lang.String[r0]     // Catch:{ all -> 0x01cc }
            java.lang.String r3 = "ufe"
            r6 = 0
            r0[r6] = r3     // Catch:{ all -> 0x01cc }
            r1.zza(r4, r0)     // Catch:{ all -> 0x01cc }
        L_0x0116:
            boolean r0 = r24.zzon()     // Catch:{ all -> 0x01cc }
            com.google.android.gms.internal.ads.zzaej r0 = r5.zza(r7, r0)     // Catch:{ all -> 0x01cc }
            r11.disconnect()     // Catch:{ IOException -> 0x01d8 }
            if (r2 == 0) goto L_0x0128
            com.google.android.gms.internal.ads.zzagi r1 = r2.zzchc     // Catch:{ IOException -> 0x01d8 }
            r1.zzor()     // Catch:{ IOException -> 0x01d8 }
        L_0x0128:
            return r0
        L_0x0129:
            r0 = move-exception
            r16 = r6
            goto L_0x0130
        L_0x012d:
            r0 = move-exception
            r16 = 0
        L_0x0130:
            com.google.android.gms.common.util.IOUtils.closeQuietly(r16)     // Catch:{ all -> 0x01cc }
            throw r0     // Catch:{ all -> 0x01cc }
        L_0x0134:
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x01cc }
            r3 = 0
            zza(r0, r12, r3, r9)     // Catch:{ all -> 0x01cc }
            if (r9 < r13) goto L_0x01a5
            r0 = 400(0x190, float:5.6E-43)
            if (r9 >= r0) goto L_0x01a5
            java.lang.String r0 = "Location"
            java.lang.String r0 = r11.getHeaderField(r0)     // Catch:{ all -> 0x01cc }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x01cc }
            if (r6 == 0) goto L_0x0164
            java.lang.String r0 = "No location header to follow redirect."
            com.google.android.gms.internal.ads.zzakb.zzdk(r0)     // Catch:{ all -> 0x01cc }
            com.google.android.gms.internal.ads.zzaej r0 = new com.google.android.gms.internal.ads.zzaej     // Catch:{ all -> 0x01cc }
            r1 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x01cc }
            r11.disconnect()     // Catch:{ IOException -> 0x01d8 }
            if (r2 == 0) goto L_0x0163
            com.google.android.gms.internal.ads.zzagi r1 = r2.zzchc     // Catch:{ IOException -> 0x01d8 }
            r1.zzor()     // Catch:{ IOException -> 0x01d8 }
        L_0x0163:
            return r0
        L_0x0164:
            java.net.URL r6 = new java.net.URL     // Catch:{ all -> 0x01cc }
            r6.<init>(r0)     // Catch:{ all -> 0x01cc }
            r0 = 1
            int r10 = r10 + r0
            com.google.android.gms.internal.ads.zzna<java.lang.Integer> r0 = com.google.android.gms.internal.ads.zznk.zzbes     // Catch:{ all -> 0x01cc }
            com.google.android.gms.internal.ads.zzni r9 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ all -> 0x01cc }
            java.lang.Object r0 = r9.zzd(r0)     // Catch:{ all -> 0x01cc }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x01cc }
            int r0 = r0.intValue()     // Catch:{ all -> 0x01cc }
            if (r10 <= r0) goto L_0x0193
            java.lang.String r0 = "Too many redirects."
            com.google.android.gms.internal.ads.zzakb.zzdk(r0)     // Catch:{ all -> 0x01cc }
            com.google.android.gms.internal.ads.zzaej r0 = new com.google.android.gms.internal.ads.zzaej     // Catch:{ all -> 0x01cc }
            r1 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x01cc }
            r11.disconnect()     // Catch:{ IOException -> 0x01d8 }
            if (r2 == 0) goto L_0x0192
            com.google.android.gms.internal.ads.zzagi r1 = r2.zzchc     // Catch:{ IOException -> 0x01d8 }
            r1.zzor()     // Catch:{ IOException -> 0x01d8 }
        L_0x0192:
            return r0
        L_0x0193:
            r5.zzl(r12)     // Catch:{ all -> 0x01cc }
            r11.disconnect()     // Catch:{ IOException -> 0x01d8 }
            if (r2 == 0) goto L_0x01a0
            com.google.android.gms.internal.ads.zzagi r0 = r2.zzchc     // Catch:{ IOException -> 0x01d8 }
            r0.zzor()     // Catch:{ IOException -> 0x01d8 }
        L_0x01a0:
            r0 = r18
            r9 = 0
            goto L_0x0042
        L_0x01a5:
            r0 = 46
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cc }
            r1.<init>(r0)     // Catch:{ all -> 0x01cc }
            java.lang.String r0 = "Received error HTTP response code: "
            r1.append(r0)     // Catch:{ all -> 0x01cc }
            r1.append(r9)     // Catch:{ all -> 0x01cc }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x01cc }
            com.google.android.gms.internal.ads.zzakb.zzdk(r0)     // Catch:{ all -> 0x01cc }
            com.google.android.gms.internal.ads.zzaej r0 = new com.google.android.gms.internal.ads.zzaej     // Catch:{ all -> 0x01cc }
            r1 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x01cc }
            r11.disconnect()     // Catch:{ IOException -> 0x01d8 }
            if (r2 == 0) goto L_0x01cb
            com.google.android.gms.internal.ads.zzagi r1 = r2.zzchc     // Catch:{ IOException -> 0x01d8 }
            r1.zzor()     // Catch:{ IOException -> 0x01d8 }
        L_0x01cb:
            return r0
        L_0x01cc:
            r0 = move-exception
            r11.disconnect()     // Catch:{ IOException -> 0x01d8 }
            if (r2 == 0) goto L_0x01d7
            com.google.android.gms.internal.ads.zzagi r1 = r2.zzchc     // Catch:{ IOException -> 0x01d8 }
            r1.zzor()     // Catch:{ IOException -> 0x01d8 }
        L_0x01d7:
            throw r0     // Catch:{ IOException -> 0x01d8 }
        L_0x01d8:
            r0 = move-exception
            java.lang.String r1 = "Error while connecting to ad server: "
            java.lang.String r0 = r0.getMessage()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r2 = r0.length()
            if (r2 == 0) goto L_0x01ee
            java.lang.String r0 = r1.concat(r0)
            goto L_0x01f3
        L_0x01ee:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r1)
        L_0x01f3:
            com.google.android.gms.internal.ads.zzakb.zzdk(r0)
            com.google.android.gms.internal.ads.zzaej r0 = new com.google.android.gms.internal.ads.zzaej
            r1 = 2
            r0.<init>(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzafn.zza(com.google.android.gms.internal.ads.zzaef, android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.google.android.gms.internal.ads.zzafz, com.google.android.gms.internal.ads.zznx, com.google.android.gms.internal.ads.zzafm):com.google.android.gms.internal.ads.zzaej");
    }

    public static zzafn zza(Context context, zzafm zzafm) {
        zzafn zzafn;
        synchronized (sLock) {
            if (zzchh == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                zznk.initialize(context);
                zzchh = new zzafn(context, zzafm);
                if (context.getApplicationContext() != null) {
                    zzbv.zzek().zzal(context);
                }
                zzajz.zzai(context);
            }
            zzafn = zzchh;
        }
        return zzafn;
    }

    private static void zza(String str, Map<String, List<String>> map, String str2, int i) {
        if (zzakb.isLoggable(2)) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 39);
            sb.append("Http Response: {\n  URL:\n    ");
            sb.append(str);
            sb.append("\n  Headers:");
            zzakb.m1419v(sb.toString());
            if (map != null) {
                for (String str3 : map.keySet()) {
                    StringBuilder sb2 = new StringBuilder(String.valueOf(str3).length() + 5);
                    sb2.append("    ");
                    sb2.append(str3);
                    sb2.append(":");
                    zzakb.m1419v(sb2.toString());
                    for (String valueOf : (List) map.get(str3)) {
                        String str4 = "      ";
                        String valueOf2 = String.valueOf(valueOf);
                        zzakb.m1419v(valueOf2.length() != 0 ? str4.concat(valueOf2) : new String(str4));
                    }
                }
            }
            zzakb.m1419v("  Body:");
            if (str2 != null) {
                int i2 = 0;
                while (i2 < Math.min(str2.length(), 100000)) {
                    int i3 = i2 + 1000;
                    zzakb.m1419v(str2.substring(i2, Math.min(str2.length(), i3)));
                    i2 = i3;
                }
            } else {
                zzakb.m1419v("    null");
            }
            StringBuilder sb3 = new StringBuilder(34);
            sb3.append("  Response Code:\n    ");
            sb3.append(i);
            sb3.append("\n}");
            zzakb.m1419v(sb3.toString());
        }
    }

    public final void zza(zzaef zzaef, zzaeq zzaeq) {
        zzbv.zzeo().zzd(this.mContext, zzaef.zzacr);
        zzanz zzb = zzaki.zzb(new zzafp(this, zzaef, zzaeq));
        zzbv.zzez().zzsa();
        zzbv.zzez().getHandler().postDelayed(new zzafq(this, zzb), DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
    }

    public final void zza(zzaey zzaey, zzaet zzaet) {
        zzakb.m1419v("Nonagon code path entered in octagon");
        throw new IllegalArgumentException();
    }

    public final zzaej zzb(zzaef zzaef) {
        return zza(this.mContext, this.zzchi, zzaef, this.zzchj);
    }

    public final void zzb(zzaey zzaey, zzaet zzaet) {
        zzakb.m1419v("Nonagon code path entered in octagon");
        throw new IllegalArgumentException();
    }
}
