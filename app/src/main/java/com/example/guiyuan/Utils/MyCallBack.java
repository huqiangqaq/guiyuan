package com.example.guiyuan.Utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class MyCallBack extends RequestCallBack<String> {
	private int type;
	private Context context;
	private Spinner sp;
	public static final int FOODNAME = 1;
	public static final int FOODTYPE = 2;
	public static final int STORE = 3;

	public MyCallBack(Context context, int type, Spinner sp) {
		this.context = context;
		this.type = type;
		this.sp = sp;
	}

	@Override
	public void onFailure(HttpException e, String arg1) {
		e.printStackTrace();
	}

	@Override
	public void onSuccess(ResponseInfo<String> req) {
		if(type>3)return;
		String data = req.result;
		List<String> list = new ArrayList<String>();
		try {
			JSONObject json = new JSONObject(data);
			String str = json
					.getString(type == 1 ? "getGrainVarietiesListResult"
							: (type == 2 ? "getGrainPropertyListResult"
									: "getCargoNoListResult"));
			str = str.substring(1,str.length()-2);
			String[] arr = str.split("\\},");
			for (int i = 1; i < arr.length; i++) {
				String s = arr[i].substring(1);
				String[] ary = s.split(",");
				list.add(ary[1]);
			}
			sp.setAdapter(new ArrayAdapter<String>(context,
					android.R.layout.simple_spinner_dropdown_item, list));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
