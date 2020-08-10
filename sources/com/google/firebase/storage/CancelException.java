package com.google.firebase.storage;

import java.io.IOException;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class CancelException extends IOException {
    CancelException() {
        super("The operation was canceled.");
    }
}
