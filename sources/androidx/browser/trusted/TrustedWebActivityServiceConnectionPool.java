package androidx.browser.trusted;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

public final class TrustedWebActivityServiceConnectionPool {
    private static final String TAG = "TWAConnectionPool";
    private final Map<Uri, ConnectionHolder> mConnections = new HashMap();
    private final Context mContext;

    static class BindToServiceAsyncTask extends AsyncTask<Void, Void, Exception> {
        private final Context mAppContext;
        private final ConnectionHolder mConnection;
        private final Intent mIntent;

        BindToServiceAsyncTask(Context context, Intent intent, ConnectionHolder connectionHolder) {
            this.mAppContext = context.getApplicationContext();
            this.mIntent = intent;
            this.mConnection = connectionHolder;
        }

        /* access modifiers changed from: protected */
        public Exception doInBackground(Void... voidArr) {
            try {
                if (this.mAppContext.bindService(this.mIntent, this.mConnection, 1)) {
                    return null;
                }
                this.mAppContext.unbindService(this.mConnection);
                return new IllegalStateException("Could not bind to the service");
            } catch (SecurityException e) {
                Log.w(TrustedWebActivityServiceConnectionPool.TAG, "SecurityException while binding.", e);
                return e;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Exception exc) {
            if (exc != null) {
                this.mConnection.cancel(exc);
            }
        }
    }

    private TrustedWebActivityServiceConnectionPool(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static TrustedWebActivityServiceConnectionPool create(Context context) {
        return new TrustedWebActivityServiceConnectionPool(context);
    }

    public ListenableFuture<TrustedWebActivityServiceConnection> connect(Uri uri, Set<Token> set, Executor executor) {
        ConnectionHolder connectionHolder = (ConnectionHolder) this.mConnections.get(uri);
        if (connectionHolder != null) {
            return connectionHolder.getServiceWrapper();
        }
        Intent createServiceIntent = createServiceIntent(this.mContext, uri, set, true);
        if (createServiceIntent == null) {
            return FutureUtils.immediateFailedFuture(new IllegalArgumentException("No service exists for scope"));
        }
        ConnectionHolder connectionHolder2 = new ConnectionHolder(new Runnable(uri) {
            private final /* synthetic */ Uri f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                TrustedWebActivityServiceConnectionPool.this.lambda$connect$0$TrustedWebActivityServiceConnectionPool(this.f$1);
            }
        });
        this.mConnections.put(uri, connectionHolder2);
        new BindToServiceAsyncTask(this.mContext, createServiceIntent, connectionHolder2).executeOnExecutor(executor, new Void[0]);
        return connectionHolder2.getServiceWrapper();
    }

    public /* synthetic */ void lambda$connect$0$TrustedWebActivityServiceConnectionPool(Uri uri) {
        ConnectionHolder connectionHolder = (ConnectionHolder) this.mConnections.remove(uri);
    }

    public boolean serviceExistsForScope(Uri uri, Set<Token> set) {
        boolean z = true;
        if (this.mConnections.get(uri) != null) {
            return true;
        }
        if (createServiceIntent(this.mContext, uri, set, false) == null) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public void unbindAllConnections() {
        for (ConnectionHolder unbindService : this.mConnections.values()) {
            this.mContext.unbindService(unbindService);
        }
        this.mConnections.clear();
    }

    private Intent createServiceIntent(Context context, Uri uri, Set<Token> set, boolean z) {
        if (set == null || set.size() == 0) {
            return null;
        }
        Intent intent = new Intent();
        intent.setData(uri);
        intent.setAction("android.intent.action.VIEW");
        String str = null;
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 65536)) {
            String str2 = resolveInfo.activityInfo.packageName;
            Iterator it = set.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((Token) it.next()).matches(str2, context.getPackageManager())) {
                        str = str2;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        String str3 = TAG;
        if (str == null) {
            if (z) {
                StringBuilder sb = new StringBuilder();
                sb.append("No TWA candidates for ");
                sb.append(uri);
                sb.append(" have been registered.");
                Log.w(str3, sb.toString());
            }
            return null;
        }
        Intent intent2 = new Intent();
        intent2.setPackage(str);
        intent2.setAction(TrustedWebActivityService.ACTION_TRUSTED_WEB_ACTIVITY_SERVICE);
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent2, 131072);
        if (resolveService == null) {
            if (z) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Could not find TWAService for ");
                sb2.append(str);
                Log.w(str3, sb2.toString());
            }
            return null;
        }
        if (z) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Found ");
            sb3.append(resolveService.serviceInfo.name);
            sb3.append(" to handle request for ");
            sb3.append(uri);
            Log.i(str3, sb3.toString());
        }
        Intent intent3 = new Intent();
        intent3.setComponent(new ComponentName(str, resolveService.serviceInfo.name));
        return intent3;
    }
}
