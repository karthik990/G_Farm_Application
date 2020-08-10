package com.rometools.utils;

import java.io.Closeable;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* renamed from: com.rometools.utils.IO */
public final class C4621IO {
    private static final Logger LOG = LoggerFactory.getLogger(C4621IO.class);

    private C4621IO() {
    }

    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOG.warn("Unable to close resource", (Throwable) e);
            }
        }
    }
}
