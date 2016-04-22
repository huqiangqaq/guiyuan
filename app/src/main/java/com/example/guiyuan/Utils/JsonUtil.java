package com.example.guiyuan.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.guiyuan.entity.Item;

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
    
    public static String parseLoginResult(String socketName,String json){
        String str = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            str = jsonObject.getString(socketName+"Result");
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
    public static List<Item> pareJson(Context context, String jsonStr){
        List<Item> list = new ArrayList<Item>();
        try {
            JSONObject object = new JSONObject(jsonStr);
            String array = object.getString("getDetailByRfidCodeResult");
            JSONArray array1 = new JSONArray(array);
            if (array1.length()>0){
                for (int i= 0;i<array1.length();i++){
                    String item_carnum = array1.getJSONObject(i).getString("车牌");
                    String item_category = array1.getJSONObject(i).getString("品种");
                    String item_singlecount = array1.getJSONObject(i).getString("单次包数");
                    String item_pizhong = array1.getJSONObject(i).getString("皮重");
                    Item item = new Item(item_carnum,item_category,item_singlecount,item_pizhong);
                    list.add(item);
                }
            }else {
                Toast.makeText(context,"当前卡号无效，请更换IC卡",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String pareWeight(String jsonStr){
        String weight = "";
        try {
            JSONObject object = new JSONObject(jsonStr);
            String array = object.getString("getWeightResult");
            JSONArray array1 = new JSONArray(array);
            for (int i=0;i<array1.length();i++){
                weight = array1.getJSONObject(i).getString("重量");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weight;
    }

    public static String parseResult_return(String jsonStr){
        String result = "";
        try {
            JSONObject object = new JSONObject(jsonStr);
            String data = object.getString("PostSingleWeightRecordResult");
            JSONArray array = new JSONArray(data);
            for (int i=0;i<array.length();i++){
                result = array.getJSONObject(i).getString("处理结果");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String parseResut_end(String jsonStr){
        String result = "";
        try {
            JSONObject object = new JSONObject(jsonStr);
            String data = object.getString("PostALLWeightRecordResult");
            JSONArray array = new JSONArray(data);
            for (int i = 0;i<array.length();i++){
                result = array.getJSONObject(i).getString("处理结果");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
