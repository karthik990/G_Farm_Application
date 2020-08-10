package org.jdom2.filter;

final class ClassFilter<T> extends AbstractFilter<T> {
    private static final long serialVersionUID = 200;
    private final Class<? extends T> fclass;

    public ClassFilter(Class<? extends T> cls) {
        this.fclass = cls;
    }

    public T filter(Object obj) {
        if (this.fclass.isInstance(obj)) {
            return this.fclass.cast(obj);
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ClassFilter: Class ");
        sb.append(this.fclass.getName());
        sb.append("]");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ClassFilter) {
            return this.fclass.equals(((ClassFilter) obj).fclass);
        }
        return false;
    }

    public int hashCode() {
        return this.fclass.hashCode();
    }
}
