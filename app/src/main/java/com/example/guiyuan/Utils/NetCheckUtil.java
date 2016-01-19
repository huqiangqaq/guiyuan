package com.example.guiyuan.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by huqiang on 2016/1/6.
 */
public class NetCheckUtil {
    /**
     * 跟网络相关的工具类
     */

    private NetCheckUtil() {
        throw new UnsupportedOperationException("can not be instantiated");
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (null!=info &&info.isConnected()){
                if (info.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否WiFi连接
     */
    public static boolean isWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm==null){
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }
    /**
     * 判断是否移动网络连接
     */
    public static boolean isMobile(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm ==null){
            return  false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     *
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity){
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings","com.android.settings.WireslessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent,0);
    }
}
