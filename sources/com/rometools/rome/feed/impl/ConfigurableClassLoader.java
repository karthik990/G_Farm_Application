package com.rometools.rome.feed.impl;

public enum ConfigurableClassLoader {
    INSTANCE;
    
    private ClassLoader classLoader;

    public ClassLoader getClassLoader() {
        if (this.classLoader == null) {
            this.classLoader = ConfigurableClassLoader.class.getClassLoader();
        }
        return this.classLoader;
    }

    public void setClassLoader(ClassLoader classLoader2) {
        this.classLoader = classLoader2;
    }
}
