package com.applyze;

import java.io.Serializable;
import java.util.Date;

class ScreenDisplay implements Serializable {
    private Date date;
    private String title;

    ScreenDisplay(String str) {
        if (str == null || str.equalsIgnoreCase("")) {
            str = "_unknown";
        }
        this.title = str;
        this.date = ServiceUtil.getDate();
    }

    /* access modifiers changed from: 0000 */
    public String getTitle() {
        return this.title;
    }

    /* access modifiers changed from: 0000 */
    public void setTitle(String str) {
        this.title = str;
    }

    /* access modifiers changed from: 0000 */
    public Date getDate() {
        return this.date;
    }

    /* access modifiers changed from: 0000 */
    public void setDate(Date date2) {
        this.date = date2;
    }
}
