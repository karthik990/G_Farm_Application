package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.entity.DecompressingEntity;
import org.apache.http.client.entity.DeflateInputStreamFactory;
import org.apache.http.client.entity.GZIPInputStreamFactory;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import p043io.fabric.sdk.android.services.network.HttpRequest;

public class ResponseContentEncoding implements HttpResponseInterceptor {
    public static final String UNCOMPRESSED = "http.client.response.uncompressed";
    private final Lookup<InputStreamFactory> decoderRegistry;
    private final boolean ignoreUnknown;

    public ResponseContentEncoding(Lookup<InputStreamFactory> lookup, boolean z) {
        if (lookup == null) {
            String str = "x-gzip";
            String str2 = "deflate";
            lookup = RegistryBuilder.create().register(HttpRequest.ENCODING_GZIP, GZIPInputStreamFactory.getInstance()).register(str, GZIPInputStreamFactory.getInstance()).register(str2, DeflateInputStreamFactory.getInstance()).build();
        }
        this.decoderRegistry = lookup;
        this.ignoreUnknown = z;
    }

    public ResponseContentEncoding(boolean z) {
        this(null, z);
    }

    public ResponseContentEncoding(Lookup<InputStreamFactory> lookup) {
        this(lookup, true);
    }

    public ResponseContentEncoding() {
        this(null);
    }

    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        HeaderElement[] elements;
        HttpEntity entity = httpResponse.getEntity();
        if (HttpClientContext.adapt(httpContext).getRequestConfig().isContentCompressionEnabled() && entity != null && entity.getContentLength() != 0) {
            Header contentEncoding = entity.getContentEncoding();
            if (contentEncoding != null) {
                for (HeaderElement headerElement : contentEncoding.getElements()) {
                    String lowerCase = headerElement.getName().toLowerCase(Locale.ROOT);
                    InputStreamFactory inputStreamFactory = (InputStreamFactory) this.decoderRegistry.lookup(lowerCase);
                    if (inputStreamFactory != null) {
                        httpResponse.setEntity(new DecompressingEntity(httpResponse.getEntity(), inputStreamFactory));
                        httpResponse.removeHeaders("Content-Length");
                        httpResponse.removeHeaders("Content-Encoding");
                        httpResponse.removeHeaders("Content-MD5");
                    } else if (!HTTP.IDENTITY_CODING.equals(lowerCase) && !this.ignoreUnknown) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unsupported Content-Encoding: ");
                        sb.append(headerElement.getName());
                        throw new HttpException(sb.toString());
                    }
                }
            }
        }
    }
}
