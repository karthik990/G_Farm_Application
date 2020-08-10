package com.google.firebase.database;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.SnapshotHolder;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class MutableData {
    /* access modifiers changed from: private */
    public final SnapshotHolder holder;
    /* access modifiers changed from: private */
    public final Path prefixPath;

    MutableData(Node node) {
        this(new SnapshotHolder(node), new Path(""));
    }

    private MutableData(SnapshotHolder snapshotHolder, Path path) {
        this.holder = snapshotHolder;
        this.prefixPath = path;
        ValidationPath.validateWithObject(this.prefixPath, getValue());
    }

    /* access modifiers changed from: 0000 */
    public Node getNode() {
        return this.holder.getNode(this.prefixPath);
    }

    public boolean hasChildren() {
        Node node = getNode();
        return !node.isLeafNode() && !node.isEmpty();
    }

    public boolean hasChild(String str) {
        return !getNode().getChild(new Path(str)).isEmpty();
    }

    public MutableData child(String str) {
        Validation.validatePathString(str);
        return new MutableData(this.holder, this.prefixPath.child(new Path(str)));
    }

    public long getChildrenCount() {
        return (long) getNode().getChildCount();
    }

    public Iterable<MutableData> getChildren() {
        Node node = getNode();
        if (node.isEmpty() || node.isLeafNode()) {
            return new Iterable<MutableData>() {
                public Iterator<MutableData> iterator() {
                    return new Iterator<MutableData>() {
                        public boolean hasNext() {
                            return false;
                        }

                        public MutableData next() {
                            throw new NoSuchElementException();
                        }

                        public void remove() {
                            throw new UnsupportedOperationException("remove called on immutable collection");
                        }
                    };
                }
            };
        }
        final Iterator it = IndexedNode.from(node).iterator();
        return new Iterable<MutableData>() {
            public Iterator<MutableData> iterator() {
                return new Iterator<MutableData>() {
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    public MutableData next() {
                        return new MutableData(MutableData.this.holder, MutableData.this.prefixPath.child(((NamedNode) it.next()).getName()));
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("remove called on immutable collection");
                    }
                };
            }
        };
    }

    public String getKey() {
        if (this.prefixPath.getBack() != null) {
            return this.prefixPath.getBack().asString();
        }
        return null;
    }

    public Object getValue() {
        return getNode().getValue();
    }

    public <T> T getValue(Class<T> cls) {
        return CustomClassMapper.convertToCustomClass(getNode().getValue(), cls);
    }

    public <T> T getValue(GenericTypeIndicator<T> genericTypeIndicator) {
        return CustomClassMapper.convertToCustomClass(getNode().getValue(), genericTypeIndicator);
    }

    public void setValue(Object obj) throws DatabaseException {
        ValidationPath.validateWithObject(this.prefixPath, obj);
        Object convertToPlainJavaTypes = CustomClassMapper.convertToPlainJavaTypes(obj);
        Validation.validateWritableObject(convertToPlainJavaTypes);
        this.holder.update(this.prefixPath, NodeUtilities.NodeFromJSON(convertToPlainJavaTypes));
    }

    public void setPriority(Object obj) {
        this.holder.update(this.prefixPath, getNode().updatePriority(PriorityUtilities.parsePriority(this.prefixPath, obj)));
    }

    public Object getPriority() {
        return getNode().getPriority().getValue();
    }

    public boolean equals(Object obj) {
        if (obj instanceof MutableData) {
            MutableData mutableData = (MutableData) obj;
            if (this.holder.equals(mutableData.holder) && this.prefixPath.equals(mutableData.prefixPath)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        ChildKey front = this.prefixPath.getFront();
        StringBuilder sb = new StringBuilder();
        sb.append("MutableData { key = ");
        sb.append(front != null ? front.asString() : "<none>");
        sb.append(", value = ");
        sb.append(this.holder.getRootNode().getValue(true));
        sb.append(" }");
        return sb.toString();
    }
}
