package com.startapp.android.publish.cache;

import com.startapp.android.publish.adsCommon.StartAppAd.AdMode;
import com.startapp.common.p042c.C2362f;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: StartAppSDK */
public class ACMConfig implements Serializable {
    public static final int DEFAULT_CACHE_SIZE = 7;
    private static final long serialVersionUID = 1;
    private long adCacheTTL = 3600;
    @C2362f(mo20786b = EnumSet.class, mo20787c = AdMode.class)
    private Set<AdMode> autoLoad = EnumSet.of(AdMode.FULLPAGE);
    @C2362f(mo20785a = true)
    private FailuresHandler failuresHandler = new FailuresHandler();
    private boolean localCache = true;
    private int maxCacheSize = 7;
    private long returnAdCacheTTL = 3600;
    private boolean returnAdShouldLoadInBg = true;

    public long getAdCacheTtl() {
        return TimeUnit.SECONDS.toMillis(this.adCacheTTL);
    }

    public long getReturnAdCacheTTL() {
        return TimeUnit.SECONDS.toMillis(this.returnAdCacheTTL);
    }

    public Set<AdMode> getAutoLoad() {
        return this.autoLoad;
    }

    public boolean isLocalCache() {
        return this.localCache;
    }

    public boolean shouldReturnAdLoadInBg() {
        return this.returnAdShouldLoadInBg;
    }

    public FailuresHandler getFailuresHandler() {
        return this.failuresHandler;
    }

    public int getMaxCacheSize() {
        return this.maxCacheSize;
    }

    public void setMaxCacheSize(int i) {
        this.maxCacheSize = i;
    }
}
