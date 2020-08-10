package com.firebase.p037ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* renamed from: com.firebase.ui.auth.viewmodel.email.RecoverPasswordHandler */
public class RecoverPasswordHandler extends AuthViewModelBase<String> {
    public RecoverPasswordHandler(Application application) {
        super(application);
    }

    public void startReset(final String str) {
        setResult(Resource.forLoading());
        getAuth().sendPasswordResetEmail(str).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                Resource resource;
                if (task.isSuccessful()) {
                    resource = Resource.forSuccess(str);
                } else {
                    resource = Resource.forFailure(task.getException());
                }
                RecoverPasswordHandler.this.setResult(resource);
            }
        });
    }
}
