package p043io.netty.util;

import com.google.common.base.Ascii;

/* renamed from: io.netty.util.ByteProcessor */
public interface ByteProcessor {
    public static final ByteProcessor FIND_CR = new IndexOfProcessor(Ascii.f1866CR);
    public static final ByteProcessor FIND_CRLF = new ByteProcessor() {
        public boolean process(byte b) {
            return (b == 13 || b == 10) ? false : true;
        }
    };
    public static final ByteProcessor FIND_LF = new IndexOfProcessor(10);
    public static final ByteProcessor FIND_LINEAR_WHITESPACE = new ByteProcessor() {
        public boolean process(byte b) {
            return (b == 32 || b == 9) ? false : true;
        }
    };
    public static final ByteProcessor FIND_NON_CR = new IndexNotOfProcessor(Ascii.f1866CR);
    public static final ByteProcessor FIND_NON_CRLF = new ByteProcessor() {
        public boolean process(byte b) {
            return b == 13 || b == 10;
        }
    };
    public static final ByteProcessor FIND_NON_LF = new IndexNotOfProcessor(10);
    public static final ByteProcessor FIND_NON_LINEAR_WHITESPACE = new ByteProcessor() {
        public boolean process(byte b) {
            return b == 32 || b == 9;
        }
    };
    public static final ByteProcessor FIND_NON_NUL = new IndexNotOfProcessor(0);
    public static final ByteProcessor FIND_NUL = new IndexOfProcessor(0);
    public static final ByteProcessor FIND_SEMI_COLON = new IndexOfProcessor(59);

    /* renamed from: io.netty.util.ByteProcessor$IndexNotOfProcessor */
    public static class IndexNotOfProcessor implements ByteProcessor {
        private final byte byteToNotFind;

        public IndexNotOfProcessor(byte b) {
            this.byteToNotFind = b;
        }

        public boolean process(byte b) {
            return b == this.byteToNotFind;
        }
    }

    /* renamed from: io.netty.util.ByteProcessor$IndexOfProcessor */
    public static class IndexOfProcessor implements ByteProcessor {
        private final byte byteToFind;

        public IndexOfProcessor(byte b) {
            this.byteToFind = b;
        }

        public boolean process(byte b) {
            return b != this.byteToFind;
        }
    }

    boolean process(byte b) throws Exception;
}
