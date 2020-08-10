package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.core.util.Pools.Pool;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemoryCache.ResourceRemovedListener;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.FactoryPools.Factory;
import java.util.Map;
import java.util.concurrent.Executor;

public class Engine implements EngineJobListener, ResourceRemovedListener, ResourceListener {
    private static final int JOB_POOL_SIZE = 150;
    private static final String TAG = "Engine";
    private static final boolean VERBOSE_IS_LOGGABLE = Log.isLoggable(TAG, 2);
    private final ActiveResources activeResources;
    private final MemoryCache cache;
    private final DecodeJobFactory decodeJobFactory;
    private final LazyDiskCacheProvider diskCacheProvider;
    private final EngineJobFactory engineJobFactory;
    private final Jobs jobs;
    private final EngineKeyFactory keyFactory;
    private final ResourceRecycler resourceRecycler;

    static class DecodeJobFactory {
        private int creationOrder;
        final DiskCacheProvider diskCacheProvider;
        final Pool<DecodeJob<?>> pool = FactoryPools.threadSafe(150, new Factory<DecodeJob<?>>() {
            public DecodeJob<?> create() {
                return new DecodeJob<>(DecodeJobFactory.this.diskCacheProvider, DecodeJobFactory.this.pool);
            }
        });

        DecodeJobFactory(DiskCacheProvider diskCacheProvider2) {
            this.diskCacheProvider = diskCacheProvider2;
        }

        /* access modifiers changed from: 0000 */
        public <R> DecodeJob<R> build(GlideContext glideContext, Object obj, EngineKey engineKey, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, boolean z3, Options options, Callback<R> callback) {
            GlideContext glideContext2 = glideContext;
            Object obj2 = obj;
            EngineKey engineKey2 = engineKey;
            Key key2 = key;
            int i3 = i;
            int i4 = i2;
            Class<?> cls3 = cls;
            Class<R> cls4 = cls2;
            Priority priority2 = priority;
            DiskCacheStrategy diskCacheStrategy2 = diskCacheStrategy;
            Map<Class<?>, Transformation<?>> map2 = map;
            boolean z4 = z;
            boolean z5 = z2;
            boolean z6 = z3;
            Options options2 = options;
            Callback<R> callback2 = callback;
            DecodeJob decodeJob = (DecodeJob) Preconditions.checkNotNull((DecodeJob) this.pool.acquire());
            int i5 = this.creationOrder;
            int i6 = i5;
            this.creationOrder = i5 + 1;
            return decodeJob.init(glideContext2, obj2, engineKey2, key2, i3, i4, cls3, cls4, priority2, diskCacheStrategy2, map2, z4, z5, z6, options2, callback2, i6);
        }
    }

    static class EngineJobFactory {
        final GlideExecutor animationExecutor;
        final GlideExecutor diskCacheExecutor;
        final EngineJobListener engineJobListener;
        final Pool<EngineJob<?>> pool = FactoryPools.threadSafe(150, new Factory<EngineJob<?>>() {
            public EngineJob<?> create() {
                EngineJob engineJob = new EngineJob(EngineJobFactory.this.diskCacheExecutor, EngineJobFactory.this.sourceExecutor, EngineJobFactory.this.sourceUnlimitedExecutor, EngineJobFactory.this.animationExecutor, EngineJobFactory.this.engineJobListener, EngineJobFactory.this.resourceListener, EngineJobFactory.this.pool);
                return engineJob;
            }
        });
        final ResourceListener resourceListener;
        final GlideExecutor sourceExecutor;
        final GlideExecutor sourceUnlimitedExecutor;

        EngineJobFactory(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, EngineJobListener engineJobListener2, ResourceListener resourceListener2) {
            this.diskCacheExecutor = glideExecutor;
            this.sourceExecutor = glideExecutor2;
            this.sourceUnlimitedExecutor = glideExecutor3;
            this.animationExecutor = glideExecutor4;
            this.engineJobListener = engineJobListener2;
            this.resourceListener = resourceListener2;
        }

        /* access modifiers changed from: 0000 */
        public void shutdown() {
            Executors.shutdownAndAwaitTermination(this.diskCacheExecutor);
            Executors.shutdownAndAwaitTermination(this.sourceExecutor);
            Executors.shutdownAndAwaitTermination(this.sourceUnlimitedExecutor);
            Executors.shutdownAndAwaitTermination(this.animationExecutor);
        }

