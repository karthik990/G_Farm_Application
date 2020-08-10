package p043io.grpc.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;
import p043io.grpc.CallOptions;
import p043io.grpc.ClientCall;
import p043io.grpc.ConnectivityState;
import p043io.grpc.ForwardingChannelBuilder;
import p043io.grpc.ManagedChannel;
import p043io.grpc.ManagedChannelBuilder;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.internal.GrpcUtil;

/* renamed from: io.grpc.android.AndroidChannelBuilder */
public final class AndroidChannelBuilder extends ForwardingChannelBuilder<AndroidChannelBuilder> {
    private static final String LOG_TAG = "AndroidChannelBuilder";
    @Nullable
    private static final Class<?> OKHTTP_CHANNEL_BUILDER_CLASS = findOkHttp();
    @Nullable
    private Context context;
    private final ManagedChannelBuilder<?> delegateBuilder;

    /* renamed from: io.grpc.android.AndroidChannelBuilder$AndroidChannel */
    static final class AndroidChannel extends ManagedChannel {
        /* access modifiers changed from: private */
        @Nullable
        public final ConnectivityManager connectivityManager;
        /* access modifiers changed from: private */
        @Nullable
        public final Context context;
        /* access modifiers changed from: private */
        public final ManagedChannel delegate;
        private final Object lock = new Object();
        private Runnable unregisterRunnable;

        /* renamed from: io.grpc.android.AndroidChannelBuilder$AndroidChannel$DefaultNetworkCallback */
        private class DefaultNetworkCallback extends NetworkCallback {
            private boolean isConnected;

            private DefaultNetworkCallback() {
                this.isConnected = false;
            }

            public void onAvailable(Network network) {
                if (this.isConnected) {
                    AndroidChannel.this.delegate.enterIdle();
                } else {
                    AndroidChannel.this.delegate.resetConnectBackoff();
                }
                this.isConnected = true;
            }

            public void onLost(Network network) {
                this.isConnected = false;
            }
        }

        /* renamed from: io.grpc.android.AndroidChannelBuilder$AndroidChannel$NetworkReceiver */
        private class NetworkReceiver extends BroadcastReceiver {
            private boolean isConnected;

            private NetworkReceiver() {
                this.isConnected = false;
            }

            public void onReceive(Context context, Intent intent) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                boolean z = this.isConnected;
                this.isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                if (this.isConnected && !z) {
                    AndroidChannel.this.delegate.resetConnectBackoff();
                }
            }
        }

        AndroidChannel(ManagedChannel managedChannel, @Nullable Context context2) {
            this.delegate = managedChannel;
            this.context = context2;
            if (context2 != null) {
                this.connectivityManager = (ConnectivityManager) context2.getSystemService("connectivity");
                try {
                    configureNetworkMonitoring();
                } catch (SecurityException e) {
                    Log.w(AndroidChannelBuilder.LOG_TAG, "Failed to configure network monitoring. Does app have ACCESS_NETWORK_STATE permission?", e);
                }
            } else {
                this.connectivityManager = null;
            }
        }

        private void configureNetworkMonitoring() {
            if (VERSION.SDK_INT < 24 || this.connectivityManager == null) {
                final NetworkReceiver networkReceiver = new NetworkReceiver();
                this.context.registerReceiver(networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.unregisterRunnable = new Runnable() {
                    public void run() {
                        AndroidChannel.this.context.unregisterReceiver(networkReceiver);
                    }
                };
                return;
            }
            final DefaultNetworkCallback defaultNetworkCallback = new DefaultNetworkCallback();
            this.connectivityManager.registerDefaultNetworkCallback(defaultNetworkCallback);
            this.unregisterRunnable = new Runnable() {
                public void run() {
                    AndroidChannel.this.connectivityManager.unregisterNetworkCallback(defaultNetworkCallback);
                }
            };
        }

        private void unregisterNetworkListener() {
            synchronized (this.lock) {
                if (this.unregisterRunnable != null) {
                    this.unregisterRunnable.run();
                    this.unregisterRunnable = null;
                }
            }
        }

        public ManagedChannel shutdown() {
            unregisterNetworkListener();
            return this.delegate.shutdown();
        }

        public boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        public boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        public ManagedChannel shutdownNow() {
            unregisterNetworkListener();
            return this.delegate.shutdownNow();
        }

        public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.delegate.awaitTermination(j, timeUnit);
        }

