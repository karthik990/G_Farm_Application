package com.google.firebase.database.connection;

import com.google.firebase.database.connection.util.StringListReader;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import com.google.firebase.database.tubesock.WebSocket;
import com.google.firebase.database.tubesock.WebSocketEventHandler;
import com.google.firebase.database.tubesock.WebSocketException;
import com.google.firebase.database.tubesock.WebSocketMessage;
import com.google.firebase.database.util.JsonMapper;
import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class WebsocketConnection {
    private static final long CONNECT_TIMEOUT_MS = 30000;
    private static final long KEEP_ALIVE_TIMEOUT_MS = 45000;
    private static final int MAX_FRAME_SIZE = 16384;
    private static long connectionId;
    /* access modifiers changed from: private */
    public WSClient conn;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> connectTimeout;
    private final ConnectionContext connectionContext;
    private Delegate delegate;
    /* access modifiers changed from: private */
    public boolean everConnected = false;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService executorService;
    private StringListReader frameReader;
    private boolean isClosed = false;
    private ScheduledFuture<?> keepAlive;
    /* access modifiers changed from: private */
    public final LogWrapper logger;
    private long totalFrames = 0;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface Delegate {
        void onDisconnect(boolean z);

        void onMessage(Map<String, Object> map);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private interface WSClient {
        void close();

        void connect();

        void send(String str);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private class WSClientTubesock implements WSClient, WebSocketEventHandler {

        /* renamed from: ws */
        private WebSocket f2008ws;

        private WSClientTubesock(WebSocket webSocket) {
            this.f2008ws = webSocket;
            this.f2008ws.setEventHandler(this);
        }

        public void onOpen() {
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    WebsocketConnection.this.connectTimeout.cancel(false);
                    WebsocketConnection.this.everConnected = true;
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("websocket opened", new Object[0]);
                    }
                    WebsocketConnection.this.resetKeepAlive();
                }
            });
        }

        public void onMessage(WebSocketMessage webSocketMessage) {
            final String text = webSocketMessage.getText();
            if (WebsocketConnection.this.logger.logsDebug()) {
                LogWrapper access$200 = WebsocketConnection.this.logger;
                StringBuilder sb = new StringBuilder();
                sb.append("ws message: ");
                sb.append(text);
                access$200.debug(sb.toString(), new Object[0]);
            }
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    WebsocketConnection.this.handleIncomingFrame(text);
                }
            });
        }

        public void onClose() {
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("closed", new Object[0]);
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        public void onError(final WebSocketException webSocketException) {
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    if (webSocketException.getCause() == null || !(webSocketException.getCause() instanceof EOFException)) {
                        WebsocketConnection.this.logger.debug("WebSocket error.", webSocketException, new Object[0]);
                    } else {
                        WebsocketConnection.this.logger.debug("WebSocket reached EOF.", new Object[0]);
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        public void onLogMessage(String str) {
            if (WebsocketConnection.this.logger.logsDebug()) {
                LogWrapper access$200 = WebsocketConnection.this.logger;
                StringBuilder sb = new StringBuilder();
                sb.append("Tubesock: ");
                sb.append(str);
                access$200.debug(sb.toString(), new Object[0]);
            }
        }

        public void send(String str) {
            this.f2008ws.send(str);
        }

        public void close() {
            this.f2008ws.close();
        }

        private void shutdown() {
            this.f2008ws.close();
            try {
                this.f2008ws.blockClose();
            } catch (InterruptedException e) {
                WebsocketConnection.this.logger.error("Interrupted while shutting down websocket threads", e);
            }
        }

        public void connect() {
            try {
                this.f2008ws.connect();
            } catch (WebSocketException e) {
                if (WebsocketConnection.this.logger.logsDebug()) {
                    WebsocketConnection.this.logger.debug("Error connecting", e, new Object[0]);
                }
                shutdown();
            }
        }
    }

    public void start() {
    }

    public WebsocketConnection(ConnectionContext connectionContext2, HostInfo hostInfo, String str, Delegate delegate2, String str2) {
        this.connectionContext = connectionContext2;
        this.executorService = connectionContext2.getExecutorService();
        this.delegate = delegate2;
        long j = connectionId;
        connectionId = 1 + j;
        Logger logger2 = connectionContext2.getLogger();
        StringBuilder sb = new StringBuilder();
        sb.append("ws_");
        sb.append(j);
        this.logger = new LogWrapper(logger2, "WebSocket", sb.toString());
        this.conn = createConnection(hostInfo, str, str2);
    }

    private WSClient createConnection(HostInfo hostInfo, String str, String str2) {
        if (str == null) {
            str = hostInfo.getHost();
        }
        URI connectionUrl = HostInfo.getConnectionUrl(str, hostInfo.isSecure(), hostInfo.getNamespace(), str2);
        HashMap hashMap = new HashMap();
        hashMap.put("User-Agent", this.connectionContext.getUserAgent());
        return new WSClientTubesock(new WebSocket(this.connectionContext, connectionUrl, null, hashMap));
    }

    public void open() {
        this.conn.connect();
        this.connectTimeout = this.executorService.schedule(new Runnable() {
            public void run() {
                WebsocketConnection.this.closeIfNeverConnected();
            }
        }, 30000, TimeUnit.MILLISECONDS);
    }

    public void close() {
        if (this.logger.logsDebug()) {
            this.logger.debug("websocket is being closed", new Object[0]);
        }
        this.isClosed = true;
        this.conn.close();
        ScheduledFuture<?> scheduledFuture = this.connectTimeout;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        ScheduledFuture<?> scheduledFuture2 = this.keepAlive;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(true);
        }
    }

    public void send(Map<String, Object> map) {
        resetKeepAlive();
        try {
            String[] splitIntoFrames = splitIntoFrames(JsonMapper.serializeJson(map), 16384);
            if (splitIntoFrames.length > 1) {
                WSClient wSClient = this.conn;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(splitIntoFrames.length);
                wSClient.send(sb.toString());
            }
            for (String send : splitIntoFrames) {
                this.conn.send(send);
            }
        } catch (IOException e) {
            LogWrapper logWrapper = this.logger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to serialize message: ");
            sb2.append(map.toString());
            logWrapper.error(sb2.toString(), e);
            shutdown();
        }
    }

    private void appendFrame(String str) {
        this.frameReader.addString(str);
        this.totalFrames--;
        if (this.totalFrames == 0) {
            try {
                this.frameReader.freeze();
                Map parseJson = JsonMapper.parseJson(this.frameReader.toString());
                this.frameReader = null;
                if (this.logger.logsDebug()) {
                    LogWrapper logWrapper = this.logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append("handleIncomingFrame complete frame: ");
                    sb.append(parseJson);
                    logWrapper.debug(sb.toString(), new Object[0]);
                }
                this.delegate.onMessage(parseJson);
            } catch (IOException e) {
                LogWrapper logWrapper2 = this.logger;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error parsing frame: ");
                sb2.append(this.frameReader.toString());
                logWrapper2.error(sb2.toString(), e);
                close();
                shutdown();
            } catch (ClassCastException e2) {
                LogWrapper logWrapper3 = this.logger;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error parsing frame (cast error): ");
                sb3.append(this.frameReader.toString());
                logWrapper3.error(sb3.toString(), e2);
                close();
                shutdown();
            }
        }
    }

    private void handleNewFrameCount(int i) {
        this.totalFrames = (long) i;
        this.frameReader = new StringListReader();
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder sb = new StringBuilder();
            sb.append("HandleNewFrameCount: ");
            sb.append(this.totalFrames);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
    }

    private String extractFrameCount(String str) {
        if (str.length() <= 6) {
            try {
                int parseInt = Integer.parseInt(str);
                if (parseInt > 0) {
                    handleNewFrameCount(parseInt);
                }
                return null;
            } catch (NumberFormatException unused) {
            }
        }
        handleNewFrameCount(1);
        return str;
    }

    /* access modifiers changed from: private */
    public void handleIncomingFrame(String str) {
        if (!this.isClosed) {
            resetKeepAlive();
            if (isBuffering()) {
                appendFrame(str);
                return;
            }
            String extractFrameCount = extractFrameCount(str);
            if (extractFrameCount != null) {
                appendFrame(extractFrameCount);
            }
        }
    }

    /* access modifiers changed from: private */
    public void resetKeepAlive() {
        if (!this.isClosed) {
            ScheduledFuture<?> scheduledFuture = this.keepAlive;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                if (this.logger.logsDebug()) {
                    LogWrapper logWrapper = this.logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Reset keepAlive. Remaining: ");
                    sb.append(this.keepAlive.getDelay(TimeUnit.MILLISECONDS));
                    logWrapper.debug(sb.toString(), new Object[0]);
                }
            } else if (this.logger.logsDebug()) {
                this.logger.debug("Reset keepAlive", new Object[0]);
            }
            this.keepAlive = this.executorService.schedule(nop(), KEEP_ALIVE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        }
    }

    private Runnable nop() {
        return new Runnable() {
            public void run() {
                if (WebsocketConnection.this.conn != null) {
                    WebsocketConnection.this.conn.send("0");
                    WebsocketConnection.this.resetKeepAlive();
                }
            }
        };
    }

    private boolean isBuffering() {
        return this.frameReader != null;
    }

    /* access modifiers changed from: private */
    public void onClosed() {
        if (!this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("closing itself", new Object[0]);
            }
            shutdown();
        }
        this.conn = null;
        ScheduledFuture<?> scheduledFuture = this.keepAlive;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    private void shutdown() {
        this.isClosed = true;
        this.delegate.onDisconnect(this.everConnected);
    }

    /* access modifiers changed from: private */
    public void closeIfNeverConnected() {
        if (!this.everConnected && !this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("timed out on connect", new Object[0]);
            }
            this.conn.close();
        }
    }

    private static String[] splitIntoFrames(String str, int i) {
        int i2 = 0;
        if (str.length() <= i) {
            return new String[]{str};
        }
        ArrayList arrayList = new ArrayList();
        while (i2 < str.length()) {
            int i3 = i2 + i;
            arrayList.add(str.substring(i2, Math.min(i3, str.length())));
            i2 = i3;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
