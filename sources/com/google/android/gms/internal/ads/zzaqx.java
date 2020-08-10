package com.google.android.gms.internal.ads;

import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;
import com.google.android.gms.ads.internal.gmsg.zza;
import com.google.android.gms.ads.internal.gmsg.zzab;
import com.google.android.gms.ads.internal.gmsg.zzac;
import com.google.android.gms.ads.internal.gmsg.zzad;
import com.google.android.gms.ads.internal.gmsg.zzb;
import com.google.android.gms.ads.internal.gmsg.zzd;
import com.google.android.gms.ads.internal.gmsg.zzf;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.ads.internal.gmsg.zzy;
import com.google.android.gms.ads.internal.gmsg.zzz;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzc;
import com.google.android.gms.ads.internal.overlay.zzl;
import com.google.android.gms.ads.internal.overlay.zzn;
import com.google.android.gms.ads.internal.overlay.zzt;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.ads.internal.zzx;
import com.google.android.gms.common.util.Predicate;
import com.google.android.gms.iid.InstanceID;
import com.google.api.client.http.HttpMethods;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.ParametersAreNonnullByDefault;
import org.apache.http.HttpHost;
import org.objectweb.asm.Opcodes;

@zzadh
@ParametersAreNonnullByDefault
public class zzaqx extends WebViewClient implements zzasc {
    private static final String[] zzdbo = {"UNKNOWN", "HOST_LOOKUP", "UNSUPPORTED_AUTH_SCHEME", "AUTHENTICATION", "PROXY_AUTHENTICATION", HttpMethods.CONNECT, "IO", InstanceID.ERROR_TIMEOUT, "REDIRECT_LOOP", "UNSUPPORTED_SCHEME", "FAILED_SSL_HANDSHAKE", "BAD_URL", "FILE", "FILE_NOT_FOUND", "TOO_MANY_REQUESTS"};
    private static final String[] zzdbp = {"NOT_YET_VALID", "EXPIRED", "ID_MISMATCH", "UNTRUSTED", "DATE_INVALID", "INVALID"};
    private final Object mLock;
    private boolean zzaek;
    private zzjd zzapt;
    private zzb zzbll;
    private zzd zzblm;
    private zzz zzbmu;
    private zzx zzbmw;
    private zzaab zzbmx;
    private zzaam zzbmy;
    private zzt zzbnb;
    private zzn zzbnc;
    private zzaqw zzbnd;
    private final HashMap<String, List<zzv<? super zzaqw>>> zzdbq;
    private zzasd zzdbr;
    private zzase zzdbs;
    private zzasf zzdbt;
    private boolean zzdbu;
    private boolean zzdbv;
    private OnGlobalLayoutListener zzdbw;
    private OnScrollChangedListener zzdbx;
    private boolean zzdby;
    private final zzaak zzdbz;
    private zzasg zzdca;
    private boolean zzdcb;
    private boolean zzdcc;
    private int zzdcd;
    private OnAttachStateChangeListener zzdce;
    protected zzait zzxd;

    public zzaqx(zzaqw zzaqw, boolean z) {
        this(zzaqw, z, new zzaak(zzaqw, zzaqw.zzua(), new zzmw(zzaqw.getContext())), null);
    }

    private zzaqx(zzaqw zzaqw, boolean z, zzaak zzaak, zzaab zzaab) {
        this.zzdbq = new HashMap<>();
        this.mLock = new Object();
        this.zzdbu = false;
        this.zzbnd = zzaqw;
        this.zzaek = z;
        this.zzdbz = zzaak;
        this.zzbmx = null;
    }

    /* access modifiers changed from: private */
    public final void zza(View view, zzait zzait, int i) {
        if (zzait.zzph() && i > 0) {
            zzait.zzr(view);
            if (zzait.zzph()) {
                zzakk.zzcrm.postDelayed(new zzaqz(this, view, zzait, i), 100);
            }
        }
    }

    private final void zza(AdOverlayInfoParcel adOverlayInfoParcel) {
        zzaab zzaab = this.zzbmx;
        boolean zznf = zzaab != null ? zzaab.zznf() : false;
        zzbv.zzei();
        zzl.zza(this.zzbnd.getContext(), adOverlayInfoParcel, !zznf);
        if (this.zzxd != null) {
            String str = adOverlayInfoParcel.url;
            if (str == null && adOverlayInfoParcel.zzbyl != null) {
                str = adOverlayInfoParcel.zzbyl.url;
            }
            this.zzxd.zzcf(str);
        }
    }

