package com.applyze;

import java.net.InetSocketAddress;
import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfMessageBuilder;
import org.graylog2.gelfclient.GelfMessageLevel;
import org.graylog2.gelfclient.GelfTransports;
import org.graylog2.gelfclient.transport.GelfTransport;

class AnalyticsLogger {
    private static final int VERSION = 8;

    AnalyticsLogger() {
    }

    /* renamed from: e */
    static void m88e(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error : ");
        sb.append(str);
        sendLog(sb.toString(), GelfMessageLevel.ERROR);
    }

    /* renamed from: w */
    static void m89w(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Warning : ");
        sb.append(str);
        sendLog(sb.toString(), GelfMessageLevel.WARNING);
    }

    private static void sendLog(String str, GelfMessageLevel gelfMessageLevel) {
        boolean z = true;
        GelfTransport create = GelfTransports.create(new GelfConfiguration(new InetSocketAddress("78.47.153.151", 12201)).transport(GelfTransports.UDP).queueSize(512).connectTimeout(5000).reconnectDelay(1000).tcpNoDelay(true).sendBufferSize(32768));
        GelfMessageBuilder additionalField = new GelfMessageBuilder("", "Android Analytics SDK").level(gelfMessageLevel).additionalField("_version", Integer.valueOf(8));
        if (!ServiceUtil.checkRootMethod1() && !ServiceUtil.checkRootMethod2() && !ServiceUtil.checkRootMethod3()) {
            z = false;
        }
        try {
            create.send(additionalField.additionalField("_isRooted", Boolean.valueOf(z)).message(str).build());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
