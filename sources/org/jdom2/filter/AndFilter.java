package org.jdom2.filter;

final class AndFilter<T> extends AbstractFilter<T> {
    private static final long serialVersionUID = 200;
    private final Filter<?> base;
    private final Filter<T> refiner;

    public AndFilter(Filter<?> filter, Filter<T> filter2) {
        if (filter == null || filter2 == null) {
            throw new NullPointerException("Cannot have a null base or refiner filter");
        }
        this.base = filter;
        this.refiner = filter2;
    }

    public T filter(Object obj) {
        if (this.base.filter(obj) != null) {
            return this.refiner.filter(obj);
        }
        return null;
    }

    public int hashCode() {
        return this.base.hashCode() ^ this.refiner.hashCode();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AndFilter)) {
            return false;
        }
        AndFilter andFilter = (AndFilter) obj;
        if ((!this.base.equals(andFilter.base) || !this.refiner.equals(andFilter.refiner)) && (!this.refiner.equals(andFilter.base) || !this.base.equals(andFilter.refiner))) {
            z = false;
        }
        return z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[AndFilter: ");
        sb.append(this.base.toString());
        sb.append(",\n");
        sb.append("            ");
        sb.append(this.refiner.toString());
        sb.append("]");
        return sb.toString();
    }
}