    private final void zzd(Context context, String str, String str2, String str3) {
        String str4;
        if (((Boolean) zzkb.zzik().zzd(zznk.zzazy)).booleanValue()) {
            Bundle bundle = new Bundle();
            bundle.putString(NotificationCompat.CATEGORY_ERROR, str);
            bundle.putString("code", str2);
            if (!TextUtils.isEmpty(str3)) {
                Uri parse = Uri.parse(str3);
                if (parse.getHost() != null) {
                    str4 = parse.getHost();
                    bundle.putString("host", str4);
                    zzbv.zzek().zza(context, this.zzbnd.zztq().zzcw, "gmob-apps", bundle, true);
                }
            }
            str4 = "";
            bundle.putString("host", str4);
            zzbv.zzek().zza(context, this.zzbnd.zztq().zzcw, "gmob-apps", bundle, true);
        }
    }

    private final WebResourceResponse zze(String str, Map<String, String> map) throws IOException {
        HttpURLConnection httpURLConnection;
        URL url = new URL(str);
        int i = 0;
        while (true) {
            i++;
            if (i <= 20) {
                URLConnection openConnection = url.openConnection();
                openConnection.setConnectTimeout(10000);
                openConnection.setReadTimeout(10000);
                for (Entry entry : map.entrySet()) {
                    openConnection.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                }
                if (openConnection instanceof HttpURLConnection) {
                    httpURLConnection = (HttpURLConnection) openConnection;
                    zzbv.zzek().zza(this.zzbnd.getContext(), this.zzbnd.zztq().zzcw, false, httpURLConnection);
                    zzamy zzamy = new zzamy();
                    zzamy.zza(httpURLConnection, (byte[]) null);
                    int responseCode = httpURLConnection.getResponseCode();
                    zzamy.zza(httpURLConnection, responseCode);
                    if (responseCode < 300 || responseCode >= 400) {
                        zzbv.zzek();
                    } else {
                        String headerField = httpURLConnection.getHeaderField("Location");
                        if (headerField != null) {
                            URL url2 = new URL(url, headerField);
                            String protocol = url2.getProtocol();
                            if (protocol == null) {
                                zzakb.zzdk("Protocol is null");
                                return null;
                            } else if (protocol.equals(HttpHost.DEFAULT_SCHEME_NAME) || protocol.equals("https")) {
                                String str2 = "Redirecting to ";
                                String valueOf = String.valueOf(headerField);
                                zzakb.zzck(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                                httpURLConnection.disconnect();
                                url = url2;
                            } else {
                                String str3 = "Unsupported scheme: ";
                                String valueOf2 = String.valueOf(protocol);
                                zzakb.zzdk(valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
                                return null;
                            }
                        } else {
                            throw new IOException("Missing Location header in redirect");
                        }
                    }
                } else {
                    throw new IOException("Invalid protocol.");
                }
            } else {
                StringBuilder sb = new StringBuilder(32);
                sb.append("Too many redirects (20)");
                throw new IOException(sb.toString());
            }
        }
        zzbv.zzek();
        return zzakk.zzb(httpURLConnection);
    }

    private final void zzi(Uri uri) {
        String path = uri.getPath();
        List<zzv> list = (List) this.zzdbq.get(path);
        if (list != null) {
            zzbv.zzek();
            Map zzg = zzakk.zzg(uri);
            if (zzakb.isLoggable(2)) {
                String str = "Received GMSG: ";
                String valueOf = String.valueOf(path);
                zzakb.m1419v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                for (String str2 : zzg.keySet()) {
                    String str3 = (String) zzg.get(str2);
                    StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 4 + String.valueOf(str3).length());
                    sb.append("  ");
                    sb.append(str2);
                    sb.append(": ");
                    sb.append(str3);
                    zzakb.m1419v(sb.toString());
                }
            }
            for (zzv zza : list) {
                zza.zza(this.zzbnd, zzg);
            }
            return;
        }
        String valueOf2 = String.valueOf(uri);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 32);
        sb2.append("No GMSG handler found for GMSG: ");
        sb2.append(valueOf2);
        zzakb.m1419v(sb2.toString());
    }

