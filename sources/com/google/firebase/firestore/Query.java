package com.google.firebase.firestore;

import android.app.Activity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Preconditions;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.firebase.firestore.core.ActivityScope;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.EventManager.ListenOptions;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.Filter.Operator;
import com.google.firebase.firestore.core.ListenerRegistrationImpl;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.RelationFilter;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ReferenceValue;
import com.google.firebase.firestore.model.value.ServerTimestampValue;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Query {
    final FirebaseFirestore firestore;
    final com.google.firebase.firestore.core.Query query;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Direction {
        ASCENDING,
        DESCENDING
    }

    Query(com.google.firebase.firestore.core.Query query2, FirebaseFirestore firebaseFirestore) {
        this.query = (com.google.firebase.firestore.core.Query) Preconditions.checkNotNull(query2);
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
    }

    public FirebaseFirestore getFirestore() {
        return this.firestore;
    }

    private void validateOrderByFieldMatchesInequality(FieldPath fieldPath, FieldPath fieldPath2) {
        if (!fieldPath.equals(fieldPath2)) {
            String canonicalString = fieldPath2.canonicalString();
            throw new IllegalArgumentException(String.format("Invalid query. You have an inequality where filter (whereLessThan(), whereGreaterThan(), etc.) on field '%s' and so you must also have '%s' as your first orderBy() field, but your first orderBy() is currently on field '%s' instead.", new Object[]{canonicalString, canonicalString, fieldPath.canonicalString()}));
        }
    }

    private void validateNewFilter(Filter filter) {
        if (filter instanceof RelationFilter) {
            RelationFilter relationFilter = (RelationFilter) filter;
            if (relationFilter.isInequality()) {
                FieldPath inequalityField = this.query.inequalityField();
                FieldPath field = filter.getField();
                if (inequalityField == null || inequalityField.equals(field)) {
                    FieldPath firstOrderByField = this.query.getFirstOrderByField();
                    if (firstOrderByField != null) {
                        validateOrderByFieldMatchesInequality(firstOrderByField, field);
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException(String.format("All where filters other than whereEqualTo() must be on the same field. But you have filters on '%s' and '%s'", new Object[]{inequalityField.canonicalString(), field.canonicalString()}));
            } else if (relationFilter.getOperator() == Operator.ARRAY_CONTAINS && this.query.hasArrayContainsFilter()) {
                throw new IllegalArgumentException("Invalid Query. Queries only support having a single array-contains filter.");
            }
        }
    }

    public Query whereEqualTo(String str, @Nullable Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.EQUAL, obj);
    }

    public Query whereEqualTo(FieldPath fieldPath, @Nullable Object obj) {
        return whereHelper(fieldPath, Operator.EQUAL, obj);
    }

    public Query whereLessThan(String str, Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.LESS_THAN, obj);
    }

    public Query whereLessThan(FieldPath fieldPath, Object obj) {
        return whereHelper(fieldPath, Operator.LESS_THAN, obj);
    }

    public Query whereLessThanOrEqualTo(String str, Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.LESS_THAN_OR_EQUAL, obj);
    }

    public Query whereLessThanOrEqualTo(FieldPath fieldPath, Object obj) {
        return whereHelper(fieldPath, Operator.LESS_THAN_OR_EQUAL, obj);
    }

    public Query whereGreaterThan(String str, Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.GREATER_THAN, obj);
    }

    public Query whereGreaterThan(FieldPath fieldPath, Object obj) {
        return whereHelper(fieldPath, Operator.GREATER_THAN, obj);
    }

    public Query whereGreaterThanOrEqualTo(String str, Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.GREATER_THAN_OR_EQUAL, obj);
    }

    public Query whereGreaterThanOrEqualTo(FieldPath fieldPath, Object obj) {
        return whereHelper(fieldPath, Operator.GREATER_THAN_OR_EQUAL, obj);
    }

    public Query whereArrayContains(String str, Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.ARRAY_CONTAINS, obj);
    }

    public Query whereArrayContains(FieldPath fieldPath, Object obj) {
        return whereHelper(fieldPath, Operator.ARRAY_CONTAINS, obj);
    }

    private Query whereHelper(FieldPath fieldPath, Operator operator, Object obj) {
        FieldValue fieldValue;
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        Preconditions.checkNotNull(operator, "Provided op must not be null.");
        if (!fieldPath.getInternalPath().isKeyField()) {
            fieldValue = this.firestore.getDataConverter().parseQueryValue(obj);
        } else if (operator == Operator.ARRAY_CONTAINS) {
            throw new IllegalArgumentException("Invalid query. You can't perform array-contains queries on FieldPath.documentId() since document IDs are not arrays.");
        } else if (obj instanceof String) {
            String str = (String) obj;
            if (str.isEmpty()) {
                throw new IllegalArgumentException("Invalid query. When querying with FieldPath.documentId() you must provide a valid document ID, but it was an empty string.");
            } else if (this.query.isCollectionGroupQuery() || !str.contains("/")) {
                ResourcePath resourcePath = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(str));
                if (DocumentKey.isDocumentKey(resourcePath)) {
                    fieldValue = ReferenceValue.valueOf(getFirestore().getDatabaseId(), DocumentKey.fromPath(resourcePath));
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid query. When querying a collection group by FieldPath.documentId(), the value provided must result in a valid document path, but '");
                    sb.append(resourcePath);
                    sb.append("' is not because it has an odd number of segments (");
                    sb.append(resourcePath.length());
                    sb.append(").");
                    throw new IllegalArgumentException(sb.toString());
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Invalid query. When querying a collection by FieldPath.documentId() you must provide a plain document ID, but '");
                sb2.append(str);
                sb2.append("' contains a '/' character.");
                throw new IllegalArgumentException(sb2.toString());
            }
        } else if (obj instanceof DocumentReference) {
            fieldValue = ReferenceValue.valueOf(getFirestore().getDatabaseId(), ((DocumentReference) obj).getKey());
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Invalid query. When querying with FieldPath.documentId() you must provide a valid String or DocumentReference, but it was of type: ");
            sb3.append(Util.typeName(obj));
            throw new IllegalArgumentException(sb3.toString());
        }
        Filter create = Filter.create(fieldPath.getInternalPath(), operator, fieldValue);
        validateNewFilter(create);
        return new Query(this.query.filter(create), this.firestore);
    }

    private void validateOrderByField(FieldPath fieldPath) {
        FieldPath inequalityField = this.query.inequalityField();
        if (this.query.getFirstOrderByField() == null && inequalityField != null) {
            validateOrderByFieldMatchesInequality(fieldPath, inequalityField);
        }
    }

    public Query orderBy(String str) {
        return orderBy(FieldPath.fromDotSeparatedPath(str), Direction.ASCENDING);
    }

    public Query orderBy(FieldPath fieldPath) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), Direction.ASCENDING);
    }

    public Query orderBy(String str, Direction direction) {
        return orderBy(FieldPath.fromDotSeparatedPath(str), direction);
    }

    public Query orderBy(FieldPath fieldPath, Direction direction) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), direction);
    }

    private Query orderBy(FieldPath fieldPath, Direction direction) {
        com.google.firebase.firestore.core.OrderBy.Direction direction2;
        Preconditions.checkNotNull(direction, "Provided direction must not be null.");
        if (this.query.getStartAt() != null) {
            throw new IllegalArgumentException("Invalid query. You must not call Query.startAt() or Query.startAfter() before calling Query.orderBy().");
        } else if (this.query.getEndAt() == null) {
            validateOrderByField(fieldPath);
            if (direction == Direction.ASCENDING) {
                direction2 = com.google.firebase.firestore.core.OrderBy.Direction.ASCENDING;
            } else {
                direction2 = com.google.firebase.firestore.core.OrderBy.Direction.DESCENDING;
            }
            return new Query(this.query.orderBy(OrderBy.getInstance(direction2, fieldPath)), this.firestore);
        } else {
            throw new IllegalArgumentException("Invalid query. You must not call Query.endAt() or Query.endBefore() before calling Query.orderBy().");
        }
    }

    public Query limit(long j) {
        if (j > 0) {
            return new Query(this.query.limit(j), this.firestore);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid Query. Query limit (");
        sb.append(j);
        sb.append(") is invalid. Limit must be positive.");
        throw new IllegalArgumentException(sb.toString());
    }

    public Query startAt(DocumentSnapshot documentSnapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAt", documentSnapshot, true)), this.firestore);
    }

    public Query startAt(Object... objArr) {
        return new Query(this.query.startAt(boundFromFields("startAt", objArr, true)), this.firestore);
    }

    public Query startAfter(DocumentSnapshot documentSnapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAfter", documentSnapshot, false)), this.firestore);
    }

    public Query startAfter(Object... objArr) {
        return new Query(this.query.startAt(boundFromFields("startAfter", objArr, false)), this.firestore);
    }

    public Query endBefore(DocumentSnapshot documentSnapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endBefore", documentSnapshot, true)), this.firestore);
    }

    public Query endBefore(Object... objArr) {
        return new Query(this.query.endAt(boundFromFields("endBefore", objArr, true)), this.firestore);
    }

    public Query endAt(DocumentSnapshot documentSnapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endAt", documentSnapshot, false)), this.firestore);
    }

    public Query endAt(Object... objArr) {
        return new Query(this.query.endAt(boundFromFields("endAt", objArr, false)), this.firestore);
    }

    private Bound boundFromDocumentSnapshot(String str, DocumentSnapshot documentSnapshot, boolean z) {
        Preconditions.checkNotNull(documentSnapshot, "Provided snapshot must not be null.");
        if (documentSnapshot.exists()) {
            Document document = documentSnapshot.getDocument();
            ArrayList arrayList = new ArrayList();
            for (OrderBy orderBy : this.query.getOrderBy()) {
                if (orderBy.getField().equals(FieldPath.KEY_PATH)) {
                    arrayList.add(ReferenceValue.valueOf(this.firestore.getDatabaseId(), document.getKey()));
                } else {
                    FieldValue field = document.getField(orderBy.getField());
                    String str2 = "Invalid query. You are trying to start or end a query using a document for which the field '";
                    if (field instanceof ServerTimestampValue) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(orderBy.getField());
                        sb.append("' is an uncommitted server timestamp. (Since the value of this field is unknown, you cannot start/end a query with it.)");
                        throw new IllegalArgumentException(sb.toString());
                    } else if (field != null) {
                        arrayList.add(field);
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(orderBy.getField());
                        sb2.append("' (used as the orderBy) does not exist.");
                        throw new IllegalArgumentException(sb2.toString());
                    }
                }
            }
            return new Bound(arrayList, z);
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Can't use a DocumentSnapshot for a document that doesn't exist for ");
        sb3.append(str);
        sb3.append("().");
        throw new IllegalArgumentException(sb3.toString());
    }

    private Bound boundFromFields(String str, Object[] objArr, boolean z) {
        List explicitOrderBy = this.query.getExplicitOrderBy();
        if (objArr.length <= explicitOrderBy.size()) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < objArr.length; i++) {
                String str2 = objArr[i];
                if (!((OrderBy) explicitOrderBy.get(i)).getField().equals(FieldPath.KEY_PATH)) {
                    arrayList.add(this.firestore.getDataConverter().parseQueryValue(str2));
                } else if (str2 instanceof String) {
                    String str3 = str2;
                    if (this.query.isCollectionGroupQuery() || !str3.contains("/")) {
                        ResourcePath resourcePath = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(str3));
                        if (DocumentKey.isDocumentKey(resourcePath)) {
                            arrayList.add(ReferenceValue.valueOf(this.firestore.getDatabaseId(), DocumentKey.fromPath(resourcePath)));
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Invalid query. When querying a collection group and ordering by FieldPath.documentId(), the value passed to ");
                            sb.append(str);
                            sb.append("() must result in a valid document path, but '");
                            sb.append(resourcePath);
                            sb.append("' is not because it contains an odd number of segments.");
                            throw new IllegalArgumentException(sb.toString());
                        }
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Invalid query. When querying a collection and ordering by FieldPath.documentId(), the value passed to ");
                        sb2.append(str);
                        sb2.append("() must be a plain document ID, but '");
                        sb2.append(str3);
                        sb2.append("' contains a slash.");
                        throw new IllegalArgumentException(sb2.toString());
                    }
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Invalid query. Expected a string for document ID in ");
                    sb3.append(str);
                    sb3.append("(), but got ");
                    sb3.append(str2);
                    sb3.append(".");
                    throw new IllegalArgumentException(sb3.toString());
                }
            }
            return new Bound(arrayList, z);
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Too many arguments provided to ");
        sb4.append(str);
        sb4.append("(). The number of arguments must be less than or equal to the number of orderBy() clauses.");
        throw new IllegalArgumentException(sb4.toString());
    }

    public Task<QuerySnapshot> get() {
        return get(Source.DEFAULT);
    }

    public Task<QuerySnapshot> get(Source source) {
        if (source == Source.CACHE) {
            return this.firestore.getClient().getDocumentsFromLocalCache(this.query).continueWith(Executors.DIRECT_EXECUTOR, Query$$Lambda$1.lambdaFactory$(this));
        }
        return getViaSnapshotListener(source);
    }

    static /* synthetic */ QuerySnapshot lambda$get$0(Query query2, Task task) throws Exception {
        return new QuerySnapshot(new Query(query2.query, query2.firestore), (ViewSnapshot) task.getResult(), query2.firestore);
    }

    private Task<QuerySnapshot> getViaSnapshotListener(Source source) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
        ListenOptions listenOptions = new ListenOptions();
        listenOptions.includeDocumentMetadataChanges = true;
        listenOptions.includeQueryMetadataChanges = true;
        listenOptions.waitForSyncWhenOnline = true;
        taskCompletionSource2.setResult(addSnapshotListenerInternal(Executors.DIRECT_EXECUTOR, listenOptions, null, Query$$Lambda$2.lambdaFactory$(taskCompletionSource, taskCompletionSource2, source)));
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$getViaSnapshotListener$1(TaskCompletionSource taskCompletionSource, TaskCompletionSource taskCompletionSource2, Source source, QuerySnapshot querySnapshot, FirebaseFirestoreException firebaseFirestoreException) {
        String str = "Failed to register a listener for a query result";
        if (firebaseFirestoreException != null) {
            taskCompletionSource.setException(firebaseFirestoreException);
            return;
        }
        try {
            ((ListenerRegistration) Tasks.await(taskCompletionSource2.getTask())).remove();
            if (!querySnapshot.getMetadata().isFromCache() || source != Source.SERVER) {
                taskCompletionSource.setResult(querySnapshot);
            } else {
                taskCompletionSource.setException(new FirebaseFirestoreException("Failed to get documents from server. (However, these documents may exist in the local cache. Run again without setting source to SERVER to retrieve the cached documents.)", Code.UNAVAILABLE));
            }
        } catch (ExecutionException e) {
            throw Assert.fail(e, str, new Object[0]);
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw Assert.fail(e2, str, new Object[0]);
        }
    }

    public ListenerRegistration addSnapshotListener(EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(MetadataChanges.EXCLUDE, eventListener);
    }

    public ListenerRegistration addSnapshotListener(Executor executor, EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(executor, MetadataChanges.EXCLUDE, eventListener);
    }

    public ListenerRegistration addSnapshotListener(Activity activity, EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(activity, MetadataChanges.EXCLUDE, eventListener);
    }

    public ListenerRegistration addSnapshotListener(MetadataChanges metadataChanges, EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(Executors.DEFAULT_CALLBACK_EXECUTOR, metadataChanges, eventListener);
    }

    public ListenerRegistration addSnapshotListener(Executor executor, MetadataChanges metadataChanges, EventListener<QuerySnapshot> eventListener) {
        Preconditions.checkNotNull(executor, "Provided executor must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(eventListener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(executor, internalOptions(metadataChanges), null, eventListener);
    }

    public ListenerRegistration addSnapshotListener(Activity activity, MetadataChanges metadataChanges, EventListener<QuerySnapshot> eventListener) {
        Preconditions.checkNotNull(activity, "Provided activity must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(eventListener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(Executors.DEFAULT_CALLBACK_EXECUTOR, internalOptions(metadataChanges), activity, eventListener);
    }

    private ListenerRegistration addSnapshotListenerInternal(Executor executor, ListenOptions listenOptions, @Nullable Activity activity, EventListener<QuerySnapshot> eventListener) {
        AsyncEventListener asyncEventListener = new AsyncEventListener(executor, Query$$Lambda$3.lambdaFactory$(this, eventListener));
        return ActivityScope.bind(activity, new ListenerRegistrationImpl(this.firestore.getClient(), this.firestore.getClient().listen(this.query, listenOptions, asyncEventListener), asyncEventListener));
    }

    static /* synthetic */ void lambda$addSnapshotListenerInternal$2(Query query2, EventListener eventListener, ViewSnapshot viewSnapshot, FirebaseFirestoreException firebaseFirestoreException) {
        if (firebaseFirestoreException != null) {
            eventListener.onEvent(null, firebaseFirestoreException);
            return;
        }
        Assert.hardAssert(viewSnapshot != null, "Got event without value or error set", new Object[0]);
        eventListener.onEvent(new QuerySnapshot(query2, viewSnapshot, query2.firestore), null);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Query)) {
            return false;
        }
        Query query2 = (Query) obj;
        if (!this.query.equals(query2.query) || !this.firestore.equals(query2.firestore)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (this.query.hashCode() * 31) + this.firestore.hashCode();
    }

    private static ListenOptions internalOptions(MetadataChanges metadataChanges) {
        ListenOptions listenOptions = new ListenOptions();
        boolean z = true;
        listenOptions.includeDocumentMetadataChanges = metadataChanges == MetadataChanges.INCLUDE;
        if (metadataChanges != MetadataChanges.INCLUDE) {
            z = false;
        }
        listenOptions.includeQueryMetadataChanges = z;
        listenOptions.waitForSyncWhenOnline = false;
        return listenOptions;
    }
}
