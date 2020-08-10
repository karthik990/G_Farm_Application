package p043io.fabric.sdk.android.services.network;

import java.io.InputStream;

/* renamed from: io.fabric.sdk.android.services.network.PinningInfoProvider */
public interface PinningInfoProvider {
    public static final long PIN_CREATION_TIME_UNDEFINED = -1;

    String getKeyStorePassword();

    InputStream getKeyStoreStream();

    long getPinCreationTimeInMillis();

    String[] getPins();
}
