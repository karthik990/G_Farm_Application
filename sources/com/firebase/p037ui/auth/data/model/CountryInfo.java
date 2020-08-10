package com.firebase.p037ui.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.text.Collator;
import java.util.Locale;

/* renamed from: com.firebase.ui.auth.data.model.CountryInfo */
public final class CountryInfo implements Comparable<CountryInfo>, Parcelable {
    public static final Creator<CountryInfo> CREATOR = new Creator<CountryInfo>() {
        public CountryInfo createFromParcel(Parcel parcel) {
            return new CountryInfo(parcel);
        }

        public CountryInfo[] newArray(int i) {
            return new CountryInfo[i];
        }
    };
    private final Collator mCollator = Collator.getInstance(Locale.getDefault());
    private final int mCountryCode;
    private final Locale mLocale;

    public int describeContents() {
        return 0;
    }

    public CountryInfo(Locale locale, int i) {
        this.mCollator.setStrength(0);
        this.mLocale = locale;
        this.mCountryCode = i;
    }

    protected CountryInfo(Parcel parcel) {
        this.mCollator.setStrength(0);
        this.mLocale = (Locale) parcel.readSerializable();
        this.mCountryCode = parcel.readInt();
    }

    public static String localeToEmoji(Locale locale) {
        String country = locale.getCountry();
        int codePointAt = (Character.codePointAt(country, 0) - 65) + 127462;
        int codePointAt2 = (Character.codePointAt(country, 1) - 65) + 127462;
        StringBuilder sb = new StringBuilder();
        sb.append(new String(Character.toChars(codePointAt)));
        sb.append(new String(Character.toChars(codePointAt2)));
        return sb.toString();
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getCountryCode() {
        return this.mCountryCode;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CountryInfo countryInfo = (CountryInfo) obj;
        if (this.mCountryCode == countryInfo.mCountryCode) {
            Locale locale = this.mLocale;
            if (locale == null) {
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        Locale locale = this.mLocale;
        return ((locale != null ? locale.hashCode() : 0) * 31) + this.mCountryCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(localeToEmoji(this.mLocale));
        sb.append(" ");
        sb.append(this.mLocale.getDisplayCountry());
        sb.append(" +");
        sb.append(this.mCountryCode);
        return sb.toString();
    }

    public int compareTo(CountryInfo countryInfo) {
        return this.mCollator.compare(this.mLocale.getDisplayCountry(), countryInfo.mLocale.getDisplayCountry());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(this.mLocale);
        parcel.writeInt(this.mCountryCode);
    }
}
