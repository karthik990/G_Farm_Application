package com.mobiroller.activities.chat;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.chat.ChatUserSearchResultAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatIndexModel;
import com.mobiroller.models.events.UserListEmptyEvent;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.views.theme.Theme;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatUserSearchResultActivity extends AveActivity {
    private ChatUserSearchResultAdapter adapter;
    @Inject
    BannerHelper bannerHelper;
    @BindView(2131362144)
    RecyclerView chatList;
    @BindView(2131362334)
    ImageView emptyImageView;
    @BindView(2131362335)
    TextView emptyTextView;
    @BindView(2131362145)
    LinearLayout emptyView;
    private int mFilterBy;
    private String mSearchText;
    @BindView(2131363281)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    private ProgressViewHelper progressViewHelper;
    private SharedPrefHelper sharedPrefHelper;
    private ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_chat_user_search_result);
        ButterKnife.bind((Activity) this);
        this.toolbarHelper = new ToolbarHelper(UtilManager.sharedPrefHelper());
        this.toolbarHelper.setToolbarTitle(this, getResources().getString(R.string.search_results));
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.emptyView.setVisibility(8);
        this.chatList.setVisibility(0);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.emptyTextView.setText(getString(R.string.no_user_found));
        TextView textView = this.emptyTextView;
        boolean isColorDark = ColorUtil.isColorDark(Theme.primaryColor);
        int i = ViewCompat.MEASURED_STATE_MASK;
        textView.setTextColor(isColorDark ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK);
        ImageView imageView = this.emptyImageView;
        if (ColorUtil.isColorDark(Theme.primaryColor)) {
            i = Theme.primaryColor;
        }
        imageView.setColorFilter(i);
        this.emptyImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_person_white_24dp));
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.progressViewHelper.show();
        this.mFilterBy = getIntent().getIntExtra(ChatConstants.SEARCH_INTENT_EXTRA_FILTER_BY, 0);
        this.mSearchText = getIntent().getStringExtra(ChatConstants.SEARCH_INTENT_EXTRA_SEARCH_TEXT);
        if (this.mSearchText == null) {
            finish();
        }
        new Timer().schedule(new TimerTask() {
            public void run() {
                EventBus.getDefault().post(new UserListEmptyEvent());
            }
        }, 1000);
        setChatUserList();
    }

    public void onResume() {
        super.onResume();
        RelativeLayout relativeLayout = this.mainLayout;
        if (relativeLayout != null) {
            this.bannerHelper.addBannerAd(relativeLayout, this.overlayLayout);
        }
    }

    /* access modifiers changed from: private */
    public void setUserAdapter(ChatIndexModel chatIndexModel) {
        if (chatIndexModel != null) {
            this.progressViewHelper.dismiss();
            addItemToAdapter(chatIndexModel);
        }
    }

    private void addItemToAdapter(ChatIndexModel chatIndexModel) {
        if (this.adapter.getItemCount() == 0) {
            this.adapter.addItem(chatIndexModel);
        } else {
            this.adapter.checkItem(chatIndexModel);
        }
    }

    private void setChatUserList() {
        this.adapter = new ChatUserSearchResultAdapter(this, this.chatList, false);
        this.chatList.setAdapter(this.adapter);
        this.chatList.setLayoutManager(new LinearLayoutManager(this));
        getSearchResult();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkAdapterIsEmpty(UserListEmptyEvent userListEmptyEvent) {
        if (!isFinishing() && this.progressViewHelper.isShowing()) {
            this.progressViewHelper.dismiss();
        }
        if (this.adapter.getItemCount() != 0) {
            this.emptyView.setVisibility(8);
            this.chatList.setVisibility(0);
            return;
        }
        this.emptyView.setVisibility(0);
        this.chatList.setVisibility(8);
    }

    public void getSearchResult() {
        Query startAt = FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_SEARCH_INDEX).orderByChild(this.mFilterBy == 1 ? "un" : "n").startAt(this.mSearchText);
        StringBuilder sb = new StringBuilder();
        sb.append(this.mSearchText);
        sb.append("");
        startAt.endAt(sb.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        ChatIndexModel chatIndexModel = (ChatIndexModel) dataSnapshot2.getValue(ChatIndexModel.class);
                        chatIndexModel.uid = dataSnapshot2.getKey();
                        if (!TextUtils.equals(chatIndexModel.uid, FirebaseAuth.getInstance().getCurrentUser().getUid()) && ChatUserSearchResultActivity.this.validateUserModel(chatIndexModel)) {
                            ChatUserSearchResultActivity.this.setUserAdapter(chatIndexModel);
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean validateUserModel(ChatIndexModel chatIndexModel) {
        return (chatIndexModel == null || chatIndexModel.name == null || chatIndexModel.name.isEmpty()) ? false : true;
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }
}
