package com.startapp.android.publish.ads.video.p073c.p074a;

import android.text.TextUtils;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4875a;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4876b;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4877c;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4878d;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4879e;
import com.startapp.android.publish.ads.video.p073c.p076b.C4887a;
import com.startapp.android.publish.adsCommon.C5070p;
import com.startapp.android.publish.omsdk.AdVerification;
import com.startapp.android.publish.omsdk.VerificationDetails;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.startapp.android.publish.ads.video.c.a.e */
/* compiled from: StartAppSDK */
public class C4886e {

    /* renamed from: a */
    private static String f2894a = "VASTModel";

    /* renamed from: b */
    private HashMap<C4880b, List<C4877c>> f2895b;

    /* renamed from: c */
    private List<C4876b> f2896c;

    /* renamed from: d */
    private int f2897d;

    /* renamed from: e */
    private C4879e f2898e;

    /* renamed from: f */
    private List<String> f2899f;

    /* renamed from: g */
    private List<String> f2900g;

    /* renamed from: h */
    private int f2901h;

    /* renamed from: i */
    private C4876b f2902i = null;

    /* renamed from: j */
    private List<C4875a> f2903j;

    /* renamed from: k */
    private AdVerification f2904k;

    public C4886e(Document document) {
        this.f2897d = m2705c(document);
        this.f2895b = m2702a(document);
        this.f2896c = m2704b(document);
        this.f2898e = m2706d(document);
        this.f2899f = m2707e(document);
        this.f2900g = m2708f(document);
        this.f2901h = m2709g(document);
        this.f2903j = m2710h(document);
        this.f2904k = m2711i(document);
    }

    /* renamed from: a */
    public boolean mo61753a(C4881c cVar) {
        this.f2902i = C4887a.m2721a(this, cVar);
        return this.f2902i != null;
    }

    /* renamed from: a */
    public HashMap<C4880b, List<C4877c>> mo61752a() {
        return this.f2895b;
    }

    /* renamed from: b */
    public List<C4876b> mo61754b() {
        return this.f2896c;
    }

    /* renamed from: c */
    public C4879e mo61755c() {
        return this.f2898e;
    }

    /* renamed from: d */
    public List<String> mo61756d() {
        return this.f2899f;
    }

    /* renamed from: e */
    public List<String> mo61757e() {
        return this.f2900g;
    }

    /* renamed from: f */
    public int mo61758f() {
        return this.f2901h;
    }

    /* renamed from: g */
    public C4876b mo61759g() {
        return this.f2902i;
    }

