package com.crashlytics.android.core;

import java.io.InputStream;
import p043io.fabric.sdk.android.services.network.PinningInfoProvider;

class CrashlyticsPinningInfoProvider implements PinningInfoProvider {
    private final PinningInfoProvider pinningInfo;

    public long getPinCreationTimeInMillis() {
        return -1;
    }

    public CrashlyticsPinningInfoProvider(PinningInfoProvider pinningInfoProvider) {
        this.pinningInfo = pinningInfoProvider;
    }

    public InputStream getKeyStoreStream() {
        return this.pinningInfo.getKeyStoreStream();
    }

    public String getKeyStorePassword() {
        return this.pinningInfo.getKeyStorePassword();
    }

    public String[] getPins() {
        return this.pinningInfo.getPins();
    }
}
