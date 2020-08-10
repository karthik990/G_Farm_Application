package com.mancj.materialsearchbar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.mancj.materialsearchbar.C3850R;

public class DefaultSuggestionsAdapter extends SuggestionsAdapter<String, SuggestionHolder> {
    /* access modifiers changed from: private */
    public com.mancj.materialsearchbar.adapter.SuggestionsAdapter.OnItemViewClickListener listener;

    public interface OnItemViewClickListener {
        void OnItemClickListener(int i, View view);

        void OnItemDeleteListener(int i, View view);
    }

    class SuggestionHolder extends ViewHolder {
        private final ImageView iv_delete;
        /* access modifiers changed from: private */
        public final TextView text;

        public SuggestionHolder(View view) {
            super(view);
            this.text = (TextView) view.findViewById(C3850R.C3853id.text);
            this.iv_delete = (ImageView) view.findViewById(C3850R.C3853id.iv_delete);
            view.setOnClickListener(new OnClickListener(DefaultSuggestionsAdapter.this) {
                public void onClick(View view) {
                    view.setTag(DefaultSuggestionsAdapter.this.getSuggestions().get(SuggestionHolder.this.getAdapterPosition()));
                    DefaultSuggestionsAdapter.this.listener.OnItemClickListener(SuggestionHolder.this.getAdapterPosition(), view);
                }
            });
            this.iv_delete.setOnClickListener(new OnClickListener(DefaultSuggestionsAdapter.this) {
                public void onClick(View view) {
                    int adapterPosition = SuggestionHolder.this.getAdapterPosition();
                    if (adapterPosition > 0 && adapterPosition < DefaultSuggestionsAdapter.this.getSuggestions().size()) {
                        view.setTag(DefaultSuggestionsAdapter.this.getSuggestions().get(SuggestionHolder.this.getAdapterPosition()));
                        DefaultSuggestionsAdapter.this.listener.OnItemDeleteListener(SuggestionHolder.this.getAdapterPosition(), view);
                    }
                }
            });
        }
    }

    public int getSingleViewHeight() {
        return 50;
    }

    public DefaultSuggestionsAdapter(LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    public void setListener(com.mancj.materialsearchbar.adapter.SuggestionsAdapter.OnItemViewClickListener onItemViewClickListener) {
        this.listener = onItemViewClickListener;
    }

    public SuggestionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SuggestionHolder(getLayoutInflater().inflate(C3850R.layout.item_last_request, viewGroup, false));
    }

    public void onBindSuggestionHolder(String str, SuggestionHolder suggestionHolder, int i) {
        suggestionHolder.text.setText((CharSequence) getSuggestions().get(i));
    }
}
