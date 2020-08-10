package com.google.api.client.util;

public final class Joiner {
    private final com.google.common.base.Joiner wrapped;

    /* renamed from: on */
    public static Joiner m1763on(char c) {
        return new Joiner(com.google.common.base.Joiner.m1774on(c));
    }

    private Joiner(com.google.common.base.Joiner joiner) {
        this.wrapped = joiner;
    }

    public final String join(Iterable<?> iterable) {
        return this.wrapped.join(iterable);
    }
}
