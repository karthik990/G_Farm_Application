package com.firebase.p037ui.auth.p038ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.firebase.p037ui.auth.C1330R;

/* renamed from: com.firebase.ui.auth.ui.AppCompatBase */
public abstract class AppCompatBase extends HelperActivityBase {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(C1330R.C1336style.FirebaseUI);
        setTheme(getFlowParams().themeId);
    }

    /* access modifiers changed from: protected */
    public void switchFragment(Fragment fragment, int i, String str, boolean z, boolean z2) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(C1330R.anim.fui_slide_in_right, C1330R.anim.fui_slide_out_left);
        }
        beginTransaction.replace(i, fragment, str);
        if (z2) {
            beginTransaction.addToBackStack(null).commit();
        } else {
            beginTransaction.disallowAddToBackStack().commit();
        }
    }

    /* access modifiers changed from: protected */
    public void switchFragment(Fragment fragment, int i, String str) {
        switchFragment(fragment, i, str, false, false);
    }
}
