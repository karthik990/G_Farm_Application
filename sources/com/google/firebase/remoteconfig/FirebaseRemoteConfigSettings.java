package com.google.firebase.remoteconfig;

public class FirebaseRemoteConfigSettings {
    private final boolean zzjn;

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean zzjn = false;

        public Builder setDeveloperModeEnabled(boolean z) {
            this.zzjn = z;
            return this;
        }

        public FirebaseRemoteConfigSettings build() {
            return new FirebaseRemoteConfigSettings(this);
        }
    }

    private FirebaseRemoteConfigSettings(Builder builder) {
        this.zzjn = builder.zzjn;
    }

    public boolean isDeveloperModeEnabled() {
        return this.zzjn;
    }
}
