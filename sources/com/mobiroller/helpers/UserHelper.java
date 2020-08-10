package com.mobiroller.helpers;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder;
import com.google.android.gms.common.api.Scope;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.models.events.ShoppingCartCountEvent;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.util.ECommerceUtil;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import p043io.paperdb.Paper;

public class UserHelper {
    private static String USER_BOOK_KEY = "userBookKey";
    private static GoogleSignInClient mGoogleSignInClient = null;
    private static String userBlockedByUserListKey = "userBlockedByUserListKey";
    private static String userBlockedUserListKey = "userBlockedUserListKey";
    private static String userEmailKey = "userEmailKey";
    private static String userGoogleTokenKey = "userGoogleToken";
    private static String userIdKey = "userIdKey";
    private static String userImageUrlKey = "userImageUrlKey";
    private static String userLoginResponseKey = "userLoginResponseKey";
    private static String userNameKey = "userName";
    private static String userSessionTokenKey = "userSessionTokenKey";

    public static void logout(SharedPrefHelper sharedPrefHelper, AppCompatActivity appCompatActivity) {
        removeAllUserData(sharedPrefHelper, appCompatActivity);
        EventBus.getDefault().post(new LoginEvent());
        ECommerceUtil.badgeCount = 0;
        EventBus.getDefault().post(new ShoppingCartCountEvent(0));
    }

    public static void logout(AppCompatActivity appCompatActivity) {
        removeAllUserData(UtilManager.sharedPrefHelper(), appCompatActivity);
        EventBus.getDefault().post(new LoginEvent());
        ECommerceUtil.badgeCount = 0;
        EventBus.getDefault().post(new ShoppingCartCountEvent(0));
    }

