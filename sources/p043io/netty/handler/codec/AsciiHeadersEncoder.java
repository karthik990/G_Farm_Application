package p043io.netty.handler.codec;

import java.util.Map.Entry;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.util.AsciiString;

/* renamed from: io.netty.handler.codec.AsciiHeadersEncoder */
public final class AsciiHeadersEncoder {
    private final ByteBuf buf;
    private final NewlineType newlineType;
    private final SeparatorType separatorType;

    /* renamed from: io.netty.handler.codec.AsciiHeadersEncoder$1 */
    static /* synthetic */ class C56631 {

        /* renamed from: $SwitchMap$io$netty$handler$codec$AsciiHeadersEncoder$NewlineType */
        static final /* synthetic */ int[] f3708x354ada9 = new int[NewlineType.values().length];

        /* renamed from: $SwitchMap$io$netty$handler$codec$AsciiHeadersEncoder$SeparatorType */
        static final /* synthetic */ int[] f3709xcf2033ba = new int[SeparatorType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|(2:5|6)|7|9|10|11|12|14) */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        static {
            /*
                io.netty.handler.codec.AsciiHeadersEncoder$NewlineType[] r0 = p043io.netty.handler.codec.AsciiHeadersEncoder.NewlineType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3708x354ada9 = r0
                r0 = 1
                int[] r1 = f3708x354ada9     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.AsciiHeadersEncoder$NewlineType r2 = p043io.netty.handler.codec.AsciiHeadersEncoder.NewlineType.LF     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f3708x354ada9     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.AsciiHeadersEncoder$NewlineType r3 = p043io.netty.handler.codec.AsciiHeadersEncoder.NewlineType.CRLF     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                io.netty.handler.codec.AsciiHeadersEncoder$SeparatorType[] r2 = p043io.netty.handler.codec.AsciiHeadersEncoder.SeparatorType.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f3709xcf2033ba = r2
                int[] r2 = f3709xcf2033ba     // Catch:{ NoSuchFieldError -> 0x0032 }
                io.netty.handler.codec.AsciiHeadersEncoder$SeparatorType r3 = p043io.netty.handler.codec.AsciiHeadersEncoder.SeparatorType.COLON     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = f3709xcf2033ba     // Catch:{ NoSuchFieldError -> 0x003c }
                io.netty.handler.codec.AsciiHeadersEncoder$SeparatorType r2 = p043io.netty.handler.codec.AsciiHeadersEncoder.SeparatorType.COLON_SPACE     // Catch:{ NoSuchFieldError -> 0x003c }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.AsciiHeadersEncoder.C56631.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.AsciiHeadersEncoder$NewlineType */
    public enum NewlineType {
        LF,
        CRLF
    }

    /* renamed from: io.netty.handler.codec.AsciiHeadersEncoder$SeparatorType */
    public enum SeparatorType {
        COLON,
        COLON_SPACE
    }

    private static int c2b(char c) {
        if (c < 256) {
            return (byte) c;
        }
        return 63;
    }

    public AsciiHeadersEncoder(ByteBuf byteBuf) {
        this(byteBuf, SeparatorType.COLON_SPACE, NewlineType.CRLF);
    }

    public AsciiHeadersEncoder(ByteBuf byteBuf, SeparatorType separatorType2, NewlineType newlineType2) {
        if (byteBuf == null) {
            throw new NullPointerException("buf");
        } else if (separatorType2 == null) {
            throw new NullPointerException("separatorType");
        } else if (newlineType2 != null) {
            this.buf = byteBuf;
            this.separatorType = separatorType2;
            this.newlineType = newlineType2;
        } else {
            throw new NullPointerException("newlineType");
        }
    }

    public void encode(Entry<CharSequence, CharSequence> entry) {
        int i;
        int i2;
        CharSequence charSequence = (CharSequence) entry.getKey();
        CharSequence charSequence2 = (CharSequence) entry.getValue();
        ByteBuf byteBuf = this.buf;
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i3 = length + length2 + 4;
        int writerIndex = byteBuf.writerIndex();
        byteBuf.ensureWritable(i3);
        writeAscii(byteBuf, writerIndex, charSequence, length);
        int i4 = writerIndex + length;
        int i5 = C56631.f3709xcf2033ba[this.separatorType.ordinal()];
        if (i5 == 1) {
            i = i4 + 1;
            byteBuf.setByte(i4, 58);
        } else if (i5 == 2) {
            int i6 = i4 + 1;
            byteBuf.setByte(i4, 58);
            int i7 = i6 + 1;
            byteBuf.setByte(i6, 32);
            i = i7;
        } else {
            throw new Error();
        }
        writeAscii(byteBuf, i, charSequence2, length2);
        int i8 = i + length2;
        int i9 = C56631.f3708x354ada9[this.newlineType.ordinal()];
        if (i9 == 1) {
            i2 = i8 + 1;
            byteBuf.setByte(i8, 10);
        } else if (i9 == 2) {
            int i10 = i8 + 1;
            byteBuf.setByte(i8, 13);
            int i11 = i10 + 1;
            byteBuf.setByte(i10, 10);
            i2 = i11;
        } else {
            throw new Error();
        }
        byteBuf.writerIndex(i2);
    }

    private static void writeAscii(ByteBuf byteBuf, int i, CharSequence charSequence, int i2) {
        if (charSequence instanceof AsciiString) {
            writeAsciiString(byteBuf, i, (AsciiString) charSequence, i2);
        } else {
            writeCharSequence(byteBuf, i, charSequence, i2);
        }
    }

    private static void writeAsciiString(ByteBuf byteBuf, int i, AsciiString asciiString, int i2) {
        ByteBufUtil.copy(asciiString, 0, byteBuf, i, i2);
    }

    private static void writeCharSequence(ByteBuf byteBuf, int i, CharSequence charSequence, int i2) {
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i + 1;
            byteBuf.setByte(i, c2b(charSequence.charAt(i3)));
            i3++;
            i = i4;
        }
    }
}
