package com.google.firebase.database.collection;

import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder.KeyTranslator;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
final /* synthetic */ class ImmutableSortedMap$Builder$$Lambda$1 implements KeyTranslator {
    private static final ImmutableSortedMap$Builder$$Lambda$1 instance = new ImmutableSortedMap$Builder$$Lambda$1();

    private ImmutableSortedMap$Builder$$Lambda$1() {
    }

    public static KeyTranslator lambdaFactory$() {
        return instance;
    }

    public Object translate(Object obj) {
        return Builder.lambda$static$0(obj);
    }
}
