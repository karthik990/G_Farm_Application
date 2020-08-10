package com.twitter.sdk.android.core;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.AuthToken;

public class Session<T extends AuthToken> {
    @SerializedName("auth_token")
    private final T authToken;
    @SerializedName("id")

    /* renamed from: id */
    private final long f3653id;

    public Session(T t, long j) {
        if (t != null) {
            this.authToken = t;
            this.f3653id = j;
            return;
        }
        throw new IllegalArgumentException("AuthToken must not be null.");
    }

    public T getAuthToken() {
        return this.authToken;
    }

    public long getId() {
        return this.f3653id;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Session session = (Session) obj;
        if (this.f3653id != session.f3653id) {
            return false;
        }
        T t = this.authToken;
        T t2 = session.authToken;
        if (t != null) {
            z = t.equals(t2);
        } else if (t2 != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        T t = this.authToken;
        int hashCode = (t != null ? t.hashCode() : 0) * 31;
        long j = this.f3653id;
        return hashCode + ((int) (j ^ (j >>> 32)));
    }
}
