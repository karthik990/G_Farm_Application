package com.google.firebase.database.tubesock;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.objectweb.asm.Opcodes;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class WebSocketWriter {
    private WritableByteChannel channel;
    private boolean closeSent = false;
    private final Thread innerThread = WebSocket.getThreadFactory().newThread(new Runnable() {
        public void run() {
            WebSocketWriter.this.runWriter();
        }
    });
    private BlockingQueue<ByteBuffer> pendingBuffers;
    private final Random random = new Random();
    private volatile boolean stop = false;
    private WebSocket websocket;

    WebSocketWriter(WebSocket webSocket, String str, int i) {
        ThreadInitializer intializer = WebSocket.getIntializer();
        Thread innerThread2 = getInnerThread();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("Writer-");
        sb.append(i);
        intializer.setName(innerThread2, sb.toString());
        this.websocket = webSocket;
        this.pendingBuffers = new LinkedBlockingQueue();
    }

    /* access modifiers changed from: 0000 */
    public void setOutput(OutputStream outputStream) {
        this.channel = Channels.newChannel(outputStream);
    }

    private ByteBuffer frameInBuffer(byte b, boolean z, byte[] bArr) throws IOException {
        int i = z ? 6 : 2;
        int length = bArr.length;
        int i2 = Opcodes.IAND;
        if (length >= 126) {
            i = length <= 65535 ? i + 2 : i + 8;
        }
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + i);
        allocate.put((byte) (b | Byte.MIN_VALUE));
        if (length < 126) {
            if (z) {
                length |= 128;
            }
            allocate.put((byte) length);
        } else if (length <= 65535) {
            if (z) {
                i2 = 254;
            }
            allocate.put((byte) i2);
            allocate.putShort((short) length);
        } else {
            int i3 = Opcodes.LAND;
            if (z) {
                i3 = 255;
            }
            allocate.put((byte) i3);
            allocate.putInt(0);
            allocate.putInt(length);
        }
        if (z) {
            byte[] generateMask = generateMask();
            allocate.put(generateMask);
            for (int i4 = 0; i4 < bArr.length; i4++) {
                allocate.put((byte) (bArr[i4] ^ generateMask[i4 % 4]));
            }
        }
        allocate.flip();
        return allocate;
    }

    private byte[] generateMask() {
        byte[] bArr = new byte[4];
        this.random.nextBytes(bArr);
        return bArr;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void send(byte b, boolean z, byte[] bArr) throws IOException {
        ByteBuffer frameInBuffer = frameInBuffer(b, z, bArr);
        if (this.stop) {
            if (this.closeSent || b != 8) {
                throw new WebSocketException("Shouldn't be sending");
            }
        }
        if (b == 8) {
            this.closeSent = true;
        }
        this.pendingBuffers.add(frameInBuffer);
    }

    private void writeMessage() throws InterruptedException, IOException {
        this.channel.write((ByteBuffer) this.pendingBuffers.take());
    }

    /* access modifiers changed from: 0000 */
    public void stopIt() {
        this.stop = true;
    }

    private void handleError(WebSocketException webSocketException) {
        this.websocket.handleReceiverError(webSocketException);
    }

    /* access modifiers changed from: private */
    public void runWriter() {
        while (!this.stop && !Thread.interrupted()) {
            try {
                writeMessage();
            } catch (IOException e) {
                handleError(new WebSocketException("IO Exception", e));
                return;
            } catch (InterruptedException unused) {
                return;
            }
        }
        for (int i = 0; i < this.pendingBuffers.size(); i++) {
            writeMessage();
        }
    }

    /* access modifiers changed from: 0000 */
    public Thread getInnerThread() {
        return this.innerThread;
    }
}
