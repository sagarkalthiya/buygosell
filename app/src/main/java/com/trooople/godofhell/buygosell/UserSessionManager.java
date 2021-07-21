package com.trooople.godofhell.buygosell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.trooople.godofhell.buygosell.Activity.Login_activity;

import java.util.HashMap;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "Buygosell";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_USER_ID = "user_id";

    // User name (make variable public to access from outside)
    public static final String KEY_FNAME = "first_name";

    // User name (make variable public to access from outside)
    public static final String KEY_LNAME = "last_name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String PASSWORD = "password";

    public static final String COUNTRY = "country";

    public static final String STATE = "state";

    public static final String CITY = "city";

    public static final String MOBILE_NO = "mobile";

    public static final String DOB = "dob";

    public static final String PROFILE_PIC = "profile_pic";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String user_id, String name, String last_name, String email, String password, String country, String state, String city, String mobile, String dob, String profile_pic){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USER_ID, user_id);

        // Storing name in pref
        editor.putString(KEY_FNAME, name);

        // Storing name in pref
        editor.putString(KEY_LNAME, last_name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing password in pref
        editor.putString(PASSWORD, password);

        editor.putString(COUNTRY, country);

        editor.putString(STATE, state);

        editor.putString(CITY, city);

        editor.putString(MOBILE_NO, mobile);

        editor.putString(DOB, dob);

        editor.putString(PROFILE_PIC, profile_pic);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login_activity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }

    public boolean checking_looging(){
        if(!this.isUserLoggedIn()){



            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));

        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));

        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(COUNTRY, pref.getString(COUNTRY, null));

        user.put(STATE, pref.getString(STATE, null));

        user.put(CITY, pref.getString(CITY, null));

        user.put(MOBILE_NO, pref.getString(MOBILE_NO, null));

        user.put(DOB, pref.getString(DOB, null));

        user.put(PROFILE_PIC, pref.getString(PROFILE_PIC, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, Login_activity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public void Clear_Shared_Preferences(){
        editor.clear();
        editor.commit();
    }

    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}