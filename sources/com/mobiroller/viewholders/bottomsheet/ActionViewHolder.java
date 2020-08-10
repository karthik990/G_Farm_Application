package com.mobiroller.viewholders.bottomsheet;

import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.models.bottomsheet.ActionModel;

public class ActionViewHolder extends ViewHolder {
    @BindView(2131362531)
    ImageView image;
    @BindView(2131363258)
    TextView title;

    public ActionViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ActionModel actionModel) {
        this.image.setImageResource(actionModel.iconRes);
        TextView textView = this.title;
        textView.setText(textView.getContext().getResources().getText(actionModel.titleRes));
        if (actionModel.colorize) {
            this.image.setColorFilter(UtilManager.sharedPrefHelper().getActionBarColor(), Mode.SRC_ATOP);
        }
    }
}
