package p043io.netty.handler.codec;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.codec.HeadersUtils */
public final class HeadersUtils {

    /* renamed from: io.netty.handler.codec.HeadersUtils$CharSequenceDelegatingStringSet */
    private static final class CharSequenceDelegatingStringSet extends DelegatingStringSet<CharSequence> {
        public CharSequenceDelegatingStringSet(Set<CharSequence> set) {
            super(set);
        }

        public boolean add(String str) {
            return this.allNames.add(str);
        }

        public boolean addAll(Collection<? extends String> collection) {
            return this.allNames.addAll(collection);
        }
    }

    /* renamed from: io.netty.handler.codec.HeadersUtils$DelegatingStringSet */
    private static abstract class DelegatingStringSet<T> implements Set<String> {
        protected final Set<T> allNames;

        public DelegatingStringSet(Set<T> set) {
            this.allNames = (Set) ObjectUtil.checkNotNull(set, "allNames");
        }

        public int size() {
            return this.allNames.size();
        }

        public boolean isEmpty() {
            return this.allNames.isEmpty();
        }

        public boolean contains(Object obj) {
            return this.allNames.contains(obj.toString());
        }

        public Iterator<String> iterator() {
            return new StringIterator(this.allNames.iterator());
        }

        public Object[] toArray() {
            Object[] objArr = new Object[size()];
            fillArray(objArr);
            return objArr;
        }

        public <X> X[] toArray(X[] xArr) {
            if (xArr == null || xArr.length < size()) {
                X[] xArr2 = (Object[]) new Object[size()];
                fillArray(xArr2);
                return xArr2;
            }
            fillArray(xArr);
            return xArr;
        }

        private void fillArray(Object[] objArr) {
            Iterator it = this.allNames.iterator();
            for (int i = 0; i < size(); i++) {
                objArr[i] = it.next();
            }
        }

        public boolean remove(Object obj) {
            return this.allNames.remove(obj);
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean z = false;
            for (Object remove : collection) {
                if (remove(remove)) {
                    z = true;
                }
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            Iterator it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public void clear() {
            this.allNames.clear();
        }
    }

    /* renamed from: io.netty.handler.codec.HeadersUtils$StringEntry */
    private static final class StringEntry implements Entry<String, String> {
        private final Entry<CharSequence, CharSequence> entry;
        private String name;
        private String value;

        StringEntry(Entry<CharSequence, CharSequence> entry2) {
            this.entry = entry2;
        }

        public String getKey() {
            if (this.name == null) {
                this.name = ((CharSequence) this.entry.getKey()).toString();
            }
            return this.name;
        }

        public String getValue() {
            if (this.value == null && this.entry.getValue() != null) {
                this.value = ((CharSequence) this.entry.getValue()).toString();
            }
            return this.value;
        }

        public String setValue(String str) {
            String value2 = getValue();
            this.entry.setValue(str);
            return value2;
        }

        public String toString() {
            return this.entry.toString();
        }
    }

    /* renamed from: io.netty.handler.codec.HeadersUtils$StringEntryIterator */
    private static final class StringEntryIterator implements Iterator<Entry<String, String>> {
        private final Iterator<Entry<CharSequence, CharSequence>> iter;

        public StringEntryIterator(Iterator<Entry<CharSequence, CharSequence>> it) {
            this.iter = it;
        }

        public boolean hasNext() {
            return this.iter.hasNext();
        }

        public Entry<String, String> next() {
            return new StringEntry((Entry) this.iter.next());
        }

        public void remove() {
            this.iter.remove();
        }
    }

    /* renamed from: io.netty.handler.codec.HeadersUtils$StringIterator */
    private static final class StringIterator<T> implements Iterator<String> {
        private final Iterator<T> iter;

        public StringIterator(Iterator<T> it) {
            this.iter = it;
        }

        public boolean hasNext() {
            return this.iter.hasNext();
        }

        public String next() {
            Object next = this.iter.next();
            if (next != null) {
                return next.toString();
            }
            return null;
        }

        public void remove() {
            this.iter.remove();
        }
    }

    private HeadersUtils() {
    }

    public static <K, V> List<String> getAllAsString(Headers<K, V, ?> headers, K k) {
        final List all = headers.getAll(k);
        return new AbstractList<String>() {
            public String get(int i) {
                Object obj = all.get(i);
                if (obj != null) {
                    return obj.toString();
                }
                return null;
            }

            public int size() {
                return all.size();
            }
        };
    }

    public static <K, V> String getAsString(Headers<K, V, ?> headers, K k) {
        Object obj = headers.get(k);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static Iterator<Entry<String, String>> iteratorAsString(Iterable<Entry<CharSequence, CharSequence>> iterable) {
        return new StringEntryIterator(iterable.iterator());
    }

    public static Set<String> namesAsString(Headers<CharSequence, CharSequence, ?> headers) {
        return new CharSequenceDelegatingStringSet(headers.names());
    }
}
