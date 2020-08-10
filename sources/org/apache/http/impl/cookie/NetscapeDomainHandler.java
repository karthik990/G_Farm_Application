package org.apache.http.impl.cookie;

import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

public class NetscapeDomainHandler extends BasicDomainHandler {
    public String getAttributeName() {
        return ClientCookie.DOMAIN_ATTR;
    }

    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (!TextUtils.isBlank(str)) {
            setCookie.setDomain(str);
            return;
        }
        throw new MalformedCookieException("Blank or null value for domain attribute");
    }

    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (host.equals(domain) || BasicDomainHandler.domainMatch(domain, host)) {
            String str = ".";
            if (host.contains(str)) {
                int countTokens = new StringTokenizer(domain, str).countTokens();
                String str2 = "Domain attribute \"";
                if (isSpecialDomain(domain)) {
                    if (countTokens < 2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(domain);
                        sb.append("\" violates the Netscape cookie specification for ");
                        sb.append("special domains");
                        throw new CookieRestrictionViolationException(sb.toString());
                    }
                } else if (countTokens < 3) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append(domain);
                    sb2.append("\" violates the Netscape cookie specification");
                    throw new CookieRestrictionViolationException(sb2.toString());
                }
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Illegal domain attribute \"");
            sb3.append(domain);
            sb3.append("\". Domain of origin: \"");
            sb3.append(host);
            sb3.append("\"");
            throw new CookieRestrictionViolationException(sb3.toString());
        }
    }

    private static boolean isSpecialDomain(String str) {
        String upperCase = str.toUpperCase(Locale.ROOT);
        return upperCase.endsWith(".COM") || upperCase.endsWith(".EDU") || upperCase.endsWith(".NET") || upperCase.endsWith(".GOV") || upperCase.endsWith(".MIL") || upperCase.endsWith(".ORG") || upperCase.endsWith(".INT");
    }

    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (domain == null) {
            return false;
        }
        return host.endsWith(domain);
    }
}
