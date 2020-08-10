package com.braintreepayments.api.internal;

import android.text.TextUtils;
import com.braintreepayments.api.exceptions.BraintreeApiErrorResponse;
import com.braintreepayments.api.exceptions.UnprocessableEntityException;
import java.io.IOException;
import java.net.HttpURLConnection;
import javax.net.ssl.SSLException;

public class BraintreeApiHttpClient extends HttpClient {
    public static final String API_VERSION_2016_10_07 = "2016-10-07";
    private final String mApiVersion;
    private final String mBearer;

    public BraintreeApiHttpClient(String str, String str2) {
        this(str, str2, API_VERSION_2016_10_07);
    }

    public BraintreeApiHttpClient(String str, String str2, String str3) {
        this.mBaseUrl = str;
        this.mBearer = str2;
        this.mApiVersion = str3;
        setUserAgent("braintree/android/2.21.0");
        try {
            setSSLSocketFactory(new TLSSocketFactory(BraintreeApiCertificate.getCertInputStream()));
        } catch (SSLException unused) {
            setSSLSocketFactory(null);
        }
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection init(String str) throws IOException {
        HttpURLConnection init = super.init(str);
        if (!TextUtils.isEmpty(this.mBearer)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bearer ");
            sb.append(this.mBearer);
            init.setRequestProperty("Authorization", sb.toString());
        }
        init.setRequestProperty("Braintree-Version", this.mApiVersion);
        return init;
    }

    /* access modifiers changed from: protected */
    public String parseResponse(HttpURLConnection httpURLConnection) throws Exception {
        try {
            return super.parseResponse(httpURLConnection);
        } catch (UnprocessableEntityException e) {
            throw new BraintreeApiErrorResponse(e.getMessage());
        }
    }
}
