package com.mobiroller.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.mobiroller.adapters.DialogFilterAdapter;
import com.mobiroller.mobi942763453128.R;
import java.util.ArrayList;
import java.util.Collection;

public class MaterialListFilterDialog {
    /* access modifiers changed from: private */
    public DialogFilterAdapter mAdapter;
    private MaterialDialog mMaterialDialog;
    private RecyclerView mRecyclerView;

    private class FilterTextWatcher implements TextWatcher {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        private FilterTextWatcher() {
        }

        public void afterTextChanged(Editable editable) {
            if (MaterialListFilterDialog.this.mAdapter != null) {
                MaterialListFilterDialog.this.mAdapter.getFilter().filter(editable);
            }
        }
    }

    public MaterialListFilterDialog(Context context, int i, Collection collection) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(collection);
        this.mAdapter = new DialogFilterAdapter(arrayList);
        this.mMaterialDialog = new Builder(context).title(i).customView((int) R.layout.layout_filter_list_dialog, false).negativeText((int) R.string.cancel).build();
        View customView = this.mMaterialDialog.getCustomView();
        this.mRecyclerView = (RecyclerView) customView.findViewById(R.id.list);
        EditText editText = (EditText) customView.findViewById(R.id.input);
        this.mRecyclerView.setAdapter(this.mAdapter);
        editText.addTextChangedListener(new FilterTextWatcher());
    }

    public RecyclerView show() {
        this.mMaterialDialog.show();
        return this.mRecyclerView;
    }

    public void dismiss() {
        MaterialDialog materialDialog = this.mMaterialDialog;
        if (materialDialog != null && materialDialog.isShowing()) {
            this.mMaterialDialog.dismiss();
        }
    }
}
