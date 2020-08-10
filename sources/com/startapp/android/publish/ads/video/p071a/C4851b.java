package com.startapp.android.publish.ads.video.p071a;

import android.text.TextUtils;
import com.startapp.android.publish.ads.video.p073c.p074a.C4874a;
import com.startapp.android.publish.ads.video.tracking.VideoTrackingLink;
import com.startapp.android.publish.ads.video.tracking.VideoTrackingLink.TrackingSource;
import com.startapp.android.publish.ads.video.tracking.VideoTrackingParams;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.common.p092a.C5138a;
import com.startapp.common.p092a.C5155g;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.ads.video.a.b */
/* compiled from: StartAppSDK */
public class C4851b {

    /* renamed from: a */
    private VideoTrackingLink[] f2742a;

    /* renamed from: b */
    private VideoTrackingParams f2743b;

    /* renamed from: c */
    private String f2744c;

    /* renamed from: d */
    private int f2745d;

    /* renamed from: e */
    private String f2746e = "";

    /* renamed from: f */
    private C4874a f2747f;

    public C4851b(VideoTrackingLink[] videoTrackingLinkArr, VideoTrackingParams videoTrackingParams, String str, int i) {
        this.f2742a = videoTrackingLinkArr;
        this.f2743b = videoTrackingParams;
        this.f2744c = str;
        this.f2745d = i;
    }

    /* renamed from: a */
    public C4851b mo61657a(C4874a aVar) {
        this.f2747f = aVar;
        return this;
    }

    /* renamed from: a */
    public C4851b mo61658a(String str) {
        this.f2746e = str;
        return this;
    }

    /* renamed from: a */
    public C4850a mo61656a() {
        VideoTrackingLink[] videoTrackingLinkArr;
        String str = "VideoEventBuilder";
        if (!m2557b()) {
            C5155g.m3807a(str, 3, "Some fields have illegal values");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (VideoTrackingLink videoTrackingLink : this.f2742a) {
            if (videoTrackingLink.getTrackingUrl() == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Ignoring tracking link - tracking url is null: tracking link = ");
                sb.append(videoTrackingLink);
                C5155g.m3807a(str, 5, sb.toString());
            } else if (this.f2743b.getOffset() <= 0 || videoTrackingLink.shouldAppendReplay()) {
                arrayList.add(m2554a(videoTrackingLink));
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Ignoring tracking link - external replay event: tracking link = ");
                sb2.append(videoTrackingLink);
                C5155g.m3807a(str, 3, sb2.toString());
            }
        }
        return new C4850a(arrayList, this.f2746e);
    }

    /* renamed from: b */
    private boolean m2557b() {
        return (this.f2742a == null || this.f2743b == null) ? false : true;
    }

    /* renamed from: a */
    private String m2554a(VideoTrackingLink videoTrackingLink) {
        StringBuilder sb = new StringBuilder();
        VideoTrackingParams b = m2555b(videoTrackingLink);
        String trackingUrl = videoTrackingLink.getTrackingUrl();
        sb.append(m2556b(trackingUrl));
        sb.append(b.getQueryString());
        if (b.getInternalTrackingParamsIndicator()) {
            sb.append(C5138a.m3711a(C4988c.m3133e(trackingUrl)));
        }
        return sb.toString();
    }

    /* renamed from: b */
    private VideoTrackingParams m2555b(VideoTrackingLink videoTrackingLink) {
        TrackingSource trackingSource = videoTrackingLink.getTrackingSource();
        return this.f2743b.setInternalTrackingParamsIndicator(trackingSource != null && trackingSource == TrackingSource.STARTAPP).setShouldAppendOffset(videoTrackingLink.shouldAppendReplay()).setReplayParameter(videoTrackingLink.getReplayParameter());
    }

    /* renamed from: b */
    private String m2556b(String str) {
        String str2 = this.f2744c;
        String str3 = "[TIMESTAMP]";
        String replace = str.replace("[ASSETURI]", str2 != null ? TextUtils.htmlEncode(str2) : "").replace("[CONTENTPLAYHEAD]", TextUtils.htmlEncode(m2553a(this.f2745d))).replace("[CACHEBUSTING]", TextUtils.htmlEncode(m2558c())).replace(str3, TextUtils.htmlEncode(m2559d()));
        C4874a aVar = this.f2747f;
        if (aVar == null) {
            return replace;
        }
        return replace.replace("[ERRORCODE]", String.valueOf(aVar.mo61698a()));
    }

    /* renamed from: c */
    private static String m2558c() {
        return String.valueOf(new SecureRandom().nextInt(90000000) + 10000000);
    }

    /* renamed from: a */
    private static String m2553a(int i) {
        long convert = TimeUnit.SECONDS.convert((long) i, TimeUnit.MILLISECONDS);
        long j = convert % 60;
        long j2 = convert / 3600;
        long j3 = (convert % 3600) / 60;
        long j4 = (long) (i % 1000);
        return String.format(Locale.US, "%02d:%02d:%02d.%03d", new Object[]{Long.valueOf(j2), Long.valueOf(j3), Long.valueOf(j), Long.valueOf(j4)});
    }

    /* renamed from: d */
    private static String m2559d() {
        String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format(new Date());
        int length = format.length() - 2;
        StringBuilder sb = new StringBuilder();
        sb.append(format.substring(0, length));
        sb.append(":");
        sb.append(format.substring(length));
        return sb.toString();
    }
}
