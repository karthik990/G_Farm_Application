package org.jdom2.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import org.jdom2.Content;

public abstract class AbstractFilter<T> implements Filter<T> {
    private static final long serialVersionUID = 200;

    public final boolean matches(Object obj) {
        return filter(obj) != null;
    }

    public List<T> filter(List<?> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                Object filter = filter(list.get(i));
                if (filter != null) {
                    arrayList.add(filter);
                }
            }
            if (arrayList.isEmpty()) {
                return Collections.emptyList();
            }
            return Collections.unmodifiableList(arrayList);
        }
        ArrayList arrayList2 = new ArrayList(10);
        for (Object filter2 : list) {
            Object filter3 = filter(filter2);
            if (filter3 != null) {
                arrayList2.add(filter3);
            }
        }
        if (arrayList2.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(arrayList2);
    }

    public final Filter<?> negate() {
        if (this instanceof NegateFilter) {
            return ((NegateFilter) this).getBaseFilter();
        }
        return new NegateFilter(this);
    }

    /* renamed from: or */
    public final Filter<? extends Content> mo75696or(Filter<?> filter) {
        return new OrFilter(this, filter);
    }

    public final Filter<T> and(Filter<?> filter) {
        return new AndFilter(filter, this);
    }

    public <R> Filter<R> refine(Filter<R> filter) {
        return new AndFilter(this, filter);
    }
}
