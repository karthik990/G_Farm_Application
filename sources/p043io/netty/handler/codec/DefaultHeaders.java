package p043io.netty.handler.codec;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import org.objectweb.asm.signature.SignatureVisitor;
import p043io.netty.handler.codec.Headers;
import p043io.netty.util.HashingStrategy;
import p043io.netty.util.internal.MathUtil;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.codec.DefaultHeaders */
public class DefaultHeaders<K, V, T extends Headers<K, V, T>> implements Headers<K, V, T> {
    static final int HASH_CODE_SEED = -1028477387;
    private final HeaderEntry<K, V>[] entries;
    private final byte hashMask;
    private final HashingStrategy<K> hashingStrategy;
    protected final HeaderEntry<K, V> head;
    private final NameValidator<K> nameValidator;
    int size;
    private final ValueConverter<V> valueConverter;

    /* renamed from: io.netty.handler.codec.DefaultHeaders$HeaderEntry */
    protected static class HeaderEntry<K, V> implements Entry<K, V> {
        protected HeaderEntry<K, V> after;
        protected HeaderEntry<K, V> before;
        protected final int hash;
        protected final K key;
        protected HeaderEntry<K, V> next;
        protected V value;

        protected HeaderEntry(int i, K k) {
            this.hash = i;
            this.key = k;
        }

        HeaderEntry(int i, K k, V v, HeaderEntry<K, V> headerEntry, HeaderEntry<K, V> headerEntry2) {
            this.hash = i;
            this.key = k;
            this.value = v;
            this.next = headerEntry;
            this.after = headerEntry2;
            this.before = headerEntry2.before;
            pointNeighborsToThis();
        }

        HeaderEntry() {
            this.hash = -1;
            this.key = null;
            this.after = this;
            this.before = this;
        }

        /* access modifiers changed from: protected */
        public final void pointNeighborsToThis() {
            this.before.after = this;
            this.after.before = this;
        }

        public final HeaderEntry<K, V> before() {
            return this.before;
        }

        public final HeaderEntry<K, V> after() {
            return this.after;
        }

        /* access modifiers changed from: protected */
        public void remove() {
            HeaderEntry<K, V> headerEntry = this.before;
            headerEntry.after = this.after;
            this.after.before = headerEntry;
        }

        public final K getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }

