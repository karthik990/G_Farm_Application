package org.jdom2;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import org.jdom2.filter.Filter;
import org.jdom2.internal.ArrayCopy;

final class ContentList extends AbstractList<Content> implements RandomAccess {
    private static final int INITIAL_ARRAY_SIZE = 4;
    private transient int dataModiCount = Integer.MIN_VALUE;
    /* access modifiers changed from: private */
    public Content[] elementData = null;
    private final Parent parent;
    /* access modifiers changed from: private */
    public int size;
    private transient int sizeModCount = Integer.MIN_VALUE;

    private final class CLIterator implements Iterator<Content> {
        private boolean canremove;
        private int cursor;
        private int expect;

        private CLIterator() {
            this.expect = -1;
            this.cursor = 0;
            this.canremove = false;
            this.expect = ContentList.this.getModCount();
        }

        public boolean hasNext() {
            return this.cursor < ContentList.this.size;
        }

        public Content next() {
            if (ContentList.this.getModCount() != this.expect) {
                throw new ConcurrentModificationException("ContentList was modified outside of this Iterator");
            } else if (this.cursor < ContentList.this.size) {
                this.canremove = true;
                Content[] access$300 = ContentList.this.elementData;
                int i = this.cursor;
                this.cursor = i + 1;
                return access$300[i];
            } else {
                throw new NoSuchElementException("Iterated beyond the end of the ContentList.");
            }
        }

        public void remove() {
            if (ContentList.this.getModCount() != this.expect) {
                throw new ConcurrentModificationException("ContentList was modified outside of this Iterator");
            } else if (this.canremove) {
                this.canremove = false;
                ContentList contentList = ContentList.this;
                int i = this.cursor - 1;
                this.cursor = i;
                contentList.remove(i);
                this.expect = ContentList.this.getModCount();
            } else {
                throw new IllegalStateException("Can only remove() content after a call to next()");
            }
        }
    }

    private final class CLListIterator implements ListIterator<Content> {
        private boolean canremove = false;
        private boolean canset = false;
        private int cursor = -1;
        private int expectedmod = -1;
        private boolean forward = false;

        CLListIterator(int i) {
            this.expectedmod = ContentList.this.getModCount();
            this.forward = false;
            ContentList.this.checkIndex(i, false);
            this.cursor = i;
        }

        private void checkConcurrent() {
            if (this.expectedmod != ContentList.this.getModCount()) {
                throw new ConcurrentModificationException("The ContentList supporting this iterator has been modified bysomething other than this Iterator.");
            }
        }

        public boolean hasNext() {
            return (this.forward ? this.cursor + 1 : this.cursor) < ContentList.this.size;
        }

        public boolean hasPrevious() {
            return (this.forward ? this.cursor : this.cursor - 1) >= 0;
        }

        public int nextIndex() {
            return this.forward ? this.cursor + 1 : this.cursor;
        }

        public int previousIndex() {
            return this.forward ? this.cursor : this.cursor - 1;
        }

        public Content next() {
            checkConcurrent();
            int i = this.forward ? this.cursor + 1 : this.cursor;
            if (i < ContentList.this.size) {
                this.cursor = i;
                this.forward = true;
                this.canremove = true;
                this.canset = true;
                return ContentList.this.elementData[this.cursor];
            }
            throw new NoSuchElementException("next() is beyond the end of the Iterator");
        }

        public Content previous() {
            checkConcurrent();
            int i = this.forward ? this.cursor : this.cursor - 1;
            if (i >= 0) {
                this.cursor = i;
                this.forward = false;
                this.canremove = true;
                this.canset = true;
                return ContentList.this.elementData[this.cursor];
            }
            throw new NoSuchElementException("previous() is beyond the beginning of the Iterator");
        }

        public void add(Content content) {
            checkConcurrent();
            int i = this.forward ? this.cursor + 1 : this.cursor;
            ContentList.this.add(i, content);
            this.expectedmod = ContentList.this.getModCount();
            this.canset = false;
            this.canremove = false;
            this.cursor = i;
            this.forward = true;
        }

        public void remove() {
            checkConcurrent();
            if (this.canremove) {
                ContentList.this.remove(this.cursor);
                this.forward = false;
                this.expectedmod = ContentList.this.getModCount();
                this.canremove = false;
                this.canset = false;
                return;
            }
            throw new IllegalStateException("Can not remove an element unless either next() or previous() has been called since the last remove()");
        }

