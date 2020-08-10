package p043io.netty.handler.codec;

/* renamed from: io.netty.handler.codec.UnsupportedMessageTypeException */
public class UnsupportedMessageTypeException extends CodecException {
    private static final long serialVersionUID = 2799598826487038726L;

    public UnsupportedMessageTypeException(Object obj, Class<?>... clsArr) {
        super(message(obj == null ? "null" : obj.getClass().getName(), clsArr));
    }

    public UnsupportedMessageTypeException() {
    }

    public UnsupportedMessageTypeException(String str, Throwable th) {
        super(str, th);
    }

    public UnsupportedMessageTypeException(String str) {
        super(str);
    }

    public UnsupportedMessageTypeException(Throwable th) {
        super(th);
    }

    private static String message(String str, Class<?>... clsArr) {
        StringBuilder sb = new StringBuilder(str);
        if (clsArr != null && clsArr.length > 0) {
            sb.append(" (expected: ");
            sb.append(clsArr[0].getName());
            for (int i = 1; i < clsArr.length; i++) {
                Class<?> cls = clsArr[i];
                if (cls == null) {
                    break;
                }
                sb.append(", ");
                sb.append(cls.getName());
            }
            sb.append(')');
        }
        return sb.toString();
    }
}
