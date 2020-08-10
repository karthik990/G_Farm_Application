package com.firebase.p037ui.auth.util.p039ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.Collections;

/* renamed from: com.firebase.ui.auth.util.ui.BucketedTextChangeListener */
public final class BucketedTextChangeListener implements TextWatcher {
    private final ContentChangeCallback mCallback;
    private final EditText mEditText;
    private final int mExpectedContentLength;
    private final String mPlaceHolder;
    private final String[] mPostFixes;

    /* renamed from: com.firebase.ui.auth.util.ui.BucketedTextChangeListener$ContentChangeCallback */
    public interface ContentChangeCallback {
        void whenComplete();

        void whileIncomplete();
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public BucketedTextChangeListener(EditText editText, int i, String str, ContentChangeCallback contentChangeCallback) {
        this.mEditText = editText;
        this.mExpectedContentLength = i;
        this.mPostFixes = generatePostfixArray(str, i);
        this.mCallback = contentChangeCallback;
        this.mPlaceHolder = str;
    }

    private static String[] generatePostfixArray(CharSequence charSequence, int i) {
        String[] strArr = new String[(i + 1)];
        for (int i2 = 0; i2 <= i; i2++) {
            strArr[i2] = TextUtils.join("", Collections.nCopies(i2, charSequence));
        }
        return strArr;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String str = "";
        String replaceAll = charSequence.toString().replaceAll(" ", str).replaceAll(this.mPlaceHolder, str);
        int min = Math.min(replaceAll.length(), this.mExpectedContentLength);
        String substring = replaceAll.substring(0, min);
        this.mEditText.removeTextChangedListener(this);
        EditText editText = this.mEditText;
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(this.mPostFixes[this.mExpectedContentLength - min]);
        editText.setText(sb.toString());
        this.mEditText.setSelection(min);
        this.mEditText.addTextChangedListener(this);
        if (min == this.mExpectedContentLength) {
            ContentChangeCallback contentChangeCallback = this.mCallback;
            if (contentChangeCallback != null) {
                contentChangeCallback.whenComplete();
                return;
            }
        }
        ContentChangeCallback contentChangeCallback2 = this.mCallback;
        if (contentChangeCallback2 != null) {
            contentChangeCallback2.whileIncomplete();
        }
    }
}