        public void set(Content content) {
            checkConcurrent();
            if (this.canset) {
                ContentList.this.set(this.cursor, content);
                this.expectedmod = ContentList.this.getModCount();
                return;
            }
            throw new IllegalStateException("Can not set an element unless either next() or previous() has been called since the last remove() or set()");
        }
    }

    class FilterList<F extends Content> extends AbstractList<F> {
        int[] backingpos = new int[(ContentList.this.size + 4)];
        int backingsize = 0;
        final Filter<F> filter;
        int xdata = -1;

        FilterList(Filter<F> filter2) {
            this.filter = filter2;
        }

        public boolean isEmpty() {
            return resync(0) == ContentList.this.size;
        }

        /* access modifiers changed from: private */
        public final int resync(int i) {
            int i2 = 0;
            if (this.xdata != ContentList.this.getDataModCount()) {
                this.xdata = ContentList.this.getDataModCount();
                this.backingsize = 0;
                if (ContentList.this.size >= this.backingpos.length) {
                    this.backingpos = new int[(ContentList.this.size + 1)];
                }
            }
            if (i >= 0 && i < this.backingsize) {
                return this.backingpos[i];
            }
            int i3 = this.backingsize;
            if (i3 > 0) {
                i2 = this.backingpos[i3 - 1] + 1;
            }
            while (i2 < ContentList.this.size) {
                if (((Content) this.filter.filter((Object) ContentList.this.elementData[i2])) != null) {
                    int[] iArr = this.backingpos;
                    int i4 = this.backingsize;
                    iArr[i4] = i2;
                    this.backingsize = i4 + 1;
                    if (i4 == i) {
                        return i2;
                    }
                }
                i2++;
            }
            return ContentList.this.size;
        }

        public void add(int i, Content content) {
            String str = " Size: ";
            String str2 = "Index: ";
            if (i >= 0) {
                int resync = resync(i);
                if (resync == ContentList.this.size && i > size()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(i);
                    sb.append(str);
                    sb.append(size());
                    throw new IndexOutOfBoundsException(sb.toString());
                } else if (this.filter.matches(content)) {
                    ContentList.this.add(resync, content);
                    if (this.backingpos.length <= ContentList.this.size) {
                        int[] iArr = this.backingpos;
                        this.backingpos = ArrayCopy.copyOf(iArr, iArr.length + 1);
                    }
                    this.backingpos[i] = resync;
                    this.backingsize = i + 1;
                    this.xdata = ContentList.this.getDataModCount();
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Filter won't allow the ");
                    sb2.append(content.getClass().getName());
                    sb2.append(" '");
                    sb2.append(content);
                    sb2.append("' to be added to the list");
                    throw new IllegalAddException(sb2.toString());
                }
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(i);
                sb3.append(str);
                sb3.append(size());
                throw new IndexOutOfBoundsException(sb3.toString());
            }
        }

        /* JADX INFO: finally extract failed */
        public boolean addAll(int i, Collection<? extends F> collection) {
            if (collection != null) {
                String str = " Size: ";
                String str2 = "Index: ";
                if (i >= 0) {
                    int resync = resync(i);
                    if (resync != ContentList.this.size || i <= size()) {
                        int size = collection.size();
                        int i2 = 0;
                        if (size == 0) {
                            return false;
                        }
                        ContentList contentList = ContentList.this;
                        contentList.ensureCapacity(contentList.size() + size);
                        int access$100 = ContentList.this.getModCount();
                        int access$500 = ContentList.this.getDataModCount();
                        try {
                            Iterator it = collection.iterator();
                            while (it.hasNext()) {
                                Content content = (Content) it.next();
                                if (content == null) {
                                    throw new NullPointerException("Cannot add null content");
                                } else if (this.filter.matches(content)) {
                                    int i3 = resync + i2;
                                    ContentList.this.add(i3, content);
                                    if (this.backingpos.length <= ContentList.this.size) {
                                        this.backingpos = ArrayCopy.copyOf(this.backingpos, this.backingpos.length + size);
                                    }
                                    int i4 = i + i2;
                                    this.backingpos[i4] = i3;
                                    this.backingsize = i4 + 1;
                                    this.xdata = ContentList.this.getDataModCount();
                                    i2++;
                                } else {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Filter won't allow the ");
                                    sb.append(content.getClass().getName());
                                    sb.append(" '");
                                    sb.append(content);
                                    sb.append("' to be added to the list");
                                    throw new IllegalAddException(sb.toString());
                                }
                            }
                            return true;
                        } catch (Throwable th) {
                            while (true) {
                                i2--;
                                if (i2 >= 0) {
                                    ContentList.this.remove(resync + i2);
                                } else {
                                    ContentList.this.setModCount(access$100, access$500);
                                    this.backingsize = i;
                                    this.xdata = access$100;
                                    throw th;
                                }
                            }
                        }
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(i);
                        sb2.append(str);
                        sb2.append(size());
                        throw new IndexOutOfBoundsException(sb2.toString());
                    }
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append(i);
                    sb3.append(str);
                    sb3.append(size());
                    throw new IndexOutOfBoundsException(sb3.toString());
                }
            } else {
                throw new NullPointerException("Cannot add a null collection");
            }
        }

