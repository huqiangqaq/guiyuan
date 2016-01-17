package com.example.guiyuan.Activity.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cilico.tools.Nfcreceive;

import com.example.guiyuan.R;
import com.example.guiyuan.Activity.HisVerticalActivity;
import com.example.guiyuan.Activity.ZhiKuActivity;
import com.example.guiyuan.Activity.ZhiKuActivity.CustomQueryRequestCallback;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.MyCallBack;
import com.example.guiyuan.Utils.NetUtil;
import com.ichoice.nfcHandler.Constants;
import com.ichoice.nfcHandler.MainNfcHandler;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class ZhiKuFragment extends Fragment{
	TextView tv_his;
	TextView tv_che4;
	TextView tv_foodname;
	TextView tv_foodtype;
	TextView tv_level;
	TextView tv_rl;
	TextView tv_water;
	TextView tv_zazhi;
	Spinner sp_storenum;
	EditText et_kouliang;
	EditText et_zeng;
	TextView tv_confirm;
	TextView tv_shuaka;
	private Handler myhandler = new Handler();
	private Handler mnfcHandler = new MainNfcHandler();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_zhi_ku, container,false);
		tv_his = (TextView) view.findViewById(R.id.tv_his);
		tv_che4 = (TextView) view.findViewById(R.id.tv_che4);
		tv_foodname = (TextView) view.findViewById(R.id.tv_foodname);
		tv_foodtype = (TextView) view.findViewById(R.id.tv_foodtype);
		tv_level = (TextView) view.findViewById(R.id.tv_level);
		tv_rl = (TextView) view.findViewById(R.id.tv_rl);
		tv_water = (TextView) view.findViewById(R.id.tv_water);
		tv_zazhi = (TextView) view.findViewById(R.id.tv_zazhi);
		sp_storenum = (Spinner) view.findViewById(R.id.sp_storenum);
		tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
		tv_shuaka = (TextView) view.findViewById(R.id.tv_shuaka);
		
		Nfcreceive.m_handler = mnfcHandler;
		tv_his.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						HisVerticalActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		tv_shuaka.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String code = Nfcreceive.readSigOneBlock(
						Constants.PASSWORD, Constants.ADD);
				if (!"".equals(code)&&code!=null) {
					NetUtil.sendNetReqByGet(
							Constant.ZHIKU_ADDRESS + "/" + code,
							new CustomQueryRequestCallback());
					tv_confirm.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							int count = sp_storenum.getAdapter().getCount();
							if (count != 0)
								NetUtil.sendNetReqByGet(
										Constant.UPDATE_ADDRESS + "/" + code
												+ "/"
												+ sp_storenum.getSelectedItem(),
										new MyCallBack(getActivity(), 4,
												null));
						}
					});
				}
			}
		});
		return view;
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
						getActivity(), 3, sp_storenum));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}


}
