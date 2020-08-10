package com.google.firebase.database.core;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.android.AndroidPlatform;
import com.google.firebase.database.connection.ConnectionAuthTokenProvider;
import com.google.firebase.database.connection.ConnectionContext;
import com.google.firebase.database.connection.HostInfo;
import com.google.firebase.database.connection.PersistentConnection;
import com.google.firebase.database.connection.PersistentConnection.Delegate;
import com.google.firebase.database.core.persistence.NoopPersistenceManager;
import com.google.firebase.database.core.persistence.PersistenceManager;
import com.google.firebase.database.core.utilities.DefaultRunLoop;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import com.google.firebase.database.logging.Logger.Level;
import java.io.File;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.http.client.config.CookieSpecs;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Context {
    private static final long DEFAULT_CACHE_SIZE = 10485760;
    protected AuthTokenProvider authTokenProvider;
    protected long cacheSize = DEFAULT_CACHE_SIZE;
    protected EventTarget eventTarget;
    protected FirebaseApp firebaseApp;
    private PersistenceManager forcedPersistenceManager;
    private boolean frozen = false;
    protected Level logLevel = Level.INFO;
    protected List<String> loggedComponents;
    protected Logger logger;
    protected boolean persistenceEnabled;
    protected String persistenceKey;
    private Platform platform;
    protected RunLoop runLoop;
    private boolean stopped = false;
    protected String userAgent;

    private Platform getPlatform() {
        if (this.platform == null) {
            initializeAndroidPlatform();
        }
        return this.platform;
    }

    private synchronized void initializeAndroidPlatform() {
        this.platform = new AndroidPlatform(this.firebaseApp);
    }

    public boolean isFrozen() {
        return this.frozen;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void freeze() {
        if (!this.frozen) {
            this.frozen = true;
            initServices();
        }
    }

    public void requireStarted() {
        if (this.stopped) {
            restartServices();
            this.stopped = false;
        }
    }

    private void initServices() {
        ensureLogger();
        getPlatform();
        ensureUserAgent();
        ensureEventTarget();
        ensureRunLoop();
        ensureSessionIdentifier();
        ensureAuthTokenProvider();
    }

    private void restartServices() {
        this.eventTarget.restart();
        this.runLoop.restart();
    }

    /* access modifiers changed from: 0000 */
    public void stop() {
        this.stopped = true;
        this.eventTarget.shutdown();
        this.runLoop.shutdown();
    }

    /* access modifiers changed from: protected */
    public void assertUnfrozen() {
        if (isFrozen()) {
            throw new DatabaseException("Modifications to DatabaseConfig objects must occur before they are in use");
        }
    }

    public List<String> getOptDebugLogComponents() {
        return this.loggedComponents;
    }

    public Level getLogLevel() {
        return this.logLevel;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public LogWrapper getLogger(String str) {
        return new LogWrapper(this.logger, str);
    }

    public LogWrapper getLogger(String str, String str2) {
        return new LogWrapper(this.logger, str, str2);
    }

    public ConnectionContext getConnectionContext() {
        ConnectionContext connectionContext = new ConnectionContext(getLogger(), wrapAuthTokenProvider(getAuthTokenProvider(), getExecutorService()), getExecutorService(), isPersistenceEnabled(), FirebaseDatabase.getSdkVersion(), getUserAgent(), getSSLCacheDirectory().getAbsolutePath());
        return connectionContext;
    }

    /* access modifiers changed from: 0000 */
    public PersistenceManager getPersistenceManager(String str) {
        PersistenceManager persistenceManager = this.forcedPersistenceManager;
        if (persistenceManager != null) {
            return persistenceManager;
        }
        if (!this.persistenceEnabled) {
            return new NoopPersistenceManager();
        }
        PersistenceManager createPersistenceManager = this.platform.createPersistenceManager(this, str);
        if (createPersistenceManager != null) {
            return createPersistenceManager;
        }
        throw new IllegalArgumentException("You have enabled persistence, but persistence is not supported on this platform.");
    }

    public boolean isPersistenceEnabled() {
        return this.persistenceEnabled;
    }

    public long getPersistenceCacheSizeBytes() {
        return this.cacheSize;
    }

    /* access modifiers changed from: 0000 */
    public void forcePersistenceManager(PersistenceManager persistenceManager) {
        this.forcedPersistenceManager = persistenceManager;
    }

    public EventTarget getEventTarget() {
        return this.eventTarget;
    }

    public RunLoop getRunLoop() {
        return this.runLoop;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String getPlatformVersion() {
        return getPlatform().getPlatformVersion();
    }

    public String getSessionPersistenceKey() {
        return this.persistenceKey;
    }

    public AuthTokenProvider getAuthTokenProvider() {
        return this.authTokenProvider;
    }

    public PersistentConnection newPersistentConnection(HostInfo hostInfo, Delegate delegate) {
        return getPlatform().newPersistentConnection(this, getConnectionContext(), hostInfo, delegate);
    }

    private ScheduledExecutorService getExecutorService() {
        RunLoop runLoop2 = getRunLoop();
        if (runLoop2 instanceof DefaultRunLoop) {
            return ((DefaultRunLoop) runLoop2).getExecutorService();
        }
        throw new RuntimeException("Custom run loops are not supported!");
    }

    private void ensureLogger() {
        if (this.logger == null) {
            this.logger = getPlatform().newLogger(this, this.logLevel, this.loggedComponents);
        }
    }

    private void ensureRunLoop() {
        if (this.runLoop == null) {
            this.runLoop = this.platform.newRunLoop(this);
        }
    }

    private void ensureEventTarget() {
        if (this.eventTarget == null) {
            this.eventTarget = getPlatform().newEventTarget(this);
        }
    }

    private void ensureUserAgent() {
        if (this.userAgent == null) {
            this.userAgent = buildUserAgent(getPlatform().getUserAgent(this));
        }
    }

    private void ensureAuthTokenProvider() {
        Preconditions.checkNotNull(this.authTokenProvider, "You must register an authTokenProvider before initializing Context.");
    }

    private void ensureSessionIdentifier() {
        if (this.persistenceKey == null) {
            this.persistenceKey = CookieSpecs.DEFAULT;
        }
    }

    private String buildUserAgent(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Firebase/");
        sb.append("5");
        String str2 = "/";
        sb.append(str2);
        sb.append(FirebaseDatabase.getSdkVersion());
        sb.append(str2);
        sb.append(str);
        return sb.toString();
    }

    private static ConnectionAuthTokenProvider wrapAuthTokenProvider(AuthTokenProvider authTokenProvider2, ScheduledExecutorService scheduledExecutorService) {
        return Context$$Lambda$1.lambdaFactory$(authTokenProvider2, scheduledExecutorService);
    }

    public File getSSLCacheDirectory() {
        return getPlatform().getSSLCacheDirectory();
    }
}
