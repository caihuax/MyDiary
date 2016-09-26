package com.example.mydiary.mydiary;

import java.io.Serializable;

/**
 * Created by caihuax on 2016/9/26.
 */

public class DiaryCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    private String year;
    private String month;
    private String date;
    private String day;
    private String text;

    public DiaryCopy() {
        year = "";
        month = "";
        date = "";
        day = "";
        text = "";
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getText() {
        return text;
    }

    public void setData(String y, String m, String d, String s, String t) {
        year = y;
        month = m;
        date = d;
        day = s;
        text = t;
    }

}
