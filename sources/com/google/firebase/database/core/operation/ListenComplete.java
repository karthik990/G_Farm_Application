package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.operation.Operation.OperationType;
import com.google.firebase.database.snapshot.ChildKey;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ListenComplete extends Operation {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public ListenComplete(OperationSource operationSource, Path path) {
        super(OperationType.ListenComplete, operationSource, path);
    }

    public Operation operationForChild(ChildKey childKey) {
        if (this.path.isEmpty()) {
            return new ListenComplete(this.source, Path.getEmptyPath());
        }
        return new ListenComplete(this.source, this.path.popFront());
    }

    public String toString() {
        return String.format("ListenComplete { path=%s, source=%s }", new Object[]{getPath(), getSource()});
    }
}
