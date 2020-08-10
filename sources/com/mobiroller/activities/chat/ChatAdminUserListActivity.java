package com.mobiroller.activities.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.chat.ChatUserAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.events.UserListEmptyEvent;
import com.mobiroller.preview.C4290R;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatAdminUserListActivity extends AveActivity {
    private ChatUserAdapter adapter;
    @BindView(2131362144)
    RecyclerView chatList;
    private String databasePath = null;
    @BindView(2131362334)
    ImageView emptyImageView;
    @BindView(2131362335)
    TextView emptyTextView;
    @BindView(2131362145)
    LinearLayout emptyView;
    private InterstitialAdsUtil interstitialAdsUtil;
    private ProgressViewHelper progressViewHelper;
    ArrayList<String> roles = null;
    private SharedPrefHelper sharedPrefHelper;
    @BindView(2131363276)
    MobirollerToolbar toolbar;
    private ToolbarHelper toolbarHelper;
    Unbinder unbinder;
    private String userRolesId = "";

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        setContentView((int) R.layout.layout_chat_banned_user_list);
        ButterKnife.bind((Activity) this);
        setMobirollerToolbar(this.toolbar);
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        this.toolbar.getContext().setTheme(C4290R.C4297style.AppTheme_Toolbar);
        this.toolbarHelper = new ToolbarHelper(this.sharedPrefHelper);
        EventBus.getDefault().register(this);
        this.emptyView.setVisibility(8);
        this.chatList.setVisibility(0);
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
        this.databasePath = getIntent().getStringExtra(ChatConstants.CHAT_DATABASE_PATH);
        String str = "title";
        if (!getIntent().hasExtra(str)) {
            this.toolbarHelper.setToolbarTitle(this, getResources().getString(R.string.blocked_users));
        } else {
            this.userRolesId = getIntent().getStringExtra("roleId");
            this.toolbarHelper.setToolbarTitle(this, getIntent().getStringExtra(str));
        }
        setChatUserList();
        new Timer().schedule(new TimerTask() {
            public void run() {
                EventBus.getDefault().post(new UserListEmptyEvent());
            }
        }, 1000);
    }

    /* access modifiers changed from: private */
    public void setUserAdapter(ChatUserModel chatUserModel) {
        if (chatUserModel != null) {
            ArrayList<String> arrayList = this.roles;
            String str = " ";
            if (arrayList == null || arrayList.size() <= 0) {
                this.progressViewHelper.dismiss();
                addItemToAdapter(chatUserModel);
                ActionBar supportActionBar = getSupportActionBar();
                StringBuilder sb = new StringBuilder();
                sb.append(this.adapter.getItemCount());
                sb.append(str);
                sb.append(getString(R.string.person));
                supportActionBar.setSubtitle((CharSequence) sb.toString());
                return;
            }
            for (int i = 0; i < this.roles.size(); i++) {
                if (chatUserModel.roleString.equalsIgnoreCase((String) this.roles.get(i))) {
                    addItemToAdapter(chatUserModel);
                    ActionBar supportActionBar2 = getSupportActionBar();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.adapter.getItemCount());
                    sb2.append(str);
                    sb2.append(getString(R.string.person));
                    supportActionBar2.setSubtitle((CharSequence) sb2.toString());
                    this.progressViewHelper.dismiss();
                    return;
                }
            }
        }
    }

    private void addItemToAdapter(ChatUserModel chatUserModel) {
        if (this.adapter.getItemCount() == 0) {
            this.adapter.addItem(chatUserModel);
        } else {
            this.adapter.checkItem(chatUserModel);
        }
        this.chatList.smoothScrollToPosition(0);
    }

    private void setChatUserList() {
        this.adapter = new ChatUserAdapter(this, this.chatList, true);
        this.chatList.setAdapter(this.adapter);
        this.chatList.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent().hasExtra("roleId")) {
            getAuthorizedUsers();
        } else {
            getBannedUsers();
        }
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

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject((AppCompatActivity) this);
        return this;
    }

    private void getAuthorizedUsers() {
        Query startAt = FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).orderByChild("cris").startAt(this.userRolesId);
        StringBuilder sb = new StringBuilder();
        sb.append(this.userRolesId);
        sb.append("");
        startAt.endAt(sb.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatUserModel chatUserModel;
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        try {
                            chatUserModel = (ChatUserModel) dataSnapshot2.getValue(ChatUserModel.class);
                        } catch (Exception unused) {
                            chatUserModel = new ChatUserModel();
                            chatUserModel.parseSnapshot(dataSnapshot2);
                        }
                        chatUserModel.uid = dataSnapshot2.getKey();
                        if (!TextUtils.equals(chatUserModel.uid, FirebaseAuth.getInstance().getCurrentUser().getUid()) && ChatAdminUserListActivity.this.validateUserModel(chatUserModel)) {
                            ChatAdminUserListActivity.this.setUserAdapter(chatUserModel);
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean validateUserModel(ChatUserModel chatUserModel) {
        return (chatUserModel == null || chatUserModel.getName() == null || chatUserModel.getName().isEmpty()) ? false : true;
    }

    /* access modifiers changed from: private */
    public void getUserFromFirebase(String str) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(str).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatUserModel chatUserModel;
                try {
                    chatUserModel = (ChatUserModel) dataSnapshot.getValue(ChatUserModel.class);
                } catch (Exception unused) {
                    chatUserModel = new ChatUserModel();
                    chatUserModel.parseSnapshot(dataSnapshot);
                }
                chatUserModel.uid = dataSnapshot.getKey();
                ChatAdminUserListActivity.this.setUserAdapter(chatUserModel);
                ChatAdminUserListActivity.this.checkAdapterIsEmpty(new UserListEmptyEvent());
            }
        });
    }

    private void getBannedUsers() {
        FirebaseDatabase.getInstance().getReference().child(this.databasePath).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashMap = (HashMap) dataSnapshot.getValue();
                if (hashMap != null) {
                    for (Entry key : hashMap.entrySet()) {
                        ChatAdminUserListActivity.this.getUserFromFirebase((String) key.getKey());
                    }
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getToolbar().inflateMenu(R.menu.search_option_menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_search) {
            this.interstitialAdsUtil.checkInterstitialAds(new Intent(this, ChatUserListActivity.class));
        }
        return true;
    }
}
