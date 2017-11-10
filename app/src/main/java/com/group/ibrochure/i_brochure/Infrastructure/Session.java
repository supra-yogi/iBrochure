package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Yogi on 08/11/2017.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserOrEmail(String userOrEmail) {
        prefs.edit().putString("userOrEmail", userOrEmail).apply();
    }

    public void setId(int Id) {
        prefs.edit().putInt("id", Id).apply();
    }

    public String getUserOrEmail() {
        return prefs.getString("userOrEmail", "");
    }

    public int getId() {
        return prefs.getInt("id", 0);
    }

    public void logOut() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