    /* renamed from: h */
    public AdVerification mo61760h() {
        return this.f2904k;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:8|9|10|11|(2:13|(3:15|16|(1:18)(2:19|20)))|21|22|(1:24)(1:25)|26|36) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0072 */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0078 A[Catch:{ Exception -> 0x00b1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007f A[Catch:{ Exception -> 0x00b1 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.HashMap<com.startapp.android.publish.ads.video.p073c.p074a.C4880b, java.util.List<com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4877c>> m2702a(org.w3c.dom.Document r9) {
        /*
            r8 = this;
            java.lang.String r0 = "%"
            java.lang.String r1 = f2894a
            r2 = 3
            java.lang.String r3 = "getTrackingUrls"
            com.startapp.common.p092a.C5155g.m3807a(r1, r2, r3)
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            javax.xml.xpath.XPathFactory r2 = javax.xml.xpath.XPathFactory.newInstance()
            javax.xml.xpath.XPath r2 = r2.newXPath()
            java.lang.String r3 = "/VASTS/VAST/Ad/InLine/Creatives/Creative/Linear/TrackingEvents/Tracking|/VASTS/VAST/Ad/InLine/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking|/VASTS/VAST/Ad/Wrapper/Creatives/Creative/Linear/TrackingEvents/Tracking|/VASTS/VAST/Ad/Wrapper/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking"
            javax.xml.namespace.QName r4 = javax.xml.xpath.XPathConstants.NODESET     // Catch:{ Exception -> 0x00b1 }
            java.lang.Object r9 = r2.evaluate(r3, r9, r4)     // Catch:{ Exception -> 0x00b1 }
            org.w3c.dom.NodeList r9 = (org.w3c.dom.NodeList) r9     // Catch:{ Exception -> 0x00b1 }
            if (r9 == 0) goto L_0x00b0
            r2 = 0
        L_0x0024:
            int r3 = r9.getLength()     // Catch:{ Exception -> 0x00b1 }
            if (r2 >= r3) goto L_0x00b0
            org.w3c.dom.Node r3 = r9.item(r2)     // Catch:{ Exception -> 0x00b1 }
            org.w3c.dom.NamedNodeMap r4 = r3.getAttributes()     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r5 = "event"
            org.w3c.dom.Node r5 = r4.getNamedItem(r5)     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r5 = r5.getNodeValue()     // Catch:{ Exception -> 0x00b1 }
            com.startapp.android.publish.ads.video.c.a.b r5 = com.startapp.android.publish.ads.video.p073c.p074a.C4880b.valueOf(r5)     // Catch:{ IllegalArgumentException -> 0x0090 }
            java.lang.String r3 = com.startapp.android.publish.adsCommon.C5070p.m3484b(r3)     // Catch:{ Exception -> 0x00b1 }
            r6 = -1
            java.lang.String r7 = "offset"
            org.w3c.dom.Node r4 = r4.getNamedItem(r7)     // Catch:{ Exception -> 0x00b1 }
            if (r4 == 0) goto L_0x0072
            java.lang.String r4 = r4.getNodeValue()     // Catch:{ Exception -> 0x00b1 }
            if (r4 == 0) goto L_0x0072
            boolean r7 = r4.contains(r0)     // Catch:{ Exception -> 0x0072 }
            if (r7 == 0) goto L_0x006b
            java.lang.String r7 = ""
            java.lang.String r4 = r4.replace(r0, r7)     // Catch:{ Exception -> 0x0072 }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x0072 }
            int r7 = r8.f2897d     // Catch:{ Exception -> 0x0072 }
            int r7 = r7 / 100
            int r7 = r7 * r4
            r6 = r7
            goto L_0x0072
        L_0x006b:
            int r4 = m2701a(r4)     // Catch:{ Exception -> 0x0072 }
            int r4 = r4 * 1000
            r6 = r4
        L_0x0072:
            boolean r4 = r1.containsKey(r5)     // Catch:{ Exception -> 0x00b1 }
            if (r4 == 0) goto L_0x007f
            java.lang.Object r4 = r1.get(r5)     // Catch:{ Exception -> 0x00b1 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ Exception -> 0x00b1 }
            goto L_0x0087
        L_0x007f:
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x00b1 }
            r4.<init>()     // Catch:{ Exception -> 0x00b1 }
            r1.put(r5, r4)     // Catch:{ Exception -> 0x00b1 }
        L_0x0087:
            com.startapp.android.publish.ads.video.c.a.a.c r5 = new com.startapp.android.publish.ads.video.c.a.a.c     // Catch:{ Exception -> 0x00b1 }
            r5.<init>(r3, r6)     // Catch:{ Exception -> 0x00b1 }
            r4.add(r5)     // Catch:{ Exception -> 0x00b1 }
            goto L_0x00ac
        L_0x0090:
            java.lang.String r3 = f2894a     // Catch:{ Exception -> 0x00b1 }
            r4 = 5
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b1 }
            r6.<init>()     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r7 = "Event:"
            r6.append(r7)     // Catch:{ Exception -> 0x00b1 }
            r6.append(r5)     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r5 = " is not valid. Skipping it."
            r6.append(r5)     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x00b1 }
            com.startapp.common.p092a.C5155g.m3807a(r3, r4, r5)     // Catch:{ Exception -> 0x00b1 }
        L_0x00ac:
            int r2 = r2 + 1
            goto L_0x0024
        L_0x00b0:
            return r1
        L_0x00b1:
            r9 = move-exception
            java.lang.String r0 = f2894a
            r1 = 6
            java.lang.String r2 = r9.getMessage()
            com.startapp.common.p092a.C5155g.m3808a(r0, r1, r2, r9)
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.video.p073c.p074a.C4886e.m2702a(org.w3c.dom.Document):java.util.HashMap");
    }

    /* renamed from: b */
    private List<C4876b> m2704b(Document document) {
        String str;
        Integer num;
        String str2;
        Integer num2;
        Integer num3;
        String str3;
        Boolean bool;
        Boolean bool2;
        String str4;
        C5155g.m3807a(f2894a, 3, "getMediaFiles");
        ArrayList arrayList = new ArrayList();
        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//MediaFile", document, XPathConstants.NODESET);
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    C4876b bVar = new C4876b();
                    Node item = nodeList.item(i);
                    NamedNodeMap attributes = item.getAttributes();
                    Node namedItem = attributes.getNamedItem("apiFramework");
                    if (namedItem == null) {
                        str = null;
                    } else {
                        str = namedItem.getNodeValue();
                    }
                    bVar.mo61731e(str);
                    Node namedItem2 = attributes.getNamedItem("bitrate");
                    if (namedItem2 == null) {
                        num = null;
                    } else {
                        num = Integer.valueOf(namedItem2.getNodeValue());
                    }
                    bVar.mo61719a(num);
                    Node namedItem3 = attributes.getNamedItem("delivery");
                    if (namedItem3 == null) {
                        str2 = null;
                    } else {
                        str2 = namedItem3.getNodeValue();
                    }
                    bVar.mo61727c(str2);
                    Node namedItem4 = attributes.getNamedItem(SettingsJsonConstants.ICON_HEIGHT_KEY);
                    if (namedItem4 == null) {
                        num2 = null;
                    } else {
                        num2 = Integer.valueOf(namedItem4.getNodeValue());
                    }
                    bVar.mo61726c(num2);
                    Node namedItem5 = attributes.getNamedItem(SettingsJsonConstants.ICON_WIDTH_KEY);
                    if (namedItem5 == null) {
                        num3 = null;
                    } else {
                        num3 = Integer.valueOf(namedItem5.getNodeValue());
                    }
                    bVar.mo61723b(num3);
                    Node namedItem6 = attributes.getNamedItem(TtmlNode.ATTR_ID);
                    if (namedItem6 == null) {
                        str3 = null;
                    } else {
                        str3 = namedItem6.getNodeValue();
                    }
                    bVar.mo61724b(str3);
                    Node namedItem7 = attributes.getNamedItem("maintainAspectRatio");
                    if (namedItem7 == null) {
                        bool = null;
                    } else {
                        bool = Boolean.valueOf(namedItem7.getNodeValue());
                    }
                    bVar.mo61722b(bool);
                    Node namedItem8 = attributes.getNamedItem("scalable");
                    if (namedItem8 == null) {
                        bool2 = null;
                    } else {
                        bool2 = Boolean.valueOf(namedItem8.getNodeValue());
                    }
                    bVar.mo61718a(bool2);
                    Node namedItem9 = attributes.getNamedItem("type");
                    if (namedItem9 == null) {
                        str4 = null;
                    } else {
                        str4 = namedItem9.getNodeValue();
                    }
                    bVar.mo61729d(str4);
                    bVar.mo61720a(C5070p.m3484b(item));
                    if (bVar.mo61732f()) {
                        arrayList.add(bVar);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            C5155g.m3808a(f2894a, 6, e.getMessage(), e);
            return null;
        }
    }

    /* renamed from: c */
    private int m2705c(Document document) {
        C5155g.m3807a(f2894a, 3, "getDuration");
        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//Duration", document, XPathConstants.NODESET);
            if (nodeList != null && nodeList.getLength() > 0) {
                return m2701a(C5070p.m3484b(nodeList.item(0)));
            }
        } catch (Exception e) {
            C5155g.m3808a(f2894a, 6, e.getMessage(), e);
        }
        return Integer.MAX_VALUE;
    }

    /* renamed from: d */
    private C4879e m2706d(Document document) {
        C5155g.m3807a(f2894a, 3, "getVideoClicks");
        C4879e eVar = new C4879e();
        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//VideoClicks", document, XPathConstants.NODESET);
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList childNodes = nodeList.item(i).getChildNodes();
                    for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
                        Node item = childNodes.item(i2);
                        String nodeName = item.getNodeName();
                        String b = C5070p.m3484b(item);
                        if (nodeName.equalsIgnoreCase("ClickTracking")) {
                            eVar.mo61741b().add(b);
                        } else if (nodeName.equalsIgnoreCase("ClickThrough")) {
                            eVar.mo61740a(b);
                        } else if (nodeName.equalsIgnoreCase("CustomClick")) {
                            eVar.mo61742c().add(b);
                        }
                    }
                }
            }
            return eVar;
        } catch (Exception e) {
            C5155g.m3808a(f2894a, 6, e.getMessage(), e);
            return null;
        }
    }

