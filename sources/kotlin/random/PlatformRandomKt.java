package kotlin.random;

import com.google.cloud.datastore.core.number.NumberComparisonHelper;
import java.util.Random;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0000\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0007¨\u0006\n"}, mo22062d2 = {"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"}, mo22063k = 2, mo22064mv = {1, 1, 15})
/* compiled from: PlatformRandom.kt */
public final class PlatformRandomKt {
    public static final Random asJavaRandom(Random random) {
        Intrinsics.checkParameterIsNotNull(random, "$this$asJavaRandom");
        AbstractPlatformRandom abstractPlatformRandom = (AbstractPlatformRandom) (!(random instanceof AbstractPlatformRandom) ? null : random);
        if (abstractPlatformRandom != null) {
            Random impl = abstractPlatformRandom.getImpl();
            if (impl != null) {
                return impl;
            }
        }
        return new KotlinRandom(random);
    }

    public static final Random asKotlinRandom(Random random) {
        Intrinsics.checkParameterIsNotNull(random, "$this$asKotlinRandom");
        KotlinRandom kotlinRandom = (KotlinRandom) (!(random instanceof KotlinRandom) ? null : random);
        if (kotlinRandom != null) {
            Random impl = kotlinRandom.getImpl();
            if (impl != null) {
                return impl;
            }
        }
        return new PlatformRandom(random);
    }

    private static final Random defaultPlatformRandom() {
        return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
    }

    public static final double doubleFromParts(int i, int i2) {
        double d = (double) ((((long) i) << 27) + ((long) i2));
        double d2 = (double) NumberComparisonHelper.MAX_SAFE_LONG;
        Double.isNaN(d);
        Double.isNaN(d2);
        return d / d2;
    }
}
