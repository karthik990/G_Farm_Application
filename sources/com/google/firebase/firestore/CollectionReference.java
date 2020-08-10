package com.google.firebase.firestore;

import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Util;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class CollectionReference extends Query {
    CollectionReference(ResourcePath resourcePath, FirebaseFirestore firebaseFirestore) {
        super(Query.atPath(resourcePath), firebaseFirestore);
        if (resourcePath.length() % 2 != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid collection reference. Collection references must have an odd number of segments, but ");
            sb.append(resourcePath.canonicalString());
            sb.append(" has ");
            sb.append(resourcePath.length());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public String getId() {
        return this.query.getPath().getLastSegment();
    }

    @Nullable
    public DocumentReference getParent() {
        ResourcePath resourcePath = (ResourcePath) this.query.getPath().popLast();
        if (resourcePath.isEmpty()) {
            return null;
        }
        return new DocumentReference(DocumentKey.fromPath(resourcePath), this.firestore);
    }

    public String getPath() {
        return this.query.getPath().canonicalString();
    }

    public DocumentReference document() {
        return document(Util.autoId());
    }

    public DocumentReference document(String str) {
        Preconditions.checkNotNull(str, "Provided document path must not be null.");
        return DocumentReference.forPath((ResourcePath) this.query.getPath().append(ResourcePath.fromString(str)), this.firestore);
    }

    public Task<DocumentReference> add(Object obj) {
        Preconditions.checkNotNull(obj, "Provided data must not be null.");
        DocumentReference document = document();
        return document.set(obj).continueWith(Executors.DIRECT_EXECUTOR, CollectionReference$$Lambda$1.lambdaFactory$(document));
    }
}
