package com.example.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;

/*
    Session class is used to easily set and get shared preferences, without using hardcoded strings
    in multiple classes (i.e. the preferences name "MySharedPrefs")
*/
public class Session {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private static final String PREF_NAME = "MySharedPrefs";

    static String USERNAME_STRING = "username";
    static String EMAIL_STRING = "email";
    /*  Salvare l'ID dell'utente può facilitare le query di ricerca delle liste ma sarebbe veramente
        brutto per la sicurezza dell'app se dovesse essere veramente rilasciata
     */
    static String ID_STRING = "id";
    static String LOGIN_STRING = "isLoggedIn";

    public Session(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUsername(String username){
        editor.putString(USERNAME_STRING, username);
        editor.apply();
    }

    public void setEmail(String email){
        editor.putString(EMAIL_STRING, email);
        editor.apply();

    }

    public void setLoginStatus(Boolean bool){
        editor.putBoolean(LOGIN_STRING, bool);
        editor.apply();
    }

    public void setAllUserInfos(String username, String email, Integer userId){
        editor.putString(USERNAME_STRING, username);
        editor.putString(EMAIL_STRING, email);
        editor.putBoolean(LOGIN_STRING, true);
        editor.putInt(ID_STRING, userId);
        editor.apply();
    }

    public String getUsername(){
        return sharedPreferences.getString(USERNAME_STRING, null);
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL_STRING, null);
    }

    public Integer getUserId(){
        return sharedPreferences.getInt(ID_STRING, 0);
    }

    public Boolean getLoginStatus(){
        return sharedPreferences.getBoolean(LOGIN_STRING,false);
    }

}
