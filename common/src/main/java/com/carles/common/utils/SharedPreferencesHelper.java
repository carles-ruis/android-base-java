package com.carles.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper {

    private static final String PREFERENCE_EXPIRATION_TIME_PREFIX = "expiration_time_prefix_";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public long getCacheExpirationTime(String className, String itemId) {
        return sharedPreferences.getLong(PREFERENCE_EXPIRATION_TIME_PREFIX + className + itemId, 0l);
    }

    public void setCacheExpirationTime(String className, String itemId, long timestamp) {
        sharedPreferences.edit().putLong(PREFERENCE_EXPIRATION_TIME_PREFIX + className + itemId, timestamp).apply();
    }
}
