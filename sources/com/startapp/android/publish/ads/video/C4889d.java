package com.startapp.android.publish.ads.video;

import android.content.Context;
import android.util.Base64;
import com.startapp.android.publish.ads.video.C4869c.C4872a;
import com.startapp.android.publish.ads.video.C4920g.C4922a;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.cache.C5091h;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5152e;
import com.startapp.common.p092a.C5155g;
import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* renamed from: com.startapp.android.publish.ads.video.d */
/* compiled from: StartAppSDK */
public class C4889d {

    /* renamed from: a */
    private static C4889d f2910a = new C4889d();

    /* renamed from: b */
    private LinkedList<C5091h> f2911b = new LinkedList<>();

    private C4889d() {
    }

    /* renamed from: a */
    public void mo61765a(Context context, String str, C4922a aVar, C4872a aVar2) {
        C5192a aVar3 = C5192a.HIGH;
        final Context context2 = context;
        final String str2 = str;
        final C4922a aVar4 = aVar;
        final C4872a aVar5 = aVar2;
        C48901 r1 = new Runnable() {
            public void run() {
                C4889d.this.m2733b(context2, str2, aVar4, aVar5);
            }
        };
        C5188g.m3935a(aVar3, (Runnable) r1);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2733b(final Context context, String str, final C4922a aVar, final C4872a aVar2) {
        String str2;
        String str3 = EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR;
        StringBuilder sb = new StringBuilder();
        sb.append("Full cache: ");
        sb.append(str);
        String str4 = "VideoAdCacheManager";
        C5155g.m3807a(str4, 3, sb.toString());
        m2730a(context);
        try {
            URL url = new URL(str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(url.getHost());
            sb2.append(url.getPath().replace("/", str3));
            String sb3 = sb2.toString();
            try {
                String substring = sb3.substring(0, sb3.lastIndexOf(46));
                String substring2 = sb3.substring(sb3.lastIndexOf(46));
                StringBuilder sb4 = new StringBuilder();
                sb4.append(new String(Base64.encodeToString(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(substring.getBytes()), 0)).replaceAll("[^a-zA-Z0-9]+", str3));
                sb4.append(substring2);
                str2 = sb4.toString();
            } catch (NoSuchAlgorithmException e) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Error applying MD5 digest to filename ");
                sb5.append(sb3);
                C5155g.m3808a(str4, 6, sb5.toString(), e);
                str2 = sb3;
            }
            final C5091h hVar = new C5091h(str2);
            C4920g gVar = new C4920g(context, url, str2, new C4922a() {
                /* renamed from: a */
                public void mo61662a(String str) {
                    C4922a aVar = aVar;
                    if (aVar != null) {
                        aVar.mo61662a(str);
                    }
                    if (str != null) {
                        hVar.mo62518a(System.currentTimeMillis());
                        hVar.mo62519a(str);
                        C4889d.this.mo61764a(context, hVar);
                    }
                }
            }, new C4872a() {
                /* renamed from: a */
                public void mo61663a(String str) {
                    C4872a aVar = aVar2;
                    if (aVar != null) {
                        aVar.mo61663a(str);
                    }
                }
            });
            gVar.mo61815a();
        } catch (MalformedURLException e2) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Malformed url ");
            sb6.append(str);
            C5155g.m3808a(str4, 6, sb6.toString(), e2);
            if (aVar != null) {
                aVar.mo61662a(null);
            }
        }
    }

    /* renamed from: a */
    public boolean mo61766a(int i) {
        Iterator it = this.f2911b.iterator();
        boolean z = false;
        while (it.hasNext() && this.f2911b.size() > i) {
            C5091h hVar = (C5091h) it.next();
            if (!C4923h.m2847a(hVar.mo62520b())) {
                z = true;
                it.remove();
                if (hVar.mo62520b() != null) {
                    new File(hVar.mo62520b()).delete();
                    StringBuilder sb = new StringBuilder();
                    sb.append("cachedVideoAds reached the maximum of ");
                    sb.append(i);
                    sb.append(" videos - removed ");
                    sb.append(hVar.mo62517a());
                    sb.append(" Size = ");
                    sb.append(this.f2911b.size());
                    C5155g.m3807a("VideoAdCacheManager", 3, sb.toString());
                }
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61764a(Context context, C5091h hVar) {
        String str = "VideoAdCacheManager";
        if (this.f2911b.contains(hVar)) {
            this.f2911b.remove(hVar);
            StringBuilder sb = new StringBuilder();
            sb.append("cachedVideoAds already contained ");
            sb.append(hVar.mo62517a());
            sb.append(" - removed. Size = ");
            sb.append(this.f2911b.size());
            C5155g.m3807a(str, 3, sb.toString());
        }
        mo61766a(C4983b.m3032a().mo62154H().mo62423b() - 1);
        this.f2911b.add(hVar);
        m2732b(context);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Added ");
        sb2.append(hVar.mo62517a());
        sb2.append(" to cachedVideoAds. Size = ");
        sb2.append(this.f2911b.size());
        C5155g.m3807a(str, 3, sb2.toString());
    }

    /* renamed from: a */
    private void m2730a(Context context) {
        if (this.f2911b == null) {
            this.f2911b = (LinkedList) C5152e.m3786a(context, "CachedAds", LinkedList.class);
            if (this.f2911b == null) {
                this.f2911b = new LinkedList<>();
            }
            if (mo61766a(C4983b.m3032a().mo62154H().mo62423b())) {
                m2732b(context);
            }
        }
    }

    /* renamed from: b */
    private void m2732b(Context context) {
        C5152e.m3799b(context, "CachedAds", (Serializable) this.f2911b);
    }

    /* renamed from: a */
    public static C4889d m2729a() {
        return f2910a;
    }
}
