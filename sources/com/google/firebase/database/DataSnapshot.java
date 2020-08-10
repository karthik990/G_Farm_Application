package com.google.firebase.database;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DataSnapshot {
    private final IndexedNode node;
    /* access modifiers changed from: private */
    public final DatabaseReference query;

    DataSnapshot(DatabaseReference databaseReference, IndexedNode indexedNode) {
        this.node = indexedNode;
        this.query = databaseReference;
    }

    public DataSnapshot child(String str) {
        return new DataSnapshot(this.query.child(str), IndexedNode.from(this.node.getNode().getChild(new Path(str))));
    }

    public boolean hasChild(String str) {
        if (this.query.getParent() == null) {
            Validation.validateRootPathString(str);
        } else {
            Validation.validatePathString(str);
        }
        return !this.node.getNode().getChild(new Path(str)).isEmpty();
    }

    public boolean hasChildren() {
        return this.node.getNode().getChildCount() > 0;
    }

    public boolean exists() {
        return !this.node.getNode().isEmpty();
    }

    public Object getValue() {
        return this.node.getNode().getValue();
    }

    public Object getValue(boolean z) {
        return this.node.getNode().getValue(z);
    }

    public <T> T getValue(Class<T> cls) {
        return CustomClassMapper.convertToCustomClass(this.node.getNode().getValue(), cls);
    }

    public <T> T getValue(GenericTypeIndicator<T> genericTypeIndicator) {
        return CustomClassMapper.convertToCustomClass(this.node.getNode().getValue(), genericTypeIndicator);
    }

    public long getChildrenCount() {
        return (long) this.node.getNode().getChildCount();
    }

    public DatabaseReference getRef() {
        return this.query;
    }

    public String getKey() {
        return this.query.getKey();
    }

    public Iterable<DataSnapshot> getChildren() {
        final Iterator it = this.node.iterator();
        return new Iterable<DataSnapshot>() {
            public Iterator<DataSnapshot> iterator() {
                return new Iterator<DataSnapshot>() {
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    public DataSnapshot next() {
                        NamedNode namedNode = (NamedNode) it.next();
                        return new DataSnapshot(DataSnapshot.this.query.child(namedNode.getName().asString()), IndexedNode.from(namedNode.getNode()));
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("remove called on immutable collection");
                    }
                };
            }
        };
    }

    public Object getPriority() {
        Object value = this.node.getNode().getPriority().getValue();
        return value instanceof Long ? Double.valueOf((double) ((Long) value).longValue()) : value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataSnapshot { key = ");
        sb.append(this.query.getKey());
        sb.append(", value = ");
        sb.append(this.node.getNode().getValue(true));
        sb.append(" }");
        return sb.toString();
    }
}
