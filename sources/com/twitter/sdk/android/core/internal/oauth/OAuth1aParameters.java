package com.twitter.sdk.android.core.internal.oauth;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.internal.network.UrlUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;
import okio.ByteString;

class OAuth1aParameters {
    private static final SecureRandom RAND = new SecureRandom();
    private static final String SIGNATURE_METHOD = "HMAC-SHA1";
    private static final String VERSION = "1.0";
    private final TwitterAuthConfig authConfig;
    private final TwitterAuthToken authToken;
    private final String callback;
    private final String method;
    private final Map<String, String> postParams;
    private final String url;

    OAuth1aParameters(TwitterAuthConfig twitterAuthConfig, TwitterAuthToken twitterAuthToken, String str, String str2, String str3, Map<String, String> map) {
        this.authConfig = twitterAuthConfig;
        this.authToken = twitterAuthToken;
        this.callback = str;
        this.method = str2;
        this.url = str3;
        this.postParams = map;
    }

    public String getAuthorizationHeader() {
        String nonce = getNonce();
        String timestamp = getTimestamp();
        return constructAuthorizationHeader(nonce, timestamp, calculateSignature(constructSignatureBase(nonce, timestamp)));
    }

    private String getNonce() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(System.nanoTime()));
        sb.append(String.valueOf(Math.abs(RAND.nextLong())));
        return sb.toString();
    }

    private String getTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /* access modifiers changed from: 0000 */
    public String constructSignatureBase(String str, String str2) {
        URI create = URI.create(this.url);
        TreeMap queryParams = UrlUtils.getQueryParams(create, true);
        Map<String, String> map = this.postParams;
        if (map != null) {
            queryParams.putAll(map);
        }
        String str3 = this.callback;
        if (str3 != null) {
            queryParams.put(OAuthConstants.PARAM_CALLBACK, str3);
        }
        queryParams.put(OAuthConstants.PARAM_CONSUMER_KEY, this.authConfig.getConsumerKey());
        queryParams.put(OAuthConstants.PARAM_NONCE, str);
        queryParams.put(OAuthConstants.PARAM_SIGNATURE_METHOD, SIGNATURE_METHOD);
        queryParams.put(OAuthConstants.PARAM_TIMESTAMP, str2);
        TwitterAuthToken twitterAuthToken = this.authToken;
        if (!(twitterAuthToken == null || twitterAuthToken.token == null)) {
            queryParams.put(OAuthConstants.PARAM_TOKEN, this.authToken.token);
        }
        queryParams.put(OAuthConstants.PARAM_VERSION, "1.0");
        StringBuilder sb = new StringBuilder();
        sb.append(create.getScheme());
        sb.append("://");
        sb.append(create.getHost());
        sb.append(create.getPath());
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.method.toUpperCase(Locale.ENGLISH));
        sb3.append(Typography.amp);
        sb3.append(UrlUtils.percentEncode(sb2));
        sb3.append(Typography.amp);
        sb3.append(getEncodedQueryParams(queryParams));
        return sb3.toString();
    }

    private String getEncodedQueryParams(TreeMap<String, String> treeMap) {
        StringBuilder sb = new StringBuilder();
        int size = treeMap.size();
        int i = 0;
        for (Entry entry : treeMap.entrySet()) {
            sb.append(UrlUtils.percentEncode(UrlUtils.percentEncode((String) entry.getKey())));
            sb.append("%3D");
            sb.append(UrlUtils.percentEncode(UrlUtils.percentEncode((String) entry.getValue())));
            i++;
            if (i < size) {
                sb.append("%26");
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String calculateSignature(String str) {
        String str2 = "HmacSHA1";
        String str3 = "UTF8";
        String str4 = "";
        String str5 = "Failed to calculate signature";
        String str6 = "Twitter";
        try {
            String signingKey = getSigningKey();
            byte[] bytes = str.getBytes(str3);
            SecretKeySpec secretKeySpec = new SecretKeySpec(signingKey.getBytes(str3), str2);
            Mac instance = Mac.getInstance(str2);
            instance.init(secretKeySpec);
            byte[] doFinal = instance.doFinal(bytes);
            return ByteString.m1340of(doFinal, 0, doFinal.length).base64();
        } catch (InvalidKeyException e) {
            Twitter.getLogger().mo20820e(str6, str5, e);
            return str4;
        } catch (NoSuchAlgorithmException e2) {
            Twitter.getLogger().mo20820e(str6, str5, e2);
            return str4;
        } catch (UnsupportedEncodingException e3) {
            Twitter.getLogger().mo20820e(str6, str5, e3);
            return str4;
        }
    }

    private String getSigningKey() {
        TwitterAuthToken twitterAuthToken = this.authToken;
        String str = twitterAuthToken != null ? twitterAuthToken.secret : null;
        StringBuilder sb = new StringBuilder();
        sb.append(UrlUtils.urlEncode(this.authConfig.getConsumerSecret()));
        sb.append(Typography.amp);
        sb.append(UrlUtils.urlEncode(str));
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String constructAuthorizationHeader(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("OAuth");
        appendParameter(sb, OAuthConstants.PARAM_CALLBACK, this.callback);
        appendParameter(sb, OAuthConstants.PARAM_CONSUMER_KEY, this.authConfig.getConsumerKey());
        appendParameter(sb, OAuthConstants.PARAM_NONCE, str);
        appendParameter(sb, OAuthConstants.PARAM_SIGNATURE, str3);
        appendParameter(sb, OAuthConstants.PARAM_SIGNATURE_METHOD, SIGNATURE_METHOD);
        appendParameter(sb, OAuthConstants.PARAM_TIMESTAMP, str2);
        TwitterAuthToken twitterAuthToken = this.authToken;
        appendParameter(sb, OAuthConstants.PARAM_TOKEN, twitterAuthToken != null ? twitterAuthToken.token : null);
        appendParameter(sb, OAuthConstants.PARAM_VERSION, "1.0");
        return sb.substring(0, sb.length() - 1);
    }

    private void appendParameter(StringBuilder sb, String str, String str2) {
        if (str2 != null) {
            sb.append(' ');
            sb.append(UrlUtils.percentEncode(str));
            sb.append("=\"");
            sb.append(UrlUtils.percentEncode(str2));
            sb.append("\",");
        }
    }
}
