package com.mobiroller.viewholders.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.bumptech.glide.Glide;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.CircleImageView;

public class ChatBannedUserViewHolder extends ViewHolder {
    /* access modifiers changed from: private */
    public Activity context;
    @BindView(2131362266)
    CircleImageView dialogAvatar;
    @BindView(2131362268)
    RelativeLayout dialogContainer;
    @BindView(2131362275)
    TextView dialogName;
    @BindView(2131362279)
    TextView dialogUnreadBubble;
    /* access modifiers changed from: private */
    public ChatUserModel userModel;

    public ChatBannedUserViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ChatUserModel chatUserModel, Activity activity) {
        this.userModel = chatUserModel;
        this.context = activity;
        if (chatUserModel.imageUrl == null || chatUserModel.imageUrl.equalsIgnoreCase("")) {
            Glide.with(activity).load(Integer.valueOf(R.drawable.defaultuser)).into((ImageView) this.dialogAvatar);
        } else {
            ImageManager.displayUserImage(activity, chatUserModel.imageUrl, this.dialogAvatar);
        }
        this.dialogName.setText(chatUserModel.getName());
        if (chatUserModel.isOnline == null || !chatUserModel.isOnline.equalsIgnoreCase("n")) {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_offline_circle));
        } else {
            this.dialogUnreadBubble.setBackground(activity.getResources().getDrawable(R.drawable.chat_online_circle));
        }
    }

    @OnClick({2131362268})
    public void openChatActivity() {
        Intent intent = new Intent(this.context, UserProfileActivity.class);
        intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.userModel.uid);
        this.context.startActivity(intent);
    }

    @OnClick({2131362266})
    public void showImage() {
        SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        final MaterialDialog build = new Builder(this.context).customView((int) R.layout.layout_chat_image_popup, false).build();
        build.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View customView = build.getCustomView();
        ImageView imageView = (ImageView) customView.findViewById(R.id.image);
        TextView textView = (TextView) customView.findViewById(R.id.userName);
        ImageView imageView2 = (ImageView) customView.findViewById(R.id.text_user);
        View findViewById = customView.findViewById(R.id.divider);
        RelativeLayout relativeLayout = (RelativeLayout) customView.findViewById(R.id.mainLayout);
        ImageView imageView3 = (ImageView) customView.findViewById(R.id.user_profile);
        if (this.userModel.imageUrl == null || this.userModel.imageUrl.equalsIgnoreCase("")) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.defaultuser)).into(imageView);
        } else {
            ImageManager.displayUserImage(this.context, this.userModel.imageUrl, imageView);
        }
        textView.setText(this.userModel.getName());
        imageView2.setColorFilter(sharedPrefHelper.getActionBarColor(), Mode.SRC_IN);
        imageView3.setColorFilter(sharedPrefHelper.getActionBarColor(), Mode.SRC_IN);
        findViewById.setBackgroundColor(sharedPrefHelper.getActionBarColor());
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatBannedUserViewHolder.this.openChatActivity();
            }
        });
        imageView3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatBannedUserViewHolder.this.context.startActivity(new Intent(ChatBannedUserViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatBannedUserViewHolder.this.userModel.uid));
            }
        });
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                build.dismiss();
            }
        });
        build.setCancelable(true);
        build.show();
    }
}
