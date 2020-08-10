package p043io.opencensus.contrib.grpc.metrics;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import p043io.opencensus.stats.Stats;
import p043io.opencensus.stats.View;
import p043io.opencensus.stats.ViewManager;

/* renamed from: io.opencensus.contrib.grpc.metrics.RpcViews */
public final class RpcViews {
    static final ImmutableSet<View> RPC_CUMULATIVE_VIEWS_SET = ImmutableSet.m1931of(RpcViewConstants.RPC_CLIENT_ERROR_COUNT_VIEW, RpcViewConstants.RPC_CLIENT_ROUNDTRIP_LATENCY_VIEW, RpcViewConstants.RPC_CLIENT_REQUEST_BYTES_VIEW, RpcViewConstants.RPC_CLIENT_RESPONSE_BYTES_VIEW, RpcViewConstants.RPC_CLIENT_REQUEST_COUNT_VIEW, RpcViewConstants.RPC_CLIENT_RESPONSE_COUNT_VIEW, RpcViewConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_VIEW, RpcViewConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_VIEW, RpcViewConstants.RPC_CLIENT_SERVER_ELAPSED_TIME_VIEW, RpcViewConstants.RPC_CLIENT_STARTED_COUNT_CUMULATIVE_VIEW, RpcViewConstants.RPC_CLIENT_FINISHED_COUNT_CUMULATIVE_VIEW, RpcViewConstants.RPC_SERVER_ERROR_COUNT_VIEW, RpcViewConstants.RPC_SERVER_SERVER_LATENCY_VIEW, RpcViewConstants.RPC_SERVER_SERVER_ELAPSED_TIME_VIEW, RpcViewConstants.RPC_SERVER_REQUEST_BYTES_VIEW, RpcViewConstants.RPC_SERVER_RESPONSE_BYTES_VIEW, RpcViewConstants.RPC_SERVER_REQUEST_COUNT_VIEW, RpcViewConstants.RPC_SERVER_RESPONSE_COUNT_VIEW, RpcViewConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_VIEW, RpcViewConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_VIEW, RpcViewConstants.RPC_SERVER_STARTED_COUNT_CUMULATIVE_VIEW, RpcViewConstants.RPC_SERVER_FINISHED_COUNT_CUMULATIVE_VIEW);
    static final ImmutableSet<View> RPC_INTERVAL_VIEWS_SET = ImmutableSet.m1931of(RpcViewConstants.RPC_CLIENT_ERROR_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_ROUNDTRIP_LATENCY_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_REQUEST_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_RESPONSE_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_REQUEST_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_RESPONSE_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_SERVER_ELAPSED_TIME_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_STARTED_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_FINISHED_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_ERROR_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_SERVER_LATENCY_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_SERVER_ELAPSED_TIME_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_REQUEST_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_RESPONSE_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_REQUEST_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_RESPONSE_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_STARTED_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_SERVER_FINISHED_COUNT_MINUTE_VIEW, RpcViewConstants.RPC_CLIENT_ERROR_COUNT_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_ROUNDTRIP_LATENCY_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_REQUEST_BYTES_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_RESPONSE_BYTES_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_REQUEST_COUNT_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_RESPONSE_COUNT_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_SERVER_ELAPSED_TIME_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_STARTED_COUNT_HOUR_VIEW, RpcViewConstants.RPC_CLIENT_FINISHED_COUNT_HOUR_VIEW, RpcViewConstants.RPC_SERVER_ERROR_COUNT_HOUR_VIEW, RpcViewConstants.RPC_SERVER_SERVER_LATENCY_HOUR_VIEW, RpcViewConstants.RPC_SERVER_SERVER_ELAPSED_TIME_HOUR_VIEW, RpcViewConstants.RPC_SERVER_REQUEST_BYTES_HOUR_VIEW, RpcViewConstants.RPC_SERVER_RESPONSE_BYTES_HOUR_VIEW, RpcViewConstants.RPC_SERVER_REQUEST_COUNT_HOUR_VIEW, RpcViewConstants.RPC_SERVER_RESPONSE_COUNT_HOUR_VIEW, RpcViewConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_HOUR_VIEW, RpcViewConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_HOUR_VIEW, RpcViewConstants.RPC_SERVER_STARTED_COUNT_HOUR_VIEW, RpcViewConstants.RPC_SERVER_FINISHED_COUNT_HOUR_VIEW);

    public static void registerAllCumulativeViews() {
        registerAllCumulativeViews(Stats.getViewManager());
    }

    static void registerAllCumulativeViews(ViewManager viewManager) {
        UnmodifiableIterator it = RPC_CUMULATIVE_VIEWS_SET.iterator();
        while (it.hasNext()) {
            viewManager.registerView((View) it.next());
        }
    }

    public static void registerAllIntervalViews() {
        registerAllIntervalViews(Stats.getViewManager());
    }

    static void registerAllIntervalViews(ViewManager viewManager) {
        UnmodifiableIterator it = RPC_INTERVAL_VIEWS_SET.iterator();
        while (it.hasNext()) {
            viewManager.registerView((View) it.next());
        }
    }

    public static void registerAllViews() {
        registerAllViews(Stats.getViewManager());
    }

    static void registerAllViews(ViewManager viewManager) {
        registerAllCumulativeViews(viewManager);
        registerAllIntervalViews(viewManager);
    }

    private RpcViews() {
    }
}
