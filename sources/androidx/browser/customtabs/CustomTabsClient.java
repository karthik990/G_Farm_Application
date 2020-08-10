package androidx.browser.customtabs;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback.Stub;
import android.support.customtabs.ICustomTabsService;
import android.text.TextUtils;
import androidx.browser.customtabs.CustomTabsSession.PendingSession;
import java.util.ArrayList;
import java.util.List;

public class CustomTabsClient {
    private final Context mApplicationContext;
    private final ICustomTabsService mService;
    private final ComponentName mServiceComponentName;

    CustomTabsClient(ICustomTabsService iCustomTabsService, ComponentName componentName, Context context) {
        this.mService = iCustomTabsService;
        this.mServiceComponentName = componentName;
        this.mApplicationContext = context;
    }

    public static boolean bindCustomTabsService(Context context, String str, CustomTabsServiceConnection customTabsServiceConnection) {
        customTabsServiceConnection.setApplicationContext(context.getApplicationContext());
        Intent intent = new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
        if (!TextUtils.isEmpty(str)) {
            intent.setPackage(str);
        }
        return context.bindService(intent, customTabsServiceConnection, 33);
    }

    public static String getPackageName(Context context, List<String> list) {
        return getPackageName(context, list, false);
    }

    public static String getPackageName(Context context, List<String> list, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        List<String> arrayList = list == null ? new ArrayList<>() : list;
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://"));
        if (!z) {
            ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
            if (resolveActivity != null) {
                String str = resolveActivity.activityInfo.packageName;
                ArrayList arrayList2 = new ArrayList(arrayList.size() + 1);
                arrayList2.add(str);
                if (list != null) {
                    arrayList2.addAll(list);
                }
                arrayList = arrayList2;
            }
        }
        Intent intent2 = new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
        for (String str2 : arrayList) {
            intent2.setPackage(str2);
            if (packageManager.resolveService(intent2, 0) != null) {
                return str2;
            }
        }
        return null;
    }

    public static boolean connectAndInitialize(Context context, String str) {
        if (str == null) {
            return false;
        }
        final Context applicationContext = context.getApplicationContext();
        try {
            return bindCustomTabsService(applicationContext, str, new CustomTabsServiceConnection() {
                public void onServiceDisconnected(ComponentName componentName) {
                }

                public final void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                    customTabsClient.warmup(0);
                    applicationContext.unbindService(this);
                }
            });
        } catch (SecurityException unused) {
            return false;
        }
    }

    public boolean warmup(long j) {
        try {
            return this.mService.warmup(j);
        } catch (RemoteException unused) {
            return false;
        }
    }

    private static PendingIntent createSessionId(Context context, int i) {
        return PendingIntent.getActivity(context, i, new Intent(), 0);
    }

    public CustomTabsSession newSession(CustomTabsCallback customTabsCallback) {
        return newSessionInternal(customTabsCallback, null);
    }

    public CustomTabsSession newSession(CustomTabsCallback customTabsCallback, int i) {
        return newSessionInternal(customTabsCallback, createSessionId(this.mApplicationContext, i));
    }

    public static PendingSession newPendingSession(Context context, CustomTabsCallback customTabsCallback, int i) {
        return new PendingSession(customTabsCallback, createSessionId(context, i));
    }

    private CustomTabsSession newSessionInternal(CustomTabsCallback customTabsCallback, PendingIntent pendingIntent) {
        boolean z;
        Stub createCallbackWrapper = createCallbackWrapper(customTabsCallback);
        CustomTabsSession customTabsSession = null;
        if (pendingIntent != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putParcelable(CustomTabsIntent.EXTRA_SESSION_ID, pendingIntent);
                z = this.mService.newSessionWithExtras(createCallbackWrapper, bundle);
            } catch (RemoteException unused) {
            }
        } else {
            z = this.mService.newSession(createCallbackWrapper);
        }
        if (!z) {
            return null;
        }
        customTabsSession = new CustomTabsSession(this.mService, createCallbackWrapper, this.mServiceComponentName, pendingIntent);
        return customTabsSession;
    }

    public Bundle extraCommand(String str, Bundle bundle) {
        try {
            return this.mService.extraCommand(str, bundle);
        } catch (RemoteException unused) {
            return null;
        }
    }

    private Stub createCallbackWrapper(final CustomTabsCallback customTabsCallback) {
        return new Stub() {
            private Handler mHandler = new Handler(Looper.getMainLooper());

            public void onNavigationEvent(final int i, final Bundle bundle) {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.onNavigationEvent(i, bundle);
                        }
                    });
                }
            }

            public void extraCallback(final String str, final Bundle bundle) throws RemoteException {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.extraCallback(str, bundle);
                        }
                    });
                }
            }

            public Bundle extraCallbackWithResult(String str, Bundle bundle) throws RemoteException {
                CustomTabsCallback customTabsCallback = customTabsCallback;
                if (customTabsCallback == null) {
                    return null;
                }
                return customTabsCallback.extraCallbackWithResult(str, bundle);
            }

            public void onMessageChannelReady(final Bundle bundle) throws RemoteException {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.onMessageChannelReady(bundle);
                        }
                    });
                }
            }

            public void onPostMessage(final String str, final Bundle bundle) throws RemoteException {
                if (customTabsCallback != null) {
                    this.mHandler.post(new Runnable() {
                        public void run() {
                            customTabsCallback.onPostMessage(str, bundle);
                        }
                    });
                }
            }

            public void onRelationshipValidationResult(int i, Uri uri, boolean z, Bundle bundle) throws RemoteException {
                if (customTabsCallback != null) {
                    Handler handler = this.mHandler;
                    final int i2 = i;
                    final Uri uri2 = uri;
                    final boolean z2 = z;
                    final Bundle bundle2 = bundle;
                    C02475 r1 = new Runnable() {
                        public void run() {
                            customTabsCallback.onRelationshipValidationResult(i2, uri2, z2, bundle2);
                        }
                    };
                    handler.post(r1);
                }
            }
        };
    }

    public CustomTabsSession attachSession(PendingSession pendingSession) {
        return newSessionInternal(pendingSession.getCallback(), pendingSession.getId());
    }
}
