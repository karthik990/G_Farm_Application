package com.twitter.sdk.android.core.internal.oauth;

import com.google.gson.annotations.SerializedName;

public class GuestAuthToken extends OAuth2Token {
    private static final long EXPIRES_IN_MS = 10800000;
    public static final String HEADER_GUEST_TOKEN = "x-guest-token";
    @SerializedName("guest_token")
    private final String guestToken;

    public GuestAuthToken(String str, String str2, String str3) {
        super(str, str2);
        this.guestToken = str3;
    }

    public GuestAuthToken(String str, String str2, String str3, long j) {
        super(str, str2, j);
        this.guestToken = str3;
    }

    public String getGuestToken() {
        return this.guestToken;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= this.createdAt + EXPIRES_IN_MS;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        GuestAuthToken guestAuthToken = (GuestAuthToken) obj;
        String str = this.guestToken;
        String str2 = guestAuthToken.guestToken;
        return str == null ? str2 == null : str.equals(str2);
    }

    public int hashCode() {
        int hashCode = super.hashCode() * 31;
        String str = this.guestToken;
        return hashCode + (str != null ? str.hashCode() : 0);
    }
}
