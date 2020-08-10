package org.apache.http.client.protocol;

import com.google.api.client.http.HttpMethods;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class RequestClientConnControl implements HttpRequestInterceptor {
    private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";
    private final Log log = LogFactory.getLog(getClass());

    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        boolean equalsIgnoreCase = httpRequest.getRequestLine().getMethod().equalsIgnoreCase(HttpMethods.CONNECT);
        String str = HTTP.CONN_KEEP_ALIVE;
        String str2 = PROXY_CONN_DIRECTIVE;
        if (equalsIgnoreCase) {
            httpRequest.setHeader(str2, str);
            return;
        }
        RouteInfo httpRoute = HttpClientContext.adapt(httpContext).getHttpRoute();
        if (httpRoute == null) {
            this.log.debug("Connection route not set in the context");
            return;
        }
        if (httpRoute.getHopCount() == 1 || httpRoute.isTunnelled()) {
            String str3 = "Connection";
            if (!httpRequest.containsHeader(str3)) {
                httpRequest.addHeader(str3, str);
            }
        }
        if (httpRoute.getHopCount() == 2 && !httpRoute.isTunnelled() && !httpRequest.containsHeader(str2)) {
            httpRequest.addHeader(str2, str);
        }
    }
}
