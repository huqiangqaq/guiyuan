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
//	@ViewInject(R.id.et_kouliang)
//	EditText et_kouliang;
//	@ViewInject(R.id.et_zeng)
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
	private static  boolean flag=false;
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
		if(!Constant.DEBUG_WITH_NO_NFC_DEVICE) {Nfcreceive.m_handler = mnfcHandler;}
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
				String code =Nfcreceive.readSigOneBlock(Constants.PASSWORD,Constants.ADD);

//				if(!Constant.DEBUG_WITH_NO_NFC_DEVICE) {
//					code = Nfcreceive.readSigOneBlock(
//						Constants.PASSWORD, Constants.ADD);}
//				else {
//					 code="55222222";
//				}
//				final String code ="5";
				mCode = code;
				Log.i("hjkkkj",mCode);
				if (!"".equals(mCode)) {
					NetUtil.sendNetReqByGet(
							Constant.ZHIKU_ADDRESS + "/" + mCode,
							new CustomQueryRequestCallback());
					tv_confirm.setVisibility(View.VISIBLE);
					tv_cancel.setVisibility(View.VISIBLE);
					tv_confirm.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							flag = false;
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
				flag = true;
				new MyThread().execute(Constant.TERMINATE_ADDRESS);
				//NetUtil.sendNetReqByGet(Constant.TERMINATE_ADDRESS + "/" + UserName + "/" + mCode, new MyCallBack(ZhiKuActivity.this, 4, null));
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
				if ((data.length())<55){
					Toast.makeText(ZhiKuActivity.this,"当前卡号无效，请换卡",Toast.LENGTH_SHORT).show();
				}else {
					data = data.substring(1, data.length() - 2);
					String[] arr = data.split("\\},");
					String s = arr[1].substring(1);
					String[] ary = s.split(",");
					if (("".equals(ary[0]))||("null".equals(ary[0]))){
						tv_che4.setText("");
					}else {
						tv_che4.setText(ary[0]);
					}
					if (("".equals(ary[1]))||("null".equals(ary[1]))){
						tv_foodname.setText("");
					}else {
						tv_foodname.setText(ary[1]);
					}
					if (("".equals(ary[4]))||("null".equals(ary[4]))){
						tv_level.setText("");
					}else {
						tv_level.setText(ary[4]);
					}
					if (("".equals(ary[3]))||("null".equals(ary[3]))){
						tv_foodtype.setText("");
					}else {
						tv_foodtype.setText(ary[3]);
					}
					if (("".equals(ary[5]))||("null".equals(ary[5]))){
						tv_water.setText("");
					}else {
						tv_water.setText(ary[5]);
					}
					if (("".equals(ary[6]))||("null".equals(ary[6]))){
						tv_rl.setText("");
					}else {
						tv_rl.setText(ary[6]);
					}
					if (("".equals(ary[7]))||("null".equals(ary[7]))){
						tv_zazhi.setText("");
					}else {
						tv_zazhi.setText(ary[7]);
					}

					NetUtil.sendNetReqByGet(Constant.STORE_ADDRESS, new MyCallBack(
							ZhiKuActivity.this, 3, sp_storenum));
				}

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
			HttpGetAndPost myhttAndPost;
			if (flag){
				myhttAndPost=new HttpGetAndPost(params[0]/*+File.separatorChar +"jj"*/,UserName+"/"+mCode);
			}else {
				myhttAndPost=new HttpGetAndPost(params[0]/*+File.separatorChar +"jj"*/,UserName+"/"+mCode+"/"+storenum);
			}


			myhttAndPost.getHttpClient();
			String  jsonStr=myhttAndPost.doPost();
			Log.i("CT_PDA_POST_DEMO", "RESP:" + jsonStr.toString());
			return jsonStr;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			String result = null;
			if (flag){
				result = JsonUtil.parseLoginResult("terminateTrade",s);
			}else {
				result = JsonUtil.parseLoginResult("updateCargoNoAndStatus",s);
			}

			//String result = JsonUtil.parseLoginResult(s);
			if ("true".equals(result)){
				Toast.makeText(getApplicationContext(),"操作成功",Toast.LENGTH_SHORT).show();
				tv_che4.setText("");
				tv_foodname.setText("");
				tv_level.setText("");
				tv_foodtype.setText("");
				tv_water.setText("");
				tv_rl.setText("");
				tv_zazhi.setText("");
				tv_confirm.setVisibility(View.GONE);
				tv_cancel.setVisibility(View.GONE);

			}else {
				Toast.makeText(getApplicationContext(),"操作失败",Toast.LENGTH_SHORT).show();
			}
		}
	}

}