        public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
            return this.delegate.newCall(methodDescriptor, callOptions);
        }

        public String authority() {
            return this.delegate.authority();
        }

        public ConnectivityState getState(boolean z) {
            return this.delegate.getState(z);
        }

        public void notifyWhenStateChanged(ConnectivityState connectivityState, Runnable runnable) {
            this.delegate.notifyWhenStateChanged(connectivityState, runnable);
        }

        public void resetConnectBackoff() {
            this.delegate.resetConnectBackoff();
        }

        public void enterIdle() {
            this.delegate.enterIdle();
        }
    }

    private static final Class<?> findOkHttp() {
        try {
            return Class.forName("io.grpc.okhttp.OkHttpChannelBuilder");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static final AndroidChannelBuilder forTarget(String str) {
        return new AndroidChannelBuilder(str);
    }

    public static AndroidChannelBuilder forAddress(String str, int i) {
        return forTarget(GrpcUtil.authorityFromHostAndPort(str, i));
    }

    public static AndroidChannelBuilder fromBuilder(ManagedChannelBuilder<?> managedChannelBuilder) {
        return new AndroidChannelBuilder(managedChannelBuilder);
    }

    private AndroidChannelBuilder(String str) {
        Class<?> cls = OKHTTP_CHANNEL_BUILDER_CLASS;
        if (cls != null) {
            try {
                this.delegateBuilder = (ManagedChannelBuilder) cls.getMethod("forTarget", new Class[]{String.class}).invoke(null, new Object[]{str});
            } catch (Exception e) {
                throw new RuntimeException("Failed to create ManagedChannelBuilder", e);
            }
        } else {
            throw new UnsupportedOperationException("No ManagedChannelBuilder found on the classpath");
        }
    }

    private AndroidChannelBuilder(ManagedChannelBuilder<?> managedChannelBuilder) {
        this.delegateBuilder = (ManagedChannelBuilder) Preconditions.checkNotNull(managedChannelBuilder, "delegateBuilder");
    }

    public AndroidChannelBuilder context(Context context2) {
        this.context = context2;
        return this;
    }

    @Deprecated
    public AndroidChannelBuilder transportExecutor(@Nullable Executor executor) {
        try {
            OKHTTP_CHANNEL_BUILDER_CLASS.getMethod("transportExecutor", new Class[]{Executor.class}).invoke(this.delegateBuilder, new Object[]{executor});
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke transportExecutor on delegate builder", e);
        }
    }

    @Deprecated
    public AndroidChannelBuilder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        try {
            OKHTTP_CHANNEL_BUILDER_CLASS.getMethod("sslSocketFactory", new Class[]{SSLSocketFactory.class}).invoke(this.delegateBuilder, new Object[]{sSLSocketFactory});
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke sslSocketFactory on delegate builder", e);
        }
    }

    @Deprecated
    public AndroidChannelBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        try {
            OKHTTP_CHANNEL_BUILDER_CLASS.getMethod("scheduledExecutorService", new Class[]{ScheduledExecutorService.class}).invoke(this.delegateBuilder, new Object[]{scheduledExecutorService});
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke scheduledExecutorService on delegate builder", e);
        }
    }

    /* access modifiers changed from: protected */
    public ManagedChannelBuilder<?> delegate() {
        return this.delegateBuilder;
    }

    public ManagedChannel build() {
        return new AndroidChannel(this.delegateBuilder.build(), this.context);
    }
}
