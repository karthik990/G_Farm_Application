package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

public class RequestAcceptEncoding implements HttpRequestInterceptor {
    private final String acceptEncoding;

    public RequestAcceptEncoding(List<String> list) {
        if (list == null || list.isEmpty()) {
            this.acceptEncoding = "gzip,deflate";
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append((String) list.get(i));
        }
        this.acceptEncoding = sb.toString();
    }

    public RequestAcceptEncoding() {
        this(null);
    }

    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        RequestConfig requestConfig = HttpClientContext.adapt(httpContext).getRequestConfig();
        String str = "Accept-Encoding";
        if (!httpRequest.containsHeader(str) && requestConfig.isContentCompressionEnabled()) {
            httpRequest.addHeader(str, this.acceptEncoding);
        }
    }
}
