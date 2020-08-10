package org.jdom2.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.internal.ArrayCopy;

public final class NamespaceStack implements Iterable<Namespace> {
    private static final Namespace[] DEFAULTSEED = {Namespace.NO_NAMESPACE, Namespace.XML_NAMESPACE};
    private static final Namespace[] EMPTY = new Namespace[0];
    private static final Iterable<Namespace> EMPTYITER = new EmptyIterable();
    private static final Comparator<Namespace> NSCOMP = new Comparator<Namespace>() {
        public int compare(Namespace namespace, Namespace namespace2) {
            return namespace.getPrefix().compareTo(namespace2.getPrefix());
        }
    };
    private Namespace[][] added;
    private int depth;
    private Namespace[][] scope;

    private static final class BackwardWalker implements Iterator<Namespace> {
        int cursor = -1;
        private final Namespace[] namespaces;

        public BackwardWalker(Namespace[] namespaceArr) {
            this.namespaces = namespaceArr;
            this.cursor = namespaceArr.length - 1;
        }

        public boolean hasNext() {
            return this.cursor >= 0;
        }

        public Namespace next() {
            int i = this.cursor;
            if (i >= 0) {
                Namespace[] namespaceArr = this.namespaces;
                this.cursor = i - 1;
                return namespaceArr[i];
            }
            throw new NoSuchElementException("Cannot over-iterate...");
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove Namespaces from iterator");
        }
    }

    private static final class EmptyIterable implements Iterable<Namespace>, Iterator<Namespace> {
        public boolean hasNext() {
            return false;
        }

        public Iterator<Namespace> iterator() {
            return this;
        }

        private EmptyIterable() {
        }

        public Namespace next() {
            throw new NoSuchElementException("Can not call next() on an empty Iterator.");
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove Namespaces from iterator");
        }
    }

    private static final class ForwardWalker implements Iterator<Namespace> {
        int cursor = 0;
        private final Namespace[] namespaces;

        public ForwardWalker(Namespace[] namespaceArr) {
            this.namespaces = namespaceArr;
        }

        public boolean hasNext() {
            return this.cursor < this.namespaces.length;
        }

        public Namespace next() {
            int i = this.cursor;
            Namespace[] namespaceArr = this.namespaces;
            if (i < namespaceArr.length) {
                this.cursor = i + 1;
                return namespaceArr[i];
            }
            throw new NoSuchElementException("Cannot over-iterate...");
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove Namespaces from iterator");
        }
    }

    private static final class NamespaceIterable implements Iterable<Namespace> {
        private final boolean forward;
        private final Namespace[] namespaces;

        public NamespaceIterable(Namespace[] namespaceArr, boolean z) {
            this.forward = z;
            this.namespaces = namespaceArr;
        }

        public Iterator<Namespace> iterator() {
            return this.forward ? new ForwardWalker(this.namespaces) : new BackwardWalker(this.namespaces);
        }
    }

    private static final int binarySearch(Namespace[] namespaceArr, int i, int i2, Namespace namespace) {
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            if (namespaceArr[i4] == namespace) {
                return i4;
            }
            int compare = NSCOMP.compare(namespaceArr[i4], namespace);
            if (compare < 0) {
                i = i4 + 1;
            } else if (compare <= 0) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return (-i) - 1;
    }

    public NamespaceStack() {
        this(DEFAULTSEED);
    }

    public NamespaceStack(Namespace[] namespaceArr) {
        this.added = new Namespace[10][];
        this.scope = new Namespace[10][];
        this.depth = -1;
        this.depth++;
        Namespace[][] namespaceArr2 = this.added;
        int i = this.depth;
        namespaceArr2[i] = namespaceArr;
        this.scope[i] = namespaceArr2[i];
    }

    private static final Namespace[] checkNamespace(List<Namespace> list, Namespace namespace, Namespace[] namespaceArr) {
        if (namespace == namespaceArr[0]) {
            return namespaceArr;
        }
        if (namespace.getPrefix().equals(namespaceArr[0].getPrefix())) {
            list.add(namespace);
            Namespace[] namespaceArr2 = (Namespace[]) ArrayCopy.copyOf((E[]) namespaceArr, namespaceArr.length);
            namespaceArr2[0] = namespace;
            return namespaceArr2;
        }
        int binarySearch = binarySearch(namespaceArr, 1, namespaceArr.length, namespace);
        if (binarySearch >= 0 && namespace == namespaceArr[binarySearch]) {
            return namespaceArr;
        }
        list.add(namespace);
        if (binarySearch >= 0) {
            Namespace[] namespaceArr3 = (Namespace[]) ArrayCopy.copyOf((E[]) namespaceArr, namespaceArr.length);
            namespaceArr3[binarySearch] = namespace;
            return namespaceArr3;
        }
        Namespace[] namespaceArr4 = (Namespace[]) ArrayCopy.copyOf((E[]) namespaceArr, namespaceArr.length + 1);
        int i = (-binarySearch) - 1;
        System.arraycopy(namespaceArr4, i, namespaceArr4, i + 1, (namespaceArr4.length - i) - 1);
        namespaceArr4[i] = namespace;
        return namespaceArr4;
    }

