package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.android.billingclient.BuildConfig;
import com.android.billingclient.api.BillingClient.FeatureType;
import com.android.billingclient.api.BillingResult.Builder;
import com.android.billingclient.api.Purchase.PurchasesResult;
import com.android.billingclient.api.SkuDetails.SkuDetailsResult;
import com.android.billingclient.util.BillingHelper;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;

class BillingClientImpl extends BillingClient {
    private static final long ASYNCHRONOUS_TIMEOUT_IN_MILLISECONDS = 30000;
    private static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
    private static final int MAX_IAP_VERSION = 9;
    private static final int MAX_SKU_DETAILS_ITEMS_PER_REQUEST = 20;
    private static final int MIN_IAP_VERSION = 3;
    private static final long SYNCHRONOUS_TIMEOUT_IN_MILLISECONDS = 5000;
    private static final String TAG = "BillingClient";
    /* access modifiers changed from: private */
    public final Context mApplicationContext;
    /* access modifiers changed from: private */
    public final BillingBroadcastManager mBroadcastManager;
    /* access modifiers changed from: private */
    public final int mChildDirected;
    /* access modifiers changed from: private */
    public int mClientState;
    private final boolean mEnablePendingPurchases;
    private ExecutorService mExecutorService;
    /* access modifiers changed from: private */
    public boolean mIABv6Supported;
    /* access modifiers changed from: private */
    public boolean mIABv8Supported;
    /* access modifiers changed from: private */
    public boolean mIABv9Supported;
    /* access modifiers changed from: private */
    public final String mQualifiedVersionNumber;
    /* access modifiers changed from: private */
    public IInAppBillingService mService;
    private BillingServiceConnection mServiceConnection;
    /* access modifiers changed from: private */
    public boolean mSubscriptionUpdateSupported;
    /* access modifiers changed from: private */
    public boolean mSubscriptionsSupported;
    private final Handler mUiThreadHandler;
    /* access modifiers changed from: private */
    public final int mUnderAgeOfConsent;
    private final ResultReceiver onPurchaseFinishedReceiver;

    private final class BillingServiceConnection implements ServiceConnection {
        /* access modifiers changed from: private */
        public boolean disconnected;
        /* access modifiers changed from: private */
        public final Object lock;
        /* access modifiers changed from: private */
        public BillingClientStateListener mListener;

        private BillingServiceConnection(BillingClientStateListener billingClientStateListener) {
            this.lock = new Object();
            this.disconnected = false;
            this.mListener = billingClientStateListener;
        }

