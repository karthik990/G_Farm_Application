package p043io.fabric.sdk.android.services.settings;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.Kit;
import p043io.fabric.sdk.android.Logger;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;
import p043io.fabric.sdk.android.services.common.CommonUtils;
import p043io.fabric.sdk.android.services.network.HttpMethod;
import p043io.fabric.sdk.android.services.network.HttpRequest;
import p043io.fabric.sdk.android.services.network.HttpRequestFactory;

/* renamed from: io.fabric.sdk.android.services.settings.DefaultSettingsSpiCall */
class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String HEADER_DEVICE_MODEL = "X-CRASHLYTICS-DEVICE-MODEL";
    static final String HEADER_INSTALLATION_ID = "X-CRASHLYTICS-INSTALLATION-ID";
    static final String HEADER_OS_BUILD_VERSION = "X-CRASHLYTICS-OS-BUILD-VERSION";
    static final String HEADER_OS_DISPLAY_VERSION = "X-CRASHLYTICS-OS-DISPLAY-VERSION";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    /* access modifiers changed from: 0000 */
    public boolean requestWasSuccessful(int i) {
        return i == 200 || i == 201 || i == 202 || i == 203;
    }

    public DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory) {
        this(kit, str, str2, httpRequestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, str, str2, httpRequestFactory, httpMethod);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0090  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.json.JSONObject invoke(p043io.fabric.sdk.android.services.settings.SettingsRequest r10) {
        /*
            r9 = this;
            java.lang.String r0 = "X-REQUEST-ID"
            java.lang.String r1 = "Settings request ID: "
            java.lang.String r2 = "Fabric"
            r3 = 0
            java.util.Map r4 = r9.getQueryParamsFor(r10)     // Catch:{ HttpRequestException -> 0x0075, all -> 0x0070 }
            io.fabric.sdk.android.services.network.HttpRequest r5 = r9.getHttpRequest(r4)     // Catch:{ HttpRequestException -> 0x0075, all -> 0x0070 }
            io.fabric.sdk.android.services.network.HttpRequest r10 = r9.applyHeadersTo(r5, r10)     // Catch:{ HttpRequestException -> 0x006d, all -> 0x006a }
            io.fabric.sdk.android.Logger r5 = p043io.fabric.sdk.android.Fabric.getLogger()     // Catch:{ HttpRequestException -> 0x0068 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ HttpRequestException -> 0x0068 }
            r6.<init>()     // Catch:{ HttpRequestException -> 0x0068 }
            java.lang.String r7 = "Requesting settings from "
            r6.append(r7)     // Catch:{ HttpRequestException -> 0x0068 }
            java.lang.String r7 = r9.getUrl()     // Catch:{ HttpRequestException -> 0x0068 }
            r6.append(r7)     // Catch:{ HttpRequestException -> 0x0068 }
            java.lang.String r6 = r6.toString()     // Catch:{ HttpRequestException -> 0x0068 }
            r5.mo64074d(r2, r6)     // Catch:{ HttpRequestException -> 0x0068 }
            io.fabric.sdk.android.Logger r5 = p043io.fabric.sdk.android.Fabric.getLogger()     // Catch:{ HttpRequestException -> 0x0068 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ HttpRequestException -> 0x0068 }
            r6.<init>()     // Catch:{ HttpRequestException -> 0x0068 }
            java.lang.String r7 = "Settings query params were: "
            r6.append(r7)     // Catch:{ HttpRequestException -> 0x0068 }
            r6.append(r4)     // Catch:{ HttpRequestException -> 0x0068 }
            java.lang.String r4 = r6.toString()     // Catch:{ HttpRequestException -> 0x0068 }
            r5.mo64074d(r2, r4)     // Catch:{ HttpRequestException -> 0x0068 }
            org.json.JSONObject r3 = r9.handleResponse(r10)     // Catch:{ HttpRequestException -> 0x0068 }
            if (r10 == 0) goto L_0x008c
            io.fabric.sdk.android.Logger r4 = p043io.fabric.sdk.android.Fabric.getLogger()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
        L_0x0056:
            r5.append(r1)
            java.lang.String r10 = r10.header(r0)
            r5.append(r10)
            java.lang.String r10 = r5.toString()
            r4.mo64074d(r2, r10)
            goto L_0x008c
        L_0x0068:
            r4 = move-exception
            goto L_0x0077
        L_0x006a:
            r3 = move-exception
            r10 = r5
            goto L_0x008e
        L_0x006d:
            r4 = move-exception
            r10 = r5
            goto L_0x0077
        L_0x0070:
            r10 = move-exception
            r8 = r3
            r3 = r10
            r10 = r8
            goto L_0x008e
        L_0x0075:
            r4 = move-exception
            r10 = r3
        L_0x0077:
            io.fabric.sdk.android.Logger r5 = p043io.fabric.sdk.android.Fabric.getLogger()     // Catch:{ all -> 0x008d }
            java.lang.String r6 = "Settings request failed."
            r5.mo64077e(r2, r6, r4)     // Catch:{ all -> 0x008d }
            if (r10 == 0) goto L_0x008c
            io.fabric.sdk.android.Logger r4 = p043io.fabric.sdk.android.Fabric.getLogger()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            goto L_0x0056
        L_0x008c:
            return r3
        L_0x008d:
            r3 = move-exception
        L_0x008e:
            if (r10 == 0) goto L_0x00aa
            io.fabric.sdk.android.Logger r4 = p043io.fabric.sdk.android.Fabric.getLogger()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r1)
            java.lang.String r10 = r10.header(r0)
            r5.append(r10)
            java.lang.String r10 = r5.toString()
            r4.mo64074d(r2, r10)
        L_0x00aa:
            goto L_0x00ac
        L_0x00ab:
            throw r3
        L_0x00ac:
            goto L_0x00ab
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.fabric.sdk.android.services.settings.DefaultSettingsSpiCall.invoke(io.fabric.sdk.android.services.settings.SettingsRequest):org.json.JSONObject");
    }

    /* access modifiers changed from: 0000 */
    public JSONObject handleResponse(HttpRequest httpRequest) {
        int code = httpRequest.code();
        Logger logger = Fabric.getLogger();
        StringBuilder sb = new StringBuilder();
        sb.append("Settings result was: ");
        sb.append(code);
        String sb2 = sb.toString();
        String str = Fabric.TAG;
        logger.mo64074d(str, sb2);
        if (requestWasSuccessful(code)) {
            return getJsonObjectFrom(httpRequest.body());
        }
        Logger logger2 = Fabric.getLogger();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Failed to retrieve settings from ");
        sb3.append(getUrl());
        logger2.mo64076e(str, sb3.toString());
        return null;
    }

    private JSONObject getJsonObjectFrom(String str) {
        try {
            return new JSONObject(str);
        } catch (Exception e) {
            Logger logger = Fabric.getLogger();
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to parse settings JSON from ");
            sb.append(getUrl());
            String sb2 = sb.toString();
            String str2 = Fabric.TAG;
            logger.mo64075d(str2, sb2, e);
            Logger logger2 = Fabric.getLogger();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Settings response ");
            sb3.append(str);
            logger2.mo64074d(str2, sb3.toString());
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest settingsRequest) {
        HashMap hashMap = new HashMap();
        hashMap.put(BUILD_VERSION_PARAM, settingsRequest.buildVersion);
        hashMap.put(DISPLAY_VERSION_PARAM, settingsRequest.displayVersion);
        hashMap.put("source", Integer.toString(settingsRequest.source));
        if (settingsRequest.iconHash != null) {
            hashMap.put(ICON_HASH, settingsRequest.iconHash);
        }
        String str = settingsRequest.instanceId;
        if (!CommonUtils.isNullOrEmpty(str)) {
            hashMap.put(INSTANCE_PARAM, str);
        }
        return hashMap;
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, SettingsRequest settingsRequest) {
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_API_KEY, settingsRequest.apiKey);
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE);
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
        applyNonNullHeader(httpRequest, "Accept", "application/json");
        applyNonNullHeader(httpRequest, HEADER_DEVICE_MODEL, settingsRequest.deviceModel);
        applyNonNullHeader(httpRequest, HEADER_OS_BUILD_VERSION, settingsRequest.osBuildVersion);
        applyNonNullHeader(httpRequest, HEADER_OS_DISPLAY_VERSION, settingsRequest.osDisplayVersion);
        applyNonNullHeader(httpRequest, HEADER_INSTALLATION_ID, settingsRequest.installationId);
        return httpRequest;
    }

    private void applyNonNullHeader(HttpRequest httpRequest, String str, String str2) {
        if (str2 != null) {
            httpRequest.header(str, str2);
        }
    }
}
