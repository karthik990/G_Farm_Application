package com.google.firebase.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoManager;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.ParsedUrl;
import com.google.firebase.database.core.utilities.PushIdGenerator;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.slf4j.Marker;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DatabaseReference extends Query {
    private static DatabaseConfig defaultConfig;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface CompletionListener {
        void onComplete(DatabaseError databaseError, DatabaseReference databaseReference);
    }

    DatabaseReference(Repo repo, Path path) {
        super(repo, path);
    }

    DatabaseReference(String str, DatabaseConfig databaseConfig) {
        this(Utilities.parseUrl(str), databaseConfig);
    }

    private DatabaseReference(ParsedUrl parsedUrl, DatabaseConfig databaseConfig) {
        this(RepoManager.getRepo(databaseConfig, parsedUrl.repoInfo), parsedUrl.path);
    }

    public DatabaseReference child(String str) {
        if (str != null) {
            if (getPath().isEmpty()) {
                Validation.validateRootPathString(str);
            } else {
                Validation.validatePathString(str);
            }
            return new DatabaseReference(this.repo, getPath().child(new Path(str)));
        }
        throw new NullPointerException("Can't pass null for argument 'pathString' in child()");
    }

    public DatabaseReference push() {
        return new DatabaseReference(this.repo, getPath().child(ChildKey.fromString(PushIdGenerator.generatePushChildName(this.repo.getServerTime()))));
    }

    public Task<Void> setValue(Object obj) {
        return setValueInternal(obj, PriorityUtilities.parsePriority(this.path, null), null);
    }

    public Task<Void> setValue(Object obj, Object obj2) {
        return setValueInternal(obj, PriorityUtilities.parsePriority(this.path, obj2), null);
    }

    public void setValue(Object obj, CompletionListener completionListener) {
        setValueInternal(obj, PriorityUtilities.parsePriority(this.path, null), completionListener);
    }

    public void setValue(Object obj, Object obj2, CompletionListener completionListener) {
        setValueInternal(obj, PriorityUtilities.parsePriority(this.path, obj2), completionListener);
    }

    private Task<Void> setValueInternal(Object obj, Node node, CompletionListener completionListener) {
        Validation.validateWritablePath(getPath());
        ValidationPath.validateWithObject(getPath(), obj);
        Object convertToPlainJavaTypes = CustomClassMapper.convertToPlainJavaTypes(obj);
        Validation.validateWritableObject(convertToPlainJavaTypes);
        final Node NodeFromJSON = NodeUtilities.NodeFromJSON(convertToPlainJavaTypes, node);
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                DatabaseReference.this.repo.setValue(DatabaseReference.this.getPath(), NodeFromJSON, (CompletionListener) wrapOnComplete.getSecond());
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }

    public Task<Void> setPriority(Object obj) {
        return setPriorityInternal(PriorityUtilities.parsePriority(this.path, obj), null);
    }

    public void setPriority(Object obj, CompletionListener completionListener) {
        setPriorityInternal(PriorityUtilities.parsePriority(this.path, obj), completionListener);
    }

    private Task<Void> setPriorityInternal(final Node node, CompletionListener completionListener) {
        Validation.validateWritablePath(getPath());
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                DatabaseReference.this.repo.setValue(DatabaseReference.this.getPath().child(ChildKey.getPriorityKey()), node, (CompletionListener) wrapOnComplete.getSecond());
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }

    public Task<Void> updateChildren(Map<String, Object> map) {
        return updateChildrenInternal(map, null);
    }

    public void updateChildren(Map<String, Object> map, CompletionListener completionListener) {
        updateChildrenInternal(map, completionListener);
    }

    private Task<Void> updateChildrenInternal(Map<String, Object> map, CompletionListener completionListener) {
        if (map != null) {
            final Map convertToPlainJavaTypes = CustomClassMapper.convertToPlainJavaTypes(map);
            final CompoundWrite fromPathMerge = CompoundWrite.fromPathMerge(Validation.parseAndValidateUpdate(getPath(), convertToPlainJavaTypes));
            final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    DatabaseReference.this.repo.updateChildren(DatabaseReference.this.getPath(), fromPathMerge, (CompletionListener) wrapOnComplete.getSecond(), convertToPlainJavaTypes);
                }
            });
            return (Task) wrapOnComplete.getFirst();
        }
        throw new NullPointerException("Can't pass null for argument 'update' in updateChildren()");
    }

    public Task<Void> removeValue() {
        return setValue(null);
    }

    public void removeValue(CompletionListener completionListener) {
        setValue((Object) null, completionListener);
    }

    public OnDisconnect onDisconnect() {
        Validation.validateWritablePath(getPath());
        return new OnDisconnect(this.repo, getPath());
    }

    public void runTransaction(Handler handler) {
        runTransaction(handler, true);
    }

    public void runTransaction(final Handler handler, final boolean z) {
        if (handler != null) {
            Validation.validateWritablePath(getPath());
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    DatabaseReference.this.repo.startTransaction(DatabaseReference.this.getPath(), handler, z);
                }
            });
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'handler' in runTransaction()");
    }

    public static void goOffline() {
        goOffline(getDefaultConfig());
    }

    static void goOffline(DatabaseConfig databaseConfig) {
        RepoManager.interrupt((Context) databaseConfig);
    }

    public static void goOnline() {
        goOnline(getDefaultConfig());
    }

    static void goOnline(DatabaseConfig databaseConfig) {
        RepoManager.resume((Context) databaseConfig);
    }

    public FirebaseDatabase getDatabase() {
        return this.repo.getDatabase();
    }

    public String toString() {
        DatabaseReference parent = getParent();
        if (parent == null) {
            return this.repo.toString();
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(parent.toString());
            sb.append("/");
            sb.append(URLEncoder.encode(getKey(), "UTF-8").replace(Marker.ANY_NON_NULL_MARKER, "%20"));
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to URLEncode key: ");
            sb2.append(getKey());
            throw new DatabaseException(sb2.toString(), e);
        }
    }

    public DatabaseReference getParent() {
        Path parent = getPath().getParent();
        if (parent != null) {
            return new DatabaseReference(this.repo, parent);
        }
        return null;
    }

    public DatabaseReference getRoot() {
        return new DatabaseReference(this.repo, new Path(""));
    }

    public String getKey() {
        if (getPath().isEmpty()) {
            return null;
        }
        return getPath().getBack().asString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof DatabaseReference) && toString().equals(obj.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    /* access modifiers changed from: 0000 */
    public void setHijackHash(final boolean z) {
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                DatabaseReference.this.repo.setHijackHash(z);
            }
        });
    }

    private static synchronized DatabaseConfig getDefaultConfig() {
        DatabaseConfig databaseConfig;
        synchronized (DatabaseReference.class) {
            if (defaultConfig == null) {
                defaultConfig = new DatabaseConfig();
            }
            databaseConfig = defaultConfig;
        }
        return databaseConfig;
    }
}
