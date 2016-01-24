package com.example.guiyuan;

import android.app.Application;

/**
 * Created by huqiang on 2016/1/24 16:44.
 */
public class MyApplication extends Application {
    private String UserName;
    private String PassWord;
    private static MyApplication instance;
    public static MyApplication getInstance(){
        if (instance==null){
            instance = new MyApplication();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
