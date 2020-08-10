package com.google.android.gms.internal.firebase_auth;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzay<E> extends zzav<E> implements List<E>, RandomAccess {
    private static final zzbj<Object> zzgv = new zzax(zzbb.zzhb, 0);

    public static <E> zzay<E> zzce() {
        return zzbb.zzhb;
    }

    public final zzay<E> zzcd() {
        return this;
    }

    public static <E> zzay<E> zza(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        int i = 0;
        Object[] objArr = {e, e2, e3, e4, e5, e6, e7, e8};
        while (i < 8) {
            if (objArr[i] != null) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder(20);
                sb.append("at index ");
                sb.append(i);
                throw new NullPointerException(sb.toString());
            }
        }
        return new zzbb(objArr, 8);
    }

    static <E> zzay<E> zza(Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return zzbb.zzhb;
        }
        return new zzbb(objArr, length);
    }

    zzay() {
    }

    public final zzbk<E> zzbz() {
        return (zzbj) listIterator();
    }

    public int indexOf(@NullableDecl Object obj) {
        if (obj == null) {
            return -1;
        }
        if (this instanceof RandomAccess) {
            int size = size();
            int i = 0;
            if (obj == null) {
                while (i < size) {
                    if (get(i) == null) {
                        return i;
                    }
                    i++;
                }
            } else {
                while (i < size) {
                    if (obj.equals(get(i))) {
                        return i;
                    }
                    i++;
                }
            }
            return -1;
        }
        ListIterator listIterator = listIterator();
        while (listIterator.hasNext()) {
            if (zzak.equal(obj, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    public int lastIndexOf(@NullableDecl Object obj) {
        if (obj == null) {
            return -1;
        }
        if (this instanceof RandomAccess) {
            if (obj == null) {
                for (int size = size() - 1; size >= 0; size--) {
                    if (get(size) == null) {
                        return size;
                    }
                }
            } else {
                for (int size2 = size() - 1; size2 >= 0; size2--) {
                    if (obj.equals(get(size2))) {
                        return size2;
                    }
                }
            }
            return -1;
        }
        ListIterator listIterator = listIterator(size());
        while (listIterator.hasPrevious()) {
            if (zzak.equal(obj, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    public boolean contains(@NullableDecl Object obj) {
        return indexOf(obj) >= 0;
    }

    /* renamed from: zzc */
    public zzay<E> subList(int i, int i2) {
        zzaj.zza(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 == 0) {
            return zzbb.zzhb;
        }
        return new zzba(this, i, i3);
    }

    @Deprecated
    public final boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E remove(int i) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public int zza(Object[] objArr, int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i + i2] = get(i2);
        }
        return i + size;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == zzaj.checkNotNull(this)) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size == list.size()) {
                if (!(this instanceof RandomAccess) || !(list instanceof RandomAccess)) {
                    zzay zzay = this;
                    int size2 = zzay.size();
                    Iterator it = list.iterator();
                    int i = 0;
                    while (true) {
                        if (i < size2) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Object obj2 = zzay.get(i);
                            i++;
                            if (!zzak.equal(obj2, it.next())) {
                                break;
                            }
                        } else if (!it.hasNext()) {
                            return true;
                        }
                    }
                } else {
                    int i2 = 0;
                    while (i2 < size) {
                        if (zzak.equal(get(i2), list.get(i2))) {
                            i2++;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < size(); i2++) {
            i = (((i * 31) + get(i2).hashCode()) ^ -1) ^ -1;
        }
        return i;
    }

    public /* synthetic */ Iterator iterator() {
        return iterator();
    }

    public /* synthetic */ ListIterator listIterator(int i) {
        zzaj.zzb(i, size());
        if (isEmpty()) {
            return zzgv;
        }
        return new zzax(this, i);
    }

    public /* synthetic */ ListIterator listIterator() {
        return (zzbj) listIterator(0);
    }
}
