package p043io.netty.util.internal;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: io.netty.util.internal.UnpaddedInternalThreadLocalMap */
class UnpaddedInternalThreadLocalMap {
    static final AtomicInteger nextIndex = new AtomicInteger();
    static final ThreadLocal<InternalThreadLocalMap> slowThreadLocalMap = new ThreadLocal<>();
    ArrayList<Object> arrayList;
    Map<Charset, CharsetDecoder> charsetDecoderCache;
    Map<Charset, CharsetEncoder> charsetEncoderCache;
    IntegerHolder counterHashCode;
    int futureListenerStackDepth;
    Map<Class<?>, Boolean> handlerSharableCache;
    Object[] indexedVariables;
    int localChannelReaderStackDepth;
    ThreadLocalRandom random;
    StringBuilder stringBuilder;
    Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache;
    Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache;

    UnpaddedInternalThreadLocalMap(Object[] objArr) {
        this.indexedVariables = objArr;
    }
}
