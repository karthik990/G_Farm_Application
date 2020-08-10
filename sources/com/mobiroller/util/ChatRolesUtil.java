package com.mobiroller.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiroller.models.MChatRoleModel;
import com.mobiroller.models.chat.ChatRoleModel;
import java.util.ArrayList;
import java.util.List;

public class ChatRolesUtil {
    private static final String CHAT_ROLE_LIST = "ChatRolesList";
    private static final String CHAT_ROLE_PREF_NAME = "ChatRolesPreferences";

    private static ArrayList<MChatRoleModel> getDb(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHAT_ROLE_PREF_NAME, 0);
        String str = CHAT_ROLE_LIST;
        String string = sharedPreferences.getString(str, null);
        if (string == null) {
            return null;
        }
        try {
            return (ArrayList) new Gson().fromJson(string, new TypeToken<ArrayList<MChatRoleModel>>() {
            }.getType());
        } catch (Exception unused) {
            sharedPreferences.edit().remove(str).apply();
            return new ArrayList<>();
        }
    }

    public static List<ChatRoleModel> getDatabase(Context context) {
        ArrayList db = getDb(context);
        if (db == null) {
            return new ArrayList();
        }
        if (db.size() == 0) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < db.size(); i++) {
            arrayList.add(((MChatRoleModel) db.get(i)).getChatRoleModel());
        }
        return arrayList;
    }

    public static void updateDb(List<MChatRoleModel> list, Context context) {
        String json = new Gson().toJson((Object) list, new TypeToken<ArrayList<MChatRoleModel>>() {
        }.getType());
        Editor edit = context.getSharedPreferences(CHAT_ROLE_PREF_NAME, 0).edit();
        edit.putString(CHAT_ROLE_LIST, json);
        edit.apply();
    }
}
