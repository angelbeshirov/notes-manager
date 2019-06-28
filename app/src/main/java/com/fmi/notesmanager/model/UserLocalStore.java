package com.fmi.notesmanager.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_NAME = "userDetals";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("id", user.getId());
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        spEditor.putString("email", user.getEmail());

        spEditor.apply();
    }

    public User getLoggedInUser() {
        Integer id = userLocalDatabase.getInt("id", -1);
        String username = userLocalDatabase.getString("username", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");

        return new User(id, username, password, email);
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);

        spEditor.apply();
    }

    public void clear() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }

    public boolean isUserLoggedIn() {
        return userLocalDatabase.getBoolean("loggedIn", false);
    }
}
