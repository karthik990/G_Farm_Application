package com.firebase.p037ui.auth.data.model;

/* renamed from: com.firebase.ui.auth.data.model.Resource */
public final class Resource<T> {
    private final Exception mException;
    private boolean mIsUsed;
    private final State mState;
    private final T mValue;

    private Resource(State state, T t, Exception exc) {
        this.mState = state;
        this.mValue = t;
        this.mException = exc;
    }

    public static <T> Resource<T> forSuccess(T t) {
        return new Resource<>(State.SUCCESS, t, null);
    }

    public static <T> Resource<T> forFailure(Exception exc) {
        return new Resource<>(State.FAILURE, null, exc);
    }

    public static <T> Resource<T> forLoading() {
        return new Resource<>(State.LOADING, null, null);
    }

    public State getState() {
        return this.mState;
    }

    public final Exception getException() {
        this.mIsUsed = true;
        return this.mException;
    }

    public T getValue() {
        this.mIsUsed = true;
        return this.mValue;
    }

    public boolean isUsed() {
        return this.mIsUsed;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Resource resource = (Resource) obj;
        if (this.mState == resource.mState) {
            T t = this.mValue;
            if (t != null ? t.equals(resource.mValue) : resource.mValue == null) {
                Exception exc = this.mException;
                Exception exc2 = resource.mException;
                if (exc != null) {
                }
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        int hashCode = this.mState.hashCode() * 31;
        T t = this.mValue;
        int i = 0;
        int hashCode2 = (hashCode + (t == null ? 0 : t.hashCode())) * 31;
        Exception exc = this.mException;
        if (exc != null) {
            i = exc.hashCode();
        }
        return hashCode2 + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resource{mState=");
        sb.append(this.mState);
        sb.append(", mValue=");
        sb.append(this.mValue);
        sb.append(", mException=");
        sb.append(this.mException);
        sb.append('}');
        return sb.toString();
    }
}
