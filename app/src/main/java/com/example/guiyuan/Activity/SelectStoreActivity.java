package com.example.guiyuan.Activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guiyuan.R;
import com.example.guiyuan.Adapter.CangweiAdapter;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.R.menu;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.NetUtil;
import com.example.guiyuan.entity.Cangku;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SelectStoreActivity extends Activity {
	private List<Cangku> list;
	private CangweiAdapter adapter;
	@ViewInject(R.id.lvlist)ListView lvlist;
	@ViewInject(R.id.iv_back)ImageView iv_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_store);
		ViewUtils.inject(this);
		list=new ArrayList<Cangku>();
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SelectStoreActivity.this.finish();
			}
		});
		NetUtil.sendNetReqByGet(Constant.ALL_ADDRESS,new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> req) {
				String str=req.result;
				JSONObject json;
				try {
					json = new JSONObject(str);
					String data=json.getString("getAllCargoDetailResult");
					data=data.substring(1,data.length()-1);
					String[] arr=data.split("\\},");
					for(int i=1;i<arr.length;i++){
						String s=arr[i].substring(1);
						String[] ary=s.split(",");
						list.add(new Cangku(ary[0], ary[1],Integer.valueOf(ary[2]), ary[3], ary[4]));
					}
					adapter=new CangweiAdapter(SelectStoreActivity.this, list);
					lvlist.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		lvlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(list!=null&&list.size()>0){
					Intent intent=getIntent();
					Cangku c=list.get(position);
					intent.putExtra("storename",c.getStorenum());
					intent.putExtra("specials",c.getFoodname());
					intent.putExtra("level", c.getLevel());
					intent.putExtra("water", c.getWater());
					SelectStoreActivity.this.setResult(RESULT_OK, intent);
					SelectStoreActivity.this.finish();
				}
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_store, menu);
		return true;
	}

}
