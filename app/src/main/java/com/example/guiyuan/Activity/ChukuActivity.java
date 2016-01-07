package com.example.guiyuan.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guiyuan.R;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.R.menu;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.MyCallBack;
import com.example.guiyuan.Utils.NetUtil;
import com.ichoice.nfcHandler.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import cilico.tools.Nfcreceive;

public class ChukuActivity extends Activity {
	@ViewInject(R.id.tv_name)
	TextView tv_name;
	@ViewInject(R.id.tv_chepai)
	TextView tv_chepai;
	@ViewInject(R.id.tv_idenfi)
	TextView tv_idenfi;
	@ViewInject(R.id.tv_foodname)
	TextView tv_foodname;
	@ViewInject(R.id.tv_level)
	TextView tv_level;
	@ViewInject(R.id.tv_water)
	TextView tv_water;
	@ViewInject(R.id.tv_price)
	TextView tv_price;
	@ViewInject(R.id.tv_weight)
	TextView tv_weight;
	@ViewInject(R.id.tv_left)
	TextView tv_left;
	@ViewInject(R.id.shuaka)
	TextView shuaka;
	@ViewInject(R.id.tv_confirm)
	TextView tv_confirm;
	@ViewInject(R.id.llcangku)
	LinearLayout llcangku;
	@ViewInject(R.id.red_storename)
	TextView red_storename;
	@ViewInject(R.id.red_foodname)
	TextView red_foodname;
	@ViewInject(R.id.red_level)
	TextView red_level;
	@ViewInject(R.id.red_water)
	TextView red_water;
	@ViewInject(R.id.llwrap)
	LinearLayout llwrap;
	@ViewInject(R.id.tv_reselect)
	TextView tv_reselect;
	@ViewInject(R.id.tv_cancel)
	TextView tv_cancel;
	private static final int REQUEST_CODE=1;
	private static String mCode="";
	private static String mStroeNum="";
	private static String UserName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chuku);
		ViewUtils.inject(this);
		shuaka.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String code = Nfcreceive.readSigOneBlock(Constants.PASSWORD,
						Constants.ADD);
//				String code="1";
				mCode = code;
				if (code != null && !code.equals("")) {
					NetUtil.sendNetReqByGet(Constant.CONTACT_ADDRESS+"/"+code,
							new RequestCallBack<String>() {
								@Override
								public void onSuccess(ResponseInfo<String> req) {
									String str = req.result;
									JSONObject json;
									try {
										json = new JSONObject(str);
										String data = json
												.getString("getSaleInfomationResult");
										data = data.substring(1,
												data.length() - 1);
										String[] arr = data.split("\\},");
										String s = arr[1].substring(1);
										String[] ary = s.split(",");
										tv_name.setText(ary[0]);
										tv_chepai.setText(ary[1]);
										tv_idenfi.setText(ary[2]);
										tv_foodname.setText(ary[3]);
										tv_level.setText(ary[4] + "级");
										tv_water.setText(ary[5] + "%");
										tv_price.setText(ary[6] + "元");
										tv_weight.setText(ary[7] + "吨");
										tv_left.setText(ary[8] + "吨");
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onFailure(HttpException e,
										String arg1) {
									e.printStackTrace();
								}
							});
				}
			}
		});
		tv_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean flag=llcangku.isShown();
				if(!flag)
					ChukuActivity.this.startActivityForResult(new Intent(ChukuActivity.this,SelectStoreActivity.class),REQUEST_CODE);
				else{
					//确认提交
					Intent intent = getIntent();
					UserName = intent.getStringExtra("UserName");
					System.out.println(mStroeNum);
					System.out.println(mCode);
					NetUtil.sendNetReqByGet(
							Constant.UPDATE_ADDRESS + "/"+UserName+"/" + mCode
									+ "/"
									+ mStroeNum,
							new MyCallBack(ChukuActivity.this, 4,
									null));
					Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		tv_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println(mCode);
				NetUtil.sendNetReqByGet(Constant.TERMINATE_ADDRESS+"/"+UserName+"/"+mCode, new MyCallBack(ChukuActivity.this, 4, null));
				Toast.makeText(getApplicationContext(), "退粮成功", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chuku, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){
			llcangku.setVisibility(View.VISIBLE);
			tv_reselect.setVisibility(View.VISIBLE);
			tv_confirm.setText("确认");
			tv_reselect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ChukuActivity.this.startActivityForResult(new Intent(ChukuActivity.this,SelectStoreActivity.class),REQUEST_CODE);
				}
			});
			String storenum=data.getStringExtra("storename");
			mStroeNum = storenum;
			String name=data.getStringExtra("specials");
			String level=data.getIntExtra("level", 0)+"";
			String water=data.getStringExtra("water");
			red_storename.setText(storenum);
			red_foodname.setText("品种:"+name);
			red_level.setText("等级:"+level+"等");
			red_water.setText("水分:"+water+"%");
		}
	}
}
