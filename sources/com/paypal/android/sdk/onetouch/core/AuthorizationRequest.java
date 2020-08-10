package com.paypal.android.sdk.onetouch.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Base64;
import com.braintreepayments.api.Json;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.paypal.android.sdk.onetouch.core.base.ContextInspector;
import com.paypal.android.sdk.onetouch.core.base.DeviceInspector;
import com.paypal.android.sdk.onetouch.core.config.ConfigEndpoint;
import com.paypal.android.sdk.onetouch.core.config.OAuth2Recipe;
import com.paypal.android.sdk.onetouch.core.config.OtcConfiguration;
import com.paypal.android.sdk.onetouch.core.config.Recipe;
import com.paypal.android.sdk.onetouch.core.encryption.EncryptionUtils;
import com.paypal.android.sdk.onetouch.core.encryption.OtcCrypto;
import com.paypal.android.sdk.onetouch.core.enums.Protocol;
import com.paypal.android.sdk.onetouch.core.enums.RequestTarget;
import com.paypal.android.sdk.onetouch.core.enums.ResponseType;
import com.paypal.android.sdk.onetouch.core.exception.BrowserSwitchException;
import com.paypal.android.sdk.onetouch.core.exception.InvalidEncryptionDataException;
import com.paypal.android.sdk.onetouch.core.exception.ResponseParsingException;
import com.paypal.android.sdk.onetouch.core.fpti.TrackingPoint;
import com.paypal.android.sdk.onetouch.core.network.EnvironmentManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class AuthorizationRequest extends Request<AuthorizationRequest> implements Parcelable {
    public static final Creator<AuthorizationRequest> CREATOR = new Creator<AuthorizationRequest>() {
        public AuthorizationRequest[] newArray(int i) {
            return new AuthorizationRequest[i];
        }

        public AuthorizationRequest createFromParcel(Parcel parcel) {
            return new AuthorizationRequest(parcel);
        }
    };
    private final Pattern WHITESPACE_PATTERN;
    private final HashMap<String, String> mAdditionalPayloadAttributes;
    private final byte[] mEncryptionKey;
    private final String mMsgGuid;
    private final OtcCrypto mOtcCrypto;
    private String mPrivacyUrl;
    private final HashSet<String> mScopes;
    private String mUserAgreementUrl;

    private class RFC3339DateFormat extends SimpleDateFormat {
        RFC3339DateFormat() {
            super("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        }
    }

    @Deprecated
    public String getBrowserSwitchUrl() {
        return "";
    }

    public boolean validateV1V2Response(Bundle bundle) {
        return true;
    }

    public AuthorizationRequest(Context context) {
        this.WHITESPACE_PATTERN = Pattern.compile("\\s");
        this.mOtcCrypto = new OtcCrypto();
        clientMetadataId(PayPalOneTouchCore.getClientMetadataId(context));
        this.mMsgGuid = UUID.randomUUID().toString();
        this.mEncryptionKey = this.mOtcCrypto.generateRandom256BitKey();
        this.mAdditionalPayloadAttributes = new HashMap<>();
        this.mScopes = new HashSet<>();
    }

    public AuthorizationRequest withAdditionalPayloadAttribute(String str, String str2) {
        this.mAdditionalPayloadAttributes.put(str, str2);
        return this;
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getAdditionalPayloadAttributes() {
        return new HashMap(this.mAdditionalPayloadAttributes);
    }

    public AuthorizationRequest withScopeValue(String str) {
        if (!this.WHITESPACE_PATTERN.matcher(str).find()) {
            this.mScopes.add(str);
            return this;
        }
        throw new IllegalArgumentException("scopes must be provided individually, with no whitespace");
    }

    private Set<String> getScopes() {
        return new HashSet(this.mScopes);
    }

    public String getScopeString() {
        return TextUtils.join(" ", getScopes());
    }

    public AuthorizationRequest privacyUrl(String str) {
        this.mPrivacyUrl = str;
        return this;
    }

    public String getPrivacyUrl() {
        return this.mPrivacyUrl;
    }

    public AuthorizationRequest userAgreementUrl(String str) {
        this.mUserAgreementUrl = str;
        return this;
    }

    public String getUserAgreementUrl() {
        return this.mUserAgreementUrl;
    }

    @Deprecated
    public String getBrowserSwitchUrl(Context context, OtcConfiguration otcConfiguration) throws CertificateException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, JSONException, BadPaddingException, InvalidEncryptionDataException, InvalidKeyException {
        ConfigEndpoint endpoint = otcConfiguration.getBrowserOauth2Config(getScopes()).getEndpoint(getEnvironment());
        X509Certificate x509CertificateFromBase64String = EncryptionUtils.getX509CertificateFromBase64String(endpoint.certificate);
        StringBuilder sb = new StringBuilder();
        sb.append(endpoint.url);
        sb.append("?payload=");
        String str = "utf-8";
        sb.append(URLEncoder.encode(buildPayload(context, x509CertificateFromBase64String), str));
        sb.append("&payloadEnc=");
        sb.append(URLEncoder.encode(buildPayloadEnc(x509CertificateFromBase64String), str));
        sb.append("&x-source=");
        sb.append(context.getPackageName());
        sb.append("&x-success=");
        sb.append(getSuccessUrl());
        sb.append("&x-cancel=");
        sb.append(getCancelUrl());
        return sb.toString();
    }

    public Recipe getBrowserSwitchRecipe(OtcConfiguration otcConfiguration) {
        return otcConfiguration.getBrowserOauth2Config(getScopes());
    }

    private boolean isValidResponse(String str) {
        return this.mMsgGuid.equals(str);
    }

    private String buildPayloadEnc(Certificate certificate) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidEncryptionDataException, InvalidKeyException, JSONException {
        return Base64.encodeToString(this.mOtcCrypto.encryptRSAData(getJsonObjectToEncrypt().toString().getBytes(), certificate), 2);
    }

    private JSONObject getJsonObjectToEncrypt() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(Param.TIMESTAMP, new RFC3339DateFormat().format(new Date()));
        jSONObject.put("msg_GUID", this.mMsgGuid);
        jSONObject.put("sym_key", EncryptionUtils.byteArrayToHexString(this.mEncryptionKey));
        String deviceName = DeviceInspector.getDeviceName();
        jSONObject.put("device_name", deviceName.substring(0, Math.min(deviceName.length(), 30)));
        return jSONObject;
    }

    private String buildPayload(Context context, X509Certificate x509Certificate) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ClientCookie.VERSION_ATTR, 3);
            jSONObject.put("client_id", getClientId());
            jSONObject.put("app_name", DeviceInspector.getApplicationInfoName(context));
            jSONObject.put("environment", getEnvironment());
            jSONObject.put("environment_url", EnvironmentManager.getEnvironmentUrl(getEnvironment()));
            jSONObject.put("scope", getScopeString());
            jSONObject.put("response_type", "code");
            jSONObject.put("privacy_url", getPrivacyUrl());
            jSONObject.put("agreement_url", getUserAgreementUrl());
            jSONObject.put("client_metadata_id", getClientMetadataId());
            jSONObject.put("key_id", x509Certificate.getSerialNumber());
            jSONObject.put("android_chrome_available", isChromeAvailable(context));
            for (Entry entry : this.mAdditionalPayloadAttributes.entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
            return Base64.encodeToString(jSONObject.toString().getBytes(), 2);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isChromeAvailable(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(IdentityProviders.PAYPAL));
        intent.setPackage("com.android.chrome");
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public Result parseBrowserResponse(Uri uri) {
        JSONObject jSONObject;
        String lastPathSegment = uri.getLastPathSegment();
        String queryParameter = uri.getQueryParameter("payloadEnc");
        try {
            jSONObject = new JSONObject(new String(Base64.decode(uri.getQueryParameter("payload"), 0)));
        } catch (IllegalArgumentException | NullPointerException | JSONException unused) {
            jSONObject = new JSONObject();
        }
        String str = "null";
        String str2 = "error";
        String str3 = "";
        if (Uri.parse(getSuccessUrl()).getLastPathSegment().equals(lastPathSegment)) {
            String str4 = "msg_GUID";
            if (!jSONObject.has(str4)) {
                return new Result((Throwable) new ResponseParsingException("Response incomplete"));
            }
            if (TextUtils.isEmpty(queryParameter) || !isValidResponse(Json.optString(jSONObject, str4, str3))) {
                return new Result((Throwable) new ResponseParsingException("Response invalid"));
            }
            try {
                JSONObject decryptedPayload = getDecryptedPayload(queryParameter);
                String optString = Json.optString(jSONObject, str2, str3);
                if (TextUtils.isEmpty(optString) || str.equals(optString)) {
                    return new Result(Json.optString(jSONObject, "environment", str3), ResponseType.authorization_code, new JSONObject().put("code", decryptedPayload.getString("payment_code")), decryptedPayload.getString("email"));
                }
                return new Result((Throwable) new BrowserSwitchException(optString));
            } catch (InvalidEncryptionDataException | IllegalArgumentException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | JSONException e) {
                return new Result((Throwable) new ResponseParsingException(e));
            }
        } else if (!Uri.parse(getCancelUrl()).getLastPathSegment().equals(lastPathSegment)) {
            return new Result((Throwable) new ResponseParsingException("Response uri invalid"));
        } else {
            String optString2 = Json.optString(jSONObject, str2, str3);
            if (TextUtils.isEmpty(optString2) || str.equals(optString2)) {
                return new Result();
            }
            return new Result((Throwable) new BrowserSwitchException(optString2));
        }
    }

    @Deprecated
    public Result parseBrowserResponse(ContextInspector contextInspector, Uri uri) {
        return parseBrowserResponse(uri);
    }

    public boolean validateV1V2Response(ContextInspector contextInspector, Bundle bundle) {
        return validateV1V2Response(bundle);
    }

    public Recipe getRecipeToExecute(Context context, OtcConfiguration otcConfiguration) {
        for (OAuth2Recipe oAuth2Recipe : otcConfiguration.getOauth2Recipes()) {
            if (oAuth2Recipe.isValidForScopes(getScopes())) {
                if (RequestTarget.wallet == oAuth2Recipe.getTarget()) {
                    if (oAuth2Recipe.isValidAppTarget(context)) {
                        return oAuth2Recipe;
                    }
                } else if (RequestTarget.browser == oAuth2Recipe.getTarget()) {
                    try {
                        if (oAuth2Recipe.isValidBrowserTarget(context, getBrowserSwitchUrl(context, otcConfiguration))) {
                            return oAuth2Recipe;
                        }
                    } catch (InvalidEncryptionDataException | UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | JSONException unused) {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    public void trackFpti(Context context, TrackingPoint trackingPoint, Protocol protocol) {
        HashMap hashMap = new HashMap();
        hashMap.put("clid", getClientId());
        PayPalOneTouchCore.getFptiManager(context).trackFpti(trackingPoint, getEnvironment(), hashMap, protocol);
    }

    private JSONObject getDecryptedPayload(String str) throws IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, InvalidEncryptionDataException, JSONException, IllegalArgumentException {
        return new JSONObject(new String(new OtcCrypto().decryptAESCTRData(Base64.decode(str, 0), this.mEncryptionKey)));
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mPrivacyUrl);
        parcel.writeString(this.mUserAgreementUrl);
        parcel.writeSerializable(this.mScopes);
        parcel.writeSerializable(this.mAdditionalPayloadAttributes);
        parcel.writeString(this.mMsgGuid);
        parcel.writeInt(this.mEncryptionKey.length);
        parcel.writeByteArray(this.mEncryptionKey);
    }

    private AuthorizationRequest(Parcel parcel) {
        super(parcel);
        this.WHITESPACE_PATTERN = Pattern.compile("\\s");
        this.mOtcCrypto = new OtcCrypto();
        this.mPrivacyUrl = parcel.readString();
        this.mUserAgreementUrl = parcel.readString();
        this.mScopes = (HashSet) parcel.readSerializable();
        this.mAdditionalPayloadAttributes = (HashMap) parcel.readSerializable();
        this.mMsgGuid = parcel.readString();
        this.mEncryptionKey = new byte[parcel.readInt()];
        parcel.readByteArray(this.mEncryptionKey);
    }
}
