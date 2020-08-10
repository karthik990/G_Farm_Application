package com.google.firebase.database;

import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.core.ChildEventRegistration;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.ValueEventRegistration;
import com.google.firebase.database.core.ZombieEventManager;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.PathIndex;
import com.google.firebase.database.snapshot.PriorityIndex;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.firebase.database.snapshot.StringNode;
import com.google.firebase.database.snapshot.ValueIndex;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Query {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean orderByCalled;
    protected final QueryParams params;
    protected final Path path;
    protected final Repo repo;

    Query(Repo repo2, Path path2, QueryParams queryParams, boolean z) throws DatabaseException {
        this.repo = repo2;
        this.path = path2;
        this.params = queryParams;
        this.orderByCalled = z;
        Utilities.hardAssert(queryParams.isValid(), "Validation of queries failed.");
    }

    Query(Repo repo2, Path path2) {
        this.repo = repo2;
        this.path = path2;
        this.params = QueryParams.DEFAULT_PARAMS;
        this.orderByCalled = false;
    }

    private void validateQueryEndpoints(QueryParams queryParams) {
        if (queryParams.getIndex().equals(KeyIndex.getInstance())) {
            String str = "You must use startAt(String value), endAt(String value) or equalTo(String value) in combination with orderByKey(). Other type of values or using the version with 2 parameters is not supported";
            if (queryParams.hasStart()) {
                Node indexStartValue = queryParams.getIndexStartValue();
                if (!Objects.equal(queryParams.getIndexStartName(), ChildKey.getMinName()) || !(indexStartValue instanceof StringNode)) {
                    throw new IllegalArgumentException(str);
                }
            }
            if (queryParams.hasEnd()) {
                Node indexEndValue = queryParams.getIndexEndValue();
                if (!queryParams.getIndexEndName().equals(ChildKey.getMaxName()) || !(indexEndValue instanceof StringNode)) {
                    throw new IllegalArgumentException(str);
                }
            }
        } else if (!queryParams.getIndex().equals(PriorityIndex.getInstance())) {
        } else {
            if ((queryParams.hasStart() && !PriorityUtilities.isValidPriority(queryParams.getIndexStartValue())) || (queryParams.hasEnd() && !PriorityUtilities.isValidPriority(queryParams.getIndexEndValue()))) {
                throw new IllegalArgumentException("When using orderByPriority(), values provided to startAt(), endAt(), or equalTo() must be valid priorities.");
            }
        }
    }

    private void validateLimit(QueryParams queryParams) {
        if (queryParams.hasStart() && queryParams.hasEnd() && queryParams.hasLimit() && !queryParams.hasAnchoredLimit()) {
            throw new IllegalArgumentException("Can't combine startAt(), endAt() and limit(). Use limitToFirst() or limitToLast() instead");
        }
    }

    private void validateEqualToCall() {
        if (this.params.hasStart()) {
            throw new IllegalArgumentException("Can't call equalTo() and startAt() combined");
        } else if (this.params.hasEnd()) {
            throw new IllegalArgumentException("Can't call equalTo() and endAt() combined");
        }
    }

    private void validateNoOrderByCall() {
        if (this.orderByCalled) {
            throw new IllegalArgumentException("You can't combine multiple orderBy calls!");
        }
    }

    public ValueEventListener addValueEventListener(ValueEventListener valueEventListener) {
        addEventRegistration(new ValueEventRegistration(this.repo, valueEventListener, getSpec()));
        return valueEventListener;
    }

    public ChildEventListener addChildEventListener(ChildEventListener childEventListener) {
        addEventRegistration(new ChildEventRegistration(this.repo, childEventListener, getSpec()));
        return childEventListener;
    }

    public void addListenerForSingleValueEvent(final ValueEventListener valueEventListener) {
        addEventRegistration(new ValueEventRegistration(this.repo, new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Query.this.removeEventListener((ValueEventListener) this);
                valueEventListener.onDataChange(dataSnapshot);
            }

            public void onCancelled(DatabaseError databaseError) {
                valueEventListener.onCancelled(databaseError);
            }
        }, getSpec()));
    }

    public void removeEventListener(ValueEventListener valueEventListener) {
        if (valueEventListener != null) {
            removeEventRegistration(new ValueEventRegistration(this.repo, valueEventListener, getSpec()));
            return;
        }
        throw new NullPointerException("listener must not be null");
    }

    public void removeEventListener(ChildEventListener childEventListener) {
        if (childEventListener != null) {
            removeEventRegistration(new ChildEventRegistration(this.repo, childEventListener, getSpec()));
            return;
        }
        throw new NullPointerException("listener must not be null");
    }

    private void removeEventRegistration(final EventRegistration eventRegistration) {
        ZombieEventManager.getInstance().zombifyForRemove(eventRegistration);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                Query.this.repo.removeEventCallback(eventRegistration);
            }
        });
    }

    private void addEventRegistration(final EventRegistration eventRegistration) {
        ZombieEventManager.getInstance().recordEventRegistration(eventRegistration);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                Query.this.repo.addEventCallback(eventRegistration);
            }
        });
    }

    public void keepSynced(final boolean z) {
        if (this.path.isEmpty() || !this.path.getFront().equals(ChildKey.getInfoKey())) {
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    Query.this.repo.keepSynced(Query.this.getSpec(), z);
                }
            });
            return;
        }
        throw new DatabaseException("Can't call keepSynced() on .info paths.");
    }

    public Query startAt(String str) {
        return startAt(str, (String) null);
    }

    public Query startAt(double d) {
        return startAt(d, (String) null);
    }

    public Query startAt(boolean z) {
        return startAt(z, (String) null);
    }

    public Query startAt(String str, String str2) {
        return startAt(str != null ? new StringNode(str, PriorityUtilities.NullPriority()) : EmptyNode.Empty(), str2);
    }

    public Query startAt(double d, String str) {
        return startAt((Node) new DoubleNode(Double.valueOf(d), PriorityUtilities.NullPriority()), str);
    }

    public Query startAt(boolean z, String str) {
        return startAt((Node) new BooleanNode(Boolean.valueOf(z), PriorityUtilities.NullPriority()), str);
    }

    private Query startAt(Node node, String str) {
        Validation.validateNullableKey(str);
        if (!node.isLeafNode() && !node.isEmpty()) {
            throw new IllegalArgumentException("Can only use simple values for startAt()");
        } else if (!this.params.hasStart()) {
            QueryParams startAt = this.params.startAt(node, str != null ? ChildKey.fromString(str) : null);
            validateLimit(startAt);
            validateQueryEndpoints(startAt);
            return new Query(this.repo, this.path, startAt, this.orderByCalled);
        } else {
            throw new IllegalArgumentException("Can't call startAt() or equalTo() multiple times");
        }
    }

    public Query endAt(String str) {
        return endAt(str, (String) null);
    }

    public Query endAt(double d) {
        return endAt(d, (String) null);
    }

    public Query endAt(boolean z) {
        return endAt(z, (String) null);
    }

    public Query endAt(String str, String str2) {
        return endAt(str != null ? new StringNode(str, PriorityUtilities.NullPriority()) : EmptyNode.Empty(), str2);
    }

    public Query endAt(double d, String str) {
        return endAt((Node) new DoubleNode(Double.valueOf(d), PriorityUtilities.NullPriority()), str);
    }

    public Query endAt(boolean z, String str) {
        return endAt((Node) new BooleanNode(Boolean.valueOf(z), PriorityUtilities.NullPriority()), str);
    }

    private Query endAt(Node node, String str) {
        Validation.validateNullableKey(str);
        if (node.isLeafNode() || node.isEmpty()) {
            ChildKey fromString = str != null ? ChildKey.fromString(str) : null;
            if (!this.params.hasEnd()) {
                QueryParams endAt = this.params.endAt(node, fromString);
                validateLimit(endAt);
                validateQueryEndpoints(endAt);
                return new Query(this.repo, this.path, endAt, this.orderByCalled);
            }
            throw new IllegalArgumentException("Can't call endAt() or equalTo() multiple times");
        }
        throw new IllegalArgumentException("Can only use simple values for endAt()");
    }

    public Query equalTo(String str) {
        validateEqualToCall();
        return startAt(str).endAt(str);
    }

    public Query equalTo(double d) {
        validateEqualToCall();
        return startAt(d).endAt(d);
    }

    public Query equalTo(boolean z) {
        validateEqualToCall();
        return startAt(z).endAt(z);
    }

    public Query equalTo(String str, String str2) {
        validateEqualToCall();
        return startAt(str, str2).endAt(str, str2);
    }

    public Query equalTo(double d, String str) {
        validateEqualToCall();
        return startAt(d, str).endAt(d, str);
    }

    public Query equalTo(boolean z, String str) {
        validateEqualToCall();
        return startAt(z, str).endAt(z, str);
    }

    public Query limitToFirst(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer!");
        } else if (!this.params.hasLimit()) {
            return new Query(this.repo, this.path, this.params.limitToFirst(i), this.orderByCalled);
        } else {
            throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
        }
    }

    public Query limitToLast(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer!");
        } else if (!this.params.hasLimit()) {
            return new Query(this.repo, this.path, this.params.limitToLast(i), this.orderByCalled);
        } else {
            throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
        }
    }

    public Query orderByChild(String str) {
        if (str != null) {
            String str2 = "Can't use '";
            if (str.equals("$key") || str.equals(".key")) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(str);
                sb.append("' as path, please use orderByKey() instead!");
                throw new IllegalArgumentException(sb.toString());
            } else if (str.equals("$priority") || str.equals(".priority")) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(str);
                sb2.append("' as path, please use orderByPriority() instead!");
                throw new IllegalArgumentException(sb2.toString());
            } else if (str.equals("$value") || str.equals(".value")) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(str);
                sb3.append("' as path, please use orderByValue() instead!");
                throw new IllegalArgumentException(sb3.toString());
            } else {
                Validation.validatePathString(str);
                validateNoOrderByCall();
                Path path2 = new Path(str);
                if (path2.size() != 0) {
                    return new Query(this.repo, this.path, this.params.orderBy(new PathIndex(path2)), true);
                }
                throw new IllegalArgumentException("Can't use empty path, use orderByValue() instead!");
            }
        } else {
            throw new NullPointerException("Key can't be null");
        }
    }

    public Query orderByPriority() {
        validateNoOrderByCall();
        QueryParams orderBy = this.params.orderBy(PriorityIndex.getInstance());
        validateQueryEndpoints(orderBy);
        return new Query(this.repo, this.path, orderBy, true);
    }

    public Query orderByKey() {
        validateNoOrderByCall();
        QueryParams orderBy = this.params.orderBy(KeyIndex.getInstance());
        validateQueryEndpoints(orderBy);
        return new Query(this.repo, this.path, orderBy, true);
    }

    public Query orderByValue() {
        validateNoOrderByCall();
        return new Query(this.repo, this.path, this.params.orderBy(ValueIndex.getInstance()), true);
    }

    public DatabaseReference getRef() {
        return new DatabaseReference(this.repo, getPath());
    }

    public Path getPath() {
        return this.path;
    }

    public Repo getRepo() {
        return this.repo;
    }

    public QuerySpec getSpec() {
        return new QuerySpec(this.path, this.params);
    }
}
