package com.example.guiyuan.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 83916 on 2016/1/2.
 */
public class PreferenceService {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Set<String> set = new HashSet<String>();

    public PreferenceService(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void save(String UserName,String PassWord){
        set.add(UserName);
        editor.putStringSet("content",set);
        editor.putString("UserName",UserName);
        editor.putString("PassWord",PassWord);
        editor.commit();
    }
    public void save(String num){
        editor.putString("num",num);
        editor.commit();
    }

    public Map<String,String> getPrefrences(){
        Map<String,String> map = new HashMap<String,String>();
        SharedPreferences preference = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        map.put("UserName",preference.getString("UserName",""));
        map.put("PassWord",preference.getString("PassWord",""));
        map.put("num",preference.getString("num","0"));
        return map;
    }
}
