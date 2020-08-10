package com.mobiroller.views;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.mobi942763453128.R;
import java.util.ArrayList;

public class CreditCardNumberTextWatcher implements TextWatcher {
    public static Integer[] patternLogos = {Integer.valueOf(R.drawable.ic_e_commerce_visa_icon), Integer.valueOf(R.drawable.ic_e_commerce_master_card_icon)};
    private boolean isEmpty = true;
    private ArrayList<String> listOfPattern;
    private EditText mEditText;
    private TextInputLayout textInputLayout;

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public CreditCardNumberTextWatcher(EditText editText, TextInputLayout textInputLayout2) {
        this.mEditText = editText;
        this.textInputLayout = textInputLayout2;
        this.listOfPattern = getListOfPattern();
    }

    public void afterTextChanged(Editable editable) {
        String obj = editable.toString();
        if (obj.length() <= 3 && obj.length() != 0) {
            Log.e("CreditCard", "afterTextChanged 2");
            if (obj.length() == 2) {
                for (int i = 0; i < this.listOfPattern.size(); i++) {
                    if (obj.substring(0, 2).matches((String) this.listOfPattern.get(i))) {
                        this.textInputLayout.setError(null);
                        this.mEditText.setError(null);
                        this.mEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, patternLogos[i].intValue(), 0);
                        this.isEmpty = false;
                        return;
                    }
                    if (!this.isEmpty) {
                        this.mEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                }
            } else if (obj.length() == 1) {
                this.mEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    public static ArrayList<String> getListOfPattern() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("^4[0-9]$");
        arrayList.add("^5[1-8]$");
        return arrayList;
    }
}
