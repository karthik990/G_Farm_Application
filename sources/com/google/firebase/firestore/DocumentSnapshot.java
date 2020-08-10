package com.google.firebase.firestore;

import com.google.common.base.Preconditions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.FieldValueOptions;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.model.value.ReferenceValue;
import com.google.firebase.firestore.util.CustomClassMapper;
import com.google.firebase.firestore.util.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class DocumentSnapshot {
    @Nullable
    private final Document doc;
    private final FirebaseFirestore firestore;
    private final DocumentKey key;
    private final SnapshotMetadata metadata;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ServerTimestampBehavior {
        NONE,
        ESTIMATE,
        PREVIOUS;
        
        static final ServerTimestampBehavior DEFAULT = null;

        static {
            ServerTimestampBehavior serverTimestampBehavior;
            DEFAULT = serverTimestampBehavior;
        }
    }

    DocumentSnapshot(FirebaseFirestore firebaseFirestore, DocumentKey documentKey, @Nullable Document document, boolean z, boolean z2) {
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
        this.key = (DocumentKey) Preconditions.checkNotNull(documentKey);
        this.doc = document;
        this.metadata = new SnapshotMetadata(z2, z);
    }

    static DocumentSnapshot fromDocument(FirebaseFirestore firebaseFirestore, Document document, boolean z, boolean z2) {
        DocumentSnapshot documentSnapshot = new DocumentSnapshot(firebaseFirestore, document.getKey(), document, z, z2);
        return documentSnapshot;
    }

    static DocumentSnapshot fromNoDocument(FirebaseFirestore firebaseFirestore, DocumentKey documentKey, boolean z, boolean z2) {
        DocumentSnapshot documentSnapshot = new DocumentSnapshot(firebaseFirestore, documentKey, null, z, z2);
        return documentSnapshot;
    }

    public String getId() {
        return this.key.getPath().getLastSegment();
    }

    public SnapshotMetadata getMetadata() {
        return this.metadata;
    }

    public boolean exists() {
        return this.doc != null;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Document getDocument() {
        return this.doc;
    }

    @Nullable
    public Map<String, Object> getData() {
        return getData(ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public Map<String, Object> getData(ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        Document document = this.doc;
        if (document == null) {
            return null;
        }
        return convertObject(document.getData(), FieldValueOptions.create(serverTimestampBehavior, this.firestore.getFirestoreSettings().areTimestampsInSnapshotsEnabled()));
    }

    @Nullable
    public <T> T toObject(Class<T> cls) {
        return toObject(cls, ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public <T> T toObject(Class<T> cls, ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(cls, "Provided POJO type must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        Map data = getData(serverTimestampBehavior);
        if (data == null) {
            return null;
        }
        return CustomClassMapper.convertToCustomClass(data, cls);
    }

    public boolean contains(String str) {
        return contains(FieldPath.fromDotSeparatedPath(str));
    }

    public boolean contains(FieldPath fieldPath) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        Document document = this.doc;
        return (document == null || document.getField(fieldPath.getInternalPath()) == null) ? false : true;
    }

    @Nullable
    public Object get(String str) {
        return get(FieldPath.fromDotSeparatedPath(str), ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public Object get(String str, ServerTimestampBehavior serverTimestampBehavior) {
        return get(FieldPath.fromDotSeparatedPath(str), serverTimestampBehavior);
    }

    @Nullable
    public Object get(FieldPath fieldPath) {
        return get(fieldPath, ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public Object get(FieldPath fieldPath, ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        return getInternal(fieldPath.getInternalPath(), FieldValueOptions.create(serverTimestampBehavior, this.firestore.getFirestoreSettings().areTimestampsInSnapshotsEnabled()));
    }

    @Nullable
    public <T> T get(String str, Class<T> cls) {
        return get(FieldPath.fromDotSeparatedPath(str), cls, ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public <T> T get(String str, Class<T> cls, ServerTimestampBehavior serverTimestampBehavior) {
        return get(FieldPath.fromDotSeparatedPath(str), cls, serverTimestampBehavior);
    }

    @Nullable
    public <T> T get(FieldPath fieldPath, Class<T> cls) {
        return get(fieldPath, cls, ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public <T> T get(FieldPath fieldPath, Class<T> cls, ServerTimestampBehavior serverTimestampBehavior) {
        Object obj = get(fieldPath, serverTimestampBehavior);
        if (obj == null) {
            return null;
        }
        return CustomClassMapper.convertToCustomClass(obj, cls);
    }

    @Nullable
    public Boolean getBoolean(String str) {
        return (Boolean) getTypedValue(str, Boolean.class);
    }

    @Nullable
    public Double getDouble(String str) {
        Number number = (Number) getTypedValue(str, Number.class);
        if (number != null) {
            return Double.valueOf(number.doubleValue());
        }
        return null;
    }

    @Nullable
    public String getString(String str) {
        return (String) getTypedValue(str, String.class);
    }

    @Nullable
    public Long getLong(String str) {
        Number number = (Number) getTypedValue(str, Number.class);
        if (number != null) {
            return Long.valueOf(number.longValue());
        }
        return null;
    }

    @Nullable
    public Date getDate(String str) {
        return getDate(str, ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public Date getDate(String str, ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(str, "Provided field path must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        return (Date) castTypedValue(getInternal(FieldPath.fromDotSeparatedPath(str).getInternalPath(), FieldValueOptions.create(serverTimestampBehavior, false)), str, Date.class);
    }

    @Nullable
    public Timestamp getTimestamp(String str) {
        return getTimestamp(str, ServerTimestampBehavior.DEFAULT);
    }

    @Nullable
    public Timestamp getTimestamp(String str, ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(str, "Provided field path must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        return (Timestamp) castTypedValue(getInternal(FieldPath.fromDotSeparatedPath(str).getInternalPath(), FieldValueOptions.create(serverTimestampBehavior, true)), str, Timestamp.class);
    }

    @Nullable
    public Blob getBlob(String str) {
        return (Blob) getTypedValue(str, Blob.class);
    }

    @Nullable
    public GeoPoint getGeoPoint(String str) {
        return (GeoPoint) getTypedValue(str, GeoPoint.class);
    }

    @Nullable
    public DocumentReference getDocumentReference(String str) {
        return (DocumentReference) getTypedValue(str, DocumentReference.class);
    }

    public DocumentReference getReference() {
        return new DocumentReference(this.key, this.firestore);
    }

    @Nullable
    private <T> T getTypedValue(String str, Class<T> cls) {
        Preconditions.checkNotNull(str, "Provided field must not be null.");
        return castTypedValue(get(str, ServerTimestampBehavior.DEFAULT), str, cls);
    }

    @Nullable
    private <T> T castTypedValue(Object obj, String str, Class<T> cls) {
        if (obj == null) {
            return null;
        }
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Field '");
        sb.append(str);
        sb.append("' is not a ");
        sb.append(cls.getName());
        throw new RuntimeException(sb.toString());
    }

    @Nullable
    private Object convertValue(FieldValue fieldValue, FieldValueOptions fieldValueOptions) {
        if (fieldValue instanceof ObjectValue) {
            return convertObject((ObjectValue) fieldValue, fieldValueOptions);
        }
        if (fieldValue instanceof ArrayValue) {
            return convertArray((ArrayValue) fieldValue, fieldValueOptions);
        }
        if (!(fieldValue instanceof ReferenceValue)) {
            return fieldValue.value(fieldValueOptions);
        }
        ReferenceValue referenceValue = (ReferenceValue) fieldValue;
        DocumentKey documentKey = (DocumentKey) referenceValue.value(fieldValueOptions);
        DatabaseId databaseId = referenceValue.getDatabaseId();
        DatabaseId databaseId2 = this.firestore.getDatabaseId();
        if (!databaseId.equals(databaseId2)) {
            Logger.warn("DocumentSnapshot", "Document %s contains a document reference within a different database (%s/%s) which is not supported. It will be treated as a reference in the current database (%s/%s) instead.", documentKey.getPath(), databaseId.getProjectId(), databaseId.getDatabaseId(), databaseId2.getProjectId(), databaseId2.getDatabaseId());
        }
        return new DocumentReference(documentKey, this.firestore);
    }

    private Map<String, Object> convertObject(ObjectValue objectValue, FieldValueOptions fieldValueOptions) {
        HashMap hashMap = new HashMap();
        Iterator it = objectValue.getInternalValue().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            hashMap.put((String) entry.getKey(), convertValue((FieldValue) entry.getValue(), fieldValueOptions));
        }
        return hashMap;
    }

    private List<Object> convertArray(ArrayValue arrayValue, FieldValueOptions fieldValueOptions) {
        ArrayList arrayList = new ArrayList(arrayValue.getInternalValue().size());
        for (FieldValue convertValue : arrayValue.getInternalValue()) {
            arrayList.add(convertValue(convertValue, fieldValueOptions));
        }
        return arrayList;
    }

    @Nullable
    private Object getInternal(FieldPath fieldPath, FieldValueOptions fieldValueOptions) {
        Document document = this.doc;
        if (document != null) {
            FieldValue field = document.getField(fieldPath);
            if (field != null) {
                return convertValue(field, fieldValueOptions);
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        if (r4.metadata.equals(r5.metadata) != false) goto L_0x003d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@javax.annotation.Nullable java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.firebase.firestore.DocumentSnapshot
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            com.google.firebase.firestore.DocumentSnapshot r5 = (com.google.firebase.firestore.DocumentSnapshot) r5
            com.google.firebase.firestore.FirebaseFirestore r1 = r4.firestore
            com.google.firebase.firestore.FirebaseFirestore r3 = r5.firestore
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x003c
            com.google.firebase.firestore.model.DocumentKey r1 = r4.key
            com.google.firebase.firestore.model.DocumentKey r3 = r5.key
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x003c
            com.google.firebase.firestore.model.Document r1 = r4.doc
            if (r1 != 0) goto L_0x0029
            com.google.firebase.firestore.model.Document r1 = r5.doc
            if (r1 != 0) goto L_0x003c
            goto L_0x0031
        L_0x0029:
            com.google.firebase.firestore.model.Document r3 = r5.doc
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x003c
        L_0x0031:
            com.google.firebase.firestore.SnapshotMetadata r1 = r4.metadata
            com.google.firebase.firestore.SnapshotMetadata r5 = r5.metadata
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x003c
            goto L_0x003d
        L_0x003c:
            r0 = 0
        L_0x003d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.DocumentSnapshot.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int hashCode = ((this.firestore.hashCode() * 31) + this.key.hashCode()) * 31;
        Document document = this.doc;
        return ((hashCode + (document != null ? document.hashCode() : 0)) * 31) + this.metadata.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DocumentSnapshot{key=");
        sb.append(this.key);
        sb.append(", metadata=");
        sb.append(this.metadata);
        sb.append(", doc=");
        sb.append(this.doc);
        sb.append('}');
        return sb.toString();
    }
}
