package com.afollestad.materialdialogs;

import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.util.DialogUtils;

class DefaultRvAdapter extends Adapter<DefaultVH> {
    /* access modifiers changed from: private */
    public InternalListCallback callback;
    /* access modifiers changed from: private */
    public final MaterialDialog dialog;
    private final GravityEnum itemGravity;
    private final int layout;

    /* renamed from: com.afollestad.materialdialogs.DefaultRvAdapter$1 */
    static /* synthetic */ class C08221 {

        /* renamed from: $SwitchMap$com$afollestad$materialdialogs$MaterialDialog$ListType */
        static final /* synthetic */ int[] f70x59373eeb = new int[ListType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.afollestad.materialdialogs.MaterialDialog$ListType[] r0 = com.afollestad.materialdialogs.MaterialDialog.ListType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f70x59373eeb = r0
                int[] r0 = f70x59373eeb     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.afollestad.materialdialogs.MaterialDialog$ListType r1 = com.afollestad.materialdialogs.MaterialDialog.ListType.SINGLE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f70x59373eeb     // Catch:{ NoSuchFieldError -> 0x001f }
                com.afollestad.materialdialogs.MaterialDialog$ListType r1 = com.afollestad.materialdialogs.MaterialDialog.ListType.MULTI     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.DefaultRvAdapter.C08221.<clinit>():void");
        }
    }

    static class DefaultVH extends ViewHolder implements OnClickListener, OnLongClickListener {
        final DefaultRvAdapter adapter;
        final CompoundButton control;
        final TextView title;

        DefaultVH(View view, DefaultRvAdapter defaultRvAdapter) {
            super(view);
            this.control = (CompoundButton) view.findViewById(C0829R.C0832id.md_control);
            this.title = (TextView) view.findViewById(C0829R.C0832id.md_title);
            this.adapter = defaultRvAdapter;
            view.setOnClickListener(this);
            if (defaultRvAdapter.dialog.builder.listLongCallback != null) {
                view.setOnLongClickListener(this);
            }
        }

        public void onClick(View view) {
            if (this.adapter.callback != null && getAdapterPosition() != -1) {
                CharSequence charSequence = null;
                if (this.adapter.dialog.builder.items != null && getAdapterPosition() < this.adapter.dialog.builder.items.size()) {
                    charSequence = (CharSequence) this.adapter.dialog.builder.items.get(getAdapterPosition());
                }
                View view2 = view;
                this.adapter.callback.onItemSelected(this.adapter.dialog, view2, getAdapterPosition(), charSequence, false);
            }
        }

        public boolean onLongClick(View view) {
            if (this.adapter.callback == null || getAdapterPosition() == -1) {
                return false;
            }
            CharSequence charSequence = null;
            if (this.adapter.dialog.builder.items != null && getAdapterPosition() < this.adapter.dialog.builder.items.size()) {
                charSequence = (CharSequence) this.adapter.dialog.builder.items.get(getAdapterPosition());
            }
            return this.adapter.callback.onItemSelected(this.adapter.dialog, view, getAdapterPosition(), charSequence, true);
        }
    }

    interface InternalListCallback {
        boolean onItemSelected(MaterialDialog materialDialog, View view, int i, CharSequence charSequence, boolean z);
    }

    DefaultRvAdapter(MaterialDialog materialDialog, int i) {
        this.dialog = materialDialog;
        this.layout = i;
        this.itemGravity = materialDialog.builder.itemsGravity;
    }

    /* access modifiers changed from: 0000 */
    public void setCallback(InternalListCallback internalListCallback) {
        this.callback = internalListCallback;
    }

