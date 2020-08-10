package com.mobiroller.models;

import com.google.gson.Gson;
import java.io.Serializable;

public class TodoModel implements Serializable {
    private boolean checked;
    private Color color;
    private String text;
    private String uniqueId;

    public enum Color {
        RED,
        YELLOW,
        GREEN
    }

    public TodoModel() {
        this.text = "";
        this.color = Color.YELLOW;
        this.checked = false;
    }

    public TodoModel(TodoModel todoModel) {
        this.text = todoModel.getText();
        this.color = todoModel.getColor();
        this.checked = todoModel.getChecked();
    }

    public static TodoModel create(String str) {
        return (TodoModel) new Gson().fromJson(str, TodoModel.class);
    }

    public String serialize() {
        return new Gson().toJson((Object) this);
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(String str) {
        this.uniqueId = str;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color2) {
        this.color = color2;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean z) {
        this.checked = z;
    }

    public void toggleChecked() {
        this.checked = !this.checked;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ text: ");
        sb.append(this.text);
        sb.append(",  color: ");
        sb.append(this.color);
        sb.append(", checked: ");
        sb.append(this.checked);
        sb.append("]");
        return sb.toString();
    }
}
