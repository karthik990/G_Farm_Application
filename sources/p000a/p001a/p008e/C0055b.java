package p000a.p001a.p008e;

import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: a.a.e.b */
/* compiled from: StartAppSDK */
public final class C0055b implements Serializable {

    /* renamed from: a */
    public static final C0056a f16a = new C0056a(null);
    private Set<? extends Object> _options;
    private final Pattern nativePattern;

    /* renamed from: a.a.e.b$a */
    /* compiled from: StartAppSDK */
    public static final class C0056a {
        private C0056a() {
        }

        public /* synthetic */ C0056a(C0029e eVar) {
            this();
        }
    }

    /* renamed from: a.a.e.b$b */
    /* compiled from: StartAppSDK */
    private static final class C0057b implements Serializable {

        /* renamed from: a */
        public static final C0058a f17a = new C0058a(null);
        private static final long serialVersionUID = 0;
        private final int flags;
        private final String pattern;

        /* renamed from: a.a.e.b$b$a */
        /* compiled from: StartAppSDK */
        public static final class C0058a {
            private C0058a() {
            }

            public /* synthetic */ C0058a(C0029e eVar) {
                this();
            }
        }

        public C0057b(String str, int i) {
            C0032h.m44b(str, "pattern");
            this.pattern = str;
            this.flags = i;
        }

        private final Object readResolve() {
            Pattern compile = Pattern.compile(this.pattern, this.flags);
            C0032h.m41a((Object) compile, "Pattern.compile(pattern, flags)");
            return new C0055b(compile);
        }
    }

    public C0055b(Pattern pattern) {
        C0032h.m44b(pattern, "nativePattern");
        this.nativePattern = pattern;
    }

    public C0055b(String str) {
        C0032h.m44b(str, "pattern");
        Pattern compile = Pattern.compile(str);
        C0032h.m41a((Object) compile, "Pattern.compile(pattern)");
        this(compile);
    }

    /* renamed from: a */
    public final boolean mo69a(CharSequence charSequence) {
        C0032h.m44b(charSequence, Keys.INPUT);
        return this.nativePattern.matcher(charSequence).matches();
    }

    public String toString() {
        String pattern = this.nativePattern.toString();
        C0032h.m41a((Object) pattern, "nativePattern.toString()");
        return pattern;
    }

    private final Object writeReplace() {
        String pattern = this.nativePattern.pattern();
        C0032h.m41a((Object) pattern, "nativePattern.pattern()");
        return new C0057b(pattern, this.nativePattern.flags());
    }
}
