package androidx.lifecycle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

@Deprecated
public class ViewModelStores {
    private ViewModelStores() {
    }

    @Deprecated
    /* renamed from: of */
    public static ViewModelStore m81of(FragmentActivity fragmentActivity) {
        return fragmentActivity.getViewModelStore();
    }

    @Deprecated
    /* renamed from: of */
    public static ViewModelStore m80of(Fragment fragment) {
        return fragment.getViewModelStore();
    }
}
