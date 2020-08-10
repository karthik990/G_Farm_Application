package com.bumptech.glide.load.model;

import androidx.core.util.Pools.Pool;
import com.bumptech.glide.Registry.NoModelLoaderAvailableException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelLoaderRegistry {
    private final ModelLoaderCache cache;
    private final MultiModelLoaderFactory multiModelLoaderFactory;

    private static class ModelLoaderCache {
        private final Map<Class<?>, Entry<?>> cachedModelLoaders = new HashMap();

        private static class Entry<Model> {
            final List<ModelLoader<Model, ?>> loaders;

            public Entry(List<ModelLoader<Model, ?>> list) {
                this.loaders = list;
            }
        }

        ModelLoaderCache() {
        }

        public void clear() {
            this.cachedModelLoaders.clear();
        }

        public <Model> void put(Class<Model> cls, List<ModelLoader<Model, ?>> list) {
            if (((Entry) this.cachedModelLoaders.put(cls, new Entry(list))) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Already cached loaders for model: ");
                sb.append(cls);
                throw new IllegalStateException(sb.toString());
            }
        }

        public <Model> List<ModelLoader<Model, ?>> get(Class<Model> cls) {
            Entry entry = (Entry) this.cachedModelLoaders.get(cls);
            if (entry == null) {
                return null;
            }
            return entry.loaders;
        }
    }

    public ModelLoaderRegistry(Pool<List<Throwable>> pool) {
        this(new MultiModelLoaderFactory(pool));
    }

    private ModelLoaderRegistry(MultiModelLoaderFactory multiModelLoaderFactory2) {
        this.cache = new ModelLoaderCache();
        this.multiModelLoaderFactory = multiModelLoaderFactory2;
    }

    public synchronized <Model, Data> void append(Class<Model> cls, Class<Data> cls2, ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        this.multiModelLoaderFactory.append(cls, cls2, modelLoaderFactory);
        this.cache.clear();
    }

    public synchronized <Model, Data> void prepend(Class<Model> cls, Class<Data> cls2, ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        this.multiModelLoaderFactory.prepend(cls, cls2, modelLoaderFactory);
        this.cache.clear();
    }

    public synchronized <Model, Data> void remove(Class<Model> cls, Class<Data> cls2) {
        tearDown(this.multiModelLoaderFactory.remove(cls, cls2));
        this.cache.clear();
    }

    public synchronized <Model, Data> void replace(Class<Model> cls, Class<Data> cls2, ModelLoaderFactory<? extends Model, ? extends Data> modelLoaderFactory) {
        tearDown(this.multiModelLoaderFactory.replace(cls, cls2, modelLoaderFactory));
        this.cache.clear();
    }

    private <Model, Data> void tearDown(List<ModelLoaderFactory<? extends Model, ? extends Data>> list) {
        for (ModelLoaderFactory teardown : list) {
            teardown.teardown();
        }
    }

    public <A> List<ModelLoader<A, ?>> getModelLoaders(A a) {
        List modelLoadersForClass = getModelLoadersForClass(getClass(a));
        if (!modelLoadersForClass.isEmpty()) {
            int size = modelLoadersForClass.size();
            List emptyList = Collections.emptyList();
            boolean z = true;
            for (int i = 0; i < size; i++) {
                ModelLoader modelLoader = (ModelLoader) modelLoadersForClass.get(i);
                if (modelLoader.handles(a)) {
                    if (z) {
                        emptyList = new ArrayList(size - i);
                        z = false;
                    }
                    emptyList.add(modelLoader);
                }
            }
            if (!emptyList.isEmpty()) {
                return emptyList;
            }
            throw new NoModelLoaderAvailableException(a, modelLoadersForClass);
        }
        throw new NoModelLoaderAvailableException(a);
    }

    public synchronized <Model, Data> ModelLoader<Model, Data> build(Class<Model> cls, Class<Data> cls2) {
        return this.multiModelLoaderFactory.build(cls, cls2);
    }

    public synchronized List<Class<?>> getDataClasses(Class<?> cls) {
        return this.multiModelLoaderFactory.getDataClasses(cls);
    }

    private synchronized <A> List<ModelLoader<A, ?>> getModelLoadersForClass(Class<A> cls) {
        List<ModelLoader<A, ?>> list;
        list = this.cache.get(cls);
        if (list == null) {
            list = Collections.unmodifiableList(this.multiModelLoaderFactory.build(cls));
            this.cache.put(cls, list);
        }
        return list;
    }

    private static <A> Class<A> getClass(A a) {
        return a.getClass();
    }
}