        public final V setValue(V v) {
            ObjectUtil.checkNotNull(v, Param.VALUE);
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.key.toString());
            sb.append(SignatureVisitor.INSTANCEOF);
            sb.append(this.value.toString());
            return sb.toString();
        }
    }

    /* renamed from: io.netty.handler.codec.DefaultHeaders$HeaderIterator */
    private final class HeaderIterator implements Iterator<Entry<K, V>> {
        private HeaderEntry<K, V> current;

        private HeaderIterator() {
            this.current = DefaultHeaders.this.head;
        }

        public boolean hasNext() {
            return this.current.after != DefaultHeaders.this.head;
        }

        public Entry<K, V> next() {
            this.current = this.current.after;
            if (this.current != DefaultHeaders.this.head) {
                return this.current;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException("read only");
        }
    }

    /* renamed from: io.netty.handler.codec.DefaultHeaders$NameValidator */
    public interface NameValidator<K> {
        public static final NameValidator NOT_NULL = new NameValidator() {
            public void validateName(Object obj) {
                ObjectUtil.checkNotNull(obj, PostalAddressParser.USER_ADDRESS_NAME_KEY);
            }
        };

        void validateName(K k);
    }

    private T thisT() {
        return this;
    }

    public DefaultHeaders(ValueConverter<V> valueConverter2) {
        this(HashingStrategy.JAVA_HASHER, valueConverter2);
    }

    public DefaultHeaders(ValueConverter<V> valueConverter2, NameValidator<K> nameValidator2) {
        this(HashingStrategy.JAVA_HASHER, valueConverter2, nameValidator2);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy2, ValueConverter<V> valueConverter2) {
        this(hashingStrategy2, valueConverter2, NameValidator.NOT_NULL);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy2, ValueConverter<V> valueConverter2, NameValidator<K> nameValidator2) {
        this(hashingStrategy2, valueConverter2, nameValidator2, 16);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy2, ValueConverter<V> valueConverter2, NameValidator<K> nameValidator2, int i) {
        this.valueConverter = (ValueConverter) ObjectUtil.checkNotNull(valueConverter2, "valueConverter");
        this.nameValidator = (NameValidator) ObjectUtil.checkNotNull(nameValidator2, "nameValidator");
        this.hashingStrategy = (HashingStrategy) ObjectUtil.checkNotNull(hashingStrategy2, "nameHashingStrategy");
        this.entries = new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.max(2, Math.min(i, 128)))];
        this.hashMask = (byte) (this.entries.length - 1);
        this.head = new HeaderEntry<>();
    }

    public V get(K k) {
        ObjectUtil.checkNotNull(k, PostalAddressParser.USER_ADDRESS_NAME_KEY);
        int hashCode = this.hashingStrategy.hashCode(k);
        V v = null;
        for (HeaderEntry<K, V> headerEntry = this.entries[index(hashCode)]; headerEntry != null; headerEntry = headerEntry.next) {
            if (headerEntry.hash == hashCode && this.hashingStrategy.equals(k, headerEntry.key)) {
                v = headerEntry.value;
            }
        }
        return v;
    }

    public V get(K k, V v) {
        V v2 = get(k);
        return v2 == null ? v : v2;
    }

    public V getAndRemove(K k) {
        int hashCode = this.hashingStrategy.hashCode(k);
        return remove0(hashCode, index(hashCode), ObjectUtil.checkNotNull(k, PostalAddressParser.USER_ADDRESS_NAME_KEY));
    }

    public V getAndRemove(K k, V v) {
        V andRemove = getAndRemove(k);
        return andRemove == null ? v : andRemove;
    }

    public List<V> getAll(K k) {
        ObjectUtil.checkNotNull(k, PostalAddressParser.USER_ADDRESS_NAME_KEY);
        LinkedList linkedList = new LinkedList();
        int hashCode = this.hashingStrategy.hashCode(k);
        for (HeaderEntry<K, V> headerEntry = this.entries[index(hashCode)]; headerEntry != null; headerEntry = headerEntry.next) {
            if (headerEntry.hash == hashCode && this.hashingStrategy.equals(k, headerEntry.key)) {
                linkedList.addFirst(headerEntry.getValue());
            }
        }
        return linkedList;
    }

    public List<V> getAllAndRemove(K k) {
        List<V> all = getAll(k);
        remove(k);
        return all;
    }

    public boolean contains(K k) {
        return get(k) != null;
    }

    public boolean containsObject(K k, Object obj) {
        return contains(k, this.valueConverter.convertObject(ObjectUtil.checkNotNull(obj, Param.VALUE)));
    }

    public boolean containsBoolean(K k, boolean z) {
        return contains(k, this.valueConverter.convertBoolean(z));
    }

    public boolean containsByte(K k, byte b) {
        return contains(k, this.valueConverter.convertByte(b));
    }

    public boolean containsChar(K k, char c) {
        return contains(k, this.valueConverter.convertChar(c));
    }

    public boolean containsShort(K k, short s) {
        return contains(k, this.valueConverter.convertShort(s));
    }

    public boolean containsInt(K k, int i) {
        return contains(k, this.valueConverter.convertInt(i));
    }

    public boolean containsLong(K k, long j) {
        return contains(k, this.valueConverter.convertLong(j));
    }

    public boolean containsFloat(K k, float f) {
        return contains(k, this.valueConverter.convertFloat(f));
    }

    public boolean containsDouble(K k, double d) {
        return contains(k, this.valueConverter.convertDouble(d));
    }

    public boolean containsTimeMillis(K k, long j) {
        return contains(k, this.valueConverter.convertTimeMillis(j));
    }

    public boolean contains(K k, V v) {
        return contains(k, v, HashingStrategy.JAVA_HASHER);
    }

    public final boolean contains(K k, V v, HashingStrategy<? super V> hashingStrategy2) {
        ObjectUtil.checkNotNull(k, PostalAddressParser.USER_ADDRESS_NAME_KEY);
        int hashCode = this.hashingStrategy.hashCode(k);
        for (HeaderEntry<K, V> headerEntry = this.entries[index(hashCode)]; headerEntry != null; headerEntry = headerEntry.next) {
            if (headerEntry.hash == hashCode && this.hashingStrategy.equals(k, headerEntry.key) && hashingStrategy2.equals(v, headerEntry.value)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        HeaderEntry<K, V> headerEntry = this.head;
        return headerEntry == headerEntry.after;
    }

    public Set<K> names() {
        if (isEmpty()) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(size());
        for (HeaderEntry<K, V> headerEntry = this.head.after; headerEntry != this.head; headerEntry = headerEntry.after) {
            linkedHashSet.add(headerEntry.getKey());
        }
        return linkedHashSet;
    }

    public T add(K k, V v) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(v, Param.VALUE);
        int hashCode = this.hashingStrategy.hashCode(k);
        add0(hashCode, index(hashCode), k, v);
        return thisT();
    }

    public T add(K k, Iterable<? extends V> iterable) {
        this.nameValidator.validateName(k);
        int hashCode = this.hashingStrategy.hashCode(k);
        int index = index(hashCode);
        for (Object add0 : iterable) {
            add0(hashCode, index, k, add0);
        }
        return thisT();
    }

    public T add(K k, V... vArr) {
        this.nameValidator.validateName(k);
        int hashCode = this.hashingStrategy.hashCode(k);
        int index = index(hashCode);
        for (V add0 : vArr) {
            add0(hashCode, index, k, add0);
        }
        return thisT();
    }

    public T addObject(K k, Object obj) {
        return add(k, (V) this.valueConverter.convertObject(ObjectUtil.checkNotNull(obj, Param.VALUE)));
    }

    public T addObject(K k, Iterable<?> iterable) {
        for (Object addObject : iterable) {
            addObject(k, addObject);
        }
        return thisT();
    }

    public T addObject(K k, Object... objArr) {
        for (Object addObject : objArr) {
            addObject(k, addObject);
        }
        return thisT();
    }

    public T addInt(K k, int i) {
        return add(k, (V) this.valueConverter.convertInt(i));
    }

    public T addLong(K k, long j) {
        return add(k, (V) this.valueConverter.convertLong(j));
    }

    public T addDouble(K k, double d) {
        return add(k, (V) this.valueConverter.convertDouble(d));
    }

    public T addTimeMillis(K k, long j) {
        return add(k, (V) this.valueConverter.convertTimeMillis(j));
    }

    public T addChar(K k, char c) {
        return add(k, (V) this.valueConverter.convertChar(c));
    }

    public T addBoolean(K k, boolean z) {
        return add(k, (V) this.valueConverter.convertBoolean(z));
    }

    public T addFloat(K k, float f) {
        return add(k, (V) this.valueConverter.convertFloat(f));
    }

    public T addByte(K k, byte b) {
        return add(k, (V) this.valueConverter.convertByte(b));
    }

    public T addShort(K k, short s) {
        return add(k, (V) this.valueConverter.convertShort(s));
    }

    public T add(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            addImpl(headers);
            return thisT();
        }
        throw new IllegalArgumentException("can't add to itself.");
    }

    /* access modifiers changed from: protected */
    public void addImpl(Headers<? extends K, ? extends V, ?> headers) {
        if (headers instanceof DefaultHeaders) {
            DefaultHeaders defaultHeaders = (DefaultHeaders) headers;
            HeaderEntry<K, V> headerEntry = defaultHeaders.head.after;
            if (defaultHeaders.hashingStrategy == this.hashingStrategy && defaultHeaders.nameValidator == this.nameValidator) {
                while (headerEntry != defaultHeaders.head) {
                    add0(headerEntry.hash, index(headerEntry.hash), headerEntry.key, headerEntry.value);
                    headerEntry = headerEntry.after;
                }
                return;
            }
            while (headerEntry != defaultHeaders.head) {
                add(headerEntry.key, headerEntry.value);
                headerEntry = headerEntry.after;
            }
            return;
        }
        Iterator it = headers.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            add((K) entry.getKey(), (V) entry.getValue());
        }
    }

    public T set(K k, V v) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(v, Param.VALUE);
        int hashCode = this.hashingStrategy.hashCode(k);
        int index = index(hashCode);
        remove0(hashCode, index, k);
        add0(hashCode, index, k, v);
        return thisT();
    }

    public T set(K k, Iterable<? extends V> iterable) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(iterable, "values");
        int hashCode = this.hashingStrategy.hashCode(k);
        int index = index(hashCode);
        remove0(hashCode, index, k);
        for (Object next : iterable) {
            if (next == null) {
                break;
            }
            add0(hashCode, index, k, next);
        }
        return thisT();
    }

    public T set(K k, V... vArr) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(vArr, "values");
        int hashCode = this.hashingStrategy.hashCode(k);
        int index = index(hashCode);
        remove0(hashCode, index, k);
        for (V v : vArr) {
            if (v == null) {
                break;
            }
            add0(hashCode, index, k, v);
        }
        return thisT();
    }

    public T setObject(K k, Object obj) {
        ObjectUtil.checkNotNull(obj, Param.VALUE);
        return set(k, (V) ObjectUtil.checkNotNull(this.valueConverter.convertObject(obj), "convertedValue"));
    }

    public T setObject(K k, Iterable<?> iterable) {
        this.nameValidator.validateName(k);
        int hashCode = this.hashingStrategy.hashCode(k);
        int index = index(hashCode);
        remove0(hashCode, index, k);
        for (Object next : iterable) {
            if (next == null) {
                break;
            }
            add0(hashCode, index, k, this.valueConverter.convertObject(next));
        }
        return thisT();
    }

    public T setObject(K k, Object... objArr) {
        this.nameValidator.validateName(k);
        int hashCode = this.hashingStrategy.hashCode(k);
        int index = index(hashCode);
        remove0(hashCode, index, k);
        for (Object obj : objArr) {
            if (obj == null) {
                break;
            }
            add0(hashCode, index, k, this.valueConverter.convertObject(obj));
        }
        return thisT();
    }

    public T setInt(K k, int i) {
        return set(k, (V) this.valueConverter.convertInt(i));
    }

    public T setLong(K k, long j) {
        return set(k, (V) this.valueConverter.convertLong(j));
    }

    public T setDouble(K k, double d) {
        return set(k, (V) this.valueConverter.convertDouble(d));
    }

    public T setTimeMillis(K k, long j) {
        return set(k, (V) this.valueConverter.convertTimeMillis(j));
    }

    public T setFloat(K k, float f) {
        return set(k, (V) this.valueConverter.convertFloat(f));
    }

    public T setChar(K k, char c) {
        return set(k, (V) this.valueConverter.convertChar(c));
    }

    public T setBoolean(K k, boolean z) {
        return set(k, (V) this.valueConverter.convertBoolean(z));
    }

    public T setByte(K k, byte b) {
        return set(k, (V) this.valueConverter.convertByte(b));
    }

    public T setShort(K k, short s) {
        return set(k, (V) this.valueConverter.convertShort(s));
    }

    public T set(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            clear();
            addImpl(headers);
        }
        return thisT();
    }

    public T setAll(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            for (Object remove : headers.names()) {
                remove(remove);
            }
            addImpl(headers);
        }
        return thisT();
    }

    public boolean remove(K k) {
        return getAndRemove(k) != null;
    }

    public T clear() {
        Arrays.fill(this.entries, null);
        HeaderEntry<K, V> headerEntry = this.head;
        headerEntry.after = headerEntry;
        headerEntry.before = headerEntry;
        this.size = 0;
        return thisT();
    }

    public Iterator<Entry<K, V>> iterator() {
        return new HeaderIterator();
    }

    public Boolean getBoolean(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Boolean.valueOf(this.valueConverter.convertToBoolean(obj));
        }
        return null;
    }

    public boolean getBoolean(K k, boolean z) {
        Boolean bool = getBoolean(k);
        return bool != null ? bool.booleanValue() : z;
    }

    public Byte getByte(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Byte.valueOf(this.valueConverter.convertToByte(obj));
        }
        return null;
    }

    public byte getByte(K k, byte b) {
        Byte b2 = getByte(k);
        return b2 != null ? b2.byteValue() : b;
    }

    public Character getChar(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Character.valueOf(this.valueConverter.convertToChar(obj));
        }
        return null;
    }

    public char getChar(K k, char c) {
        Character ch = getChar(k);
        return ch != null ? ch.charValue() : c;
    }

    public Short getShort(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Short.valueOf(this.valueConverter.convertToShort(obj));
        }
        return null;
    }

    public short getShort(K k, short s) {
        Short sh = getShort(k);
        return sh != null ? sh.shortValue() : s;
    }

    public Integer getInt(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Integer.valueOf(this.valueConverter.convertToInt(obj));
        }
        return null;
    }

    public int getInt(K k, int i) {
        Integer num = getInt(k);
        return num != null ? num.intValue() : i;
    }

    public Long getLong(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Long.valueOf(this.valueConverter.convertToLong(obj));
        }
        return null;
    }

    public long getLong(K k, long j) {
        Long l = getLong(k);
        return l != null ? l.longValue() : j;
    }

    public Float getFloat(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Float.valueOf(this.valueConverter.convertToFloat(obj));
        }
        return null;
    }

    public float getFloat(K k, float f) {
        Float f2 = getFloat(k);
        return f2 != null ? f2.floatValue() : f;
    }

    public Double getDouble(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Double.valueOf(this.valueConverter.convertToDouble(obj));
        }
        return null;
    }

    public double getDouble(K k, double d) {
        Double d2 = getDouble(k);
        return d2 != null ? d2.doubleValue() : d;
    }

    public Long getTimeMillis(K k) {
        Object obj = get(k);
        if (obj != null) {
            return Long.valueOf(this.valueConverter.convertToTimeMillis(obj));
        }
        return null;
    }

    public long getTimeMillis(K k, long j) {
        Long timeMillis = getTimeMillis(k);
        return timeMillis != null ? timeMillis.longValue() : j;
    }

    public Boolean getBooleanAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Boolean.valueOf(this.valueConverter.convertToBoolean(andRemove));
        }
        return null;
    }

    public boolean getBooleanAndRemove(K k, boolean z) {
        Boolean booleanAndRemove = getBooleanAndRemove(k);
        return booleanAndRemove != null ? booleanAndRemove.booleanValue() : z;
    }

    public Byte getByteAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Byte.valueOf(this.valueConverter.convertToByte(andRemove));
        }
        return null;
    }

    public byte getByteAndRemove(K k, byte b) {
        Byte byteAndRemove = getByteAndRemove(k);
        return byteAndRemove != null ? byteAndRemove.byteValue() : b;
    }

    public Character getCharAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove == null) {
            return null;
        }
        try {
            return Character.valueOf(this.valueConverter.convertToChar(andRemove));
        } catch (Throwable unused) {
            return null;
        }
    }

    public char getCharAndRemove(K k, char c) {
        Character charAndRemove = getCharAndRemove(k);
        return charAndRemove != null ? charAndRemove.charValue() : c;
    }

    public Short getShortAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Short.valueOf(this.valueConverter.convertToShort(andRemove));
        }
        return null;
    }

    public short getShortAndRemove(K k, short s) {
        Short shortAndRemove = getShortAndRemove(k);
        return shortAndRemove != null ? shortAndRemove.shortValue() : s;
    }

    public Integer getIntAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Integer.valueOf(this.valueConverter.convertToInt(andRemove));
        }
        return null;
    }

    public int getIntAndRemove(K k, int i) {
        Integer intAndRemove = getIntAndRemove(k);
        return intAndRemove != null ? intAndRemove.intValue() : i;
    }

    public Long getLongAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Long.valueOf(this.valueConverter.convertToLong(andRemove));
        }
        return null;
    }

    public long getLongAndRemove(K k, long j) {
        Long longAndRemove = getLongAndRemove(k);
        return longAndRemove != null ? longAndRemove.longValue() : j;
    }

    public Float getFloatAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Float.valueOf(this.valueConverter.convertToFloat(andRemove));
        }
        return null;
    }

    public float getFloatAndRemove(K k, float f) {
        Float floatAndRemove = getFloatAndRemove(k);
        return floatAndRemove != null ? floatAndRemove.floatValue() : f;
    }

    public Double getDoubleAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Double.valueOf(this.valueConverter.convertToDouble(andRemove));
        }
        return null;
    }

    public double getDoubleAndRemove(K k, double d) {
        Double doubleAndRemove = getDoubleAndRemove(k);
        return doubleAndRemove != null ? doubleAndRemove.doubleValue() : d;
    }

    public Long getTimeMillisAndRemove(K k) {
        Object andRemove = getAndRemove(k);
        if (andRemove != null) {
            return Long.valueOf(this.valueConverter.convertToTimeMillis(andRemove));
        }
        return null;
    }

    public long getTimeMillisAndRemove(K k, long j) {
        Long timeMillisAndRemove = getTimeMillisAndRemove(k);
        return timeMillisAndRemove != null ? timeMillisAndRemove.longValue() : j;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Headers)) {
            return false;
        }
        return equals((Headers) obj, HashingStrategy.JAVA_HASHER);
    }

    public int hashCode() {
        return hashCode(HashingStrategy.JAVA_HASHER);
    }

    public final boolean equals(Headers<K, V, ?> headers, HashingStrategy<V> hashingStrategy2) {
        if (headers.size() != size()) {
            return false;
        }
        if (this == headers) {
            return true;
        }
        for (Object next : names()) {
            List all = headers.getAll(next);
            List all2 = getAll(next);
            if (all.size() != all2.size()) {
                return false;
            }
            int i = 0;
            while (true) {
                if (i < all.size()) {
                    if (!hashingStrategy2.equals(all.get(i), all2.get(i))) {
                        return false;
                    }
                    i++;
                }
            }
        }
        return true;
    }

    public final int hashCode(HashingStrategy<V> hashingStrategy2) {
        int i = HASH_CODE_SEED;
        for (Object next : names()) {
            i = (i * 31) + this.hashingStrategy.hashCode(next);
            List all = getAll(next);
            for (int i2 = 0; i2 < all.size(); i2++) {
                i = (i * 31) + hashingStrategy2.hashCode(all.get(i2));
            }
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        String str = "";
        for (Object next : names()) {
            List all = getAll(next);
            int i = 0;
            while (i < all.size()) {
                sb.append(str);
                sb.append(next);
                sb.append(": ");
                sb.append(all.get(i));
                i++;
                str = ", ";
            }
        }
        sb.append(']');
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public HeaderEntry<K, V> newHeaderEntry(int i, K k, V v, HeaderEntry<K, V> headerEntry) {
        HeaderEntry headerEntry2 = new HeaderEntry(i, k, v, headerEntry, this.head);
        return headerEntry2;
    }

    /* access modifiers changed from: protected */
    public ValueConverter<V> valueConverter() {
        return this.valueConverter;
    }

    private int index(int i) {
        return i & this.hashMask;
    }

    private void add0(int i, int i2, K k, V v) {
        HeaderEntry<K, V>[] headerEntryArr = this.entries;
        headerEntryArr[i2] = newHeaderEntry(i, k, v, headerEntryArr[i2]);
        this.size++;
    }

    private V remove0(int i, int i2, K k) {
        HeaderEntry<K, V> headerEntry = this.entries[i2];
        V v = null;
        if (headerEntry == null) {
            return null;
        }
        for (HeaderEntry<K, V> headerEntry2 = headerEntry.next; headerEntry2 != null; headerEntry2 = headerEntry.next) {
            if (headerEntry2.hash != i || !this.hashingStrategy.equals(k, headerEntry2.key)) {
                headerEntry = headerEntry2;
            } else {
                v = headerEntry2.value;
                headerEntry.next = headerEntry2.next;
                headerEntry2.remove();
                this.size--;
            }
        }
        HeaderEntry<K, V> headerEntry3 = this.entries[i2];
        if (headerEntry3.hash == i && this.hashingStrategy.equals(k, headerEntry3.key)) {
            if (v == null) {
                v = headerEntry3.value;
            }
            this.entries[i2] = headerEntry3.next;
            headerEntry3.remove();
            this.size--;
        }
        return v;
    }
}
