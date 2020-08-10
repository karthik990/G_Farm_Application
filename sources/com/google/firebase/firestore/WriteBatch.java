package com.google.firebase.firestore;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Preconditions;
import com.google.firebase.firestore.core.UserData.ParsedSetData;
import com.google.firebase.firestore.core.UserData.ParsedUpdateData;
import com.google.firebase.firestore.model.mutation.DeleteMutation;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class WriteBatch {
    private boolean committed = false;
    private final FirebaseFirestore firestore;
    private final ArrayList<Mutation> mutations = new ArrayList<>();

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface Function {
        void apply(WriteBatch writeBatch);
    }

    WriteBatch(FirebaseFirestore firebaseFirestore) {
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
    }

    public WriteBatch set(DocumentReference documentReference, Object obj) {
        return set(documentReference, obj, SetOptions.OVERWRITE);
    }

    public WriteBatch set(DocumentReference documentReference, Object obj, SetOptions setOptions) {
        ParsedSetData parsedSetData;
        this.firestore.validateReference(documentReference);
        Preconditions.checkNotNull(obj, "Provided data must not be null.");
        Preconditions.checkNotNull(setOptions, "Provided options must not be null.");
        verifyNotCommitted();
        if (setOptions.isMerge()) {
            parsedSetData = this.firestore.getDataConverter().parseMergeData(obj, setOptions.getFieldMask());
        } else {
            parsedSetData = this.firestore.getDataConverter().parseSetData(obj);
        }
        this.mutations.addAll(parsedSetData.toMutationList(documentReference.getKey(), Precondition.NONE));
        return this;
    }

    public WriteBatch update(DocumentReference documentReference, Map<String, Object> map) {
        return update(documentReference, this.firestore.getDataConverter().parseUpdateData(map));
    }

    public WriteBatch update(DocumentReference documentReference, String str, Object obj, Object... objArr) {
        return update(documentReference, this.firestore.getDataConverter().parseUpdateData(Util.collectUpdateArguments(1, str, obj, objArr)));
    }

    public WriteBatch update(DocumentReference documentReference, FieldPath fieldPath, Object obj, Object... objArr) {
        return update(documentReference, this.firestore.getDataConverter().parseUpdateData(Util.collectUpdateArguments(1, fieldPath, obj, objArr)));
    }

    private WriteBatch update(DocumentReference documentReference, ParsedUpdateData parsedUpdateData) {
        this.firestore.validateReference(documentReference);
        verifyNotCommitted();
        this.mutations.addAll(parsedUpdateData.toMutationList(documentReference.getKey(), Precondition.exists(true)));
        return this;
    }

    public WriteBatch delete(DocumentReference documentReference) {
        this.firestore.validateReference(documentReference);
        verifyNotCommitted();
        this.mutations.add(new DeleteMutation(documentReference.getKey(), Precondition.NONE));
        return this;
    }

    public Task<Void> commit() {
        verifyNotCommitted();
        this.committed = true;
        if (this.mutations.size() > 0) {
            return this.firestore.getClient().write(this.mutations);
        }
        return Tasks.forResult(null);
    }

    private void verifyNotCommitted() {
        if (this.committed) {
            throw new IllegalStateException("A write batch can no longer be used after commit() has been called.");
        }
    }
}
