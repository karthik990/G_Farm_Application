package com.ogaclejapan.smarttablayout.utils.v13;

import android.app.Fragment;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import java.io.Serializable;
import java.util.ArrayList;

public class Bundler {
    private final Bundle bundle;

    public Bundler() {
        this(null);
    }

    private Bundler(Bundle bundle2) {
        this.bundle = bundle2 == null ? new Bundle() : new Bundle(bundle2);
    }

    /* renamed from: of */
    public static Bundler m2042of(Bundle bundle2) {
        return new Bundler(bundle2);
    }

    public Bundler putAll(Bundle bundle2) {
        this.bundle.putAll(bundle2);
        return this;
    }

    public Bundler putByte(String str, byte b) {
        this.bundle.putByte(str, b);
        return this;
    }

    public Bundler putChar(String str, char c) {
        this.bundle.putChar(str, c);
        return this;
    }

    public Bundler putShort(String str, short s) {
        this.bundle.putShort(str, s);
        return this;
    }

    public Bundler putFloat(String str, float f) {
        this.bundle.putFloat(str, f);
        return this;
    }

    public Bundler putCharSequence(String str, CharSequence charSequence) {
        this.bundle.putCharSequence(str, charSequence);
        return this;
    }

    public Bundler putParcelable(String str, Parcelable parcelable) {
        this.bundle.putParcelable(str, parcelable);
        return this;
    }

    public Bundler putSize(String str, Size size) {
        this.bundle.putSize(str, size);
        return this;
    }

    public Bundler putSizeF(String str, SizeF sizeF) {
        this.bundle.putSizeF(str, sizeF);
        return this;
    }

    public Bundler putParcelableArray(String str, Parcelable[] parcelableArr) {
        this.bundle.putParcelableArray(str, parcelableArr);
        return this;
    }

    public Bundler putParcelableArrayList(String str, ArrayList<? extends Parcelable> arrayList) {
        this.bundle.putParcelableArrayList(str, arrayList);
        return this;
    }

    public Bundler putSparseParcelableArray(String str, SparseArray<? extends Parcelable> sparseArray) {
        this.bundle.putSparseParcelableArray(str, sparseArray);
        return this;
    }

    public Bundler putIntegerArrayList(String str, ArrayList<Integer> arrayList) {
        this.bundle.putIntegerArrayList(str, arrayList);
        return this;
    }

    public Bundler putStringArrayList(String str, ArrayList<String> arrayList) {
        this.bundle.putStringArrayList(str, arrayList);
        return this;
    }

    public Bundler putCharSequenceArrayList(String str, ArrayList<CharSequence> arrayList) {
        this.bundle.putCharSequenceArrayList(str, arrayList);
        return this;
    }

    public Bundler putSerializable(String str, Serializable serializable) {
        this.bundle.putSerializable(str, serializable);
        return this;
    }

    public Bundler putByteArray(String str, byte[] bArr) {
        this.bundle.putByteArray(str, bArr);
        return this;
    }

    public Bundler putShortArray(String str, short[] sArr) {
        this.bundle.putShortArray(str, sArr);
        return this;
    }

    public Bundler putCharArray(String str, char[] cArr) {
        this.bundle.putCharArray(str, cArr);
        return this;
    }

    public Bundler putFloatArray(String str, float[] fArr) {
        this.bundle.putFloatArray(str, fArr);
        return this;
    }

    public Bundler putCharSequenceArray(String str, CharSequence[] charSequenceArr) {
        this.bundle.putCharSequenceArray(str, charSequenceArr);
        return this;
    }

    public Bundler putBundle(String str, Bundle bundle2) {
        this.bundle.putBundle(str, bundle2);
        return this;
    }

    public Bundler putBinder(String str, IBinder iBinder) {
        this.bundle.putBinder(str, iBinder);
        return this;
    }

    public Bundler putBoolean(String str, boolean z) {
        this.bundle.putBoolean(str, z);
        return this;
    }

    public Bundler putInt(String str, int i) {
        this.bundle.putInt(str, i);
        return this;
    }

    public Bundler putLong(String str, long j) {
        this.bundle.putLong(str, j);
        return this;
    }

    public Bundler putDouble(String str, double d) {
        this.bundle.putDouble(str, d);
        return this;
    }

    public Bundler putString(String str, String str2) {
        this.bundle.putString(str, str2);
        return this;
    }

    public Bundler putBooleanArray(String str, boolean[] zArr) {
        this.bundle.putBooleanArray(str, zArr);
        return this;
    }

    public Bundler putIntArray(String str, int[] iArr) {
        this.bundle.putIntArray(str, iArr);
        return this;
    }

    public Bundler putLongArray(String str, long[] jArr) {
        this.bundle.putLongArray(str, jArr);
        return this;
    }

    public Bundler putDoubleArray(String str, double[] dArr) {
        this.bundle.putDoubleArray(str, dArr);
        return this;
    }

    public Bundler putStringArray(String str, String[] strArr) {
        this.bundle.putStringArray(str, strArr);
        return this;
    }

    public Bundle get() {
        return this.bundle;
    }

    public <T extends Fragment> T into(T t) {
        t.setArguments(get());
        return t;
    }
}
