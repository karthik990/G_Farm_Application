package com.google.firebase.database;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface ValueEventListener {
    void onCancelled(DatabaseError databaseError);

    void onDataChange(DataSnapshot dataSnapshot);
}
