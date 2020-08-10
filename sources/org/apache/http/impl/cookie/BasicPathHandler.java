package org.apache.http.impl.cookie;

import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

public class BasicPathHandler implements CommonCookieAttributeHandler {
    public String getAttributeName() {
        return ClientCookie.PATH_ATTR;
    }

    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
    }

    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(str)) {
            str = "/";
        }
        setCookie.setPath(str);
    }

    static boolean pathMatch(String str, String str2) {
        String str3 = "/";
        if (str2 == null) {
            str2 = str3;
        }
        if (str2.length() > 1 && str2.endsWith(str3)) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        return str.startsWith(str2) && (str2.equals(str3) || str.length() == str2.length() || str.charAt(str2.length()) == '/');
    }

    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        return pathMatch(cookieOrigin.getPath(), cookie.getPath());
    }
}
