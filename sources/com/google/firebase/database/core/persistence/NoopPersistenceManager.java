package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class NoopPersistenceManager implements PersistenceManager {
    private static final String TAG = "NoopPersistenceManager";
    private boolean insideTransaction = false;

    public void saveUserOverwrite(Path path, Node node, long j) {
        verifyInsideTransaction();
    }

    public void saveUserMerge(Path path, CompoundWrite compoundWrite, long j) {
        verifyInsideTransaction();
    }

    public void removeUserWrite(long j) {
        verifyInsideTransaction();
    }

    public void removeAllUserWrites() {
        verifyInsideTransaction();
    }

    public void applyUserWriteToServerCache(Path path, Node node) {
        verifyInsideTransaction();
    }

    public void applyUserWriteToServerCache(Path path, CompoundWrite compoundWrite) {
        verifyInsideTransaction();
    }

    public List<UserWriteRecord> loadUserWrites() {
        return Collections.emptyList();
    }

    public CacheNode serverCache(QuerySpec querySpec) {
        return new CacheNode(IndexedNode.from(EmptyNode.Empty(), querySpec.getIndex()), false, false);
    }

    public void updateServerCache(QuerySpec querySpec, Node node) {
        verifyInsideTransaction();
    }

    public void updateServerCache(Path path, CompoundWrite compoundWrite) {
        verifyInsideTransaction();
    }

    public void setQueryActive(QuerySpec querySpec) {
        verifyInsideTransaction();
    }

    public void setQueryInactive(QuerySpec querySpec) {
        verifyInsideTransaction();
    }

    public void setQueryComplete(QuerySpec querySpec) {
        verifyInsideTransaction();
    }

    public void setTrackedQueryKeys(QuerySpec querySpec, Set<ChildKey> set) {
        verifyInsideTransaction();
    }

    public void updateTrackedQueryKeys(QuerySpec querySpec, Set<ChildKey> set, Set<ChildKey> set2) {
        verifyInsideTransaction();
    }

    public <T> T runInTransaction(Callable<T> callable) {
        Utilities.hardAssert(!this.insideTransaction, "runInTransaction called when an existing transaction is already in progress.");
        this.insideTransaction = true;
        try {
            T call = callable.call();
            this.insideTransaction = false;
            return call;
        } catch (Throwable th) {
            this.insideTransaction = false;
            throw th;
        }
    }

    private void verifyInsideTransaction() {
        Utilities.hardAssert(this.insideTransaction, "Transaction expected to already be in progress.");
    }
}
