package p043io.netty.util;

/* renamed from: io.netty.util.IllegalReferenceCountException */
public class IllegalReferenceCountException extends IllegalStateException {
    private static final long serialVersionUID = -2507492394288153468L;

    public IllegalReferenceCountException() {
    }

    public IllegalReferenceCountException(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("refCnt: ");
        sb.append(i);
        this(sb.toString());
    }

    public IllegalReferenceCountException(int i, int i2) {
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("refCnt: ");
        sb2.append(i);
        sb2.append(", ");
        if (i2 > 0) {
            sb = new StringBuilder();
            sb.append("increment: ");
        } else {
            sb = new StringBuilder();
            sb.append("decrement: ");
            i2 = -i2;
        }
        sb.append(i2);
        sb2.append(sb.toString());
        this(sb2.toString());
    }

    public IllegalReferenceCountException(String str) {
        super(str);
    }

    public IllegalReferenceCountException(String str, Throwable th) {
        super(str, th);
    }

    public IllegalReferenceCountException(Throwable th) {
        super(th);
    }
}
