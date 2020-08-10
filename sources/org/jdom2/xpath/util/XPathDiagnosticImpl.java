package org.jdom2.xpath.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jdom2.filter.Filter;
import org.jdom2.xpath.XPathDiagnostic;
import org.jdom2.xpath.XPathExpression;

public class XPathDiagnosticImpl<T> implements XPathDiagnostic<T> {
    private final Object dcontext;
    private final List<Object> dfiltered;
    private final boolean dfirstonly;
    private final List<Object> draw;
    private final List<T> dresult;
    private final XPathExpression<T> dxpath;

    public XPathDiagnosticImpl(Object obj, XPathExpression<T> xPathExpression, List<?> list, boolean z) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        ArrayList arrayList3 = new ArrayList(size);
        Filter filter = xPathExpression.getFilter();
        for (Object next : list) {
            arrayList.add(next);
            Object filter2 = filter.filter(next);
            if (filter2 == null) {
                arrayList2.add(next);
            } else {
                arrayList3.add(filter2);
            }
        }
        this.dcontext = obj;
        this.dxpath = xPathExpression;
        this.dfirstonly = z;
        this.dfiltered = Collections.unmodifiableList(arrayList2);
        this.draw = Collections.unmodifiableList(arrayList);
        this.dresult = Collections.unmodifiableList(arrayList3);
    }

    public Object getContext() {
        return this.dcontext;
    }

    public XPathExpression<T> getXPathExpression() {
        return this.dxpath;
    }

    public List<T> getResult() {
        return this.dresult;
    }

    public List<Object> getFilteredResults() {
        return this.dfiltered;
    }

    public List<Object> getRawResults() {
        return this.draw;
    }

    public boolean isFirstOnly() {
        return this.dfirstonly;
    }

    public String toString() {
        Object[] objArr = new Object[6];
        objArr[0] = this.dxpath.getExpression();
        objArr[1] = this.dfirstonly ? "first" : "all";
        objArr[2] = this.dcontext.getClass().getName();
        objArr[3] = Integer.valueOf(this.draw.size());
        objArr[4] = Integer.valueOf(this.dfiltered.size());
        objArr[5] = Integer.valueOf(this.dresult.size());
        return String.format("[XPathDiagnostic: '%s' evaluated (%s) against %s produced  raw=%d discarded=%d returned=%d]", objArr);
    }
}
