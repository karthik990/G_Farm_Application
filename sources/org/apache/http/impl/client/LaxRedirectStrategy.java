package org.apache.http.impl.client;

public class LaxRedirectStrategy extends DefaultRedirectStrategy {
    public static final LaxRedirectStrategy INSTANCE = new LaxRedirectStrategy();
    private static final String[] REDIRECT_METHODS = {"GET", "POST", "HEAD", "DELETE"};

    /* access modifiers changed from: protected */
    public boolean isRedirectable(String str) {
        for (String equalsIgnoreCase : REDIRECT_METHODS) {
            if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
