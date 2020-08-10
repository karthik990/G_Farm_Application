package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class RequestExpectContinue implements HttpRequestInterceptor {
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        String str = "Expect";
        if (!httpRequest.containsHeader(str) && (httpRequest instanceof HttpEntityEnclosingRequest)) {
            ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
            if (entity != null && entity.getContentLength() != 0 && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0) && HttpClientContext.adapt(httpContext).getRequestConfig().isExpectContinueEnabled()) {
                httpRequest.addHeader(str, HTTP.EXPECT_CONTINUE);
            }
        }
    }
}
