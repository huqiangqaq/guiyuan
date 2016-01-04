package com.example.guiyuan.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guiyuan.R;
import com.example.guiyuan.Adapter.HistoryAdapter;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.R.menu;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.NetUtil;
import com.example.guiyuan.entity.HistoryVertical;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HisVerticalActivity extends Activity {
@ViewInject(R.id.iv_back)ImageView iv_back;
@ViewInject(R.id.lvlist)ListView lvlist;
private List<HistoryVertical> list;
private HistoryAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_his_vertical);
		ViewUtils.inject(this);
		init();
	}
	public void init(){
		list=new ArrayList<HistoryVertical>();
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HisVerticalActivity.this.finish();
			}
		});
		NetUtil.sendNetReqByGet(Constant.HIS_ADDRESS,new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> req) {
				String str=req.result;
				JSONObject json;
				try {
					json = new JSONObject(str);
					String data=json.getString("getCarRecordListResult");
					data=data.substring(1,data.length()-1);
					String[] arr=data.split("\\},");
					for(int i=1;i<arr.length;i++){
						String s=arr[i].substring(1);
						String[] ary=s.split(",");
						list.add(new HistoryVertical(ary[0], ary[1], ary[2],Integer.valueOf(ary[3]), ary[4], ary[5],
								Float.valueOf(ary[6]),Float.valueOf(ary[7]),Float.valueOf(ary[8]),Float.valueOf(ary[9]), ary[10], ary[11]));
					}
					adapter=new HistoryAdapter(HisVerticalActivity.this, list);
					lvlist.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(HisVerticalActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.his_vertical, menu);
		return true;
	}

}
