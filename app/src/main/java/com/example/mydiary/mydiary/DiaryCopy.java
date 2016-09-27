package com.example.mydiary.mydiary;

import java.io.Serializable;

/**
 * Created by caihuax on 2016/9/26.
 */

class DiaryCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    private String year;
    private String month;
    private String date;
    private String day;
    private String text;

    DiaryCopy() {
        year = "";
        month = "";
        date = "";
        day = "";
        text = "";
    }

    String getYear() {
        return year;
    }

    String getMonth() {
        return month;
    }

    String getDate() {
        return date;
    }

    String getDay() {
        return day;
    }

    String getText() {
        return text;
    }

    void setData(String y, String m, String d, String s, String t) {
        year = y;
        month = m;
        date = d;
        day = s;
        text = t;
    }

}
