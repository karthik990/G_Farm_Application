package com.mobiroller.viewholders.chat;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.chat.LogModel;
import com.mobiroller.views.CircleImageView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChatLogViewHolder extends ViewHolder {
    /* access modifiers changed from: private */
    public Activity context;
    @BindView(2131362268)
    RelativeLayout dialogContainer;
    @BindView(2131362269)
    TextView dialogDate;
    @BindView(2131362272)
    TextView dialogLastMessage;
    @BindView(2131362277)
    CircleImageView dialogSenderAvatar;
    private FirebaseChatHelper firebaseChatHelper;
    private LogModel logModel;
    @BindView(2131362730)
    RelativeLayout messageLayout;
    /* access modifiers changed from: private */
    public ChatUserModel receiverUser;
    @BindView(2131363120)
    RelativeLayout senderLayout;
    /* access modifiers changed from: private */
    public ChatUserModel senderUser;

    public ChatLogViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(LogModel logModel2, Activity activity) {
        this.firebaseChatHelper = new FirebaseChatHelper(activity.getApplicationContext());
        this.logModel = logModel2;
        this.context = activity;
        if (logModel2.getReceiverUid() != null && this.receiverUser == null) {
            this.firebaseChatHelper.getUserFromFirebase(logModel2.getReceiverUid(), true);
        }
        if (logModel2.getAuthUid() != null && this.senderUser == null) {
            this.firebaseChatHelper.getUserFromFirebase(logModel2.getAuthUid(), true);
        }
        if (DateUtils.isToday(((Long) logModel2.getTimeStamp()).longValue())) {
            this.dialogDate.setText(new SimpleDateFormat("HH:mm").format(new Date(((Long) logModel2.getTimeStamp()).longValue())));
        } else {
            Calendar instance = Calendar.getInstance();
            instance.add(6, -1);
            Calendar instance2 = Calendar.getInstance();
            instance2.setTime(new Date(((Long) logModel2.getTimeStamp()).longValue()));
            if (instance.get(1) == instance2.get(1) && instance.get(6) == instance2.get(6)) {
                this.dialogDate.setText(activity.getString(R.string.date_header_yesterday));
            } else {
                this.dialogDate.setText(new SimpleDateFormat(ChatConstants.CHAT_LOG_DATE_FORMAT).format(new Date(((Long) logModel2.getTimeStamp()).longValue())));
            }
        }
        if (logModel2.authUser != null) {
            setAuthUser(logModel2.authUser);
        }
        if (logModel2.receiverUser != null) {
            setReceiverUser(logModel2.receiverUser);
        }
        setInfoText();
        setIcon();
    }

    private void setIcon() {
        if (this.logModel.getType() == 2) {
            this.dialogSenderAvatar.setImageResource(R.drawable.ic_close_white_24dp);
        } else if (this.logModel.getType() == 3) {
            this.dialogSenderAvatar.setImageResource(R.drawable.ic_done_white_48dp);
        } else if (this.logModel.getType() == 4) {
            this.dialogSenderAvatar.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);
        } else if (this.logModel.getType() == 5) {
            this.dialogSenderAvatar.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        }
    }

    private void setInfoText() {
        ChatUserModel chatUserModel = this.senderUser;
        if (chatUserModel != null && this.receiverUser != null) {
            String str = "";
            if (chatUserModel.getName() == null) {
                this.senderUser.name = str;
            }
            if (this.receiverUser.getName() == null) {
                this.receiverUser.name = str;
            }
            if (this.logModel.getType() == 2) {
                this.dialogLastMessage.setText(this.context.getString(R.string.banned_by, new Object[]{this.receiverUser.getName(), this.senderUser.getName()}));
            } else if (this.logModel.getType() == 3) {
                this.dialogLastMessage.setText(this.context.getString(R.string.unbanned_by, new Object[]{this.receiverUser.getName(), this.senderUser.getName()}));
            } else if (this.logModel.getType() == 4) {
                this.dialogLastMessage.setText(this.context.getString(R.string.authorized_by, new Object[]{this.receiverUser.getName(), this.logModel.getMessage(), this.senderUser.getName()}));
            } else if (this.logModel.getType() == 5) {
                this.dialogLastMessage.setText(this.context.getString(R.string.unauthorized_by, new Object[]{this.receiverUser.getName(), this.senderUser.getName()}));
            }
        }
    }

    private void setAuthUser(ChatUserModel chatUserModel) {
        if (chatUserModel.uid.equalsIgnoreCase(this.logModel.getAuthUid())) {
            this.senderUser = chatUserModel;
            setInfoText();
        }
    }

    private void setReceiverUser(ChatUserModel chatUserModel) {
        if (chatUserModel.uid.equalsIgnoreCase(this.logModel.getReceiverUid())) {
            this.receiverUser = chatUserModel;
            setInfoText();
        }
    }

    @OnClick({2131362268})
    public void openReceiverProfile() {
        new Builder(this.context).content((CharSequence) this.dialogLastMessage.getText().toString()).positiveText((CharSequence) this.receiverUser.getName()).negativeText((CharSequence) this.senderUser.getName()).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatLogViewHolder.this.context.startActivity(new Intent(ChatLogViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatLogViewHolder.this.receiverUser.uid));
            }
        }).onNegative(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ChatLogViewHolder.this.context.startActivity(new Intent(ChatLogViewHolder.this.context, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, ChatLogViewHolder.this.senderUser.uid));
            }
        }).show();
    }
}
