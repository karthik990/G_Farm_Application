package com.mancj.materialsearchbar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.List;

public abstract class SuggestionsAdapter<S, V extends ViewHolder> extends Adapter<V> implements Filterable {
    private final LayoutInflater inflater;
    protected int maxSuggestionsCount = 5;
    protected List<S> suggestions = new ArrayList();
    protected List<S> suggestions_clone = new ArrayList();

    public interface OnItemViewClickListener {
        void OnItemClickListener(int i, View view);

        void OnItemDeleteListener(int i, View view);
    }

    public Filter getFilter() {
        return null;
    }

    public abstract int getSingleViewHeight();

    public abstract void onBindSuggestionHolder(S s, V v, int i);

    public SuggestionsAdapter(LayoutInflater layoutInflater) {
        this.inflater = layoutInflater;
    }

    public void addSuggestion(S s) {
        if (this.maxSuggestionsCount > 0 && s != null) {
            if (!this.suggestions.contains(s)) {
                int size = this.suggestions.size();
                int i = this.maxSuggestionsCount;
                if (size >= i) {
                    this.suggestions.remove(i - 1);
                }
                this.suggestions.add(0, s);
            } else {
                this.suggestions.remove(s);
                this.suggestions.add(0, s);
            }
            this.suggestions_clone = this.suggestions;
            notifyDataSetChanged();
        }
    }

    public void setSuggestions(List<S> list) {
        this.suggestions = list;
        this.suggestions_clone = list;
        notifyDataSetChanged();
    }

    public void clearSuggestions() {
        this.suggestions.clear();
        this.suggestions_clone = this.suggestions;
        notifyDataSetChanged();
    }

    public void deleteSuggestion(int i, S s) {
        if (s != null && this.suggestions.contains(s)) {
            notifyItemRemoved(i);
            this.suggestions.remove(s);
            this.suggestions_clone = this.suggestions;
        }
    }

    public List<S> getSuggestions() {
        return this.suggestions;
    }

    public int getMaxSuggestionsCount() {
        return this.maxSuggestionsCount;
    }

    public void setMaxSuggestionsCount(int i) {
        this.maxSuggestionsCount = i;
    }

    /* access modifiers changed from: protected */
    public LayoutInflater getLayoutInflater() {
        return this.inflater;
    }

    public void onBindViewHolder(V v, int i) {
        onBindSuggestionHolder(this.suggestions.get(i), v, i);
    }

    public int getListHeight() {
        return getItemCount() * getSingleViewHeight();
    }

    public int getItemCount() {
        return this.suggestions.size();
    }
}
