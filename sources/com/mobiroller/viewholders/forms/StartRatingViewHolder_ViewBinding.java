package com.mobiroller.viewholders.forms;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class StartRatingViewHolder_ViewBinding implements Unbinder {
    private StartRatingViewHolder target;

    public StartRatingViewHolder_ViewBinding(StartRatingViewHolder startRatingViewHolder, View view) {
        this.target = startRatingViewHolder;
        startRatingViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_item_title, "field 'title'", TextView.class);
        startRatingViewHolder.ratingBar = (RatingBar) C0812Utils.findRequiredViewAsType(view, R.id.form_item_rating_bar, "field 'ratingBar'", RatingBar.class);
    }

    public void unbind() {
        StartRatingViewHolder startRatingViewHolder = this.target;
        if (startRatingViewHolder != null) {
            this.target = null;
            startRatingViewHolder.title = null;
            startRatingViewHolder.ratingBar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
