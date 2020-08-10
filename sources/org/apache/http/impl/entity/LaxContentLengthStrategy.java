package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

public class LaxContentLengthStrategy implements ContentLengthStrategy {
    public static final LaxContentLengthStrategy INSTANCE = new LaxContentLengthStrategy();
    private final int implicitLen;

    public LaxContentLengthStrategy(int i) {
        this.implicitLen = i;
    }

    public LaxContentLengthStrategy() {
        this(-1);
    }

    public long determineLength(HttpMessage httpMessage) throws HttpException {
        long j;
        Args.notNull(httpMessage, "HTTP message");
        Header firstHeader = httpMessage.getFirstHeader("Transfer-Encoding");
        long j2 = -1;
        if (firstHeader != null) {
            try {
                HeaderElement[] elements = firstHeader.getElements();
                int length = elements.length;
                if (!HTTP.IDENTITY_CODING.equalsIgnoreCase(firstHeader.getValue()) && length > 0) {
                    if (HTTP.CHUNK_CODING.equalsIgnoreCase(elements[length - 1].getName())) {
                        return -2;
                    }
                }
                return -1;
            } catch (ParseException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid Transfer-Encoding header value: ");
                sb.append(firstHeader);
                throw new ProtocolException(sb.toString(), e);
            }
        } else {
            String str = "Content-Length";
            if (httpMessage.getFirstHeader(str) == null) {
                return (long) this.implicitLen;
            }
            Header[] headers = httpMessage.getHeaders(str);
            int length2 = headers.length - 1;
            while (true) {
                if (length2 < 0) {
                    j = -1;
                    break;
                }
                try {
                    j = Long.parseLong(headers[length2].getValue());
                    break;
                } catch (NumberFormatException unused) {
                    length2--;
                }
            }
            if (j >= 0) {
                j2 = j;
            }
            return j2;
        }
    }
}
