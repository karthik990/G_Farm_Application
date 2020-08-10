package com.google.android.exoplayer2.scheduler;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.PowerManager;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Requirements implements Parcelable {
    public static final Creator<Requirements> CREATOR = new Creator<Requirements>() {
        public Requirements createFromParcel(Parcel parcel) {
            return new Requirements(parcel.readInt());
        }

        public Requirements[] newArray(int i) {
            return new Requirements[i];
        }
    };
    public static final int DEVICE_CHARGING = 8;
    public static final int DEVICE_IDLE = 4;
    public static final int NETWORK = 1;
    public static final int NETWORK_UNMETERED = 2;
    private final int requirements;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequirementFlags {
    }

    public int describeContents() {
        return 0;
    }

    public Requirements(int i) {
        if ((i & 2) != 0) {
            i |= 1;
        }
        this.requirements = i;
    }

    public int getRequirements() {
        return this.requirements;
    }

    public boolean isNetworkRequired() {
        return (this.requirements & 1) != 0;
    }

    public boolean isUnmeteredNetworkRequired() {
        return (this.requirements & 2) != 0;
    }

    public boolean isChargingRequired() {
        return (this.requirements & 8) != 0;
    }

    public boolean isIdleRequired() {
        return (this.requirements & 4) != 0;
    }

    public boolean checkRequirements(Context context) {
        return getNotMetRequirements(context) == 0;
    }

    public int getNotMetRequirements(Context context) {
        int notMetNetworkRequirements = getNotMetNetworkRequirements(context);
        if (isChargingRequired() && !isDeviceCharging(context)) {
            notMetNetworkRequirements |= 8;
        }
        return (!isIdleRequired() || isDeviceIdle(context)) ? notMetNetworkRequirements : notMetNetworkRequirements | 4;
    }

    private int getNotMetNetworkRequirements(Context context) {
        if (!isNetworkRequired()) {
            return 0;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) Assertions.checkNotNull(connectivityManager)).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || !isInternetConnectivityValidated(connectivityManager)) {
            return this.requirements & 3;
        }
        if (!isUnmeteredNetworkRequired() || !connectivityManager.isActiveNetworkMetered()) {
            return 0;
        }
        return 2;
    }

    private boolean isDeviceCharging(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        boolean z = false;
        if (registerReceiver == null) {
            return false;
        }
        int intExtra = registerReceiver.getIntExtra("status", -1);
        if (intExtra == 2 || intExtra == 5) {
            z = true;
        }
        return z;
    }

    private boolean isDeviceIdle(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (Util.SDK_INT >= 23) {
            return powerManager.isDeviceIdleMode();
        }
        if (Util.SDK_INT >= 20) {
            if (!powerManager.isInteractive()) {
                return true;
            }
        } else if (!powerManager.isScreenOn()) {
            return true;
        }
        return false;
    }

    private static boolean isInternetConnectivityValidated(ConnectivityManager connectivityManager) {
        if (Util.SDK_INT < 23) {
            return true;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        boolean z = false;
        if (activeNetwork == null) {
            return false;
        }
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities == null || !networkCapabilities.hasCapability(16)) {
            z = true;
        }
        return !z;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this.requirements != ((Requirements) obj).requirements) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.requirements;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.requirements);
    }
}