    public DefaultVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(this.layout, viewGroup, false);
        DialogUtils.setBackgroundCompat(inflate, this.dialog.getListSelector());
        return new DefaultVH(inflate, this);
    }

    public void onBindViewHolder(DefaultVH defaultVH, int i) {
        View view = defaultVH.itemView;
        boolean isIn = DialogUtils.isIn(Integer.valueOf(i), this.dialog.builder.disabledIndices);
        int i2 = C08221.f70x59373eeb[this.dialog.listType.ordinal()];
        if (i2 == 1) {
            RadioButton radioButton = (RadioButton) defaultVH.control;
            boolean z = this.dialog.builder.selectedIndex == i;
            if (this.dialog.builder.choiceWidgetColor != null) {
                MDTintHelper.setTint(radioButton, this.dialog.builder.choiceWidgetColor);
            } else {
                MDTintHelper.setTint(radioButton, this.dialog.builder.widgetColor);
            }
            radioButton.setChecked(z);
            radioButton.setEnabled(!isIn);
        } else if (i2 == 2) {
            CheckBox checkBox = (CheckBox) defaultVH.control;
            boolean contains = this.dialog.selectedIndicesList.contains(Integer.valueOf(i));
            if (this.dialog.builder.choiceWidgetColor != null) {
                MDTintHelper.setTint(checkBox, this.dialog.builder.choiceWidgetColor);
            } else {
                MDTintHelper.setTint(checkBox, this.dialog.builder.widgetColor);
            }
            checkBox.setChecked(contains);
            checkBox.setEnabled(!isIn);
        }
        defaultVH.title.setText((CharSequence) this.dialog.builder.items.get(i));
        defaultVH.title.setTextColor(this.dialog.builder.itemColor);
        this.dialog.setTypeface(defaultVH.title, this.dialog.builder.regularFont);
        ViewGroup viewGroup = (ViewGroup) view;
        setupGravity(viewGroup);
        if (this.dialog.builder.itemIds != null) {
            if (i < this.dialog.builder.itemIds.length) {
                view.setId(this.dialog.builder.itemIds[i]);
            } else {
                view.setId(-1);
            }
        }
        if (VERSION.SDK_INT >= 21 && viewGroup.getChildCount() == 2) {
            if (viewGroup.getChildAt(0) instanceof CompoundButton) {
                viewGroup.getChildAt(0).setBackground(null);
            } else if (viewGroup.getChildAt(1) instanceof CompoundButton) {
                viewGroup.getChildAt(1).setBackground(null);
            }
        }
    }

    public int getItemCount() {
        if (this.dialog.builder.items != null) {
            return this.dialog.builder.items.size();
        }
        return 0;
    }

    private void setupGravity(ViewGroup viewGroup) {
        ((LinearLayout) viewGroup).setGravity(this.itemGravity.getGravityInt() | 16);
        if (viewGroup.getChildCount() != 2) {
            return;
        }
        if (this.itemGravity == GravityEnum.END && !isRTL() && (viewGroup.getChildAt(0) instanceof CompoundButton)) {
            CompoundButton compoundButton = (CompoundButton) viewGroup.getChildAt(0);
            viewGroup.removeView(compoundButton);
            TextView textView = (TextView) viewGroup.getChildAt(0);
            viewGroup.removeView(textView);
            textView.setPadding(textView.getPaddingRight(), textView.getPaddingTop(), textView.getPaddingLeft(), textView.getPaddingBottom());
            viewGroup.addView(textView);
            viewGroup.addView(compoundButton);
        } else if (this.itemGravity == GravityEnum.START && isRTL() && (viewGroup.getChildAt(1) instanceof CompoundButton)) {
            CompoundButton compoundButton2 = (CompoundButton) viewGroup.getChildAt(1);
            viewGroup.removeView(compoundButton2);
            TextView textView2 = (TextView) viewGroup.getChildAt(0);
            viewGroup.removeView(textView2);
            textView2.setPadding(textView2.getPaddingRight(), textView2.getPaddingTop(), textView2.getPaddingRight(), textView2.getPaddingBottom());
            viewGroup.addView(compoundButton2);
            viewGroup.addView(textView2);
        }
    }

    private boolean isRTL() {
        boolean z = false;
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (this.dialog.getBuilder().getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
            z = true;
        }
        return z;
    }
}
