package com.ogaclejapan.smarttablayout.utils.v13;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import com.ogaclejapan.smarttablayout.utils.PagerItem;

public class FragmentPagerItem extends PagerItem {
    private static final String KEY_POSITION = "FragmentPagerItem:Position";
    private static final String TAG = "FragmentPagerItem";
    private final Bundle args;
    private final String className;

    protected FragmentPagerItem(CharSequence charSequence, float f, String str, Bundle bundle) {
        super(charSequence, f);
        this.className = str;
        this.args = bundle;
    }

    /* renamed from: of */
    public static FragmentPagerItem m2045of(CharSequence charSequence, Class<? extends Fragment> cls) {
        return m2043of(charSequence, 1.0f, cls);
    }

    /* renamed from: of */
    public static FragmentPagerItem m2046of(CharSequence charSequence, Class<? extends Fragment> cls, Bundle bundle) {
        return m2044of(charSequence, 1.0f, cls, bundle);
    }

    /* renamed from: of */
    public static FragmentPagerItem m2043of(CharSequence charSequence, float f, Class<? extends Fragment> cls) {
        return m2044of(charSequence, f, cls, new Bundle());
    }

    /* renamed from: of */
    public static FragmentPagerItem m2044of(CharSequence charSequence, float f, Class<? extends Fragment> cls, Bundle bundle) {
        return new FragmentPagerItem(charSequence, f, cls.getName(), bundle);
    }

    public static boolean hasPosition(Bundle bundle) {
        return bundle != null && bundle.containsKey(KEY_POSITION);
    }

    public static int getPosition(Bundle bundle) {
        if (hasPosition(bundle)) {
            return bundle.getInt(KEY_POSITION);
        }
        return 0;
    }

    static void setPosition(Bundle bundle, int i) {
        bundle.putInt(KEY_POSITION, i);
    }

    public Fragment instantiate(Context context, int i) {
        setPosition(this.args, i);
        return Fragment.instantiate(context, this.className, this.args);
    }
}
