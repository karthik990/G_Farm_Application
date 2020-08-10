package org.jdom2.filter;

import java.io.Serializable;
import java.util.List;

public interface Filter<T> extends Serializable {
    Filter<T> and(Filter<?> filter);

    T filter(Object obj);

    List<T> filter(List<?> list);

    boolean matches(Object obj);

    Filter<? extends Object> negate();

    /* renamed from: or */
    Filter<? extends Object> mo75696or(Filter<?> filter);

    <R> Filter<R> refine(Filter<R> filter);
}
