package lib.android.paypal.com.magnessdk;

import android.content.Context;
import lib.android.paypal.com.magnessdk.network.MagnesNetworkingFactoryImpl;

public final class MagnesSettings {
    private static final int APPGUID_MAXLENGTH = 36;
    private String appGuid;
    private Context context;
    private boolean disableBeacon;
    private boolean disableRemoteConfig;
    private boolean enableNetworkOnCallerThread;
    private Environment environment;
    private MagnesNetworkingFactoryImpl magnesNetworkingFactoryImpl;
    private int magnesSource;
    private String notificationToken;

    public static class Builder {
        /* access modifiers changed from: private */
        public String appGuid;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public boolean disableBeacon = false;
        /* access modifiers changed from: private */
        public boolean disableRemoteConfig = false;
        /* access modifiers changed from: private */
        public boolean enableNetworkOnCallerThread;
        /* access modifiers changed from: private */
        public Environment environment = Environment.LIVE;
        /* access modifiers changed from: private */
        public MagnesNetworkingFactoryImpl magnesNetworkingFactoryImpl;
        /* access modifiers changed from: private */
        public String notificationToken;
        /* access modifiers changed from: private */
        public int sourceFlow = -1;

        public Builder(Context context2) {
            this.context = context2;
        }

        public MagnesSettings build() {
            return new MagnesSettings(this);
        }

        public Builder disableBeacon(boolean z) {
            this.disableBeacon = z;
            return this;
        }

        public Builder disableRemoteConfig(boolean z) {
            this.disableRemoteConfig = z;
            return this;
        }

        public Builder enableNetworkOnCallerThread(boolean z) {
            this.enableNetworkOnCallerThread = z;
            return this;
        }

        public Builder setAppGuid(String str) {
            this.appGuid = str;
            return this;
        }

        public Builder setMagnesEnvironment(Environment environment2) {
            this.environment = environment2;
            return this;
        }

        public Builder setMagnesNetworkingFactory(MagnesNetworkingFactoryImpl magnesNetworkingFactoryImpl2) {
            this.magnesNetworkingFactoryImpl = magnesNetworkingFactoryImpl2;
            return this;
        }

        public Builder setMagnesSource(MagnesSource magnesSource) {
            this.sourceFlow = magnesSource.getVersion();
            return this;
        }

        public Builder setNotificationToken(String str) {
            this.notificationToken = str;
            return this;
        }
    }

    private MagnesSettings(Builder builder) {
        this.magnesSource = -1;
        this.disableRemoteConfig = false;
        this.disableBeacon = false;
        this.magnesSource = builder.sourceFlow;
        this.appGuid = builder.appGuid;
        this.notificationToken = builder.notificationToken;
        this.disableRemoteConfig = builder.disableRemoteConfig;
        this.disableBeacon = builder.disableBeacon;
        this.context = builder.context;
        this.magnesNetworkingFactoryImpl = builder.magnesNetworkingFactoryImpl;
        this.enableNetworkOnCallerThread = builder.enableNetworkOnCallerThread;
        this.environment = builder.environment;
    }

    public String getAppGuid() {
        return this.appGuid;
    }

    /* access modifiers changed from: 0000 */
    public Context getContext() {
        return this.context;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public MagnesNetworkingFactoryImpl getMagnesNetworkingFactoryImpl() {
        return this.magnesNetworkingFactoryImpl;
    }

    public int getMagnesSource() {
        return this.magnesSource;
    }

    public String getNotificationToken() {
        return this.notificationToken;
    }

    public boolean isDisableBeacon() {
        return this.disableBeacon;
    }

    public boolean isDisableRemoteConfig() {
        return this.disableRemoteConfig;
    }

    public boolean isEnableNetworkOnCallerThread() {
        return this.enableNetworkOnCallerThread;
    }
}
