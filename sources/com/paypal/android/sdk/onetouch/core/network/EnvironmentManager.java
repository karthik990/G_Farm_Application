package com.paypal.android.sdk.onetouch.core.network;

public class EnvironmentManager {
    public static final String LIVE = "live";
    public static final String LIVE_API_M_ENDPOINT = "https://api-m.paypal.com/v1/";
    public static final String MOCK = "mock";
    public static final String SANDBOX = "sandbox";
    public static final String SANDBOX_API_M_ENDPOINT = "https://api-m.sandbox.paypal.com/v1/";

    public static boolean isMock(String str) {
        return str.equals(MOCK);
    }

    public static boolean isSandbox(String str) {
        return str.equals(SANDBOX);
    }

    public static boolean isLive(String str) {
        return str.equals(LIVE);
    }

    public static boolean isStage(String str) {
        return !isLive(str) && !isSandbox(str) && !isMock(str);
    }

    public static String getEnvironmentUrl(String str) {
        if (isLive(str)) {
            return LIVE_API_M_ENDPOINT;
        }
        if (isSandbox(str)) {
            return SANDBOX_API_M_ENDPOINT;
        }
        if (isMock(str)) {
            str = null;
        }
        return str;
    }
}
