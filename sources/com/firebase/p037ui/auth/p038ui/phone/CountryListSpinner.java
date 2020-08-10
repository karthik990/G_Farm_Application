package com.firebase.p037ui.auth.p038ui.phone;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import androidx.appcompat.widget.AppCompatEditText;
import com.firebase.p037ui.auth.data.model.CountryInfo;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PhoneNumberUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* renamed from: com.firebase.ui.auth.ui.phone.CountryListSpinner */
public final class CountryListSpinner extends AppCompatEditText implements OnClickListener {
    private static final String KEY_COUNTRY_INFO = "KEY_COUNTRY_INFO";
    private static final String KEY_SUPER_STATE = "KEY_SUPER_STATE";
    private Set<String> mBlacklistedCountryIsos;
    private final CountryListAdapter mCountryListAdapter;
    private final DialogPopup mDialogPopup;
    private OnClickListener mListener;
    private CountryInfo mSelectedCountryInfo;
    /* access modifiers changed from: private */
    public String mSelectedCountryName;
    private final String mTextFormat;
    private Set<String> mWhitelistedCountryIsos;

    /* renamed from: com.firebase.ui.auth.ui.phone.CountryListSpinner$DialogPopup */
    public class DialogPopup implements DialogInterface.OnClickListener {
        private static final long DELAY_MILLIS = 10;
        private AlertDialog dialog;
        private final CountryListAdapter listAdapter;

        DialogPopup(CountryListAdapter countryListAdapter) {
            this.listAdapter = countryListAdapter;
        }

        public void dismiss() {
            AlertDialog alertDialog = this.dialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.dialog = null;
            }
        }

        public boolean isShowing() {
            AlertDialog alertDialog = this.dialog;
            return alertDialog != null && alertDialog.isShowing();
        }