        public F get(int i) {
            String str = " Size: ";
            String str2 = "Index: ";
            if (i >= 0) {
                int resync = resync(i);
                if (resync != ContentList.this.size) {
                    return (Content) this.filter.filter((Object) ContentList.this.get(resync));
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(i);
                sb.append(str);
                sb.append(size());
                throw new IndexOutOfBoundsException(sb.toString());
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(i);
            sb2.append(str);
            sb2.append(size());
            throw new IndexOutOfBoundsException(sb2.toString());
        }

        public Iterator<F> iterator() {
            return new FilterListIterator(this, 0);
        }

        public ListIterator<F> listIterator() {
            return new FilterListIterator(this, 0);
        }

        public ListIterator<F> listIterator(int i) {
            return new FilterListIterator(this, i);
        }

        public F remove(int i) {
            String str = " Size: ";
            String str2 = "Index: ";
            if (i >= 0) {
                int resync = resync(i);
                if (resync != ContentList.this.size) {
                    Content remove = ContentList.this.remove(resync);
                    this.backingsize = i;
                    this.xdata = ContentList.this.getDataModCount();
                    return (Content) this.filter.filter((Object) remove);
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(i);
                sb.append(str);
                sb.append(size());
                throw new IndexOutOfBoundsException(sb.toString());
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(i);
            sb2.append(str);
            sb2.append(size());
            throw new IndexOutOfBoundsException(sb2.toString());
        }

        public F set(int i, F f) {
            String str = " Size: ";
            String str2 = "Index: ";
            if (i >= 0) {
                int resync = resync(i);
                if (resync != ContentList.this.size) {
                    Content content = (Content) this.filter.filter((Object) f);
                    if (content != null) {
                        F f2 = (Content) this.filter.filter((Object) ContentList.this.set(resync, content));
                        this.xdata = ContentList.this.getDataModCount();
                        return f2;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Filter won't allow index ");
                    sb.append(i);
                    sb.append(" to be set to ");
                    sb.append(f.getClass().getName());
                    throw new IllegalAddException(sb.toString());
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(i);
                sb2.append(str);
                sb2.append(size());
                throw new IndexOutOfBoundsException(sb2.toString());
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str2);
            sb3.append(i);
            sb3.append(str);
            sb3.append(size());
            throw new IndexOutOfBoundsException(sb3.toString());
        }

        public int size() {
            resync(-1);
            return this.backingsize;
        }

        private final int fbinarySearch(int[] iArr, int i, int i2, Comparator<? super F> comparator) {
            int i3 = i - 1;
            Content content = ContentList.this.elementData[this.backingpos[i2]];
            int i4 = 0;
            while (i4 <= i3) {
                int i5 = (i4 + i3) >>> 1;
                int compare = comparator.compare(content, ContentList.this.elementData[iArr[i5]]);
                if (compare == 0) {
                    while (compare == 0 && i5 < i3) {
                        int i6 = i5 + 1;
                        if (comparator.compare(content, ContentList.this.elementData[iArr[i6]]) != 0) {
                            break;
                        }
                        i5 = i6;
                    }
                    return i5 + 1;
                } else if (compare < 0) {
                    i3 = i5 - 1;
                } else {
                    i4 = i5 + 1;
                }
            }
            return i4;
        }

        public final void sort(Comparator<? super F> comparator) {
            if (comparator != null) {
                int size = size();
                int[] iArr = new int[size];
                for (int i = 0; i < size; i++) {
                    int fbinarySearch = fbinarySearch(iArr, i, i, comparator);
                    if (fbinarySearch < i) {
                        System.arraycopy(iArr, fbinarySearch, iArr, fbinarySearch + 1, i - fbinarySearch);
                    }
                    iArr[fbinarySearch] = this.backingpos[i];
                }
                ContentList.this.sortInPlace(iArr);
            }
        }
    }

    final class FilterListIterator<F extends Content> implements ListIterator<F> {
        private boolean canremove = false;
        private boolean canset = false;
        private int cursor = -1;
        private int expectedmod = -1;
        private final FilterList<F> filterlist;
        private boolean forward = false;

        FilterListIterator(FilterList<F> filterList, int i) {
            this.filterlist = filterList;
            this.expectedmod = ContentList.this.getModCount();
            this.forward = false;
            String str = " Size: ";
            String str2 = "Index: ";
            if (i < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(i);
                sb.append(str);
                sb.append(this.filterlist.size());
                throw new IndexOutOfBoundsException(sb.toString());
            } else if (this.filterlist.resync(i) != ContentList.this.size || i <= this.filterlist.size()) {
                this.cursor = i;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(i);
                sb2.append(str);
                sb2.append(this.filterlist.size());
                throw new IndexOutOfBoundsException(sb2.toString());
            }
        }

        private void checkConcurrent() {
            if (this.expectedmod != ContentList.this.getModCount()) {
                throw new ConcurrentModificationException("The ContentList supporting the FilterList this iterator is processing has been modified by something other than this Iterator.");
            }
        }

        public boolean hasNext() {
            return this.filterlist.resync(this.forward ? this.cursor + 1 : this.cursor) < ContentList.this.size;
        }

        public boolean hasPrevious() {
            return (this.forward ? this.cursor : this.cursor - 1) >= 0;
        }

        public int nextIndex() {
            return this.forward ? this.cursor + 1 : this.cursor;
        }

        public int previousIndex() {
            return this.forward ? this.cursor : this.cursor - 1;
        }

        public F next() {
            checkConcurrent();
            int i = this.forward ? this.cursor + 1 : this.cursor;
            if (this.filterlist.resync(i) < ContentList.this.size) {
                this.cursor = i;
                this.forward = true;
                this.canremove = true;
                this.canset = true;
                return this.filterlist.get(this.cursor);
            }
            throw new NoSuchElementException("next() is beyond the end of the Iterator");
        }

        public F previous() {
            checkConcurrent();
            int i = this.forward ? this.cursor : this.cursor - 1;
            if (i >= 0) {
                this.cursor = i;
                this.forward = false;
                this.canremove = true;
                this.canset = true;
                return this.filterlist.get(this.cursor);
            }
            throw new NoSuchElementException("previous() is beyond the beginning of the Iterator");
        }

        public void add(Content content) {
            checkConcurrent();
            int i = this.forward ? this.cursor + 1 : this.cursor;
            this.filterlist.add(i, content);
            this.expectedmod = ContentList.this.getModCount();
            this.canset = false;
            this.canremove = false;
            this.cursor = i;
            this.forward = true;
        }

        public void remove() {
            checkConcurrent();
            if (this.canremove) {
                this.filterlist.remove(this.cursor);
                this.forward = false;
                this.expectedmod = ContentList.this.getModCount();
                this.canremove = false;
                this.canset = false;
                return;
            }
            throw new IllegalStateException("Can not remove an element unless either next() or previous() has been called since the last remove()");
        }

        public void set(F f) {
            checkConcurrent();
            if (this.canset) {
                this.filterlist.set(this.cursor, f);
                this.expectedmod = ContentList.this.getModCount();
                return;
            }
            throw new IllegalStateException("Can not set an element unless either next() or previous() has been called since the last remove() or set()");
        }
    }

    ContentList(Parent parent2) {
        this.parent = parent2;
    }

    /* access modifiers changed from: 0000 */
    public final void uncheckedAddContent(Content content) {
        content.parent = this.parent;
        ensureCapacity(this.size + 1);
        Content[] contentArr = this.elementData;
        int i = this.size;
        this.size = i + 1;
        contentArr[i] = content;
        incModCount();
    }

    /* access modifiers changed from: private */
    public final void setModCount(int i, int i2) {
        this.sizeModCount = i;
        this.dataModiCount = i2;
    }

    /* access modifiers changed from: private */
    public final int getModCount() {
        return this.sizeModCount;
    }

    private final void incModCount() {
        this.dataModiCount++;
        this.sizeModCount++;
    }

    private final void incDataModOnly() {
        this.dataModiCount++;
    }

    /* access modifiers changed from: private */
    public final int getDataModCount() {
        return this.dataModiCount;
    }

    /* access modifiers changed from: private */
    public final void checkIndex(int i, boolean z) {
        int i2 = z ? this.size - 1 : this.size;
        if (i < 0 || i > i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Index: ");
            sb.append(i);
            sb.append(" Size: ");
            sb.append(this.size);
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    private final void checkPreConditions(Content content, int i, boolean z) {
        if (content != null) {
            checkIndex(i, z);
            if (content.getParent() != null) {
                Parent parent2 = content.getParent();
                if (parent2 instanceof Document) {
                    throw new IllegalAddException((Element) content, "The Content already has an existing parent document");
                }
                StringBuilder sb = new StringBuilder();
                sb.append("The Content already has an existing parent \"");
                sb.append(((Element) parent2).getQualifiedName());
                sb.append("\"");
                throw new IllegalAddException(sb.toString());
            }
            Parent parent3 = this.parent;
            if (content == parent3) {
                throw new IllegalAddException("The Element cannot be added to itself");
            } else if ((parent3 instanceof Element) && (content instanceof Element) && ((Element) content).isAncestor((Element) parent3)) {
                throw new IllegalAddException("The Element cannot be added as a descendent of itself");
            }
        } else {
            throw new NullPointerException("Cannot add null object");
        }
    }

    public void add(int i, Content content) {
        checkPreConditions(content, i, false);
        this.parent.canContainContent(content, i, false);
        content.setParent(this.parent);
        ensureCapacity(this.size + 1);
        int i2 = this.size;
        if (i == i2) {
            Content[] contentArr = this.elementData;
            this.size = i2 + 1;
            contentArr[i2] = content;
        } else {
            Content[] contentArr2 = this.elementData;
            System.arraycopy(contentArr2, i, contentArr2, i + 1, i2 - i);
            this.elementData[i] = content;
            this.size++;
        }
        incModCount();
    }

    public boolean addAll(Collection<? extends Content> collection) {
        return addAll(this.size, collection);
    }

    public boolean addAll(int i, Collection<? extends Content> collection) {
        if (collection != null) {
            boolean z = 0;
            checkIndex(i, z);
            if (collection.isEmpty()) {
                return z;
            }
            int size2 = collection.size();
            if (size2 == 1) {
                add(i, (Content) collection.iterator().next());
                return true;
            }
            ensureCapacity(size() + size2);
            int modCount = getModCount();
            int dataModCount = getDataModCount();
            try {
                int i2 = z;
                for (Content add : collection) {
                    add(i + i2, add);
                }
                return true;
            } finally {
                int i3 = z;
                while (true) {
                    int i4 = i3 - 1;
                    if (i4 >= 0) {
                        remove(i + i4);
                        i3 = i4;
                    } else {
                        setModCount(modCount, dataModCount);
                    }
                }
            }
        } else {
            throw new NullPointerException("Can not add a null collection to the ContentList");
        }
    }

    public void clear() {
        if (this.elementData != null) {
            for (int i = 0; i < this.size; i++) {
                removeParent(this.elementData[i]);
            }
            this.elementData = null;
            this.size = 0;
        }
        incModCount();
    }

    /* access modifiers changed from: 0000 */
    public void clearAndSet(Collection<? extends Content> collection) {
        if (collection == null || collection.isEmpty()) {
            clear();
            return;
        }
        Content[] contentArr = this.elementData;
        int i = this.size;
        int modCount = getModCount();
        int dataModCount = getDataModCount();
        while (true) {
            int i2 = this.size;
            if (i2 > 0) {
                int i3 = i2 - 1;
                this.size = i3;
                contentArr[i3].setParent(null);
            } else {
                this.size = 0;
                this.elementData = null;
                try {
                    addAll(0, collection);
                    return;
                } catch (Throwable th) {
                    this.elementData = contentArr;
                    while (true) {
                        int i4 = this.size;
                        if (i4 < i) {
                            Content[] contentArr2 = this.elementData;
                            this.size = i4 + 1;
                            contentArr2[i4].setParent(this.parent);
                        } else {
                            setModCount(modCount, dataModCount);
                            throw th;
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureCapacity(int i) {
        Content[] contentArr = this.elementData;
        if (contentArr == null) {
            this.elementData = new Content[Math.max(i, 4)];
        } else if (i >= contentArr.length) {
            int i2 = ((this.size * 3) / 2) + 1;
            if (i2 >= i) {
                i = i2;
            }
            this.elementData = (Content[]) ArrayCopy.copyOf((E[]) contentArr, i);
        }
    }

    public Content get(int i) {
        checkIndex(i, true);
        return this.elementData[i];
    }

    /* access modifiers changed from: 0000 */
    public <E extends Content> List<E> getView(Filter<E> filter) {
        return new FilterList(filter);
    }

    /* access modifiers changed from: 0000 */
    public int indexOfFirstElement() {
        if (this.elementData != null) {
            for (int i = 0; i < this.size; i++) {
                if (this.elementData[i] instanceof Element) {
                    return i;
                }
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public int indexOfDocType() {
        if (this.elementData != null) {
            for (int i = 0; i < this.size; i++) {
                if (this.elementData[i] instanceof DocType) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Content remove(int i) {
        checkIndex(i, true);
        Content content = this.elementData[i];
        removeParent(content);
        Content[] contentArr = this.elementData;
        System.arraycopy(contentArr, i + 1, contentArr, i, (this.size - i) - 1);
        Content[] contentArr2 = this.elementData;
        int i2 = this.size - 1;
        this.size = i2;
        contentArr2[i2] = null;
        incModCount();
        return content;
    }

    private static void removeParent(Content content) {
        content.setParent(null);
    }

    public Content set(int i, Content content) {
        checkPreConditions(content, i, true);
        this.parent.canContainContent(content, i, true);
        Content content2 = this.elementData[i];
        removeParent(content2);
        content.setParent(this.parent);
        this.elementData[i] = content;
        incDataModOnly();
        return content2;
    }

    public int size() {
        return this.size;
    }

    public Iterator<Content> iterator() {
        return new CLIterator();
    }

    public ListIterator<Content> listIterator() {
        return new CLListIterator(0);
    }

    public ListIterator<Content> listIterator(int i) {
        return new CLListIterator(i);
    }

    public String toString() {
        return super.toString();
    }

    /* access modifiers changed from: private */
    public void sortInPlace(int[] iArr) {
        int[] copyOf = ArrayCopy.copyOf(iArr, iArr.length);
        Arrays.sort(copyOf);
        Content[] contentArr = new Content[copyOf.length];
        for (int i = 0; i < contentArr.length; i++) {
            contentArr[i] = this.elementData[iArr[i]];
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            this.elementData[copyOf[i2]] = contentArr[i2];
        }
    }

    private final int binarySearch(int[] iArr, int i, int i2, Comparator<? super Content> comparator) {
        int i3 = i - 1;
        Content content = this.elementData[i2];
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            int compare = comparator.compare(content, this.elementData[iArr[i5]]);
            if (compare == 0) {
                while (compare == 0 && i5 < i3) {
                    int i6 = i5 + 1;
                    if (comparator.compare(content, this.elementData[iArr[i6]]) != 0) {
                        break;
                    }
                    i5 = i6;
                }
                return i5 + 1;
            } else if (compare < 0) {
                i3 = i5 - 1;
            } else {
                i4 = i5 + 1;
            }
        }
        return i4;
    }

    public final void sort(Comparator<? super Content> comparator) {
        if (comparator != null) {
            int i = this.size;
            int[] iArr = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                int binarySearch = binarySearch(iArr, i2, i2, comparator);
                if (binarySearch < i2) {
                    System.arraycopy(iArr, binarySearch, iArr, binarySearch + 1, i2 - binarySearch);
                }
                iArr[binarySearch] = i2;
            }
            sortInPlace(iArr);
        }
    }
}
