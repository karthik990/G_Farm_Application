package org.apache.http.cookie;

import java.io.Serializable;
import java.util.Comparator;

public class CookieIdentityComparator implements Serializable, Comparator<Cookie> {
    private static final long serialVersionUID = 4466565437490631532L;

    public int compare(Cookie cookie, Cookie cookie2) {
        int compareTo = cookie.getName().compareTo(cookie2.getName());
        if (compareTo == 0) {
            String domain = cookie.getDomain();
            String str = ".local";
            String str2 = "";
            if (domain == null) {
                domain = str2;
            } else if (domain.indexOf(46) == -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(domain);
                sb.append(str);
                domain = sb.toString();
            }
            String domain2 = cookie2.getDomain();
            if (domain2 != null) {
                if (domain2.indexOf(46) == -1) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(domain2);
                    sb2.append(str);
                    str2 = sb2.toString();
                } else {
                    str2 = domain2;
                }
            }
            compareTo = domain.compareToIgnoreCase(str2);
        }
        if (compareTo != 0) {
            return compareTo;
        }
        String path = cookie.getPath();
        String str3 = "/";
        if (path == null) {
            path = str3;
        }
        String path2 = cookie2.getPath();
        if (path2 == null) {
            path2 = str3;
        }
        return path.compareTo(path2);
    }
}
