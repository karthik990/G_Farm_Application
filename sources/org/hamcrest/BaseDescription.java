package org.hamcrest;

import java.util.Arrays;
import java.util.Iterator;
import kotlin.text.Typography;
import org.hamcrest.internal.ArrayIterator;
import org.hamcrest.internal.SelfDescribingValueIterator;

public abstract class BaseDescription implements Description {
    /* access modifiers changed from: protected */
    public abstract void append(char c);

    public Description appendText(String str) {
        append(str);
        return this;
    }

    public Description appendDescriptionOf(SelfDescribing selfDescribing) {
        selfDescribing.describeTo(this);
        return this;
    }

    public Description appendValue(Object obj) {
        if (obj == null) {
            append("null");
        } else if (obj instanceof String) {
            toJavaSyntax((String) obj);
        } else if (obj instanceof Character) {
            append('\"');
            toJavaSyntax(((Character) obj).charValue());
            append('\"');
        } else if (obj instanceof Short) {
            append((char) Typography.less);
            append(descriptionOf(obj));
            append("s>");
        } else if (obj instanceof Long) {
            append((char) Typography.less);
            append(descriptionOf(obj));
            append("L>");
        } else if (obj instanceof Float) {
            append((char) Typography.less);
            append(descriptionOf(obj));
            append("F>");
        } else if (obj.getClass().isArray()) {
            appendValueList("[", ", ", "]", (Iterator<T>) new ArrayIterator<T>(obj));
        } else {
            append((char) Typography.less);
            append(descriptionOf(obj));
            append((char) Typography.greater);
        }
        return this;
    }

    private String descriptionOf(Object obj) {
        try {
            return String.valueOf(obj);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(obj.getClass().getName());
            sb.append("@");
            sb.append(Integer.toHexString(obj.hashCode()));
            return sb.toString();
        }
    }

    public <T> Description appendValueList(String str, String str2, String str3, T... tArr) {
        return appendValueList(str, str2, str3, (Iterable<T>) Arrays.asList(tArr));
    }

    public <T> Description appendValueList(String str, String str2, String str3, Iterable<T> iterable) {
        return appendValueList(str, str2, str3, iterable.iterator());
    }

    private <T> Description appendValueList(String str, String str2, String str3, Iterator<T> it) {
        return appendList(str, str2, str3, (Iterator<? extends SelfDescribing>) new SelfDescribingValueIterator<Object>(it));
    }

    public Description appendList(String str, String str2, String str3, Iterable<? extends SelfDescribing> iterable) {
        return appendList(str, str2, str3, iterable.iterator());
    }

    private Description appendList(String str, String str2, String str3, Iterator<? extends SelfDescribing> it) {
        append(str);
        boolean z = false;
        while (it.hasNext()) {
            if (z) {
                append(str2);
            }
            appendDescriptionOf((SelfDescribing) it.next());
            z = true;
        }
        append(str3);
        return this;
    }

    /* access modifiers changed from: protected */
    public void append(String str) {
        for (int i = 0; i < str.length(); i++) {
            append(str.charAt(i));
        }
    }

    private void toJavaSyntax(String str) {
        append('\"');
        for (int i = 0; i < str.length(); i++) {
            toJavaSyntax(str.charAt(i));
        }
        append('\"');
    }

    private void toJavaSyntax(char c) {
        if (c == 9) {
            append("\\t");
        } else if (c == 10) {
            append("\\n");
        } else if (c == 13) {
            append("\\r");
        } else if (c != '\"') {
            append(c);
        } else {
            append("\\\"");
        }
    }
}
