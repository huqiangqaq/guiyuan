package com.example.guiyuan.Base;

import android.app.Activity;
import android.os.Bundle;

import com.example.guiyuan.Utils.ActivityCollector;

/**
 * Created by huqiang on 2016/2/29 16:52.
 */
public class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