    private static void removeAllUserData(SharedPrefHelper sharedPrefHelper, AppCompatActivity appCompatActivity) {
        sharedPrefHelper.setUserLoginStatus(false);
        sharedPrefHelper.setUserRole("0");
        String str = "";
        sharedPrefHelper.setUserId(str);
        sharedPrefHelper.setFirebaseToken(str);
        sharedPrefHelper.setGoogleSignInAccount(null);
        Paper.book(USER_BOOK_KEY).delete(userGoogleTokenKey);
        Paper.book(USER_BOOK_KEY).delete(userIdKey);
        Paper.book(USER_BOOK_KEY).delete(userSessionTokenKey);
        Paper.book(USER_BOOK_KEY).delete(userImageUrlKey);
        Paper.book(USER_BOOK_KEY).delete(userNameKey);
        Paper.book(USER_BOOK_KEY).delete(userLoginResponseKey);
        Paper.book(USER_BOOK_KEY).delete(userEmailKey);
        signOut(appCompatActivity);
        try {
            if (!(FirebaseAuth.getInstance() == null || FirebaseAuth.getInstance().getCurrentUser() == null)) {
                logoutFirebase(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
            FirebaseAuth.getInstance().signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void signOut(Activity activity) {
        try {
            mGoogleSignInClient = GoogleSignIn.getClient(activity, new Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope("https://www.googleapis.com/auth/youtube.readonly"), new Scope("https://www.googleapis.com/auth/youtube.force-ssl")).build());
            mGoogleSignInClient.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void logoutFirebase(String str) {
        String str2 = "o";
        String str3 = "f";
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(str).child(ChatConstants.ARG_USER_INFO).child(str2).setValue(str3);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(str).child(str2).setValue(str3);
    }

    public static void saveLoginCredentials(AppCompatActivity appCompatActivity, UserLoginResponse userLoginResponse) {
        SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        if (!(userLoginResponse.communityRoleId == null || userLoginResponse.communityPermissionTypeId == null)) {
            if (userLoginResponse.communityPermissionTypeId.equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
                sharedPrefHelper.setUserIsChatAdmin(true);
                sharedPrefHelper.setUserIsChatMod(false);
                sharedPrefHelper.setUserIsChatSuperUser(true);
            } else if (userLoginResponse.communityPermissionTypeId.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
                sharedPrefHelper.setUserIsChatMod(false);
                sharedPrefHelper.setUserIsChatAdmin(true);
                sharedPrefHelper.setUserIsChatSuperUser(false);
            } else {
                sharedPrefHelper.setUserIsChatAdmin(false);
                sharedPrefHelper.setUserIsChatMod(false);
                sharedPrefHelper.setUserIsChatSuperUser(false);
            }
        }
        sharedPrefHelper.setUserId(userLoginResponse.f1505id);
        for (UserProfileElement userProfileElement : userLoginResponse.profileValues) {
            Editor edit = sharedPrefHelper.getSharedPrefs(appCompatActivity).edit();
            StringBuilder sb = new StringBuilder();
            sb.append(sharedPrefHelper.getUserId());
            sb.append("_id_");
            sb.append(userProfileElement.f2188id);
            edit.putString(sb.toString(), userProfileElement.value).apply();
            if (userProfileElement.type.equalsIgnoreCase("nameSurname")) {
                setUserName(userProfileElement.value);
            }
            if (userProfileElement.type.equalsIgnoreCase("photo")) {
                setUserImageUrl(userProfileElement.value);
            }
        }
        sharedPrefHelper.setUserLoginStatus(true);
        sharedPrefHelper.setUserRole(userLoginResponse.roleId);
        if (userLoginResponse.sessionToken != null) {
            setUserSessionToken(userLoginResponse.sessionToken);
        }
        setUserId(userLoginResponse.f1505id);
        setUserLoginResponse(userLoginResponse);
        setUserEmail(userLoginResponse.email);
        EventBus.getDefault().post(new LoginEvent());
    }

    public static boolean getUserLoginStatusForGoogle(SharedPrefHelper sharedPrefHelper) {
        return sharedPrefHelper.getGoogleSignInAccount() != null;
    }

    public static String getGoogleSignInToken() {
        return (String) Paper.book(USER_BOOK_KEY).read(userGoogleTokenKey);
    }

    public static void setGoogleSignInToken(String str) {
        Paper.book(USER_BOOK_KEY).write(userGoogleTokenKey, str);
    }

    public static String getUserId() {
        return (String) Paper.book(USER_BOOK_KEY).read(userIdKey);
    }

    public static void setUserId(String str) {
        Paper.book(USER_BOOK_KEY).write(userIdKey, str);
    }

    public static String getUserSessionToken() {
        return Paper.book(USER_BOOK_KEY).contains(userSessionTokenKey) ? (String) Paper.book(USER_BOOK_KEY).read(userSessionTokenKey) : "";
    }

    public static void setUserSessionToken(String str) {
        Paper.book(USER_BOOK_KEY).write(userSessionTokenKey, str);
    }

    public static String getUserImageUrl() {
        return Paper.book(USER_BOOK_KEY).contains(userImageUrlKey) ? (String) Paper.book(USER_BOOK_KEY).read(userImageUrlKey) : "";
    }

    public static void setUserImageUrl(String str) {
        Paper.book(USER_BOOK_KEY).write(userImageUrlKey, str);
    }

    public static String getUserName() {
        return Paper.book(USER_BOOK_KEY).contains(userNameKey) ? (String) Paper.book(USER_BOOK_KEY).read(userNameKey) : "";
    }

    public static void setUserName(String str) {
        Paper.book(USER_BOOK_KEY).write(userNameKey, str);
    }

    public static UserLoginResponse getUserLoginResponse() {
        if (Paper.book(USER_BOOK_KEY).contains(userLoginResponseKey)) {
            return (UserLoginResponse) Paper.book(USER_BOOK_KEY).read(userLoginResponseKey);
        }
        return null;
    }

    public static void setUserLoginResponse(UserLoginResponse userLoginResponse) {
        Paper.book(USER_BOOK_KEY).write(userLoginResponseKey, userLoginResponse);
    }

    public static String getUserEmail() {
        return Paper.book(USER_BOOK_KEY).contains(userEmailKey) ? (String) Paper.book(USER_BOOK_KEY).read(userEmailKey) : "";
    }

    public static void setUserEmail(String str) {
        Paper.book(USER_BOOK_KEY).write(userEmailKey, str);
    }

    public static ArrayList<String> getUserBlockedUserList() {
        if (Paper.book(USER_BOOK_KEY).contains(userBlockedUserListKey)) {
            return (ArrayList) Paper.book(USER_BOOK_KEY).read(userBlockedUserListKey);
        }
        return new ArrayList<>();
    }

    public static void setUserBlockedUserList(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Paper.book(USER_BOOK_KEY).write(userBlockedUserListKey, list);
    }

    public static ArrayList<String> getUserBlockedByUserList() {
        if (Paper.book(USER_BOOK_KEY).contains(userBlockedByUserListKey)) {
            return (ArrayList) Paper.book(USER_BOOK_KEY).read(userBlockedByUserListKey);
        }
        return new ArrayList<>();
    }

    public static void setUserBlockedByUserList(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Paper.book(USER_BOOK_KEY).write(userBlockedByUserListKey, list);
    }
}
