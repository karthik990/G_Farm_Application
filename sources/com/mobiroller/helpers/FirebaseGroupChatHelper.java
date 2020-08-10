package com.mobiroller.helpers;

import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.models.chat.GroupChatModel;
import com.mobiroller.models.chat.GroupChatUserModel;
import com.mobiroller.models.chat.MessageModel;

public class FirebaseGroupChatHelper {
    public static final String TAG = "FirebaseGroupChatHelper";
    /* access modifiers changed from: private */
    public Context context;
    private SharedPrefHelper sharedPrefHelper;

    public FirebaseGroupChatHelper(Context context2) {
        this.context = context2;
        this.sharedPrefHelper = new SharedPrefHelper(context2);
    }

    public void sendMessageToGroupChat(final GroupChatModel groupChatModel, final MessageModel messageModel, final String str) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_CHAT_ROOMS).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_CHAT_ROOMS).child(groupChatModel.getChatId()).push().setValue(messageModel);
                FirebaseGroupChatHelper.this.addGroupRoomToMessaging(groupChatModel);
                for (int i = 0; i < groupChatModel.getUserList().size(); i++) {
                    FirebaseGroupChatHelper.this.sendPushNotificationToGroup(groupChatModel.getGroupName(), messageModel.getMessage(), groupChatModel.getChatId(), new SharedPrefHelper(FirebaseGroupChatHelper.this.context).getFirebaseToken(), ((GroupChatUserModel) groupChatModel.getUserList().get(i)).getFirebaseToken(), str);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendPushNotificationToGroup(String str, String str2, String str3, String str4, String str5, String str6) {
        if (str5 != null) {
            FcmNotificationBuilder.initialize().title(str).message(str2).context(this.context).username(str3).uid(FirebaseAuth.getInstance().getCurrentUser().getUid()).screenId(str6).firebaseToken(str4).receiverFirebaseToken(str5).send();
        }
    }

    public void addGroupRoomToMessaging(GroupChatModel groupChatModel) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_MESSAGING_USERS).child(groupChatModel.getChatId()).setValue(groupChatModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                task.isSuccessful();
            }
        });
    }
}
