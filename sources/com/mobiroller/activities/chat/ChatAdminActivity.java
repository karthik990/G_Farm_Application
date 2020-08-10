package com.mobiroller.activities.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.chat.ChatAdminAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatAdminModel;
import com.mobiroller.models.chat.ChatRoleModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ChatAdminActivity extends AveActivity {
    private ChatAdminAdapter adapter;
    @Inject
    ApiRequestManager apiRequestManager;
    private List<ChatAdminModel> dataList;
    private FirebaseChatHelper firebaseChatHelper;
    @BindView(2131363022)
    RecyclerView recyclerView;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    private ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_recyclerview_with_toolbar);
        ButterKnife.bind((Activity) this);
        this.firebaseChatHelper = new FirebaseChatHelper(getApplicationContext());
        this.toolbarHelper = new ToolbarHelper(UtilManager.sharedPrefHelper());
        this.dataList = new ArrayList();
        String str = "panel";
        if (getIntent().getExtras().getString(str).equalsIgnoreCase(ChatConstants.ADMIN_PANEL)) {
            loadAdminPanel();
        } else if (getIntent().getExtras().getString(str).equalsIgnoreCase(ChatConstants.ADMIN_ROLE_PANEL)) {
            loadAdminRolePanel();
        }
        this.adapter = new ChatAdminAdapter(this.dataList, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String str2 = "to";
        if (getIntent().hasExtra(str2) && getIntent().getStringExtra(str2).equalsIgnoreCase("report")) {
            startActivity(new Intent(this, ChatAdminReportActivity.class));
        }
    }

    public void loadAdminPanel() {
        this.toolbarHelper.setToolbarTitle(this, getResources().getString(R.string.management_panel));
        this.dataList.add(new ChatAdminModel(getString(R.string.blocked_users), getString(R.string.blocked_users_description), (int) R.drawable.ic_block_black_24dp, new Intent(this, ChatAdminUserListActivity.class)));
        this.dataList.add(new ChatAdminModel(getString(R.string.chat_role_management), getString(R.string.chat_role_management_description), (int) R.drawable.ic_supervisor_account_black_24dp, new Intent(this, ChatAdminActivity.class)));
        this.dataList.add(new ChatAdminModel(getString(R.string.chat_reports), getString(R.string.chat_reports_description), (int) R.drawable.ic_report_black_24dp, new Intent(this, ChatAdminReportActivity.class)));
        if (this.sharedPrefHelper.getUserIsChatAdmin()) {
            this.dataList.add(new ChatAdminModel(getString(R.string.chat_logs), getString(R.string.chat_logs_description), (int) R.drawable.ic_receipt_black_24dp, new Intent(this, ChatAdminLogActivity.class)));
        }
    }

    public void loadAdminRolePanel() {
        this.toolbarHelper.setToolbarTitle(this, getResources().getString(R.string.chat_role_management));
        for (int i = 0; i < MobiRollerApplication.getChatRoleModels().size(); i++) {
            ChatRoleModel chatRoleModel = (ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i);
            List<ChatAdminModel> list = this.dataList;
            ChatAdminModel chatAdminModel = new ChatAdminModel(chatRoleModel.getChatRoleName(), chatRoleModel.getChatRoleDescription(), chatRoleModel.getRibbonImage(), new Intent(this, ChatAdminUserListActivity.class), chatRoleModel.getId());
            list.add(chatAdminModel);
        }
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }
}
