package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.util.Args;

public class RequestDate implements HttpRequestInterceptor {
    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            String str = "Date";
            if (!httpRequest.containsHeader(str)) {
                httpRequest.setHeader(str, DATE_GENERATOR.getCurrentDate());
            }
        }
    }
}
