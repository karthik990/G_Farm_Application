package com.truenet.android;

import com.startapp.common.p042c.C2362f;
import java.util.ArrayList;
import java.util.List;
import p000a.p001a.p002a.C0007g;
import p000a.p001a.p003b.p005b.C0032h;

/* compiled from: StartAppSDK */
public final class LinksData {
    private final boolean bulkResponse;
    private final long maxRedirectTime;
    private final int numOfRedirect;
    private final long sleep;
    private final int validateParallel;
    @C2362f(mo20786b = ArrayList.class, mo20787c = Link.class)
    private final List<Link> validation;

    public static /* synthetic */ LinksData copy$default(LinksData linksData, long j, int i, boolean z, int i2, long j2, List list, int i3, Object obj) {
        LinksData linksData2 = linksData;
        return linksData.copy((i3 & 1) != 0 ? linksData2.sleep : j, (i3 & 2) != 0 ? linksData2.validateParallel : i, (i3 & 4) != 0 ? linksData2.bulkResponse : z, (i3 & 8) != 0 ? linksData2.numOfRedirect : i2, (i3 & 16) != 0 ? linksData2.maxRedirectTime : j2, (i3 & 32) != 0 ? linksData2.validation : list);
    }

    public final long component1() {
        return this.sleep;
    }

    public final int component2() {
        return this.validateParallel;
    }

    public final boolean component3() {
        return this.bulkResponse;
    }

    public final int component4() {
        return this.numOfRedirect;
    }

    public final long component5() {
        return this.maxRedirectTime;
    }

    public final List<Link> component6() {
        return this.validation;
    }

    public final LinksData copy(long j, int i, boolean z, int i2, long j2, List<Link> list) {
        List<Link> list2 = list;
        C0032h.m44b(list2, "validation");
        LinksData linksData = new LinksData(j, i, z, i2, j2, list2);
        return linksData;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof LinksData) {
                LinksData linksData = (LinksData) obj;
                if (this.sleep == linksData.sleep) {
                    if (this.validateParallel == linksData.validateParallel) {
                        if (this.bulkResponse == linksData.bulkResponse) {
                            if (this.numOfRedirect == linksData.numOfRedirect) {
                                if (!(this.maxRedirectTime == linksData.maxRedirectTime) || !C0032h.m43a((Object) this.validation, (Object) linksData.validation)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        long j = this.sleep;
        int i = ((((int) (j ^ (j >>> 32))) * 31) + this.validateParallel) * 31;
        boolean z = this.bulkResponse;
        if (z) {
            z = true;
        }
        int i2 = (((i + (z ? 1 : 0)) * 31) + this.numOfRedirect) * 31;
        long j2 = this.maxRedirectTime;
        int i3 = (i2 + ((int) (j2 ^ (j2 >>> 32)))) * 31;
        List<Link> list = this.validation;
        return i3 + (list != null ? list.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LinksData(sleep=");
        sb.append(this.sleep);
        sb.append(", validateParallel=");
        sb.append(this.validateParallel);
        sb.append(", bulkResponse=");
        sb.append(this.bulkResponse);
        sb.append(", numOfRedirect=");
        sb.append(this.numOfRedirect);
        sb.append(", maxRedirectTime=");
        sb.append(this.maxRedirectTime);
        sb.append(", validation=");
        sb.append(this.validation);
        sb.append(")");
        return sb.toString();
    }

    public LinksData(long j, int i, boolean z, int i2, long j2, List<Link> list) {
        C0032h.m44b(list, "validation");
        this.sleep = j;
        this.validateParallel = i;
        this.bulkResponse = z;
        this.numOfRedirect = i2;
        this.maxRedirectTime = j2;
        this.validation = list;
    }

    public final long getSleep() {
        return this.sleep;
    }

    public final int getValidateParallel() {
        return this.validateParallel;
    }

    public final boolean getBulkResponse() {
        return this.bulkResponse;
    }

    public final int getNumOfRedirect() {
        return this.numOfRedirect;
    }

    public final long getMaxRedirectTime() {
        return this.maxRedirectTime;
    }

    public final List<Link> getValidation() {
        return this.validation;
    }

    public LinksData() {
        this(0, 0, false, 0, 0, C0007g.m5a());
    }
}
