package com.google.firebase.database;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.Logger.Level;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoInfo;
import com.google.firebase.database.core.RepoManager;
import com.google.firebase.database.core.utilities.ParsedUrl;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class FirebaseDatabase {
    private static final String SDK_VERSION = "3.0.0";
    private final FirebaseApp app;
    private final DatabaseConfig config;
    /* access modifiers changed from: private */
    public Repo repo;
    private final RepoInfo repoInfo;

    public static String getSdkVersion() {
        return SDK_VERSION;
    }

    public static FirebaseDatabase getInstance() {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, instance.getOptions().getDatabaseUrl());
        }
        throw new DatabaseException("You must call FirebaseApp.initialize() first.");
    }

    public static FirebaseDatabase getInstance(String str) {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, str);
        }
        throw new DatabaseException("You must call FirebaseApp.initialize() first.");
    }

    public static FirebaseDatabase getInstance(FirebaseApp firebaseApp) {
        return getInstance(firebaseApp, firebaseApp.getOptions().getDatabaseUrl());
    }

    public static synchronized FirebaseDatabase getInstance(FirebaseApp firebaseApp, String str) {
        FirebaseDatabase firebaseDatabase;
        synchronized (FirebaseDatabase.class) {
            if (!TextUtils.isEmpty(str)) {
                ParsedUrl parseUrl = Utilities.parseUrl(str);
                if (parseUrl.path.isEmpty()) {
                    Preconditions.checkNotNull(firebaseApp, "Provided FirebaseApp must not be null.");
                    FirebaseDatabaseComponent firebaseDatabaseComponent = (FirebaseDatabaseComponent) firebaseApp.get(FirebaseDatabaseComponent.class);
                    Preconditions.checkNotNull(firebaseDatabaseComponent, "Firebase Database component is not present.");
                    firebaseDatabase = firebaseDatabaseComponent.get(parseUrl.repoInfo);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Specified Database URL '");
                    sb.append(str);
                    sb.append("' is invalid. It should point to the root of a Firebase Database but it includes a path: ");
                    sb.append(parseUrl.path.toString());
                    throw new DatabaseException(sb.toString());
                }
            } else {
                throw new DatabaseException("Failed to get FirebaseDatabase instance: Specify DatabaseURL within FirebaseApp or from your getInstance() call.");
            }
        }
        return firebaseDatabase;
    }

    static FirebaseDatabase createForTests(FirebaseApp firebaseApp, RepoInfo repoInfo2, DatabaseConfig databaseConfig) {
        FirebaseDatabase firebaseDatabase = new FirebaseDatabase(firebaseApp, repoInfo2, databaseConfig);
        firebaseDatabase.ensureRepo();
        return firebaseDatabase;
    }

    FirebaseDatabase(FirebaseApp firebaseApp, RepoInfo repoInfo2, DatabaseConfig databaseConfig) {
        this.app = firebaseApp;
        this.repoInfo = repoInfo2;
        this.config = databaseConfig;
    }

    public FirebaseApp getApp() {
        return this.app;
    }

    public DatabaseReference getReference() {
        ensureRepo();
        return new DatabaseReference(this.repo, Path.getEmptyPath());
    }

    public DatabaseReference getReference(String str) {
        ensureRepo();
        if (str != null) {
            Validation.validateRootPathString(str);
            return new DatabaseReference(this.repo, new Path(str));
        }
        throw new NullPointerException("Can't pass null for argument 'pathString' in FirebaseDatabase.getReference()");
    }

    public DatabaseReference getReferenceFromUrl(String str) {
        ensureRepo();
        if (str != null) {
            ParsedUrl parseUrl = Utilities.parseUrl(str);
            if (parseUrl.repoInfo.host.equals(this.repo.getRepoInfo().host)) {
                return new DatabaseReference(this.repo, parseUrl.path);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid URL (");
            sb.append(str);
            sb.append(") passed to getReference().  URL was expected to match configured Database URL: ");
            sb.append(getReference().toString());
            throw new DatabaseException(sb.toString());
        }
        throw new NullPointerException("Can't pass null for argument 'url' in FirebaseDatabase.getReferenceFromUrl()");
    }

    public void purgeOutstandingWrites() {
        ensureRepo();
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                FirebaseDatabase.this.repo.purgeOutstandingWrites();
            }
        });
    }

    public void goOnline() {
        ensureRepo();
        RepoManager.resume(this.repo);
    }

    public void goOffline() {
        ensureRepo();
        RepoManager.interrupt(this.repo);
    }

    public synchronized void setLogLevel(Level level) {
        assertUnfrozen("setLogLevel");
        this.config.setLogLevel(level);
    }

    public synchronized void setPersistenceEnabled(boolean z) {
        assertUnfrozen("setPersistenceEnabled");
        this.config.setPersistenceEnabled(z);
    }

    public synchronized void setPersistenceCacheSizeBytes(long j) {
        assertUnfrozen("setPersistenceCacheSizeBytes");
        this.config.setPersistenceCacheSizeBytes(j);
    }

    private void assertUnfrozen(String str) {
        if (this.repo != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calls to ");
            sb.append(str);
            sb.append("() must be made before any other usage of FirebaseDatabase instance.");
            throw new DatabaseException(sb.toString());
        }
    }

    private synchronized void ensureRepo() {
        if (this.repo == null) {
            this.repo = RepoManager.createRepo(this.config, this.repoInfo, this);
        }
    }

    /* access modifiers changed from: 0000 */
    public DatabaseConfig getConfig() {
        return this.config;
    }
}
