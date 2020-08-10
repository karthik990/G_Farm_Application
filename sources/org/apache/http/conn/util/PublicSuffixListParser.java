package org.apache.http.conn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public final class PublicSuffixListParser {
    public PublicSuffixList parse(Reader reader) throws IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return new PublicSuffixList(DomainType.UNKNOWN, arrayList, arrayList2);
            }
            if (!readLine.isEmpty() && !readLine.startsWith("//")) {
                if (readLine.startsWith(".")) {
                    readLine = readLine.substring(1);
                }
                boolean startsWith = readLine.startsWith("!");
                if (startsWith) {
                    readLine = readLine.substring(1);
                }
                if (startsWith) {
                    arrayList2.add(readLine);
                } else {
                    arrayList.add(readLine);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0013 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<org.apache.http.conn.util.PublicSuffixList> parseByType(java.io.Reader r9) throws java.io.IOException {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 2
            r0.<init>(r1)
            java.io.BufferedReader r1 = new java.io.BufferedReader
            r1.<init>(r9)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r2 = 256(0x100, float:3.59E-43)
            r9.<init>(r2)
            r9 = 0
        L_0x0013:
            r2 = r9
            r3 = r2
            r4 = r3
        L_0x0016:
            java.lang.String r5 = r1.readLine()
            if (r5 == 0) goto L_0x0092
            boolean r6 = r5.isEmpty()
            if (r6 == 0) goto L_0x0023
            goto L_0x0016
        L_0x0023:
            java.lang.String r6 = "//"
            boolean r6 = r5.startsWith(r6)
            if (r6 == 0) goto L_0x005e
            if (r2 != 0) goto L_0x0043
            java.lang.String r6 = "===BEGIN ICANN DOMAINS==="
            boolean r6 = r5.contains(r6)
            if (r6 == 0) goto L_0x0038
            org.apache.http.conn.util.DomainType r2 = org.apache.http.conn.util.DomainType.ICANN
            goto L_0x0016
        L_0x0038:
            java.lang.String r6 = "===BEGIN PRIVATE DOMAINS==="
            boolean r5 = r5.contains(r6)
            if (r5 == 0) goto L_0x0016
            org.apache.http.conn.util.DomainType r2 = org.apache.http.conn.util.DomainType.PRIVATE
            goto L_0x0016
        L_0x0043:
            java.lang.String r6 = "===END ICANN DOMAINS==="
            boolean r6 = r5.contains(r6)
            if (r6 != 0) goto L_0x0053
            java.lang.String r6 = "===END PRIVATE DOMAINS==="
            boolean r5 = r5.contains(r6)
            if (r5 == 0) goto L_0x0016
        L_0x0053:
            if (r3 == 0) goto L_0x0013
            org.apache.http.conn.util.PublicSuffixList r5 = new org.apache.http.conn.util.PublicSuffixList
            r5.<init>(r2, r3, r4)
            r0.add(r5)
            goto L_0x0013
        L_0x005e:
            if (r2 != 0) goto L_0x0061
            goto L_0x0016
        L_0x0061:
            java.lang.String r6 = "."
            boolean r6 = r5.startsWith(r6)
            r7 = 1
            if (r6 == 0) goto L_0x006e
            java.lang.String r5 = r5.substring(r7)
        L_0x006e:
            java.lang.String r6 = "!"
            boolean r6 = r5.startsWith(r6)
            if (r6 == 0) goto L_0x007a
            java.lang.String r5 = r5.substring(r7)
        L_0x007a:
            if (r6 == 0) goto L_0x0087
            if (r4 != 0) goto L_0x0083
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
        L_0x0083:
            r4.add(r5)
            goto L_0x0016
        L_0x0087:
            if (r3 != 0) goto L_0x008e
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
        L_0x008e:
            r3.add(r5)
            goto L_0x0016
        L_0x0092:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.conn.util.PublicSuffixListParser.parseByType(java.io.Reader):java.util.List");
    }
}
