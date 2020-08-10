package com.google.firebase.database.connection;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface ListenHashProvider {
    CompoundHash getCompoundHash();

    String getSimpleHash();

    boolean shouldIncludeCompoundHash();
}
