package com.firebase.p037ui.auth.util.data;

import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.firebase.ui.auth.util.data.AuthOperationManager */
public class AuthOperationManager {
    private static String firebaseAppName = "FUIScratchApp";
    private static AuthOperationManager mAuthManager;
    public FirebaseAuth mScratchAuth;

    private AuthOperationManager() {
    }

    public static synchronized AuthOperationManager getInstance() {
        AuthOperationManager authOperationManager;
        synchronized (AuthOperationManager.class) {
            if (mAuthManager == null) {
                mAuthManager = new AuthOperationManager();
            }
            authOperationManager = mAuthManager;
        }
        return authOperationManager;
    }

    private FirebaseApp getScratchApp(FirebaseApp firebaseApp) {
        try {
            return FirebaseApp.getInstance(firebaseAppName);
        } catch (IllegalStateException unused) {
            return FirebaseApp.initializeApp(firebaseApp.getApplicationContext(), firebaseApp.getOptions(), firebaseAppName);
        }
    }

    private FirebaseAuth getScratchAuth(FlowParameters flowParameters) {
        if (this.mScratchAuth == null) {
            this.mScratchAuth = FirebaseAuth.getInstance(getScratchApp(FirebaseApp.getInstance(flowParameters.appName)));
        }
        return this.mScratchAuth;
    }

    public Task<AuthResult> createOrLinkUserWithEmailAndPassword(FirebaseAuth firebaseAuth, FlowParameters flowParameters, String str, String str2) {
        if (!canUpgradeAnonymous(firebaseAuth, flowParameters)) {
            return firebaseAuth.createUserWithEmailAndPassword(str, str2);
        }
        return firebaseAuth.getCurrentUser().linkWithCredential(EmailAuthProvider.getCredential(str, str2));
    }

    public Task<AuthResult> signInAndLinkWithCredential(FirebaseAuth firebaseAuth, FlowParameters flowParameters, AuthCredential authCredential) {
        if (canUpgradeAnonymous(firebaseAuth, flowParameters)) {
            return firebaseAuth.getCurrentUser().linkWithCredential(authCredential);
        }
        return firebaseAuth.signInWithCredential(authCredential);
    }

    public boolean canUpgradeAnonymous(FirebaseAuth firebaseAuth, FlowParameters flowParameters) {
        return flowParameters.isAnonymousUpgradeEnabled() && firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isAnonymous();
    }

    public Task<AuthResult> validateCredential(AuthCredential authCredential, FlowParameters flowParameters) {
        return getScratchAuth(flowParameters).signInWithCredential(authCredential);
    }

    public Task<AuthResult> safeLink(AuthCredential authCredential, final AuthCredential authCredential2, FlowParameters flowParameters) {
        return getScratchAuth(flowParameters).signInWithCredential(authCredential).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
            public Task<AuthResult> then(Task<AuthResult> task) throws Exception {
                return task.isSuccessful() ? ((AuthResult) task.getResult()).getUser().linkWithCredential(authCredential2) : task;
            }
        });
    }
}
