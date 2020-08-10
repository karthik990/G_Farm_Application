package com.google.firebase.database.core.utilities;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface Predicate<T> {
    public static final Predicate<Object> TRUE = new Predicate<Object>() {
        public boolean evaluate(Object obj) {
            return true;
        }
    };

    boolean evaluate(T t);
}
