package p043io.netty.buffer;

import p043io.netty.util.ByteProcessor;

@Deprecated
/* renamed from: io.netty.buffer.ByteBufProcessor */
public interface ByteBufProcessor extends ByteProcessor {
    @Deprecated
    public static final ByteBufProcessor FIND_CR = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b != 13;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_CRLF = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return (b == 13 || b == 10) ? false : true;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_LF = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b != 10;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_LINEAR_WHITESPACE = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return (b == 32 || b == 9) ? false : true;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_CR = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b == 13;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_CRLF = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b == 13 || b == 10;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_LF = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b == 10;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_LINEAR_WHITESPACE = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b == 32 || b == 9;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NON_NUL = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b == 0;
        }
    };
    @Deprecated
    public static final ByteBufProcessor FIND_NUL = new ByteBufProcessor() {
        public boolean process(byte b) throws Exception {
            return b != 0;
        }
    };
}
