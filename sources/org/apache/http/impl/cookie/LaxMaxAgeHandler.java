package org.apache.http.impl.cookie;

import java.util.Date;
import java.util.regex.Pattern;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

public class LaxMaxAgeHandler extends AbstractCookieAttributeHandler implements CommonCookieAttributeHandler {
    private static final Pattern MAX_AGE_PATTERN = Pattern.compile("^\\-?[0-9]+$");

    public String getAttributeName() {
        return ClientCookie.MAX_AGE_ATTR;
    }

    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        Date date;
        Args.notNull(setCookie, "Cookie");
        if (!TextUtils.isBlank(str) && MAX_AGE_PATTERN.matcher(str).matches()) {
            try {
                int parseInt = Integer.parseInt(str);
                if (parseInt >= 0) {
                    date = new Date(System.currentTimeMillis() + (((long) parseInt) * 1000));
                } else {
                    date = new Date(Long.MIN_VALUE);
                }
                setCookie.setExpiryDate(date);
            } catch (NumberFormatException unused) {
            }
        }
    }
}
