package com.stfalcon.chatkit.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.stfalcon.chatkit.commons.models.IDialog;

public class DialogsList extends RecyclerView {
    private DialogListStyle dialogStyle;

    public DialogsList(Context context) {
        super(context);
    }

    public DialogsList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        parseStyle(context, attributeSet);
    }

    public DialogsList(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        parseStyle(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 1, false);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        setLayoutManager(linearLayoutManager);
        setItemAnimator(defaultItemAnimator);
    }

    public void setAdapter(Adapter adapter) {
        throw new IllegalArgumentException("You can't set adapter to DialogsList. Use #setAdapter(DialogsListAdapter) instead.");
    }

    public <DIALOG extends IDialog> void setAdapter(DialogsListAdapter<DIALOG> dialogsListAdapter) {
        setAdapter(dialogsListAdapter, true);
    }

    public <DIALOG extends IDialog> void setAdapter(DialogsListAdapter<DIALOG> dialogsListAdapter, boolean z) {
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setSupportsChangeAnimations(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 1, z);
        setItemAnimator(defaultItemAnimator);
        setLayoutManager(linearLayoutManager);
        dialogsListAdapter.setStyle(this.dialogStyle);
        super.setAdapter(dialogsListAdapter);
    }

    private void parseStyle(Context context, AttributeSet attributeSet) {
        this.dialogStyle = DialogListStyle.parse(context, attributeSet);
    }
}
