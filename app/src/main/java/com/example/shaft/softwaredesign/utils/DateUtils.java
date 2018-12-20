package com.example.shaft.softwaredesign.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date parseToDate(String date) {
        SimpleDateFormat parser = new SimpleDateFormat();
        Date res = new Date();
        try {
            res = parser.parse(date);
        }
        catch (ParseException ignore){
            Log.e("datetime", "Not valid date!");
        }
        return res;
    }

    public static String parseToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat();
        return formatter.format(date);
    }

}
