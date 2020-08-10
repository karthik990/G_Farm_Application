package com.mobiroller.fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class aveSettingsViewFragment_ViewBinding implements Unbinder {
    private aveSettingsViewFragment target;
    private View view7f0a0322;
    private View view7f0a05ad;

    public aveSettingsViewFragment_ViewBinding(final aveSettingsViewFragment avesettingsviewfragment, View view) {
        this.target = avesettingsviewfragment;
        avesettingsviewfragment.languageTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.language_textview, "field 'languageTextView'", TextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.language_layout, "field 'languageLayout' and method 'languageLayoutOnClick'");
        avesettingsviewfragment.languageLayout = (LinearLayout) C0812Utils.castView(findRequiredView, R.id.language_layout, "field 'languageLayout'", LinearLayout.class);
        this.view7f0a0322 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                avesettingsviewfragment.languageLayoutOnClick();
            }
        });
        avesettingsviewfragment.notificationSwitch = (Switch) C0812Utils.findRequiredViewAsType(view, R.id.notification_switch, "field 'notificationSwitch'", Switch.class);
        avesettingsviewfragment.notificationSoundSwitch = (Switch) C0812Utils.findRequiredViewAsType(view, R.id.notification_sound_switch, "field 'notificationSoundSwitch'", Switch.class);
        avesettingsviewfragment.appVersionTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.app_version_textview, "field 'appVersionTextView'", TextView.class);
        avesettingsviewfragment.appVersionLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.app_version_layout, "field 'appVersionLayout'", LinearLayout.class);
        avesettingsviewfragment.settingLinearLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.setting_linear_layout, "field 'settingLinearLayout'", LinearLayout.class);
        avesettingsviewfragment.textSizeTextview = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.text_size_textview, "field 'textSizeTextview'", TextView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.text_size_layout, "field 'textSizeLayout' and method 'textSizeLayoutOnClick'");
        avesettingsviewfragment.textSizeLayout = (LinearLayout) C0812Utils.castView(findRequiredView2, R.id.text_size_layout, "field 'textSizeLayout'", LinearLayout.class);
        this.view7f0a05ad = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                avesettingsviewfragment.textSizeLayoutOnClick();
            }
        });
        avesettingsviewfragment.userAgreementLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.user_agreement_layout, "field 'userAgreementLayout'", LinearLayout.class);
        avesettingsviewfragment.userAgreementViewLayout = C0812Utils.findRequiredView(view, R.id.user_agreement_view_layout, "field 'userAgreementViewLayout'");
        avesettingsviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        avesettingsviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveSettingsViewFragment avesettingsviewfragment = this.target;
        if (avesettingsviewfragment != null) {
            this.target = null;
            avesettingsviewfragment.languageTextView = null;
            avesettingsviewfragment.languageLayout = null;
            avesettingsviewfragment.notificationSwitch = null;
            avesettingsviewfragment.notificationSoundSwitch = null;
            avesettingsviewfragment.appVersionTextView = null;
            avesettingsviewfragment.appVersionLayout = null;
            avesettingsviewfragment.settingLinearLayout = null;
            avesettingsviewfragment.textSizeTextview = null;
            avesettingsviewfragment.textSizeLayout = null;
            avesettingsviewfragment.userAgreementLayout = null;
            avesettingsviewfragment.userAgreementViewLayout = null;
            avesettingsviewfragment.mainLayout = null;
            avesettingsviewfragment.overlayLayout = null;
            this.view7f0a0322.setOnClickListener(null);
            this.view7f0a0322 = null;
            this.view7f0a05ad.setOnClickListener(null);
            this.view7f0a05ad = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
