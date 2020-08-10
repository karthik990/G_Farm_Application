package org.apache.http.cookie;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.Serializable;
import java.util.Comparator;

public class CookiePathComparator implements Serializable, Comparator<Cookie> {
    public static final CookiePathComparator INSTANCE = new CookiePathComparator();
    private static final long serialVersionUID = 7523645369616405818L;

    private String normalizePath(Cookie cookie) {
        String path = cookie.getPath();
        String str = "/";
        if (path == null) {
            path = str;
        }
        if (path.endsWith(str)) {
            return path;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append(JsonPointer.SEPARATOR);
        return sb.toString();
    }

    public int compare(Cookie cookie, Cookie cookie2) {
        String normalizePath = normalizePath(cookie);
        String normalizePath2 = normalizePath(cookie2);
        if (normalizePath.equals(normalizePath2)) {
            return 0;
        }
        if (normalizePath.startsWith(normalizePath2)) {
            return -1;
        }
        if (normalizePath2.startsWith(normalizePath)) {
            return 1;
        }
        return 0;
    }
}
