package com.firebase.p037ui.auth.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.firebase.ui.auth.viewmodel.ViewModelBase */
public abstract class ViewModelBase<T> extends AndroidViewModel {
    private T mArguments;
    private final AtomicBoolean mIsInitialized = new AtomicBoolean();

    /* access modifiers changed from: protected */
    public void onCreate() {
    }

    protected ViewModelBase(Application application) {
        super(application);
    }

    public void init(T t) {
        if (this.mIsInitialized.compareAndSet(false, true)) {
            this.mArguments = t;
            onCreate();
        }
    }

    /* access modifiers changed from: protected */
    public T getArguments() {
        return this.mArguments;
    }

    /* access modifiers changed from: protected */
    public void setArguments(T t) {
        this.mArguments = t;
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        this.mIsInitialized.set(false);
    }
}
