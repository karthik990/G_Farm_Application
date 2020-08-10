package com.google.firebase.firestore;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Preconditions;
import com.google.firebase.firestore.core.UserData.ParsedSetData;
import com.google.firebase.firestore.core.UserData.ParsedUpdateData;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Transaction {
    private final FirebaseFirestore firestore;
    private final com.google.firebase.firestore.core.Transaction transaction;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface Function<TResult> {
        TResult apply(Transaction transaction) throws FirebaseFirestoreException;
    }

    Transaction(com.google.firebase.firestore.core.Transaction transaction2, FirebaseFirestore firebaseFirestore) {
        this.transaction = (com.google.firebase.firestore.core.Transaction) Preconditions.checkNotNull(transaction2);
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
    }

    public Transaction set(DocumentReference documentReference, Object obj) {
        return set(documentReference, obj, SetOptions.OVERWRITE);
    }

    public Transaction set(DocumentReference documentReference, Object obj, SetOptions setOptions) {
        ParsedSetData parsedSetData;
        this.firestore.validateReference(documentReference);
        Preconditions.checkNotNull(obj, "Provided data must not be null.");
        Preconditions.checkNotNull(setOptions, "Provided options must not be null.");
        if (setOptions.isMerge()) {
            parsedSetData = this.firestore.getDataConverter().parseMergeData(obj, setOptions.getFieldMask());
        } else {
            parsedSetData = this.firestore.getDataConverter().parseSetData(obj);
        }
        this.transaction.set(documentReference.getKey(), parsedSetData);
        return this;
    }

    public Transaction update(DocumentReference documentReference, Map<String, Object> map) {
        return update(documentReference, this.firestore.getDataConverter().parseUpdateData(map));
    }

    public Transaction update(DocumentReference documentReference, String str, Object obj, Object... objArr) {
        return update(documentReference, this.firestore.getDataConverter().parseUpdateData(Util.collectUpdateArguments(1, str, obj, objArr)));
    }

    public Transaction update(DocumentReference documentReference, FieldPath fieldPath, Object obj, Object... objArr) {
        return update(documentReference, this.firestore.getDataConverter().parseUpdateData(Util.collectUpdateArguments(1, fieldPath, obj, objArr)));
    }

    private Transaction update(DocumentReference documentReference, ParsedUpdateData parsedUpdateData) {
        this.firestore.validateReference(documentReference);
        this.transaction.update(documentReference.getKey(), parsedUpdateData);
        return this;
    }

    public Transaction delete(DocumentReference documentReference) {
        this.firestore.validateReference(documentReference);
        this.transaction.delete(documentReference.getKey());
        return this;
    }

    private Task<DocumentSnapshot> getAsync(DocumentReference documentReference) {
        return this.transaction.lookup(Collections.singletonList(documentReference.getKey())).continueWith(Executors.DIRECT_EXECUTOR, Transaction$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ DocumentSnapshot lambda$getAsync$0(Transaction transaction2, Task task) throws Exception {
        if (task.isSuccessful()) {
            List list = (List) task.getResult();
            if (list.size() == 1) {
                MaybeDocument maybeDocument = (MaybeDocument) list.get(0);
                if (maybeDocument instanceof Document) {
                    return DocumentSnapshot.fromDocument(transaction2.firestore, (Document) maybeDocument, false, false);
                }
                if (maybeDocument instanceof NoDocument) {
                    return DocumentSnapshot.fromNoDocument(transaction2.firestore, maybeDocument.getKey(), false, false);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("BatchGetDocumentsRequest returned unexpected document type: ");
                sb.append(maybeDocument.getClass().getCanonicalName());
                throw Assert.fail(sb.toString(), new Object[0]);
            }
            throw Assert.fail("Mismatch in docs returned from document lookup.", new Object[0]);
        }
        throw task.getException();
    }

    public DocumentSnapshot get(DocumentReference documentReference) throws FirebaseFirestoreException {
        this.firestore.validateReference(documentReference);
        try {
            return (DocumentSnapshot) Tasks.await(getAsync(documentReference));
        } catch (ExecutionException e) {
            if (e.getCause() instanceof FirebaseFirestoreException) {
                throw ((FirebaseFirestoreException) e.getCause());
            }
            throw new RuntimeException(e.getCause());
        } catch (InterruptedException e2) {
            throw new RuntimeException(e2);
        }
    }
}
