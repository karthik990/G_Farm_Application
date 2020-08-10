package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.impl.ConfigurableClassLoader;
import com.rometools.rome.p052io.DelegatingModuleGenerator;
import com.rometools.rome.p052io.DelegatingModuleParser;
import com.rometools.rome.p052io.WireFeedGenerator;
import com.rometools.rome.p052io.WireFeedParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: com.rometools.rome.io.impl.PluginManager */
public abstract class PluginManager<T> {
    private final List<String> keys;
    private final WireFeedGenerator parentGenerator;
    private final WireFeedParser parentParser;
    private List<T> pluginsList;
    private Map<String, T> pluginsMap;
    private final String[] propertyValues;

    /* access modifiers changed from: protected */
    public abstract String getKey(T t);

    protected PluginManager(String str) {
        this(str, null, null);
    }

    protected PluginManager(String str, WireFeedParser wireFeedParser, WireFeedGenerator wireFeedGenerator) {
        this.parentParser = wireFeedParser;
        this.parentGenerator = wireFeedGenerator;
        this.propertyValues = PropertiesLoader.getPropertiesLoader().getTokenizedProperty(str, ", ");
        loadPlugins();
        this.pluginsMap = Collections.unmodifiableMap(this.pluginsMap);
        this.pluginsList = Collections.unmodifiableList(this.pluginsList);
        this.keys = Collections.unmodifiableList(new ArrayList(this.pluginsMap.keySet()));
    }

    /* access modifiers changed from: protected */
    public List<String> getKeys() {
        return this.keys;
    }

    /* access modifiers changed from: protected */
    public List<T> getPlugins() {
        return this.pluginsList;
    }

    /* access modifiers changed from: protected */
    public Map<String, T> getPluginMap() {
        return this.pluginsMap;
    }

    /* access modifiers changed from: protected */
    public T getPlugin(String str) {
        return this.pluginsMap.get(str);
    }

    private void loadPlugins() {
        Class[] classes;
        String str = "could not instantiate plugin ";
        ArrayList arrayList = new ArrayList();
        this.pluginsList = new ArrayList();
        this.pluginsMap = new HashMap();
        try {
            for (Class cls : getClasses()) {
                String name = cls.getName();
                Object newInstance = cls.newInstance();
                if (newInstance instanceof DelegatingModuleParser) {
                    ((DelegatingModuleParser) newInstance).setFeedParser(this.parentParser);
                }
                if (newInstance instanceof DelegatingModuleGenerator) {
                    ((DelegatingModuleGenerator) newInstance).setFeedGenerator(this.parentGenerator);
                }
                this.pluginsMap.put(getKey(newInstance), newInstance);
                this.pluginsList.add(newInstance);
            }
            for (Object add : this.pluginsMap.values()) {
                arrayList.add(add);
            }
            Iterator it = this.pluginsList.iterator();
            while (it.hasNext()) {
                if (!arrayList.contains(it.next())) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(null);
            throw new RuntimeException(sb.toString(), e);
        } catch (ExceptionInInitializerError e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(null);
            throw new RuntimeException(sb2.toString(), e2);
        }
    }

    private Class<T>[] getClasses() throws ClassNotFoundException {
        String[] strArr;
        Class cls;
        ClassLoader classLoader = ConfigurableClassLoader.INSTANCE.getClassLoader();
        ArrayList arrayList = new ArrayList();
        boolean booleanValue = Boolean.valueOf(System.getProperty("rome.pluginmanager.useloadclass", "false")).booleanValue();
        for (String str : this.propertyValues) {
            if (booleanValue) {
                cls = classLoader.loadClass(str);
            } else {
                cls = Class.forName(str, true, classLoader);
            }
            arrayList.add(cls);
        }
        Class<T>[] clsArr = new Class[arrayList.size()];
        arrayList.toArray(clsArr);
        return clsArr;
    }
}
