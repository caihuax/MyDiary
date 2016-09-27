package com.example.mydiary.mydiary;

import java.util.Calendar;

class Calculate {

    static String countDay(int d){
        String ret;
        switch (d){
            case 1:
                ret = "SUN";
                break;
            case 2:
                ret = "MON";
                break;
            case 3:
                ret = "TUE";
                break;
            case 4:
                ret = "WED";
                break;
            case 5:
                ret = "THU";
                break;
            case 6:
                ret = "FRI";
                break;
            case 7:
                ret = "SAT";
                break;
            default:
                ret = "";
        }
        return ret;
    }

    static int countDaysOfaMonth(Calendar calendar){
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.MONTH, -1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }
}
