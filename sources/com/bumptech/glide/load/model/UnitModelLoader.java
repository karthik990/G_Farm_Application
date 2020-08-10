package com.bumptech.glide.load.model;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataFetcher.DataCallback;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import com.bumptech.glide.signature.ObjectKey;

public class UnitModelLoader<Model> implements ModelLoader<Model, Model> {
    private static final UnitModelLoader<?> INSTANCE = new UnitModelLoader<>();

    public static class Factory<Model> implements ModelLoaderFactory<Model, Model> {
        private static final Factory<?> FACTORY = new Factory<>();

        public void teardown() {
        }

        public static <T> Factory<T> getInstance() {
            return FACTORY;
        }

        public ModelLoader<Model, Model> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return UnitModelLoader.getInstance();
        }
    }

    private static class UnitFetcher<Model> implements DataFetcher<Model> {
        private final Model resource;

        public void cancel() {
        }

        public void cleanup() {
        }

        UnitFetcher(Model model) {
            this.resource = model;
        }

        public void loadData(Priority priority, DataCallback<? super Model> dataCallback) {
            dataCallback.onDataReady(this.resource);
        }

        public Class<Model> getDataClass() {
            return this.resource.getClass();
        }

        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }
    }

    public boolean handles(Model model) {
        return true;
    }

    public static <T> UnitModelLoader<T> getInstance() {
        return INSTANCE;
    }

    public LoadData<Model> buildLoadData(Model model, int i, int i2, Options options) {
        return new LoadData<>(new ObjectKey(model), new UnitFetcher(model));
    }
}
