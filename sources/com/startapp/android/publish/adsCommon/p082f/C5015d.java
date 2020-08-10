package com.startapp.android.publish.adsCommon.p082f;

/* renamed from: com.startapp.android.publish.adsCommon.f.d */
/* compiled from: StartAppSDK */
public enum C5015d {
    FAILED_SMART_REDIRECT("failed_smart_redirect"),
    EXCEPTION("exception"),
    PERIODIC("periodic"),
    GENERAL("general"),
    WRONG_PACKAGE_REACHED("wrong_package_reached"),
    VIDEO_MEDIA_PLAYER_ERROR("video_media_player_error"),
    FAILED_EXTRACTING_DPARAMS("failed_extracting_dparams"),
    FAILED_SMART_REDIRECT_HOP_INFO("failed_smart_redirect_hop_info"),
    SUCCESS_SMART_REDIRECT_HOP_INFO("success_smart_redirect_hop_info"),
    USER_CONSENT("user_consent"),
    METADATA_NULL("metadata_object_is_null"),
    NON_IMPRESSION_NO_DPARAM("non_impression_without_dparams");
    
    private String value;

    private C5015d(String str) {
        this.value = str;
    }

    /* renamed from: a */
    public String mo62283a() {
        return this.value;
    }
}
