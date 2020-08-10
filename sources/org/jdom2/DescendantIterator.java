package org.jdom2;

import java.util.Iterator;
import org.jdom2.internal.ArrayCopy;
import org.jdom2.util.IteratorIterable;

final class DescendantIterator implements IteratorIterable<Content> {
    private Iterator<Content> ascending = null;
    private Iterator<Content> current = null;
    private Iterator<Content> descending = null;
    private boolean hasnext = true;
    private final Parent parent;
    private int ssize = 0;
    private Object[] stack = new Object[16];

    DescendantIterator(Parent parent2) {
        this.parent = parent2;
        this.current = parent2.getContent().iterator();
        this.hasnext = this.current.hasNext();
    }

    public DescendantIterator iterator() {
        return new DescendantIterator(this.parent);
    }

    public boolean hasNext() {
        return this.hasnext;
    }

    public Content next() {
        Iterator<Content> it = this.descending;
        if (it != null) {
            this.current = it;
            this.descending = null;
        } else {
            Iterator<Content> it2 = this.ascending;
            if (it2 != null) {
                this.current = it2;
                this.ascending = null;
            }
        }
        Content content = (Content) this.current.next();
        if (content instanceof Element) {
            Element element = (Element) content;
            if (element.getContentSize() > 0) {
                this.descending = element.getContent().iterator();
                int i = this.ssize;
                Object[] objArr = this.stack;
                if (i >= objArr.length) {
                    this.stack = ArrayCopy.copyOf((E[]) objArr, i + 16);
                }
                Object[] objArr2 = this.stack;
                int i2 = this.ssize;
                this.ssize = i2 + 1;
                objArr2[i2] = this.current;
                return content;
            }
        }
        if (this.current.hasNext()) {
            return content;
        }
        do {
            int i3 = this.ssize;
            if (i3 > 0) {
                Object[] objArr3 = this.stack;
                int i4 = i3 - 1;
                this.ssize = i4;
                this.ascending = (Iterator) objArr3[i4];
                objArr3[this.ssize] = null;
            } else {
                this.ascending = null;
                this.hasnext = false;
                return content;
            }
        } while (!this.ascending.hasNext());
        return content;
    }

    public void remove() {
        this.current.remove();
        this.descending = null;
        if (!this.current.hasNext() && this.ascending == null) {
            do {
                int i = this.ssize;
                if (i > 0) {
                    Object[] objArr = this.stack;
                    int i2 = i - 1;
                    this.ssize = i2;
                    Iterator<Content> it = (Iterator) objArr[i2];
                    objArr[this.ssize] = null;
                    this.ascending = it;
                } else {
                    this.ascending = null;
                    this.hasnext = false;
                }
            } while (!this.ascending.hasNext());
        }
    }
}
