package com.mobiroller.models.youtube;

import com.mobiroller.models.ScreenModel;

public class YoutubeNoNetworkEvent {
    public String screenId;
    public ScreenModel screenModel;

    public YoutubeNoNetworkEvent(ScreenModel screenModel2, String str) {
        this.screenModel = screenModel2;
        this.screenId = str;
    }
}
