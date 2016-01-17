package com.example.guiyuan.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guiyuan.R;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.MyCallBack;
import com.example.guiyuan.Utils.NetUtil;
import com.ichoice.nfcHandler.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import cilico.tools.Nfcreceive;

public class MenWeiActivity extends Activity {
	@ViewInject(R.id.iv_logomsg)
	ImageView iv_logomsg;
	@ViewInject(R.id.iv_message)
	ImageView iv_message;
	@ViewInject(R.id.tv_message)
	TextView tv_message;
	@ViewInject(R.id.tv_name)
	TextView tv_name;
	@ViewInject(R.id.tv_sex)
	TextView tv_sex;
	@ViewInject(R.id.tv_idenfi)
	TextView tv_idenfi;
	@ViewInject(R.id.tv_chepai)
	TextView tv_chepai;
	@ViewInject(R.id.tv_pinzhong)
	TextView tv_pinzhong;
	@ViewInject(R.id.tv_level)
	TextView tv_level;
	@ViewInject(R.id.tv_shuifen)
	TextView tv_shuifen;
	@ViewInject(R.id.tv_maozhong)
	TextView tv_maozhong;
	@ViewInject(R.id.tv_pizhong)
	TextView tv_pizhong;
	@ViewInject(R.id.tv_date)
	TextView tv_date;
	@ViewInject(R.id.btn_recycle)
	Button btn_recycle;
	@ViewInject(R.id.shuaka)
	TextView shuaka;
	@ViewInject(R.id.tv_minzu)
	TextView tv_minzu;
	@ViewInject(R.id.ll1)
	LinearLayout ll1;
	@ViewInject(R.id.llcontent)
	LinearLayout llcontent;
	private static String mCode="";
	private static String UserName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_men_wei);
		ViewUtils.inject(this);
		shuaka.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "12323", 0).show();
				// TODO Auto-generated method stub
				String code=null;
				if(!Constant.DEBUG_WITH_NO_NFC_DEVICE) { code = Nfcreceive.readSigOneBlock(Constants.PASSWORD,
						Constants.ADD);}
				else {
					code = "55222222";
				}
