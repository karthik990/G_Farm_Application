package com.google.firebase.database.tubesock;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class MessageBuilderFactory {

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static class BinaryBuilder implements Builder {
        private int pendingByteCount = 0;
        private List<byte[]> pendingBytes = new ArrayList();

        BinaryBuilder() {
        }

        public boolean appendBytes(byte[] bArr) {
            this.pendingBytes.add(bArr);
            this.pendingByteCount += bArr.length;
            return true;
        }

        public WebSocketMessage toMessage() {
            byte[] bArr = new byte[this.pendingByteCount];
            int i = 0;
            for (int i2 = 0; i2 < this.pendingBytes.size(); i2++) {
                byte[] bArr2 = (byte[]) this.pendingBytes.get(i2);
                System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
                i += bArr2.length;
            }
            return new WebSocketMessage(bArr);
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    interface Builder {
        boolean appendBytes(byte[] bArr);

        WebSocketMessage toMessage();
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static class TextBuilder implements Builder {
        private static ThreadLocal<CharsetDecoder> localDecoder = new ThreadLocal<CharsetDecoder>() {
            /* access modifiers changed from: protected */
            public CharsetDecoder initialValue() {
                CharsetDecoder newDecoder = Charset.forName("UTF8").newDecoder();
                newDecoder.onMalformedInput(CodingErrorAction.REPORT);
                newDecoder.onUnmappableCharacter(CodingErrorAction.REPORT);
                return newDecoder;
            }
        };
        private static ThreadLocal<CharsetEncoder> localEncoder = new ThreadLocal<CharsetEncoder>() {
            /* access modifiers changed from: protected */
            public CharsetEncoder initialValue() {
                CharsetEncoder newEncoder = Charset.forName("UTF8").newEncoder();
                newEncoder.onMalformedInput(CodingErrorAction.REPORT);
                newEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);
                return newEncoder;
            }
        };
        private StringBuilder builder = new StringBuilder();
        private ByteBuffer carryOver;

        TextBuilder() {
        }

        public boolean appendBytes(byte[] bArr) {
            String decodeString = decodeString(bArr);
            if (decodeString == null) {
                return false;
            }
            this.builder.append(decodeString);
            return true;
        }

        public WebSocketMessage toMessage() {
            if (this.carryOver != null) {
                return null;
            }
            return new WebSocketMessage(this.builder.toString());
        }

        private String decodeString(byte[] bArr) {
            try {
                return ((CharsetDecoder) localDecoder.get()).decode(ByteBuffer.wrap(bArr)).toString();
            } catch (CharacterCodingException unused) {
                return null;
            }
        }

        private String decodeStringStreaming(byte[] bArr) {
            try {
                ByteBuffer buffer = getBuffer(bArr);
                int remaining = (int) (((float) buffer.remaining()) * ((CharsetDecoder) localDecoder.get()).averageCharsPerByte());
                CharBuffer allocate = CharBuffer.allocate(remaining);
                while (true) {
                    CoderResult decode = ((CharsetDecoder) localDecoder.get()).decode(buffer, allocate, false);
                    if (decode.isError()) {
                        return null;
                    }
                    if (decode.isUnderflow()) {
                        if (buffer.remaining() > 0) {
                            this.carryOver = buffer;
                        }
                        ((CharsetEncoder) localEncoder.get()).encode(CharBuffer.wrap(allocate));
                        allocate.flip();
                        return allocate.toString();
                    } else if (decode.isOverflow()) {
                        remaining = (remaining * 2) + 1;
                        CharBuffer allocate2 = CharBuffer.allocate(remaining);
                        allocate.flip();
                        allocate2.put(allocate);
                        allocate = allocate2;
                    }
                }
            } catch (CharacterCodingException unused) {
                return null;
            }
        }

        private ByteBuffer getBuffer(byte[] bArr) {
            ByteBuffer byteBuffer = this.carryOver;
            if (byteBuffer == null) {
                return ByteBuffer.wrap(bArr);
            }
            ByteBuffer allocate = ByteBuffer.allocate(bArr.length + byteBuffer.remaining());
            allocate.put(this.carryOver);
            this.carryOver = null;
            allocate.put(bArr);
            allocate.flip();
            return allocate;
        }
    }

    MessageBuilderFactory() {
    }

    static Builder builder(byte b) {
        if (b == 2) {
            return new BinaryBuilder();
        }
        return new TextBuilder();
    }
}
