package com.example.aismobile;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private Context mContext;

    public static final String KEY_ISLOGGEDIN = "is_logged_in";
    public static final String PREF_NAME  = "ais_pref";
    public static final String KEY_EMPLOYEE_ID  = "employee_id";
    public static final String KEY_USER_NAME  = "user_name";
    public static final String KEY_USER_DISPLAYNAME  = "user_displayname";
    public static final String KEY_USERGROUP_ID  = "usergroup_id";
    public static final String KEY_USERGROUP_NAME  = "usergroup_name";
    public static final String KEY_USER_PWD  = "user_pwd";
    public static final String KEY_TOKEN  = "token";
    public static final String KEY_EMPLOYEE_NUMBER  = "employee_number";
    public static final String KEY_GENDER  = "gender";
    public static final String KEY_PLACE_BIRTHDAY  = "place_birthday";
    public static final String KEY_BIRTHDAY  = "birthday";
    public static final String KEY_ADDRESS  = "address";
    public static final String KEY_MOBILE_PHONE  = "mobile_phone";
    public static final String KEY_EMAIL  = "email";
    public static final String KEY_FILE_NAME  = "employee_file_name";

    public SharedPrefManager(Context context){
        this.mContext = context;
    }

    public static SharedPrefManager getInstance(Context context){
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        return sharedPrefManager;
    }

    public boolean isLogin(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(KEY_ISLOGGEDIN) && sharedPreferences.getBoolean(KEY_ISLOGGEDIN,false);
    }

    public void login(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISLOGGEDIN, true);

        editor.apply();
    }

    public void logout(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void setEmployeeId(String employeeId){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_ID, employeeId);
        editor.apply();
    }

    public void setUserName(String userName){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
    }

    public void setUserDisplayName(String userDisplayName){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_DISPLAYNAME, userDisplayName);
        editor.apply();
    }

    public void setUserGroupId(String userGroupId){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERGROUP_ID, userGroupId);
        editor.apply();
    }

    public void setUserGroupName(String userGroupName){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERGROUP_NAME, userGroupName);
        editor.apply();
    }

    public void setUserPwd(String userPwd){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_PWD, userPwd);
        editor.apply();
    }

    public void setToken(String token){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public void setEmployeeNumber(String employeeNumber){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_NUMBER, employeeNumber);
        editor.apply();
    }

    public void setGender(String gender){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public void setPlaceBirthday(String placeBirthday){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_PLACE_BIRTHDAY, placeBirthday);
        editor.apply();
    }

    public void setBirthday(String birthday){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_BIRTHDAY, birthday);
        editor.apply();
    }

    public void setAddress(String address){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ADDRESS, address);
        editor.apply();
    }

    public void setMobilePhone(String mobilePhone){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_MOBILE_PHONE, mobilePhone);
        editor.apply();
    }

    public void setEmail(String email){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void setFileName(String fileName){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_FILE_NAME, fileName);
        editor.apply();
    }

    public String getEmployeeId(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_ID,null);
    }

    public String getUserName(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME,null);
    }

    public String getUserDisplayName(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_DISPLAYNAME,null);
    }

    public String getUserGroupId(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERGROUP_ID,null);
    }

    public String getUserGroupName(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERGROUP_NAME,null);
    }

    public String getUserPwd(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_PWD,null);
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN,null);
    }

    public String getEmployeeNumber(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_NUMBER,null);
    }

    public String getGender(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_GENDER,null);
    }

    public String getPlaceBirthday(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PLACE_BIRTHDAY,null);
    }

    public String getBirthday(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_BIRTHDAY,null);
    }

    public String getAddress(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADDRESS,null);
    }

    public String getMobilePhone(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MOBILE_PHONE,null);
    }

    public String getEmail(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);
    }

    public String getFileName(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FILE_NAME,null);
    }
}
