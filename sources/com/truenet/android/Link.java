package com.truenet.android;

import com.startapp.android.publish.common.metaData.MetaData;
import p000a.p001a.p003b.p005b.C0032h;

/* compiled from: StartAppSDK */
public final class Link {
    private final String htmlStorage;
    private final String imageStorage;
    private final String instanceId;
    private final String metaData;
    private final String validationUrl;

    public static /* synthetic */ Link copy$default(Link link, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = link.instanceId;
        }
        if ((i & 2) != 0) {
            str2 = link.validationUrl;
        }
        String str6 = str2;
        if ((i & 4) != 0) {
            str3 = link.imageStorage;
        }
        String str7 = str3;
        if ((i & 8) != 0) {
            str4 = link.htmlStorage;
        }
        String str8 = str4;
        if ((i & 16) != 0) {
            str5 = link.metaData;
        }
        return link.copy(str, str6, str7, str8, str5);
    }

    public final String component1() {
        return this.instanceId;
    }

    public final String component2() {
        return this.validationUrl;
    }

    public final String component3() {
        return this.imageStorage;
    }

    public final String component4() {
        return this.htmlStorage;
    }

    public final String component5() {
        return this.metaData;
    }

    public final Link copy(String str, String str2, String str3, String str4, String str5) {
        C0032h.m44b(str, "instanceId");
        C0032h.m44b(str2, "validationUrl");
        C0032h.m44b(str3, "imageStorage");
        C0032h.m44b(str4, "htmlStorage");
        C0032h.m44b(str5, MetaData.KEY_METADATA);
        Link link = new Link(str, str2, str3, str4, str5);
        return link;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0038, code lost:
        if (p000a.p001a.p003b.p005b.C0032h.m43a((java.lang.Object) r2.metaData, (java.lang.Object) r3.metaData) != false) goto L_0x003d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r2 == r3) goto L_0x003d
            boolean r0 = r3 instanceof com.truenet.android.Link
            if (r0 == 0) goto L_0x003b
            com.truenet.android.Link r3 = (com.truenet.android.Link) r3
            java.lang.String r0 = r2.instanceId
            java.lang.String r1 = r3.instanceId
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x003b
            java.lang.String r0 = r2.validationUrl
            java.lang.String r1 = r3.validationUrl
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x003b
            java.lang.String r0 = r2.imageStorage
            java.lang.String r1 = r3.imageStorage
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x003b
            java.lang.String r0 = r2.htmlStorage
            java.lang.String r1 = r3.htmlStorage
            boolean r0 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r1)
            if (r0 == 0) goto L_0x003b
            java.lang.String r0 = r2.metaData
            java.lang.String r3 = r3.metaData
            boolean r3 = p000a.p001a.p003b.p005b.C0032h.m43a(r0, r3)
            if (r3 == 0) goto L_0x003b
            goto L_0x003d
        L_0x003b:
            r3 = 0
            return r3
        L_0x003d:
            r3 = 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.Link.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        String str = this.instanceId;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.validationUrl;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.imageStorage;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.htmlStorage;
        int hashCode4 = (hashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.metaData;
        if (str5 != null) {
            i = str5.hashCode();
        }
        return hashCode4 + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Link(instanceId=");
        sb.append(this.instanceId);
        sb.append(", validationUrl=");
        sb.append(this.validationUrl);
        sb.append(", imageStorage=");
        sb.append(this.imageStorage);
        sb.append(", htmlStorage=");
        sb.append(this.htmlStorage);
        sb.append(", metaData=");
        sb.append(this.metaData);
        sb.append(")");
        return sb.toString();
    }

    public Link(String str, String str2, String str3, String str4, String str5) {
        C0032h.m44b(str, "instanceId");
        C0032h.m44b(str2, "validationUrl");
        C0032h.m44b(str3, "imageStorage");
        C0032h.m44b(str4, "htmlStorage");
        C0032h.m44b(str5, MetaData.KEY_METADATA);
        this.instanceId = str;
        this.validationUrl = str2;
        this.imageStorage = str3;
        this.htmlStorage = str4;
        this.metaData = str5;
    }

    public final String getInstanceId() {
        return this.instanceId;
    }

    public final String getValidationUrl() {
        return this.validationUrl;
    }

    public final String getImageStorage() {
        return this.imageStorage;
    }

    public final String getHtmlStorage() {
        return this.htmlStorage;
    }

    public final String getMetaData() {
        return this.metaData;
    }

    public Link() {
        this("", "", "", "", "");
    }
}