    private final void zzuy() {
        if (this.zzdce != null) {
            this.zzbnd.getView().removeOnAttachStateChangeListener(this.zzdce);
        }
    }

    private final void zzvd() {
        if (this.zzdbr != null && ((this.zzdcb && this.zzdcd <= 0) || this.zzdcc)) {
            this.zzdbr.zze(!this.zzdcc);
            this.zzdbr = null;
        }
        this.zzbnd.zzup();
    }

    public final void onLoadResource(WebView webView, String str) {
        String valueOf = String.valueOf(str);
        String str2 = "Loading resource: ";
        zzakb.m1419v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        Uri parse = Uri.parse(str);
        if ("gmsg".equalsIgnoreCase(parse.getScheme())) {
            if ("mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
                zzi(parse);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r1 == null) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        r1.zzly();
        r0.zzdbs = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        zzvd();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
        r0.zzdcb = true;
        r1 = r0.zzdbs;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onPageFinished(android.webkit.WebView r1, java.lang.String r2) {
        /*
            r0 = this;
            java.lang.Object r1 = r0.mLock
            monitor-enter(r1)
            com.google.android.gms.internal.ads.zzaqw r2 = r0.zzbnd     // Catch:{ all -> 0x0029 }
            boolean r2 = r2.isDestroyed()     // Catch:{ all -> 0x0029 }
            if (r2 == 0) goto L_0x0017
            java.lang.String r2 = "Blank page loaded, 1..."
            com.google.android.gms.internal.ads.zzakb.m1419v(r2)     // Catch:{ all -> 0x0029 }
            com.google.android.gms.internal.ads.zzaqw r2 = r0.zzbnd     // Catch:{ all -> 0x0029 }
            r2.zzuk()     // Catch:{ all -> 0x0029 }
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            return
        L_0x0017:
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            r1 = 1
            r0.zzdcb = r1
            com.google.android.gms.internal.ads.zzase r1 = r0.zzdbs
            if (r1 == 0) goto L_0x0025
            r1.zzly()
            r1 = 0
            r0.zzdbs = r1
        L_0x0025:
            r0.zzvd()
            return
        L_0x0029:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaqx.onPageFinished(android.webkit.WebView, java.lang.String):void");
    }

    public final void onReceivedError(WebView webView, int i, String str, String str2) {
        String str3;
        if (i < 0) {
            int i2 = (-i) - 1;
            String[] strArr = zzdbo;
            if (i2 < strArr.length) {
                str3 = strArr[i2];
                zzd(this.zzbnd.getContext(), "http_err", str3, str2);
                super.onReceivedError(webView, i, str, str2);
            }
        }
        str3 = String.valueOf(i);
        zzd(this.zzbnd.getContext(), "http_err", str3, str2);
        super.onReceivedError(webView, i, str, str2);
    }

    public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        String str;
        if (sslError != null) {
            int primaryError = sslError.getPrimaryError();
            if (primaryError >= 0) {
                String[] strArr = zzdbp;
                if (primaryError < strArr.length) {
                    str = strArr[primaryError];
                    zzd(this.zzbnd.getContext(), "ssl_err", str, zzbv.zzem().zza(sslError));
                }
            }
            str = String.valueOf(primaryError);
            zzd(this.zzbnd.getContext(), "ssl_err", str, zzbv.zzem().zza(sslError));
        }
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    public final void reset() {
        zzait zzait = this.zzxd;
        if (zzait != null) {
            zzait.zzpj();
            this.zzxd = null;
        }
        zzuy();
        synchronized (this.mLock) {
            this.zzdbq.clear();
            this.zzapt = null;
            this.zzbnc = null;
            this.zzdbr = null;
            this.zzdbs = null;
            this.zzbll = null;
            this.zzblm = null;
            this.zzdbu = false;
            this.zzaek = false;
            this.zzdbv = false;
            this.zzdby = false;
            this.zzbnb = null;
            this.zzdbt = null;
            if (this.zzbmx != null) {
                this.zzbmx.zzm(true);
                this.zzbmx = null;
            }
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        return zzd(str, Collections.emptyMap());
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (!(keyCode == 79 || keyCode == 222)) {
            switch (keyCode) {
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                    break;
                default:
                    switch (keyCode) {
                        case Opcodes.IAND /*126*/:
                        case Opcodes.LAND /*127*/:
                        case 128:
                        case 129:
                        case 130:
                            break;
                        default:
                            return false;
                    }
            }
        }
        return true;
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        String valueOf = String.valueOf(str);
        String str2 = "AdWebView shouldOverrideUrlLoading: ";
        zzakb.m1419v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        Uri parse = Uri.parse(str);
        if ("gmsg".equalsIgnoreCase(parse.getScheme())) {
            if ("mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
                zzi(parse);
                return true;
            }
        }
        if (this.zzdbu && webView == this.zzbnd.getWebView()) {
            String scheme = parse.getScheme();
            if (HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                if (this.zzapt != null) {
                    if (((Boolean) zzkb.zzik().zzd(zznk.zzaxf)).booleanValue()) {
                        this.zzapt.onAdClicked();
                        zzait zzait = this.zzxd;
                        if (zzait != null) {
                            zzait.zzcf(str);
                        }
                        this.zzapt = null;
                    }
                }
                return super.shouldOverrideUrlLoading(webView, str);
            }
        }
        if (!this.zzbnd.getWebView().willNotDraw()) {
            try {
                zzci zzui = this.zzbnd.zzui();
                if (zzui != null && zzui.zzb(parse)) {
                    parse = zzui.zza(parse, this.zzbnd.getContext(), this.zzbnd.getView(), this.zzbnd.zzto());
                }
            } catch (zzcj unused) {
                String str3 = "Unable to append parameter to URL: ";
                String valueOf2 = String.valueOf(str);
                zzakb.zzdk(valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
            }
            zzx zzx = this.zzbmw;
            if (zzx == null || zzx.zzcy()) {
                zzc zzc = new zzc("android.intent.action.VIEW", parse.toString(), null, null, null, null, null);
                zza(zzc);
            } else {
                this.zzbmw.zzs(str);
            }
        } else {
            String str4 = "AdWebView unable to handle URL: ";
            String valueOf3 = String.valueOf(str);
            zzakb.zzdk(valueOf3.length() != 0 ? str4.concat(valueOf3) : new String(str4));
        }
        return true;
    }