    /* renamed from: e */
    private List<String> m2707e(Document document) {
        C5155g.m3807a(f2894a, 3, "getImpressions");
        return m2703a(document, "//Impression");
    }

    /* renamed from: f */
    private List<String> m2708f(Document document) {
        C5155g.m3807a(f2894a, 3, "getErrorUrl");
        return m2703a(document, "//Error");
    }

    /* renamed from: g */
    private int m2709g(Document document) {
        String str = "skipoffset";
        C5155g.m3807a(f2894a, 3, "getSkipOffset");
        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//Linear", document, XPathConstants.NODESET);
            if (nodeList != null) {
                int i = 0;
                while (i < nodeList.getLength()) {
                    try {
                        if (nodeList.item(i).getAttributes().getNamedItem(str) != null) {
                            return m2701a(nodeList.item(i).getAttributes().getNamedItem(str).getNodeValue());
                        }
                        i++;
                    } catch (Exception e) {
                        C5155g.m3808a(f2894a, 6, e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e2) {
            C5155g.m3808a(f2894a, 6, e2.getMessage(), e2);
        }
        return Integer.MAX_VALUE;
    }

    /* renamed from: h */
    private List<C4875a> m2710h(Document document) {
        String str;
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        String str2;
        Integer num7;
        String str3;
        C5155g.m3807a(f2894a, 3, "getIcons");
        ArrayList arrayList = new ArrayList();
        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//Icon", document, XPathConstants.NODESET);
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    C4875a aVar = new C4875a();
                    Node item = nodeList.item(i);
                    NamedNodeMap attributes = item.getAttributes();
                    Node namedItem = attributes.getNamedItem("program");
                    if (namedItem == null) {
                        str = null;
                    } else {
                        str = namedItem.getNodeValue();
                    }
                    aVar.mo61701a(str);
                    Node namedItem2 = attributes.getNamedItem(SettingsJsonConstants.ICON_WIDTH_KEY);
                    if (namedItem2 == null) {
                        num = null;
                    } else {
                        num = Integer.valueOf(namedItem2.getNodeValue());
                    }
                    aVar.mo61700a(num);
                    Node namedItem3 = attributes.getNamedItem(SettingsJsonConstants.ICON_HEIGHT_KEY);
                    if (namedItem3 == null) {
                        num2 = null;
                    } else {
                        num2 = Integer.valueOf(namedItem3.getNodeValue());
                    }
                    aVar.mo61703b(num2);
                    Node namedItem4 = attributes.getNamedItem("xPosition");
                    if (namedItem4 == null) {
                        num3 = null;
                    } else {
                        num3 = Integer.valueOf(namedItem4.getNodeValue());
                    }
                    aVar.mo61706c(num3);
                    Node namedItem5 = attributes.getNamedItem("yPosition");
                    if (namedItem5 == null) {
                        num4 = null;
                    } else {
                        num4 = Integer.valueOf(namedItem5.getNodeValue());
                    }
                    aVar.mo61709d(num4);
                    Node namedItem6 = attributes.getNamedItem("duration");
                    if (namedItem6 == null) {
                        num5 = null;
                    } else {
                        num5 = Integer.valueOf(namedItem6.getNodeValue());
                    }
                    aVar.mo61711e(num5);
                    Node namedItem7 = attributes.getNamedItem("offset");
                    if (namedItem7 == null) {
                        num6 = null;
                    } else {
                        num6 = Integer.valueOf(namedItem7.getNodeValue());
                    }
                    aVar.mo61713f(num6);
                    Node namedItem8 = attributes.getNamedItem("apiFramework");
                    if (namedItem8 == null) {
                        str2 = null;
                    } else {
                        str2 = namedItem8.getNodeValue();
                    }
                    aVar.mo61704b(str2);
                    Node namedItem9 = attributes.getNamedItem("pxratio");
                    if (namedItem9 == null) {
                        num7 = null;
                    } else {
                        num7 = Integer.valueOf(namedItem9.getNodeValue());
                    }
                    aVar.mo61715g(num7);
                    NodeList childNodes = item.getChildNodes();
                    for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
                        Node item2 = childNodes.item(i2);
                        String nodeName = item2.getNodeName();
                        String b = C5070p.m3484b(item2);
                        if (nodeName.equalsIgnoreCase("IconClicks")) {
                            NodeList childNodes2 = item.getChildNodes();
                            for (int i3 = 0; i3 < childNodes2.getLength(); i3++) {
                                Node item3 = childNodes.item(i2);
                                String nodeName2 = item3.getNodeName();
                                String b2 = C5070p.m3484b(item3);
                                if (nodeName2.equalsIgnoreCase("ClickThrough")) {
                                    aVar.mo61707c(b2);
                                } else if (nodeName2.equalsIgnoreCase("IconViewTracking")) {
                                    aVar.mo61714g().add(b2);
                                }
                            }
                        } else if (nodeName.equalsIgnoreCase("ClickTracking")) {
                            aVar.mo61712f().add(b);
                        } else if (nodeName.equalsIgnoreCase("StaticResource")) {
                            C4878d dVar = new C4878d();
                            dVar.mo61738b(b);
                            Node namedItem10 = item.getAttributes().getNamedItem("creativeType");
                            if (namedItem10 == null) {
                                str3 = null;
                            } else {
                                str3 = namedItem10.getNodeValue();
                            }
                            dVar.mo61736a(str3);
                            if (dVar.mo61737a()) {
                                aVar.mo61710e().add(dVar);
                            }
                        }
                    }
                    if (aVar.mo61716h()) {
                        arrayList.add(aVar);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            C5155g.m3808a(f2894a, 6, e.getMessage(), e);
            return null;
        }
    }

    /* renamed from: a */
    private List<String> m2703a(Document document, String str) {
        C5155g.m3807a(f2894a, 3, "getListFromXPath");
        ArrayList arrayList = new ArrayList();
        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate(str, document, XPathConstants.NODESET);
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    arrayList.add(C5070p.m3484b(nodeList.item(i)));
                }
            }
            return arrayList;
        } catch (Exception e) {
            C5155g.m3808a(f2894a, 6, e.getMessage(), e);
            return null;
        }
    }

    /* renamed from: i */
    private AdVerification m2711i(Document document) {
        AdVerification adVerification;
        String str = "vendor";
        try {
            NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//AdVerifications", document, XPathConstants.NODESET);
            if (nodeList != null) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList childNodes = nodeList.item(i).getChildNodes();
                    for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
                        Node item = childNodes.item(i2);
                        if (item.getNodeName().equalsIgnoreCase("Verification")) {
                            String str2 = "";
                            NamedNodeMap attributes = item.getAttributes();
                            String nodeValue = (attributes == null || attributes.getNamedItem(str) == null) ? null : attributes.getNamedItem(str).getNodeValue();
                            NodeList childNodes2 = item.getChildNodes();
                            String str3 = null;
                            String str4 = null;
                            String str5 = str2;
                            for (int i3 = 0; i3 < childNodes2.getLength(); i3++) {
                                Node item2 = childNodes2.item(i3);
                                if (item2.getNodeName().equalsIgnoreCase("JavaScriptResource")) {
                                    Node namedItem = item2.getAttributes().getNamedItem("apiFramework");
                                    if (namedItem != null) {
                                        str5 = namedItem.getNodeValue();
                                    }
                                    str3 = C5070p.m3484b(item2);
                                } else if (item2.getNodeName().equalsIgnoreCase("VerificationParameters")) {
                                    str4 = C5070p.m3484b(item2);
                                }
                            }
                            if (!TextUtils.isEmpty(nodeValue) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4) && "omid".equalsIgnoreCase(str5)) {
                                arrayList.add(new VerificationDetails(nodeValue, str3, str4));
                            }
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    adVerification = new AdVerification((VerificationDetails[]) arrayList.toArray(new VerificationDetails[arrayList.size()]));
                    return adVerification;
                }
            }
            adVerification = null;
            return adVerification;
        } catch (Exception e) {
            C5155g.m3808a(f2894a, 6, e.getMessage(), e);
            return null;
        }
    }

    /* renamed from: a */
    private static int m2701a(String str) {
        String[] split = str.split(":");
        return (Integer.parseInt(split[0]) * 3600) + (Integer.parseInt(split[1]) * 60) + Integer.parseInt(split[2]);
    }
}
