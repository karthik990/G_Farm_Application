package com.mobiroller.viewholders.forms;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.models.SubmitModel;
import org.greenrobot.eventbus.EventBus;
import p015br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class SubmitViewHolder extends ViewHolder {
    @BindView(2131362446)
    CircularProgressButton submit;

    public SubmitViewHolder(View view, int i) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    @OnClick({2131362446})
    public void submit() {
        EventBus.getDefault().post(new SubmitModel());
    }

    public CircularProgressButton getSubmitButton() {
        return this.submit;
    }
}
