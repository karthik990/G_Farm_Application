package com.firebase.p037ui.auth.p038ui.email;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest.Builder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* renamed from: com.firebase.ui.auth.ui.email.CheckEmailHandler */
public class CheckEmailHandler extends AuthViewModelBase<User> {
    public CheckEmailHandler(Application application) {
        super(application);
    }

    public void fetchCredential() {
        setResult(Resource.forFailure(new PendingIntentRequiredException(Credentials.getClient((Context) getApplication()).getHintPickerIntent(new Builder().setEmailAddressIdentifierSupported(true).build()), 101)));
    }

    public void fetchProvider(final String str) {
        setResult(Resource.forLoading());
        ProviderUtils.fetchTopProvider(getAuth(), (FlowParameters) getArguments(), str).addOnCompleteListener(new OnCompleteListener<String>() {
            public void onComplete(Task<String> task) {
                if (task.isSuccessful()) {
                    CheckEmailHandler.this.setResult(Resource.forSuccess(new User.Builder((String) task.getResult(), str).build()));
                } else {
                    CheckEmailHandler.this.setResult(Resource.forFailure(task.getException()));
                }
            }
        });
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 101 && i2 == -1) {
            setResult(Resource.forLoading());
            final Credential credential = (Credential) intent.getParcelableExtra(Credential.EXTRA_KEY);
            final String id = credential.getId();
            ProviderUtils.fetchTopProvider(getAuth(), (FlowParameters) getArguments(), id).addOnCompleteListener(new OnCompleteListener<String>() {
                public void onComplete(Task<String> task) {
                    if (task.isSuccessful()) {
                        CheckEmailHandler.this.setResult(Resource.forSuccess(new User.Builder((String) task.getResult(), id).setName(credential.getName()).setPhotoUri(credential.getProfilePictureUri()).build()));
                    } else {
                        CheckEmailHandler.this.setResult(Resource.forFailure(task.getException()));
                    }
                }
            });
        }
    }
}
