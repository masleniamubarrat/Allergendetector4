package com.example.allergendetector;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static  final
    String PREF_NAME = "AppSession",KEY_USER_EMAIL = "userEmail",KEY_USER_PASSWORD = "userPassword";

    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
    public void saveUserCredentials(String email, String password){
    editor.putString(KEY_USER_EMAIL, email);
    editor.putString(KEY_USER_PASSWORD, password);
    editor.apply();
    }

    public  boolean isLoggedIn(){
        return sharedPreferences.contains(KEY_USER_EMAIL)&&sharedPreferences.contains(KEY_USER_PASSWORD);

    }
    public String getUserEmail(){
        return sharedPreferences.getString(KEY_USER_EMAIL,"");
    }

    public void clearSession(){
    editor.clear();
    editor.apply();}

}
