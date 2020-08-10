package com.mobiroller.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiroller.models.TodoModel;
import com.mobiroller.models.TodoModel.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoUtil {
    private static final String SHARED_PREFS_FILE = "MobirollerTodoModule";
    private static final String TODO_PREF_NAME = "MobirollerTodo";

    public static List<TodoModel> sort(TodoModel todoModel, List<TodoModel> list) {
        if (todoModel == null) {
            return list;
        }
        list.remove(todoModel);
        return insert(todoModel, list);
    }

    private static List<TodoModel> insert(TodoModel todoModel, List<TodoModel> list) {
        todoModel.setUniqueId(UUID.randomUUID().toString());
        int i = 0;
        if (todoModel.getChecked()) {
            int size = list.size();
            while (true) {
                if (i >= list.size()) {
                    i = size;
                    break;
                } else if (((TodoModel) list.get(i)).getChecked()) {
                    break;
                } else {
                    i++;
                }
            }
        }
        if (todoModel.getColor() == Color.RED || todoModel.getChecked()) {
            list.add(i, todoModel);
            return list;
        }
        while (i < list.size()) {
            if (!todoModel.getChecked() && ((TodoModel) list.get(i)).getChecked()) {
                list.add(i, todoModel);
                return list;
            } else if (todoModel.getColor() == Color.YELLOW && ((TodoModel) list.get(i)).getColor() != Color.RED) {
                list.add(i, todoModel);
                return list;
            } else if (todoModel.getColor() == Color.GREEN && ((TodoModel) list.get(i)).getColor() == Color.GREEN) {
                list.add(i, todoModel);
                return list;
            } else {
                i++;
            }
        }
        list.add(todoModel);
        return list;
    }

    public static View findViewByIndex(int i, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int childCount = (recyclerView.getChildCount() + findFirstVisibleItemPosition) - 1;
        if (i >= findFirstVisibleItemPosition && i <= childCount) {
            return recyclerView.getChildAt(i - findFirstVisibleItemPosition);
        }
        System.out.println("Invisible view!");
        return null;
    }

    public static ArrayList<TodoModel> getDb(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(SHARED_PREFS_FILE);
        sb.append(str);
        return (ArrayList) new Gson().fromJson(context.getSharedPreferences(sb.toString(), 0).getString(TODO_PREF_NAME, null), new TypeToken<ArrayList<TodoModel>>() {
        }.getType());
    }

    public static void updateDb(ArrayList<TodoModel> arrayList, Context context, String str) {
        String json = new Gson().toJson((Object) arrayList, new TypeToken<ArrayList<TodoModel>>() {
        }.getType());
        StringBuilder sb = new StringBuilder();
        sb.append(SHARED_PREFS_FILE);
        sb.append(str);
        Editor edit = context.getSharedPreferences(sb.toString(), 0).edit();
        edit.putString(TODO_PREF_NAME, json);
        edit.apply();
    }

    public static void removeFromList(TodoModel todoModel, Context context, String str) {
        ArrayList db = getDb(context, str);
        if (db == null) {
            db = new ArrayList();
        }
        int i = 0;
        while (true) {
            if (i >= db.size()) {
                break;
            } else if (((TodoModel) db.get(i)).getUniqueId().equalsIgnoreCase(todoModel.getUniqueId())) {
                db.remove(i);
                break;
            } else {
                i++;
            }
        }
        updateDb(db, context, str);
    }
}
