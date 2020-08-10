package p000a.p001a.p003b.p005b;

import java.util.Arrays;
import java.util.List;
import p000a.p001a.C0020b;

/* renamed from: a.a.b.b.h */
/* compiled from: StartAppSDK */
public class C0032h {
    private C0032h() {
    }

    /* renamed from: a */
    public static void m40a() {
        throw ((C0020b) m38a((T) new C0020b()));
    }

    /* renamed from: a */
    public static void m41a(Object obj, String str) {
        if (obj == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" must not be null");
            throw ((IllegalStateException) m38a((T) new IllegalStateException(sb.toString())));
        }
    }

    /* renamed from: b */
    public static void m44b(Object obj, String str) {
        if (obj == null) {
            m42a(str);
        }
    }

    /* renamed from: a */
    private static void m42a(String str) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter specified as non-null is null: method ");
        sb.append(className);
        sb.append(".");
        sb.append(methodName);
        sb.append(", parameter ");
        sb.append(str);
        throw ((IllegalArgumentException) m38a((T) new IllegalArgumentException(sb.toString())));
    }

    /* renamed from: a */
    public static boolean m43a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    /* renamed from: a */
    private static <T extends Throwable> T m38a(T t) {
        return m39a(t, C0032h.class.getName());
    }

    /* renamed from: a */
    static <T extends Throwable> T m39a(T t, String str) {
        StackTraceElement[] stackTrace = t.getStackTrace();
        int length = stackTrace.length;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.equals(stackTrace[i2].getClassName())) {
                i = i2;
            }
        }
        List subList = Arrays.asList(stackTrace).subList(i + 1, length);
        t.setStackTrace((StackTraceElement[]) subList.toArray(new StackTraceElement[subList.size()]));
        return t;
    }
}
