package com.flurry.android;

public interface FlurryConfigListener {
    void onActivateComplete(boolean z);

    void onFetchError(boolean z);

    void onFetchNoChange();

    void onFetchSuccess();
}
