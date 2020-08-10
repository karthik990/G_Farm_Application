package com.google.android.gms.internal.gtm;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Preconditions;
import com.mobiroller.constants.Constants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import kotlin.text.Typography;
import org.objectweb.asm.signature.SignatureVisitor;

final class zzck extends zzan {
    /* access modifiers changed from: private */
    public static final byte[] zzabr = Constants.NEW_LINE.getBytes();
    private final String zzabp = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{"GoogleAnalytics", zzao.VERSION, VERSION.RELEASE, zzcz.zza(Locale.getDefault()), Build.MODEL, Build.ID});
    private final zzcv zzabq;

    zzck(zzap zzap) {
        super(zzap);
        this.zzabq = new zzcv(zzap.zzcn());
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
        zza("Network initialized. User agent", this.zzabp);
    }

    public final boolean zzfr() {
        NetworkInfo networkInfo;
        zzk.zzav();
        zzdb();
        try {
            networkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException unused) {
            networkInfo = null;
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        zzq("No network connectivity");
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0159, code lost:
        if (zza(r5) == 200) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x019b, code lost:
        if (zza(r6, r5) == 200) goto L_0x0135;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01b5 A[EDGE_INSN: B:71:0x01b5->B:67:0x01b5 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<java.lang.Long> zzb(java.util.List<com.google.android.gms.internal.gtm.zzcd> r9) {
        /*
            r8 = this;
            com.google.android.gms.analytics.zzk.zzav()
            r8.zzdb()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r9)
            com.google.android.gms.internal.gtm.zzbq r0 = r8.zzcp()
            java.util.Set r0 = r0.zzew()
            boolean r0 = r0.isEmpty()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0059
            com.google.android.gms.internal.gtm.zzcv r0 = r8.zzabq
            com.google.android.gms.internal.gtm.zzbz<java.lang.Integer> r3 = com.google.android.gms.internal.gtm.zzby.zzaab
            java.lang.Object r3 = r3.get()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            long r3 = (long) r3
            r5 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 * r5
            boolean r0 = r0.zzj(r3)
            if (r0 != 0) goto L_0x0033
            goto L_0x0059
        L_0x0033:
            com.google.android.gms.internal.gtm.zzbz<java.lang.String> r0 = com.google.android.gms.internal.gtm.zzby.zzzu
            java.lang.Object r0 = r0.get()
            java.lang.String r0 = (java.lang.String) r0
            com.google.android.gms.internal.gtm.zzbg r0 = com.google.android.gms.internal.gtm.zzbg.zzz(r0)
            com.google.android.gms.internal.gtm.zzbg r3 = com.google.android.gms.internal.gtm.zzbg.NONE
            if (r0 == r3) goto L_0x0045
            r0 = 1
            goto L_0x0046
        L_0x0045:
            r0 = 0
        L_0x0046:
            com.google.android.gms.internal.gtm.zzbz<java.lang.String> r3 = com.google.android.gms.internal.gtm.zzby.zzzv
            java.lang.Object r3 = r3.get()
            java.lang.String r3 = (java.lang.String) r3
            com.google.android.gms.internal.gtm.zzbm r3 = com.google.android.gms.internal.gtm.zzbm.zzaa(r3)
            com.google.android.gms.internal.gtm.zzbm r4 = com.google.android.gms.internal.gtm.zzbm.GZIP
            if (r3 != r4) goto L_0x005a
            r3 = r0
            r0 = 1
            goto L_0x005c
        L_0x0059:
            r0 = 0
        L_0x005a:
            r3 = r0
            r0 = 0
        L_0x005c:
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 == 0) goto L_0x0105
            boolean r1 = r9.isEmpty()
            r1 = r1 ^ r2
            com.google.android.gms.common.internal.Preconditions.checkArgument(r1)
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r0)
            int r2 = r9.size()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r3 = "Uploading batched hits. compression, count"
            r8.zza(r3, r1, r2)
            com.google.android.gms.internal.gtm.zzcl r1 = new com.google.android.gms.internal.gtm.zzcl
            r1.<init>(r8)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r9 = r9.iterator()
        L_0x0087:
            boolean r3 = r9.hasNext()
            if (r3 == 0) goto L_0x00a5
            java.lang.Object r3 = r9.next()
            com.google.android.gms.internal.gtm.zzcd r3 = (com.google.android.gms.internal.gtm.zzcd) r3
            boolean r5 = r1.zze(r3)
            if (r5 == 0) goto L_0x00a5
            long r5 = r3.zzfg()
            java.lang.Long r3 = java.lang.Long.valueOf(r5)
            r2.add(r3)
            goto L_0x0087
        L_0x00a5:
            int r9 = r1.zzfu()
            if (r9 != 0) goto L_0x00ac
            return r2
        L_0x00ac:
            java.net.URL r9 = r8.zzfs()
            if (r9 != 0) goto L_0x00b8
            java.lang.String r9 = "Failed to build batching endpoint url"
            r8.zzu(r9)
            goto L_0x0100
        L_0x00b8:
            if (r0 == 0) goto L_0x00c3
            byte[] r0 = r1.getPayload()
            int r9 = r8.zzb(r9, r0)
            goto L_0x00cb
        L_0x00c3:
            byte[] r0 = r1.getPayload()
            int r9 = r8.zza(r9, r0)
        L_0x00cb:
            if (r4 != r9) goto L_0x00db
            int r9 = r1.zzfu()
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            java.lang.String r0 = "Batched upload completed. Hits batched"
            r8.zza(r0, r9)
            return r2
        L_0x00db:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r9)
            java.lang.String r1 = "Network error uploading hits. status code"
            r8.zza(r1, r0)
            com.google.android.gms.internal.gtm.zzbq r0 = r8.zzcp()
            java.util.Set r0 = r0.zzew()
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            boolean r9 = r0.contains(r9)
            if (r9 == 0) goto L_0x0100
            java.lang.String r9 = "Server instructed the client to stop batching"
            r8.zzt(r9)
            com.google.android.gms.internal.gtm.zzcv r9 = r8.zzabq
            r9.start()
        L_0x0100:
            java.util.List r9 = java.util.Collections.emptyList()
            return r9
        L_0x0105:
            java.util.ArrayList r0 = new java.util.ArrayList
            int r3 = r9.size()
            r0.<init>(r3)
            java.util.Iterator r9 = r9.iterator()
        L_0x0112:
            boolean r3 = r9.hasNext()
            if (r3 == 0) goto L_0x01b5
            java.lang.Object r3 = r9.next()
            com.google.android.gms.internal.gtm.zzcd r3 = (com.google.android.gms.internal.gtm.zzcd) r3
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r3)
            boolean r5 = r3.zzfj()
            r5 = r5 ^ r2
            java.lang.String r5 = r8.zza(r3, r5)
            if (r5 != 0) goto L_0x0137
            com.google.android.gms.internal.gtm.zzci r5 = r8.zzco()
            java.lang.String r6 = "Error formatting hit for upload"
            r5.zza(r3, r6)
        L_0x0135:
            r5 = 1
            goto L_0x019e
        L_0x0137:
            int r6 = r5.length()
            com.google.android.gms.internal.gtm.zzbz<java.lang.Integer> r7 = com.google.android.gms.internal.gtm.zzby.zzzt
            java.lang.Object r7 = r7.get()
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r6 > r7) goto L_0x015e
            java.net.URL r5 = r8.zzb(r3, r5)
            if (r5 != 0) goto L_0x0155
            java.lang.String r5 = "Failed to build collect GET endpoint url"
            r8.zzu(r5)
            goto L_0x015c
        L_0x0155:
            int r5 = r8.zza(r5)
            if (r5 != r4) goto L_0x015c
        L_0x015b:
            goto L_0x0135
        L_0x015c:
            r5 = 0
            goto L_0x019e
        L_0x015e:
            java.lang.String r5 = r8.zza(r3, r1)
            if (r5 != 0) goto L_0x016e
            com.google.android.gms.internal.gtm.zzci r5 = r8.zzco()
            java.lang.String r6 = "Error formatting hit for POST upload"
            r5.zza(r3, r6)
            goto L_0x0135
        L_0x016e:
            byte[] r5 = r5.getBytes()
            int r6 = r5.length
            com.google.android.gms.internal.gtm.zzbz<java.lang.Integer> r7 = com.google.android.gms.internal.gtm.zzby.zzzy
            java.lang.Object r7 = r7.get()
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r6 <= r7) goto L_0x018b
            com.google.android.gms.internal.gtm.zzci r5 = r8.zzco()
            java.lang.String r6 = "Hit payload exceeds size limit"
            r5.zza(r3, r6)
            goto L_0x0135
        L_0x018b:
            java.net.URL r6 = r8.zzd(r3)
            if (r6 != 0) goto L_0x0197
            java.lang.String r5 = "Failed to build collect POST endpoint url"
            r8.zzu(r5)
            goto L_0x015c
        L_0x0197:
            int r5 = r8.zza(r6, r5)
            if (r5 != r4) goto L_0x015c
            goto L_0x015b
        L_0x019e:
            if (r5 == 0) goto L_0x01b5
            long r5 = r3.zzfg()
            java.lang.Long r3 = java.lang.Long.valueOf(r5)
            r0.add(r3)
            int r3 = r0.size()
            int r5 = com.google.android.gms.internal.gtm.zzbq.zzer()
            if (r3 < r5) goto L_0x0112
        L_0x01b5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzck.zzb(java.util.List):java.util.List");
    }

    private final int zza(URL url) {
        Preconditions.checkNotNull(url);
        zzb("GET request", url);
        HttpURLConnection httpURLConnection = null;
        try {
            HttpURLConnection zzb = zzb(url);
            zzb.connect();
            zza(zzb);
            int responseCode = zzb.getResponseCode();
            if (responseCode == 200) {
                zzcs().zzcl();
            }
            zzb("GET status", Integer.valueOf(responseCode));
            if (zzb != null) {
                zzb.disconnect();
            }
            return responseCode;
        } catch (IOException e) {
            zzd("Network GET connection error", e);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return 0;
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r5v2, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0079 A[SYNTHETIC, Splitter:B:29:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008b A[SYNTHETIC, Splitter:B:39:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0095  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zza(java.net.URL r5, byte[] r6) {
        /*
            r4 = this;
            java.lang.String r0 = "Error closing http post connection output stream"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r5)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r6)
            int r1 = r6.length
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.String r2 = "POST bytes, url"
            r4.zzb(r2, r1, r5)
            boolean r1 = zzda()
            if (r1 == 0) goto L_0x0022
            java.lang.String r1 = new java.lang.String
            r1.<init>(r6)
            java.lang.String r2 = "Post payload\n"
            r4.zza(r2, r1)
        L_0x0022:
            r1 = 0
            android.content.Context r2 = r4.getContext()     // Catch:{ IOException -> 0x0070, all -> 0x006d }
            r2.getPackageName()     // Catch:{ IOException -> 0x0070, all -> 0x006d }
            java.net.HttpURLConnection r5 = r4.zzb(r5)     // Catch:{ IOException -> 0x0070, all -> 0x006d }
            r2 = 1
            r5.setDoOutput(r2)     // Catch:{ IOException -> 0x006b }
            int r2 = r6.length     // Catch:{ IOException -> 0x006b }
            r5.setFixedLengthStreamingMode(r2)     // Catch:{ IOException -> 0x006b }
            r5.connect()     // Catch:{ IOException -> 0x006b }
            java.io.OutputStream r1 = r5.getOutputStream()     // Catch:{ IOException -> 0x006b }
            r1.write(r6)     // Catch:{ IOException -> 0x006b }
            r4.zza(r5)     // Catch:{ IOException -> 0x006b }
            int r6 = r5.getResponseCode()     // Catch:{ IOException -> 0x006b }
            r2 = 200(0xc8, float:2.8E-43)
            if (r6 != r2) goto L_0x0052
            com.google.android.gms.internal.gtm.zzae r2 = r4.zzcs()     // Catch:{ IOException -> 0x006b }
            r2.zzcl()     // Catch:{ IOException -> 0x006b }
        L_0x0052:
            java.lang.String r2 = "POST status"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)     // Catch:{ IOException -> 0x006b }
            r4.zzb(r2, r3)     // Catch:{ IOException -> 0x006b }
            if (r1 == 0) goto L_0x0065
            r1.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r1 = move-exception
            r4.zze(r0, r1)
        L_0x0065:
            if (r5 == 0) goto L_0x006a
            r5.disconnect()
        L_0x006a:
            return r6
        L_0x006b:
            r6 = move-exception
            goto L_0x0072
        L_0x006d:
            r6 = move-exception
            r5 = r1
            goto L_0x0089
        L_0x0070:
            r6 = move-exception
            r5 = r1
        L_0x0072:
            java.lang.String r2 = "Network POST connection error"
            r4.zzd(r2, r6)     // Catch:{ all -> 0x0088 }
            if (r1 == 0) goto L_0x0081
            r1.close()     // Catch:{ IOException -> 0x007d }
            goto L_0x0081
        L_0x007d:
            r6 = move-exception
            r4.zze(r0, r6)
        L_0x0081:
            if (r5 == 0) goto L_0x0086
            r5.disconnect()
        L_0x0086:
            r5 = 0
            return r5
        L_0x0088:
            r6 = move-exception
        L_0x0089:
            if (r1 == 0) goto L_0x0093
            r1.close()     // Catch:{ IOException -> 0x008f }
            goto L_0x0093
        L_0x008f:
            r1 = move-exception
            r4.zze(r0, r1)
        L_0x0093:
            if (r5 == 0) goto L_0x0098
            r5.disconnect()
        L_0x0098:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzck.zza(java.net.URL, byte[]):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d6 A[SYNTHETIC, Splitter:B:42:0x00d6] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00e8 A[SYNTHETIC, Splitter:B:52:0x00e8] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzb(java.net.URL r11, byte[] r12) {
        /*
            r10 = this;
            java.lang.String r0 = "Error closing http compressed post connection output stream"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r11)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r12)
            r1 = 0
            android.content.Context r2 = r10.getContext()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r2.getPackageName()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r2.<init>()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            java.util.zip.GZIPOutputStream r3 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r3.<init>(r2)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r3.write(r12)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r3.close()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r2.close()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            byte[] r2 = r2.toByteArray()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            java.lang.String r3 = "POST compressed size, ratio %, url"
            int r4 = r2.length     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r5 = 100
            int r7 = r2.length     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            long r7 = (long) r7     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            long r7 = r7 * r5
            int r5 = r12.length     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            long r5 = (long) r5     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            long r7 = r7 / r5
            java.lang.Long r5 = java.lang.Long.valueOf(r7)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r10.zza(r3, r4, r5, r11)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            int r3 = r2.length     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            int r4 = r12.length     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            if (r3 <= r4) goto L_0x0051
            java.lang.String r3 = "Compressed payload is larger then uncompressed. compressed, uncompressed"
            int r4 = r2.length     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            int r5 = r12.length     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r10.zzc(r3, r4, r5)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
        L_0x0051:
            boolean r3 = zzda()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            if (r3 == 0) goto L_0x0073
            java.lang.String r3 = "Post payload"
            java.lang.String r4 = "\n"
            java.lang.String r5 = new java.lang.String     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r5.<init>(r12)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            int r12 = r5.length()     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            if (r12 == 0) goto L_0x006b
            java.lang.String r12 = r4.concat(r5)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            goto L_0x0070
        L_0x006b:
            java.lang.String r12 = new java.lang.String     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r12.<init>(r4)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
        L_0x0070:
            r10.zza(r3, r12)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
        L_0x0073:
            java.net.HttpURLConnection r11 = r10.zzb(r11)     // Catch:{ IOException -> 0x00cd, all -> 0x00ca }
            r12 = 1
            r11.setDoOutput(r12)     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            java.lang.String r12 = "Content-Encoding"
            java.lang.String r3 = "gzip"
            r11.addRequestProperty(r12, r3)     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            int r12 = r2.length     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            r11.setFixedLengthStreamingMode(r12)     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            r11.connect()     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            java.io.OutputStream r12 = r11.getOutputStream()     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            r12.write(r2)     // Catch:{ IOException -> 0x00ba, all -> 0x00b4 }
            r12.close()     // Catch:{ IOException -> 0x00ba, all -> 0x00b4 }
            r10.zza(r11)     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            int r12 = r11.getResponseCode()     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r12 != r2) goto L_0x00a5
            com.google.android.gms.internal.gtm.zzae r2 = r10.zzcs()     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            r2.zzcl()     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
        L_0x00a5:
            java.lang.String r2 = "POST status"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r12)     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            r10.zzb(r2, r3)     // Catch:{ IOException -> 0x00c5, all -> 0x00c0 }
            if (r11 == 0) goto L_0x00b3
            r11.disconnect()
        L_0x00b3:
            return r12
        L_0x00b4:
            r1 = move-exception
            r9 = r12
            r12 = r11
            r11 = r1
            r1 = r9
            goto L_0x00e6
        L_0x00ba:
            r1 = move-exception
            r9 = r12
            r12 = r11
            r11 = r1
            r1 = r9
            goto L_0x00cf
        L_0x00c0:
            r12 = move-exception
            r9 = r12
            r12 = r11
            r11 = r9
            goto L_0x00e6
        L_0x00c5:
            r12 = move-exception
            r9 = r12
            r12 = r11
            r11 = r9
            goto L_0x00cf
        L_0x00ca:
            r11 = move-exception
            r12 = r1
            goto L_0x00e6
        L_0x00cd:
            r11 = move-exception
            r12 = r1
        L_0x00cf:
            java.lang.String r2 = "Network compressed POST connection error"
            r10.zzd(r2, r11)     // Catch:{ all -> 0x00e5 }
            if (r1 == 0) goto L_0x00de
            r1.close()     // Catch:{ IOException -> 0x00da }
            goto L_0x00de
        L_0x00da:
            r11 = move-exception
            r10.zze(r0, r11)
        L_0x00de:
            if (r12 == 0) goto L_0x00e3
            r12.disconnect()
        L_0x00e3:
            r11 = 0
            return r11
        L_0x00e5:
            r11 = move-exception
        L_0x00e6:
            if (r1 == 0) goto L_0x00f0
            r1.close()     // Catch:{ IOException -> 0x00ec }
            goto L_0x00f0
        L_0x00ec:
            r1 = move-exception
            r10.zze(r0, r1)
        L_0x00f0:
            if (r12 == 0) goto L_0x00f5
            r12.disconnect()
        L_0x00f5:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzck.zzb(java.net.URL, byte[]):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0021 A[SYNTHETIC, Splitter:B:19:0x0021] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(java.net.HttpURLConnection r4) throws java.io.IOException {
        /*
            r3 = this;
            java.lang.String r0 = "Error closing http connection input stream"
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ all -> 0x001d }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ all -> 0x001b }
        L_0x000a:
            int r2 = r4.read(r1)     // Catch:{ all -> 0x001b }
            if (r2 > 0) goto L_0x000a
            if (r4 == 0) goto L_0x001a
            r4.close()     // Catch:{ IOException -> 0x0016 }
            return
        L_0x0016:
            r4 = move-exception
            r3.zze(r0, r4)
        L_0x001a:
            return
        L_0x001b:
            r1 = move-exception
            goto L_0x001f
        L_0x001d:
            r1 = move-exception
            r4 = 0
        L_0x001f:
            if (r4 == 0) goto L_0x0029
            r4.close()     // Catch:{ IOException -> 0x0025 }
            goto L_0x0029
        L_0x0025:
            r4 = move-exception
            r3.zze(r0, r4)
        L_0x0029:
            goto L_0x002b
        L_0x002a:
            throw r1
        L_0x002b:
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzck.zza(java.net.HttpURLConnection):void");
    }

    private final HttpURLConnection zzb(URL url) throws IOException {
        URLConnection openConnection = url.openConnection();
        if (openConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setConnectTimeout(((Integer) zzby.zzaad.get()).intValue());
            httpURLConnection.setReadTimeout(((Integer) zzby.zzaae.get()).intValue());
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestProperty("User-Agent", this.zzabp);
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        }
        throw new IOException("Failed to obtain http connection");
    }

    private final URL zzd(zzcd zzcd) {
        String str;
        String str2;
        if (zzcd.zzfj()) {
            String valueOf = String.valueOf(zzbq.zzet());
            String valueOf2 = String.valueOf(zzbq.zzev());
            if (valueOf2.length() != 0) {
                str = valueOf.concat(valueOf2);
                return new URL(str);
            }
            str2 = new String(valueOf);
        } else {
            String valueOf3 = String.valueOf(zzbq.zzeu());
            String valueOf4 = String.valueOf(zzbq.zzev());
            if (valueOf4.length() != 0) {
                str = valueOf3.concat(valueOf4);
                return new URL(str);
            }
            str2 = new String(valueOf3);
        }
        str = str2;
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzb(zzcd zzcd, String str) {
        String str2;
        String str3 = "?";
        if (zzcd.zzfj()) {
            String zzet = zzbq.zzet();
            String zzev = zzbq.zzev();
            StringBuilder sb = new StringBuilder(String.valueOf(zzet).length() + 1 + String.valueOf(zzev).length() + String.valueOf(str).length());
            sb.append(zzet);
            sb.append(zzev);
            sb.append(str3);
            sb.append(str);
            str2 = sb.toString();
        } else {
            String zzeu = zzbq.zzeu();
            String zzev2 = zzbq.zzev();
            StringBuilder sb2 = new StringBuilder(String.valueOf(zzeu).length() + 1 + String.valueOf(zzev2).length() + String.valueOf(str).length());
            sb2.append(zzeu);
            sb2.append(zzev2);
            sb2.append(str3);
            sb2.append(str);
            str2 = sb2.toString();
        }
        try {
            return new URL(str2);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzfs() {
        String valueOf = String.valueOf(zzbq.zzet());
        String valueOf2 = String.valueOf((String) zzby.zzzs.get());
        try {
            return new URL(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final String zza(zzcd zzcd, boolean z) {
        String str;
        String str2;
        String str3;
        String str4;
        Preconditions.checkNotNull(zzcd);
        StringBuilder sb = new StringBuilder();
        try {
            Iterator it = zzcd.zzdm().entrySet().iterator();
            while (true) {
                str = "z";
                str2 = "qt";
                str3 = "ht";
                if (!it.hasNext()) {
                    break;
                }
                Entry entry = (Entry) it.next();
                String str5 = (String) entry.getKey();
                if (!str3.equals(str5) && !str2.equals(str5) && !"AppUID".equals(str5) && !str.equals(str5) && !"_gmsv".equals(str5)) {
                    zza(sb, str5, (String) entry.getValue());
                }
            }
            zza(sb, str3, String.valueOf(zzcd.zzfh()));
            zza(sb, str2, String.valueOf(zzcn().currentTimeMillis() - zzcd.zzfh()));
            if (z) {
                long zzfk = zzcd.zzfk();
                if (zzfk != 0) {
                    str4 = String.valueOf(zzfk);
                } else {
                    str4 = String.valueOf(zzcd.zzfg());
                }
                zza(sb, str, str4);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    private static void zza(StringBuilder sb, String str, String str2) throws UnsupportedEncodingException {
        if (sb.length() != 0) {
            sb.append(Typography.amp);
        }
        String str3 = "UTF-8";
        sb.append(URLEncoder.encode(str, str3));
        sb.append(SignatureVisitor.INSTANCEOF);
        sb.append(URLEncoder.encode(str2, str3));
    }
}
