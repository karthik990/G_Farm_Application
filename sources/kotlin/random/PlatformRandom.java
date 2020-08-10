package kotlin.random;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo22062d2 = {"Lkotlin/random/PlatformRandom;", "Lkotlin/random/AbstractPlatformRandom;", "impl", "Ljava/util/Random;", "(Ljava/util/Random;)V", "getImpl", "()Ljava/util/Random;", "kotlin-stdlib"}, mo22063k = 1, mo22064mv = {1, 1, 15})
/* compiled from: PlatformRandom.kt */
final class PlatformRandom extends AbstractPlatformRandom {
    private final Random impl;

    public PlatformRandom(Random random) {
        Intrinsics.checkParameterIsNotNull(random, "impl");
        this.impl = random;
    }

    public Random getImpl() {
        return this.impl;
    }
}
