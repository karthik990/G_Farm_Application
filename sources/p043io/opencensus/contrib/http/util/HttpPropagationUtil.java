package p043io.opencensus.contrib.http.util;

import p043io.opencensus.trace.propagation.TextFormat;

/* renamed from: io.opencensus.contrib.http.util.HttpPropagationUtil */
public class HttpPropagationUtil {
    private HttpPropagationUtil() {
    }

    public static TextFormat getCloudTraceFormat() {
        return new CloudTraceFormat();
    }
}
