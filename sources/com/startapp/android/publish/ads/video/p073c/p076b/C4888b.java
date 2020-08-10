package com.startapp.android.publish.ads.video.p073c.p076b;

import android.content.Context;
import com.startapp.android.publish.ads.video.p073c.p074a.C4874a;
import com.startapp.android.publish.ads.video.p073c.p074a.C4881c;
import com.startapp.android.publish.ads.video.p073c.p074a.C4886e;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.C5070p;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5156h;
import com.startapp.common.p092a.C5156h.C5157a;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/* renamed from: com.startapp.android.publish.ads.video.c.b.b */
/* compiled from: StartAppSDK */
public final class C4888b {

    /* renamed from: a */
    private final int f2905a;

    /* renamed from: b */
    private final int f2906b;

    /* renamed from: c */
    private C4886e f2907c;

    /* renamed from: d */
    private StringBuilder f2908d = new StringBuilder(500);

    /* renamed from: e */
    private long f2909e = -1;

    public C4888b(int i, int i2) {
        this.f2905a = i;
        this.f2906b = i2;
    }

    /* renamed from: a */
    public C4886e mo61763a() {
        return this.f2907c;
    }

    /* renamed from: a */
    public C4874a mo61762a(Context context, String str, C4881c cVar) {
        this.f2907c = null;
        this.f2909e = System.currentTimeMillis();
        C4874a a = mo61761a(context, str, 0);
        String str2 = "VASTProcessor";
        if (a == C4874a.XMLParsingError) {
            StringBuilder sb = new StringBuilder();
            sb.append("processXml error ");
            sb.append(a);
            C5155g.m3807a(str2, 3, sb.toString());
            return C4874a.XMLParsingError;
        }
        Document a2 = m2724a(this.f2908d.toString());
        if (a2 == null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("wrapMergedVastDocWithVasts error ");
            sb2.append(a);
            C5155g.m3807a(str2, 3, sb2.toString());
            return C4874a.XMLParsingError;
        }
        this.f2907c = new C4886e(a2);
        if (!this.f2907c.mo61753a(cVar)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("validate error ");
            sb3.append(a);
            C5155g.m3807a(str2, 3, sb3.toString());
            if (a == C4874a.ErrorNone) {
                a = C4874a.MediaNotSupported;
            }
        }
        return a;
    }

    /* renamed from: a */
    public C4874a mo61761a(Context context, String str, int i) {
        String str2 = "VASTProcessor";
        if (i >= this.f2905a) {
            StringBuilder sb = new StringBuilder();
            sb.append("VAST wrapping exceeded max limit of ");
            sb.append(this.f2905a);
            C5155g.m3807a(str2, 6, sb.toString());
            return C4874a.WrapperLimitReached;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.f2909e;
        long j2 = currentTimeMillis - j;
        if (j2 <= ((long) this.f2906b) || j <= 0) {
            Document b = m2725b(str);
            if (b == null) {
                return C4874a.XMLParsingError;
            }
            String a = m2723a(b);
            if (b.getChildNodes().getLength() == 0 || b.getChildNodes().item(0).getChildNodes().getLength() == 0 || a == null) {
                return C4874a.WrapperNoReponse;
            }
            this.f2908d.append(a);
            NodeList elementsByTagName = b.getElementsByTagName("VASTAdTagURI");
            if (elementsByTagName == null || elementsByTagName.getLength() == 0) {
                return C4874a.ErrorNone;
            }
            String b2 = C5070p.m3484b(elementsByTagName.item(0));
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Wrapper URL: ");
            sb2.append(b2);
            C5155g.m3807a(str2, 3, sb2.toString());
            try {
                C5157a a2 = C5156h.m3810a(context, b2.replace(" ", "%20"), null, C5051k.m3339a(context, "User-Agent", "-1"), false);
                if (a2 == null || a2.mo62865a() == null) {
                    return C4874a.WrapperNoReponse;
                }
                return mo61761a(context, a2.mo62865a(), i + 1);
            } catch (Exception e) {
                C5155g.m3808a(str2, 6, "processXml network", e);
                return C4874a.GeneralWrapperError;
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("VAST wrapping exceeded timeout ");
            sb3.append(j2);
            C5155g.m3807a(str2, 6, sb3.toString());
            return C4874a.WrapperTimeout;
        }
    }

    /* renamed from: a */
    public static Document m2724a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<VASTS>");
        sb.append(str);
        sb.append("</VASTS>");
        return C5070p.m3483a(sb.toString());
    }

    /* renamed from: b */
    public static Document m2725b(String str) {
        Document a = C5070p.m3483a(str);
        if (a != null) {
            a.getDocumentElement().normalize();
        }
        return a;
    }

    /* renamed from: a */
    public static String m2723a(Document document) {
        if (document != null) {
            NodeList elementsByTagName = document.getElementsByTagName("VAST");
            if (elementsByTagName != null && elementsByTagName.getLength() > 0) {
                return C5070p.m3482a(elementsByTagName.item(0));
            }
        }
        return null;
    }
}
