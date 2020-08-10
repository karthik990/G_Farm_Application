package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.util.Args;

public class ResponseServer implements HttpResponseInterceptor {
    private final String originServer;

    public ResponseServer(String str) {
        this.originServer = str;
    }

    public ResponseServer() {
        this(null);
    }

    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        String str = "Server";
        if (!httpResponse.containsHeader(str)) {
            String str2 = this.originServer;
            if (str2 != null) {
                httpResponse.addHeader(str, str2);
            }
        }
    }
}
