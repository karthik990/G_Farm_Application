package com.firebase.p037ui.auth.p038ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.ui.FragmentBase */
public abstract class FragmentBase extends Fragment implements ProgressView {
    private HelperActivityBase mActivity;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof HelperActivityBase) {
            this.mActivity = (HelperActivityBase) activity;
            return;
        }
        throw new IllegalStateException("Cannot use this fragment without the helper activity");
    }

    public FlowParameters getFlowParams() {
        return this.mActivity.getFlowParams();
    }

    public void startSaveCredentials(FirebaseUser firebaseUser, IdpResponse idpResponse, String str) {
        this.mActivity.startSaveCredentials(firebaseUser, idpResponse, str);
    }
}