        /* access modifiers changed from: 0000 */
        public <R> EngineJob<R> build(Key key, boolean z, boolean z2, boolean z3, boolean z4) {
            return ((EngineJob) Preconditions.checkNotNull((EngineJob) this.pool.acquire())).init(key, z, z2, z3, z4);
        }
    }

    private static class LazyDiskCacheProvider implements DiskCacheProvider {
        private volatile DiskCache diskCache;
        private final DiskCache.Factory factory;

        LazyDiskCacheProvider(DiskCache.Factory factory2) {
            this.factory = factory2;
        }

        /* access modifiers changed from: 0000 */
        public synchronized void clearDiskCacheIfCreated() {
            if (this.diskCache != null) {
                this.diskCache.clear();
            }
        }

        public DiskCache getDiskCache() {
            if (this.diskCache == null) {
                synchronized (this) {
                    if (this.diskCache == null) {
                        this.diskCache = this.factory.build();
                    }
                    if (this.diskCache == null) {
                        this.diskCache = new DiskCacheAdapter();
                    }
                }
            }
            return this.diskCache;
        }
    }

    public class LoadStatus {

        /* renamed from: cb */
        private final ResourceCallback f248cb;
        private final EngineJob<?> engineJob;

        LoadStatus(ResourceCallback resourceCallback, EngineJob<?> engineJob2) {
            this.f248cb = resourceCallback;
            this.engineJob = engineJob2;
        }

        public void cancel() {
            synchronized (Engine.this) {
                this.engineJob.removeCallback(this.f248cb);
            }
        }
    }

