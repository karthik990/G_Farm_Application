package p043io.opencensus.contrib.grpc.metrics;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import p043io.opencensus.stats.Measure.MeasureDouble;
import p043io.opencensus.stats.Measure.MeasureLong;
import p043io.opencensus.tags.TagKey;

/* renamed from: io.opencensus.contrib.grpc.metrics.RpcMeasureConstants */
public final class RpcMeasureConstants {
    private static final String BYTE = "By";
    private static final String COUNT = "1";
    private static final String MILLISECOND = "ms";
    public static final MeasureLong RPC_CLIENT_ERROR_COUNT;
    public static final MeasureLong RPC_CLIENT_FINISHED_COUNT;
    public static final MeasureDouble RPC_CLIENT_REQUEST_BYTES;
    public static final MeasureLong RPC_CLIENT_REQUEST_COUNT;
    public static final MeasureDouble RPC_CLIENT_RESPONSE_BYTES;
    public static final MeasureLong RPC_CLIENT_RESPONSE_COUNT;
    public static final MeasureDouble RPC_CLIENT_ROUNDTRIP_LATENCY;
    public static final MeasureDouble RPC_CLIENT_SERVER_ELAPSED_TIME;
    public static final MeasureLong RPC_CLIENT_STARTED_COUNT;
    public static final MeasureDouble RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES;
    public static final MeasureDouble RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES;
    public static final TagKey RPC_METHOD = TagKey.create(Param.METHOD);
    public static final MeasureLong RPC_SERVER_ERROR_COUNT;
    public static final MeasureLong RPC_SERVER_FINISHED_COUNT;
    public static final MeasureDouble RPC_SERVER_REQUEST_BYTES;
    public static final MeasureLong RPC_SERVER_REQUEST_COUNT;
    public static final MeasureDouble RPC_SERVER_RESPONSE_BYTES;
    public static final MeasureLong RPC_SERVER_RESPONSE_COUNT;
    public static final MeasureDouble RPC_SERVER_SERVER_ELAPSED_TIME;
    public static final MeasureDouble RPC_SERVER_SERVER_LATENCY;
    public static final MeasureLong RPC_SERVER_STARTED_COUNT;
    public static final MeasureDouble RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES;
    public static final MeasureDouble RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES;
    public static final TagKey RPC_STATUS = TagKey.create("canonical_status");

    static {
        String str = "RPC Errors";
        String str2 = "1";
        RPC_CLIENT_ERROR_COUNT = MeasureLong.create("grpc.io/client/error_count", str, str2);
        String str3 = "Request bytes";
        String str4 = BYTE;
        RPC_CLIENT_REQUEST_BYTES = MeasureDouble.create("grpc.io/client/request_bytes", str3, str4);
        String str5 = "Response bytes";
        RPC_CLIENT_RESPONSE_BYTES = MeasureDouble.create("grpc.io/client/response_bytes", str5, str4);
        String str6 = MILLISECOND;
        RPC_CLIENT_ROUNDTRIP_LATENCY = MeasureDouble.create("grpc.io/client/roundtrip_latency", "RPC roundtrip latency msec", str6);
        String str7 = "Server elapsed time in msecs";
        RPC_CLIENT_SERVER_ELAPSED_TIME = MeasureDouble.create("grpc.io/client/server_elapsed_time", str7, str6);
        String str8 = "Uncompressed Request bytes";
        RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES = MeasureDouble.create("grpc.io/client/uncompressed_request_bytes", str8, str4);
        String str9 = "Uncompressed Response bytes";
        RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES = MeasureDouble.create("grpc.io/client/uncompressed_response_bytes", str9, str4);
        RPC_CLIENT_STARTED_COUNT = MeasureLong.create("grpc.io/client/started_count", "Number of client RPCs (streams) started", str2);
        RPC_CLIENT_FINISHED_COUNT = MeasureLong.create("grpc.io/client/finished_count", "Number of client RPCs (streams) finished", str2);
        RPC_CLIENT_REQUEST_COUNT = MeasureLong.create("grpc.io/client/request_count", "Number of client RPC request messages", str2);
        RPC_CLIENT_RESPONSE_COUNT = MeasureLong.create("grpc.io/client/response_count", "Number of client RPC response messages", str2);
        RPC_SERVER_ERROR_COUNT = MeasureLong.create("grpc.io/server/error_count", str, str2);
        RPC_SERVER_REQUEST_BYTES = MeasureDouble.create("grpc.io/server/request_bytes", str3, str4);
        RPC_SERVER_RESPONSE_BYTES = MeasureDouble.create("grpc.io/server/response_bytes", str5, str4);
        RPC_SERVER_SERVER_ELAPSED_TIME = MeasureDouble.create("grpc.io/server/server_elapsed_time", str7, str6);
        RPC_SERVER_SERVER_LATENCY = MeasureDouble.create("grpc.io/server/server_latency", "Latency in msecs", str6);
        RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES = MeasureDouble.create("grpc.io/server/uncompressed_request_bytes", str8, str4);
        RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES = MeasureDouble.create("grpc.io/server/uncompressed_response_bytes", str9, str4);
        RPC_SERVER_STARTED_COUNT = MeasureLong.create("grpc.io/server/started_count", "Number of server RPCs (streams) started", str2);
        RPC_SERVER_FINISHED_COUNT = MeasureLong.create("grpc.io/server/finished_count", "Number of server RPCs (streams) finished", str2);
        RPC_SERVER_REQUEST_COUNT = MeasureLong.create("grpc.io/server/request_count", "Number of server RPC request messages", str2);
        RPC_SERVER_RESPONSE_COUNT = MeasureLong.create("grpc.io/server/response_count", "Number of server RPC response messages", str2);
    }

    RpcMeasureConstants() {
        throw new AssertionError();
    }
}