    public final void zza(int i, int i2, boolean z) {
        this.zzdbz.zzc(i, i2);
        zzaab zzaab = this.zzbmx;
        if (zzaab != null) {
            zzaab.zza(i, i2, z);
        }
    }

    public final void zza(OnGlobalLayoutListener onGlobalLayoutListener, OnScrollChangedListener onScrollChangedListener) {
        synchronized (this.mLock) {
            this.zzdbv = true;
            this.zzbnd.zzuo();
            this.zzdbw = onGlobalLayoutListener;
            this.zzdbx = onScrollChangedListener;
        }
    }

    public final void zza(zzc zzc) {
        boolean zzuj = this.zzbnd.zzuj();
        AdOverlayInfoParcel adOverlayInfoParcel = new AdOverlayInfoParcel(zzc, (!zzuj || this.zzbnd.zzud().zzvs()) ? this.zzapt : null, zzuj ? null : this.zzbnc, this.zzbnb, this.zzbnd.zztq());
        zza(adOverlayInfoParcel);
    }

    public final void zza(zzasd zzasd) {
        this.zzdbr = zzasd;
    }

    public final void zza(zzase zzase) {
        this.zzdbs = zzase;
    }

    public final void zza(zzasf zzasf) {
        this.zzdbt = zzasf;
    }

    public final void zza(zzasg zzasg) {
        this.zzdca = zzasg;
    }

