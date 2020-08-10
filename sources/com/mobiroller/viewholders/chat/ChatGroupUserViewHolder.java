package com.mobiroller.viewholders.chat;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.mobiroller.models.chat.UserModel;
import com.mobiroller.views.CircleImageView;

public class ChatGroupUserViewHolder extends ViewHolder {
    private Context context;
    @BindView(2131362266)
    CircleImageView dialogAvatar;
    @BindView(2131362268)
    RelativeLayout dialogContainer;
    @BindView(2131362275)
    TextView dialogName;
    public View itemView;
    private UserModel userModel;

    @OnClick({2131362268})
    public void openChatActivity() {
    }

    public ChatGroupUserViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(UserModel userModel2, Context context2) {
        this.userModel = userModel2;
        this.context = context2;
        this.itemView = this.dialogContainer;
        Glide.with(context2).load(userModel2.getImageUrl()).into((ImageView) this.dialogAvatar);
        this.dialogName.setText(userModel2.getName());
    }
}