        public void onServiceDisconnected(ComponentName componentName) {
            BillingHelper.logWarn(BillingClientImpl.TAG, "Billing service disconnected.");
            BillingClientImpl.this.mService = null;
            BillingClientImpl.this.mClientState = 0;
            synchronized (this.lock) {
                if (this.mListener != null) {
                    this.mListener.onBillingServiceDisconnected();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void markDisconnectedAndCleanUp() {
            synchronized (this.lock) {
                this.mListener = null;
                this.disconnected = true;
            }
        }

        /* access modifiers changed from: private */
        public void notifySetupResult(final BillingResult billingResult) {
            BillingClientImpl.this.postToUiThread(new Runnable() {
                public void run() {
                    synchronized (BillingServiceConnection.this.lock) {
                        if (BillingServiceConnection.this.mListener != null) {
                            BillingServiceConnection.this.mListener.onBillingSetupFinished(billingResult);
                        }
                    }
                }
            });
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BillingHelper.logVerbose(BillingClientImpl.TAG, "Billing service connected.");
            BillingClientImpl.this.mService = Stub.asInterface(iBinder);
            if (BillingClientImpl.this.executeAsync(new Callable<Void>() {
                /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
                    r3 = com.android.billingclient.api.BillingClientImpl.access$200(r10.this$1.this$0).getPackageName();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:11:0x0021, code lost:
                    r5 = 9;
                    r6 = 3;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
                    if (r5 < 3) goto L_0x003d;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
                    r6 = com.android.billingclient.api.BillingClientImpl.access$300(r10.this$1.this$0).isBillingSupported(r5, r3, "subs");
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:15:0x0037, code lost:
                    if (r6 != 0) goto L_0x003a;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:17:0x003a, code lost:
                    r5 = r5 - 1;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
                    r5 = 0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
                    r7 = r10.this$1.this$0;
                    r9 = true;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:20:0x0044, code lost:
                    if (r5 < 5) goto L_0x0048;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:21:0x0046, code lost:
                    r8 = true;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:22:0x0048, code lost:
                    r8 = false;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
                    com.android.billingclient.api.BillingClientImpl.access$1602(r7, r8);
                    r7 = r10.this$1.this$0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:24:0x0050, code lost:
                    if (r5 < 3) goto L_0x0054;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:25:0x0052, code lost:
                    r8 = true;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
                    r8 = false;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:27:0x0055, code lost:
                    com.android.billingclient.api.BillingClientImpl.access$1702(r7, r8);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:28:0x0058, code lost:
                    if (r5 >= 3) goto L_0x0061;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
                    com.android.billingclient.util.BillingHelper.logVerbose(com.android.billingclient.api.BillingClientImpl.TAG, "In-app billing API does not support subscription on this device.");
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:30:0x0061, code lost:
                    r5 = 9;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:31:0x0063, code lost:
                    if (r5 < 3) goto L_0x0079;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:32:0x0065, code lost:
                    r6 = com.android.billingclient.api.BillingClientImpl.access$300(r10.this$1.this$0).isBillingSupported(r5, r3, "inapp");
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:33:0x0073, code lost:
                    if (r6 != 0) goto L_0x0076;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:35:0x0076, code lost:
                    r5 = r5 - 1;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:36:0x0079, code lost:
                    r5 = 0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:37:0x007a, code lost:
                    r3 = r10.this$1.this$0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:38:0x007e, code lost:
                    if (r5 < 9) goto L_0x0082;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:39:0x0080, code lost:
                    r4 = true;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:40:0x0082, code lost:
                    r4 = false;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:41:0x0083, code lost:
                    com.android.billingclient.api.BillingClientImpl.access$1802(r3, r4);
                    r3 = r10.this$1.this$0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:42:0x008c, code lost:
                    if (r5 < 8) goto L_0x0090;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:43:0x008e, code lost:
                    r4 = true;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:44:0x0090, code lost:
                    r4 = false;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:45:0x0091, code lost:
                    com.android.billingclient.api.BillingClientImpl.access$1902(r3, r4);
                    r3 = r10.this$1.this$0;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:46:0x0099, code lost:
                    if (r5 < 6) goto L_0x009c;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:48:0x009c, code lost:
                    r9 = false;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:49:0x009d, code lost:
                    com.android.billingclient.api.BillingClientImpl.access$2002(r3, r9);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:50:0x00a0, code lost:
                    if (r5 >= 3) goto L_0x00a9;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:51:0x00a2, code lost:
                    com.android.billingclient.util.BillingHelper.logWarn(com.android.billingclient.api.BillingClientImpl.TAG, "In-app billing API version 3 is not supported on this device.");
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a9, code lost:
                    if (r6 != 0) goto L_0x00b4;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:53:0x00ab, code lost:
                    com.android.billingclient.api.BillingClientImpl.access$1202(r10.this$1.this$0, 2);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b4, code lost:
                    com.android.billingclient.api.BillingClientImpl.access$1202(r10.this$1.this$0, 0);
                    com.android.billingclient.api.BillingClientImpl.access$302(r10.this$1.this$0, null);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:55:0x00c3, code lost:
                    r6 = 3;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c4, code lost:
                    com.android.billingclient.util.BillingHelper.logWarn(com.android.billingclient.api.BillingClientImpl.TAG, "Exception while checking if billing is supported; try to reconnect");
                    com.android.billingclient.api.BillingClientImpl.access$1202(r10.this$1.this$0, 0);
                    com.android.billingclient.api.BillingClientImpl.access$302(r10.this$1.this$0, null);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:59:0x00d9, code lost:
                    if (r6 != 0) goto L_0x00e3;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:60:0x00db, code lost:
                    com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.access$2100(r10.this$1, com.android.billingclient.api.BillingResults.f83OK);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:61:0x00e3, code lost:
                    com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.access$2100(r10.this$1, com.android.billingclient.api.BillingResults.API_VERSION_NOT_V3);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:62:0x00ea, code lost:
                    return null;
                 */
                /* JADX WARNING: Removed duplicated region for block: B:60:0x00db  */
                /* JADX WARNING: Removed duplicated region for block: B:61:0x00e3  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public java.lang.Void call() {
                    /*
                        r10 = this;
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this
                        java.lang.Object r0 = r0.lock
                        monitor-enter(r0)
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r1 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ all -> 0x00eb }
                        boolean r1 = r1.disconnected     // Catch:{ all -> 0x00eb }
                        r2 = 0
                        if (r1 == 0) goto L_0x0012
                        monitor-exit(r0)     // Catch:{ all -> 0x00eb }
                        return r2
                    L_0x0012:
                        monitor-exit(r0)     // Catch:{ all -> 0x00eb }
                        r0 = 3
                        r1 = 0
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r3 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c3 }
                        com.android.billingclient.api.BillingClientImpl r3 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c3 }
                        android.content.Context r3 = r3.mApplicationContext     // Catch:{ Exception -> 0x00c3 }
                        java.lang.String r3 = r3.getPackageName()     // Catch:{ Exception -> 0x00c3 }
                        r4 = 9
                        r5 = 9
                        r6 = 3
                    L_0x0026:
                        if (r5 < r0) goto L_0x003d
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r7 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r7 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.vending.billing.IInAppBillingService r7 = r7.mService     // Catch:{ Exception -> 0x00c4 }
                        java.lang.String r8 = "subs"
                        int r6 = r7.isBillingSupported(r5, r3, r8)     // Catch:{ Exception -> 0x00c4 }
                        if (r6 != 0) goto L_0x003a
                        goto L_0x003e
                    L_0x003a:
                        int r5 = r5 + -1
                        goto L_0x0026
                    L_0x003d:
                        r5 = 0
                    L_0x003e:
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r7 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r7 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        r8 = 5
                        r9 = 1
                        if (r5 < r8) goto L_0x0048
                        r8 = 1
                        goto L_0x0049
                    L_0x0048:
                        r8 = 0
                    L_0x0049:
                        r7.mSubscriptionUpdateSupported = r8     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r7 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r7 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        if (r5 < r0) goto L_0x0054
                        r8 = 1
                        goto L_0x0055
                    L_0x0054:
                        r8 = 0
                    L_0x0055:
                        r7.mSubscriptionsSupported = r8     // Catch:{ Exception -> 0x00c4 }
                        if (r5 >= r0) goto L_0x0061
                        java.lang.String r5 = "BillingClient"
                        java.lang.String r7 = "In-app billing API does not support subscription on this device."
                        com.android.billingclient.util.BillingHelper.logVerbose(r5, r7)     // Catch:{ Exception -> 0x00c4 }
                    L_0x0061:
                        r5 = 9
                    L_0x0063:
                        if (r5 < r0) goto L_0x0079
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r7 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r7 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.vending.billing.IInAppBillingService r7 = r7.mService     // Catch:{ Exception -> 0x00c4 }
                        java.lang.String r8 = "inapp"
                        int r6 = r7.isBillingSupported(r5, r3, r8)     // Catch:{ Exception -> 0x00c4 }
                        if (r6 != 0) goto L_0x0076
                        goto L_0x007a
                    L_0x0076:
                        int r5 = r5 + -1
                        goto L_0x0063
                    L_0x0079:
                        r5 = 0
                    L_0x007a:
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r3 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r3 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        if (r5 < r4) goto L_0x0082
                        r4 = 1
                        goto L_0x0083
                    L_0x0082:
                        r4 = 0
                    L_0x0083:
                        r3.mIABv9Supported = r4     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r3 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r3 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        r4 = 8
                        if (r5 < r4) goto L_0x0090
                        r4 = 1
                        goto L_0x0091
                    L_0x0090:
                        r4 = 0
                    L_0x0091:
                        r3.mIABv8Supported = r4     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r3 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r3 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        r4 = 6
                        if (r5 < r4) goto L_0x009c
                        goto L_0x009d
                    L_0x009c:
                        r9 = 0
                    L_0x009d:
                        r3.mIABv6Supported = r9     // Catch:{ Exception -> 0x00c4 }
                        if (r5 >= r0) goto L_0x00a9
                        java.lang.String r0 = "BillingClient"
                        java.lang.String r3 = "In-app billing API version 3 is not supported on this device."
                        com.android.billingclient.util.BillingHelper.logWarn(r0, r3)     // Catch:{ Exception -> 0x00c4 }
                    L_0x00a9:
                        if (r6 != 0) goto L_0x00b4
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r0 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        r3 = 2
                        r0.mClientState = r3     // Catch:{ Exception -> 0x00c4 }
                        goto L_0x00d9
                    L_0x00b4:
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r0 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        r0.mClientState = r1     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this     // Catch:{ Exception -> 0x00c4 }
                        com.android.billingclient.api.BillingClientImpl r0 = com.android.billingclient.api.BillingClientImpl.this     // Catch:{ Exception -> 0x00c4 }
                        r0.mService = r2     // Catch:{ Exception -> 0x00c4 }
                        goto L_0x00d9
                    L_0x00c3:
                        r6 = 3
                    L_0x00c4:
                        java.lang.String r0 = "BillingClient"
                        java.lang.String r3 = "Exception while checking if billing is supported; try to reconnect"
                        com.android.billingclient.util.BillingHelper.logWarn(r0, r3)
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this
                        com.android.billingclient.api.BillingClientImpl r0 = com.android.billingclient.api.BillingClientImpl.this
                        r0.mClientState = r1
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this
                        com.android.billingclient.api.BillingClientImpl r0 = com.android.billingclient.api.BillingClientImpl.this
                        r0.mService = r2
                    L_0x00d9:
                        if (r6 != 0) goto L_0x00e3
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this
                        com.android.billingclient.api.BillingResult r1 = com.android.billingclient.api.BillingResults.f83OK
                        r0.notifySetupResult(r1)
                        goto L_0x00ea
                    L_0x00e3:
                        com.android.billingclient.api.BillingClientImpl$BillingServiceConnection r0 = com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.this
                        com.android.billingclient.api.BillingResult r1 = com.android.billingclient.api.BillingResults.API_VERSION_NOT_V3
                        r0.notifySetupResult(r1)
                    L_0x00ea:
                        return r2
                    L_0x00eb:
                        r1 = move-exception
                        monitor-exit(r0)     // Catch:{ all -> 0x00eb }
                        goto L_0x00ef
                    L_0x00ee:
                        throw r1
                    L_0x00ef:
                        goto L_0x00ee
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.BillingServiceConnection.C09312.call():java.lang.Void");
                }
            }, 30000, new Runnable() {
                public void run() {
                    BillingClientImpl.this.mClientState = 0;
                    BillingClientImpl.this.mService = null;
                    BillingServiceConnection.this.notifySetupResult(BillingResults.SERVICE_TIMEOUT);
                }
            }) == null) {
                notifySetupResult(BillingClientImpl.this.getBillingResultForNullFutureResult());
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClientState {
        public static final int CLOSED = 3;
        public static final int CONNECTED = 2;
        public static final int CONNECTING = 1;
        public static final int DISCONNECTED = 0;
    }

    private static class PurchaseHistoryResult {
        private BillingResult mBillingResult;
        private List<PurchaseHistoryRecord> mPurchaseHistoryRecordList;

        PurchaseHistoryResult(BillingResult billingResult, List<PurchaseHistoryRecord> list) {
            this.mPurchaseHistoryRecordList = list;
            this.mBillingResult = billingResult;
        }

        /* access modifiers changed from: 0000 */
        public BillingResult getBillingResult() {
            return this.mBillingResult;
        }

        /* access modifiers changed from: 0000 */
        public List<PurchaseHistoryRecord> getPurchaseHistoryRecordList() {
            return this.mPurchaseHistoryRecordList;
        }
    }

    /* access modifiers changed from: 0000 */
    public void setExecutorService(ExecutorService executorService) {
        this.mExecutorService = executorService;
    }

    BillingClientImpl(Context context, int i, int i2, boolean z, PurchasesUpdatedListener purchasesUpdatedListener) {
        this(context, i, i2, z, purchasesUpdatedListener, BuildConfig.VERSION_NAME);
    }

    private BillingClientImpl(Activity activity, int i, int i2, boolean z, String str) {
        this(activity.getApplicationContext(), i, i2, z, new BillingClientNativeCallback(), str);
    }

    private BillingClientImpl(Context context, int i, int i2, boolean z, PurchasesUpdatedListener purchasesUpdatedListener, String str) {
        this.mClientState = 0;
        this.mUiThreadHandler = new Handler(Looper.getMainLooper());
        this.onPurchaseFinishedReceiver = new ResultReceiver(this.mUiThreadHandler) {
            public void onReceiveResult(int i, Bundle bundle) {
                PurchasesUpdatedListener listener = BillingClientImpl.this.mBroadcastManager.getListener();
                String str = BillingClientImpl.TAG;
                if (listener == null) {
                    BillingHelper.logWarn(str, "PurchasesUpdatedListener is null - no way to return the response.");
                    return;
                }
                listener.onPurchasesUpdated(BillingResult.newBuilder().setResponseCode(i).setDebugMessage(BillingHelper.getDebugMessageFromBundle(bundle, str)).build(), BillingHelper.extractPurchases(bundle));
            }
        };
        this.mApplicationContext = context.getApplicationContext();
        this.mChildDirected = i;
        this.mUnderAgeOfConsent = i2;
        this.mEnablePendingPurchases = z;
        this.mBroadcastManager = new BillingBroadcastManager(this.mApplicationContext, purchasesUpdatedListener);
        this.mQualifiedVersionNumber = str;
    }

    public BillingResult isFeatureSupported(String str) {
        if (!isReady()) {
            return BillingResults.SERVICE_DISCONNECTED;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -422092961:
                if (str.equals(FeatureType.SUBSCRIPTIONS_UPDATE)) {
                    c = 1;
                    break;
                }
                break;
            case 207616302:
                if (str.equals(FeatureType.PRICE_CHANGE_CONFIRMATION)) {
                    c = 4;
                    break;
                }
                break;
            case 292218239:
                if (str.equals(FeatureType.IN_APP_ITEMS_ON_VR)) {
                    c = 2;
                    break;
                }
                break;
            case 1219490065:
                if (str.equals(FeatureType.SUBSCRIPTIONS_ON_VR)) {
                    c = 3;
                    break;
                }
                break;
            case 1987365622:
                if (str.equals(FeatureType.SUBSCRIPTIONS)) {
                    c = 0;
                    break;
                }
                break;
        }
        if (c == 0) {
            return this.mSubscriptionsSupported ? BillingResults.f83OK : BillingResults.FEATURE_NOT_SUPPORTED;
        } else if (c == 1) {
            return this.mSubscriptionUpdateSupported ? BillingResults.f83OK : BillingResults.FEATURE_NOT_SUPPORTED;
        } else if (c == 2) {
            return isBillingSupportedOnVr("inapp");
        } else {
            if (c == 3) {
                return isBillingSupportedOnVr("subs");
            }
            if (c != 4) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unsupported feature: ");
                sb.append(str);
                BillingHelper.logWarn(TAG, sb.toString());
                return BillingResults.UNKNOWN_FEATURE;
            }
            return this.mIABv8Supported ? BillingResults.f83OK : BillingResults.FEATURE_NOT_SUPPORTED;
        }
    }

    public boolean isReady() {
        return (this.mClientState != 2 || this.mService == null || this.mServiceConnection == null) ? false : true;
    }

    public void startConnection(BillingClientStateListener billingClientStateListener) {
        boolean isReady = isReady();
        String str = TAG;
        if (isReady) {
            BillingHelper.logVerbose(str, "Service connection is valid. No need to re-initialize.");
            billingClientStateListener.onBillingSetupFinished(BillingResults.f83OK);
            return;
        }
        int i = this.mClientState;
        if (i == 1) {
            BillingHelper.logWarn(str, "Client is already in the process of connecting to billing service.");
            billingClientStateListener.onBillingSetupFinished(BillingResults.CLIENT_CONNECTING);
        } else if (i == 3) {
            BillingHelper.logWarn(str, "Client was already closed and can't be reused. Please create another instance.");
            billingClientStateListener.onBillingSetupFinished(BillingResults.SERVICE_DISCONNECTED);
        } else {
            this.mClientState = 1;
            this.mBroadcastManager.registerReceiver();
            BillingHelper.logVerbose(str, "Starting in-app billing setup.");
            this.mServiceConnection = new BillingServiceConnection(billingClientStateListener);
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            String str2 = "com.android.vending";
            intent.setPackage(str2);
            List queryIntentServices = this.mApplicationContext.getPackageManager().queryIntentServices(intent, 0);
            if (queryIntentServices != null && !queryIntentServices.isEmpty()) {
                ResolveInfo resolveInfo = (ResolveInfo) queryIntentServices.get(0);
                if (resolveInfo.serviceInfo != null) {
                    String str3 = resolveInfo.serviceInfo.packageName;
                    String str4 = resolveInfo.serviceInfo.name;
                    if (!str2.equals(str3) || str4 == null) {
                        BillingHelper.logWarn(str, "The device doesn't have valid Play Store.");
                    } else {
                        ComponentName componentName = new ComponentName(str3, str4);
                        Intent intent2 = new Intent(intent);
                        intent2.setComponent(componentName);
                        intent2.putExtra(BillingHelper.LIBRARY_VERSION_KEY, this.mQualifiedVersionNumber);
                        if (this.mApplicationContext.bindService(intent2, this.mServiceConnection, 1)) {
                            BillingHelper.logVerbose(str, "Service was bonded successfully.");
                            return;
                        }
                        BillingHelper.logWarn(str, "Connection to Billing service is blocked.");
                    }
                }
            }
            this.mClientState = 0;
            BillingHelper.logVerbose(str, "Billing service unavailable on device.");
            billingClientStateListener.onBillingSetupFinished(BillingResults.BILLING_UNAVAILABLE);
        }
    }

    private void startConnection(long j) {
        startConnection((BillingClientStateListener) new BillingClientNativeCallback(j));
    }

    public void endConnection() {
        String str = TAG;
        try {
            this.mBroadcastManager.destroy();
            if (this.mServiceConnection != null) {
                this.mServiceConnection.markDisconnectedAndCleanUp();
            }
            if (!(this.mServiceConnection == null || this.mService == null)) {
                BillingHelper.logVerbose(str, "Unbinding from service.");
                this.mApplicationContext.unbindService(this.mServiceConnection);
                this.mServiceConnection = null;
            }
            this.mService = null;
            if (this.mExecutorService != null) {
                this.mExecutorService.shutdownNow();
                this.mExecutorService = null;
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("There was an exception while ending connection: ");
            sb.append(e);
            BillingHelper.logWarn(str, sb.toString());
        } catch (Throwable th) {
            this.mClientState = 3;
            throw th;
        }
        this.mClientState = 3;
    }

    private void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, long j) {
        launchPriceChangeConfirmationFlow(activity, priceChangeFlowParams, (PriceChangeConfirmationListener) new BillingClientNativeCallback(j));
    }

    public void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, final PriceChangeConfirmationListener priceChangeConfirmationListener) {
        String str = BillingHelper.RESPONSE_SUBS_MANAGEMENT_INTENT_KEY;
        String str2 = "; try to reconnect";
        if (!isReady()) {
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(BillingResults.SERVICE_DISCONNECTED);
            return;
        }
        String str3 = "Please fix the input params. priceChangeFlowParams must contain valid sku.";
        String str4 = TAG;
        if (priceChangeFlowParams == null || priceChangeFlowParams.getSkuDetails() == null) {
            BillingHelper.logWarn(str4, str3);
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(BillingResults.NULL_SKU);
            return;
        }
        final String sku = priceChangeFlowParams.getSkuDetails().getSku();
        if (sku == null) {
            BillingHelper.logWarn(str4, str3);
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(BillingResults.NULL_SKU);
        } else if (!this.mIABv8Supported) {
            BillingHelper.logWarn(str4, "Current client doesn't support price change confirmation flow.");
            priceChangeConfirmationListener.onPriceChangeConfirmationResult(BillingResults.FEATURE_NOT_SUPPORTED);
        } else {
            final Bundle bundle = new Bundle();
            bundle.putString(BillingHelper.LIBRARY_VERSION_KEY, this.mQualifiedVersionNumber);
            bundle.putBoolean(BillingHelper.EXTRA_PARAM_KEY_SUBS_PRICE_CHANGE, true);
            try {
                Bundle bundle2 = (Bundle) executeAsync(new Callable<Bundle>() {
                    public Bundle call() throws Exception {
                        return BillingClientImpl.this.mService.getSubscriptionManagementIntent(8, BillingClientImpl.this.mApplicationContext.getPackageName(), sku, "subs", bundle);
                    }
                }, 5000, null).get(5000, TimeUnit.MILLISECONDS);
                int responseCodeFromBundle = BillingHelper.getResponseCodeFromBundle(bundle2, str4);
                BillingResult build = BillingResult.newBuilder().setResponseCode(responseCodeFromBundle).setDebugMessage(BillingHelper.getDebugMessageFromBundle(bundle2, str4)).build();
                if (responseCodeFromBundle != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to launch price change flow, error response code: ");
                    sb.append(responseCodeFromBundle);
                    BillingHelper.logWarn(str4, sb.toString());
                    priceChangeConfirmationListener.onPriceChangeConfirmationResult(build);
                    return;
                }
                C09223 r4 = new ResultReceiver(this.mUiThreadHandler) {
                    public void onReceiveResult(int i, Bundle bundle) {
                        priceChangeConfirmationListener.onPriceChangeConfirmationResult(BillingResult.newBuilder().setResponseCode(i).setDebugMessage(BillingHelper.getDebugMessageFromBundle(bundle, BillingClientImpl.TAG)).build());
                    }
                };
                Intent intent = new Intent(activity, ProxyBillingActivity.class);
                intent.putExtra(str, (PendingIntent) bundle2.getParcelable(str));
                intent.putExtra("result_receiver", r4);
                activity.startActivity(intent);
            } catch (CancellationException | TimeoutException unused) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Time out while launching Price Change Flow for sku: ");
                sb2.append(sku);
                sb2.append(str2);
                BillingHelper.logWarn(str4, sb2.toString());
                priceChangeConfirmationListener.onPriceChangeConfirmationResult(BillingResults.SERVICE_TIMEOUT);
            } catch (Exception unused2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Exception caught while launching Price Change Flow for sku: ");
                sb3.append(sku);
                sb3.append(str2);
                BillingHelper.logWarn(str4, sb3.toString());
                priceChangeConfirmationListener.onPriceChangeConfirmationResult(BillingResults.SERVICE_DISCONNECTED);
            }
        }
    }

    public BillingResult launchBillingFlow(Activity activity, final BillingFlowParams billingFlowParams) {
        Future future;
        String str = "BUY_INTENT";
        String str2 = "; try to reconnect";
        if (!isReady()) {
            return broadcastFailureAndReturnBillingResponse(BillingResults.SERVICE_DISCONNECTED);
        }
        final String skuType = billingFlowParams.getSkuType();
        final String sku = billingFlowParams.getSku();
        SkuDetails skuDetails = billingFlowParams.getSkuDetails();
        boolean z = true;
        boolean z2 = skuDetails != null && skuDetails.isRewarded();
        String str3 = TAG;
        if (sku == null) {
            BillingHelper.logWarn(str3, "Please fix the input params. SKU can't be null.");
            return broadcastFailureAndReturnBillingResponse(BillingResults.NULL_SKU);
        } else if (skuType == null) {
            BillingHelper.logWarn(str3, "Please fix the input params. SkuType can't be null.");
            return broadcastFailureAndReturnBillingResponse(BillingResults.NULL_SKU_TYPE);
        } else if (!skuType.equals("subs") || this.mSubscriptionsSupported) {
            if (billingFlowParams.getOldSku() == null) {
                z = false;
            }
            if (!z || this.mSubscriptionUpdateSupported) {
                String str4 = "Current client doesn't support extra params for buy intent.";
                if (billingFlowParams.hasExtraParams() && !this.mIABv6Supported) {
                    BillingHelper.logWarn(str3, str4);
                    return broadcastFailureAndReturnBillingResponse(BillingResults.EXTRA_PARAMS_NOT_SUPPORTED);
                } else if (!z2 || this.mIABv6Supported) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Constructing buy intent for ");
                    sb.append(sku);
                    sb.append(", item type: ");
                    sb.append(skuType);
                    BillingHelper.logVerbose(str3, sb.toString());
                    if (this.mIABv6Supported) {
                        final Bundle constructExtraParamsForLaunchBillingFlow = BillingHelper.constructExtraParamsForLaunchBillingFlow(billingFlowParams, this.mIABv9Supported, this.mEnablePendingPurchases, this.mQualifiedVersionNumber);
                        if (!skuDetails.getSkuDetailsToken().isEmpty()) {
                            constructExtraParamsForLaunchBillingFlow.putString(BillingHelper.EXTRA_PARAM_KEY_SKU_DETAILS_TOKEN, skuDetails.getSkuDetailsToken());
                        }
                        if (z2) {
                            constructExtraParamsForLaunchBillingFlow.putString(BillingFlowParams.EXTRA_PARAM_KEY_RSKU, skuDetails.rewardToken());
                            int i = this.mChildDirected;
                            if (i != 0) {
                                constructExtraParamsForLaunchBillingFlow.putInt(BillingFlowParams.EXTRA_PARAM_CHILD_DIRECTED, i);
                            }
                            int i2 = this.mUnderAgeOfConsent;
                            if (i2 != 0) {
                                constructExtraParamsForLaunchBillingFlow.putInt(BillingFlowParams.EXTRA_PARAM_UNDER_AGE_OF_CONSENT, i2);
                            }
                        }
                        final int i3 = this.mIABv9Supported ? 9 : billingFlowParams.getVrPurchaseFlow() ? 7 : 6;
                        final String str5 = sku;
                        C09234 r2 = new Callable<Bundle>() {
                            public Bundle call() throws Exception {
                                return BillingClientImpl.this.mService.getBuyIntentExtraParams(i3, BillingClientImpl.this.mApplicationContext.getPackageName(), str5, skuType, null, constructExtraParamsForLaunchBillingFlow);
                            }
                        };
                        future = executeAsync(r2, 5000, null);
                    } else if (z) {
                        future = executeAsync(new Callable<Bundle>() {
                            public Bundle call() throws Exception {
                                return BillingClientImpl.this.mService.getBuyIntentToReplaceSkus(5, BillingClientImpl.this.mApplicationContext.getPackageName(), Arrays.asList(new String[]{billingFlowParams.getOldSku()}), sku, "subs", null);
                            }
                        }, 5000, null);
                    } else {
                        future = executeAsync(new Callable<Bundle>() {
                            public Bundle call() throws Exception {
                                return BillingClientImpl.this.mService.getBuyIntent(3, BillingClientImpl.this.mApplicationContext.getPackageName(), sku, skuType, null);
                            }
                        }, 5000, null);
                    }
                    try {
                        Bundle bundle = (Bundle) future.get(5000, TimeUnit.MILLISECONDS);
                        int responseCodeFromBundle = BillingHelper.getResponseCodeFromBundle(bundle, str3);
                        String debugMessageFromBundle = BillingHelper.getDebugMessageFromBundle(bundle, str3);
                        if (responseCodeFromBundle != 0) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Unable to buy item, Error response code: ");
                            sb2.append(responseCodeFromBundle);
                            BillingHelper.logWarn(str3, sb2.toString());
                            return broadcastFailureAndReturnBillingResponse(BillingResult.newBuilder().setResponseCode(responseCodeFromBundle).setDebugMessage(debugMessageFromBundle).build());
                        }
                        Intent intent = new Intent(activity, ProxyBillingActivity.class);
                        intent.putExtra("result_receiver", this.onPurchaseFinishedReceiver);
                        intent.putExtra(str, (PendingIntent) bundle.getParcelable(str));
                        activity.startActivity(intent);
                        return BillingResults.f83OK;
                    } catch (CancellationException | TimeoutException unused) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Time out while launching billing flow: ; for sku: ");
                        sb3.append(sku);
                        sb3.append(str2);
                        BillingHelper.logWarn(str3, sb3.toString());
                        return broadcastFailureAndReturnBillingResponse(BillingResults.SERVICE_TIMEOUT);
                    } catch (Exception unused2) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Exception while launching billing flow: ; for sku: ");
                        sb4.append(sku);
                        sb4.append(str2);
                        BillingHelper.logWarn(str3, sb4.toString());
                        return broadcastFailureAndReturnBillingResponse(BillingResults.SERVICE_DISCONNECTED);
                    }
                } else {
                    BillingHelper.logWarn(str3, str4);
                    return broadcastFailureAndReturnBillingResponse(BillingResults.EXTRA_PARAMS_NOT_SUPPORTED);
                }
            } else {
                BillingHelper.logWarn(str3, "Current client doesn't support subscriptions update.");
                return broadcastFailureAndReturnBillingResponse(BillingResults.SUBSCRIPTIONS_UPDATE_NOT_SUPPORTED);
            }
        } else {
            BillingHelper.logWarn(str3, "Current client doesn't support subscriptions.");
            return broadcastFailureAndReturnBillingResponse(BillingResults.SUBSCRIPTIONS_NOT_SUPPORTED);
        }
    }

