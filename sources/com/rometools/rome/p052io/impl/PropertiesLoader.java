package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.impl.ConfigurableClassLoader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

/* renamed from: com.rometools.rome.io.impl.PropertiesLoader */
public class PropertiesLoader {
    private static final String EXTRA_PLUGIN_FILE = "rome.properties";
    private static final String MASTER_PLUGIN_FILE = "com/rometools/rome/rome.properties";
    private static Map<ClassLoader, PropertiesLoader> clMap = new WeakHashMap();
    private final Properties[] properties;

    public static PropertiesLoader getPropertiesLoader() {
        PropertiesLoader propertiesLoader;
        synchronized (PropertiesLoader.class) {
            ClassLoader classLoader = ConfigurableClassLoader.INSTANCE.getClassLoader();
            propertiesLoader = (PropertiesLoader) clMap.get(classLoader);
            if (propertiesLoader == null) {
                try {
                    propertiesLoader = new PropertiesLoader(MASTER_PLUGIN_FILE, EXTRA_PLUGIN_FILE);
                    clMap.put(classLoader, propertiesLoader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return propertiesLoader;
    }

    private PropertiesLoader(String str, String str2) throws IOException {
        String str3 = "], ";
        ArrayList arrayList = new ArrayList();
        ClassLoader classLoader = ConfigurableClassLoader.INSTANCE.getClassLoader();
        try {
            InputStream resourceAsStream = classLoader.getResourceAsStream(str);
            Properties properties2 = new Properties();
            properties2.load(resourceAsStream);
            resourceAsStream.close();
            arrayList.add(properties2);
            Enumeration resources = classLoader.getResources(str2);
            while (resources.hasMoreElements()) {
                URL url = (URL) resources.nextElement();
                Properties properties3 = new Properties();
                try {
                    InputStream openStream = url.openStream();
                    properties3.load(openStream);
                    openStream.close();
                    arrayList.add(properties3);
                } catch (IOException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("could not load ROME extensions plugins file [");
                    sb.append(url.toString());
                    sb.append(str3);
                    sb.append(e.getMessage());
                    IOException iOException = new IOException(sb.toString());
                    iOException.setStackTrace(e.getStackTrace());
                    throw iOException;
                }
            }
            this.properties = new Properties[arrayList.size()];
            arrayList.toArray(this.properties);
        } catch (IOException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("could not load ROME master plugins file [");
            sb2.append(str);
            sb2.append(str3);
            sb2.append(e2.getMessage());
            IOException iOException2 = new IOException(sb2.toString());
            iOException2.setStackTrace(e2.getStackTrace());
            throw iOException2;
        }
    }

    public String[] getTokenizedProperty(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        for (Properties property : this.properties) {
            String property2 = property.getProperty(str);
            if (property2 != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(property2, str2);
                while (stringTokenizer.hasMoreTokens()) {
                    arrayList.add(stringTokenizer.nextToken());
                }
            }
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }

    public String[] getProperty(String str) {
        ArrayList arrayList = new ArrayList();
        for (Properties property : this.properties) {
            String property2 = property.getProperty(str);
            if (property2 != null) {
                arrayList.add(property2);
            }
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }
}
