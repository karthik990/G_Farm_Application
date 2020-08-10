package com.google.api.client.util.store;

import java.io.IOException;
import org.objectweb.asm.signature.SignatureVisitor;

public final class DataStoreUtils {
    public static String toString(DataStore<?> dataStore) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            boolean z = true;
            for (String str : dataStore.keySet()) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                sb.append(str);
                sb.append(SignatureVisitor.INSTANCEOF);
                sb.append(dataStore.get(str));
            }
            sb.append('}');
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DataStoreUtils() {
    }
}