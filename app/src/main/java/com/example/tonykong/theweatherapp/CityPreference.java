package com.example.tonykong.theweatherapp;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by TonyKong on 12/31/2016.
 */

public class CityPreference {
    SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getCity() {
        return prefs.getString("city", "Vancouver, CA");
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }
}
