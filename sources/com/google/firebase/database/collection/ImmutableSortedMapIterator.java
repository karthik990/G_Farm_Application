package com.google.firebase.database.collection;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
public class ImmutableSortedMapIterator<K, V> implements Iterator<Entry<K, V>> {
    private final boolean isReverse;
    private final ArrayDeque<LLRBValueNode<K, V>> nodeStack = new ArrayDeque<>();

    ImmutableSortedMapIterator(LLRBNode<K, V> lLRBNode, K k, Comparator<K> comparator, boolean z) {
        this.isReverse = z;
        while (!lLRBNode.isEmpty()) {
            int i = k != null ? z ? comparator.compare(k, lLRBNode.getKey()) : comparator.compare(lLRBNode.getKey(), k) : 1;
            if (i < 0) {
                if (z) {
                    lLRBNode = lLRBNode.getLeft();
                } else {
                    lLRBNode = lLRBNode.getRight();
                }
            } else if (i == 0) {
                this.nodeStack.push((LLRBValueNode) lLRBNode);
                return;
            } else {
                this.nodeStack.push((LLRBValueNode) lLRBNode);
                if (z) {
                    lLRBNode = lLRBNode.getRight();
                } else {
                    lLRBNode = lLRBNode.getLeft();
                }
            }
        }
    }

    public boolean hasNext() {
        return this.nodeStack.size() > 0;
    }

    public Entry<K, V> next() {
        try {
            LLRBValueNode lLRBValueNode = (LLRBValueNode) this.nodeStack.pop();
            SimpleEntry simpleEntry = new SimpleEntry(lLRBValueNode.getKey(), lLRBValueNode.getValue());
            if (this.isReverse) {
                for (LLRBNode left = lLRBValueNode.getLeft(); !left.isEmpty(); left = left.getRight()) {
                    this.nodeStack.push((LLRBValueNode) left);
                }
            } else {
                for (LLRBNode right = lLRBValueNode.getRight(); !right.isEmpty(); right = right.getLeft()) {
                    this.nodeStack.push((LLRBValueNode) right);
                }
            }
            return simpleEntry;
        } catch (EmptyStackException unused) {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
