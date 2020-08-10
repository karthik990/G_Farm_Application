package androidx.browser.trusted;

import android.app.Notification;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.customtabs.trusted.ITrustedWebActivityService;

public final class TrustedWebActivityServiceConnection {
    private static final String KEY_ACTIVE_NOTIFICATIONS = "android.support.customtabs.trusted.ACTIVE_NOTIFICATIONS";
    private static final String KEY_CHANNEL_NAME = "android.support.customtabs.trusted.CHANNEL_NAME";
    private static final String KEY_NOTIFICATION = "android.support.customtabs.trusted.NOTIFICATION";
    private static final String KEY_NOTIFICATION_SUCCESS = "android.support.customtabs.trusted.NOTIFICATION_SUCCESS";
    private static final String KEY_PLATFORM_ID = "android.support.customtabs.trusted.PLATFORM_ID";
    private static final String KEY_PLATFORM_TAG = "android.support.customtabs.trusted.PLATFORM_TAG";
    private final ComponentName mComponentName;
    private final ITrustedWebActivityService mService;

    static class ActiveNotificationsArgs {
        public final Parcelable[] notifications;

        ActiveNotificationsArgs(Parcelable[] parcelableArr) {
            this.notifications = parcelableArr;
        }

        public static ActiveNotificationsArgs fromBundle(Bundle bundle) {
            String str = TrustedWebActivityServiceConnection.KEY_ACTIVE_NOTIFICATIONS;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str);
            return new ActiveNotificationsArgs(bundle.getParcelableArray(str));
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(TrustedWebActivityServiceConnection.KEY_ACTIVE_NOTIFICATIONS, this.notifications);
            return bundle;
        }
    }

    static class CancelNotificationArgs {
        public final int platformId;
        public final String platformTag;

        CancelNotificationArgs(String str, int i) {
            this.platformTag = str;
            this.platformId = i;
        }

        public static CancelNotificationArgs fromBundle(Bundle bundle) {
            String str = TrustedWebActivityServiceConnection.KEY_PLATFORM_TAG;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str);
            String str2 = TrustedWebActivityServiceConnection.KEY_PLATFORM_ID;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str2);
            return new CancelNotificationArgs(bundle.getString(str), bundle.getInt(str2));
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(TrustedWebActivityServiceConnection.KEY_PLATFORM_TAG, this.platformTag);
            bundle.putInt(TrustedWebActivityServiceConnection.KEY_PLATFORM_ID, this.platformId);
            return bundle;
        }
    }

    static class NotificationsEnabledArgs {
        public final String channelName;

        NotificationsEnabledArgs(String str) {
            this.channelName = str;
        }

        public static NotificationsEnabledArgs fromBundle(Bundle bundle) {
            String str = TrustedWebActivityServiceConnection.KEY_CHANNEL_NAME;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str);
            return new NotificationsEnabledArgs(bundle.getString(str));
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(TrustedWebActivityServiceConnection.KEY_CHANNEL_NAME, this.channelName);
            return bundle;
        }
    }

    static class NotifyNotificationArgs {
        public final String channelName;
        public final Notification notification;
        public final int platformId;
        public final String platformTag;

        NotifyNotificationArgs(String str, int i, Notification notification2, String str2) {
            this.platformTag = str;
            this.platformId = i;
            this.notification = notification2;
            this.channelName = str2;
        }

        public static NotifyNotificationArgs fromBundle(Bundle bundle) {
            String str = TrustedWebActivityServiceConnection.KEY_PLATFORM_TAG;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str);
            String str2 = TrustedWebActivityServiceConnection.KEY_PLATFORM_ID;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str2);
            String str3 = TrustedWebActivityServiceConnection.KEY_NOTIFICATION;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str3);
            String str4 = TrustedWebActivityServiceConnection.KEY_CHANNEL_NAME;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str4);
            return new NotifyNotificationArgs(bundle.getString(str), bundle.getInt(str2), (Notification) bundle.getParcelable(str3), bundle.getString(str4));
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(TrustedWebActivityServiceConnection.KEY_PLATFORM_TAG, this.platformTag);
            bundle.putInt(TrustedWebActivityServiceConnection.KEY_PLATFORM_ID, this.platformId);
            bundle.putParcelable(TrustedWebActivityServiceConnection.KEY_NOTIFICATION, this.notification);
            bundle.putString(TrustedWebActivityServiceConnection.KEY_CHANNEL_NAME, this.channelName);
            return bundle;
        }
    }

    static class ResultArgs {
        public final boolean success;

        ResultArgs(boolean z) {
            this.success = z;
        }

        public static ResultArgs fromBundle(Bundle bundle) {
            String str = TrustedWebActivityServiceConnection.KEY_NOTIFICATION_SUCCESS;
            TrustedWebActivityServiceConnection.ensureBundleContains(bundle, str);
            return new ResultArgs(bundle.getBoolean(str));
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBoolean(TrustedWebActivityServiceConnection.KEY_NOTIFICATION_SUCCESS, this.success);
            return bundle;
        }
    }

    TrustedWebActivityServiceConnection(ITrustedWebActivityService iTrustedWebActivityService, ComponentName componentName) {
        this.mService = iTrustedWebActivityService;
        this.mComponentName = componentName;
    }

    public boolean areNotificationsEnabled(String str) throws RemoteException {
        return ResultArgs.fromBundle(this.mService.areNotificationsEnabled(new NotificationsEnabledArgs(str).toBundle())).success;
    }

    public boolean notify(String str, int i, Notification notification, String str2) throws RemoteException {
        return ResultArgs.fromBundle(this.mService.notifyNotificationWithChannel(new NotifyNotificationArgs(str, i, notification, str2).toBundle())).success;
    }

    public void cancel(String str, int i) throws RemoteException {
        this.mService.cancelNotification(new CancelNotificationArgs(str, i).toBundle());
    }

    public Parcelable[] getActiveNotifications() throws RemoteException {
        return ActiveNotificationsArgs.fromBundle(this.mService.getActiveNotifications()).notifications;
    }

    public int getSmallIconId() throws RemoteException {
        return this.mService.getSmallIconId();
    }

    public Bitmap getSmallIconBitmap() throws RemoteException {
        return (Bitmap) this.mService.getSmallIconBitmap().getParcelable(TrustedWebActivityService.KEY_SMALL_ICON_BITMAP);
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    static void ensureBundleContains(Bundle bundle, String str) {
        if (!bundle.containsKey(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bundle must contain ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
