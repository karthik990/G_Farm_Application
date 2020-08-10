package com.google.firebase.components;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-common@@17.0.0 */
public final class ComponentDiscovery<T> {
    private static final String COMPONENT_KEY_PREFIX = "com.google.firebase.components:";
    private static final String COMPONENT_SENTINEL_VALUE = "com.google.firebase.components.ComponentRegistrar";
    private static final String TAG = "ComponentDiscovery";
    private final T context;
    private final RegistrarNameRetriever<T> retriever;

    /* compiled from: com.google.firebase:firebase-common@@17.0.0 */
    private static class MetadataRegistrarNameRetriever implements RegistrarNameRetriever<Context> {
        private MetadataRegistrarNameRetriever() {
        }

        public List<String> retrieve(Context context) {
            Bundle metadata = getMetadata(context);
            if (metadata == null) {
                Log.w(ComponentDiscovery.TAG, "Could not retrieve metadata, returning empty list of registrars.");
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (String str : metadata.keySet()) {
                if (ComponentDiscovery.COMPONENT_SENTINEL_VALUE.equals(metadata.get(str)) && str.startsWith(ComponentDiscovery.COMPONENT_KEY_PREFIX)) {
                    arrayList.add(str.substring(31));
                }
            }
            return arrayList;
        }

        private static Bundle getMetadata(Context context) {
            String str = ComponentDiscovery.TAG;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    Log.w(str, "Context has no PackageManager.");
                    return null;
                }
                ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, ComponentDiscoveryService.class), 128);
                if (serviceInfo != null) {
                    return serviceInfo.metaData;
                }
                Log.w(str, "ComponentDiscoveryService has no service info.");
                return null;
            } catch (NameNotFoundException unused) {
                Log.w(str, "Application info not found.");
                return null;
            }
        }
    }

    /* compiled from: com.google.firebase:firebase-common@@17.0.0 */
    interface RegistrarNameRetriever<T> {
        List<String> retrieve(T t);
    }

    public static ComponentDiscovery<Context> forContext(Context context2) {
        return new ComponentDiscovery<>(context2, new MetadataRegistrarNameRetriever());
    }

    ComponentDiscovery(T t, RegistrarNameRetriever<T> registrarNameRetriever) {
        this.context = t;
        this.retriever = registrarNameRetriever;
    }

    public List<ComponentRegistrar> discover() {
        return instantiate(this.retriever.retrieve(this.context));
    }

    private static List<ComponentRegistrar> instantiate(List<String> list) {
        String str = "Could not instantiate %s";
        String str2 = "Could not instantiate %s.";
        String str3 = TAG;
        ArrayList arrayList = new ArrayList();
        for (String str4 : list) {
            try {
                Class cls = Class.forName(str4);
                if (!ComponentRegistrar.class.isAssignableFrom(cls)) {
                    Log.w(str3, String.format("Class %s is not an instance of %s", new Object[]{str4, COMPONENT_SENTINEL_VALUE}));
                } else {
                    arrayList.add((ComponentRegistrar) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                }
            } catch (ClassNotFoundException e) {
                Log.w(str3, String.format("Class %s is not an found.", new Object[]{str4}), e);
            } catch (IllegalAccessException e2) {
                Log.w(str3, String.format(str2, new Object[]{str4}), e2);
            } catch (InstantiationException e3) {
                Log.w(str3, String.format(str2, new Object[]{str4}), e3);
            } catch (NoSuchMethodException e4) {
                Log.w(str3, String.format(str, new Object[]{str4}), e4);
            } catch (InvocationTargetException e5) {
                Log.w(str3, String.format(str, new Object[]{str4}), e5);
            }
        }
        return arrayList;
    }
}
