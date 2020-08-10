package com.mobiroller.adapters.chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.bumptech.glide.Glide;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatAdminModel;
import com.mobiroller.util.InterstitialAdsUtil;
import java.util.List;

public class ChatAdminAdapter extends Adapter<ViewHolder> {
    private Context context;
    private List<ChatAdminModel> dataList;
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;

    class ChatAdminViewHolder extends ViewHolder {
        @BindView(2131362137)
        TextView chatAdminDescription;
        @BindView(2131362139)
        ImageView chatAdminIcon;
        private ChatAdminModel chatAdminModel;
        @BindView(2131362140)
        TextView chatAdminTitle;
        private Context context;
        public View itemView;

        ChatAdminViewHolder(View view) {
            super(view);
            ButterKnife.bind((Object) this, view);
        }

        public void bind(ChatAdminModel chatAdminModel2, Context context2) {
            this.context = context2;
            this.chatAdminModel = chatAdminModel2;
            this.chatAdminTitle.setText(chatAdminModel2.getTitle());
            this.chatAdminDescription.setText(chatAdminModel2.getDescription());
            if (chatAdminModel2.getIcon() != 0) {
                this.chatAdminIcon.setImageResource(chatAdminModel2.getIcon());
            } else if (chatAdminModel2.getIconUrl() != null && !chatAdminModel2.getIconUrl().equalsIgnoreCase("")) {
                Glide.with(context2).load(chatAdminModel2.getIconUrl()).into(this.chatAdminIcon);
            }
        }

        @OnClick({2131362138})
        public void openChatAdminActivity() {
            if (this.chatAdminModel.getTitle().equals(this.context.getString(R.string.chat_role_management))) {
                this.chatAdminModel.getIntent().putExtra("panel", ChatConstants.ADMIN_ROLE_PANEL);
            }
            boolean equals = this.chatAdminModel.getTitle().equals(this.context.getString(R.string.blocked_users));
            String str = ChatConstants.ARG_BANNED_USERS;
            String str2 = ChatConstants.CHAT_DATABASE_PATH;
            if (equals) {
                this.chatAdminModel.getIntent().putExtra(str2, str);
            }
            if (this.chatAdminModel.getTitle().equals(this.context.getString(R.string.chat_reports))) {
                this.chatAdminModel.getIntent().putExtra(str2, str);
            }
            if (this.chatAdminModel.getIcon() == 0) {
                String str3 = "title";
                this.chatAdminModel.getIntent().putExtra(str2, ChatConstants.ARG_USER_LIST).putExtra("roleId", this.chatAdminModel.getExtra()).putExtra(str3, this.chatAdminModel.getTitle());
            }
            ChatAdminAdapter.this.interstitialAdsUtil.checkInterstitialAds(this.chatAdminModel.getIntent());
        }
    }

    public class ChatAdminViewHolder_ViewBinding implements Unbinder {
        private ChatAdminViewHolder target;
        private View view7f0a015a;

        public ChatAdminViewHolder_ViewBinding(final ChatAdminViewHolder chatAdminViewHolder, View view) {
            this.target = chatAdminViewHolder;
            chatAdminViewHolder.chatAdminTitle = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.chat_admin_title, "field 'chatAdminTitle'", TextView.class);
            chatAdminViewHolder.chatAdminDescription = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.chat_admin_description, "field 'chatAdminDescription'", TextView.class);
            chatAdminViewHolder.chatAdminIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.chat_admin_logo, "field 'chatAdminIcon'", ImageView.class);
            View findRequiredView = C0812Utils.findRequiredView(view, R.id.chat_admin_item_layout, "method 'openChatAdminActivity'");
            this.view7f0a015a = findRequiredView;
            findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
                public void doClick(View view) {
                    chatAdminViewHolder.openChatAdminActivity();
                }
            });
        }

        public void unbind() {
            ChatAdminViewHolder chatAdminViewHolder = this.target;
            if (chatAdminViewHolder != null) {
                this.target = null;
                chatAdminViewHolder.chatAdminTitle = null;
                chatAdminViewHolder.chatAdminDescription = null;
                chatAdminViewHolder.chatAdminIcon = null;
                this.view7f0a015a.setOnClickListener(null);
                this.view7f0a015a = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }

    public ChatAdminAdapter(List<ChatAdminModel> list, Activity activity) {
        this.interstitialAdsUtil = new InterstitialAdsUtil(activity);
        this.dataList = list;
        this.context = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ChatAdminViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_admin_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((ChatAdminViewHolder) viewHolder).bind((ChatAdminModel) this.dataList.get(i), this.context);
    }

    public int getItemCount() {
        return this.dataList.size();
    }
}
