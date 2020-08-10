package com.mobiroller.fragments;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.google.android.material.snackbar.Snackbar;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.aveWebView;
import com.mobiroller.constants.Constants;
import com.mobiroller.enums.FontStyle;
import com.mobiroller.helpers.FontSizeHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.WebContent;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.views.theme.Theme;
import java.util.Arrays;
import java.util.List;

public class aveSettingsViewFragment extends BaseModuleFragment implements OnCheckedChangeListener {
    @BindView(2131361945)
    LinearLayout appVersionLayout;
    @BindView(2131361946)
    TextView appVersionTextView;
    private FontSizeHelper fontSizeHelper;
    /* access modifiers changed from: private */
    public String language;
    private String[] languageArray;
    private String[] languageCodeArray;
    @BindView(2131362594)
    LinearLayout languageLayout;
    @BindView(2131362595)
    TextView languageTextView;
    /* access modifiers changed from: private */
    public String[] localeCodes;
    /* access modifiers changed from: private */
    public LocalizationHelper mLocalizationHelper;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    private boolean notification;
    private boolean notificationSound;
    @BindView(2131362833)
    Switch notificationSoundSwitch;
    @BindView(2131362834)
    Switch notificationSwitch;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    @BindView(2131363121)
    LinearLayout settingLinearLayout;
    /* access modifiers changed from: private */
    public Snackbar snackbar;
    @BindView(2131363245)
    LinearLayout textSizeLayout;
    @BindView(2131363246)
    TextView textSizeTextview;
    Unbinder unbinder;
    @BindView(2131363344)
    LinearLayout userAgreementLayout;
    @BindView(2131363345)
    View userAgreementViewLayout;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_setting, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.mLocalizationHelper = UtilManager.localizationHelper();
        this.fontSizeHelper = new FontSizeHelper(getActivity());
        this.language = getResources().getString(R.string.locale);
        String localeCodes2 = this.sharedPrefHelper.getLocaleCodes();
        this.notification = this.sharedPrefHelper.getNotification();
        this.notificationSound = this.sharedPrefHelper.getNotificationSound();
        this.languageArray = getResources().getStringArray(R.array.supported_languages);
        this.languageCodeArray = getResources().getStringArray(R.array.supported_languages_language_codes);
        if (localeCodes2 == null) {
            this.localeCodes = new String[0];
        } else {
            this.localeCodes = localeCodes2.split(",");
        }
        if (this.localeCodes.length <= 1) {
            this.languageLayout.setVisibility(8);
        }
        loadUiProperties();
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.snackbar = Snackbar.make((View) this.settingLinearLayout, (CharSequence) getString(R.string.settings_saved), -1).setAction((CharSequence) getString(R.string.OK), (OnClickListener) new OnClickListener() {
            public void onClick(View view) {
                aveSettingsViewFragment.this.snackbar.dismiss();
            }
        });
    }

    private void loadUiProperties() {
        setLanguage();
        setFontSizeText();
        this.notificationSwitch.setChecked(this.notification);
        this.notificationSoundSwitch.setChecked(this.notificationSound);
        if (!this.notification) {
            this.notificationSoundSwitch.setEnabled(false);
        }
        this.notificationSwitch.setOnCheckedChangeListener(this);
        this.notificationSoundSwitch.setOnCheckedChangeListener(this);
        try {
            this.appVersionTextView.setText(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            this.appVersionTextView.setText(getString(R.string.unknown));
            e.printStackTrace();
        }
        if (this.sharedPrefHelper.getIsUserAgremeentActive()) {
            this.userAgreementLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        WebContent webContent = new WebContent(aveSettingsViewFragment.this.getString(R.string.user_agreement_settings), String.valueOf(Uri.parse(aveSettingsViewFragment.this.mLocalizationHelper.getLocalizedTitle(aveSettingsViewFragment.this.sharedPrefHelper.getUserAgremeent()))), true);
                        Intent intent = new Intent(aveSettingsViewFragment.this.getActivity(), aveWebView.class);
                        intent.putExtra("webContent", webContent);
                        aveSettingsViewFragment.this.startActivity(intent);
                        aveSettingsViewFragment.this.getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent intent2 = new Intent("android.intent.action.VIEW");
                        intent2.setData(Uri.parse(aveSettingsViewFragment.this.mLocalizationHelper.getLocalizedTitle(aveSettingsViewFragment.this.sharedPrefHelper.getUserAgremeent())));
                        aveSettingsViewFragment.this.startActivity(intent2);
                    }
                }
            });
            return;
        }
        this.userAgreementViewLayout.setVisibility(8);
        this.userAgreementLayout.setVisibility(8);
    }

    private void setLanguage() {
        int i = 0;
        while (true) {
            String[] strArr = this.languageCodeArray;
            if (i >= strArr.length) {
                return;
            }
            if (strArr[i].equalsIgnoreCase(this.language)) {
                this.languageTextView.setText(this.languageArray[i]);
                return;
            }
            i++;
        }
    }

    private String[] getLanguageList() {
        String[] strArr = new String[this.localeCodes.length];
        for (int i = 0; i < this.localeCodes.length; i++) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.languageCodeArray;
                if (i2 >= strArr2.length) {
                    break;
                } else if (strArr2[i2].equalsIgnoreCase(this.localeCodes[i])) {
                    strArr[i] = this.languageArray[i2];
                    break;
                } else {
                    i2++;
                }
            }
        }
        return strArr;
    }

    private String[] getFontSizeList() {
        return getResources().getStringArray(R.array.text_size_list);
    }

    private void setFontSizeText() {
        int fontOrder = this.fontSizeHelper.getFontOrder();
        if (fontOrder == 0) {
            this.textSizeTextview.setText(R.string.text_size_small);
        } else if (fontOrder == 1) {
            this.textSizeTextview.setText(R.string.text_size_medium);
        } else if (fontOrder == 2) {
            this.textSizeTextview.setText(R.string.text_size_big);
        } else if (fontOrder == 3) {
            this.textSizeTextview.setText(R.string.text_size_very_big);
        }
    }

    public void setNotification(boolean z) {
        this.notificationSoundSwitch.setEnabled(z);
        this.sharedPrefHelper.setNotification(z);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        switch (compoundButton.getId()) {
            case R.id.notification_sound_switch /*2131362833*/:
                this.sharedPrefHelper.setNotificationSound(z);
                break;
            case R.id.notification_switch /*2131362834*/:
                setNotification(z);
                break;
        }
        this.snackbar.show();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public void restartApplication() {
        Intent launchIntentForPackage = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        launchIntentForPackage.addFlags(67108864);
        getActivity().finish();
        getActivity().startActivity(launchIntentForPackage);
    }

    public void saveFontSelectedFontSize(int i) {
        if (i == 0) {
            this.fontSizeHelper.setFontStyle(FontStyle.Small);
        } else if (i == 1) {
            this.fontSizeHelper.setFontStyle(FontStyle.Medium);
        } else if (i == 2) {
            this.fontSizeHelper.setFontStyle(FontStyle.Large);
        } else if (i == 3) {
            this.fontSizeHelper.setFontStyle(FontStyle.XLarge);
        }
    }

    @OnClick({2131363245})
    public void textSizeLayoutOnClick() {
        final int fontOrder = this.fontSizeHelper.getFontOrder();
        Builder items = new Builder(getActivity()).title((int) R.string.change_font_size).items((CharSequence[]) getFontSizeList());
        boolean isColorDark = ColorUtil.isColorDark(Theme.primaryColor);
        int i = ViewCompat.MEASURED_STATE_MASK;
        Builder positiveColor = items.choiceWidgetColor(ColorStateList.valueOf(isColorDark ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK)).positiveColor(ColorStateList.valueOf(ColorUtil.isColorDark(Theme.primaryColor) ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK));
        if (ColorUtil.isColorDark(Theme.primaryColor)) {
            i = Theme.primaryColor;
        }
        positiveColor.negativeColor(ColorStateList.valueOf(i)).itemsCallbackSingleChoice(fontOrder, new ListCallbackSingleChoice() {
            public boolean onSelection(MaterialDialog materialDialog, View view, final int i, CharSequence charSequence) {
                if (i != fontOrder) {
                    if (!DynamicConstants.MobiRoller_Stage) {
                        new Builder(aveSettingsViewFragment.this.getActivity()).content((int) R.string.application_restart_info).negativeText((int) R.string.cancel).positiveText((int) R.string.OK).onPositive(new SingleButtonCallback() {
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                aveSettingsViewFragment.this.saveFontSelectedFontSize(i);
                                aveSettingsViewFragment.this.restartApplication();
                            }
                        }).show();
                    } else {
                        Toast.makeText(aveSettingsViewFragment.this.getActivity(), aveSettingsViewFragment.this.getString(R.string.not_supported_on_preview), 0).show();
                    }
                }
                return true;
            }
        }).negativeText((int) R.string.cancel).positiveText((int) R.string.OK).show();
    }

    @OnClick({2131362594})
    public void languageLayoutOnClick() {
        int i;
        try {
            i = Arrays.asList(this.localeCodes).indexOf(this.language);
        } catch (Exception unused) {
            i = -1;
        }
        Builder items = new Builder(getActivity()).title((int) R.string.change_language).items((CharSequence[]) getLanguageList());
        boolean isColorDark = ColorUtil.isColorDark(Theme.primaryColor);
        int i2 = ViewCompat.MEASURED_STATE_MASK;
        Builder positiveColor = items.choiceWidgetColor(ColorStateList.valueOf(isColorDark ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK)).positiveColor(ColorStateList.valueOf(ColorUtil.isColorDark(Theme.primaryColor) ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK));
        if (ColorUtil.isColorDark(Theme.primaryColor)) {
            i2 = Theme.primaryColor;
        }
        positiveColor.negativeColor(ColorStateList.valueOf(i2)).itemsCallbackSingleChoice(i, new ListCallbackSingleChoice() {
            public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                if (!aveSettingsViewFragment.this.language.equalsIgnoreCase(aveSettingsViewFragment.this.localeCodes[i])) {
                    if (!DynamicConstants.MobiRoller_Stage) {
                        List asList = Arrays.asList(aveSettingsViewFragment.this.getActivity().getResources().getStringArray(R.array.supported_languages_language_codes));
                        int indexOf = asList.indexOf(aveSettingsViewFragment.this.localeCodes[i].toLowerCase());
                        UtilManager.sharedPrefHelper().put(Constants.DISPLAY_LANGUAGE, ((String) asList.get(indexOf)).toLowerCase());
                        aveSettingsViewFragment.this.sharedPrefHelper.languageSetByUser();
                        Log.e("Locale Settings", ((String) asList.get(indexOf)).toLowerCase());
                        aveSettingsViewFragment.this.restartApplication();
                    } else {
                        Toast.makeText(aveSettingsViewFragment.this.getActivity(), aveSettingsViewFragment.this.getString(R.string.not_supported_on_preview), 0).show();
                    }
                }
                return true;
            }
        }).negativeText((int) R.string.cancel).positiveText((int) R.string.OK).show();
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }
}
