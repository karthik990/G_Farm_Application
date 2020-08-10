package com.google.firebase.database.core.utilities;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T t, U u) {
        this.first = t;
        this.second = u;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair pair = (Pair) obj;
        T t = this.first;
        if (t == null ? pair.first != null : !t.equals(pair.first)) {
            return false;
        }
        U u = this.second;
        U u2 = pair.second;
        return u == null ? u2 == null : u.equals(u2);
    }

    public int hashCode() {
        T t = this.first;
        int i = 0;
        int hashCode = (t != null ? t.hashCode() : 0) * 31;
        U u = this.second;
        if (u != null) {
            i = u.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pair(");
        sb.append(this.first);
        sb.append(",");
        sb.append(this.second);
        sb.append(")");
        return sb.toString();
    }
}
