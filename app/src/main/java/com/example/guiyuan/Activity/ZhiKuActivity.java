package com.example.guiyuan.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cilico.tools.Nfcreceive;

import com.example.guiyuan.R;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.R.menu;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.MyCallBack;
import com.example.guiyuan.Utils.NetUtil;
import com.ichoice.nfcHandler.Constants;
import com.ichoice.nfcHandler.MainNfcHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ZhiKuActivity extends Activity {
	@ViewInject(R.id.tv_his)
	TextView tv_his;
	@ViewInject(R.id.tv_che4)
	TextView tv_che4;
	@ViewInject(R.id.tv_foodname)
	TextView tv_foodname;
	@ViewInject(R.id.tv_foodtype)
	TextView tv_foodtype;
	@ViewInject(R.id.tv_level)
	TextView tv_level;
	@ViewInject(R.id.tv_rl)
	TextView tv_rl;
	@ViewInject(R.id.tv_water)
	TextView tv_water;
	@ViewInject(R.id.tv_zazhi)
	TextView tv_zazhi;
	@ViewInject(R.id.sp_storenum)
	Spinner sp_storenum;
	@ViewInject(R.id.et_kouliang)
	EditText et_kouliang;
	@ViewInject(R.id.et_zeng)
	EditText et_zeng;
	@ViewInject(R.id.tv_confirm)
	TextView tv_confirm;
	@ViewInject(R.id.tv_shuaka)
	TextView tv_shuaka;
	@ViewInject(R.id.tv_change)
	TextView tv_change;
	@ViewInject(R.id.tv_cancel)
	TextView tv_cancel;
	private Handler myhandler = new Handler();
	private Handler mnfcHandler = new MainNfcHandler();
	private static String mCode;
	private static String UserName;
	private static String storenum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhi_ku);
		ViewUtils.inject(this);
		tv_confirm.setVisibility(View.GONE);
		tv_cancel.setVisibility(View.GONE);
		Intent intent = getIntent();
		UserName = intent.getStringExtra("UserName");
		Nfcreceive.m_handler = mnfcHandler;
		tv_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ZhiKuActivity.this,ChukuActivity.class);
				intent.putExtra("UserName", UserName);
				ZhiKuActivity.this.finish();
				startActivity(intent);
			}
		});
		tv_his.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ZhiKuActivity.this,
						HisVerticalActivity.class);
				ZhiKuActivity.this.startActivity(intent);
			}
		});
		
		tv_shuaka.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String code = Nfcreceive.readSigOneBlock(
						Constants.PASSWORD, Constants.ADD);
//				final String code ="1";
				mCode = code;
				if (!"".equals(code)&&code!=null) {
					NetUtil.sendNetReqByGet(
							Constant.ZHIKU_ADDRESS + "/" + code,
							new CustomQueryRequestCallback());
					tv_confirm.setVisibility(View.VISIBLE);
					tv_cancel.setVisibility(View.VISIBLE);
					tv_confirm.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							int count = sp_storenum.getAdapter().getCount();

							if (count != 0)
//								NetUtil.sendNetReqByGet(
//										Constant.UPDATE_ADDRESS + "/" +UserName+"/"+ mcode
//												+ "/"
//												+ sp_storenum.getSelectedItem(),
//										new MyCallBack(ZhiKuActivity.this, 4,
//												null));
							storenum = (String) sp_storenum.getSelectedItem();
							new MyThread().execute(Constant.UPDATE_ADDRESS);
						}
					});
				}
			}
		});
		tv_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println(mCode);
				new MyThread().execute(Constant.TERMINATE_ADDRESS);
				//NetUtil.sendNetReqByGet(Constant.TERMINATE_ADDRESS + "/" + UserName + "/" + mCode, new MyCallBack(ZhiKuActivity.this, 4, null));
				Toast.makeText(getApplicationContext(), "退粮成功", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public class CustomQueryRequestCallback extends RequestCallBack<String> {

		@Override
		public void onFailure(HttpException arg0, String arg1) {

		}
		@Override
		public void onSuccess(ResponseInfo<String> req) {
			String str = req.result;
			JSONObject json;
			try {
				json = new JSONObject(str);
				String data = json.getString("getCarInfomationByCardIdResult");
				data = data.substring(1, data.length() - 1);
				String[] arr = data.split("\\},");
				String s = arr[1].substring(1);
				String[] ary = s.split(",");
				tv_che4.setText(ary[0]);
				tv_foodname.setText(ary[1]);
				tv_level.setText(ary[2]);
				tv_foodtype.setText(ary[3]);
				tv_water.setText(ary[4]);
				tv_rl.setText(ary[5]);
				tv_zazhi.setText(ary[6]);
				NetUtil.sendNetReqByGet(Constant.STORE_ADDRESS, new MyCallBack(
						ZhiKuActivity.this, 3, sp_storenum));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zhi_ku, menu);
		return true;
	}

	class MyThread extends AsyncTask<String,Void,String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			//新建http对象
			HttpGetAndPost myhttAndPost=new HttpGetAndPost(params[0]/*+File.separatorChar +"jj"*/,UserName+"/"+mCode+"/"+storenum);

			myhttAndPost.getHttpClient();
			String  jsonStr=myhttAndPost.doPost();
			Log.i("CT_PDA_POST_DEMO", "RESP:" + jsonStr.toString());
			return jsonStr;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			String result = JsonUtil.parseLoginResult(s);
		}
	}

}
