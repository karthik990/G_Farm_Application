package com.google.android.gms.internal.firebase_auth;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzav<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] zzgt = new Object[0];

    zzav() {
    }

    public abstract boolean contains(@NullableDecl Object obj);

    /* renamed from: zzbz */
    public abstract zzbk<E> iterator();

    /* access modifiers changed from: 0000 */
    public Object[] zzca() {
        return null;
    }

    public final Object[] toArray() {
        return toArray(zzgt);
    }

    public final <T> T[] toArray(T[] tArr) {
        zzaj.checkNotNull(tArr);
        int size = size();
        if (tArr.length < size) {
            Object[] zzca = zzca();
            if (zzca != null) {
                return Arrays.copyOfRange(zzca, zzcb(), zzcc(), tArr.getClass());
            }
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        zza(tArr, 0);
        return tArr;
    }

    /* access modifiers changed from: 0000 */
    public int zzcb() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public int zzcc() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public zzay<E> zzcd() {
        return isEmpty() ? zzay.zzce() : zzay.zza(toArray());
    }

    /* access modifiers changed from: 0000 */
    public int zza(Object[] objArr, int i) {
        zzbk zzbk = (zzbk) iterator();
        while (zzbk.hasNext()) {
            int i2 = i + 1;
            objArr[i] = zzbk.next();
            i = i2;
        }
        return i;
    }
}
