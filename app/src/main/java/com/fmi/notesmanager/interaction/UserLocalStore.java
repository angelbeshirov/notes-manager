package com.fmi.notesmanager.interaction;

import android.content.Context;
import android.content.SharedPreferences;

import com.fmi.notesmanager.model.User;

/**
 * This is the shared preference in the application. It is used
 * for storing the login of the user.
 *
 * @author angel.beshirov
 */
public class UserLocalStore {

    private static final String SP_NAME = "userDetails";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String EMPTY_STRING = "";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final int NO_ID = -1;
    private static final String LOGGED_IN = "loggedIn";
    private final SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt(ID, user.getId());
        spEditor.putString(USERNAME, user.getUsername());
        spEditor.putString(PASSWORD, user.getPassword());
        spEditor.putString(EMAIL, user.getEmail());

        spEditor.apply();
    }

    public User getLoggedInUser() {
        Integer id = userLocalDatabase.getInt(ID, NO_ID);
        String username = userLocalDatabase.getString(USERNAME, EMPTY_STRING);
        String email = userLocalDatabase.getString(EMAIL, EMPTY_STRING);
        String password = userLocalDatabase.getString(PASSWORD, EMPTY_STRING);

        return new User(id, username, password, email);
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean(LOGGED_IN, loggedIn);

        spEditor.apply();
    }

    public void clear() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }

    public boolean isUserLoggedIn() {
        return userLocalDatabase.getBoolean(LOGGED_IN, false);
    }
}
