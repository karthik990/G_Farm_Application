package com.mobiroller.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.FavoriteModel;
import com.mobiroller.models.GalleryModel;
import com.mobiroller.models.RssModel;
import com.mobiroller.models.ScreenModel;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteHelper {
    private final String FAVORITE_LIST = "FAVORITE_LIST";
    private final String FAVORITE_LIST_PREF;
    private Context mContext;
    private SharedPreferences mSharedPref;

    public FavoriteHelper(Context context) {
        String str = "FAVORITE_LIST_PREF";
        this.FAVORITE_LIST_PREF = str;
        this.mContext = context;
        this.mSharedPref = this.mContext.getSharedPreferences(str, 0);
    }

    public void addScreenToList(ScreenModel screenModel, String str, String str2, String str3) {
        ArrayList db = getDb();
        if (db == null) {
            db = new ArrayList();
        }
        db.add(0, new FavoriteModel(screenModel, str, str2, str3));
        updateDb(db);
    }

    public void addRssContentToList(RssModel rssModel) {
        ArrayList db = getDb();
        db.add(0, new FavoriteModel(rssModel));
        updateDb(db);
    }

    public void addImageContentToList(GalleryModel galleryModel) {
        ArrayList db = getDb();
        db.add(0, new FavoriteModel(galleryModel));
        updateDb(db);
    }

    public void removeRssContent(RssModel rssModel) {
        ArrayList db = getDb();
        int i = 0;
        while (true) {
            if (i < db.size()) {
                if (((FavoriteModel) db.get(i)).getContentType() == 1 && checkSimilarity(rssModel.getTitle(), ((FavoriteModel) db.get(i)).getRssModel().getTitle())) {
                    db.remove(i);
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        updateDb(db);
    }

    public void removeGalleryContent(GalleryModel galleryModel) {
        ArrayList db = getDb();
        int i = 0;
        while (true) {
            if (i < db.size()) {
                if (((FavoriteModel) db.get(i)).getContentType() == 2 && checkSimilarityURI(galleryModel.getURL(), ((FavoriteModel) db.get(i)).getGalleryModel().getURL())) {
                    db.remove(i);
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        updateDb(db);
    }

    public void removeScreenContent(String str) {
        ArrayList db = getDb();
        int i = 0;
        while (true) {
            if (i < db.size()) {
                if (((FavoriteModel) db.get(i)).IsScreen() && ((FavoriteModel) db.get(i)).getScreenId().equalsIgnoreCase(str)) {
                    db.remove(i);
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        updateDb(db);
    }

    public boolean isRssContentAddedToList(RssModel rssModel) {
        ArrayList db = getDb();
        for (int i = 0; i < db.size(); i++) {
            if (((FavoriteModel) db.get(i)).getContentType() == 1 && checkSimilarity(rssModel.getTitle(), ((FavoriteModel) db.get(i)).getRssModel().getTitle())) {
                return true;
            }
        }
        return false;
    }

    public boolean isImageContentAddedToList(GalleryModel galleryModel) {
        ArrayList db = getDb();
        for (int i = 0; i < db.size(); i++) {
            if (((FavoriteModel) db.get(i)).getContentType() == 2 && checkSimilarityURI(galleryModel.getURL(), ((FavoriteModel) db.get(i)).getGalleryModel().getURL())) {
                return true;
            }
        }
        return false;
    }

    public boolean isScreenAddedToList(String str) {
        ArrayList db = getDb();
        for (int i = 0; i < db.size(); i++) {
            if (((FavoriteModel) db.get(i)).IsScreen() && ((FavoriteModel) db.get(i)).getScreenId().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    private void updateDb(ArrayList<FavoriteModel> arrayList) {
        String json = new Gson().toJson((Object) arrayList, getTypeToken());
        Editor edit = this.mSharedPref.edit();
        edit.putString("FAVORITE_LIST", json);
        edit.apply();
    }

    public void removeDb() {
        Editor edit = this.mSharedPref.edit();
        edit.remove("FAVORITE_LIST");
        edit.apply();
    }

    public ArrayList<FavoriteModel> getDb() {
        String str = "FAVORITE_LIST";
        String string = this.mSharedPref.getString(str, null);
        if (string == null) {
            return new ArrayList<>();
        }
        try {
            ArrayList<FavoriteModel> arrayList = (ArrayList) new Gson().fromJson(string, getTypeToken());
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            return arrayList;
        } catch (Exception unused) {
            this.mSharedPref.edit().remove(str).apply();
            return new ArrayList<>();
        }
    }

    private Type getTypeToken() {
        return new TypeToken<ArrayList<FavoriteModel>>() {
        }.getType();
    }

    private boolean checkSimilarity(String str, String str2) {
        return similarity(str, str2) > 0.75d;
    }

    private boolean checkSimilarityURI(String str, String str2) {
        return str.substring(str.lastIndexOf(47) + 1).equalsIgnoreCase(str2.substring(str2.lastIndexOf(47) + 1));
    }

    private double similarity(String str, String str2) {
        if (str.length() < str2.length()) {
            String str3 = str2;
            str2 = str;
            str = str3;
        }
        int length = str.length();
        if (length == 0) {
            return 1.0d;
        }
        double editDistance = (double) (length - StringSimilarityHelper.editDistance(str, str2));
        double d = (double) length;
        Double.isNaN(editDistance);
        Double.isNaN(d);
        return editDistance / d;
    }

    public boolean isScreenAvailable(String str) {
        for (String equalsIgnoreCase : this.mContext.getResources().getStringArray(R.array.favorite_available_modules)) {
            if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
