package org.apache.http.protocol;

import com.google.api.client.http.HttpMethods;
import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.util.Args;

public class RequestConnControl implements HttpRequestInterceptor {
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase(HttpMethods.CONNECT)) {
            String str = "Connection";
            if (!httpRequest.containsHeader(str)) {
                httpRequest.addHeader(str, HTTP.CONN_KEEP_ALIVE);
            }
        }
    }
}
