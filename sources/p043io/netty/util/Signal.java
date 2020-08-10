package p043io.netty.util;

/* renamed from: io.netty.util.Signal */
public final class Signal extends Error implements Constant<Signal> {
    private static final ConstantPool<Signal> pool = new ConstantPool<Signal>() {
        /* access modifiers changed from: protected */
        public Signal newConstant(int i, String str) {
            return new Signal(i, str);
        }
    };
    private static final long serialVersionUID = -221145131122459977L;
    private final SignalConstant constant;

    /* renamed from: io.netty.util.Signal$SignalConstant */
    private static final class SignalConstant extends AbstractConstant<SignalConstant> {
        SignalConstant(int i, String str) {
            super(i, str);
        }
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable initCause(Throwable th) {
        return this;
    }

    public static Signal valueOf(String str) {
        return (Signal) pool.valueOf(str);
    }

    public static Signal valueOf(Class<?> cls, String str) {
        return (Signal) pool.valueOf(cls, str);
    }

    private Signal(int i, String str) {
        this.constant = new SignalConstant(i, str);
    }

    public void expect(Signal signal) {
        if (this != signal) {
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected signal: ");
            sb.append(signal);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* renamed from: id */
    public int mo67945id() {
        return this.constant.mo67945id();
    }

    public String name() {
        return this.constant.name();
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public int compareTo(Signal signal) {
        if (this == signal) {
            return 0;
        }
        return this.constant.compareTo(signal.constant);
    }

    public String toString() {
        return name();
    }
}
