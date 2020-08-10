package com.google.firebase.database.collection;

import com.google.firebase.database.collection.LLRBNode.Color;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import com.google.firebase.database.collection.LLRBNode.ShortCircuitingNodeVisitor;
import java.util.Comparator;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
public abstract class LLRBValueNode<K, V> implements LLRBNode<K, V> {
    private final K key;
    private LLRBNode<K, V> left;
    private final LLRBNode<K, V> right;
    private final V value;

    /* access modifiers changed from: protected */
    public abstract LLRBValueNode<K, V> copy(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2);

    /* access modifiers changed from: protected */
    public abstract Color getColor();

    public boolean isEmpty() {
        return false;
    }

    private static Color oppositeColor(LLRBNode lLRBNode) {
        return lLRBNode.isRed() ? Color.BLACK : Color.RED;
    }

    LLRBValueNode(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        this.key = k;
        this.value = v;
        if (lLRBNode == null) {
            lLRBNode = LLRBEmptyNode.getInstance();
        }
        this.left = lLRBNode;
        if (lLRBNode2 == null) {
            lLRBNode2 = LLRBEmptyNode.getInstance();
        }
        this.right = lLRBNode2;
    }

    public LLRBNode<K, V> getLeft() {
        return this.left;
    }

    public LLRBNode<K, V> getRight() {
        return this.right;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public LLRBValueNode<K, V> copy(K k, V v, Color color, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        if (k == null) {
            k = this.key;
        }
        if (v == null) {
            v = this.value;
        }
        if (lLRBNode == null) {
            lLRBNode = this.left;
        }
        if (lLRBNode2 == null) {
            lLRBNode2 = this.right;
        }
        if (color == Color.RED) {
            return new LLRBRedValueNode(k, v, lLRBNode, lLRBNode2);
        }
        return new LLRBBlackValueNode(k, v, lLRBNode, lLRBNode2);
    }

    public LLRBNode<K, V> insert(K k, V v, Comparator<K> comparator) {
        LLRBValueNode lLRBValueNode;
        int compare = comparator.compare(k, this.key);
        if (compare < 0) {
            lLRBValueNode = copy(null, null, this.left.insert(k, v, comparator), null);
        } else if (compare == 0) {
            lLRBValueNode = copy(k, v, null, null);
        } else {
            lLRBValueNode = copy(null, null, null, this.right.insert(k, v, comparator));
        }
        return lLRBValueNode.fixUp();
    }

    public LLRBNode<K, V> remove(K k, Comparator<K> comparator) {
        LLRBValueNode lLRBValueNode;
        if (comparator.compare(k, this.key) < 0) {
            LLRBValueNode moveRedLeft = (this.left.isEmpty() || this.left.isRed() || ((LLRBValueNode) this.left).left.isRed()) ? this : moveRedLeft();
            lLRBValueNode = moveRedLeft.copy(null, null, moveRedLeft.left.remove(k, comparator), null);
        } else {
            LLRBValueNode rotateRight = this.left.isRed() ? rotateRight() : this;
            if (!rotateRight.right.isEmpty() && !rotateRight.right.isRed() && !((LLRBValueNode) rotateRight.right).left.isRed()) {
                rotateRight = rotateRight.moveRedRight();
            }
            if (comparator.compare(k, rotateRight.key) == 0) {
                if (rotateRight.right.isEmpty()) {
                    return LLRBEmptyNode.getInstance();
                }
                LLRBNode min = rotateRight.right.getMin();
                rotateRight = rotateRight.copy(min.getKey(), min.getValue(), null, ((LLRBValueNode) rotateRight.right).removeMin());
            }
            lLRBValueNode = rotateRight.copy(null, null, null, rotateRight.right.remove(k, comparator));
        }
        return lLRBValueNode.fixUp();
    }

    public LLRBNode<K, V> getMin() {
        if (this.left.isEmpty()) {
            return this;
        }
        return this.left.getMin();
    }

    public LLRBNode<K, V> getMax() {
        if (this.right.isEmpty()) {
            return this;
        }
        return this.right.getMax();
    }

    public void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
        this.left.inOrderTraversal(nodeVisitor);
        nodeVisitor.visitEntry(this.key, this.value);
        this.right.inOrderTraversal(nodeVisitor);
    }

    public boolean shortCircuitingInOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        if (!this.left.shortCircuitingInOrderTraversal(shortCircuitingNodeVisitor) || !shortCircuitingNodeVisitor.shouldContinue(this.key, this.value)) {
            return false;
        }
        return this.right.shortCircuitingInOrderTraversal(shortCircuitingNodeVisitor);
    }

    public boolean shortCircuitingReverseOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        if (!this.right.shortCircuitingReverseOrderTraversal(shortCircuitingNodeVisitor) || !shortCircuitingNodeVisitor.shouldContinue(this.key, this.value)) {
            return false;
        }
        return this.left.shortCircuitingReverseOrderTraversal(shortCircuitingNodeVisitor);
    }

    /* access modifiers changed from: 0000 */
    public void setLeft(LLRBNode<K, V> lLRBNode) {
        this.left = lLRBNode;
    }

    private LLRBNode<K, V> removeMin() {
        if (this.left.isEmpty()) {
            return LLRBEmptyNode.getInstance();
        }
        LLRBValueNode moveRedLeft = (getLeft().isRed() || getLeft().getLeft().isRed()) ? this : moveRedLeft();
        return moveRedLeft.copy(null, null, ((LLRBValueNode) moveRedLeft.left).removeMin(), null).fixUp();
    }

    private LLRBValueNode<K, V> moveRedLeft() {
        LLRBValueNode<K, V> colorFlip = colorFlip();
        return colorFlip.getRight().getLeft().isRed() ? colorFlip.copy(null, null, null, ((LLRBValueNode) colorFlip.getRight()).rotateRight()).rotateLeft().colorFlip() : colorFlip;
    }

    private LLRBValueNode<K, V> moveRedRight() {
        LLRBValueNode<K, V> colorFlip = colorFlip();
        return colorFlip.getLeft().getLeft().isRed() ? colorFlip.rotateRight().colorFlip() : colorFlip;
    }

    private LLRBValueNode<K, V> fixUp() {
        LLRBValueNode rotateLeft = (!this.right.isRed() || this.left.isRed()) ? this : rotateLeft();
        if (rotateLeft.left.isRed() && ((LLRBValueNode) rotateLeft.left).left.isRed()) {
            rotateLeft = rotateLeft.rotateRight();
        }
        return (!rotateLeft.left.isRed() || !rotateLeft.right.isRed()) ? rotateLeft : rotateLeft.colorFlip();
    }

    private LLRBValueNode<K, V> rotateLeft() {
        return (LLRBValueNode) this.right.copy(null, null, getColor(), copy((Object) null, (Object) null, Color.RED, (LLRBNode) null, (LLRBNode) ((LLRBValueNode) this.right).left), null);
    }

    private LLRBValueNode<K, V> rotateRight() {
        return (LLRBValueNode) this.left.copy(null, null, getColor(), null, copy((Object) null, (Object) null, Color.RED, (LLRBNode) ((LLRBValueNode) this.left).right, (LLRBNode) null));
    }

    private LLRBValueNode<K, V> colorFlip() {
        LLRBNode<K, V> lLRBNode = this.left;
        LLRBNode copy = lLRBNode.copy(null, null, oppositeColor(lLRBNode), null, null);
        LLRBNode<K, V> lLRBNode2 = this.right;
        return copy((Object) null, (Object) null, oppositeColor(this), copy, lLRBNode2.copy(null, null, oppositeColor(lLRBNode2), null, null));
    }
}
