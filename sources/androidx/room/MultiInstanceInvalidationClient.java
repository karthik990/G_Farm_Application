package androidx.room;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.room.IMultiInstanceInvalidationCallback.Stub;
import androidx.room.InvalidationTracker.Observer;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MultiInstanceInvalidationClient {
    final IMultiInstanceInvalidationCallback mCallback = new Stub() {
        public void onInvalidation(final String[] strArr) {
            MultiInstanceInvalidationClient.this.mExecutor.execute(new Runnable() {
                public void run() {
                    MultiInstanceInvalidationClient.this.mInvalidationTracker.notifyObserversByTableNames(strArr);
                }
            });
        }
    };
    int mClientId;
    Context mContext;
    final Executor mExecutor;
    final InvalidationTracker mInvalidationTracker;
    final String mName;
    final Observer mObserver;
    final Runnable mRemoveObserverRunnable = new Runnable() {
        public void run() {
            MultiInstanceInvalidationClient.this.mInvalidationTracker.removeObserver(MultiInstanceInvalidationClient.this.mObserver);
        }
    };
    IMultiInstanceInvalidationService mService;
    final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MultiInstanceInvalidationClient.this.mService = IMultiInstanceInvalidationService.Stub.asInterface(iBinder);
            MultiInstanceInvalidationClient.this.mExecutor.execute(MultiInstanceInvalidationClient.this.mSetUpRunnable);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MultiInstanceInvalidationClient.this.mExecutor.execute(MultiInstanceInvalidationClient.this.mRemoveObserverRunnable);
            MultiInstanceInvalidationClient multiInstanceInvalidationClient = MultiInstanceInvalidationClient.this;
            multiInstanceInvalidationClient.mService = null;
            multiInstanceInvalidationClient.mContext = null;
        }
    };
    final Runnable mSetUpRunnable = new Runnable() {
        public void run() {
            try {
                IMultiInstanceInvalidationService iMultiInstanceInvalidationService = MultiInstanceInvalidationClient.this.mService;
                if (iMultiInstanceInvalidationService != null) {
                    MultiInstanceInvalidationClient.this.mClientId = iMultiInstanceInvalidationService.registerCallback(MultiInstanceInvalidationClient.this.mCallback, MultiInstanceInvalidationClient.this.mName);
                    MultiInstanceInvalidationClient.this.mInvalidationTracker.addObserver(MultiInstanceInvalidationClient.this.mObserver);
                }
            } catch (RemoteException e) {
                Log.w("ROOM", "Cannot register multi-instance invalidation callback", e);
            }
        }
    };
    final AtomicBoolean mStopped = new AtomicBoolean(false);
    private final Runnable mTearDownRunnable = new Runnable() {
        public void run() {
            MultiInstanceInvalidationClient.this.mInvalidationTracker.removeObserver(MultiInstanceInvalidationClient.this.mObserver);
            try {
                IMultiInstanceInvalidationService iMultiInstanceInvalidationService = MultiInstanceInvalidationClient.this.mService;
                if (iMultiInstanceInvalidationService != null) {
                    iMultiInstanceInvalidationService.unregisterCallback(MultiInstanceInvalidationClient.this.mCallback, MultiInstanceInvalidationClient.this.mClientId);
                }
            } catch (RemoteException e) {
                Log.w("ROOM", "Cannot unregister multi-instance invalidation callback", e);
            }
            if (MultiInstanceInvalidationClient.this.mContext != null) {
                MultiInstanceInvalidationClient.this.mContext.unbindService(MultiInstanceInvalidationClient.this.mServiceConnection);
                MultiInstanceInvalidationClient.this.mContext = null;
            }
        }
    };

    MultiInstanceInvalidationClient(Context context, String str, InvalidationTracker invalidationTracker, Executor executor) {
        this.mContext = context.getApplicationContext();
        this.mName = str;
        this.mInvalidationTracker = invalidationTracker;
        this.mExecutor = executor;
        this.mObserver = new Observer(invalidationTracker.mTableNames) {
            /* access modifiers changed from: 0000 */
            public boolean isRemote() {
                return true;
            }

            public void onInvalidated(Set<String> set) {
                if (!MultiInstanceInvalidationClient.this.mStopped.get()) {
                    try {
                        MultiInstanceInvalidationClient.this.mService.broadcastInvalidation(MultiInstanceInvalidationClient.this.mClientId, (String[]) set.toArray(new String[0]));
                    } catch (RemoteException e) {
                        Log.w("ROOM", "Cannot broadcast invalidation", e);
                    }
                }
            }
        };
        this.mContext.bindService(new Intent(this.mContext, MultiInstanceInvalidationService.class), this.mServiceConnection, 1);
    }

    /* access modifiers changed from: 0000 */
    public void stop() {
        if (this.mStopped.compareAndSet(false, true)) {
            this.mExecutor.execute(this.mTearDownRunnable);
        }
    }
}
