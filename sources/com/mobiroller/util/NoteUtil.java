package com.mobiroller.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiroller.models.NoteModel;
import java.util.ArrayList;

public class NoteUtil {
    public static ArrayList<NoteModel> getDb(Activity activity, String str) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("NoteUtilPreferences", 0);
        StringBuilder sb = new StringBuilder();
        sb.append("NoteUtil");
        sb.append(str);
        return (ArrayList) new Gson().fromJson(sharedPreferences.getString(sb.toString(), null), new TypeToken<ArrayList<NoteModel>>() {
        }.getType());
    }

    public static void updateDb(ArrayList<NoteModel> arrayList, Context context, String str) {
        String json = new Gson().toJson((Object) arrayList, new TypeToken<ArrayList<NoteModel>>() {
        }.getType());
        Editor edit = context.getSharedPreferences("NoteUtilPreferences", 0).edit();
        StringBuilder sb = new StringBuilder();
        sb.append("NoteUtil");
        sb.append(str);
        edit.putString(sb.toString(), json);
        edit.apply();
    }

    public static void addModel(Activity activity, NoteModel noteModel, String str) {
        ArrayList db = getDb(activity, str);
        if (db == null) {
            db = new ArrayList();
        }
        db.add(0, noteModel);
        updateDb(db, activity, str);
    }

    public static void updateModel(Activity activity, NoteModel noteModel, int i, String str) {
        ArrayList db = getDb(activity, str);
        db.set(i, noteModel);
        updateDb(db, activity, str);
    }

    public static void removeFromList(Activity activity, NoteModel noteModel, String str) {
        ArrayList db = getDb(activity, str);
        if (db == null) {
            db = new ArrayList();
        }
        int i = 0;
        while (true) {
            if (i >= db.size()) {
                break;
            } else if (((NoteModel) db.get(i)).getUniqueId().equalsIgnoreCase(noteModel.getUniqueId())) {
                db.remove(i);
                break;
            } else {
                i++;
            }
        }
        updateDb(db, activity, str);
    }
}
