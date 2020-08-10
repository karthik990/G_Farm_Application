package com.google.firebase.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.database.android.AndroidAuthTokenProvider;
import com.google.firebase.database.core.AuthTokenProvider;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.RepoInfo;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class FirebaseDatabaseComponent {
    private final FirebaseApp app;
    private final AuthTokenProvider authProvider;
    private final Map<RepoInfo, FirebaseDatabase> instances = new HashMap();

    FirebaseDatabaseComponent(FirebaseApp firebaseApp, InternalAuthProvider internalAuthProvider) {
        this.app = firebaseApp;
        if (internalAuthProvider != null) {
            this.authProvider = AndroidAuthTokenProvider.forAuthenticatedAccess(internalAuthProvider);
        } else {
            this.authProvider = AndroidAuthTokenProvider.forUnauthenticatedAccess();
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized FirebaseDatabase get(RepoInfo repoInfo) {
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase = (FirebaseDatabase) this.instances.get(repoInfo);
        if (firebaseDatabase == null) {
            DatabaseConfig databaseConfig = new DatabaseConfig();
            if (!this.app.isDefaultApp()) {
                databaseConfig.setSessionPersistenceKey(this.app.getName());
            }
            databaseConfig.setFirebaseApp(this.app);
            databaseConfig.setAuthTokenProvider(this.authProvider);
            FirebaseDatabase firebaseDatabase2 = new FirebaseDatabase(this.app, repoInfo, databaseConfig);
            this.instances.put(repoInfo, firebaseDatabase2);
            firebaseDatabase = firebaseDatabase2;
        }
        return firebaseDatabase;
    }
}
