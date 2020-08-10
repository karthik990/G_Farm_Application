package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.synd.Converter;
import com.rometools.rome.p052io.impl.PluginManager;
import java.util.List;

public class Converters extends PluginManager<Converter> {
    public static final String CONVERTERS_KEY = "Converter.classes";

    public Converters() {
        super(CONVERTERS_KEY);
    }

    public Converter getConverter(String str) {
        return (Converter) getPlugin(str);
    }

    /* access modifiers changed from: protected */
    public String getKey(Converter converter) {
        return converter.getType();
    }

    public List<String> getSupportedFeedTypes() {
        return getKeys();
    }
}
