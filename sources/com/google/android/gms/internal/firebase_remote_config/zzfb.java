package com.google.android.gms.internal.firebase_remote_config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

public final class zzfb implements FirebaseRemoteConfigValue {
    private final String value;
    private final int zzls;

    zzfb(String str, int i) {
        this.value = str;
        this.zzls = i;
    }

    public final long asLong() {
        if (this.zzls == 0) {
            return 0;
        }
        String trim = asString().trim();
        try {
            return Long.valueOf(trim).longValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("[Value: %s] cannot be converted to a %s.", new Object[]{trim, "long"}), e);
        }
    }

    public final double asDouble() {
        if (this.zzls == 0) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
        String trim = asString().trim();
        try {
            return Double.valueOf(trim).doubleValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("[Value: %s] cannot be converted to a %s.", new Object[]{trim, "double"}), e);
        }
    }

    public final String asString() {
        if (this.zzls == 0) {
            return "";
        }
        String str = this.value;
        if (str != null) {
            return str;
        }
        throw new IllegalArgumentException("Value is null, and cannot be converted to the desired type.");
    }

    public final byte[] asByteArray() {
        if (this.zzls == 0) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BYTE_ARRAY;
        }
        return this.value.getBytes(zzet.zzlb);
    }

    public final boolean asBoolean() throws IllegalArgumentException {
        if (this.zzls == 0) {
            return false;
        }
        String trim = asString().trim();
        if (zzet.zzlc.matcher(trim).matches()) {
            return true;
        }
        if (zzet.zzld.matcher(trim).matches()) {
            return false;
        }
        throw new IllegalArgumentException(String.format("[Value: %s] cannot be converted to a %s.", new Object[]{trim, "boolean"}));
    }

    public final int getSource() {
        return this.zzls;
    }
}
