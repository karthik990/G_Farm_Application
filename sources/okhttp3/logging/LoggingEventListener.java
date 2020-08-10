package okhttp3.logging;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor.Logger;

public final class LoggingEventListener extends EventListener {
    private final Logger logger;
    private long startNs;

    public static class Factory implements okhttp3.EventListener.Factory {
        private final Logger logger;

        public Factory() {
            this(Logger.DEFAULT);
        }

        public Factory(Logger logger2) {
            this.logger = logger2;
        }

        public EventListener create(Call call) {
            return new LoggingEventListener(this.logger);
        }
    }

    private LoggingEventListener(Logger logger2) {
        this.logger = logger2;
    }

    public void callStart(Call call) {
        this.startNs = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        sb.append("callStart: ");
        sb.append(call.request());
        logWithTime(sb.toString());
    }

    public void dnsStart(Call call, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("dnsStart: ");
        sb.append(str);
        logWithTime(sb.toString());
    }

    public void dnsEnd(Call call, String str, List<InetAddress> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("dnsEnd: ");
        sb.append(list);
        logWithTime(sb.toString());
    }

    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        StringBuilder sb = new StringBuilder();
        sb.append("connectStart: ");
        sb.append(inetSocketAddress);
        sb.append(" ");
        sb.append(proxy);
        logWithTime(sb.toString());
    }

    public void secureConnectStart(Call call) {
        logWithTime("secureConnectStart");
    }

    public void secureConnectEnd(Call call, @Nullable Handshake handshake) {
        logWithTime("secureConnectEnd");
    }

    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol) {
        StringBuilder sb = new StringBuilder();
        sb.append("connectEnd: ");
        sb.append(protocol);
        logWithTime(sb.toString());
    }

    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol, IOException iOException) {
        StringBuilder sb = new StringBuilder();
        sb.append("connectFailed: ");
        sb.append(protocol);
        sb.append(" ");
        sb.append(iOException);
        logWithTime(sb.toString());
    }

    public void connectionAcquired(Call call, Connection connection) {
        StringBuilder sb = new StringBuilder();
        sb.append("connectionAcquired: ");
        sb.append(connection);
        logWithTime(sb.toString());
    }

    public void connectionReleased(Call call, Connection connection) {
        logWithTime("connectionReleased");
    }

    public void requestHeadersStart(Call call) {
        logWithTime("requestHeadersStart");
    }

    public void requestHeadersEnd(Call call, Request request) {
        logWithTime("requestHeadersEnd");
    }

    public void requestBodyStart(Call call) {
        logWithTime("requestBodyStart");
    }

    public void requestBodyEnd(Call call, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append("requestBodyEnd: byteCount=");
        sb.append(j);
        logWithTime(sb.toString());
    }

    public void responseHeadersStart(Call call) {
        logWithTime("responseHeadersStart");
    }

    public void responseHeadersEnd(Call call, Response response) {
        StringBuilder sb = new StringBuilder();
        sb.append("responseHeadersEnd: ");
        sb.append(response);
        logWithTime(sb.toString());
    }

    public void responseBodyStart(Call call) {
        logWithTime("responseBodyStart");
    }

    public void responseBodyEnd(Call call, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append("responseBodyEnd: byteCount=");
        sb.append(j);
        logWithTime(sb.toString());
    }

    public void callEnd(Call call) {
        logWithTime("callEnd");
    }

    public void callFailed(Call call, IOException iOException) {
        StringBuilder sb = new StringBuilder();
        sb.append("callFailed: ");
        sb.append(iOException);
        logWithTime(sb.toString());
    }

    private void logWithTime(String str) {
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.startNs);
        Logger logger2 = this.logger;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(millis);
        sb.append(" ms] ");
        sb.append(str);
        logger2.log(sb.toString());
    }
}
