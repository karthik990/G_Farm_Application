package com.firebase.p037ui.auth.util.data;

import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;

/* renamed from: com.firebase.ui.auth.util.data.TaskFailureLogger */
public class TaskFailureLogger implements OnFailureListener {
    private String mMessage;
    private String mTag;

    public TaskFailureLogger(String str, String str2) {
        this.mTag = str;
        this.mMessage = str2;
    }

    public void onFailure(Exception exc) {
        Log.w(this.mTag, this.mMessage, exc);
    }
}
