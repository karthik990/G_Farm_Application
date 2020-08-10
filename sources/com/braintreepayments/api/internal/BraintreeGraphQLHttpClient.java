package com.braintreepayments.api.internal;

import com.braintreepayments.api.Json;
import com.braintreepayments.api.exceptions.AuthorizationException;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.UnexpectedException;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.GraphQLConstants.ErrorTypes;
import com.braintreepayments.api.internal.GraphQLConstants.Headers;
import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.braintreepayments.api.internal.GraphQLConstants.LegacyErrorCodes;
import java.net.HttpURLConnection;
import javax.net.ssl.SSLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class BraintreeGraphQLHttpClient extends BraintreeApiHttpClient {
    public BraintreeGraphQLHttpClient(String str, String str2) {
        this(str, str2, Headers.API_VERSION);
    }

    private BraintreeGraphQLHttpClient(String str, String str2, String str3) {
        super(str, str2, str3);
        try {
            setSSLSocketFactory(new TLSSocketFactory(BraintreeGraphQLCertificate.getCertInputStream()));
        } catch (SSLException unused) {
            setSSLSocketFactory(null);
        }
    }

    public void post(String str, HttpResponseCallback httpResponseCallback) {
        super.post("", str, httpResponseCallback);
    }

    /* access modifiers changed from: protected */
    public String parseResponse(HttpURLConnection httpURLConnection) throws Exception {
        String parseResponse = super.parseResponse(httpURLConnection);
        JSONArray optJSONArray = new JSONObject(parseResponse).optJSONArray(Keys.ERRORS);
        if (optJSONArray == null) {
            return parseResponse;
        }
        int i = 0;
        while (i < optJSONArray.length()) {
            JSONObject jSONObject = optJSONArray.getJSONObject(i);
            JSONObject optJSONObject = jSONObject.optJSONObject(Keys.EXTENSIONS);
            String str = "message";
            String optString = Json.optString(jSONObject, str, "An Unexpected Exception Occurred");
            if (optJSONObject != null) {
                String str2 = "";
                if (Json.optString(optJSONObject, Keys.LEGACY_CODE, str2).equals(LegacyErrorCodes.VALIDATION_NOT_ALLOWED)) {
                    throw new AuthorizationException(jSONObject.getString(str));
                } else if (Json.optString(optJSONObject, Keys.ERROR_TYPE, str2).equals(ErrorTypes.USER)) {
                    i++;
                } else {
                    throw new UnexpectedException(optString);
                }
            } else {
                throw new UnexpectedException(optString);
            }
        }
        throw ErrorWithResponse.fromGraphQLJson(parseResponse);
    }
}
