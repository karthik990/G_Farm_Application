package com.mobiroller.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class NoteModel implements Serializable {
    private String color;
    private Long created_at;
    private String description;
    private ArrayList<String> imagePaths;
    private int position;
    private String title;
    private String uniqueId;
    private Long updated_at;

    public NoteModel() {
        String str = "";
        this.color = str;
        this.title = str;
        this.description = str;
        this.imagePaths = null;
        this.updated_at = null;
        this.created_at = Long.valueOf(new Date().getTime());
        this.uniqueId = UUID.randomUUID().toString();
    }

    public NoteModel(String str, String str2, String str3) {
        this.description = str;
        this.title = str2;
        this.color = str3;
        this.updated_at = null;
        this.created_at = Long.valueOf(new Date().getTime());
    }

    public NoteModel(String str, String str2, String str3, ArrayList<String> arrayList) {
        this.description = str;
        this.title = str2;
        this.color = str3;
        this.imagePaths = arrayList;
        this.updated_at = null;
        this.created_at = Long.valueOf(new Date().getTime());
    }

    public String getTitle() {
        String str = this.title;
        return str == null ? "" : str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        String str = this.description;
        return str == null ? "" : str;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(String str) {
        this.uniqueId = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public ArrayList<String> getImagePaths() {
        if (this.imagePaths == null) {
            this.imagePaths = new ArrayList<>();
        }
        return this.imagePaths;
    }

    public void setImagePaths(ArrayList<String> arrayList) {
        this.imagePaths = arrayList;
    }

    public Long getCreated_at() {
        return this.created_at;
    }

    public Long getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at() {
        this.updated_at = Long.valueOf(new Date().getTime());
    }

    public String getFirstImagePath() {
        ArrayList<String> arrayList = this.imagePaths;
        return (arrayList == null || arrayList.size() == 0) ? "" : (String) this.imagePaths.get(0);
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NoteModel{color='");
        sb.append(this.color);
        sb.append('\'');
        sb.append(", title='");
        sb.append(this.title);
        sb.append('\'');
        sb.append(", description='");
        sb.append(this.description);
        sb.append('\'');
        sb.append(", imagePaths=");
        sb.append(this.imagePaths);
        sb.append(", created_at=");
        sb.append(this.created_at);
        sb.append(", updated_at=");
        sb.append(this.updated_at);
        sb.append(", position=");
        sb.append(this.position);
        sb.append('}');
        return sb.toString();
    }

    public boolean isUpdated() {
        return this.updated_at != null;
    }
}
