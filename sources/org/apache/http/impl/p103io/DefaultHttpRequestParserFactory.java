package org.apache.http.impl.p103io;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.p104io.HttpMessageParser;
import org.apache.http.p104io.HttpMessageParserFactory;
import org.apache.http.p104io.SessionInputBuffer;

/* renamed from: org.apache.http.impl.io.DefaultHttpRequestParserFactory */
public class DefaultHttpRequestParserFactory implements HttpMessageParserFactory<HttpRequest> {
    public static final DefaultHttpRequestParserFactory INSTANCE = new DefaultHttpRequestParserFactory();
    private final LineParser lineParser;
    private final HttpRequestFactory requestFactory;

    public DefaultHttpRequestParserFactory(LineParser lineParser2, HttpRequestFactory httpRequestFactory) {
        if (lineParser2 == null) {
            lineParser2 = BasicLineParser.INSTANCE;
        }
        this.lineParser = lineParser2;
        if (httpRequestFactory == null) {
            httpRequestFactory = DefaultHttpRequestFactory.INSTANCE;
        }
        this.requestFactory = httpRequestFactory;
    }

    public DefaultHttpRequestParserFactory() {
        this(null, null);
    }

    public HttpMessageParser<HttpRequest> create(SessionInputBuffer sessionInputBuffer, MessageConstraints messageConstraints) {
        return new DefaultHttpRequestParser(sessionInputBuffer, this.lineParser, this.requestFactory, messageConstraints);
    }
}
