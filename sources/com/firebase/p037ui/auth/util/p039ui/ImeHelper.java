package com.firebase.p037ui.auth.util.p039ui;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/* renamed from: com.firebase.ui.auth.util.ui.ImeHelper */
public class ImeHelper {

    /* renamed from: com.firebase.ui.auth.util.ui.ImeHelper$DonePressedListener */
    public interface DonePressedListener {
        void onDonePressed();
    }

    public static void setImeOnDoneListener(EditText editText, final DonePressedListener donePressedListener) {
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null && keyEvent.getKeyCode() == 66) {
                    if (keyEvent.getAction() == 1) {
                        donePressedListener.onDonePressed();
                    }
                    return true;
                } else if (i != 6) {
                    return false;
                } else {
                    donePressedListener.onDonePressed();
                    return true;
                }
            }
        });
    }
}
