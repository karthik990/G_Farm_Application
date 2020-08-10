package com.google.firebase.database.core.view;

import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Change {
    private final ChildKey childKey;
    private final EventType eventType;
    private final IndexedNode indexedNode;
    private final IndexedNode oldIndexedNode;
    private final ChildKey prevName;

    private Change(EventType eventType2, IndexedNode indexedNode2, ChildKey childKey2, ChildKey childKey3, IndexedNode indexedNode3) {
        this.eventType = eventType2;
        this.indexedNode = indexedNode2;
        this.childKey = childKey2;
        this.prevName = childKey3;
        this.oldIndexedNode = indexedNode3;
    }

    public static Change valueChange(IndexedNode indexedNode2) {
        Change change = new Change(EventType.VALUE, indexedNode2, null, null, null);
        return change;
    }

    public static Change childAddedChange(ChildKey childKey2, Node node) {
        return childAddedChange(childKey2, IndexedNode.from(node));
    }

    public static Change childAddedChange(ChildKey childKey2, IndexedNode indexedNode2) {
        Change change = new Change(EventType.CHILD_ADDED, indexedNode2, childKey2, null, null);
        return change;
    }

    public static Change childRemovedChange(ChildKey childKey2, Node node) {
        return childRemovedChange(childKey2, IndexedNode.from(node));
    }

    public static Change childRemovedChange(ChildKey childKey2, IndexedNode indexedNode2) {
        Change change = new Change(EventType.CHILD_REMOVED, indexedNode2, childKey2, null, null);
        return change;
    }

    public static Change childChangedChange(ChildKey childKey2, Node node, Node node2) {
        return childChangedChange(childKey2, IndexedNode.from(node), IndexedNode.from(node2));
    }

    public static Change childChangedChange(ChildKey childKey2, IndexedNode indexedNode2, IndexedNode indexedNode3) {
        Change change = new Change(EventType.CHILD_CHANGED, indexedNode2, childKey2, null, indexedNode3);
        return change;
    }

    public static Change childMovedChange(ChildKey childKey2, Node node) {
        return childMovedChange(childKey2, IndexedNode.from(node));
    }

    public static Change childMovedChange(ChildKey childKey2, IndexedNode indexedNode2) {
        Change change = new Change(EventType.CHILD_MOVED, indexedNode2, childKey2, null, null);
        return change;
    }

    public Change changeWithPrevName(ChildKey childKey2) {
        Change change = new Change(this.eventType, this.indexedNode, this.childKey, childKey2, this.oldIndexedNode);
        return change;
    }

    public ChildKey getChildKey() {
        return this.childKey;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public IndexedNode getIndexedNode() {
        return this.indexedNode;
    }

    public ChildKey getPrevName() {
        return this.prevName;
    }

    public IndexedNode getOldIndexedNode() {
        return this.oldIndexedNode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Change: ");
        sb.append(this.eventType);
        sb.append(" ");
        sb.append(this.childKey);
        return sb.toString();
    }
}
