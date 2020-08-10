package com.afollestad.materialdialogs.simplelist;

import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.commons.C0841R;
import com.afollestad.materialdialogs.internal.MDAdapter;
import java.util.ArrayList;
import java.util.List;

public class MaterialSimpleListAdapter extends Adapter<SimpleListVH> implements MDAdapter {
    /* access modifiers changed from: private */
    public Callback callback;
    /* access modifiers changed from: private */
    public MaterialDialog dialog;
    private List<MaterialSimpleListItem> items = new ArrayList(4);

    public interface Callback {
        void onMaterialListItemSelected(MaterialDialog materialDialog, int i, MaterialSimpleListItem materialSimpleListItem);
    }

    static class SimpleListVH extends ViewHolder implements OnClickListener {
        final MaterialSimpleListAdapter adapter;
        final ImageView icon;
        final TextView title;

        SimpleListVH(View view, MaterialSimpleListAdapter materialSimpleListAdapter) {
            super(view);
            this.icon = (ImageView) view.findViewById(16908294);
            this.title = (TextView) view.findViewById(16908310);
            this.adapter = materialSimpleListAdapter;
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (this.adapter.callback != null) {
                this.adapter.callback.onMaterialListItemSelected(this.adapter.dialog, getAdapterPosition(), this.adapter.getItem(getAdapterPosition()));
            }
        }
    }

    public MaterialSimpleListAdapter(Callback callback2) {
        this.callback = callback2;
    }

    public void add(MaterialSimpleListItem materialSimpleListItem) {
        this.items.add(materialSimpleListItem);
        notifyItemInserted(this.items.size() - 1);
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public MaterialSimpleListItem getItem(int i) {
        return (MaterialSimpleListItem) this.items.get(i);
    }

    public void setDialog(MaterialDialog materialDialog) {
        this.dialog = materialDialog;
    }

    public SimpleListVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SimpleListVH(LayoutInflater.from(viewGroup.getContext()).inflate(C0841R.layout.md_simplelist_item, viewGroup, false), this);
    }

    public void onBindViewHolder(SimpleListVH simpleListVH, int i) {
        if (this.dialog != null) {
            MaterialSimpleListItem materialSimpleListItem = (MaterialSimpleListItem) this.items.get(i);
            if (materialSimpleListItem.getIcon() != null) {
                simpleListVH.icon.setImageDrawable(materialSimpleListItem.getIcon());
                simpleListVH.icon.setPadding(materialSimpleListItem.getIconPadding(), materialSimpleListItem.getIconPadding(), materialSimpleListItem.getIconPadding(), materialSimpleListItem.getIconPadding());
                simpleListVH.icon.getBackground().setColorFilter(materialSimpleListItem.getBackgroundColor(), Mode.SRC_ATOP);
            } else {
                simpleListVH.icon.setVisibility(8);
            }
            simpleListVH.title.setTextColor(this.dialog.getBuilder().getItemColor());
            simpleListVH.title.setText(materialSimpleListItem.getContent());
            this.dialog.setTypeface(simpleListVH.title, this.dialog.getBuilder().getRegularFont());
        }
    }

    public int getItemCount() {
        return this.items.size();
    }
}
