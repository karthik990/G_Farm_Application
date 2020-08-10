package com.google.firebase.firestore.local;

import com.google.firebase.firestore.model.ResourcePath;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface IndexManager {
    void addToCollectionParentIndex(ResourcePath resourcePath);

    List<ResourcePath> getCollectionParents(String str);
}
