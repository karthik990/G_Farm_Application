package com.mobiroller.activities.chat;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.chat.ChatReportAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ReportModel;
import java.util.ArrayList;
import java.util.List;

public class ChatAdminReportActivity extends AveActivity {
    /* access modifiers changed from: private */
    public ChatReportAdapter adapter;
    private List<ReportModel> dataList;
    @BindView(2131363022)
    RecyclerView recyclerView;
    private ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_recyclerview_with_toolbar);
        ButterKnife.bind((Activity) this);
        this.toolbarHelper = new ToolbarHelper(UtilManager.sharedPrefHelper());
        this.toolbarHelper.setToolbarTitle(this, getResources().getString(R.string.chat_reports));
        this.dataList = new ArrayList();
        this.adapter = new ChatReportAdapter(this, this.recyclerView);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getReports();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject((AppCompatActivity) this);
        return this;
    }

    public void setUserAdapter(ReportModel reportModel) {
        if (reportModel != null) {
            this.progressViewHelper.dismiss();
            addItemToAdapter(reportModel);
        }
    }

    public void addItemToAdapter(ReportModel reportModel) {
        if (this.adapter.getItemCount() == 0) {
            this.adapter.addItem(reportModel);
        } else {
            this.adapter.checkItem(reportModel);
        }
    }

    public void getReports() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_CHAT_REPORTS).addChildEventListener(new ChildEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String str) {
            }

            public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                ReportModel reportModel = (ReportModel) dataSnapshot.getValue(ReportModel.class);
                if (reportModel.getVersion().equals("v1")) {
                    reportModel.key = dataSnapshot.getKey();
                    ChatAdminReportActivity.this.setUserAdapter(reportModel);
                }
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                ReportModel reportModel = (ReportModel) dataSnapshot.getValue(ReportModel.class);
                if (reportModel.getVersion().equals("v1")) {
                    reportModel.key = dataSnapshot.getKey();
                    ChatAdminReportActivity.this.setUserAdapter(reportModel);
                }
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ReportModel reportModel = (ReportModel) dataSnapshot.getValue(ReportModel.class);
                if (reportModel.getVersion().equals("v1")) {
                    reportModel.key = dataSnapshot.getKey();
                    ChatAdminReportActivity.this.adapter.removeItem(reportModel);
                }
            }
        });
    }
}
