package org.jdom2.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

final class PassThroughFilter extends AbstractFilter<Object> {
    private static final long serialVersionUID = 200;

    public Object filter(Object obj) {
        return obj;
    }

    PassThroughFilter() {
    }

    public List<Object> filter(List<?> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        if (list instanceof RandomAccess) {
            return Collections.unmodifiableList(list);
        }
        ArrayList arrayList = new ArrayList();
        for (Object add : list) {
            arrayList.add(add);
        }
        return Collections.unmodifiableList(arrayList);
    }
}
