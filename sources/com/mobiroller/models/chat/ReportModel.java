package com.mobiroller.models.chat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import java.io.Serializable;

public class ReportModel implements Serializable, Comparable {
    public String key;
    @Exclude
    public ChatUserModel receiverUser;
    private String report;
    private String reportedUid;
    private String senderUid;
    @Exclude
    public ChatUserModel senderUser;
    private Object timeStamp;
    private String version = "v1";

    public ReportModel() {
    }

    public ReportModel(String str, String str2, String str3) {
        this.senderUid = str;
        this.reportedUid = str2;
        this.report = str3;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getSenderUid() {
        return this.senderUid;
    }

    public void setSenderUid(String str) {
        this.senderUid = str;
    }

    public String getReportedUid() {
        return this.reportedUid;
    }

    public void setReportedUid(String str) {
        this.reportedUid = str;
    }

    public String getReport() {
        return this.report;
    }

    public void setReport(String str) {
        this.report = str;
    }

    public Object getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Object obj) {
        this.timeStamp = obj;
    }

    public int compareTo(Object obj) {
        return ((Long) this.timeStamp).longValue() == ((Long) ((ReportModel) obj).getTimeStamp()).longValue() ? -1 : 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ReportModel{senderUid='");
        sb.append(this.senderUid);
        sb.append('\'');
        sb.append(", reportedUid='");
        sb.append(this.reportedUid);
        sb.append('\'');
        sb.append(", report='");
        sb.append(this.report);
        sb.append('\'');
        sb.append(", timeStamp=");
        sb.append(this.timeStamp);
        sb.append(", key='");
        sb.append(this.key);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
