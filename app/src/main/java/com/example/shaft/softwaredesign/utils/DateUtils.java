package com.example.shaft.softwaredesign.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String FORMAT = "E d M yyyy HH:mm:ss z";

    public static Date parseToDate(String date) {
        SimpleDateFormat parser = new SimpleDateFormat(FORMAT);
        Date res = new Date();
        try {res = parser.parse(date);}
        catch (ParseException ignore){}
        return res;
    }

    public static String parseToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        return formatter.format(date);
    }

}
