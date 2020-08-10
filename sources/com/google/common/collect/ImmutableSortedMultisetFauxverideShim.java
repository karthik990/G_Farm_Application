package com.google.common.collect;

import com.google.common.collect.ImmutableSortedMultiset.Builder;

abstract class ImmutableSortedMultisetFauxverideShim<E> extends ImmutableMultiset<E> {
    ImmutableSortedMultisetFauxverideShim() {
    }

    @Deprecated
    public static <E> Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m1957of(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m1958of(E e, E e2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m1959of(E e, E e2, E e3) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m1960of(E e, E e2, E e3, E e4) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m1961of(E e, E e2, E e3, E e4, E e5) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m1962of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedMultiset<E> copyOf(E[] eArr) {
        throw new UnsupportedOperationException();
    }
}
