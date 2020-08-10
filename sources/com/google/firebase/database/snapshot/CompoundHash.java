package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.NodeSizeEstimator;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.ChildrenNode.ChildVisitor;
import com.google.firebase.database.snapshot.Node.HashVersion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class CompoundHash {
    private final List<String> hashes;
    private final List<Path> posts;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static class CompoundHashBuilder {
        /* access modifiers changed from: private */
        public final List<String> currentHashes = new ArrayList();
        private Stack<ChildKey> currentPath = new Stack<>();
        private int currentPathDepth;
        /* access modifiers changed from: private */
        public final List<Path> currentPaths = new ArrayList();
        private int lastLeafDepth = -1;
        private boolean needsComma = true;
        private StringBuilder optHashValueBuilder = null;
        private final SplitStrategy splitStrategy;

        public CompoundHashBuilder(SplitStrategy splitStrategy2) {
            this.splitStrategy = splitStrategy2;
        }

        public boolean buildingRange() {
            return this.optHashValueBuilder != null;
        }

        public int currentHashLength() {
            return this.optHashValueBuilder.length();
        }

        public Path currentPath() {
            return currentPath(this.currentPathDepth);
        }

        private Path currentPath(int i) {
            ChildKey[] childKeyArr = new ChildKey[i];
            for (int i2 = 0; i2 < i; i2++) {
                childKeyArr[i2] = (ChildKey) this.currentPath.get(i2);
            }
            return new Path(childKeyArr);
        }

        private void ensureRange() {
            if (!buildingRange()) {
                this.optHashValueBuilder = new StringBuilder();
                this.optHashValueBuilder.append("(");
                Iterator it = currentPath(this.currentPathDepth).iterator();
                while (it.hasNext()) {
                    appendKey(this.optHashValueBuilder, (ChildKey) it.next());
                    this.optHashValueBuilder.append(":(");
                }
                this.needsComma = false;
            }
        }

        private void appendKey(StringBuilder sb, ChildKey childKey) {
            sb.append(Utilities.stringHashV2Representation(childKey.asString()));
        }

        /* access modifiers changed from: private */
        public void processLeaf(LeafNode<?> leafNode) {
            ensureRange();
            this.lastLeafDepth = this.currentPathDepth;
            this.optHashValueBuilder.append(leafNode.getHashRepresentation(HashVersion.V2));
            this.needsComma = true;
            if (this.splitStrategy.shouldSplit(this)) {
                endRange();
            }
        }

        /* access modifiers changed from: private */
        public void startChild(ChildKey childKey) {
            ensureRange();
            if (this.needsComma) {
                this.optHashValueBuilder.append(",");
            }
            appendKey(this.optHashValueBuilder, childKey);
            this.optHashValueBuilder.append(":(");
            if (this.currentPathDepth == this.currentPath.size()) {
                this.currentPath.add(childKey);
            } else {
                this.currentPath.set(this.currentPathDepth, childKey);
            }
            this.currentPathDepth++;
            this.needsComma = false;
        }

        /* access modifiers changed from: private */
        public void endChild() {
            this.currentPathDepth--;
            if (buildingRange()) {
                this.optHashValueBuilder.append(")");
            }
            this.needsComma = true;
        }

        /* access modifiers changed from: private */
        public void finishHashing() {
            Utilities.hardAssert(this.currentPathDepth == 0, "Can't finish hashing in the middle processing a child");
            if (buildingRange()) {
                endRange();
            }
            this.currentHashes.add("");
        }

        private void endRange() {
            Utilities.hardAssert(buildingRange(), "Can't end range without starting a range!");
            int i = 0;
            while (true) {
                String str = ")";
                if (i < this.currentPathDepth) {
                    this.optHashValueBuilder.append(str);
                    i++;
                } else {
                    this.optHashValueBuilder.append(str);
                    Path currentPath2 = currentPath(this.lastLeafDepth);
                    this.currentHashes.add(Utilities.sha1HexDigest(this.optHashValueBuilder.toString()));
                    this.currentPaths.add(currentPath2);
                    this.optHashValueBuilder = null;
                    return;
                }
            }
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class SimpleSizeSplitStrategy implements SplitStrategy {
        private final long splitThreshold;

        public SimpleSizeSplitStrategy(Node node) {
            this.splitThreshold = Math.max(512, (long) Math.sqrt((double) (NodeSizeEstimator.estimateSerializedNodeSize(node) * 100)));
        }

        public boolean shouldSplit(CompoundHashBuilder compoundHashBuilder) {
            return ((long) compoundHashBuilder.currentHashLength()) > this.splitThreshold && (compoundHashBuilder.currentPath().isEmpty() || !compoundHashBuilder.currentPath().getBack().equals(ChildKey.getPriorityKey()));
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface SplitStrategy {
        boolean shouldSplit(CompoundHashBuilder compoundHashBuilder);
    }

    private CompoundHash(List<Path> list, List<String> list2) {
        if (list.size() == list2.size() - 1) {
            this.posts = list;
            this.hashes = list2;
            return;
        }
        throw new IllegalArgumentException("Number of posts need to be n-1 for n hashes in CompoundHash");
    }

    public List<Path> getPosts() {
        return Collections.unmodifiableList(this.posts);
    }

    public List<String> getHashes() {
        return Collections.unmodifiableList(this.hashes);
    }

    public static CompoundHash fromNode(Node node) {
        return fromNode(node, new SimpleSizeSplitStrategy(node));
    }

    public static CompoundHash fromNode(Node node, SplitStrategy splitStrategy) {
        if (node.isEmpty()) {
            return new CompoundHash(Collections.emptyList(), Collections.singletonList(""));
        }
        CompoundHashBuilder compoundHashBuilder = new CompoundHashBuilder(splitStrategy);
        processNode(node, compoundHashBuilder);
        compoundHashBuilder.finishHashing();
        return new CompoundHash(compoundHashBuilder.currentPaths, compoundHashBuilder.currentHashes);
    }

    /* access modifiers changed from: private */
    public static void processNode(Node node, final CompoundHashBuilder compoundHashBuilder) {
        if (node.isLeafNode()) {
            compoundHashBuilder.processLeaf((LeafNode) node);
        } else if (node.isEmpty()) {
            throw new IllegalArgumentException("Can't calculate hash on empty node!");
        } else if (node instanceof ChildrenNode) {
            ((ChildrenNode) node).forEachChild(new ChildVisitor() {
                public void visitChild(ChildKey childKey, Node node) {
                    CompoundHashBuilder.this.startChild(childKey);
                    CompoundHash.processNode(node, CompoundHashBuilder.this);
                    CompoundHashBuilder.this.endChild();
                }
            }, true);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected children node, but got: ");
            sb.append(node);
            throw new IllegalStateException(sb.toString());
        }
    }
}
