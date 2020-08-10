package com.braintreepayments.api.internal;

import android.net.Uri;
import com.braintreepayments.api.exceptions.AuthorizationException;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.UnprocessableEntityException;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.models.Authorization;
import com.braintreepayments.api.models.ClientToken;
import com.braintreepayments.api.models.TokenizationKey;
import java.io.IOException;
import java.net.HttpURLConnection;
import javax.net.ssl.SSLException;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

public class BraintreeHttpClient extends HttpClient {
    private static final String AUTHORIZATION_FINGERPRINT_KEY = "authorizationFingerprint";
    private static final String TOKENIZATION_KEY_HEADER_KEY = "Client-Key";
    private final Authorization mAuthorization;

    public static String getUserAgent() {
        return "braintree/android/2.21.0";
    }

    public BraintreeHttpClient(Authorization authorization) {
        this.mAuthorization = authorization;
        setUserAgent(getUserAgent());
        try {
            setSSLSocketFactory(new TLSSocketFactory(BraintreeGatewayCertificate.getCertInputStream()));
        } catch (SSLException unused) {
            setSSLSocketFactory(null);
        }
    }

    public void get(String str, HttpResponseCallback httpResponseCallback) {
        Uri uri;
        if (str == null) {
            postCallbackOnMainThread(httpResponseCallback, (Exception) new IllegalArgumentException("Path cannot be null"));
            return;
        }
        if (str.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            uri = Uri.parse(str);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mBaseUrl);
            sb.append(str);
            uri = Uri.parse(sb.toString());
        }
        if (this.mAuthorization instanceof ClientToken) {
            uri = uri.buildUpon().appendQueryParameter(AUTHORIZATION_FINGERPRINT_KEY, ((ClientToken) this.mAuthorization).getAuthorizationFingerprint()).build();
        }
        super.get(uri.toString(), httpResponseCallback);
    }

    public void post(String str, String str2, HttpResponseCallback httpResponseCallback) {
        try {
            if (this.mAuthorization instanceof ClientToken) {
                str2 = new JSONObject(str2).put(AUTHORIZATION_FINGERPRINT_KEY, ((ClientToken) this.mAuthorization).getAuthorizationFingerprint()).toString();
            }
            super.post(str, str2, httpResponseCallback);
        } catch (JSONException e) {
            postCallbackOnMainThread(httpResponseCallback, (Exception) e);
        }
    }

    public String post(String str, String str2) throws Exception {
        if (this.mAuthorization instanceof ClientToken) {
            str2 = new JSONObject(str2).put(AUTHORIZATION_FINGERPRINT_KEY, ((ClientToken) this.mAuthorization).getAuthorizationFingerprint()).toString();
        }
        return super.post(str, str2);
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection init(String str) throws IOException {
        HttpURLConnection init = super.init(str);
        Authorization authorization = this.mAuthorization;
        if (authorization instanceof TokenizationKey) {
            init.setRequestProperty(TOKENIZATION_KEY_HEADER_KEY, authorization.getBearer());
        }
        return init;
    }

    /* access modifiers changed from: protected */
    public String parseResponse(HttpURLConnection httpURLConnection) throws Exception {
        try {
            return super.parseResponse(httpURLConnection);
        } catch (AuthorizationException | UnprocessableEntityException e) {
            if (e instanceof AuthorizationException) {
                throw new AuthorizationException(new ErrorWithResponse(403, e.getMessage()).getMessage());
            }
            throw new ErrorWithResponse(422, e.getMessage());
        }
    }
}
