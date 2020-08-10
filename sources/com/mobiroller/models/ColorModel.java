package com.mobiroller.models;

import android.graphics.Color;
import java.io.Serializable;

public class ColorModel implements Serializable {
    private Double alpha;
    private String aveColorID;
    private Double blue;
    private Double green;
    private Double red;

    public String getAveColorID() {
        return this.aveColorID;
    }

    public void setAveColorID(String str) {
        this.aveColorID = str;
    }

    public Double getRed() {
        return this.red;
    }

    public void setRed(Double d) {
        this.red = d;
    }

    public Double getGreen() {
        return this.green;
    }

    public void setGreen(Double d) {
        this.green = d;
    }

    public Double getBlue() {
        return this.blue;
    }

    public void setBlue(Double d) {
        this.blue = d;
    }

    public Double getAlpha() {
        return this.alpha;
    }

    public void setAlpha(Double d) {
        this.alpha = d;
    }

    public int getColor() {
        float f;
        float f2;
        float f3;
        new Color();
        float f4 = 0.0f;
        try {
            f3 = Float.parseFloat(String.valueOf(getAlpha())) * 255.0f;
            try {
                f2 = Float.parseFloat(String.valueOf(getRed())) * 255.0f;
            } catch (Exception e) {
                e = e;
                f2 = 0.0f;
                f = 0.0f;
                e.printStackTrace();
                return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
            }
            try {
                f = Float.parseFloat(String.valueOf(getGreen())) * 255.0f;
            } catch (Exception e2) {
                e = e2;
                f = 0.0f;
                e.printStackTrace();
                return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
            }
            try {
                f4 = Float.parseFloat(String.valueOf(getBlue())) * 255.0f;
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
            }
        } catch (Exception e4) {
            e = e4;
            f3 = 0.0f;
            f2 = 0.0f;
            f = 0.0f;
            e.printStackTrace();
            return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
        }
        return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
    }
}
