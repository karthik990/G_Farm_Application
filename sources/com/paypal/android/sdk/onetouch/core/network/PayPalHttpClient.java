package com.paypal.android.sdk.onetouch.core.network;

import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.internal.TLSSocketFactory;
import com.paypal.android.sdk.onetouch.core.base.DeviceInspector;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;

public class PayPalHttpClient extends HttpClient<PayPalHttpClient> {
    public PayPalHttpClient() {
        setUserAgent(String.format("PayPalSDK/PayPalOneTouch-Android %s (%s; %s; %s)", new Object[]{"2.21.0", DeviceInspector.getOs(), DeviceInspector.getDeviceName(), ""}));
        setConnectTimeout((int) TimeUnit.SECONDS.toMillis(90));
        try {
            setSSLSocketFactory(new TLSSocketFactory(PayPalCertificate.getCertInputStream()));
        } catch (SSLException unused) {
            setSSLSocketFactory(null);
        }
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection init(String str) throws IOException {
        return super.init(str);
    }
}
