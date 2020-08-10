package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.util.Args;

public class ResponseConnControl implements HttpResponseInterceptor {
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        HttpCoreContext adapt = HttpCoreContext.adapt(httpContext);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String str = HTTP.CONN_CLOSE;
        String str2 = "Connection";
        if (statusCode == 400 || statusCode == 408 || statusCode == 411 || statusCode == 413 || statusCode == 414 || statusCode == 503 || statusCode == 501) {
            httpResponse.setHeader(str2, str);
            return;
        }
        Header firstHeader = httpResponse.getFirstHeader(str2);
        if (firstHeader == null || !str.equalsIgnoreCase(firstHeader.getValue())) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
                if (entity.getContentLength() < 0 && (!entity.isChunked() || protocolVersion.lessEquals(HttpVersion.HTTP_1_0))) {
                    httpResponse.setHeader(str2, str);
                    return;
                }
            }
            HttpRequest request = adapt.getRequest();
            if (request != null) {
                Header firstHeader2 = request.getFirstHeader(str2);
                if (firstHeader2 != null) {
                    httpResponse.setHeader(str2, firstHeader2.getValue());
                } else if (request.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                    httpResponse.setHeader(str2, str);
                }
            }
        }
    }
}
