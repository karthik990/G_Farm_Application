package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.lifecycle.ViewModelProvider.Factory;

public class ViewModelProviders {

    @Deprecated
    public static class DefaultFactory extends AndroidViewModelFactory {
        @Deprecated
        public DefaultFactory(Application application) {
            super(application);
        }
    }

    private static Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application != null) {
            return application;
        }
        throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call.");
    }

    private static Activity checkActivity(Fragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            return activity;
        }
        throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
    }

    /* renamed from: of */
    public static ViewModelProvider m76of(Fragment fragment) {
        return m77of(fragment, (Factory) null);
    }

    /* renamed from: of */
    public static ViewModelProvider m78of(FragmentActivity fragmentActivity) {
        return m79of(fragmentActivity, (Factory) null);
    }

    /* renamed from: of */
    public static ViewModelProvider m77of(Fragment fragment, Factory factory) {
        Application checkApplication = checkApplication(checkActivity(fragment));
        if (factory == null) {
            factory = AndroidViewModelFactory.getInstance(checkApplication);
        }
        return new ViewModelProvider(fragment.getViewModelStore(), factory);
    }

    /* renamed from: of */
    public static ViewModelProvider m79of(FragmentActivity fragmentActivity, Factory factory) {
        Application checkApplication = checkApplication(fragmentActivity);
        if (factory == null) {
            factory = AndroidViewModelFactory.getInstance(checkApplication);
        }
        return new ViewModelProvider(fragmentActivity.getViewModelStore(), factory);
    }
}
