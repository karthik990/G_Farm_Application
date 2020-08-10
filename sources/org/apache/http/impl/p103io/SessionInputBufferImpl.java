package org.apache.http.impl.p103io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import org.apache.http.MessageConstraintException;
import org.apache.http.config.MessageConstraints;
import org.apache.http.p104io.BufferInfo;
import org.apache.http.p104io.HttpTransportMetrics;
import org.apache.http.p104io.SessionInputBuffer;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

/* renamed from: org.apache.http.impl.io.SessionInputBufferImpl */
public class SessionInputBufferImpl implements SessionInputBuffer, BufferInfo {
    private final byte[] buffer;
    private int bufferLen;
    private int bufferPos;
    private CharBuffer cbuf;
    private final MessageConstraints constraints;
    private final CharsetDecoder decoder;
    private InputStream inStream;
    private final ByteArrayBuffer lineBuffer;
    private final HttpTransportMetricsImpl metrics;
    private final int minChunkLimit;

    public SessionInputBufferImpl(HttpTransportMetricsImpl httpTransportMetricsImpl, int i, int i2, MessageConstraints messageConstraints, CharsetDecoder charsetDecoder) {
        Args.notNull(httpTransportMetricsImpl, "HTTP transport metrcis");
        Args.positive(i, "Buffer size");
        this.metrics = httpTransportMetricsImpl;
        this.buffer = new byte[i];
        this.bufferPos = 0;
        this.bufferLen = 0;
        if (i2 < 0) {
            i2 = 512;
        }
        this.minChunkLimit = i2;
        if (messageConstraints == null) {
            messageConstraints = MessageConstraints.DEFAULT;
        }
        this.constraints = messageConstraints;
        this.lineBuffer = new ByteArrayBuffer(i);
        this.decoder = charsetDecoder;
    }

    public SessionInputBufferImpl(HttpTransportMetricsImpl httpTransportMetricsImpl, int i) {
        this(httpTransportMetricsImpl, i, i, null, null);
    }

    public void bind(InputStream inputStream) {
        this.inStream = inputStream;
    }

    public boolean isBound() {
        return this.inStream != null;
    }

    public int capacity() {
        return this.buffer.length;
    }

    public int length() {
        return this.bufferLen - this.bufferPos;
    }

    public int available() {
        return capacity() - length();
    }

    private int streamRead(byte[] bArr, int i, int i2) throws IOException {
        Asserts.notNull(this.inStream, "Input stream");
        return this.inStream.read(bArr, i, i2);
    }

    public int fillBuffer() throws IOException {
        int i = this.bufferPos;
        if (i > 0) {
            int i2 = this.bufferLen - i;
            if (i2 > 0) {
                byte[] bArr = this.buffer;
                System.arraycopy(bArr, i, bArr, 0, i2);
            }
            this.bufferPos = 0;
            this.bufferLen = i2;
        }
        int i3 = this.bufferLen;
        byte[] bArr2 = this.buffer;
        int streamRead = streamRead(bArr2, i3, bArr2.length - i3);
        if (streamRead == -1) {
            return -1;
        }
        this.bufferLen = i3 + streamRead;
        this.metrics.incrementBytesTransferred((long) streamRead);
        return streamRead;
    }

    public boolean hasBufferedData() {
        return this.bufferPos < this.bufferLen;
    }

    public void clear() {
        this.bufferPos = 0;
        this.bufferLen = 0;
    }

