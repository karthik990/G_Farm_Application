package com.startapp.android.publish.common.model;

import com.startapp.android.publish.adsCommon.BaseResponse;
import com.startapp.android.publish.adsCommon.adinformation.C4978c;
import com.startapp.common.p042c.C2362f;
import java.util.ArrayList;
import java.util.List;

/* compiled from: StartAppSDK */
public class GetAdResponse extends BaseResponse {
    private static final long serialVersionUID = 1;
    @C2362f(mo20785a = true)
    private C4978c adInfoOverrides = C4978c.m3006a();
    @C2362f(mo20786b = ArrayList.class, mo20787c = AdDetails.class)
    private List<AdDetails> adsDetails = new ArrayList();
    private boolean inAppBrowser;
    @C2362f(mo20786b = inAppBrowserPreLoad.class)
    private inAppBrowserPreLoad inAppBrowserPreLoad;
    private String productId;
    private String publisherId;

    /* compiled from: StartAppSDK */
    public enum ResponseType {
        HTML,
        JSON
    }

    /* compiled from: StartAppSDK */
    private enum inAppBrowserPreLoad {
        DISABLED,
        CONTENT,
        FULL
    }

    public String getPublisherId() {
        return this.publisherId;
    }

    public void setPublisherId(String str) {
        this.publisherId = str;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public List<AdDetails> getAdsDetails() {
        return this.adsDetails;
    }

    public void setAdsDetails(List<AdDetails> list) {
        this.adsDetails = list;
    }

    public C4978c getAdInfoOverride() {
        return this.adInfoOverrides;
    }

    public boolean isInAppBrowser() {
        return this.inAppBrowser;
    }

    public void setInAppBrowser(boolean z) {
        this.inAppBrowser = z;
    }

    public inAppBrowserPreLoad getInAppBrowserPreLoad() {
        return this.inAppBrowserPreLoad;
    }

    public void setInAppBrowserPreLoad(inAppBrowserPreLoad inappbrowserpreload) {
        this.inAppBrowserPreLoad = inappbrowserpreload;
    }
}
