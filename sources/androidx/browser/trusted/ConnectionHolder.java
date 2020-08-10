package androidx.browser.trusted;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.customtabs.trusted.ITrustedWebActivityService.Stub;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;

class ConnectionHolder implements ServiceConnection {
    private static final int STATE_AWAITING_CONNECTION = 0;
    private static final int STATE_CANCELLED = 3;
    private static final int STATE_CONNECTED = 1;
    private static final int STATE_DISCONNECTED = 2;
    private Exception mCancellationException;
    private final Runnable mCloseRunnable;
    private List<Completer<TrustedWebActivityServiceConnection>> mCompleters;
    private TrustedWebActivityServiceConnection mService;
    private int mState;
    private final WrapperFactory mWrapperFactory;

    static class WrapperFactory {
        WrapperFactory() {
        }

        /* access modifiers changed from: 0000 */
        public TrustedWebActivityServiceConnection create(ComponentName componentName, IBinder iBinder) {
            return new TrustedWebActivityServiceConnection(Stub.asInterface(iBinder), componentName);
        }
    }

    ConnectionHolder(Runnable runnable) {
        this(runnable, new WrapperFactory());
    }

    ConnectionHolder(Runnable runnable, WrapperFactory wrapperFactory) {
        this.mState = 0;
        this.mCompleters = new ArrayList();
        this.mCloseRunnable = runnable;
        this.mWrapperFactory = wrapperFactory;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mService = this.mWrapperFactory.create(componentName, iBinder);
        for (Completer completer : this.mCompleters) {
            completer.set(this.mService);
        }
        this.mCompleters.clear();
        this.mState = 1;
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.mService = null;
        this.mCloseRunnable.run();
        this.mState = 2;
    }

    public void cancel(Exception exc) {
        for (Completer exception : this.mCompleters) {
            exception.setException(exc);
        }
        this.mCompleters.clear();
        this.mCloseRunnable.run();
        this.mState = 3;
        this.mCancellationException = exc;
    }

    public ListenableFuture<TrustedWebActivityServiceConnection> getServiceWrapper() {
        return CallbackToFutureAdapter.getFuture(new Resolver() {
            public final Object attachCompleter(Completer completer) {
                return ConnectionHolder.this.lambda$getServiceWrapper$0$ConnectionHolder(completer);
            }
        });
    }

    public /* synthetic */ Object lambda$getServiceWrapper$0$ConnectionHolder(Completer completer) throws Exception {
        int i = this.mState;
        if (i == 0) {
            this.mCompleters.add(completer);
        } else if (i == 1) {
            TrustedWebActivityServiceConnection trustedWebActivityServiceConnection = this.mService;
            if (trustedWebActivityServiceConnection != null) {
                completer.set(trustedWebActivityServiceConnection);
            } else {
                throw new IllegalStateException("ConnectionHolder state is incorrect.");
            }
        } else if (i == 2) {
            throw new IllegalStateException("Service has been disconnected.");
        } else if (i != 3) {
            throw new IllegalStateException("Connection state is invalid");
        } else {
            throw this.mCancellationException;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ConnectionHolder, state = ");
        sb.append(this.mState);
        return sb.toString();
    }
}
