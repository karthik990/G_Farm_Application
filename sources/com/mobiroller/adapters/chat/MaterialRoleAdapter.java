package com.mobiroller.adapters.chat;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.MaterialRoleListItem;
import java.util.ArrayList;
import java.util.List;

public class MaterialRoleAdapter extends Adapter<SimpleListVH> implements MDAdapter {
    /* access modifiers changed from: private */
    public Callback callback;
    /* access modifiers changed from: private */
    public MaterialDialog dialog;
    private List<MaterialRoleListItem> items = new ArrayList(4);
    private Context mContext;

    public interface Callback {
        void onMaterialListItemSelected(MaterialDialog materialDialog, int i, MaterialRoleListItem materialRoleListItem);
    }

    static class SimpleListVH extends ViewHolder implements OnClickListener {
        final MaterialRoleAdapter adapter;
        final ImageView icon;
        final TextView title;

        SimpleListVH(View view, MaterialRoleAdapter materialRoleAdapter) {
            super(view);
            this.icon = (ImageView) view.findViewById(16908294);
            this.title = (TextView) view.findViewById(16908310);
            this.adapter = materialRoleAdapter;
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (this.adapter.callback != null) {
                this.adapter.callback.onMaterialListItemSelected(this.adapter.dialog, getAdapterPosition(), this.adapter.getItem(getAdapterPosition()));
            }
        }
    }

    public MaterialRoleAdapter(Context context, Callback callback2) {
        this.callback = callback2;
        this.mContext = context;
    }

    public void add(MaterialRoleListItem materialRoleListItem) {
        this.items.add(materialRoleListItem);
        notifyItemInserted(this.items.size() - 1);
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public MaterialRoleListItem getItem(int i) {
        return (MaterialRoleListItem) this.items.get(i);
    }

    public void setDialog(MaterialDialog materialDialog) {
        this.dialog = materialDialog;
    }

    public SimpleListVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SimpleListVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.md_simplelist_item, viewGroup, false), this);
    }

    public void onBindViewHolder(final SimpleListVH simpleListVH, int i) {
        if (this.dialog != null) {
            MaterialRoleListItem materialRoleListItem = (MaterialRoleListItem) this.items.get(i);
            if (materialRoleListItem.getIcon() != null) {
                simpleListVH.icon.setImageDrawable(materialRoleListItem.getIcon());
                simpleListVH.icon.setPadding(materialRoleListItem.getIconPadding(), materialRoleListItem.getIconPadding(), materialRoleListItem.getIconPadding(), materialRoleListItem.getIconPadding());
                simpleListVH.icon.getBackground().setColorFilter(materialRoleListItem.getBackgroundColor(), Mode.SRC_ATOP);
            } else if (materialRoleListItem.getIconUrl() != null) {
                simpleListVH.icon.getBackground().setColorFilter(materialRoleListItem.getBackgroundColor(), Mode.SRC_ATOP);
                Glide.with(this.mContext).load(materialRoleListItem.getIconUrl()).listener(new RequestListener<Drawable>() {
                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        return false;
                    }

                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        simpleListVH.icon.setImageResource(R.drawable.defaultuser);
                        return false;
                    }
                }).into(simpleListVH.icon);
            } else {
                simpleListVH.icon.setVisibility(8);
            }
            simpleListVH.title.setTextColor(this.dialog.getBuilder().getItemColor());
            simpleListVH.title.setText(materialRoleListItem.getContent());
            this.dialog.setTypeface(simpleListVH.title, this.dialog.getBuilder().getRegularFont());
        }
    }

    public int getItemCount() {
        return this.items.size();
    }
}
