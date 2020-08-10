package org.graylog2.gelfclient.encoder;

import com.fasterxml.jackson.core.JsonFactory;
import java.util.List;
import org.graylog2.gelfclient.GelfMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class GelfMessageJsonEncoder extends MessageToMessageEncoder<GelfMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(GelfMessageJsonEncoder.class);
    private final JsonFactory jsonFactory;

    public GelfMessageJsonEncoder() {
        this(new JsonFactory());
    }

    public GelfMessageJsonEncoder(JsonFactory jsonFactory2) {
        this.jsonFactory = jsonFactory2;
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        super.exceptionCaught(channelHandlerContext, th);
        LOG.error("JSON encoding error", th);
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, GelfMessage gelfMessage, List<Object> list) throws Exception {
        list.add(Unpooled.wrappedBuffer(toJson(gelfMessage)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00e7, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e8, code lost:
        if (r2 != null) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ee, code lost:
        throw r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] toJson(org.graylog2.gelfclient.GelfMessage r7) throws java.lang.Exception {
        /*
            r6 = this;
            java.lang.String r0 = "_"
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            com.fasterxml.jackson.core.JsonFactory r2 = r6.jsonFactory
            com.fasterxml.jackson.core.JsonEncoding r3 = com.fasterxml.jackson.core.JsonEncoding.UTF8
            com.fasterxml.jackson.core.JsonGenerator r2 = r2.createGenerator(r1, r3)
            r2.writeStartObject()     // Catch:{ all -> 0x00e5 }
            java.lang.String r3 = "version"
            org.graylog2.gelfclient.GelfMessageVersion r4 = r7.getVersion()     // Catch:{ all -> 0x00e5 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00e5 }
            r2.writeStringField(r3, r4)     // Catch:{ all -> 0x00e5 }
            java.lang.String r3 = "timestamp"
            double r4 = r7.getTimestamp()     // Catch:{ all -> 0x00e5 }
            r2.writeNumberField(r3, r4)     // Catch:{ all -> 0x00e5 }
            java.lang.String r3 = "host"
            java.lang.String r4 = r7.getHost()     // Catch:{ all -> 0x00e5 }
            r2.writeStringField(r3, r4)     // Catch:{ all -> 0x00e5 }
            java.lang.String r3 = "short_message"
            java.lang.String r4 = r7.getMessage()     // Catch:{ all -> 0x00e5 }
            r2.writeStringField(r3, r4)     // Catch:{ all -> 0x00e5 }
            org.graylog2.gelfclient.GelfMessageLevel r3 = r7.getLevel()     // Catch:{ all -> 0x00e5 }
            if (r3 == 0) goto L_0x004d
            java.lang.String r3 = "level"
            org.graylog2.gelfclient.GelfMessageLevel r4 = r7.getLevel()     // Catch:{ all -> 0x00e5 }
            int r4 = r4.getNumericLevel()     // Catch:{ all -> 0x00e5 }
            r2.writeNumberField(r3, r4)     // Catch:{ all -> 0x00e5 }
        L_0x004d:
            java.lang.String r3 = r7.getFullMessage()     // Catch:{ all -> 0x00e5 }
            if (r3 == 0) goto L_0x005c
            java.lang.String r3 = "full_message"
            java.lang.String r4 = r7.getFullMessage()     // Catch:{ all -> 0x00e5 }
            r2.writeStringField(r3, r4)     // Catch:{ all -> 0x00e5 }
        L_0x005c:
            java.util.Map r7 = r7.getAdditionalFields()     // Catch:{ all -> 0x00e5 }
            java.util.Set r7 = r7.entrySet()     // Catch:{ all -> 0x00e5 }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ all -> 0x00e5 }
        L_0x0068:
            boolean r3 = r7.hasNext()     // Catch:{ all -> 0x00e5 }
            if (r3 == 0) goto L_0x00d8
            java.lang.Object r3 = r7.next()     // Catch:{ all -> 0x00e5 }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x00e5 }
            java.lang.Object r4 = r3.getKey()     // Catch:{ all -> 0x00e5 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x00e5 }
            boolean r4 = r4.startsWith(r0)     // Catch:{ all -> 0x00e5 }
            if (r4 == 0) goto L_0x0087
            java.lang.Object r4 = r3.getKey()     // Catch:{ all -> 0x00e5 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x00e5 }
            goto L_0x009c
        L_0x0087:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e5 }
            r4.<init>()     // Catch:{ all -> 0x00e5 }
            r4.append(r0)     // Catch:{ all -> 0x00e5 }
            java.lang.Object r5 = r3.getKey()     // Catch:{ all -> 0x00e5 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x00e5 }
            r4.append(r5)     // Catch:{ all -> 0x00e5 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00e5 }
        L_0x009c:
            java.lang.Object r5 = r3.getValue()     // Catch:{ all -> 0x00e5 }
            boolean r5 = r5 instanceof java.lang.Number     // Catch:{ all -> 0x00e5 }
            if (r5 == 0) goto L_0x00ac
            java.lang.Object r3 = r3.getValue()     // Catch:{ all -> 0x00e5 }
            r2.writeObjectField(r4, r3)     // Catch:{ all -> 0x00e5 }
            goto L_0x0068
        L_0x00ac:
            java.lang.Object r5 = r3.getValue()     // Catch:{ all -> 0x00e5 }
            boolean r5 = r5 instanceof java.lang.Boolean     // Catch:{ all -> 0x00e5 }
            if (r5 == 0) goto L_0x00c2
            java.lang.Object r3 = r3.getValue()     // Catch:{ all -> 0x00e5 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00e5 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00e5 }
            r2.writeBooleanField(r4, r3)     // Catch:{ all -> 0x00e5 }
            goto L_0x0068
        L_0x00c2:
            java.lang.Object r5 = r3.getValue()     // Catch:{ all -> 0x00e5 }
            if (r5 != 0) goto L_0x00cc
            r2.writeNullField(r4)     // Catch:{ all -> 0x00e5 }
            goto L_0x0068
        L_0x00cc:
            java.lang.Object r3 = r3.getValue()     // Catch:{ all -> 0x00e5 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00e5 }
            r2.writeStringField(r4, r3)     // Catch:{ all -> 0x00e5 }
            goto L_0x0068
        L_0x00d8:
            r2.writeEndObject()     // Catch:{ all -> 0x00e5 }
            if (r2 == 0) goto L_0x00e0
            r2.close()
        L_0x00e0:
            byte[] r7 = r1.toByteArray()
            return r7
        L_0x00e5:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x00e7 }
        L_0x00e7:
            r7 = move-exception
            if (r2 == 0) goto L_0x00ed
            r2.close()     // Catch:{ all -> 0x00ed }
        L_0x00ed:
            goto L_0x00ef
        L_0x00ee:
            throw r7
        L_0x00ef:
            goto L_0x00ee
        */
        throw new UnsupportedOperationException("Method not decompiled: org.graylog2.gelfclient.encoder.GelfMessageJsonEncoder.toJson(org.graylog2.gelfclient.GelfMessage):byte[]");
    }
}
