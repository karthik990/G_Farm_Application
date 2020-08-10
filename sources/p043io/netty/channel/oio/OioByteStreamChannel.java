package p043io.netty.channel.oio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.WritableByteChannel;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.Channel;
import p043io.netty.channel.FileRegion;
import p043io.netty.channel.RecvByteBufAllocator.Handle;

/* renamed from: io.netty.channel.oio.OioByteStreamChannel */
public abstract class OioByteStreamChannel extends AbstractOioByteChannel {
    private static final InputStream CLOSED_IN = new InputStream() {
        public int read() {
            return -1;
        }
    };
    private static final OutputStream CLOSED_OUT = new OutputStream() {
        public void write(int i) throws IOException {
            throw new ClosedChannelException();
        }
    };

    /* renamed from: is */
    private InputStream f3705is;

    /* renamed from: os */
    private OutputStream f3706os;
    private WritableByteChannel outChannel;

    protected OioByteStreamChannel(Channel channel) {
        super(channel);
    }

    /* access modifiers changed from: protected */
    public final void activate(InputStream inputStream, OutputStream outputStream) {
        if (this.f3705is != null) {
            throw new IllegalStateException("input was set already");
        } else if (this.f3706os != null) {
            throw new IllegalStateException("output was set already");
        } else if (inputStream == null) {
            throw new NullPointerException("is");
        } else if (outputStream != null) {
            this.f3705is = inputStream;
            this.f3706os = outputStream;
        } else {
            throw new NullPointerException("os");
        }
    }

    public boolean isActive() {
        InputStream inputStream = this.f3705is;
        if (inputStream == null || inputStream == CLOSED_IN) {
            return false;
        }
        OutputStream outputStream = this.f3706os;
        if (outputStream == null || outputStream == CLOSED_OUT) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public int available() {
        try {
            return this.f3705is.available();
        } catch (IOException unused) {
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public int doReadBytes(ByteBuf byteBuf) throws Exception {
        Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        recvBufAllocHandle.attemptedBytesRead(Math.max(1, Math.min(available(), byteBuf.maxWritableBytes())));
        return byteBuf.writeBytes(this.f3705is, recvBufAllocHandle.attemptedBytesRead());
    }

    /* access modifiers changed from: protected */
    public void doWriteBytes(ByteBuf byteBuf) throws Exception {
        OutputStream outputStream = this.f3706os;
        if (outputStream != null) {
            byteBuf.readBytes(outputStream, byteBuf.readableBytes());
            return;
        }
        throw new NotYetConnectedException();
    }

    /* access modifiers changed from: protected */
    public void doWriteFileRegion(FileRegion fileRegion) throws Exception {
        OutputStream outputStream = this.f3706os;
        if (outputStream != null) {
            if (this.outChannel == null) {
                this.outChannel = Channels.newChannel(outputStream);
            }
            long j = 0;
            do {
                long transferTo = fileRegion.transferTo(this.outChannel, j);
                if (transferTo == -1) {
                    checkEOF(fileRegion);
                    return;
                }
                j += transferTo;
            } while (j < fileRegion.count());
            return;
        }
        throw new NotYetConnectedException();
    }

    private static void checkEOF(FileRegion fileRegion) throws IOException {
        if (fileRegion.transferred() < fileRegion.count()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected to be able to write ");
            sb.append(fileRegion.count());
            sb.append(" bytes, but only wrote ");
            sb.append(fileRegion.transferred());
            throw new EOFException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        InputStream inputStream = this.f3705is;
        OutputStream outputStream = this.f3706os;
        this.f3705is = CLOSED_IN;
        this.f3706os = CLOSED_OUT;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Throwable th) {
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th;
            }
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }
}
