package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

public class RequestUserAgent implements HttpRequestInterceptor {
    private final String userAgent;

    public RequestUserAgent(String str) {
        this.userAgent = str;
    }

    public RequestUserAgent() {
        this(null);
    }

    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        String str = "User-Agent";
        if (!httpRequest.containsHeader(str)) {
            String str2 = null;
            HttpParams params = httpRequest.getParams();
            if (params != null) {
                str2 = (String) params.getParameter(CoreProtocolPNames.USER_AGENT);
            }
            if (str2 == null) {
                str2 = this.userAgent;
            }
            if (str2 != null) {
                httpRequest.addHeader(str, str2);
            }
        }
    }
}