    public final void zza(zzjd zzjd, zzb zzb, zzn zzn, zzd zzd, zzt zzt, boolean z, zzz zzz, zzx zzx, zzaam zzaam, zzait zzait) {
        zzb zzb2 = zzb;
        zzd zzd2 = zzd;
        zzz zzz2 = zzz;
        zzaam zzaam2 = zzaam;
        zzait zzait2 = zzait;
        zzx zzx2 = zzx == null ? new zzx(this.zzbnd.getContext(), zzait2, null) : zzx;
        this.zzbmx = new zzaab(this.zzbnd, zzaam2);
        this.zzxd = zzait2;
        if (((Boolean) zzkb.zzik().zzd(zznk.zzayf)).booleanValue()) {
            zza("/adMetadata", (zzv<? super zzaqw>) new zza<Object>(zzb2));
        }
        zza("/appEvent", (zzv<? super zzaqw>) new com.google.android.gms.ads.internal.gmsg.zzc<Object>(zzd2));
        zza("/backButton", zzf.zzblx);
        zza("/refresh", zzf.zzbly);
        zza("/canOpenURLs", zzf.zzblo);
        zza("/canOpenIntents", zzf.zzblp);
        zza("/click", zzf.zzblq);
        zza("/close", zzf.zzblr);
        zza("/customClose", zzf.zzbls);
        zza("/instrument", zzf.zzbmb);
        zza("/delayPageLoaded", zzf.zzbmd);
        zza("/delayPageClosed", zzf.zzbme);
        zza("/getLocationInfo", zzf.zzbmf);
        zza("/httpTrack", zzf.zzblt);
        zza("/log", zzf.zzblu);
        zza("/mraid", (zzv<? super zzaqw>) new zzac<Object>(zzx2, this.zzbmx, zzaam2));
        zza("/mraidLoaded", (zzv<? super zzaqw>) this.zzdbz);
        zzad zzad = r1;
        zzx zzx3 = zzx2;
        zzad zzad2 = new zzad(this.zzbnd.getContext(), this.zzbnd.zztq(), this.zzbnd.zzui(), zzt, zzjd, zzb, zzd, zzn, zzx2, this.zzbmx);
        zza("/open", (zzv<? super zzaqw>) zzad);
        zza("/precache", (zzv<? super zzaqw>) new zzaql<Object>());
        zza("/touch", zzf.zzblw);
        zza("/video", zzf.zzblz);
        zza("/videoMeta", zzf.zzbma);
        if (zzbv.zzfh().zzs(this.zzbnd.getContext())) {
            zza("/logScionEvent", (zzv<? super zzaqw>) new zzab<Object>(this.zzbnd.getContext()));
        }
        if (zzz2 != null) {
            zza("/setInterstitialProperties", (zzv<? super zzaqw>) new zzy<Object>(zzz2));
        }
        this.zzapt = zzjd;
        this.zzbnc = zzn;
        this.zzbll = zzb2;
        this.zzblm = zzd2;
        this.zzbnb = zzt;
        this.zzbmw = zzx3;
        this.zzbmy = zzaam;
        this.zzbmu = zzz2;
        this.zzdbu = z;
    }

    public final void zza(String str, zzv<? super zzaqw> zzv) {
        synchronized (this.mLock) {
            List list = (List) this.zzdbq.get(str);
            if (list == null) {
                list = new CopyOnWriteArrayList();
                this.zzdbq.put(str, list);
            }
            list.add(zzv);
        }
    }

    public final void zza(String str, Predicate<zzv<? super zzaqw>> predicate) {
        synchronized (this.mLock) {
            List<zzv> list = (List) this.zzdbq.get(str);
            if (list != null) {
                ArrayList arrayList = new ArrayList();
                for (zzv zzv : list) {
                    if (predicate.apply(zzv)) {
                        arrayList.add(zzv);
                    }
                }
                list.removeAll(arrayList);
            }
        }
    }

    public final void zza(boolean z, int i) {
        zzjd zzjd = (!this.zzbnd.zzuj() || this.zzbnd.zzud().zzvs()) ? this.zzapt : null;
        zzn zzn = this.zzbnc;
        zzt zzt = this.zzbnb;
        zzaqw zzaqw = this.zzbnd;
        AdOverlayInfoParcel adOverlayInfoParcel = new AdOverlayInfoParcel(zzjd, zzn, zzt, zzaqw, z, i, zzaqw.zztq());
        zza(adOverlayInfoParcel);
    }

    public final void zza(boolean z, int i, String str) {
        boolean zzuj = this.zzbnd.zzuj();
        zzjd zzjd = (!zzuj || this.zzbnd.zzud().zzvs()) ? this.zzapt : null;
        zzn zzarb = zzuj ? null : new zzarb(this.zzbnd, this.zzbnc);
        zzb zzb = this.zzbll;
        zzd zzd = this.zzblm;
        zzt zzt = this.zzbnb;
        zzaqw zzaqw = this.zzbnd;
        AdOverlayInfoParcel adOverlayInfoParcel = new AdOverlayInfoParcel(zzjd, zzarb, zzb, zzd, zzt, zzaqw, z, i, str, zzaqw.zztq());
        zza(adOverlayInfoParcel);
    }

