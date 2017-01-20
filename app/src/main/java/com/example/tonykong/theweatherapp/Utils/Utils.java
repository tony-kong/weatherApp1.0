package com.example.tonykong.theweatherapp.Utils;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by TonyKong on 12/25/2016.
 */

public class Utils {
    public static String API_KEY = "77e2b8cd6a010c172320d2eb48e5b356";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather?q=";

    @NonNull
    public static String apiRequest(String city){
        return API_LINK + String.format("city=%s&APPID=%s&units=metric", city, API_KEY);
    }

    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.CANADA);
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png",icon);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.CANADA);
        Date date = new Date();
        return dateFormat.format(date);
    }

}