    public int read() throws IOException {
        while (!hasBufferedData()) {
            if (fillBuffer() == -1) {
                return -1;
            }
        }
        byte[] bArr = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            return 0;
        }
        if (hasBufferedData()) {
            int min = Math.min(i2, this.bufferLen - this.bufferPos);
            System.arraycopy(this.buffer, this.bufferPos, bArr, i, min);
            this.bufferPos += min;
            return min;
        } else if (i2 > this.minChunkLimit) {
            int streamRead = streamRead(bArr, i, i2);
            if (streamRead > 0) {
                this.metrics.incrementBytesTransferred((long) streamRead);
            }
            return streamRead;
        } else {
            while (!hasBufferedData()) {
                if (fillBuffer() == -1) {
                    return -1;
                }
            }
            int min2 = Math.min(i2, this.bufferLen - this.bufferPos);
            System.arraycopy(this.buffer, this.bufferPos, bArr, i, min2);
            this.bufferPos += min2;
            return min2;
        }
    }

    public int read(byte[] bArr) throws IOException {
        if (bArr == null) {
            return 0;
        }
        return read(bArr, 0, bArr.length);
    }

    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        int maxLineLength = this.constraints.getMaxLineLength();
        boolean z = true;
        int i = 0;
        while (z) {
            int i2 = this.bufferPos;
            while (true) {
                if (i2 >= this.bufferLen) {
                    i2 = -1;
                    break;
                } else if (this.buffer[i2] == 10) {
                    break;
                } else {
                    i2++;
                }
            }
            if (maxLineLength > 0) {
                if ((this.lineBuffer.length() + (i2 >= 0 ? i2 : this.bufferLen)) - this.bufferPos >= maxLineLength) {
                    throw new MessageConstraintException("Maximum line length limit exceeded");
                }
            }
            if (i2 == -1) {
                if (hasBufferedData()) {
                    int i3 = this.bufferLen;
                    int i4 = this.bufferPos;
                    this.lineBuffer.append(this.buffer, i4, i3 - i4);
                    this.bufferPos = this.bufferLen;
                }
                i = fillBuffer();
                if (i != -1) {
                }
            } else if (this.lineBuffer.isEmpty()) {
                return lineFromReadBuffer(charArrayBuffer, i2);
            } else {
                int i5 = i2 + 1;
                int i6 = this.bufferPos;
                this.lineBuffer.append(this.buffer, i6, i5 - i6);
                this.bufferPos = i5;
            }
            z = false;
        }
        if (i != -1 || !this.lineBuffer.isEmpty()) {
            return lineFromLineBuffer(charArrayBuffer);
        }
        return -1;
    }

    private int lineFromLineBuffer(CharArrayBuffer charArrayBuffer) throws IOException {
        int length = this.lineBuffer.length();
        if (length > 0) {
            if (this.lineBuffer.byteAt(length - 1) == 10) {
                length--;
            }
            if (length > 0 && this.lineBuffer.byteAt(length - 1) == 13) {
                length--;
            }
        }
        if (this.decoder == null) {
            charArrayBuffer.append(this.lineBuffer, 0, length);
        } else {
            length = appendDecoded(charArrayBuffer, ByteBuffer.wrap(this.lineBuffer.buffer(), 0, length));
        }
        this.lineBuffer.clear();
        return length;
    }

    private int lineFromReadBuffer(CharArrayBuffer charArrayBuffer, int i) throws IOException {
        int i2 = this.bufferPos;
        this.bufferPos = i + 1;
        if (i > i2 && this.buffer[i - 1] == 13) {
            i--;
        }
        int i3 = i - i2;
        if (this.decoder != null) {
            return appendDecoded(charArrayBuffer, ByteBuffer.wrap(this.buffer, i2, i3));
        }
        charArrayBuffer.append(this.buffer, i2, i3);
        return i3;
    }

    private int appendDecoded(CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) throws IOException {
        int i = 0;
        if (!byteBuffer.hasRemaining()) {
            return 0;
        }
        if (this.cbuf == null) {
            this.cbuf = CharBuffer.allocate(1024);
        }
        this.decoder.reset();
        while (byteBuffer.hasRemaining()) {
            i += handleDecodingResult(this.decoder.decode(byteBuffer, this.cbuf, true), charArrayBuffer, byteBuffer);
        }
        int handleDecodingResult = i + handleDecodingResult(this.decoder.flush(this.cbuf), charArrayBuffer, byteBuffer);
        this.cbuf.clear();
        return handleDecodingResult;
    }

    private int handleDecodingResult(CoderResult coderResult, CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) throws IOException {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.cbuf.flip();
        int remaining = this.cbuf.remaining();
        while (this.cbuf.hasRemaining()) {
            charArrayBuffer.append(this.cbuf.get());
        }
        this.cbuf.compact();
        return remaining;
    }

    public String readLine() throws IOException {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        if (readLine(charArrayBuffer) != -1) {
            return charArrayBuffer.toString();
        }
        return null;
    }

    public boolean isDataAvailable(int i) throws IOException {
        return hasBufferedData();
    }

    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }
}
