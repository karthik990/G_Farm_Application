package com.firebase.p037ui.auth;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.AuthMethodPickerLayout */
public class AuthMethodPickerLayout implements Parcelable {
    public static final Creator<AuthMethodPickerLayout> CREATOR = new Creator<AuthMethodPickerLayout>() {
        public AuthMethodPickerLayout createFromParcel(Parcel parcel) {
            return new AuthMethodPickerLayout(parcel);
        }

        public AuthMethodPickerLayout[] newArray(int i) {
            return new AuthMethodPickerLayout[i];
        }
    };
    /* access modifiers changed from: private */
    public int mainLayout;
    /* access modifiers changed from: private */
    public Map<String, Integer> providersButton;
    /* access modifiers changed from: private */
    public int tosPpView;

    /* renamed from: com.firebase.ui.auth.AuthMethodPickerLayout$Builder */
    public static class Builder {
        private AuthMethodPickerLayout instance = new AuthMethodPickerLayout();
        private Map<String, Integer> providersMapping;

        public Builder(int i) {
            this.instance.mainLayout = i;
            this.providersMapping = new HashMap();
        }

        public Builder setGoogleButtonId(int i) {
            this.providersMapping.put("google.com", Integer.valueOf(i));
            return this;
        }

        public Builder setFacebookButtonId(int i) {
            this.providersMapping.put("facebook.com", Integer.valueOf(i));
            return this;
        }

        public Builder setTwitterButtonId(int i) {
            this.providersMapping.put("twitter.com", Integer.valueOf(i));
            return this;
        }

        public Builder setEmailButtonId(int i) {
            this.providersMapping.put("password", Integer.valueOf(i));
            return this;
        }

        public Builder setGithubButtonId(int i) {
            this.providersMapping.put("github.com", Integer.valueOf(i));
            return this;
        }

        public Builder setPhoneButtonId(int i) {
            this.providersMapping.put("phone", Integer.valueOf(i));
            return this;
        }

        public Builder setAnonymousButtonId(int i) {
            this.providersMapping.put(AuthUI.ANONYMOUS_PROVIDER, Integer.valueOf(i));
            return this;
        }

        public Builder setTosAndPrivacyPolicyId(int i) {
            this.instance.tosPpView = i;
            return this;
        }

        public AuthMethodPickerLayout build() {
            if (!this.providersMapping.isEmpty()) {
                for (String str : this.providersMapping.keySet()) {
                    if (!AuthUI.SUPPORTED_PROVIDERS.contains(str)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unknown provider: ");
                        sb.append(str);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
                this.instance.providersButton = this.providersMapping;
                return this.instance;
            }
            throw new IllegalArgumentException("Must configure at least one button.");
        }
    }

    public int describeContents() {
        return 0;
    }

    private AuthMethodPickerLayout() {
        this.tosPpView = -1;
    }

    private AuthMethodPickerLayout(Parcel parcel) {
        this.tosPpView = -1;
        this.mainLayout = parcel.readInt();
        this.tosPpView = parcel.readInt();
        Bundle readBundle = parcel.readBundle(getClass().getClassLoader());
        this.providersButton = new HashMap();
        for (String str : readBundle.keySet()) {
            this.providersButton.put(str, Integer.valueOf(readBundle.getInt(str)));
        }
    }

    public int getMainLayout() {
        return this.mainLayout;
    }

    public int getTosPpView() {
        return this.tosPpView;
    }

    public Map<String, Integer> getProvidersButton() {
        return this.providersButton;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mainLayout);
        parcel.writeInt(this.tosPpView);
        Bundle bundle = new Bundle();
        for (String str : this.providersButton.keySet()) {
            bundle.putInt(str, ((Integer) this.providersButton.get(str)).intValue());
        }
        parcel.writeBundle(bundle);
    }
}
