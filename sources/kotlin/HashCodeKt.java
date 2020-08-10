package kotlin;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\f\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0000\n\u0000\u001a\u000f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\b¨\u0006\u0003"}, mo22062d2 = {"hashCode", "", "", "kotlin-stdlib"}, mo22063k = 2, mo22064mv = {1, 1, 15})
/* compiled from: HashCode.kt */
public final class HashCodeKt {
    private static final int hashCode(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }
}