    private BillingResult broadcastFailureAndReturnBillingResponse(BillingResult billingResult) {
        this.mBroadcastManager.getListener().onPurchasesUpdated(billingResult, null);
        return billingResult;
    }

    private int launchBillingFlowCpp(Activity activity, BillingFlowParams billingFlowParams) {
        return launchBillingFlow(activity, billingFlowParams).getResponseCode();
    }

    public PurchasesResult queryPurchases(final String str) {
        if (!isReady()) {
            return new PurchasesResult(BillingResults.SERVICE_DISCONNECTED, null);
        }
        if (TextUtils.isEmpty(str)) {
            BillingHelper.logWarn(TAG, "Please provide a valid SKU type.");
            return new PurchasesResult(BillingResults.EMPTY_SKU_TYPE, null);
        }
        try {
            return (PurchasesResult) executeAsync(new Callable<PurchasesResult>() {
                public PurchasesResult call() throws Exception {
                    return BillingClientImpl.this.queryPurchasesInternal(str);
                }
            }, 5000, null).get(5000, TimeUnit.MILLISECONDS);
        } catch (CancellationException | TimeoutException unused) {
            return new PurchasesResult(BillingResults.SERVICE_TIMEOUT, null);
        } catch (Exception unused2) {
            return new PurchasesResult(BillingResults.INTERNAL_ERROR, null);
        }
    }

