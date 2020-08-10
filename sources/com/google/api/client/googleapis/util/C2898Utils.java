package com.google.api.client.googleapis.util;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/* renamed from: com.google.api.client.googleapis.util.Utils */
public final class C2898Utils {

    /* renamed from: com.google.api.client.googleapis.util.Utils$JsonFactoryInstanceHolder */
    private static class JsonFactoryInstanceHolder {
        static final JsonFactory INSTANCE = new JacksonFactory();

        private JsonFactoryInstanceHolder() {
        }
    }

    /* renamed from: com.google.api.client.googleapis.util.Utils$TransportInstanceHolder */
    private static class TransportInstanceHolder {
        static final HttpTransport INSTANCE = new NetHttpTransport();

        private TransportInstanceHolder() {
        }
    }

    public static JsonFactory getDefaultJsonFactory() {
        return JsonFactoryInstanceHolder.INSTANCE;
    }

    public static HttpTransport getDefaultTransport() {
        return TransportInstanceHolder.INSTANCE;
    }

    private C2898Utils() {
    }
}
