package com.bumptech.glide.util.pool;

import android.util.Log;
import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SimplePool;
import androidx.core.util.Pools.SynchronizedPool;
import java.util.ArrayList;
import java.util.List;

public final class FactoryPools {
    private static final int DEFAULT_POOL_SIZE = 20;
    private static final Resetter<Object> EMPTY_RESETTER = new Resetter<Object>() {
        public void reset(Object obj) {
        }
    };
    private static final String TAG = "FactoryPools";

    public interface Factory<T> {
        T create();
    }

    private static final class FactoryPool<T> implements Pool<T> {
        private final Factory<T> factory;
        private final Pool<T> pool;
        private final Resetter<T> resetter;

        FactoryPool(Pool<T> pool2, Factory<T> factory2, Resetter<T> resetter2) {
            this.pool = pool2;
            this.factory = factory2;
            this.resetter = resetter2;
        }

        public T acquire() {
            T acquire = this.pool.acquire();
            if (acquire == null) {
                acquire = this.factory.create();
                String str = FactoryPools.TAG;
                if (Log.isLoggable(str, 2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Created new ");
                    sb.append(acquire.getClass());
                    Log.v(str, sb.toString());
                }
            }
            if (acquire instanceof Poolable) {
                ((Poolable) acquire).getVerifier().setRecycled(false);
            }
            return acquire;
        }

        public boolean release(T t) {
            if (t instanceof Poolable) {
                ((Poolable) t).getVerifier().setRecycled(true);
            }
            this.resetter.reset(t);
            return this.pool.release(t);
        }
    }

    public interface Poolable {
        StateVerifier getVerifier();
    }

    public interface Resetter<T> {
        void reset(T t);
    }

    private FactoryPools() {
    }

    public static <T extends Poolable> Pool<T> simple(int i, Factory<T> factory) {
        return build(new SimplePool(i), factory);
    }

    public static <T extends Poolable> Pool<T> threadSafe(int i, Factory<T> factory) {
        return build(new SynchronizedPool(i), factory);
    }

    public static <T> Pool<List<T>> threadSafeList() {
        return threadSafeList(20);
    }

    public static <T> Pool<List<T>> threadSafeList(int i) {
        return build(new SynchronizedPool(i), new Factory<List<T>>() {
            public List<T> create() {
                return new ArrayList();
            }
        }, new Resetter<List<T>>() {
            public void reset(List<T> list) {
                list.clear();
            }
        });
    }

    private static <T extends Poolable> Pool<T> build(Pool<T> pool, Factory<T> factory) {
        return build(pool, factory, emptyResetter());
    }

    private static <T> Pool<T> build(Pool<T> pool, Factory<T> factory, Resetter<T> resetter) {
        return new FactoryPool(pool, factory, resetter);
    }

    private static <T> Resetter<T> emptyResetter() {
        return EMPTY_RESETTER;
    }
}
