package androidx.browser.trusted;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.customtabs.trusted.ITrustedWebActivityService.Stub;
import androidx.core.app.NotificationManagerCompat;
import java.util.Locale;

public abstract class TrustedWebActivityService extends Service {
    public static final String ACTION_TRUSTED_WEB_ACTIVITY_SERVICE = "android.support.customtabs.trusted.TRUSTED_WEB_ACTIVITY_SERVICE";
    public static final String KEY_SMALL_ICON_BITMAP = "android.support.customtabs.trusted.SMALL_ICON_BITMAP";
    public static final String META_DATA_NAME_SMALL_ICON = "android.support.customtabs.trusted.SMALL_ICON";
    public static final int SMALL_ICON_NOT_SET = -1;
    private final Stub mBinder = new Stub() {
        public Bundle areNotificationsEnabled(Bundle bundle) {
            checkCaller();
            return new ResultArgs(TrustedWebActivityService.this.onAreNotificationsEnabled(NotificationsEnabledArgs.fromBundle(bundle).channelName)).toBundle();
        }

        public Bundle notifyNotificationWithChannel(Bundle bundle) {
            checkCaller();
            NotifyNotificationArgs fromBundle = NotifyNotificationArgs.fromBundle(bundle);
            return new ResultArgs(TrustedWebActivityService.this.onNotifyNotificationWithChannel(fromBundle.platformTag, fromBundle.platformId, fromBundle.notification, fromBundle.channelName)).toBundle();
        }

        public void cancelNotification(Bundle bundle) {
            checkCaller();
            CancelNotificationArgs fromBundle = CancelNotificationArgs.fromBundle(bundle);
            TrustedWebActivityService.this.onCancelNotification(fromBundle.platformTag, fromBundle.platformId);
        }

        public Bundle getActiveNotifications() {
            checkCaller();
            return new ActiveNotificationsArgs(TrustedWebActivityService.this.onGetActiveNotifications()).toBundle();
        }

        public int getSmallIconId() {
            checkCaller();
            return TrustedWebActivityService.this.onGetSmallIconId();
        }

        public Bundle getSmallIconBitmap() {
            checkCaller();
            return TrustedWebActivityService.this.onGetSmallIconBitmap();
        }

        private void checkCaller() {
            if (TrustedWebActivityService.this.mVerifiedUid == -1) {
                String[] packagesForUid = TrustedWebActivityService.this.getPackageManager().getPackagesForUid(getCallingUid());
                int i = 0;
                if (packagesForUid == null) {
                    packagesForUid = new String[0];
                }
                Token load = TrustedWebActivityService.this.getTokenStore().load();
                PackageManager packageManager = TrustedWebActivityService.this.getPackageManager();
                if (load != null) {
                    int length = packagesForUid.length;
                    while (true) {
                        if (i >= length) {
                            break;
                        } else if (load.matches(packagesForUid[i], packageManager)) {
                            TrustedWebActivityService.this.mVerifiedUid = getCallingUid();
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            if (TrustedWebActivityService.this.mVerifiedUid != getCallingUid()) {
                throw new SecurityException("Caller is not verified as Trusted Web Activity provider.");
            }
        }
    };
    private NotificationManager mNotificationManager;
    int mVerifiedUid = -1;

    public abstract TokenStore getTokenStore();

    public void onCreate() {
        super.onCreate();
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
    }

    public boolean onAreNotificationsEnabled(String str) {
        ensureOnCreateCalled();
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            return false;
        }
        if (VERSION.SDK_INT < 26) {
            return true;
        }
        return NotificationApiHelperForO.isChannelEnabled(this.mNotificationManager, channelNameToId(str));
    }

    public boolean onNotifyNotificationWithChannel(String str, int i, Notification notification, String str2) {
        ensureOnCreateCalled();
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            return false;
        }
        if (VERSION.SDK_INT >= 26) {
            String channelNameToId = channelNameToId(str2);
            notification = NotificationApiHelperForO.copyNotificationOntoChannel(this, this.mNotificationManager, notification, channelNameToId, str2);
            if (!NotificationApiHelperForO.isChannelEnabled(this.mNotificationManager, channelNameToId)) {
                return false;
            }
        }
        this.mNotificationManager.notify(str, i, notification);
        return true;
    }

    public void onCancelNotification(String str, int i) {
        ensureOnCreateCalled();
        this.mNotificationManager.cancel(str, i);
    }

    public Parcelable[] onGetActiveNotifications() {
        ensureOnCreateCalled();
        if (VERSION.SDK_INT >= 23) {
            return NotificationApiHelperForM.getActiveNotifications(this.mNotificationManager);
        }
        throw new IllegalStateException("onGetActiveNotifications cannot be called pre-M.");
    }

    public Bundle onGetSmallIconBitmap() {
        int onGetSmallIconId = onGetSmallIconId();
        Bundle bundle = new Bundle();
        if (onGetSmallIconId == -1) {
            return bundle;
        }
        bundle.putParcelable(KEY_SMALL_ICON_BITMAP, BitmapFactory.decodeResource(getResources(), onGetSmallIconId));
        return bundle;
    }

    public int onGetSmallIconId() {
        int i = -1;
        try {
            ServiceInfo serviceInfo = getPackageManager().getServiceInfo(new ComponentName(this, getClass()), 128);
            if (serviceInfo.metaData == null) {
                return -1;
            }
            i = serviceInfo.metaData.getInt(META_DATA_NAME_SMALL_ICON, -1);
            return i;
        } catch (NameNotFoundException unused) {
        }
    }

    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public final boolean onUnbind(Intent intent) {
        this.mVerifiedUid = -1;
        return super.onUnbind(intent);
    }

    private static String channelNameToId(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.toLowerCase(Locale.ROOT).replace(' ', '_'));
        sb.append("_channel_id");
        return sb.toString();
    }

    private void ensureOnCreateCalled() {
        if (this.mNotificationManager == null) {
            throw new IllegalStateException("TrustedWebActivityService has not been properly initialized. Did onCreate() call super.onCreate()?");
        }
    }
}