    public Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, boolean z) {
        this(memoryCache, factory, glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, null, null, null, null, null, null, z);
    }

    Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, Jobs jobs2, EngineKeyFactory engineKeyFactory, ActiveResources activeResources2, EngineJobFactory engineJobFactory2, DecodeJobFactory decodeJobFactory2, ResourceRecycler resourceRecycler2, boolean z) {
        this.cache = memoryCache;
        DiskCache.Factory factory2 = factory;
        this.diskCacheProvider = new LazyDiskCacheProvider(factory);
        ActiveResources activeResources3 = activeResources2 == null ? new ActiveResources(z) : activeResources2;
        this.activeResources = activeResources3;
        activeResources3.setListener(this);
        this.keyFactory = engineKeyFactory == null ? new EngineKeyFactory() : engineKeyFactory;
        this.jobs = jobs2 == null ? new Jobs() : jobs2;
        this.engineJobFactory = engineJobFactory2 == null ? new EngineJobFactory(glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, this, this) : engineJobFactory2;
        this.decodeJobFactory = decodeJobFactory2 == null ? new DecodeJobFactory(this.diskCacheProvider) : decodeJobFactory2;
        this.resourceRecycler = resourceRecycler2 == null ? new ResourceRecycler() : resourceRecycler2;
        memoryCache.setResourceRemovedListener(this);
    }

    public <R> LoadStatus load(GlideContext glideContext, Object obj, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, Options options, boolean z3, boolean z4, boolean z5, boolean z6, ResourceCallback resourceCallback, Executor executor) {
        long logTime = VERBOSE_IS_LOGGABLE ? LogTime.getLogTime() : 0;
        EngineKey buildKey = this.keyFactory.buildKey(obj, key, i, i2, map, cls, cls2, options);
        synchronized (this) {
            EngineResource loadFromMemory = loadFromMemory(buildKey, z3, logTime);
            if (loadFromMemory == null) {
                LoadStatus waitForExistingOrStartNewJob = waitForExistingOrStartNewJob(glideContext, obj, key, i, i2, cls, cls2, priority, diskCacheStrategy, map, z, z2, options, z3, z4, z5, z6, resourceCallback, executor, buildKey, logTime);
                return waitForExistingOrStartNewJob;
            }
            resourceCallback.onResourceReady(loadFromMemory, DataSource.MEMORY_CACHE);
            return null;
        }
    }

    private <R> LoadStatus waitForExistingOrStartNewJob(GlideContext glideContext, Object obj, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, Options options, boolean z3, boolean z4, boolean z5, boolean z6, ResourceCallback resourceCallback, Executor executor, EngineKey engineKey, long j) {
        ResourceCallback resourceCallback2 = resourceCallback;
        Executor executor2 = executor;
        EngineKey engineKey2 = engineKey;
        long j2 = j;
        EngineJob engineJob = this.jobs.get(engineKey2, z6);
        if (engineJob != null) {
            engineJob.addCallback(resourceCallback2, executor2);
            if (VERBOSE_IS_LOGGABLE) {
                logWithTimeAndKey("Added to existing load", j2, engineKey2);
            }
            return new LoadStatus(resourceCallback2, engineJob);
        }
        EngineJob build = this.engineJobFactory.build(engineKey, z3, z4, z5, z6);
        EngineJob engineJob2 = build;
        EngineKey engineKey3 = engineKey2;
        DecodeJob build2 = this.decodeJobFactory.build(glideContext, obj, engineKey, key, i, i2, cls, cls2, priority, diskCacheStrategy, map, z, z2, z6, options, build);
        this.jobs.put(engineKey3, engineJob2);
        EngineJob engineJob3 = engineJob2;
        EngineKey engineKey4 = engineKey3;
        ResourceCallback resourceCallback3 = resourceCallback;
        engineJob3.addCallback(resourceCallback3, executor);
        engineJob3.start(build2);
        if (VERBOSE_IS_LOGGABLE) {
            logWithTimeAndKey("Started new load", j, engineKey4);
        }
        return new LoadStatus(resourceCallback3, engineJob3);
    }

    private EngineResource<?> loadFromMemory(EngineKey engineKey, boolean z, long j) {
        if (!z) {
            return null;
        }
        EngineResource<?> loadFromActiveResources = loadFromActiveResources(engineKey);
        if (loadFromActiveResources != null) {
            if (VERBOSE_IS_LOGGABLE) {
                logWithTimeAndKey("Loaded resource from active resources", j, engineKey);
            }
            return loadFromActiveResources;
        }
        EngineResource<?> loadFromCache = loadFromCache(engineKey);
        if (loadFromCache == null) {
            return null;
        }
        if (VERBOSE_IS_LOGGABLE) {
            logWithTimeAndKey("Loaded resource from cache", j, engineKey);
        }
        return loadFromCache;
    }

    private static void logWithTimeAndKey(String str, long j, Key key) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" in ");
        sb.append(LogTime.getElapsedMillis(j));
        sb.append("ms, key: ");
        sb.append(key);
        Log.v(TAG, sb.toString());
    }

    private EngineResource<?> loadFromActiveResources(Key key) {
        EngineResource<?> engineResource = this.activeResources.get(key);
        if (engineResource != null) {
            engineResource.acquire();
        }
        return engineResource;
    }

    private EngineResource<?> loadFromCache(Key key) {
        EngineResource<?> engineResourceFromCache = getEngineResourceFromCache(key);
        if (engineResourceFromCache != null) {
            engineResourceFromCache.acquire();
            this.activeResources.activate(key, engineResourceFromCache);
        }
        return engineResourceFromCache;
    }

    private EngineResource<?> getEngineResourceFromCache(Key key) {
        Resource remove = this.cache.remove(key);
        if (remove == null) {
            return null;
        }
        if (remove instanceof EngineResource) {
            return (EngineResource) remove;
        }
        EngineResource engineResource = new EngineResource(remove, true, true, key, this);
        return engineResource;
    }

    public void release(Resource<?> resource) {
        if (resource instanceof EngineResource) {
            ((EngineResource) resource).release();
            return;
        }
        throw new IllegalArgumentException("Cannot release anything but an EngineResource");
    }

    public synchronized void onEngineJobComplete(EngineJob<?> engineJob, Key key, EngineResource<?> engineResource) {
        if (engineResource != null) {
            if (engineResource.isMemoryCacheable()) {
                this.activeResources.activate(key, engineResource);
            }
        }
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public synchronized void onEngineJobCancelled(EngineJob<?> engineJob, Key key) {
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public void onResourceRemoved(Resource<?> resource) {
        this.resourceRecycler.recycle(resource, true);
    }

    public void onResourceReleased(Key key, EngineResource<?> engineResource) {
        this.activeResources.deactivate(key);
        if (engineResource.isMemoryCacheable()) {
            this.cache.put(key, engineResource);
        } else {
            this.resourceRecycler.recycle(engineResource, false);
        }
    }

    public void clearDiskCache() {
        this.diskCacheProvider.getDiskCache().clear();
    }

    public void shutdown() {
        this.engineJobFactory.shutdown();
        this.diskCacheProvider.clearDiskCacheIfCreated();
        this.activeResources.shutdown();
    }
}
