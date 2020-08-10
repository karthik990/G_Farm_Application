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
import com.mobiroller.adapters.chat.ChatLogAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.LogModel;
import com.mobiroller.views.EndlessRecyclerViewScrollListenerRecyclerView;
import java.util.ArrayList;

public class ChatAdminLogActivity extends AveActivity {
    /* access modifiers changed from: private */
    public ChatLogAdapter adapter;
    /* access modifiers changed from: private */
    public String lastKey = null;
    /* access modifiers changed from: private */
    public boolean paginationIsFirst = true;
    @BindView(2131363022)
    RecyclerView recyclerView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_recyclerview_with_toolbar);
        ButterKnife.bind((Activity) this);
        new ToolbarHelper(UtilManager.sharedPrefHelper()).setToolbarTitle(this, getResources().getString(R.string.chat_logs));
        new ArrayList();
        this.adapter = new ChatLogAdapter(this, this.recyclerView);
        this.recyclerView.setAdapter(this.adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListenerRecyclerView(linearLayoutManager) {
            public void onLoadMore(int i, int i2) {
                ChatAdminLogActivity.this.loadMoreLogs();
            }
        });
        getLogs();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject((AppCompatActivity) this);
        return this;
    }

    public void setUserAdapter(LogModel logModel) {
        if (logModel != null) {
            this.progressViewHelper.dismiss();
            addItemToAdapter(logModel);
        }
    }

    public void addItemToAdapter(LogModel logModel) {
        if (this.adapter.getItemCount() == 0) {
            this.adapter.addItem(logModel);
        } else {
            this.adapter.checkItem(logModel);
        }
    }

    public void getLogs() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_LOGS).orderByKey().limitToLast(20).addChildEventListener(new ChildEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String str) {
            }

            public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                LogModel logModel = (LogModel) dataSnapshot.getValue(LogModel.class);
                if (logModel.getVersion().equals("v2")) {
                    logModel.key = dataSnapshot.getKey();
                    if (ChatAdminLogActivity.this.lastKey == null) {
                        ChatAdminLogActivity.this.lastKey = dataSnapshot.getKey();
                    }
                    ChatAdminLogActivity.this.setUserAdapter(logModel);
                }
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                LogModel logModel = (LogModel) dataSnapshot.getValue(LogModel.class);
                if (logModel.getVersion().equals("v2")) {
                    logModel.key = dataSnapshot.getKey();
                    ChatAdminLogActivity.this.setUserAdapter(logModel);
                }
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
                LogModel logModel = (LogModel) dataSnapshot.getValue(LogModel.class);
                if (logModel.getVersion().equals("v2")) {
                    logModel.key = dataSnapshot.getKey();
                    ChatAdminLogActivity.this.adapter.removeItem(logModel);
                }
            }
        });
    }

    public void loadMoreLogs() {
        if (this.lastKey != null) {
            this.paginationIsFirst = true;
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_LOGS).orderByKey().endAt(this.lastKey).limitToLast(20).addChildEventListener(new ChildEventListener() {
                public void onCancelled(DatabaseError databaseError) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String str) {
                }

                public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                    LogModel logModel = (LogModel) dataSnapshot.getValue(LogModel.class);
                    if (logModel.getVersion().equals("v2")) {
                        logModel.key = dataSnapshot.getKey();
                        if (!ChatAdminLogActivity.this.lastKey.equals(logModel.key)) {
                            if (ChatAdminLogActivity.this.paginationIsFirst) {
                                ChatAdminLogActivity.this.lastKey = dataSnapshot.getKey();
                                ChatAdminLogActivity.this.paginationIsFirst = false;
                            }
                            ChatAdminLogActivity.this.setUserAdapter(logModel);
                        }
                    }
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                    LogModel logModel = (LogModel) dataSnapshot.getValue(LogModel.class);
                    if (logModel.getVersion().equals("v2")) {
                        logModel.key = dataSnapshot.getKey();
                        ChatAdminLogActivity.this.setUserAdapter(logModel);
                    }
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    LogModel logModel = (LogModel) dataSnapshot.getValue(LogModel.class);
                    if (logModel.getVersion().equals("v2")) {
                        logModel.key = dataSnapshot.getKey();
                        ChatAdminLogActivity.this.adapter.removeItem(logModel);
                    }
                }
            });
        }
    }
}
