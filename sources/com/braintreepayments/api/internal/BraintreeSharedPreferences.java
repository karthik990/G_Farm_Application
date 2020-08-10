package com.braintreepayments.api.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

public class BraintreeSharedPreferences {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("BraintreeApi", 0);
    }

    public static void putParcelable(Context context, String str, Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        getSharedPreferences(context).edit().putString(str, Base64.encodeToString(obtain.marshall(), 0)).apply();
    }

    public static Parcel getParcelable(Context context, String str) {
        try {
            byte[] decode = Base64.decode(getSharedPreferences(context).getString(str, ""), 0);
            Parcel obtain = Parcel.obtain();
            obtain.unmarshall(decode, 0, decode.length);
            obtain.setDataPosition(0);
            return obtain;
        } catch (Exception unused) {
            return null;
        }
    }

    public static void remove(Context context, String str) {
        getSharedPreferences(context).edit().remove(str).apply();
    }

    public static void putString(Context context, String str, String str2) {
        getSharedPreferences(context).edit().putString(str, str2).apply();
    }

    public static String getString(Context context, String str) {
        return getSharedPreferences(context).getString(str, "");
    }
}
