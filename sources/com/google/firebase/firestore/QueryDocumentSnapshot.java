package com.google.firebase.firestore;

import com.google.common.base.Preconditions;
import com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.util.Assert;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class QueryDocumentSnapshot extends DocumentSnapshot {
    private QueryDocumentSnapshot(FirebaseFirestore firebaseFirestore, DocumentKey documentKey, @Nullable Document document, boolean z, boolean z2) {
        super(firebaseFirestore, documentKey, document, z, z2);
    }

    static QueryDocumentSnapshot fromDocument(FirebaseFirestore firebaseFirestore, Document document, boolean z, boolean z2) {
        QueryDocumentSnapshot queryDocumentSnapshot = new QueryDocumentSnapshot(firebaseFirestore, document.getKey(), document, z, z2);
        return queryDocumentSnapshot;
    }

    public Map<String, Object> getData() {
        Map<String, Object> data = super.getData();
        Assert.hardAssert(data != null, "Data in a QueryDocumentSnapshot should be non-null", new Object[0]);
        return data;
    }

    public Map<String, Object> getData(ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        Map<String, Object> data = super.getData(serverTimestampBehavior);
        Assert.hardAssert(data != null, "Data in a QueryDocumentSnapshot should be non-null", new Object[0]);
        return data;
    }

    public <T> T toObject(Class<T> cls) {
        T object = super.toObject(cls);
        Assert.hardAssert(object != null, "Object in a QueryDocumentSnapshot should be non-null", new Object[0]);
        return object;
    }

    public <T> T toObject(Class<T> cls, ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        T object = super.toObject(cls, serverTimestampBehavior);
        Assert.hardAssert(object != null, "Object in a QueryDocumentSnapshot should be non-null", new Object[0]);
        return object;
    }
}
