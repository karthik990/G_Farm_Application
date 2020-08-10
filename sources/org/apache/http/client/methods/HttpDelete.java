package org.apache.http.client.methods;

import java.net.URI;

public class HttpDelete extends HttpRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return "DELETE";
    }

    public HttpDelete() {
    }

    public HttpDelete(URI uri) {
        setURI(uri);
    }

    public HttpDelete(String str) {
        setURI(URI.create(str));
    }
}
