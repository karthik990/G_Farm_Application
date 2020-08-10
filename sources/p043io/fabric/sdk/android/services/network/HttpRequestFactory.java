package p043io.fabric.sdk.android.services.network;

import java.util.Map;

/* renamed from: io.fabric.sdk.android.services.network.HttpRequestFactory */
public interface HttpRequestFactory {
    HttpRequest buildHttpRequest(HttpMethod httpMethod, String str);

    HttpRequest buildHttpRequest(HttpMethod httpMethod, String str, Map<String, String> map);

    PinningInfoProvider getPinningInfoProvider();

    void setPinningInfoProvider(PinningInfoProvider pinningInfoProvider);
}
