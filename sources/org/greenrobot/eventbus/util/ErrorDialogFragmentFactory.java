package org.greenrobot.eventbus.util;

import android.app.Fragment;
import android.os.Bundle;

public abstract class ErrorDialogFragmentFactory<T> {
    protected final ErrorDialogConfig config;

    public static class Honeycomb extends ErrorDialogFragmentFactory<Fragment> {
        public Honeycomb(ErrorDialogConfig errorDialogConfig) {
            super(errorDialogConfig);
        }

        /* access modifiers changed from: protected */
        public Fragment createErrorFragment(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
            org.greenrobot.eventbus.util.ErrorDialogFragments.Honeycomb honeycomb = new org.greenrobot.eventbus.util.ErrorDialogFragments.Honeycomb();
            honeycomb.setArguments(bundle);
            return honeycomb;
        }
    }

    public static class Support extends ErrorDialogFragmentFactory<androidx.fragment.app.Fragment> {
        public Support(ErrorDialogConfig errorDialogConfig) {
            super(errorDialogConfig);
        }

        /* access modifiers changed from: protected */
        public androidx.fragment.app.Fragment createErrorFragment(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
            org.greenrobot.eventbus.util.ErrorDialogFragments.Support support = new org.greenrobot.eventbus.util.ErrorDialogFragments.Support();
            support.setArguments(bundle);
            return support;
        }
    }

    /* access modifiers changed from: protected */
    public abstract T createErrorFragment(ThrowableFailureEvent throwableFailureEvent, Bundle bundle);

    protected ErrorDialogFragmentFactory(ErrorDialogConfig errorDialogConfig) {
        this.config = errorDialogConfig;
    }

    /* access modifiers changed from: protected */
    public T prepareErrorFragment(ThrowableFailureEvent throwableFailureEvent, boolean z, Bundle bundle) {
        Bundle bundle2;
        if (throwableFailureEvent.isSuppressErrorUi()) {
            return null;
        }
        if (bundle != null) {
            bundle2 = (Bundle) bundle.clone();
        } else {
            bundle2 = new Bundle();
        }
        String str = ErrorDialogManager.KEY_TITLE;
        if (!bundle2.containsKey(str)) {
            bundle2.putString(str, getTitleFor(throwableFailureEvent, bundle2));
        }
        String str2 = ErrorDialogManager.KEY_MESSAGE;
        if (!bundle2.containsKey(str2)) {
            bundle2.putString(str2, getMessageFor(throwableFailureEvent, bundle2));
        }
        String str3 = ErrorDialogManager.KEY_FINISH_AFTER_DIALOG;
        if (!bundle2.containsKey(str3)) {
            bundle2.putBoolean(str3, z);
        }
        String str4 = ErrorDialogManager.KEY_EVENT_TYPE_ON_CLOSE;
        if (!bundle2.containsKey(str4) && this.config.defaultEventTypeOnDialogClosed != null) {
            bundle2.putSerializable(str4, this.config.defaultEventTypeOnDialogClosed);
        }
        String str5 = ErrorDialogManager.KEY_ICON_ID;
        if (!bundle2.containsKey(str5) && this.config.defaultDialogIconId != 0) {
            bundle2.putInt(str5, this.config.defaultDialogIconId);
        }
        return createErrorFragment(throwableFailureEvent, bundle2);
    }

    /* access modifiers changed from: protected */
    public String getTitleFor(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
        return this.config.resources.getString(this.config.defaultTitleId);
    }

    /* access modifiers changed from: protected */
    public String getMessageFor(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
        return this.config.resources.getString(this.config.getMessageIdForThrowable(throwableFailureEvent.throwable));
    }
}
