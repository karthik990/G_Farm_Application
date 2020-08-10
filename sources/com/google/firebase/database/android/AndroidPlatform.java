package com.google.firebase.database.android;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseApp.BackgroundStateChangeListener;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.connection.ConnectionContext;
import com.google.firebase.database.connection.HostInfo;
import com.google.firebase.database.connection.PersistentConnection;
import com.google.firebase.database.connection.PersistentConnection.Delegate;
import com.google.firebase.database.connection.PersistentConnectionImpl;
import com.google.firebase.database.core.EventTarget;
import com.google.firebase.database.core.Platform;
import com.google.firebase.database.core.RunLoop;
import com.google.firebase.database.core.persistence.DefaultPersistenceManager;
import com.google.firebase.database.core.persistence.LRUCachePolicy;
import com.google.firebase.database.core.persistence.PersistenceManager;
import com.google.firebase.database.core.utilities.DefaultRunLoop;
import com.google.firebase.database.logging.AndroidLogger;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import com.google.firebase.database.logging.Logger.Level;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class AndroidPlatform implements Platform {
    private static final String APP_IN_BACKGROUND_INTERRUPT_REASON = "app_in_background";
    /* access modifiers changed from: private */
    public final Context applicationContext;
    private final Set<String> createdPersistenceCaches = new HashSet();
    private final FirebaseApp firebaseApp;

    public AndroidPlatform(FirebaseApp firebaseApp2) {
        this.firebaseApp = firebaseApp2;
        FirebaseApp firebaseApp3 = this.firebaseApp;
        if (firebaseApp3 != null) {
            this.applicationContext = firebaseApp3.getApplicationContext();
            return;
        }
        String str = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        String str2 = "FirebaseDatabase";
        Log.e(str2, str);
        Log.e(str2, "ERROR: You must call FirebaseApp.initializeApp() before using Firebase Database.");
        Log.e(str2, str);
        throw new RuntimeException("You need to call FirebaseApp.initializeApp() before using Firebase Database.");
    }

    public EventTarget newEventTarget(com.google.firebase.database.core.Context context) {
        return new AndroidEventTarget();
    }

    public RunLoop newRunLoop(com.google.firebase.database.core.Context context) {
        final LogWrapper logger = context.getLogger("RunLoop");
        return new DefaultRunLoop() {
            public void handleException(final Throwable th) {
                final String messageForException = DefaultRunLoop.messageForException(th);
                logger.error(messageForException, th);
                new Handler(AndroidPlatform.this.applicationContext.getMainLooper()).post(new Runnable() {
                    public void run() {
                        throw new RuntimeException(messageForException, th);
                    }
                });
                getExecutorService().shutdownNow();
            }
        };
    }

    public PersistentConnection newPersistentConnection(com.google.firebase.database.core.Context context, ConnectionContext connectionContext, HostInfo hostInfo, Delegate delegate) {
        final PersistentConnectionImpl persistentConnectionImpl = new PersistentConnectionImpl(connectionContext, hostInfo, delegate);
        this.firebaseApp.addBackgroundStateChangeListener(new BackgroundStateChangeListener() {
            public void onBackgroundStateChanged(boolean z) {
                String str = AndroidPlatform.APP_IN_BACKGROUND_INTERRUPT_REASON;
                if (z) {
                    persistentConnectionImpl.interrupt(str);
                } else {
                    persistentConnectionImpl.resume(str);
                }
            }
        });
        return persistentConnectionImpl;
    }

    public Logger newLogger(com.google.firebase.database.core.Context context, Level level, List<String> list) {
        return new AndroidLogger(level, list);
    }

    public String getUserAgent(com.google.firebase.database.core.Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(VERSION.SDK_INT);
        sb.append("/Android");
        return sb.toString();
    }

    public String getPlatformVersion() {
        StringBuilder sb = new StringBuilder();
        sb.append("android-");
        sb.append(FirebaseDatabase.getSdkVersion());
        return sb.toString();
    }

    public PersistenceManager createPersistenceManager(com.google.firebase.database.core.Context context, String str) {
        String sessionPersistenceKey = context.getSessionPersistenceKey();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(sessionPersistenceKey);
        String sb2 = sb.toString();
        if (!this.createdPersistenceCaches.contains(sb2)) {
            this.createdPersistenceCaches.add(sb2);
            return new DefaultPersistenceManager(context, new SqlPersistenceStorageEngine(this.applicationContext, context, sb2), new LRUCachePolicy(context.getPersistenceCacheSizeBytes()));
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("SessionPersistenceKey '");
        sb3.append(sessionPersistenceKey);
        sb3.append("' has already been used.");
        throw new DatabaseException(sb3.toString());
    }

    public File getSSLCacheDirectory() {
        return this.applicationContext.getApplicationContext().getDir("sslcache", 0);
    }
}
