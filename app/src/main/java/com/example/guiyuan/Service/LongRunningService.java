package com.example.guiyuan.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import okhttp3.Call;

/**
 * Created by huqiang on 2016/4/12 10:59.
 */
public class LongRunningService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            String rfidCode = intent.getStringExtra("carnum");
                //后台访问数据，获得weight
                Log.d("LongRunningService","execute at "+new Date().toString());
                OkHttpUtils.get().url(Constant.WEIGHT_URL+rfidCode).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }
                    @Override
                    public void onResponse(String response) {
                        String weight = JsonUtil.pareWeight(response);
                        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        int time = 2*1000;
                        long triggerAtTime = SystemClock.elapsedRealtime()+time;
                        Intent i = new Intent("com.android.broadcast.RECEIVER_ACTION");
                        i.putExtra("data",weight);
                        PendingIntent pi = PendingIntent.getBroadcast(LongRunningService.this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
                        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }
}