        public void show(final int i) {
            if (this.listAdapter != null) {
                this.dialog = new Builder(CountryListSpinner.this.getContext()).setSingleChoiceItems(this.listAdapter, 0, this).create();
                this.dialog.setCanceledOnTouchOutside(true);
                final ListView listView = this.dialog.getListView();
                listView.setFastScrollEnabled(true);
                listView.setScrollbarFadingEnabled(false);
                listView.postDelayed(new Runnable() {
                    public void run() {
                        listView.setSelection(i);
                    }
                }, DELAY_MILLIS);
                this.dialog.show();
            }
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            CountryInfo countryInfo = (CountryInfo) this.listAdapter.getItem(i);
            CountryListSpinner.this.mSelectedCountryName = countryInfo.getLocale().getDisplayCountry();
            CountryListSpinner.this.setSelectedForCountry(countryInfo.getCountryCode(), countryInfo.getLocale());
            dismiss();
        }
    }

    public CountryListSpinner(Context context) {
        this(context, null, 16842881);
    }

    public CountryListSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842881);
    }

    public CountryListSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        super.setOnClickListener(this);
        this.mCountryListAdapter = new CountryListAdapter(getContext());
        this.mDialogPopup = new DialogPopup(this.mCountryListAdapter);
        this.mTextFormat = "%1$s  +%2$d";
        this.mSelectedCountryName = "";
    }

    public void init(Bundle bundle) {
        if (bundle != null) {
            List countriesToDisplayInSpinner = getCountriesToDisplayInSpinner(bundle);
            setCountriesToDisplay(countriesToDisplayInSpinner);
            setDefaultCountryForSpinner(countriesToDisplayInSpinner);
        }
    }

    private List<CountryInfo> getCountriesToDisplayInSpinner(Bundle bundle) {
        initCountrySpinnerIsosFromParams(bundle);
        Map immutableCountryIsoMap = PhoneNumberUtils.getImmutableCountryIsoMap();
        if (this.mWhitelistedCountryIsos == null && this.mBlacklistedCountryIsos == null) {
            this.mWhitelistedCountryIsos = new HashSet(immutableCountryIsoMap.keySet());
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        if (this.mWhitelistedCountryIsos == null) {
            hashSet.addAll(this.mBlacklistedCountryIsos);
        } else {
            hashSet.addAll(immutableCountryIsoMap.keySet());
            hashSet.removeAll(this.mWhitelistedCountryIsos);
        }
        for (String str : immutableCountryIsoMap.keySet()) {
            if (!hashSet.contains(str)) {
                arrayList.add(new CountryInfo(new Locale("", str), ((Integer) immutableCountryIsoMap.get(str)).intValue()));
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private void initCountrySpinnerIsosFromParams(Bundle bundle) {
        ArrayList stringArrayList = bundle.getStringArrayList(ExtraConstants.WHITELISTED_COUNTRIES);
        ArrayList stringArrayList2 = bundle.getStringArrayList(ExtraConstants.BLACKLISTED_COUNTRIES);
        if (stringArrayList != null) {
            this.mWhitelistedCountryIsos = convertCodesToIsos(stringArrayList);
        } else if (stringArrayList2 != null) {
            this.mBlacklistedCountryIsos = convertCodesToIsos(stringArrayList2);
        }
    }

    private Set<String> convertCodesToIsos(List<String> list) {
        HashSet hashSet = new HashSet();
        for (String str : list) {
            if (PhoneNumberUtils.isValid(str)) {
                hashSet.addAll(PhoneNumberUtils.getCountryIsosFromCountryCode(str));
            } else {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    public void setCountriesToDisplay(List<CountryInfo> list) {
        this.mCountryListAdapter.setData(list);
    }

    private void setDefaultCountryForSpinner(List<CountryInfo> list) {
        CountryInfo currentCountryInfo = PhoneNumberUtils.getCurrentCountryInfo(getContext());
        if (isValidIso(currentCountryInfo.getLocale().getCountry())) {
            setSelectedForCountry(currentCountryInfo.getCountryCode(), currentCountryInfo.getLocale());
        } else if (list.iterator().hasNext()) {
            CountryInfo countryInfo = (CountryInfo) list.iterator().next();
            setSelectedForCountry(countryInfo.getCountryCode(), countryInfo.getLocale());
        }
    }

    public boolean isValidIso(String str) {
        String upperCase = str.toUpperCase(Locale.getDefault());
        if (!(this.mWhitelistedCountryIsos == null && this.mBlacklistedCountryIsos == null)) {
            Set<String> set = this.mWhitelistedCountryIsos;
            if (set == null || !set.contains(upperCase)) {
                Set<String> set2 = this.mBlacklistedCountryIsos;
                if (set2 == null || set2.contains(upperCase)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, onSaveInstanceState);
        bundle.putParcelable(KEY_COUNTRY_INFO, this.mSelectedCountryInfo);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        Parcelable parcelable2 = bundle.getParcelable(KEY_SUPER_STATE);
        this.mSelectedCountryInfo = (CountryInfo) bundle.getParcelable(KEY_COUNTRY_INFO);
        super.onRestoreInstanceState(parcelable2);
    }

    private static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setSelectedForCountry(int i, Locale locale) {
        setText(String.format(this.mTextFormat, new Object[]{CountryInfo.localeToEmoji(locale), Integer.valueOf(i)}));
        this.mSelectedCountryInfo = new CountryInfo(locale, i);
    }

    public void setSelectedForCountry(Locale locale, String str) {
        if (isValidIso(locale.getCountry())) {
            String displayName = locale.getDisplayName();
            if (!TextUtils.isEmpty(displayName) && !TextUtils.isEmpty(str)) {
                this.mSelectedCountryName = displayName;
                setSelectedForCountry(Integer.parseInt(str), locale);
            }
        }
    }

    public CountryInfo getSelectedCountryInfo() {
        return this.mSelectedCountryInfo;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mDialogPopup.isShowing()) {
            this.mDialogPopup.dismiss();
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mListener = onClickListener;
    }

    public void onClick(View view) {
        this.mDialogPopup.show(this.mCountryListAdapter.getPositionForCountry(this.mSelectedCountryName));
        hideKeyboard(getContext(), this);
        executeUserClickListener(view);
    }

    private void executeUserClickListener(View view) {
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }
}
