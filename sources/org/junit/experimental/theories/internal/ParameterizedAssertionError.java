package org.junit.experimental.theories.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ParameterizedAssertionError extends AssertionError {
    private static final long serialVersionUID = 1;

    public ParameterizedAssertionError(Throwable th, String str, Object... objArr) {
        super(String.format("%s(%s)", new Object[]{str, join(", ", objArr)}));
        initCause(th);
    }

    public boolean equals(Object obj) {
        return (obj instanceof ParameterizedAssertionError) && toString().equals(obj.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public static String join(String str, Object... objArr) {
        return join(str, (Collection<Object>) Arrays.asList(objArr));
    }

    public static String join(String str, Collection<Object> collection) {
        StringBuilder sb = new StringBuilder();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            sb.append(stringValueOf(it.next()));
            if (it.hasNext()) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    private static String stringValueOf(Object obj) {
        try {
            return String.valueOf(obj);
        } catch (Throwable unused) {
            return "[toString failed]";
        }
    }
}
