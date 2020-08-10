package com.google.firebase.storage.network.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public interface HttpURLConnectionFactory {
    HttpURLConnection createInstance(URL url) throws IOException;
}
