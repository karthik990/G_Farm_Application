package com.firebase.p037ui.auth.viewmodel;

import android.app.Application;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.util.GoogleApiUtils;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

/* renamed from: com.firebase.ui.auth.viewmodel.AuthViewModelBase */
public abstract class AuthViewModelBase<T> extends OperableViewModel<FlowParameters, Resource<T>> {
    private FirebaseAuth mAuth;
    private CredentialsClient mCredentialsClient;
    private PhoneAuthProvider mPhoneAuth;

    protected AuthViewModelBase(Application application) {
        super(application);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        this.mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance(((FlowParameters) getArguments()).appName));
        this.mPhoneAuth = PhoneAuthProvider.getInstance(this.mAuth);
        this.mCredentialsClient = GoogleApiUtils.getCredentialsClient(getApplication());
    }

    public FirebaseUser getCurrentUser() {
        return this.mAuth.getCurrentUser();
    }

    /* access modifiers changed from: protected */
    public FirebaseAuth getAuth() {
        return this.mAuth;
    }

    /* access modifiers changed from: protected */
    public PhoneAuthProvider getPhoneAuth() {
        return this.mPhoneAuth;
    }

    /* access modifiers changed from: protected */
    public CredentialsClient getCredentialsClient() {
        return this.mCredentialsClient;
    }

    public void initializeForTesting(FlowParameters flowParameters, FirebaseAuth firebaseAuth, CredentialsClient credentialsClient, PhoneAuthProvider phoneAuthProvider) {
        setArguments(flowParameters);
        this.mAuth = firebaseAuth;
        this.mCredentialsClient = credentialsClient;
        this.mPhoneAuth = phoneAuthProvider;
    }
}
