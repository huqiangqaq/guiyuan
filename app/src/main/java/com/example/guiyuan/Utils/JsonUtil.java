package com.example.guiyuan.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 83916 on 2016/1/2.
 */
public class JsonUtil {

    public static String parseLoginResult(String json){
        String str = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            str = jsonObject.getString("LoginResult");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    //获取仓号
    public static List<String> parseCangHao(String json){
        String[] str =null;
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data =jsonObject.getString("getCargoNoListResult");
            System.out.println(data.substring(1,data.length()-1));
            str=data.substring(1,data.length()-1).split(",");

            Log.i("123++++++++----",str[3]);
            for(int i=2;i<str.length;i++) {
                if (i%2==1){
                    String now=str[i].substring(0,str[i].length()-1);
                    Log.i("123++++++++----",now);
                    list.add(now);
                }


            }
            } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