//				String code = "1";
				mCode = code;
				Log.i("hjljl",code);
				Intent intent = getIntent();
				UserName = intent.getStringExtra("UserName");
				if (code != null && !code.equals("")) {
					NetUtil.sendNetReqByGet(Constant.MENWEI_ADDRESS + "/" + code, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							ll1.setVisibility(View.VISIBLE);
							llcontent.setVisibility(View.VISIBLE);
							// TODO Auto-generated method stub
							String str = arg0.result;
							JSONObject json;
							try {
								json = new JSONObject(str);
								String data = json
										.getString("getCarStatusByCardIdResult");
								if (data.length()<60){
									Toast.makeText(MenWeiActivity.this,"当前卡号无效，请换卡",Toast.LENGTH_SHORT).show();
								}else {
									data = data.substring(1,
											data.length() - 2);
									String[] arr = data.split("\\},");
									String s = arr[1].substring(1);
									String[] ary = s.split(",");
									if (!"".equals(ary[0])){
										tv_name.setText(ary[0]);
									}else {
										tv_name.setText("");
									}
									if (!"".equals(ary[1])){
										tv_sex.setText(ary[1]);
									}else {
										tv_sex.setText("");
									}
									if (!"".equals(ary[2])){
										tv_minzu.setText(ary[2]);
									}else {
										tv_minzu.setText("");
									}
									if (!"".equals(ary[3])){
										tv_idenfi.setText(ary[3]);
									}else {
										tv_idenfi.setText("");
									}
									if (!"".equals(ary[4])){
										tv_chepai.setText(ary[4]);
									}else {
										tv_chepai.setText("");
									}
									if (!"".equals(ary[5])){
										tv_pinzhong.setText(ary[5]);
									}else {
										tv_pinzhong.setText("");
									}
									if (!"".equals(ary[6])){
										tv_level.setText(ary[6]);
									}else {
										tv_level.setText("");
									}
									if (!"".equals(ary[7])){
										tv_shuifen.setText(ary[7]+"%");
									}else {
										tv_shuifen.setText(""+"%");
									}
									if (!"".equals(ary[8])){
										tv_maozhong.setText(ary[8]+"KG");
									}else {
										tv_maozhong.setText(""+"KG");
									}
									if (!"".equals(ary[9])){
										tv_pizhong.setText(ary[9]+"KG");
									}else {
										tv_pizhong.setText(""+"KG");
									}
									if (!"".equals(ary[10])){
										tv_date.setText(ary[10]);
									}else {
										tv_date.setText("");
									}

									double pizhong,maozhong;
									if ("".equals(ary[9])){
										pizhong = 0;

									}else {
										pizhong = Double.parseDouble(ary[9]);
									}
									if ("".equals(ary[8])){
										maozhong = 0;

									}else {
										maozhong = Double.parseDouble(ary[8]);
									}

									String type = ary[11];
									String status = ary[12];
									double chengji = pizhong * maozhong;
									//入库判断
									if ("入库".equals(type)) {
										if ("5".equals(status)) {
											iv_logomsg.setBackgroundResource(R.drawable.in5);    //入库完成
											shuaka.setVisibility(View.GONE);
											btn_recycle.setVisibility(View.VISIBLE);
											tv_message.setText("交粮完成！可以出库！");
										} else if ("1".equals(status) || "2".equals(status) || "4".equals(status)) { //入库未完成
											iv_logomsg.setBackgroundResource(R.drawable.in124);
											tv_message.setText("交粮未完成！请去回皮！");
											tv_message.setTextColor(getResources().getColor(R.color.tomato));
											tv_pizhong.setText("请称重！");
											tv_pizhong.setTextColor(getResources().getColor(R.color.tomato));
										} else if ("3".equals(status) && chengji == 0) { 							//入库退粮未完成
											iv_logomsg.setBackgroundResource(R.drawable.in30);
											tv_message.setText("退粮未完成！请去回皮！");
											tv_message.setTextColor(getResources().getColor(R.color.tomato));
											tv_pizhong.setText("请称重！");
											tv_pizhong.setTextColor(getResources().getColor(R.color.tomato));
										} else if ("3".equals(status) && chengji != 0) {
											iv_logomsg.setBackgroundResource(R.drawable.in3);			//入库退粮完成
											btn_recycle.setVisibility(View.VISIBLE);
											shuaka.setVisibility(View.GONE);
											tv_message.setText("退粮完成！可以出库");
										}else if ("0".equals(status)){
											iv_logomsg.setBackgroundResource(R.drawable.in124);
											tv_message.setText("请登记身份！");
											tv_message.setTextColor(getResources().getColor(R.color.tomato));
										}
									}
									//出库判断
									if ("出库".equals(type)) {
										if ("5".equals(status)) {
											iv_logomsg.setBackgroundResource(R.drawable.out5);     			//出库完成
											btn_recycle.setVisibility(View.VISIBLE);
											shuaka.setVisibility(View.GONE);
											tv_message.setText("交粮完成！可以出库！");
										} else if ("1".equals(status) || "2".equals(status) || "4".equals(status)) {     //出库未完成
											iv_logomsg.setBackgroundResource(R.drawable.out12430);
											tv_message.setText("拉粮终止！请去称毛重！");
											tv_message.setTextColor(getResources().getColor(R.color.tomato));
											tv_pizhong.setText("请称重！");
											tv_pizhong.setTextColor(getResources().getColor(R.color.tomato));
										} else if ("3".equals(status) && chengji == 0) {
											iv_logomsg.setBackgroundResource(R.drawable.out12430);         //出库退粮未完成
											tv_message.setText("拉粮终止！请去称毛重！");
											tv_message.setTextColor(getResources().getColor(R.color.tomato));
											tv_pizhong.setText("请称重！");
											tv_pizhong.setTextColor(getResources().getColor(R.color.tomato));
										} else if ("3".equals(status) && chengji != 0) {					//出库退粮完成
											iv_logomsg.setBackgroundResource(R.drawable.out3);
											btn_recycle.setVisibility(View.VISIBLE);
											shuaka.setVisibility(View.GONE);
											tv_message.setText("拉粮完成！可以出库");
										}
									}
								}


							} catch (JSONException e) {
								// TODO: handle exception
							}
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		});

		btn_recycle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//NetUtil.sendNetReqByGet(Constant.RECYCLE_CARD+"/"+UserName+"/"+mCode, new MyCallBack(MenWeiActivity.this, 4, null));
				new MyThread().execute(Constant.RECYCLE_CARD);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.men_wei, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	class MyThread extends AsyncTask<String,Void,String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			//新建http对象
			HttpGetAndPost myhttAndPost=new HttpGetAndPost(params[0]/*+File.separatorChar +"jj"*/,UserName+"/"+mCode);

			myhttAndPost.getHttpClient();
			String  jsonStr=myhttAndPost.doPost();
			Log.i("CT_PDA_POST_DEMO", "RESP:" + jsonStr.toString());
			return jsonStr;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			String result = JsonUtil.parseLoginResult("recycleCard",s);
			if ("true".equals(result)){
				Toast.makeText(getApplicationContext(),"操作成功",Toast.LENGTH_SHORT).show();
				shuaka.setVisibility(View.VISIBLE);
				tv_name.setText("");
				tv_sex.setText("");
				tv_minzu.setText("");
				tv_idenfi.setText("");
				tv_chepai.setText("");
				tv_pinzhong.setText("");
				tv_level.setText("");
				tv_shuifen.setText(""+"%");
				tv_maozhong.setText(""+"KG");
				tv_pizhong.setText(""+"KG");
				tv_date.setText("");
				ll1.setVisibility(View.GONE);
				llcontent.setVisibility(View.GONE);
				btn_recycle.setVisibility(View.GONE);
			}else {
				Toast.makeText(getApplicationContext(),"操作失败",Toast.LENGTH_SHORT).show();
			}
		}
	}
}
