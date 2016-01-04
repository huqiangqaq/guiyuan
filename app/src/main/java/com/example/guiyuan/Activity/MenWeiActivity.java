package com.example.guiyuan.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guiyuan.R;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.NetUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

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
	@ViewInject(R.id.tv_time)
	TextView tv_time;
	@ViewInject(R.id.btn_recycle)
	Button btn_recycle;
	@ViewInject(R.id.shuaka)
	TextView shuaka;
	private static String mCode="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_men_wei);
		ViewUtils.inject(this);
		shuaka.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "12323", 0).show();
				// TODO Auto-generated method stub
//				String code = Nfcreceive.readSigOneBlock(Constants.PASSWORD,
//				Constants.ADD);
				String code="1";
				mCode = code;
				if (code!=null&&!code.equals("")) {
					NetUtil.sendNetReqByGet(Constant.MENWEI_ADDRESS+"/"+code, new RequestCallBack<String>() {
						
						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// TODO Auto-generated method stub
							String str = arg0.result;
							JSONObject json;
							try {
								json = new JSONObject(str);
								String data = json
										.getString("getCarStatusByCardIdResult");
								data = data.substring(1,
										data.length() - 1);
								String[] arr = data.split("\\},");
								String s = arr[1].substring(1);
								String[] ary = s.split(",");
								tv_name.setText(arr[0]);
								tv_sex.setText(arr[1]);
								tv_idenfi.setText(arr[3]);
								tv_chepai.setText(arr[4]);
								tv_pinzhong.setText(arr[5]);
								tv_level.setText(arr[6]);
								tv_shuifen.setText(arr[7]);
								tv_maozhong.setText(arr[8]);
								tv_pizhong.setText(arr[9]);
								tv_date.setText(arr[10]);
								String type = arr[11];
								String status = arr[12];
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
}
