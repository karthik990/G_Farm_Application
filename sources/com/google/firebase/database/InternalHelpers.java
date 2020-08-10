package com.google.firebase.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoInfo;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class InternalHelpers {
    public static DatabaseReference createReference(Repo repo, Path path) {
        return new DatabaseReference(repo, path);
    }

    public static DataSnapshot createDataSnapshot(DatabaseReference databaseReference, IndexedNode indexedNode) {
        return new DataSnapshot(databaseReference, indexedNode);
    }

    public static FirebaseDatabase createDatabaseForTests(FirebaseApp firebaseApp, RepoInfo repoInfo, DatabaseConfig databaseConfig) {
        return FirebaseDatabase.createForTests(firebaseApp, repoInfo, databaseConfig);
    }

    public static MutableData createMutableData(Node node) {
        return new MutableData(node);
    }
}
