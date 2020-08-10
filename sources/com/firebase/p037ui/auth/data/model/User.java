package com.firebase.p037ui.auth.data.model;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.firebase.p037ui.auth.util.ExtraConstants;

/* renamed from: com.firebase.ui.auth.data.model.User */
public class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel parcel) {
            User user = new User(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), (Uri) parcel.readParcelable(Uri.class.getClassLoader()));
            return user;
        }

        public User[] newArray(int i) {
            return new User[i];
        }
    };
    private final String mEmail;
    private final String mName;
    private final String mPhoneNumber;
    private final Uri mPhotoUri;
    private final String mProviderId;

    /* renamed from: com.firebase.ui.auth.data.model.User$Builder */
    public static class Builder {
        private String mEmail;
        private String mName;
        private String mPhoneNumber;
        private Uri mPhotoUri;
        private String mProviderId;

        public Builder(String str, String str2) {
            this.mProviderId = str;
            this.mEmail = str2;
        }

        public Builder setPhoneNumber(String str) {
            this.mPhoneNumber = str;
            return this;
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setPhotoUri(Uri uri) {
            this.mPhotoUri = uri;
            return this;
        }

        public User build() {
            User user = new User(this.mProviderId, this.mEmail, this.mPhoneNumber, this.mName, this.mPhotoUri);
            return user;
        }
    }

    public int describeContents() {
        return 0;
    }

    private User(String str, String str2, String str3, String str4, Uri uri) {
        this.mProviderId = str;
        this.mEmail = str2;
        this.mPhoneNumber = str3;
        this.mName = str4;
        this.mPhotoUri = uri;
    }

    public static User getUser(Intent intent) {
        return (User) intent.getParcelableExtra(ExtraConstants.USER);
    }

    public static User getUser(Bundle bundle) {
        return (User) bundle.getParcelable(ExtraConstants.USER);
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public String getName() {
        return this.mName;
    }

    public Uri getPhotoUri() {
        return this.mPhotoUri;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        if (this.mProviderId.equals(user.mProviderId)) {
            String str = this.mEmail;
            if (str != null ? str.equals(user.mEmail) : user.mEmail == null) {
                String str2 = this.mPhoneNumber;
                if (str2 != null ? str2.equals(user.mPhoneNumber) : user.mPhoneNumber == null) {
                    String str3 = this.mName;
                    if (str3 != null ? str3.equals(user.mName) : user.mName == null) {
                        Uri uri = this.mPhotoUri;
                        Uri uri2 = user.mPhotoUri;
                        if (uri != null) {
                        }
                    }
                }
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        int hashCode = this.mProviderId.hashCode() * 31;
        String str = this.mEmail;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mPhoneNumber;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.mName;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Uri uri = this.mPhotoUri;
        if (uri != null) {
            i = uri.hashCode();
        }
        return hashCode4 + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{mProviderId='");
        sb.append(this.mProviderId);
        sb.append('\'');
        sb.append(", mEmail='");
        sb.append(this.mEmail);
        sb.append('\'');
        sb.append(", mPhoneNumber='");
        sb.append(this.mPhoneNumber);
        sb.append('\'');
        sb.append(", mName='");
        sb.append(this.mName);
        sb.append('\'');
        sb.append(", mPhotoUri=");
        sb.append(this.mPhotoUri);
        sb.append('}');
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mProviderId);
        parcel.writeString(this.mEmail);
        parcel.writeString(this.mPhoneNumber);
        parcel.writeString(this.mName);
        parcel.writeParcelable(this.mPhotoUri, i);
    }
}
