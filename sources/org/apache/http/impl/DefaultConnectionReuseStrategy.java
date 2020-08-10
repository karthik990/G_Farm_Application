package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.TokenIterator;
import org.apache.http.message.BasicTokenIterator;

public class DefaultConnectionReuseStrategy implements ConnectionReuseStrategy {
    public static final DefaultConnectionReuseStrategy INSTANCE = new DefaultConnectionReuseStrategy();

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008f, code lost:
        if (java.lang.Integer.parseInt(r9[0].getValue()) < 0) goto L_0x0091;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean keepAlive(org.apache.http.HttpResponse r8, org.apache.http.protocol.HttpContext r9) {
        /*
            r7 = this;
            java.lang.String r0 = "HTTP response"
            org.apache.http.util.Args.notNull(r8, r0)
            java.lang.String r0 = "HTTP context"
            org.apache.http.util.Args.notNull(r9, r0)
            org.apache.http.StatusLine r0 = r8.getStatusLine()
            int r0 = r0.getStatusCode()
            java.lang.String r1 = "Transfer-Encoding"
            java.lang.String r2 = "Content-Length"
            r3 = 0
            r4 = 204(0xcc, float:2.86E-43)
            if (r0 != r4) goto L_0x0034
            org.apache.http.Header r0 = r8.getFirstHeader(r2)
            if (r0 == 0) goto L_0x002d
            java.lang.String r0 = r0.getValue()     // Catch:{ NumberFormatException -> 0x002c }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x002c }
            if (r0 <= 0) goto L_0x002d
            return r3
        L_0x002c:
        L_0x002d:
            org.apache.http.Header r0 = r8.getFirstHeader(r1)
            if (r0 == 0) goto L_0x0034
            return r3
        L_0x0034:
            java.lang.String r0 = "http.request"
            java.lang.Object r9 = r9.getAttribute(r0)
            org.apache.http.HttpRequest r9 = (org.apache.http.HttpRequest) r9
            java.lang.String r0 = "Close"
            java.lang.String r4 = "Connection"
            if (r9 == 0) goto L_0x005c
            org.apache.http.message.BasicTokenIterator r5 = new org.apache.http.message.BasicTokenIterator     // Catch:{ ParseException -> 0x005b }
            org.apache.http.HeaderIterator r6 = r9.headerIterator(r4)     // Catch:{ ParseException -> 0x005b }
            r5.<init>(r6)     // Catch:{ ParseException -> 0x005b }
        L_0x004b:
            boolean r6 = r5.hasNext()     // Catch:{ ParseException -> 0x005b }
            if (r6 == 0) goto L_0x005c
            java.lang.String r6 = r5.nextToken()     // Catch:{ ParseException -> 0x005b }
            boolean r6 = r0.equalsIgnoreCase(r6)     // Catch:{ ParseException -> 0x005b }
            if (r6 == 0) goto L_0x004b
        L_0x005b:
            return r3
        L_0x005c:
            org.apache.http.StatusLine r5 = r8.getStatusLine()
            org.apache.http.ProtocolVersion r5 = r5.getProtocolVersion()
            org.apache.http.Header r1 = r8.getFirstHeader(r1)
            r6 = 1
            if (r1 == 0) goto L_0x0078
            java.lang.String r9 = r1.getValue()
            java.lang.String r1 = "chunked"
            boolean r9 = r1.equalsIgnoreCase(r9)
            if (r9 != 0) goto L_0x0092
            return r3
        L_0x0078:
            boolean r9 = r7.canResponseHaveBody(r9, r8)
            if (r9 == 0) goto L_0x0092
            org.apache.http.Header[] r9 = r8.getHeaders(r2)
            int r1 = r9.length
            if (r1 != r6) goto L_0x0091
            r9 = r9[r3]
            java.lang.String r9 = r9.getValue()     // Catch:{ NumberFormatException -> 0x0091 }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ NumberFormatException -> 0x0091 }
            if (r9 >= 0) goto L_0x0092
        L_0x0091:
            return r3
        L_0x0092:
            org.apache.http.HeaderIterator r9 = r8.headerIterator(r4)
            boolean r1 = r9.hasNext()
            if (r1 != 0) goto L_0x00a2
            java.lang.String r9 = "Proxy-Connection"
            org.apache.http.HeaderIterator r9 = r8.headerIterator(r9)
        L_0x00a2:
            boolean r8 = r9.hasNext()
            if (r8 == 0) goto L_0x00cd
            org.apache.http.message.BasicTokenIterator r8 = new org.apache.http.message.BasicTokenIterator     // Catch:{ ParseException -> 0x00cc }
            r8.<init>(r9)     // Catch:{ ParseException -> 0x00cc }
            r9 = 0
        L_0x00ae:
            boolean r1 = r8.hasNext()     // Catch:{ ParseException -> 0x00cc }
            if (r1 == 0) goto L_0x00c9
            java.lang.String r1 = r8.nextToken()     // Catch:{ ParseException -> 0x00cc }
            boolean r2 = r0.equalsIgnoreCase(r1)     // Catch:{ ParseException -> 0x00cc }
            if (r2 == 0) goto L_0x00bf
            return r3
        L_0x00bf:
            java.lang.String r2 = "Keep-Alive"
            boolean r1 = r2.equalsIgnoreCase(r1)     // Catch:{ ParseException -> 0x00cc }
            if (r1 == 0) goto L_0x00ae
            r9 = 1
            goto L_0x00ae
        L_0x00c9:
            if (r9 == 0) goto L_0x00cd
            return r6
        L_0x00cc:
            return r3
        L_0x00cd:
            org.apache.http.HttpVersion r8 = org.apache.http.HttpVersion.HTTP_1_0
            boolean r8 = r5.lessEquals(r8)
            r8 = r8 ^ r6
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.DefaultConnectionReuseStrategy.keepAlive(org.apache.http.HttpResponse, org.apache.http.protocol.HttpContext):boolean");
    }

    /* access modifiers changed from: protected */
    public TokenIterator createTokenIterator(HeaderIterator headerIterator) {
        return new BasicTokenIterator(headerIterator);
    }

    private boolean canResponseHaveBody(HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean z = false;
        if (httpRequest != null && httpRequest.getRequestLine().getMethod().equalsIgnoreCase("HEAD")) {
            return false;
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (!(statusCode < 200 || statusCode == 204 || statusCode == 304 || statusCode == 205)) {
            z = true;
        }
        return z;
    }
}
