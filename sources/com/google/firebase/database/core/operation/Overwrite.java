package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.operation.Operation.OperationType;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Overwrite extends Operation {
    private final Node snapshot;

    public Overwrite(OperationSource operationSource, Path path, Node node) {
        super(OperationType.Overwrite, operationSource, path);
        this.snapshot = node;
    }

    public Node getSnapshot() {
        return this.snapshot;
    }

    public Operation operationForChild(ChildKey childKey) {
        if (this.path.isEmpty()) {
            return new Overwrite(this.source, Path.getEmptyPath(), this.snapshot.getImmediateChild(childKey));
        }
        return new Overwrite(this.source, this.path.popFront(), this.snapshot);
    }

    public String toString() {
        return String.format("Overwrite { path=%s, source=%s, snapshot=%s }", new Object[]{getPath(), getSource(), this.snapshot});
    }
}