    public void push(Element element) {
        ArrayList arrayList = new ArrayList(8);
        Namespace namespace = element.getNamespace();
        Namespace[] checkNamespace = checkNamespace(arrayList, namespace, this.scope[this.depth]);
        if (element.hasAdditionalNamespaces()) {
            for (Namespace namespace2 : element.getAdditionalNamespaces()) {
                if (namespace2 != namespace) {
                    checkNamespace = checkNamespace(arrayList, namespace2, checkNamespace);
                }
            }
        }
        if (element.hasAttributes()) {
            for (Attribute namespace3 : element.getAttributes()) {
                Namespace namespace4 = namespace3.getNamespace();
                if (!(namespace4 == Namespace.NO_NAMESPACE || namespace4 == namespace)) {
                    checkNamespace = checkNamespace(arrayList, namespace4, checkNamespace);
                }
            }
        }
        pushStack(namespace, checkNamespace, arrayList);
    }

    public void push(Attribute attribute) {
        ArrayList arrayList = new ArrayList(1);
        Namespace namespace = attribute.getNamespace();
        pushStack(namespace, checkNamespace(arrayList, namespace, this.scope[this.depth]), arrayList);
    }

    private final void pushStack(Namespace namespace, Namespace[] namespaceArr, List<Namespace> list) {
        this.depth++;
        int i = this.depth;
        Namespace[][] namespaceArr2 = this.scope;
        if (i >= namespaceArr2.length) {
            this.scope = (Namespace[][]) ArrayCopy.copyOf((E[]) namespaceArr2, namespaceArr2.length * 2);
            this.added = (Namespace[][]) ArrayCopy.copyOf((E[]) this.added, this.scope.length);
        }
        if (list.isEmpty()) {
            this.added[this.depth] = EMPTY;
        } else {
            this.added[this.depth] = (Namespace[]) list.toArray(new Namespace[list.size()]);
            Namespace[][] namespaceArr3 = this.added;
            int i2 = this.depth;
            if (namespaceArr3[i2][0] == namespace) {
                Arrays.sort(namespaceArr3[i2], 1, namespaceArr3[i2].length, NSCOMP);
            } else {
                Arrays.sort(namespaceArr3[i2], NSCOMP);
            }
        }
        if (namespace != namespaceArr[0]) {
            if (list.isEmpty()) {
                namespaceArr = (Namespace[]) ArrayCopy.copyOf((E[]) namespaceArr, namespaceArr.length);
            }
            Namespace namespace2 = namespaceArr[0];
            int i3 = ((-binarySearch(namespaceArr, 1, namespaceArr.length, namespace2)) - 1) - 1;
            System.arraycopy(namespaceArr, 1, namespaceArr, 0, i3);
            namespaceArr[i3] = namespace2;
            System.arraycopy(namespaceArr, 0, namespaceArr, 1, binarySearch(namespaceArr, 0, namespaceArr.length, namespace));
            namespaceArr[0] = namespace;
        }
        this.scope[this.depth] = namespaceArr;
    }

    public void pop() {
        int i = this.depth;
        if (i > 0) {
            this.scope[i] = null;
            this.added[i] = null;
            this.depth = i - 1;
            return;
        }
        throw new IllegalStateException("Cannot over-pop the stack.");
    }

    public Iterable<Namespace> addedForward() {
        Namespace[][] namespaceArr = this.added;
        int i = this.depth;
        if (namespaceArr[i].length == 0) {
            return EMPTYITER;
        }
        return new NamespaceIterable(namespaceArr[i], true);
    }

    public Iterable<Namespace> addedReverse() {
        Namespace[][] namespaceArr = this.added;
        int i = this.depth;
        if (namespaceArr[i].length == 0) {
            return EMPTYITER;
        }
        return new NamespaceIterable(namespaceArr[i], false);
    }

    public Iterator<Namespace> iterator() {
        return new ForwardWalker(this.scope[this.depth]);
    }

    public Namespace[] getScope() {
        Namespace[][] namespaceArr = this.scope;
        int i = this.depth;
        return (Namespace[]) ArrayCopy.copyOf((E[]) namespaceArr[i], namespaceArr[i].length);
    }

    public boolean isInScope(Namespace namespace) {
        Namespace[][] namespaceArr = this.scope;
        int i = this.depth;
        boolean z = false;
        if (namespace == namespaceArr[i][0]) {
            return true;
        }
        int binarySearch = binarySearch(namespaceArr[i], 1, namespaceArr[i].length, namespace);
        if (binarySearch >= 0 && namespace == this.scope[this.depth][binarySearch]) {
            z = true;
        }
        return z;
    }
}
