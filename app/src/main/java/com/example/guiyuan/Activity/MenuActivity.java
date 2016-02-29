package com.example.guiyuan.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.guiyuan.Base.BaseActivity;
import com.example.guiyuan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends BaseActivity {
    GridView gridView;
    String[] titles;
    List<Map<String,Object>> list;
    SimpleAdapter adapter;
    private static String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        gridView = (GridView) findViewById(R.id.mGridView);
        //final Intent intent = getIntent();
        //UserName = intent.getStringExtra("UserName");
        // 生成动态数组，并且转入数据
        list = new ArrayList<Map<String, Object>>();
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        titles = new String[]{"入库管理", "出库管理","烘干入库","移库管理"};
        int[] ids = new int[titles.length];
        //添加模块图标资源
        for (int i =0;i<titles.length;i++){
            if ("入库管理".equalsIgnoreCase(titles[i])){
                ids[i] =R.drawable.huo_icon;
            }else if ("出库管理".equalsIgnoreCase(titles[i])){
                ids[i] = R.drawable.huo_icon;
            }else if ("烘干入库".equalsIgnoreCase(titles[i])){
                ids[i] = R.drawable.shidu_icon;
            }else if ("移库管理".equalsIgnoreCase(titles[i])){
                ids[i] = R.drawable.xungeng_icon;
            }
        }
        for (int i=0;i<ids.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("ItemImage",ids[i]);//添加图片资源id
            map.put("ItemText",titles[i]);//
            list.add(map);
        }

        adapter = new SimpleAdapter(this,list,R.layout.grid_item,
                new String[]{"ItemImage","ItemText"},
                new int[]{R.id.ItemImage,R.id.ItemText});
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (titles[position].equalsIgnoreCase("入库管理")){
                    Intent intent1 = new Intent(MenuActivity.this,ZhiKuActivity.class);
                    //intent1.putExtra("UserName",UserName);
                    startActivity(intent1);
                }else if (titles[position].equalsIgnoreCase("出库管理")){
                    Intent intent1 = new Intent(MenuActivity.this,ChukuActivity.class);
                    //intent1.putExtra("UserName",UserName);
                    startActivity(intent1);
                }else if (titles[position].equalsIgnoreCase("烘干入库")){
                    Intent intent1 = new Intent(MenuActivity.this, HongGangActivity.class);
                    //intent1.putExtra("UserName",UserName);
                    startActivity(intent1);
                }else if (titles[position].equalsIgnoreCase("移库管理")){
                    Intent intent1 = new Intent(MenuActivity.this,YiKuActivity.class);
                    //intent1.putExtra("UserName",UserName);
                    startActivity(intent1);
                }
            }
        });
    }
}
