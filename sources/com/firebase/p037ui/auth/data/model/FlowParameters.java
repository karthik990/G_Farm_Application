package com.firebase.p037ui.auth.data.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.firebase.p037ui.auth.AuthMethodPickerLayout;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.Preconditions;
import java.util.Collections;
import java.util.List;

/* renamed from: com.firebase.ui.auth.data.model.FlowParameters */
public class FlowParameters implements Parcelable {
    public static final Creator<FlowParameters> CREATOR = new Creator<FlowParameters>() {
        public FlowParameters createFromParcel(Parcel parcel) {
            Parcel parcel2 = parcel;
            FlowParameters flowParameters = new FlowParameters(parcel.readString(), parcel2.createTypedArrayList(IdpConfig.CREATOR), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readString(), (AuthMethodPickerLayout) parcel2.readParcelable(AuthMethodPickerLayout.class.getClassLoader()));
            return flowParameters;
        }

        public FlowParameters[] newArray(int i) {
            return new FlowParameters[i];
        }
    };
    public final boolean alwaysShowProviderChoice;
    public final String appName;
    public final AuthMethodPickerLayout authMethodPickerLayout;
    public String emailLink;
    public final boolean enableAnonymousUpgrade;
    public final boolean enableCredentials;
    public final boolean enableHints;
    public final int logoId;
    public final String privacyPolicyUrl;
    public final List<IdpConfig> providers;
    public final String termsOfServiceUrl;
    public final int themeId;

    public int describeContents() {
        return 0;
    }

    public FlowParameters(String str, List<IdpConfig> list, int i, int i2, String str2, String str3, boolean z, boolean z2, boolean z3, boolean z4, String str4, AuthMethodPickerLayout authMethodPickerLayout2) {
        this.appName = (String) Preconditions.checkNotNull(str, "appName cannot be null", new Object[0]);
        this.providers = Collections.unmodifiableList((List) Preconditions.checkNotNull(list, "providers cannot be null", new Object[0]));
        this.themeId = i;
        this.logoId = i2;
        this.termsOfServiceUrl = str2;
        this.privacyPolicyUrl = str3;
        this.enableCredentials = z;
        this.enableHints = z2;
        this.enableAnonymousUpgrade = z3;
        this.alwaysShowProviderChoice = z4;
        this.emailLink = str4;
        this.authMethodPickerLayout = authMethodPickerLayout2;
    }

    public static FlowParameters fromIntent(Intent intent) {
        return (FlowParameters) intent.getParcelableExtra(ExtraConstants.FLOW_PARAMS);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.appName);
        parcel.writeTypedList(this.providers);
        parcel.writeInt(this.themeId);
        parcel.writeInt(this.logoId);
        parcel.writeString(this.termsOfServiceUrl);
        parcel.writeString(this.privacyPolicyUrl);
        parcel.writeInt(this.enableCredentials ? 1 : 0);
        parcel.writeInt(this.enableHints ? 1 : 0);
        parcel.writeInt(this.enableAnonymousUpgrade ? 1 : 0);
        parcel.writeInt(this.alwaysShowProviderChoice ? 1 : 0);
        parcel.writeString(this.emailLink);
        parcel.writeParcelable(this.authMethodPickerLayout, i);
    }

    public boolean isSingleProviderFlow() {
        return this.providers.size() == 1;
    }

    public boolean isTermsOfServiceUrlProvided() {
        return !TextUtils.isEmpty(this.termsOfServiceUrl);
    }

    public boolean isPrivacyPolicyUrlProvided() {
        return !TextUtils.isEmpty(this.privacyPolicyUrl);
    }

    public boolean isAnonymousUpgradeEnabled() {
        return this.enableAnonymousUpgrade;
    }

    public boolean shouldShowProviderChoice() {
        return !isSingleProviderFlow() || this.alwaysShowProviderChoice;
    }
}
