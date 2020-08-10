package org.apache.http.conn.ssl;

@Deprecated
public class AllowAllHostnameVerifier extends AbstractVerifier {
    public static final AllowAllHostnameVerifier INSTANCE = new AllowAllHostnameVerifier();

    public final String toString() {
        return "ALLOW_ALL";
    }

    public final void verify(String str, String[] strArr, String[] strArr2) {
    }
}
