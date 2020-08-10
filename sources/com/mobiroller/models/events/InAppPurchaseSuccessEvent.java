package com.mobiroller.models.events;

public class InAppPurchaseSuccessEvent {
    public boolean isFragment;
    public String screenId;
    public String screenType;

    public InAppPurchaseSuccessEvent(String str, String str2, boolean z) {
        this.screenId = str;
        this.screenType = str2;
        this.isFragment = z;
    }
}
