package org.jdom2.filter;

final class NegateFilter extends AbstractFilter<Object> {
    private static final long serialVersionUID = 200;
    private final Filter<?> filter;

    public NegateFilter(Filter<?> filter2) {
        this.filter = filter2;
    }

    public Object filter(Object obj) {
        if (this.filter.matches(obj)) {
            return null;
        }
        return obj;
    }

    /* access modifiers changed from: 0000 */
    public Filter<?> getBaseFilter() {
        return this.filter;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof NegateFilter) {
            return this.filter.equals(((NegateFilter) obj).filter);
        }
        return false;
    }

    public int hashCode() {
        return this.filter.hashCode() ^ -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[NegateFilter: ");
        sb.append(this.filter.toString());
        sb.append("]");
        return sb.toString();
    }
}
