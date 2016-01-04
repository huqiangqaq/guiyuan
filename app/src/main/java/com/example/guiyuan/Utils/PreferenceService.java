package com.example.guiyuan.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 83916 on 2016/1/2.
 */
public class PreferenceService {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PreferenceService(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void save(String UserName,String PassWord){

        editor.putString("UserName",UserName);
        editor.putString("PassWord",PassWord);
        editor.commit();
    }

    public Map<String,String> getPrefrences(){
        Map<String,String> map = new HashMap<String,String>();
        SharedPreferences preference = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        map.put("UserName",preference.getString("UserName",""));
        map.put("PassWord",preference.getString("PassWord",""));
        return map;
    }
}
