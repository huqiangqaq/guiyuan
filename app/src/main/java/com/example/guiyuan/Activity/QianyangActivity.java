package com.example.guiyuan.Activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cilico.tools.Nfcreceive;

import com.example.guiyuan.Application.MyApplication;
import com.example.guiyuan.Base.BaseActivity;
import com.example.guiyuan.R;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.R.menu;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.NetUtil;
import com.ichoice.nfcHandler.Constants;
import com.ichoice.nfcHandler.MainNfcHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.example.guiyuan.Utils.SpinnerDropDownList;

public class QianyangActivity extends BaseActivity {
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
	@ViewInject(id.rlMessage)
	RelativeLayout rlMessage;
	public static final int FOODNAME = 1;
	public static final int FOODTYPE = 2;
	public static final int STORE = 3;
	private Handler mnfcHandler = new MainNfcHandler();
	private String[] shengfen = {"辽","吉","京","蒙","黑","津","冀","浙","沪","粤","鲁","晋","豫","军"};
	private String[] num = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
	private static String carnum,foodname,foodtype,storenum,waters,rl,zz,UserName,code;
	private static MyApplication application;
	private static AlertDialog alertDialog = null;
	private static AlertDialog.Builder builder = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qianyang);
		builder = new AlertDialog.Builder(QianyangActivity.this);
		ViewUtils.inject(this);
		application = MyApplication.getInstance();
		UserName = application.getUserName();
		init();

		sp_chepai.setAdapter(new ArrayAdapter<String>(
				QianyangActivity.this,
				android.R.layout.simple_spinner_dropdown_item, shengfen));
		sp_chepai.setSelection(0);
		water.setText("20");
		rongliang.setText("675");
		zazhi.setText("1");
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
				/*String */
				if(!Constant.DEBUG_WITH_NO_NFC_DEVICE) {
				code = Nfcreceive.readSigOneBlock(Constants.PASSWORD,
						Constants.ADD);}
				else {
					code="55222222";
				}
//				code = "5";
				if (code!=null&&!code.equals("")) {
					rlMessage.setBackgroundColor(getResources().getColor(R.color.royalblue));
					tv_wait.setVisibility(View.VISIBLE);
					tv_wait.setText("点击此处提交化验结果");

					System.out.println(code);
					carnum = sp_chepai.getSelectedItem().toString()/*+sp_chepaiNum.getSelectedItem().toString()*/+carno.getText().toString().toUpperCase();
					//foodname = (String) sp_foodname.getSelectedItem();
					foodname = ((SpinnerDropDownList) sp_foodname.getSelectedItem()).getValue();
					
					//foodtype = (String) sp_foodtype.getSelectedItem();
					foodtype = ((SpinnerDropDownList) sp_foodtype.getSelectedItem()).getValue();

					//storenum = (String) sp_store.getSelectedItem();
					storenum = ((SpinnerDropDownList) sp_store.getSelectedItem()).getValue();
					
					waters = water.getText().toString();
					rl = rongliang.getText().toString();
					zz = zazhi.getText().toString();
					
				}
			}
		});

		tv_wait.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new MyThread().execute(Constant.URL);
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
			System.out.println("huqiang");
			e.printStackTrace();
		}

		@Override
		public void onSuccess(ResponseInfo<String> req) {
			String data = req.result;
			System.out.println(data);
			//List<String> list = new ArrayList<String>();
			List<SpinnerDropDownList> spDDL=new ArrayList<SpinnerDropDownList>();
			try {
				JSONObject json = new JSONObject(data);
				String str = json
						.getString(type == 1 ? "getGrainVarietiesListResult"
								: (type == 2 ? "getGrainPropertyListResult"
										: "getCargoNoListResult"));
				str=str.substring(1,str.length()-2);
				String[] arr = str.split("\\},");
				for (int i = 1; i < arr.length; i++) {
					String s = arr[i].substring(1);
				    String []ary = s.split(",");
				    if(!Constant.IS_CANGKUNUMBER_OPPWITH_CANGKUID) {
					    spDDL.add(new SpinnerDropDownList(i,ary));
				    }
				    else {
				    	spDDL.add(new SpinnerDropDownList(i,type,ary));
				    }
				//	list.add(ary[1]);
				}
				switch (type) {
				case FOODNAME:
					sp_foodname
							//.setAdapter(new ArrayAdapter<String>(
							.setAdapter(new ArrayAdapter<SpinnerDropDownList>(
									QianyangActivity.this,
									android.R.layout.simple_spinner_dropdown_item,
									/*list*/spDDL));
					sp_foodname.setSelection(0);
					
					break;
				case FOODTYPE:
					sp_foodtype
							//.setAdapter(new ArrayAdapter<String>(
						.setAdapter(new ArrayAdapter<SpinnerDropDownList>(
									QianyangActivity.this,
									android.R.layout.simple_spinner_dropdown_item,
									/*list*/spDDL));
					sp_foodtype.setSelection(0);
					break;
				case STORE:
					sp_store
							//.setAdapter(new ArrayAdapter<String>(
						.setAdapter(new ArrayAdapter<SpinnerDropDownList>(
							QianyangActivity.this,
							android.R.layout.simple_spinner_dropdown_item, 
							/*list*/spDDL));
					sp_store.setSelection(0);
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

	class MyThread extends AsyncTask<String,Void,String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			//新建http对象
			HttpGetAndPost myhttAndPost=new HttpGetAndPost(params[0],"PDA"+File.separatorChar +"CreateAssay/"+UserName+"/"+ carnum + "/" + waters + "/" + rl + "/" + zz
					+ "/" + storenum + "/" + foodname + "/"
					+ foodtype + "/" + code/* + ""*/);
			Map<String, String> newCreateAssay = new HashMap<String, String>();
			newCreateAssay.put("userName", UserName);
			newCreateAssay.put("carNo", carnum);
			newCreateAssay.put("moisture", waters);
			newCreateAssay.put("testWeight", rl);
			newCreateAssay.put("impurity", zz);
			newCreateAssay.put("cargoNo", storenum);
			newCreateAssay.put("grainVarieties", foodname);
			newCreateAssay.put("grainProperty", foodtype);
			newCreateAssay.put("cardNo", code);

			myhttAndPost.getHttpClient();
			String  jsonStr=myhttAndPost.doPut(newCreateAssay);
			Log.i("CT_PDA_POST_DEMO","RESP:"+jsonStr.toString());
			return jsonStr;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			//String result = JsonUtil.parseLoginResult(s);
			
			String result = JsonUtil.parseLoginResult("CreateAssay",s);
			if(result.equals("true")) {
				alertDialog = builder.setTitle("提示信息")
						.setMessage("操作成功")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								alertDialog.dismiss();
							}
						}).create();
				alertDialog.show();
				carno.setText("");
				tv_wait.setText("提交成功");

			}
			else {
				alertDialog = builder.setTitle("提示信息:")
						.setMessage("提交失败，该粮库卡可能正在使用中！")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								alertDialog.dismiss();
							}
						}).create();
				alertDialog.show();
			}
		}
	}
}
