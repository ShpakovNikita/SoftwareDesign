package com.example.shaft.softwaredesign.utils;

public class UrlUtils {

    public static String GetUrl(String data) {
        String res;
        if (data != "") {
            if (data.startsWith("https://") || data.startsWith("http://")) {
                res = data;
            }
            else{
                res =  "https://" + data;
            }
        }else{
            res = "";
        }
        return res;
    }
}