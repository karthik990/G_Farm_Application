package p043io.opencensus.contrib.http.util;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.Arrays;
import java.util.Collections;
import p043io.opencensus.stats.Aggregation;
import p043io.opencensus.stats.Aggregation.Count;
import p043io.opencensus.stats.Aggregation.Distribution;
import p043io.opencensus.stats.BucketBoundaries;
import p043io.opencensus.stats.View;
import p043io.opencensus.stats.View.Name;
import p043io.opencensus.tags.TagKey;

/* renamed from: io.opencensus.contrib.http.util.HttpViewConstants */
public final class HttpViewConstants {
    static final Aggregation COUNT = Count.create();
    public static final View HTTP_CLIENT_COMPLETED_COUNT_VIEW;
    public static final View HTTP_CLIENT_RECEIVED_BYTES_VIEW;
    public static final View HTTP_CLIENT_ROUNDTRIP_LATENCY_VIEW;
    public static final View HTTP_CLIENT_SENT_BYTES_VIEW;
    public static final View HTTP_SERVER_COMPLETED_COUNT_VIEW;
    public static final View HTTP_SERVER_LATENCY_VIEW = View.create(Name.create("opencensus.io/http/server/server_latency"), "Latency distribution of server-side HTTP requests serving", HttpMeasureConstants.HTTP_SERVER_LATENCY, LATENCY_DISTRIBUTION, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS}));
    public static final View HTTP_SERVER_RECEIVED_BYTES_VIEW;
    public static final View HTTP_SERVER_SENT_BYTES_VIEW;
    static final Aggregation LATENCY_DISTRIBUTION;
    static final Aggregation SIZE_DISTRIBUTION;

    private HttpViewConstants() {
    }

    static {
        Double valueOf = Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        SIZE_DISTRIBUTION = Distribution.create(BucketBoundaries.create(Collections.unmodifiableList(Arrays.asList(new Double[]{valueOf, Double.valueOf(1024.0d), Double.valueOf(2048.0d), Double.valueOf(4096.0d), Double.valueOf(16384.0d), Double.valueOf(65536.0d), Double.valueOf(262144.0d), Double.valueOf(1048576.0d), Double.valueOf(4194304.0d), Double.valueOf(1.6777216E7d), Double.valueOf(6.7108864E7d), Double.valueOf(2.68435456E8d), Double.valueOf(1.073741824E9d), Double.valueOf(4.294967296E9d)}))));
        LATENCY_DISTRIBUTION = Distribution.create(BucketBoundaries.create(Collections.unmodifiableList(Arrays.asList(new Double[]{valueOf, Double.valueOf(1.0d), Double.valueOf(2.0d), Double.valueOf(3.0d), Double.valueOf(4.0d), Double.valueOf(5.0d), Double.valueOf(6.0d), Double.valueOf(8.0d), Double.valueOf(10.0d), Double.valueOf(13.0d), Double.valueOf(16.0d), Double.valueOf(20.0d), Double.valueOf(25.0d), Double.valueOf(30.0d), Double.valueOf(40.0d), Double.valueOf(50.0d), Double.valueOf(65.0d), Double.valueOf(80.0d), Double.valueOf(100.0d), Double.valueOf(130.0d), Double.valueOf(160.0d), Double.valueOf(200.0d), Double.valueOf(250.0d), Double.valueOf(300.0d), Double.valueOf(400.0d), Double.valueOf(500.0d), Double.valueOf(650.0d), Double.valueOf(800.0d), Double.valueOf(1000.0d), Double.valueOf(2000.0d), Double.valueOf(5000.0d), Double.valueOf(10000.0d), Double.valueOf(20000.0d), Double.valueOf(50000.0d), Double.valueOf(100000.0d)}))));
        String str = "Count of client-side HTTP requests completed";
        HTTP_CLIENT_COMPLETED_COUNT_VIEW = View.create(Name.create("opencensus.io/http/client/completed_count"), str, HttpMeasureConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY, COUNT, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS}));
        String str2 = "Size distribution of client-side HTTP request body";
        HTTP_CLIENT_SENT_BYTES_VIEW = View.create(Name.create("opencensus.io/http/client/sent_bytes"), str2, HttpMeasureConstants.HTTP_CLIENT_SENT_BYTES, SIZE_DISTRIBUTION, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS}));
        String str3 = "Size distribution of client-side HTTP response body";
        HTTP_CLIENT_RECEIVED_BYTES_VIEW = View.create(Name.create("opencensus.io/http/client/received_bytes"), str3, HttpMeasureConstants.HTTP_CLIENT_RECEIVED_BYTES, SIZE_DISTRIBUTION, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS}));
        String str4 = "Roundtrip latency distribution of client-side HTTP requests";
        HTTP_CLIENT_ROUNDTRIP_LATENCY_VIEW = View.create(Name.create("opencensus.io/http/client/roundtrip_latency"), str4, HttpMeasureConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY, LATENCY_DISTRIBUTION, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS}));
        String str5 = "Count of HTTP server-side requests serving completed";
        HTTP_SERVER_COMPLETED_COUNT_VIEW = View.create(Name.create("opencensus.io/http/server/completed_count"), str5, HttpMeasureConstants.HTTP_SERVER_LATENCY, COUNT, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS}));
        String str6 = "Size distribution of server-side HTTP request body";
        HTTP_SERVER_RECEIVED_BYTES_VIEW = View.create(Name.create("opencensus.io/http/server/received_bytes"), str6, HttpMeasureConstants.HTTP_SERVER_RECEIVED_BYTES, SIZE_DISTRIBUTION, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS}));
        String str7 = "Size distribution of server-side HTTP response body";
        HTTP_SERVER_SENT_BYTES_VIEW = View.create(Name.create("opencensus.io/http/server/sent_bytes"), str7, HttpMeasureConstants.HTTP_SERVER_SENT_BYTES, SIZE_DISTRIBUTION, Arrays.asList(new TagKey[]{HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS}));
    }
}
