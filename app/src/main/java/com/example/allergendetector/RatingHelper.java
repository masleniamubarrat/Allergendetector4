package com.example.allergendetector;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class RatingHelper {
    private static final String PREF_KEY_RATING = "rating";

    public static void saveRating(Context context, float rating) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(PREF_KEY_RATING, rating);
        editor.apply();
    }

    public static float getRating(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getFloat(PREF_KEY_RATING, 0);
    }
}

