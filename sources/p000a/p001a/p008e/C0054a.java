package p000a.p001a.p008e;

import java.nio.charset.Charset;
import org.apache.commons.codec.CharEncoding;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: a.a.e.a */
/* compiled from: StartAppSDK */
public final class C0054a {

    /* renamed from: a */
    public static final Charset f9a;

    /* renamed from: b */
    public static final Charset f10b;

    /* renamed from: c */
    public static final Charset f11c;

    /* renamed from: d */
    public static final Charset f12d;

    /* renamed from: e */
    public static final Charset f13e;

    /* renamed from: f */
    public static final Charset f14f;

    /* renamed from: g */
    public static final C0054a f15g = new C0054a();

    static {
        Charset forName = Charset.forName("UTF-8");
        C0032h.m41a((Object) forName, "Charset.forName(\"UTF-8\")");
        f9a = forName;
        Charset forName2 = Charset.forName("UTF-16");
        C0032h.m41a((Object) forName2, "Charset.forName(\"UTF-16\")");
        f10b = forName2;
        Charset forName3 = Charset.forName(CharEncoding.UTF_16BE);
        C0032h.m41a((Object) forName3, "Charset.forName(\"UTF-16BE\")");
        f11c = forName3;
        Charset forName4 = Charset.forName("UTF-16LE");
        C0032h.m41a((Object) forName4, "Charset.forName(\"UTF-16LE\")");
        f12d = forName4;
        Charset forName5 = Charset.forName("US-ASCII");
        C0032h.m41a((Object) forName5, "Charset.forName(\"US-ASCII\")");
        f13e = forName5;
        Charset forName6 = Charset.forName("ISO-8859-1");
        C0032h.m41a((Object) forName6, "Charset.forName(\"ISO-8859-1\")");
        f14f = forName6;
    }

    private C0054a() {
    }
}