    public final void zza(boolean z, int i, String str, String str2) {
        boolean zzuj = this.zzbnd.zzuj();
        zzjd zzjd = (!zzuj || this.zzbnd.zzud().zzvs()) ? this.zzapt : null;
        zzn zzarb = zzuj ? null : new zzarb(this.zzbnd, this.zzbnc);
        zzb zzb = this.zzbll;
        zzd zzd = this.zzblm;
        zzt zzt = this.zzbnb;
        zzaqw zzaqw = this.zzbnd;
        AdOverlayInfoParcel adOverlayInfoParcel = new AdOverlayInfoParcel(zzjd, zzarb, zzb, zzd, zzt, zzaqw, z, i, str, str2, zzaqw.zztq());
        zza(adOverlayInfoParcel);
    }

    public final void zzah(boolean z) {
        this.zzdbu = z;
    }

    public final void zzb(int i, int i2) {
        zzaab zzaab = this.zzbmx;
        if (zzaab != null) {
            zzaab.zzb(i, i2);
        }
    }

    public final void zzb(String str, zzv<? super zzaqw> zzv) {
        synchronized (this.mLock) {
            List list = (List) this.zzdbq.get(str);
            if (list != null) {
                list.remove(zzv);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final WebResourceResponse zzd(String str, Map<String, String> map) {
        String str2 = "";
        try {
            String zzb = zzajb.zzb(str, this.zzbnd.getContext());
            if (!zzb.equals(str)) {
                return zze(zzb, map);
            }
            zzhl zzaa = zzhl.zzaa(str);
            if (zzaa != null) {
                zzhi zza = zzbv.zzeq().zza(zzaa);
                if (zza != null && zza.zzhi()) {
                    return new WebResourceResponse(str2, str2, zza.zzhj());
                }
            }
            if (zzamy.isEnabled()) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzazn)).booleanValue()) {
                    return zze(str, map);
                }
            }
            return null;
        } catch (Exception | NoClassDefFoundError e) {
            zzbv.zzeo().zza(e, "AdWebViewClient.interceptRequest");
            return null;
        }
    }

    public final boolean zzfz() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzaek;
        }
        return z;
    }

    public final void zznk() {
        synchronized (this.mLock) {
            this.zzdbu = false;
            this.zzaek = true;
            zzaoe.zzcvy.execute(new zzaqy(this));
        }
    }

    public final zzx zzut() {
        return this.zzbmw;
    }

    public final boolean zzuu() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzdbv;
        }
        return z;
    }

    public final OnGlobalLayoutListener zzuv() {
        OnGlobalLayoutListener onGlobalLayoutListener;
        synchronized (this.mLock) {
            onGlobalLayoutListener = this.zzdbw;
        }
        return onGlobalLayoutListener;
    }

    public final OnScrollChangedListener zzuw() {
        OnScrollChangedListener onScrollChangedListener;
        synchronized (this.mLock) {
            onScrollChangedListener = this.zzdbx;
        }
        return onScrollChangedListener;
    }

    public final boolean zzux() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzdby;
        }
        return z;
    }

    public final void zzuz() {
        zzait zzait = this.zzxd;
        if (zzait != null) {
            WebView webView = this.zzbnd.getWebView();
            if (ViewCompat.isAttachedToWindow(webView)) {
                zza((View) webView, zzait, 10);
                return;
            }
            zzuy();
            this.zzdce = new zzara(this, zzait);
            this.zzbnd.getView().addOnAttachStateChangeListener(this.zzdce);
        }
    }

    public final void zzva() {
        synchronized (this.mLock) {
            this.zzdby = true;
        }
        this.zzdcd++;
        zzvd();
    }

    public final void zzvb() {
        this.zzdcd--;
        zzvd();
    }

    public final void zzvc() {
        this.zzdcc = true;
        zzvd();
    }

    public final zzasg zzve() {
        return this.zzdca;
    }

    public final zzait zzvf() {
        return this.zzxd;
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ void zzvg() {
        this.zzbnd.zzuo();
        com.google.android.gms.ads.internal.overlay.zzd zzub = this.zzbnd.zzub();
        if (zzub != null) {
            zzub.zznk();
        }
        zzasf zzasf = this.zzdbt;
        if (zzasf != null) {
            zzasf.zzdb();
            this.zzdbt = null;
        }
    }
}