    private void queryPurchases(final String str, long j) {
        final BillingClientNativeCallback billingClientNativeCallback = new BillingClientNativeCallback(j);
        if (!isReady()) {
            billingClientNativeCallback.onQueryPurchasesResponse(BillingResults.SERVICE_DISCONNECTED, null);
        }
        if (executeAsync(new Callable<Void>() {
            public Void call() {
                final PurchasesResult access$400 = BillingClientImpl.this.queryPurchasesInternal(str);
                BillingClientImpl.this.postToUiThread(new Runnable() {
                    public void run() {
                        billingClientNativeCallback.onQueryPurchasesResponse(access$400.getBillingResult(), access$400.getPurchasesList());
                    }
                });
                return null;
            }
        }, 30000, new Runnable() {
            public void run() {
                billingClientNativeCallback.onQueryPurchasesResponse(BillingResults.SERVICE_TIMEOUT, null);
            }
        }) == null) {
            billingClientNativeCallback.onQueryPurchasesResponse(getBillingResultForNullFutureResult(), null);
        }
    }

    public void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams, final SkuDetailsResponseListener skuDetailsResponseListener) {
        if (!isReady()) {
            skuDetailsResponseListener.onSkuDetailsResponse(BillingResults.SERVICE_DISCONNECTED, null);
            return;
        }
        final String skuType = skuDetailsParams.getSkuType();
        final List skusList = skuDetailsParams.getSkusList();
        boolean isEmpty = TextUtils.isEmpty(skuType);
        String str = TAG;
        if (isEmpty) {
            BillingHelper.logWarn(str, "Please fix the input params. SKU type can't be empty.");
            skuDetailsResponseListener.onSkuDetailsResponse(BillingResults.EMPTY_SKU_TYPE, null);
        } else if (skusList == null) {
            BillingHelper.logWarn(str, "Please fix the input params. The list of SKUs can't be empty.");
            skuDetailsResponseListener.onSkuDetailsResponse(BillingResults.EMPTY_SKU_LIST, null);
        } else {
            if (executeAsync(new Callable<Void>() {
                public Void call() {
                    final SkuDetailsResult querySkuDetailsInternal = BillingClientImpl.this.querySkuDetailsInternal(skuType, skusList);
                    BillingClientImpl.this.postToUiThread(new Runnable() {
                        public void run() {
                            skuDetailsResponseListener.onSkuDetailsResponse(BillingResult.newBuilder().setResponseCode(querySkuDetailsInternal.getResponseCode()).setDebugMessage(querySkuDetailsInternal.getDebugMessage()).build(), querySkuDetailsInternal.getSkuDetailsList());
                        }
                    });
                    return null;
                }
            }, 30000, new Runnable() {
                public void run() {
                    skuDetailsResponseListener.onSkuDetailsResponse(BillingResults.SERVICE_TIMEOUT, null);
                }
            }) == null) {
                skuDetailsResponseListener.onSkuDetailsResponse(getBillingResultForNullFutureResult(), null);
            }
        }
    }

    private void querySkuDetailsAsync(String str, String[] strArr, long j) {
        querySkuDetailsAsync(SkuDetailsParams.newBuilder().setType(str).setSkusList(Arrays.asList(strArr)).build(), new BillingClientNativeCallback(j));
    }

    public void consumeAsync(final ConsumeParams consumeParams, final ConsumeResponseListener consumeResponseListener) {
        if (!isReady()) {
            consumeResponseListener.onConsumeResponse(BillingResults.SERVICE_DISCONNECTED, null);
            return;
        }
        if (executeAsync(new Callable<Void>() {
            public Void call() {
                BillingClientImpl.this.consumeInternal(consumeParams, consumeResponseListener);
                return null;
            }
        }, 30000, new Runnable() {
            public void run() {
                consumeResponseListener.onConsumeResponse(BillingResults.SERVICE_TIMEOUT, null);
            }
        }) == null) {
            consumeResponseListener.onConsumeResponse(getBillingResultForNullFutureResult(), null);
        }
    }

    private void consumeAsync(ConsumeParams consumeParams, long j) {
        consumeAsync(consumeParams, (ConsumeResponseListener) new BillingClientNativeCallback(j));
    }

    public void queryPurchaseHistoryAsync(final String str, final PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        if (!isReady()) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(BillingResults.SERVICE_DISCONNECTED, null);
            return;
        }
        if (executeAsync(new Callable<Void>() {
            public Void call() {
                final PurchaseHistoryResult access$700 = BillingClientImpl.this.queryPurchaseHistoryInternal(str);
                BillingClientImpl.this.postToUiThread(new Runnable() {
                    public void run() {
                        purchaseHistoryResponseListener.onPurchaseHistoryResponse(access$700.getBillingResult(), access$700.getPurchaseHistoryRecordList());
                    }
                });
                return null;
            }
        }, 30000, new Runnable() {
            public void run() {
                purchaseHistoryResponseListener.onPurchaseHistoryResponse(BillingResults.SERVICE_TIMEOUT, null);
            }
        }) == null) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(getBillingResultForNullFutureResult(), null);
        }
    }

    /* access modifiers changed from: private */
    public PurchaseHistoryResult queryPurchaseHistoryInternal(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Querying purchase history, item type: ");
        sb.append(str);
        String sb2 = sb.toString();
        String str2 = TAG;
        BillingHelper.logVerbose(str2, sb2);
        ArrayList arrayList = new ArrayList();
        Bundle constructExtraParamsForQueryPurchases = BillingHelper.constructExtraParamsForQueryPurchases(this.mIABv9Supported, this.mEnablePendingPurchases, this.mQualifiedVersionNumber);
        String str3 = null;
        do {
            try {
                if (!this.mIABv6Supported) {
                    BillingHelper.logWarn(str2, "getPurchaseHistory is not supported on current device");
                    return new PurchaseHistoryResult(BillingResults.GET_PURCHASE_HISTORY_NOT_SUPPORTED, null);
                }
                Bundle purchaseHistory = this.mService.getPurchaseHistory(6, this.mApplicationContext.getPackageName(), str, str3, constructExtraParamsForQueryPurchases);
                BillingResult checkPurchasesBundleValidity = PurchaseApiResponseChecker.checkPurchasesBundleValidity(purchaseHistory, str2, "getPurchaseHistory()");
                if (checkPurchasesBundleValidity != BillingResults.f83OK) {
                    return new PurchaseHistoryResult(checkPurchasesBundleValidity, null);
                }
                ArrayList stringArrayList = purchaseHistory.getStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST);
                ArrayList stringArrayList2 = purchaseHistory.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList stringArrayList3 = purchaseHistory.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                int i = 0;
                while (i < stringArrayList2.size()) {
                    String str4 = (String) stringArrayList2.get(i);
                    String str5 = (String) stringArrayList3.get(i);
                    String str6 = (String) stringArrayList.get(i);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Purchase record found for sku : ");
                    sb3.append(str6);
                    BillingHelper.logVerbose(str2, sb3.toString());
                    try {
                        PurchaseHistoryRecord purchaseHistoryRecord = new PurchaseHistoryRecord(str4, str5);
                        if (TextUtils.isEmpty(purchaseHistoryRecord.getPurchaseToken())) {
                            BillingHelper.logWarn(str2, "BUG: empty/null token!");
                        }
                        arrayList.add(purchaseHistoryRecord);
                        i++;
                    } catch (JSONException e) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Got an exception trying to decode the purchase: ");
                        sb4.append(e);
                        BillingHelper.logWarn(str2, sb4.toString());
                        return new PurchaseHistoryResult(BillingResults.INTERNAL_ERROR, null);
                    }
                }
                str3 = purchaseHistory.getString("INAPP_CONTINUATION_TOKEN");
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Continuation token: ");
                sb5.append(str3);
                BillingHelper.logVerbose(str2, sb5.toString());
            } catch (RemoteException e2) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Got exception trying to get purchase history: ");
                sb6.append(e2);
                sb6.append("; try to reconnect");
                BillingHelper.logWarn(str2, sb6.toString());
                return new PurchaseHistoryResult(BillingResults.SERVICE_DISCONNECTED, null);
            }
        } while (!TextUtils.isEmpty(str3));
        return new PurchaseHistoryResult(BillingResults.f83OK, arrayList);
    }

    private void queryPurchaseHistoryAsync(String str, long j) {
        queryPurchaseHistoryAsync(str, (PurchaseHistoryResponseListener) new BillingClientNativeCallback(j));
    }

    public void loadRewardedSku(final RewardLoadParams rewardLoadParams, final RewardResponseListener rewardResponseListener) {
        if (!this.mIABv6Supported) {
            rewardResponseListener.onRewardResponse(BillingResults.ITEM_UNAVAILABLE);
            return;
        }
        if (executeAsync(new Callable<Void>() {
            public Void call() {
                try {
                    Bundle buyIntentExtraParams = BillingClientImpl.this.mService.getBuyIntentExtraParams(6, BillingClientImpl.this.mApplicationContext.getPackageName(), rewardLoadParams.getSkuDetails().getSku(), rewardLoadParams.getSkuDetails().getType(), null, BillingHelper.constructExtraParamsForLoadRewardedSku(rewardLoadParams.getSkuDetails().rewardToken(), BillingClientImpl.this.mChildDirected, BillingClientImpl.this.mUnderAgeOfConsent, BillingClientImpl.this.mQualifiedVersionNumber));
                    Builder newBuilder = BillingResult.newBuilder();
                    String str = BillingClientImpl.TAG;
                    final BillingResult build = newBuilder.setResponseCode(BillingHelper.getResponseCodeFromBundle(buyIntentExtraParams, str)).setDebugMessage(BillingHelper.getDebugMessageFromBundle(buyIntentExtraParams, str)).build();
                    BillingClientImpl.this.postToUiThread(new Runnable() {
                        public void run() {
                            rewardResponseListener.onRewardResponse(build);
                        }
                    });
                    return null;
                } catch (Exception unused) {
                    BillingClientImpl.this.postToUiThread(new Runnable() {
                        public void run() {
                            rewardResponseListener.onRewardResponse(BillingResults.INTERNAL_ERROR);
                        }
                    });
                    return null;
                }
            }
        }, 30000, new Runnable() {
            public void run() {
                rewardResponseListener.onRewardResponse(BillingResults.SERVICE_TIMEOUT);
            }
        }) == null) {
            rewardResponseListener.onRewardResponse(getBillingResultForNullFutureResult());
        }
    }

    private void loadRewardedSku(RewardLoadParams rewardLoadParams, long j) {
        loadRewardedSku(rewardLoadParams, (RewardResponseListener) new BillingClientNativeCallback(j));
    }

    public void acknowledgePurchase(final AcknowledgePurchaseParams acknowledgePurchaseParams, final AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        if (!isReady()) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(BillingResults.SERVICE_DISCONNECTED);
        } else if (TextUtils.isEmpty(acknowledgePurchaseParams.getPurchaseToken())) {
            BillingHelper.logWarn(TAG, "Please provide a valid purchase token.");
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(BillingResults.INVALID_PURCHASE_TOKEN);
        } else if (!this.mIABv9Supported) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(BillingResults.API_VERSION_NOT_V9);
        } else {
            if (executeAsync(new Callable<Void>() {
                public Void call() {
                    try {
                        Bundle acknowledgePurchaseExtraParams = BillingClientImpl.this.mService.acknowledgePurchaseExtraParams(9, BillingClientImpl.this.mApplicationContext.getPackageName(), acknowledgePurchaseParams.getPurchaseToken(), BillingHelper.constructExtraParamsForAcknowledgePurchase(acknowledgePurchaseParams, BillingClientImpl.this.mQualifiedVersionNumber));
                        String str = BillingClientImpl.TAG;
                        final int responseCodeFromBundle = BillingHelper.getResponseCodeFromBundle(acknowledgePurchaseExtraParams, str);
                        final String debugMessageFromBundle = BillingHelper.getDebugMessageFromBundle(acknowledgePurchaseExtraParams, str);
                        BillingClientImpl.this.postToUiThread(new Runnable() {
                            public void run() {
                                acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(BillingResult.newBuilder().setResponseCode(responseCodeFromBundle).setDebugMessage(debugMessageFromBundle).build());
                            }
                        });
                        return null;
                    } catch (Exception e) {
                        BillingClientImpl.this.postToUiThread(new Runnable() {
                            public void run() {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Error acknowledge purchase; ex: ");
                                sb.append(e);
                                BillingHelper.logWarn(BillingClientImpl.TAG, sb.toString());
                                acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(BillingResults.SERVICE_DISCONNECTED);
                            }
                        });
                        return null;
                    }
                }
            }, 30000, new Runnable() {
                public void run() {
                    acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(BillingResults.SERVICE_TIMEOUT);
                }
            }) == null) {
                acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(getBillingResultForNullFutureResult());
            }
        }
    }

    private void acknowledgePurchase(AcknowledgePurchaseParams acknowledgePurchaseParams, long j) {
        acknowledgePurchase(acknowledgePurchaseParams, (AcknowledgePurchaseResponseListener) new BillingClientNativeCallback(j));
    }

    /* access modifiers changed from: private */
    public <T> Future<T> executeAsync(Callable<T> callable, long j, final Runnable runnable) {
        double d = (double) j;
        Double.isNaN(d);
        long j2 = (long) (d * 0.95d);
        if (this.mExecutorService == null) {
            this.mExecutorService = Executors.newFixedThreadPool(BillingHelper.NUMBER_OF_CORES);
        }
        try {
            final Future<T> submit = this.mExecutorService.submit(callable);
            this.mUiThreadHandler.postDelayed(new Runnable() {
                public void run() {
                    if (!submit.isDone() && !submit.isCancelled()) {
                        submit.cancel(true);
                        BillingHelper.logWarn(BillingClientImpl.TAG, "Async task is taking too long, cancel it!");
                        Runnable runnable = runnable;
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                }
            }, j2);
            return submit;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Async task throws exception ");
            sb.append(e);
            BillingHelper.logWarn(TAG, sb.toString());
            return null;
        }
    }

    private BillingResult isBillingSupportedOnVr(final String str) {
        try {
            return ((Integer) executeAsync(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return Integer.valueOf(BillingClientImpl.this.mService.isBillingSupportedExtraParams(7, BillingClientImpl.this.mApplicationContext.getPackageName(), str, BillingClientImpl.this.generateVrBundle()));
                }
            }, 5000, null).get(5000, TimeUnit.MILLISECONDS)).intValue() == 0 ? BillingResults.f83OK : BillingResults.FEATURE_NOT_SUPPORTED;
        } catch (Exception unused) {
            BillingHelper.logWarn(TAG, "Exception while checking if billing is supported; try to reconnect");
            return BillingResults.SERVICE_DISCONNECTED;
        }
    }

    /* access modifiers changed from: private */
    public Bundle generateVrBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("vr", true);
        return bundle;
    }

    /* access modifiers changed from: 0000 */
    public SkuDetailsResult querySkuDetailsInternal(String str, List<String> list) {
        Bundle bundle;
        String str2 = TAG;
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 20;
            ArrayList arrayList2 = new ArrayList(list.subList(i, i2 > size ? size : i2));
            Bundle bundle2 = new Bundle();
            bundle2.putStringArrayList("ITEM_ID_LIST", arrayList2);
            bundle2.putString(BillingHelper.LIBRARY_VERSION_KEY, this.mQualifiedVersionNumber);
            try {
                if (this.mIABv9Supported) {
                    bundle = this.mService.getSkuDetailsExtraParams(9, this.mApplicationContext.getPackageName(), str, bundle2, BillingHelper.constructExtraParamsForGetSkuDetails(this.mIABv9Supported, this.mEnablePendingPurchases, this.mQualifiedVersionNumber));
                    String str3 = str;
                } else {
                    bundle = this.mService.getSkuDetails(3, this.mApplicationContext.getPackageName(), str, bundle2);
                }
                if (bundle == null) {
                    BillingHelper.logWarn(str2, "querySkuDetailsAsync got null sku details list");
                    return new SkuDetailsResult(4, "Null sku details list", null);
                }
                String str4 = "DETAILS_LIST";
                if (!bundle.containsKey(str4)) {
                    int responseCodeFromBundle = BillingHelper.getResponseCodeFromBundle(bundle, str2);
                    String debugMessageFromBundle = BillingHelper.getDebugMessageFromBundle(bundle, str2);
                    if (responseCodeFromBundle != 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("getSkuDetails() failed. Response code: ");
                        sb.append(responseCodeFromBundle);
                        BillingHelper.logWarn(str2, sb.toString());
                        return new SkuDetailsResult(responseCodeFromBundle, debugMessageFromBundle, arrayList);
                    }
                    BillingHelper.logWarn(str2, "getSkuDetails() returned a bundle with neither an error nor a detail list.");
                    return new SkuDetailsResult(6, debugMessageFromBundle, arrayList);
                }
                ArrayList stringArrayList = bundle.getStringArrayList(str4);
                if (stringArrayList == null) {
                    String str5 = "querySkuDetailsAsync got null response list";
                    BillingHelper.logWarn(str2, str5);
                    return new SkuDetailsResult(4, str5, null);
                }
                int i3 = 0;
                while (i3 < stringArrayList.size()) {
                    try {
                        SkuDetails skuDetails = new SkuDetails((String) stringArrayList.get(i3));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Got sku details: ");
                        sb2.append(skuDetails);
                        BillingHelper.logVerbose(str2, sb2.toString());
                        arrayList.add(skuDetails);
                        i3++;
                    } catch (JSONException unused) {
                        BillingHelper.logWarn(str2, "Got a JSON exception trying to decode SkuDetails.");
                        return new SkuDetailsResult(6, "Error trying to decode SkuDetails.", null);
                    }
                }
                i = i2;
            } catch (Exception e) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("querySkuDetailsAsync got a remote exception (try to reconnect).");
                sb3.append(e);
                BillingHelper.logWarn(str2, sb3.toString());
                return new SkuDetailsResult(-1, "Service connection is disconnected.", null);
            }
        }
        return new SkuDetailsResult(0, "", arrayList);
    }

    /* access modifiers changed from: private */
    public PurchasesResult queryPurchasesInternal(String str) {
        Bundle bundle;
        StringBuilder sb = new StringBuilder();
        sb.append("Querying owned items, item type: ");
        sb.append(str);
        String sb2 = sb.toString();
        String str2 = TAG;
        BillingHelper.logVerbose(str2, sb2);
        ArrayList arrayList = new ArrayList();
        Bundle constructExtraParamsForQueryPurchases = BillingHelper.constructExtraParamsForQueryPurchases(this.mIABv9Supported, this.mEnablePendingPurchases, this.mQualifiedVersionNumber);
        String str3 = null;
        do {
            try {
                if (this.mIABv9Supported) {
                    bundle = this.mService.getPurchasesExtraParams(9, this.mApplicationContext.getPackageName(), str, str3, constructExtraParamsForQueryPurchases);
                } else {
                    bundle = this.mService.getPurchases(3, this.mApplicationContext.getPackageName(), str, str3);
                }
                BillingResult checkPurchasesBundleValidity = PurchaseApiResponseChecker.checkPurchasesBundleValidity(bundle, str2, "getPurchase()");
                if (checkPurchasesBundleValidity != BillingResults.f83OK) {
                    return new PurchasesResult(checkPurchasesBundleValidity, null);
                }
                ArrayList stringArrayList = bundle.getStringArrayList(BillingHelper.RESPONSE_INAPP_ITEM_LIST);
                ArrayList stringArrayList2 = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList stringArrayList3 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                int i = 0;
                while (i < stringArrayList2.size()) {
                    String str4 = (String) stringArrayList2.get(i);
                    String str5 = (String) stringArrayList3.get(i);
                    String str6 = (String) stringArrayList.get(i);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Sku is owned: ");
                    sb3.append(str6);
                    BillingHelper.logVerbose(str2, sb3.toString());
                    try {
                        Purchase purchase = new Purchase(str4, str5);
                        if (TextUtils.isEmpty(purchase.getPurchaseToken())) {
                            BillingHelper.logWarn(str2, "BUG: empty/null token!");
                        }
                        arrayList.add(purchase);
                        i++;
                    } catch (JSONException e) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Got an exception trying to decode the purchase: ");
                        sb4.append(e);
                        BillingHelper.logWarn(str2, sb4.toString());
                        return new PurchasesResult(BillingResults.INTERNAL_ERROR, null);
                    }
                }
                str3 = bundle.getString("INAPP_CONTINUATION_TOKEN");
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Continuation token: ");
                sb5.append(str3);
                BillingHelper.logVerbose(str2, sb5.toString());
            } catch (Exception e2) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Got exception trying to get purchases: ");
                sb6.append(e2);
                sb6.append("; try to reconnect");
                BillingHelper.logWarn(str2, sb6.toString());
                return new PurchasesResult(BillingResults.SERVICE_DISCONNECTED, null);
            }
        } while (!TextUtils.isEmpty(str3));
        return new PurchasesResult(BillingResults.f83OK, arrayList);
    }

    /* access modifiers changed from: private */
    public void postToUiThread(Runnable runnable) {
        if (!Thread.interrupted()) {
            this.mUiThreadHandler.post(runnable);
        }
    }

    /* access modifiers changed from: private */
    public void consumeInternal(ConsumeParams consumeParams, final ConsumeResponseListener consumeResponseListener) {
        String str;
        final int i;
        String str2 = TAG;
        final String purchaseToken = consumeParams.getPurchaseToken();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Consuming purchase with token: ");
            sb.append(purchaseToken);
            BillingHelper.logVerbose(str2, sb.toString());
            if (this.mIABv9Supported) {
                Bundle consumePurchaseExtraParams = this.mService.consumePurchaseExtraParams(9, this.mApplicationContext.getPackageName(), purchaseToken, BillingHelper.constructExtraParamsForConsume(consumeParams, this.mIABv9Supported, this.mQualifiedVersionNumber));
                int i2 = consumePurchaseExtraParams.getInt("RESPONSE_CODE");
                str = BillingHelper.getDebugMessageFromBundle(consumePurchaseExtraParams, str2);
                i = i2;
            } else {
                i = this.mService.consumePurchase(3, this.mApplicationContext.getPackageName(), purchaseToken);
                str = "";
            }
            final BillingResult build = BillingResult.newBuilder().setResponseCode(i).setDebugMessage(str).build();
            if (i == 0) {
                postToUiThread(new Runnable() {
                    public void run() {
                        BillingHelper.logVerbose(BillingClientImpl.TAG, "Successfully consumed purchase.");
                        consumeResponseListener.onConsumeResponse(build, purchaseToken);
                    }
                });
                return;
            }
            final ConsumeResponseListener consumeResponseListener2 = consumeResponseListener;
            final String str3 = purchaseToken;
            C092023 r1 = new Runnable() {
                public void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error consuming purchase with token. Response code: ");
                    sb.append(i);
                    BillingHelper.logWarn(BillingClientImpl.TAG, sb.toString());
                    consumeResponseListener2.onConsumeResponse(build, str3);
                }
            };
            postToUiThread(r1);
        } catch (Exception e) {
            postToUiThread(new Runnable() {
                public void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error consuming purchase; ex: ");
                    sb.append(e);
                    BillingHelper.logWarn(BillingClientImpl.TAG, sb.toString());
                    consumeResponseListener.onConsumeResponse(BillingResults.SERVICE_DISCONNECTED, purchaseToken);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public BillingResult getBillingResultForNullFutureResult() {
        int i = this.mClientState;
        return (i == 0 || i == 3) ? BillingResults.SERVICE_DISCONNECTED : BillingResults.INTERNAL_ERROR;
    }
}
