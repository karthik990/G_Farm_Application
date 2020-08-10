package org.jdom2.filter;

import org.jdom2.Content;

final class OrFilter extends AbstractFilter<Content> {
    private static final long serialVersionUID = 200;
    private final Filter<?> left;
    private final Filter<?> right;

    public OrFilter(Filter<?> filter, Filter<?> filter2) {
        if (filter == null || filter2 == null) {
            throw new IllegalArgumentException("null filter not allowed");
        }
        this.left = filter;
        this.right = filter2;
    }

    public Content filter(Object obj) {
        if (this.left.matches(obj) || this.right.matches(obj)) {
            return (Content) obj;
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OrFilter) {
            OrFilter orFilter = (OrFilter) obj;
            if ((this.left.equals(orFilter.left) && this.right.equals(orFilter.right)) || (this.left.equals(orFilter.right) && this.right.equals(orFilter.left))) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.left.hashCode() ^ -1) ^ this.right.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[OrFilter: ");
        sb.append(this.left.toString());
        sb.append(",\n");
        sb.append("           ");
        sb.append(this.right.toString());
        sb.append("]");
        return sb.toString();
    }
}
