package com.mobiroller.viewholders.chat;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.activities.chat.ChatArchivedDialogActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatModel;
import com.mobiroller.util.InterstitialAdsUtil;
import java.util.ArrayList;

public class ChatArchivedDialogsViewHolder extends ViewHolder {
    @BindView(2131361948)
    TextView archivedCountTextView;
    @BindView(2131361949)
    RelativeLayout archivedMainLayout;
    private Activity context;
    private ArrayList<ChatModel> dataList;
    private InterstitialAdsUtil interstitialAdsUtil;

    public ChatArchivedDialogsViewHolder(View view, InterstitialAdsUtil interstitialAdsUtil2) {
        super(view);
        this.interstitialAdsUtil = interstitialAdsUtil2;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ArrayList<ChatModel> arrayList, Activity activity) {
        this.dataList = arrayList;
        this.context = activity;
        this.archivedCountTextView.setText(activity.getString(R.string.archived_chats, new Object[]{Integer.valueOf(arrayList.size())}));
    }

    @OnClick({2131361949})
    public void openChatActivity() {
        Intent intent = new Intent(this.context, ChatArchivedDialogActivity.class);
        intent.putExtra(ChatConstants.ARG_ARCHIVED_LIST_MODEL, this.dataList);
        this.interstitialAdsUtil.checkInterstitialAds(intent);
    }
}
