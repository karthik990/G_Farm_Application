package com.firebase.p037ui.auth.viewmodel;

import android.util.Log;
import androidx.lifecycle.Observer;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.State;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;
import com.firebase.p037ui.auth.p038ui.ProgressView;
import com.firebase.p037ui.auth.util.p039ui.FlowUtils;

/* renamed from: com.firebase.ui.auth.viewmodel.ResourceObserver */
public abstract class ResourceObserver<T> implements Observer<Resource<T>> {
    private final HelperActivityBase mActivity;
    private final FragmentBase mFragment;
    private final int mLoadingMessage;
    private final ProgressView mProgressView;

    /* access modifiers changed from: protected */
    public abstract void onFailure(Exception exc);

    /* access modifiers changed from: protected */
    public abstract void onSuccess(T t);

    protected ResourceObserver(HelperActivityBase helperActivityBase) {
        this(helperActivityBase, null, helperActivityBase, C1330R.C1335string.fui_progress_dialog_loading);
    }

    protected ResourceObserver(HelperActivityBase helperActivityBase, int i) {
        this(helperActivityBase, null, helperActivityBase, i);
    }

    protected ResourceObserver(FragmentBase fragmentBase) {
        this(null, fragmentBase, fragmentBase, C1330R.C1335string.fui_progress_dialog_loading);
    }

    protected ResourceObserver(FragmentBase fragmentBase, int i) {
        this(null, fragmentBase, fragmentBase, i);
    }

    private ResourceObserver(HelperActivityBase helperActivityBase, FragmentBase fragmentBase, ProgressView progressView, int i) {
        this.mActivity = helperActivityBase;
        this.mFragment = fragmentBase;
        if (this.mActivity == null && this.mFragment == null) {
            throw new IllegalStateException("ResourceObserver must be attached to an Activity or a Fragment");
        }
        this.mProgressView = progressView;
        this.mLoadingMessage = i;
    }

    public final void onChanged(Resource<T> resource) {
        boolean z;
        if (resource.getState() == State.LOADING) {
            this.mProgressView.showProgress(this.mLoadingMessage);
            return;
        }
        this.mProgressView.hideProgress();
        if (!resource.isUsed()) {
            if (resource.getState() == State.SUCCESS) {
                onSuccess(resource.getValue());
            } else if (resource.getState() == State.FAILURE) {
                Exception exception = resource.getException();
                FragmentBase fragmentBase = this.mFragment;
                if (fragmentBase == null) {
                    z = FlowUtils.unhandled(this.mActivity, exception);
                } else {
                    z = FlowUtils.unhandled(fragmentBase, exception);
                }
                if (z) {
                    Log.e(AuthUI.TAG, "A sign-in error occurred.", exception);
                    onFailure(exception);
                }
            }
        }
    }
}
