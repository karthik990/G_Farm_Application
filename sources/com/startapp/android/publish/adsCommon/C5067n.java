package com.startapp.android.publish.adsCommon;

import com.startapp.common.p042c.C2362f;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.config.CookieSpecs;

/* renamed from: com.startapp.android.publish.adsCommon.n */
/* compiled from: StartAppSDK */
public class C5067n implements Serializable {
    private static final long serialVersionUID = 1;
    @C2362f(mo20786b = C5068a.class)
    private C5068a backMode = C5068a.DISABLED;
    private int maxCachedVideos = 3;
    private int maxTimeForCachingIndicator = 10;
    private int maxVastCampaignExclude = 10;
    private int maxVastLevels = 7;
    private long minAvailableStorageRequired = 20;
    private int minTimeForCachingIndicator = 1;
    private int nativePlayerProbability = 100;
    private boolean progressive = false;
    private int progressiveBufferInPercentage = 20;
    private int progressiveInitialBufferInPercentage = 5;
    private int progressiveMinBufferToPlayFromCache = 30;
    private int rewardGrantPercentage = 100;
    private String soundMode = CookieSpecs.DEFAULT;
    private int vastMediaPicker = 0;
    private int vastPreferredBitrate = 0;
    private int vastRetryTimeout = 60000;
    private int vastTimeout = 30000;
    private int videoErrorsThreshold = 2;
    private boolean videoFallback = false;

    /* renamed from: com.startapp.android.publish.adsCommon.n$a */
    /* compiled from: StartAppSDK */
    public enum C5068a {
        DISABLED,
        SKIP,
        CLOSE,
        BOTH
    }

    /* renamed from: a */
    public C5068a mo62422a() {
        return this.backMode;
    }

    /* renamed from: b */
    public int mo62423b() {
        return this.maxCachedVideos;
    }

    /* renamed from: c */
    public long mo62424c() {
        return this.minAvailableStorageRequired;
    }

    /* renamed from: d */
    public int mo62425d() {
        return this.rewardGrantPercentage;
    }

    /* renamed from: e */
    public int mo62426e() {
        return this.videoErrorsThreshold;
    }

    /* renamed from: f */
    public long mo62427f() {
        return TimeUnit.SECONDS.toMillis((long) this.minTimeForCachingIndicator);
    }

    /* renamed from: g */
    public long mo62428g() {
        return TimeUnit.SECONDS.toMillis((long) this.maxTimeForCachingIndicator);
    }

    /* renamed from: h */
    public boolean mo62429h() {
        return this.videoFallback;
    }

    /* renamed from: i */
    public boolean mo62430i() {
        return this.progressive;
    }

    /* renamed from: j */
    public int mo62431j() {
        return this.progressiveBufferInPercentage;
    }

    /* renamed from: k */
    public int mo62432k() {
        return this.progressiveInitialBufferInPercentage;
    }

    /* renamed from: l */
    public int mo62433l() {
        return this.progressiveMinBufferToPlayFromCache;
    }

    /* renamed from: m */
    public String mo62434m() {
        return this.soundMode;
    }

    /* renamed from: n */
    public int mo62435n() {
        return this.maxVastLevels;
    }

    /* renamed from: o */
    public int mo62436o() {
        return this.vastTimeout;
    }

    /* renamed from: p */
    public int mo62437p() {
        return this.vastRetryTimeout;
    }

    /* renamed from: q */
    public int mo62438q() {
        return this.maxVastCampaignExclude;
    }

    /* renamed from: r */
    public int mo62439r() {
        return this.vastMediaPicker;
    }

    /* renamed from: s */
    public int mo62440s() {
        return this.vastPreferredBitrate;
    }
}
