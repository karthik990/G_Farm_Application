package com.miguelcatalan.materialsearchview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements Filterable {
    /* access modifiers changed from: private */
    public ArrayList<String> data = new ArrayList<>();
    private boolean ellipsize;
    private LayoutInflater inflater;
    /* access modifiers changed from: private */
    public Drawable suggestionIcon;
    /* access modifiers changed from: private */
    public String[] suggestions;

    private class SuggestionsViewHolder {
        ImageView imageView;
        TextView textView;

        public SuggestionsViewHolder(View view) {
            this.textView = (TextView) view.findViewById(C3867R.C3870id.suggestion_text);
            if (SearchAdapter.this.suggestionIcon != null) {
                this.imageView = (ImageView) view.findViewById(C3867R.C3870id.suggestion_icon);
                this.imageView.setImageDrawable(SearchAdapter.this.suggestionIcon);
            }
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public SearchAdapter(Context context, String[] strArr) {
        this.inflater = LayoutInflater.from(context);
        this.suggestions = strArr;
    }

    public SearchAdapter(Context context, String[] strArr, Drawable drawable, boolean z) {
        this.inflater = LayoutInflater.from(context);
        this.suggestions = strArr;
        this.suggestionIcon = drawable;
        this.ellipsize = z;
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                String[] access$000;
                FilterResults filterResults = new FilterResults();
                if (!TextUtils.isEmpty(charSequence)) {
                    ArrayList arrayList = new ArrayList();
                    for (String str : SearchAdapter.this.suggestions) {
                        if (str.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            arrayList.add(str);
                        }
                    }
                    filterResults.values = arrayList;
                    filterResults.count = arrayList.size();
                }
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.values != null) {
                    SearchAdapter.this.data = (ArrayList) filterResults.values;
                    SearchAdapter.this.notifyDataSetChanged();
                }
            }
        };
    }

    public int getCount() {
        return this.data.size();
    }

    public Object getItem(int i) {
        return this.data.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        SuggestionsViewHolder suggestionsViewHolder;
        if (view == null) {
            view = this.inflater.inflate(C3867R.layout.suggest_item, viewGroup, false);
            suggestionsViewHolder = new SuggestionsViewHolder(view);
            view.setTag(suggestionsViewHolder);
        } else {
            suggestionsViewHolder = (SuggestionsViewHolder) view.getTag();
        }
        suggestionsViewHolder.textView.setText((String) getItem(i));
        if (this.ellipsize) {
            suggestionsViewHolder.textView.setSingleLine();
            suggestionsViewHolder.textView.setEllipsize(TruncateAt.END);
        }
        return view;
    }
}
