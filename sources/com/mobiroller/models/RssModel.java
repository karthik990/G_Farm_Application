package com.mobiroller.models;

import java.io.Serializable;
import java.util.Date;

public class RssModel implements Serializable {
    String author;
    String description;
    String guid;
    String image;
    String link;
    Date pubDate;
    String title;

    public RssModel() {
    }

    public RssModel(String str, String str2, String str3, String str4, String str5, Date date) {
        this.title = str;
        this.description = str3;
        this.link = str4;
        this.image = str2;
        this.author = str5;
        this.pubDate = date;
    }

    public RssModel(String str, String str2) {
        this.title = str;
        this.description = str2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RssModel{title='");
        sb.append(this.title);
        sb.append('\'');
        sb.append(", description='");
        sb.append(this.description);
        sb.append('\'');
        sb.append(", link='");
        sb.append(this.link);
        sb.append('\'');
        sb.append(", image='");
        sb.append(this.image);
        sb.append('\'');
        sb.append(", guid='");
        sb.append(this.guid);
        sb.append('\'');
        sb.append(", author='");
        sb.append(this.author);
        sb.append('\'');
        sb.append(", pubDate=");
        sb.append(this.pubDate);
        sb.append('}');
        return sb.toString();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String str) {
        this.guid = str;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String str) {
        this.author = str;
    }

    public Date getPubDate() {
        return this.pubDate;
    }

    public void setPubDate(Date date) {
        this.pubDate = date;
    }
}
