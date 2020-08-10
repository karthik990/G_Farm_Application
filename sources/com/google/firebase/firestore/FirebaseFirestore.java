package com.google.firebase.firestore;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Preconditions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.firestore.FirebaseFirestoreSettings.Builder;
import com.google.firebase.firestore.Transaction.Function;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.EmptyCredentialsProvider;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.Transaction;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Logger.Level;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class FirebaseFirestore {
    private static final String TAG = "FirebaseFirestore";
    private final AsyncQueue asyncQueue;
    private volatile FirestoreClient client;
    private final Context context;
    private final CredentialsProvider credentialsProvider;
    private final UserDataConverter dataConverter;
    private final DatabaseId databaseId;
    private final FirebaseApp firebaseApp;
    private final String persistenceKey;
    private FirebaseFirestoreSettings settings = new Builder().build();

    public static FirebaseFirestore getInstance() {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, DatabaseId.DEFAULT_DATABASE_ID);
        }
        throw new IllegalStateException("You must call FirebaseApp.initializeApp first.");
    }

    public static FirebaseFirestore getInstance(FirebaseApp firebaseApp2) {
        return getInstance(firebaseApp2, DatabaseId.DEFAULT_DATABASE_ID);
    }

    private static FirebaseFirestore getInstance(FirebaseApp firebaseApp2, String str) {
        Preconditions.checkNotNull(firebaseApp2, "Provided FirebaseApp must not be null.");
        FirestoreMultiDbComponent firestoreMultiDbComponent = (FirestoreMultiDbComponent) firebaseApp2.get(FirestoreMultiDbComponent.class);
        Preconditions.checkNotNull(firestoreMultiDbComponent, "Firestore component is not present.");
        return firestoreMultiDbComponent.get(str);
    }

    static FirebaseFirestore newInstance(Context context2, FirebaseApp firebaseApp2, InternalAuthProvider internalAuthProvider, String str) {
        CredentialsProvider credentialsProvider2;
        String projectId = firebaseApp2.getOptions().getProjectId();
        if (projectId != null) {
            DatabaseId forDatabase = DatabaseId.forDatabase(projectId, str);
            AsyncQueue asyncQueue2 = new AsyncQueue();
            if (internalAuthProvider == null) {
                Logger.debug(TAG, "Firebase Auth not available, falling back to unauthenticated usage.", new Object[0]);
                credentialsProvider2 = new EmptyCredentialsProvider();
            } else {
                credentialsProvider2 = new FirebaseAuthCredentialsProvider(internalAuthProvider);
            }
            asyncQueue2.enqueueAndForget(FirebaseFirestore$$Lambda$1.lambdaFactory$(context2));
            FirebaseFirestore firebaseFirestore = new FirebaseFirestore(context2, forDatabase, firebaseApp2.getName(), credentialsProvider2, asyncQueue2, firebaseApp2);
            return firebaseFirestore;
        }
        throw new IllegalArgumentException("FirebaseOptions.getProjectId() cannot be null");
    }

    static /* synthetic */ void lambda$newInstance$0(Context context2) {
        try {
            ProviderInstaller.installIfNeeded(context2);
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException unused) {
            Logger.warn("Firestore", "Failed to update ssl context", new Object[0]);
        }
    }

    FirebaseFirestore(Context context2, DatabaseId databaseId2, String str, CredentialsProvider credentialsProvider2, AsyncQueue asyncQueue2, FirebaseApp firebaseApp2) {
        this.context = (Context) Preconditions.checkNotNull(context2);
        this.databaseId = (DatabaseId) Preconditions.checkNotNull((DatabaseId) Preconditions.checkNotNull(databaseId2));
        this.dataConverter = new UserDataConverter(databaseId2);
        this.persistenceKey = (String) Preconditions.checkNotNull(str);
        this.credentialsProvider = (CredentialsProvider) Preconditions.checkNotNull(credentialsProvider2);
        this.asyncQueue = (AsyncQueue) Preconditions.checkNotNull(asyncQueue2);
        this.firebaseApp = firebaseApp2;
    }

    public FirebaseFirestoreSettings getFirestoreSettings() {
        return this.settings;
    }

    public void setFirestoreSettings(FirebaseFirestoreSettings firebaseFirestoreSettings) {
        synchronized (this.databaseId) {
            Preconditions.checkNotNull(firebaseFirestoreSettings, "Provided settings must not be null.");
            if (this.client != null) {
                if (!this.settings.equals(firebaseFirestoreSettings)) {
                    throw new IllegalStateException("FirebaseFirestore has already been started and its settings can no longer be changed. You can only call setFirestoreSettings() before calling any other methods on a FirebaseFirestore object.");
                }
            }
            this.settings = firebaseFirestoreSettings;
        }
    }

    private void ensureClientConfigured() {
        if (this.client == null) {
            synchronized (this.databaseId) {
                if (this.client == null) {
                    FirestoreClient firestoreClient = new FirestoreClient(this.context, new DatabaseInfo(this.databaseId, this.persistenceKey, this.settings.getHost(), this.settings.isSslEnabled()), this.settings, this.credentialsProvider, this.asyncQueue);
                    this.client = firestoreClient;
                }
            }
        }
    }

    public FirebaseApp getApp() {
        return this.firebaseApp;
    }

    public CollectionReference collection(String str) {
        Preconditions.checkNotNull(str, "Provided collection path must not be null.");
        ensureClientConfigured();
        return new CollectionReference(ResourcePath.fromString(str), this);
    }

    public DocumentReference document(String str) {
        Preconditions.checkNotNull(str, "Provided document path must not be null.");
        ensureClientConfigured();
        return DocumentReference.forPath(ResourcePath.fromString(str), this);
    }

    public Query collectionGroup(String str) {
        Preconditions.checkNotNull(str, "Provided collection ID must not be null.");
        if (!str.contains("/")) {
            ensureClientConfigured();
            return new Query(new Query(ResourcePath.EMPTY, str), this);
        }
        throw new IllegalArgumentException(String.format("Invalid collectionId '%s'. Collection IDs must not contain '/'.", new Object[]{str}));
    }

    private <TResult> Task<TResult> runTransaction(Function<TResult> function, Executor executor) {
        ensureClientConfigured();
        return this.client.transaction(FirebaseFirestore$$Lambda$2.lambdaFactory$(this, executor, function), 5);
    }

    public <TResult> Task<TResult> runTransaction(Function<TResult> function) {
        Preconditions.checkNotNull(function, "Provided transaction update function must not be null.");
        return runTransaction(function, Transaction.getDefaultExecutor());
    }

    public WriteBatch batch() {
        ensureClientConfigured();
        return new WriteBatch(this);
    }

    public Task<Void> runBatch(WriteBatch.Function function) {
        WriteBatch batch = batch();
        function.apply(batch);
        return batch.commit();
    }

    /* access modifiers changed from: 0000 */
    public Task<Void> shutdown() {
        if (this.client == null) {
            return Tasks.forResult(null);
        }
        return this.client.shutdown();
    }

    /* access modifiers changed from: 0000 */
    public AsyncQueue getAsyncQueue() {
        return this.asyncQueue;
    }

    public Task<Void> enableNetwork() {
        ensureClientConfigured();
        return this.client.enableNetwork();
    }

    public Task<Void> disableNetwork() {
        ensureClientConfigured();
        return this.client.disableNetwork();
    }

    public static void setLoggingEnabled(boolean z) {
        if (z) {
            Logger.setLogLevel(Level.DEBUG);
        } else {
            Logger.setLogLevel(Level.WARN);
        }
    }

    /* access modifiers changed from: 0000 */
    public FirestoreClient getClient() {
        return this.client;
    }

    /* access modifiers changed from: 0000 */
    public DatabaseId getDatabaseId() {
        return this.databaseId;
    }

    /* access modifiers changed from: 0000 */
    public UserDataConverter getDataConverter() {
        return this.dataConverter;
    }

    /* access modifiers changed from: 0000 */
    public void validateReference(DocumentReference documentReference) {
        Preconditions.checkNotNull(documentReference, "Provided DocumentReference must not be null.");
        if (documentReference.getFirestore() != this) {
            throw new IllegalArgumentException("Provided document reference is from a different Firestore instance.");
        }
    }
}
