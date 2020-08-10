package p043io.netty.handler.codec;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import p043io.netty.handler.codec.Headers;

/* renamed from: io.netty.handler.codec.EmptyHeaders */
public class EmptyHeaders<K, V, T extends Headers<K, V, T>> implements Headers<K, V, T> {
    private T thisT() {
        return this;
    }

    public boolean contains(K k) {
        return false;
    }

    public boolean contains(K k, V v) {
        return false;
    }

    public boolean containsBoolean(K k, boolean z) {
        return false;
    }

    public boolean containsByte(K k, byte b) {
        return false;
    }

    public boolean containsChar(K k, char c) {
        return false;
    }

    public boolean containsDouble(K k, double d) {
        return false;
    }

    public boolean containsFloat(K k, float f) {
        return false;
    }

    public boolean containsInt(K k, int i) {
        return false;
    }

    public boolean containsLong(K k, long j) {
        return false;
    }

    public boolean containsObject(K k, Object obj) {
        return false;
    }

    public boolean containsShort(K k, short s) {
        return false;
    }

    public boolean containsTimeMillis(K k, long j) {
        return false;
    }

    public V get(K k) {
        return null;
    }

    public V get(K k, V v) {
        return null;
    }

    public V getAndRemove(K k) {
        return null;
    }

    public V getAndRemove(K k, V v) {
        return null;
    }

    public Boolean getBoolean(K k) {
        return null;
    }

    public boolean getBoolean(K k, boolean z) {
        return z;
    }

    public Boolean getBooleanAndRemove(K k) {
        return null;
    }

    public boolean getBooleanAndRemove(K k, boolean z) {
        return z;
    }

    public byte getByte(K k, byte b) {
        return b;
    }

    public Byte getByte(K k) {
        return null;
    }

    public byte getByteAndRemove(K k, byte b) {
        return b;
    }

    public Byte getByteAndRemove(K k) {
        return null;
    }

    public char getChar(K k, char c) {
        return c;
    }

    public Character getChar(K k) {
        return null;
    }

    public char getCharAndRemove(K k, char c) {
        return c;
    }

    public Character getCharAndRemove(K k) {
        return null;
    }

    public double getDouble(K k, double d) {
        return d;
    }

    public Double getDouble(K k) {
        return null;
    }

    public double getDoubleAndRemove(K k, double d) {
        return d;
    }

    public Double getDoubleAndRemove(K k) {
        return null;
    }

    public float getFloat(K k, float f) {
        return f;
    }

    public Float getFloat(K k) {
        return null;
    }

    public float getFloatAndRemove(K k, float f) {
        return f;
    }

    public Float getFloatAndRemove(K k) {
        return null;
    }

    public int getInt(K k, int i) {
        return i;
    }

    public Integer getInt(K k) {
        return null;
    }

    public int getIntAndRemove(K k, int i) {
        return i;
    }

    public Integer getIntAndRemove(K k) {
        return null;
    }

    public long getLong(K k, long j) {
        return j;
    }

    public Long getLong(K k) {
        return null;
    }

    public long getLongAndRemove(K k, long j) {
        return j;
    }

    public Long getLongAndRemove(K k) {
        return null;
    }

    public Short getShort(K k) {
        return null;
    }

    public short getShort(K k, short s) {
        return s;
    }

    public Short getShortAndRemove(K k) {
        return null;
    }

    public short getShortAndRemove(K k, short s) {
        return s;
    }

    public long getTimeMillis(K k, long j) {
        return j;
    }

    public Long getTimeMillis(K k) {
        return null;
    }

    public long getTimeMillisAndRemove(K k, long j) {
        return j;
    }

    public Long getTimeMillisAndRemove(K k) {
        return null;
    }

    public int hashCode() {
        return -1028477387;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean remove(K k) {
        return false;
    }

    public int size() {
        return 0;
    }

    public List<V> getAll(K k) {
        return Collections.emptyList();
    }

    public List<V> getAllAndRemove(K k) {
        return Collections.emptyList();
    }

    public Set<K> names() {
        return Collections.emptySet();
    }

    public T add(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    public T add(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T add(K k, V... vArr) {
        throw new UnsupportedOperationException("read only");
    }

    public T addObject(K k, Object obj) {
        throw new UnsupportedOperationException("read only");
    }

    public T addObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T addObject(K k, Object... objArr) {
        throw new UnsupportedOperationException("read only");
    }

    public T addBoolean(K k, boolean z) {
        throw new UnsupportedOperationException("read only");
    }

    public T addByte(K k, byte b) {
        throw new UnsupportedOperationException("read only");
    }

    public T addChar(K k, char c) {
        throw new UnsupportedOperationException("read only");
    }

    public T addShort(K k, short s) {
        throw new UnsupportedOperationException("read only");
    }

    public T addInt(K k, int i) {
        throw new UnsupportedOperationException("read only");
    }

    public T addLong(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    public T addFloat(K k, float f) {
        throw new UnsupportedOperationException("read only");
    }

    public T addDouble(K k, double d) {
        throw new UnsupportedOperationException("read only");
    }

    public T addTimeMillis(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    public T add(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(K k, V... vArr) {
        throw new UnsupportedOperationException("read only");
    }

    public T setObject(K k, Object obj) {
        throw new UnsupportedOperationException("read only");
    }

    public T setObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    public T setObject(K k, Object... objArr) {
        throw new UnsupportedOperationException("read only");
    }

    public T setBoolean(K k, boolean z) {
        throw new UnsupportedOperationException("read only");
    }

    public T setByte(K k, byte b) {
        throw new UnsupportedOperationException("read only");
    }

    public T setChar(K k, char c) {
        throw new UnsupportedOperationException("read only");
    }

    public T setShort(K k, short s) {
        throw new UnsupportedOperationException("read only");
    }

    public T setInt(K k, int i) {
        throw new UnsupportedOperationException("read only");
    }

    public T setLong(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    public T setFloat(K k, float f) {
        throw new UnsupportedOperationException("read only");
    }

    public T setDouble(K k, double d) {
        throw new UnsupportedOperationException("read only");
    }

    public T setTimeMillis(K k, long j) {
        throw new UnsupportedOperationException("read only");
    }

    public T set(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    public T setAll(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    public T clear() {
        return thisT();
    }

    public Iterator<Entry<K, V>> iterator() {
        return Collections.emptyList().iterator();
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Headers)) {
            return false;
        }
        Headers headers = (Headers) obj;
        if (isEmpty() && headers.isEmpty()) {
            z = true;
        }
        return z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append(']');
        return sb.toString();
    }
}
