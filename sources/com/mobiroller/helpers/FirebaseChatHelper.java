package com.mobiroller.helpers;

import android.content.Context;
import androidx.exifinterface.media.ExifInterface;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatDialogUserEvent;
import com.mobiroller.models.chat.ChatMetaModel;
import com.mobiroller.models.chat.ChatModel;
import com.mobiroller.models.chat.ChatUserDetails;
import com.mobiroller.models.chat.ChatUserModel;
import com.mobiroller.models.chat.ChatUserProfileEvent;
import com.mobiroller.models.chat.LogModel;
import com.mobiroller.models.chat.MessageModel;
import com.mobiroller.models.chat.ReportModel;
import com.mobiroller.models.chat.UserValueListenerModel;
import com.mobiroller.models.events.ChatAccountEvent;
import com.mobiroller.models.events.DialogArchivedEvent;
import com.mobiroller.models.events.DialogRemovedEvent;
import com.mobiroller.models.events.RemoveDialogEvent;
import com.mobiroller.models.events.ReportSentEvent;
import com.mobiroller.models.events.UnarchiveDialogEvent;
import com.mobiroller.models.events.UserBlockEvent;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.models.response.UserProfileElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import org.greenrobot.eventbus.EventBus;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public class FirebaseChatHelper {
    public static final String TAG = "FirebaseChatHelper";
    private List<UserValueListenerModel> listenerModels = new ArrayList();
    /* access modifiers changed from: private */
    public Context mContext;
    private ValueEventListener mGetMe;
    private NetworkHelper mNetworkHelper;
    /* access modifiers changed from: private */
    public SharedPrefHelper mSharedPrefHelper;
    private ValueEventListener mUserStatusListener;
    private ValueEventListener mUserValueEventListener;

    public FirebaseChatHelper(Context context) {
        this.mNetworkHelper = new NetworkHelper(context);
        this.mContext = context;
        this.mSharedPrefHelper = UtilManager.sharedPrefHelper();
    }

    public void addUser(UserLoginResponse userLoginResponse) {
        updateUserFullName();
        updateUserEmail();
        updateUserProfilImage();
        updateUserUid();
        updateUserMeta(userLoginResponse);
        updateUserFirebaseToken();
        updateUserRoles(userLoginResponse);
    }

    public void updateUser(UserLoginResponse userLoginResponse) {
        updateUserMeta(userLoginResponse);
        updateUserFirebaseToken();
        updateUserUid();
        updateUserRoles(userLoginResponse);
    }

    public void updateUserFullName() {
        String str = "n";
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USER_INFO).child(str).setValue(UserHelper.getUserName());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String str2 = ChatConstants.ARG_USER_LIST;
        reference.child(str2).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str).setValue(UserHelper.getUserName().toLowerCase());
        FirebaseDatabase.getInstance().getReference().child(str2).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USER_LIST_NAME_V1).setValue(UserHelper.getUserName());
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_SEARCH_INDEX).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str).setValue(UserHelper.getUserName().toLowerCase());
    }

    public void updateUserEmail() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USER_INFO).child(ChatConstants.ARG_USER_INFO_EMAIL).setValue(UserHelper.getUserEmail());
    }

    public void updateUserProfilImage() {
        String str = "i";
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USER_INFO).child(str).setValue(UserHelper.getUserImageUrl());
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str).setValue(UserHelper.getUserImageUrl());
    }

    private void updateUserUid() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USER_INFO).child(ChatConstants.ARG_USER_INFO_UID).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void updateUserRoles(final UserLoginResponse userLoginResponse) {
        if (userLoginResponse.communityPermissionTypeId == null || userLoginResponse.communityRoleId == null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("roles").child(ChatConstants.ARG_USERS_ROLES_SUPER_USER).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onCancelled(DatabaseError databaseError) {
                }

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && ((Boolean) dataSnapshot.getValue(Boolean.class)).booleanValue()) {
                        FirebaseChatHelper.this.updateChatPermissionAndChatRole(userLoginResponse.communityPermissionTypeId, userLoginResponse.communityRoleId);
                    }
                }
            });
        } else {
            updateChatPermissionAndChatRole(userLoginResponse.communityPermissionTypeId, userLoginResponse.communityRoleId);
        }
        String str = userLoginResponse.communityPermissionTypeId;
        String str2 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
        if (str == null || !userLoginResponse.communityPermissionTypeId.equalsIgnoreCase(str2)) {
            updateUserSuperUserRole(false);
        } else {
            updateUserSuperUserRole(true);
        }
        updateUserRole(userLoginResponse.roleId);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ris").setValue(userLoginResponse.roleId);
        if (userLoginResponse.communityPermissionTypeId == null) {
            return;
        }
        if (userLoginResponse.communityPermissionTypeId.equalsIgnoreCase(str2) || userLoginResponse.communityPermissionTypeId.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_AUTHORITIES_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseInstanceId.getInstance().getToken());
        }
    }

    private void updateUserSuperUserRole(boolean z) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("roles").child(ChatConstants.ARG_USERS_ROLES_SUPER_USER).setValue(Boolean.valueOf(z));
    }

    /* access modifiers changed from: private */
    public void updateChatPermissionAndChatRole(String str, String str2) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String str3 = ChatConstants.ARG_USERS;
        String str4 = "roles";
        reference.child(str3).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str4).child(ChatConstants.ARG_USERS_ROLES_CHAT_PERMISSION_ID).setValue(str);
        String str5 = "cris";
        FirebaseDatabase.getInstance().getReference().child(str3).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str4).child(str5).setValue(str2);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str5).setValue(str2);
    }

    private void updateUserRole(String str) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("roles").child("ris").setValue(str);
    }

    private void updateUserFirebaseToken() {
        if (FirebaseInstanceId.getInstance() != null && FirebaseInstanceId.getInstance().getToken() != null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USERS_FIREBASE_TOKEN).setValue(FirebaseInstanceId.getInstance().getToken());
        }
    }

    private void updateUserMeta(UserLoginResponse userLoginResponse) {
        boolean z;
        if (userLoginResponse != null) {
            this.mSharedPrefHelper.setUserLoginModel(userLoginResponse);
            if (!(FirebaseAuth.getInstance() == null || FirebaseAuth.getInstance().getCurrentUser() == null)) {
                int i = 0;
                if (userLoginResponse.profileValues != null && userLoginResponse.profileValues != null && userLoginResponse.profileValues.size() == userLoginResponse.profileValues.size()) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= userLoginResponse.profileValues.size()) {
                            break;
                        } else if (!((UserProfileElement) this.mSharedPrefHelper.getUserLoginModel().profileValues.get(i2)).equals(userLoginResponse.profileValues.get(i2))) {
                            z = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                z = false;
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    String userImageUrl = UserHelper.getUserImageUrl();
                    String str = TtmlNode.TAG_IMAGE;
                    arrayList.add(new ChatMetaModel(str, userImageUrl, str));
                    List<UserProfileElement> list = userLoginResponse.profileValues;
                    while (i < list.size()) {
                        UserProfileElement userProfileElement = (UserProfileElement) list.get(i);
                        String str2 = userProfileElement.type;
                        if (str2.equalsIgnoreCase("userName") || str2.equalsIgnoreCase("nameSurname") || str2.equalsIgnoreCase("password") || str2.equalsIgnoreCase("photo")) {
                            list.remove(i);
                            i--;
                        } else {
                            arrayList.add(new ChatMetaModel(userProfileElement.type, userProfileElement.value, userProfileElement.title));
                        }
                        i++;
                    }
                    if (!arrayList.isEmpty()) {
                        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USERS_META).setValue(arrayList);
                    }
                }
            }
        }
    }

    private void addRoomToMessaging(ChatModel chatModel) {
        chatModel.setReceiverRead(false);
        DatabaseReference child = FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId);
        String str = ChatConstants.ARG_MESSAGING_USERS;
        child.child(str).child(chatModel.getReceiverUid()).child(chatModel.getSenderUid()).setValue(chatModel);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(str).child(chatModel.getSenderUid()).child(chatModel.getReceiverUid()).setValue(chatModel);
        DatabaseReference child2 = FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId);
        String str2 = ChatConstants.ARG_MESSAGING_USER_LIST;
        DatabaseReference child3 = child2.child(str2).child(chatModel.getSenderUid()).child(chatModel.getReceiverUid());
        Boolean valueOf = Boolean.valueOf(true);
        child3.setValue(valueOf);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(str2).child(chatModel.getReceiverUid()).child(chatModel.getSenderUid()).setValue(valueOf);
    }

    public void sendMessageToFirebaseUser(MessageModel messageModel, String str, String str2, String str3, String str4, String str5, String str6) {
        MessageModel messageModel2 = messageModel;
        DatabaseReference child = FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId);
        String str7 = ChatConstants.ARG_CHAT_ROOMS;
        DatabaseReference child2 = child.child(str7);
        StringBuilder sb = new StringBuilder();
        sb.append(messageModel.getReceiverUid());
        String str8 = EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR;
        sb.append(str8);
        sb.append(messageModel.getSenderUid());
        child2.child(sb.toString()).push().setValue(messageModel);
        addRoomToMessaging(new ChatModel(messageModel));
        sendPushNotificationToReceiver(str3, messageModel.getMessage(), messageModel.getSenderUid(), messageModel.getReceiverUid(), UtilManager.sharedPrefHelper().getFirebaseToken(), str, str6);
        DatabaseReference child3 = FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(str7);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(messageModel.getSenderUid());
        sb2.append(str8);
        sb2.append(messageModel.getReceiverUid());
        child3.child(sb2.toString()).push().setValue(messageModel);
    }

    private void sendPushNotificationToReceiver(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (str6 != null) {
            FcmNotificationBuilder.initialize().context(this.mContext).title(str).message(str2).username(str).uid(str3).screenId(str7).receiverUid(str4).firebaseToken(str5).receiverFirebaseToken(str6).send();
        }
    }

    public void sendPushNotificationInfo(String str, String str2, String str3, int i) {
        if (str3 != null) {
            FcmNotificationBuilder.initialize().context(this.mContext).title(str).notificationType(ChatConstants.ARG_USER_INFO).infoType(i).receiverUid(str2).receiverFirebaseToken(str3).send();
        }
    }

    /* access modifiers changed from: private */
    public void sendReportNotificationAllAuthorizedUsers() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_AUTHORITIES_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashMap = (HashMap) dataSnapshot.getValue();
                if (hashMap != null) {
                    for (Entry entry : hashMap.entrySet()) {
                        FirebaseChatHelper firebaseChatHelper = FirebaseChatHelper.this;
                        firebaseChatHelper.sendPushNotificationInfo(firebaseChatHelper.mContext.getString(R.string.new_report_push), (String) entry.getKey(), (String) entry.getValue(), 1);
                    }
                }
            }
        });
    }

    public void getUserFromFirebase(String str, boolean z) {
        int i = 0;
        while (true) {
            if (i < this.listenerModels.size()) {
                if (str.equalsIgnoreCase(((UserValueListenerModel) this.listenerModels.get(i)).uid) && z) {
                    removeUserListener(str);
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        this.listenerModels.add(new UserValueListenerModel(FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(str).addValueEventListener(new ValueEventListener() {
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
                EventBus.getDefault().post(new ChatDialogUserEvent(chatUserModel));
            }
        }), str));
    }

    public void getUserFromFirebaseProfile(String str, boolean z) {
        int i = 0;
        while (true) {
            if (i < this.listenerModels.size()) {
                if (str.equalsIgnoreCase(((UserValueListenerModel) this.listenerModels.get(i)).uid) && z) {
                    removeUserListener(str);
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        this.listenerModels.add(new UserValueListenerModel(FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(str).addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatUserDetails chatUserDetails;
                try {
                    chatUserDetails = (ChatUserDetails) dataSnapshot.getValue(ChatUserDetails.class);
                } catch (Exception unused) {
                    chatUserDetails = new ChatUserDetails();
                    chatUserDetails.parseSnapshot(dataSnapshot);
                }
                EventBus.getDefault().post(new ChatUserProfileEvent(chatUserDetails));
            }
        }), str));
    }

    public void removeUsersListeners() {
        for (int i = 0; i < this.listenerModels.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(((UserValueListenerModel) this.listenerModels.get(i)).uid).removeEventListener(((UserValueListenerModel) this.listenerModels.get(i)).listener);
        }
    }

    private void removeUserListener(String str) {
        for (int i = 0; i < this.listenerModels.size(); i++) {
            if (str.equalsIgnoreCase(((UserValueListenerModel) this.listenerModels.get(i)).uid)) {
                FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(((UserValueListenerModel) this.listenerModels.get(i)).uid).removeEventListener(((UserValueListenerModel) this.listenerModels.get(i)).listener);
                return;
            }
        }
    }

    public void setUserStatus() {
        String str = "o";
        final DatabaseReference child = FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USER_INFO).child(str);
        final DatabaseReference child2 = FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str);
        this.mUserStatusListener = FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (((Boolean) dataSnapshot.getValue(Boolean.class)).booleanValue()) {
                    String str = "f";
                    child.onDisconnect().setValue(str);
                    child2.onDisconnect().setValue(str);
                    String str2 = "n";
                    child.setValue(str2);
                    child2.setValue(str2);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Listener was cancelled at .info/connected");
            }
        });
    }

    public void removeUserStatusListener() {
        if (this.mUserStatusListener != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().removeEventListener(this.mUserStatusListener);
        }
    }

    public String getRoomType(String str, String str2) {
        int hashCode = str.hashCode();
        int hashCode2 = str2.hashCode();
        String str3 = EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR;
        if (hashCode > hashCode2) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str3);
            sb.append(str2);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str3);
        sb2.append(str);
        return sb2.toString();
    }

    public void removeMessagesFromChat(String str, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_CHAT_ROOMS).child(str).child((String) list.get(i)).removeValue();
        }
    }

    public void setLastMessage(final String str, final ChatUserDetails chatUserDetails) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_CHAT_ROOMS).child(str).orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatModel chatModel;
                boolean exists = dataSnapshot.exists();
                String str = ChatConstants.ARG_MESSAGING_USERS;
                if (exists) {
                    MessageModel messageModel = (MessageModel) dataSnapshot.getValue(MessageModel.class);
                    if (messageModel != null && messageModel.key != null) {
                        messageModel.key = dataSnapshot.getKey();
                        if (messageModel.getSenderUid().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            chatModel = new ChatModel(messageModel);
                        } else {
                            chatModel = new ChatModel(messageModel);
                        }
                        FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(str).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(chatModel.getRealReceiverUid()).setValue(chatModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(Task<Void> task) {
                                task.isSuccessful();
                            }
                        });
                        return;
                    }
                    return;
                }
                FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_CHAT_ROOMS).child(str).removeValue();
                FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(str).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(chatUserDetails.getUserInfo().uid).removeValue();
            }
        });
    }

    public void archiveDialog(final String str) {
        if (checkInternetConnection()) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_MESSAGING_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str).child("a").setValue(Boolean.valueOf(true)).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    EventBus.getDefault().post(new RemoveDialogEvent(str));
                    EventBus.getDefault().post(new DialogArchivedEvent());
                }
            });
        }
    }

    public void unarchiveDialog(final String str) {
        if (checkInternetConnection()) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_MESSAGING_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str).child("a").setValue(Boolean.valueOf(false)).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    EventBus.getDefault().post(new UnarchiveDialogEvent(str));
                }
            });
        }
    }

    public void deleteDialog(final String str) {
        if (checkInternetConnection()) {
            DatabaseReference child = FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_CHAT_ROOMS);
            StringBuilder sb = new StringBuilder();
            sb.append(FirebaseAuth.getInstance().getCurrentUser().getUid());
            sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            sb.append(str);
            child.child(sb.toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    EventBus.getDefault().post(new DialogRemovedEvent());
                    FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_MESSAGING_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(Task<Void> task) {
                            EventBus.getDefault().post(new RemoveDialogEvent(str));
                            FirebaseDatabase.getInstance().getReference().child(ChatConstants.chatScreenId).child(ChatConstants.ARG_MESSAGING_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(str).removeValue();
                        }
                    });
                }
            });
        }
    }

    public void deleteReportDialog(String str) {
        if (checkInternetConnection()) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_CHAT_REPORTS).child(str).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                }
            });
        }
    }

    public void sendReport(ReportModel reportModel) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_CHAT_REPORTS).push().setValue(reportModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                EventBus.getDefault().post(new ReportSentEvent());
                FirebaseChatHelper.this.sendReportNotificationAllAuthorizedUsers();
            }
        });
    }

    public void getMe() {
        this.mGetMe = FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatUserDetails chatUserDetails;
                try {
                    chatUserDetails = (ChatUserDetails) dataSnapshot.getValue(ChatUserDetails.class);
                } catch (Exception unused) {
                    chatUserDetails = new ChatUserDetails();
                    chatUserDetails.parseSnapshot(dataSnapshot);
                }
                EventBus.getDefault().post(new ChatAccountEvent(chatUserDetails));
            }
        });
    }

    public void removeGetMe() {
        FirebaseDatabase.getInstance().getReference().removeEventListener(this.mGetMe);
    }

    public boolean checkInternetConnection() {
        if (this.mNetworkHelper.isConnected()) {
            return true;
        }
        new Builder(this.mContext).title((int) R.string.connection_error).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.OK).show();
        return false;
    }

    public void logActivity(LogModel logModel) {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_LOGS).push().setValue(logModel);
    }

    public void checkDatabaseVersionExist() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.CHAT_MINIMUM_REQUIRED_VERSION_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    FirebaseChatHelper.this.setDatabaseVersion();
                } else {
                    FirebaseDatabase.getInstance().getReference().child(ChatConstants.CHAT_MINIMUM_REQUIRED_VERSION_PATH).addValueEventListener(new ValueEventListener() {
                        public void onCancelled(DatabaseError databaseError) {
                        }

                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int intValue = Integer.valueOf(dataSnapshot.getValue().toString()).intValue();
                            if (intValue > 1) {
                                FirebaseChatHelper.this.mSharedPrefHelper.setIsChatVersionSupported(false);
                            } else if (intValue == 1) {
                                FirebaseChatHelper.this.mSharedPrefHelper.setIsChatVersionSupported(true);
                            } else {
                                FirebaseChatHelper.this.mSharedPrefHelper.setIsChatVersionSupported(true);
                                FirebaseChatHelper.this.setDatabaseVersion();
                            }
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void setDatabaseVersion() {
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.CHAT_MINIMUM_REQUIRED_VERSION_PATH).setValue(Integer.valueOf(1));
    }

    private void checkUserNameIsExist(final String str, final String str2) {
        if (str != null && str2 != null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).orderByChild("un").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onCancelled(DatabaseError databaseError) {
                }

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        FirebaseChatHelper.this.setUserName(str2, str);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void setUserName(String str, String str2) {
        String str3 = "un";
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(str).child(str3).setValue(str2);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(str).child(ChatConstants.ARG_USER_INFO).child(str3).setValue(str2);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_SEARCH_INDEX).child(str).child(str3).setValue(str2);
    }

    public void generateUserName(String str, String str2) {
        String str3 = (str == null || str.isEmpty() || str.length() < 3) ? "user" : str.toLowerCase().split("\\s+")[0];
        int nextInt = new Random().nextInt(10000) + 10000;
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append(String.valueOf(nextInt));
        checkUserNameIsExist(sb.toString(), str2);
    }

    public void setUserNameManually(String str, String str2) {
        checkUserNameIsExist(str, str2);
    }

    public void blockUser(final String str, final int i) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String str2 = ChatConstants.ARG_USERS;
        reference.child(str2).child(str).child(ChatConstants.ARG_USERS_BLOCKED_BY_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseDatabase.getInstance().getReference().child(str2).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USERS_BLOCKED_LIST).child(str).setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                EventBus.getDefault().post(new UserBlockEvent(str, true, i));
            }
        });
    }

    public void unBlockUser(final String str, final int i) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String str2 = ChatConstants.ARG_USERS;
        reference.child(str2).child(str).child(ChatConstants.ARG_USERS_BLOCKED_BY_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
        FirebaseDatabase.getInstance().getReference().child(str2).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USERS_BLOCKED_LIST).child(str).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                EventBus.getDefault().post(new UserBlockEvent(str, false, i));
            }
        });
    }
}
