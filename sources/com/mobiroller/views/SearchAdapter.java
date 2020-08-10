package com.mobiroller.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ECommerceUtil;
import java.util.ArrayList;
import java.util.Arrays;

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
        ImageView removeImageView;
        TextView textView;

        public SuggestionsViewHolder(View view) {
            this.textView = (TextView) view.findViewById(R.id.suggestion_text);
            if (SearchAdapter.this.suggestionIcon != null) {
                this.imageView = (ImageView) view.findViewById(R.id.suggestion_icon);
                this.imageView.setImageDrawable(SearchAdapter.this.suggestionIcon);
            }
            this.removeImageView = (ImageView) view.findViewById(R.id.remove_icon);
            this.removeImageView.setOnClickListener(new OnClickListener(SearchAdapter.this) {
                public void onClick(View view) {
                    ECommerceUtil.removeSearchSuggestion(SuggestionsViewHolder.this.textView.getText().toString());
                    SearchAdapter.this.suggestions = ECommerceUtil.getSearchSuggestions();
                    SearchAdapter.this.data = new ArrayList();
                    SearchAdapter.this.data.addAll(Arrays.asList(SearchAdapter.this.suggestions));
                    SearchAdapter.this.notifyDataSetChanged();
                }
            });
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
                ArrayList arrayList = new ArrayList();
                if (!TextUtils.isEmpty(charSequence)) {
                    for (String str : SearchAdapter.this.suggestions) {
                        if (str.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            arrayList.add(str);
                        }
                    }
                    filterResults.values = arrayList;
                    filterResults.count = arrayList.size();
                } else {
                    arrayList.addAll(Arrays.asList(SearchAdapter.this.suggestions));
                    filterResults.values = arrayList;
                    filterResults.count = SearchAdapter.this.suggestions.length;
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
            view = this.inflater.inflate(R.layout.search_suggest_item, viewGroup, false);
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
