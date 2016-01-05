package com.example.guiyuan.Activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
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
import com.example.guiyuan.Utils.NetUtil;
import com.ichoice.nfcHandler.Constants;
import com.ichoice.nfcHandler.MainNfcHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

public class QianyangActivity extends Activity {
	@ViewInject(R.id.tv_his)
	TextView history;
	@ViewInject(R.id.sp_foodname)
	Spinner sp_foodname;
	@ViewInject(R.id.sp_foodtype)
	Spinner sp_foodtype;
	@ViewInject(R.id.sp_store)
	Spinner sp_store;
	@ViewInject(R.id.tv_wait)
	TextView tv_wait;
	@ViewInject(R.id.carno)
	EditText carno;
	@ViewInject(R.id.water)
	EditText water;
	@ViewInject(R.id.rongliang)
	EditText rongliang;
	@ViewInject(R.id.zazhi)
	EditText zazhi;
	@ViewInject(R.id.shuaka)
	TextView shuaka;
	@ViewInject(R.id.sp_chepai)
	Spinner sp_chepai;
	@ViewInject(R.id.sp_chepaiNum)
	Spinner sp_chepaiNum;
	public static final int FOODNAME = 1;
	public static final int FOODTYPE = 2;
	public static final int STORE = 3;
	private Handler mnfcHandler = new MainNfcHandler();
	private String[] shengfen = {"辽","吉","京","蒙","黑","津","冀","浙","沪","粤","鲁","晋","豫","军"};
	private String[] num = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianyang);
		ViewUtils.inject(this);
		init();
		sp_chepai.setAdapter(new ArrayAdapter<String>(
				QianyangActivity.this,
				android.R.layout.simple_spinner_dropdown_item, shengfen));
		sp_chepaiNum.setAdapter(new ArrayAdapter<String>(
				QianyangActivity.this,
				android.R.layout.simple_spinner_dropdown_item, num));		
	}
	
	public void init() {
		Nfcreceive.m_handler = mnfcHandler;
		history.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QianyangActivity.this,
						HisVerticalActivity.class);
				QianyangActivity.this.startActivity(intent);
			}
		});
		shuaka.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String code = Nfcreceive.readSigOneBlock(Constants.PASSWORD,
						Constants.ADD);
//				String code = "1";
				if (code!=null&&!code.equals("")) {
					tv_wait.setText(code);
					String carnum = sp_chepai.getSelectedItem().toString()+sp_chepaiNum.getSelectedItem().toString()+carno.getText().toString();
					String foodname = (String) sp_foodname.getSelectedItem();
					String foodtype = (String) sp_foodtype.getSelectedItem();
					String storenum = (String) sp_store.getSelectedItem();
					String waters = water.getText().toString();
					String rl = rongliang.getText().toString();
					String zz = zazhi.getText().toString();
					Intent intent = getIntent();
					String UserName = intent.getStringExtra("UserName");
					String path = Constant.BASE_ADDRESS + "CreateAssay/"+UserName
							+ carnum + "/" + waters + "/" + rl + "/" + zz
							+ "/" + storenum + "/" + foodname + "/"
							+ foodtype + "/" + code + "";
					NetUtil.sendNetReqByGet(path, new MyCallBack(4));
					Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
					carno.setText("");
					water.setText("");
					rongliang.setText("");
					zazhi.setText("");
					
				}
			}
		});
		NetUtil.sendNetReqByGet(Constant.FOODNAME_ADDRESS, new MyCallBack(
				FOODNAME));
		NetUtil.sendNetReqByGet(Constant.FOODTYPE_ADDRESS, new MyCallBack(
				FOODTYPE));
		NetUtil.sendNetReqByGet(Constant.STORE_ADDRESS, new MyCallBack(STORE));
	}

	class MyCallBack extends RequestCallBack<String> {
		private int type;

		public MyCallBack(int type) {
			this.type = type;
		}

		@Override
		public void onFailure(HttpException e, String arg1) {
			e.printStackTrace();
		}

		@Override
		public void onSuccess(ResponseInfo<String> req) {
			String data = req.result;
			List<String> list = new ArrayList<String>();
			try {
				JSONObject json = new JSONObject(data);
				String str = json
						.getString(type == 1 ? "getGrainVarietiesListResult"
								: (type == 2 ? "getGrainPropertyListResult"
										: "getCargoNoListResult"));
				String[] arr = str.split("\\},");
				for (int i = 1; i < arr.length; i++) {
					String s = arr[i].substring(1);
					String[] ary = s.split(",");
					list.add(ary[1]);
				}
				switch (type) {
				case FOODNAME:
					sp_foodname
							.setAdapter(new ArrayAdapter<String>(
									QianyangActivity.this,
									android.R.layout.simple_spinner_dropdown_item,
									list));
					break;
				case FOODTYPE:
					sp_foodtype
							.setAdapter(new ArrayAdapter<String>(
									QianyangActivity.this,
									android.R.layout.simple_spinner_dropdown_item,
									list));
					break;
				case STORE:
					sp_store.setAdapter(new ArrayAdapter<String>(
							QianyangActivity.this,
							android.R.layout.simple_spinner_dropdown_item, list));
					break;
				default:
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qianyang, menu);
		return true;
	}

}
