package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.view.accessibility.CaptioningManager;
import com.google.android.exoplayer2.util.Util;
import java.util.Locale;

public class TrackSelectionParameters implements Parcelable {
    public static final Creator<TrackSelectionParameters> CREATOR = new Creator<TrackSelectionParameters>() {
        public TrackSelectionParameters createFromParcel(Parcel parcel) {
            return new TrackSelectionParameters(parcel);
        }

        public TrackSelectionParameters[] newArray(int i) {
            return new TrackSelectionParameters[i];
        }
    };
    @Deprecated
    public static final TrackSelectionParameters DEFAULT = DEFAULT_WITHOUT_CONTEXT;
    public static final TrackSelectionParameters DEFAULT_WITHOUT_CONTEXT = new Builder().build();
    public final int disabledTextTrackSelectionFlags;
    public final String preferredAudioLanguage;
    public final String preferredTextLanguage;
    public final int preferredTextRoleFlags;
    public final boolean selectUndeterminedTextLanguage;

    public static class Builder {
        int disabledTextTrackSelectionFlags;
        String preferredAudioLanguage;
        String preferredTextLanguage;
        int preferredTextRoleFlags;
        boolean selectUndeterminedTextLanguage;

        public Builder(Context context) {
            this();
            setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
        }

        @Deprecated
        public Builder() {
            this.preferredAudioLanguage = null;
            this.preferredTextLanguage = null;
            this.preferredTextRoleFlags = 0;
            this.selectUndeterminedTextLanguage = false;
            this.disabledTextTrackSelectionFlags = 0;
        }

        Builder(TrackSelectionParameters trackSelectionParameters) {
            this.preferredAudioLanguage = trackSelectionParameters.preferredAudioLanguage;
            this.preferredTextLanguage = trackSelectionParameters.preferredTextLanguage;
            this.preferredTextRoleFlags = trackSelectionParameters.preferredTextRoleFlags;
            this.selectUndeterminedTextLanguage = trackSelectionParameters.selectUndeterminedTextLanguage;
            this.disabledTextTrackSelectionFlags = trackSelectionParameters.disabledTextTrackSelectionFlags;
        }

        public Builder setPreferredAudioLanguage(String str) {
            this.preferredAudioLanguage = str;
            return this;
        }

        public Builder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            if (Util.SDK_INT >= 19) {
                m1388x58dbf263(context);
            }
            return this;
        }

        public Builder setPreferredTextLanguage(String str) {
            this.preferredTextLanguage = str;
            return this;
        }

        public Builder setPreferredTextRoleFlags(int i) {
            this.preferredTextRoleFlags = i;
            return this;
        }

        public Builder setSelectUndeterminedTextLanguage(boolean z) {
            this.selectUndeterminedTextLanguage = z;
            return this;
        }

        public Builder setDisabledTextTrackSelectionFlags(int i) {
            this.disabledTextTrackSelectionFlags = i;
            return this;
        }

        public TrackSelectionParameters build() {
            TrackSelectionParameters trackSelectionParameters = new TrackSelectionParameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.preferredTextRoleFlags, this.selectUndeterminedTextLanguage, this.disabledTextTrackSelectionFlags);
            return trackSelectionParameters;
        }

        /* renamed from: setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettingsV19 */
        private void m1388x58dbf263(Context context) {
            if (Util.SDK_INT >= 23 || Looper.myLooper() != null) {
                CaptioningManager captioningManager = (CaptioningManager) context.getSystemService("captioning");
                if (captioningManager != null && captioningManager.isEnabled()) {
                    this.preferredTextRoleFlags = 1088;
                    Locale locale = captioningManager.getLocale();
                    if (locale != null) {
                        this.preferredTextLanguage = Util.getLocaleLanguageTag(locale);
                    }
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public static TrackSelectionParameters getDefaults(Context context) {
        return new Builder(context).build();
    }

    TrackSelectionParameters(String str, String str2, int i, boolean z, int i2) {
        this.preferredAudioLanguage = Util.normalizeLanguageCode(str);
        this.preferredTextLanguage = Util.normalizeLanguageCode(str2);
        this.preferredTextRoleFlags = i;
        this.selectUndeterminedTextLanguage = z;
        this.disabledTextTrackSelectionFlags = i2;
    }

    TrackSelectionParameters(Parcel parcel) {
        this.preferredAudioLanguage = parcel.readString();
        this.preferredTextLanguage = parcel.readString();
        this.preferredTextRoleFlags = parcel.readInt();
        this.selectUndeterminedTextLanguage = Util.readBoolean(parcel);
        this.disabledTextTrackSelectionFlags = parcel.readInt();
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackSelectionParameters trackSelectionParameters = (TrackSelectionParameters) obj;
        if (!(TextUtils.equals(this.preferredAudioLanguage, trackSelectionParameters.preferredAudioLanguage) && TextUtils.equals(this.preferredTextLanguage, trackSelectionParameters.preferredTextLanguage) && this.preferredTextRoleFlags == trackSelectionParameters.preferredTextRoleFlags && this.selectUndeterminedTextLanguage == trackSelectionParameters.selectUndeterminedTextLanguage && this.disabledTextTrackSelectionFlags == trackSelectionParameters.disabledTextTrackSelectionFlags)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        String str = this.preferredAudioLanguage;
        int i = 0;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        String str2 = this.preferredTextLanguage;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return ((((((hashCode + i) * 31) + this.preferredTextRoleFlags) * 31) + (this.selectUndeterminedTextLanguage ? 1 : 0)) * 31) + this.disabledTextTrackSelectionFlags;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.preferredAudioLanguage);
        parcel.writeString(this.preferredTextLanguage);
        parcel.writeInt(this.preferredTextRoleFlags);
        Util.writeBoolean(parcel, this.selectUndeterminedTextLanguage);
        parcel.writeInt(this.disabledTextTrackSelectionFlags);
    }
}
