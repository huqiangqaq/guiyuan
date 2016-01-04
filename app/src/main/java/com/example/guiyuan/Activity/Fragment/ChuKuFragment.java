package com.example.guiyuan.Activity.Fragment;

import com.example.guiyuan.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChuKuFragment extends Fragment {
	TextView tv_name;
	TextView tv_chepai;
	TextView tv_idenfi;
	TextView tv_foodname;
	TextView tv_level;
	TextView tv_water;
	TextView tv_price;
	TextView tv_weight;
	TextView tv_left;
	TextView shuaka;
	TextView tv_confirm;
	LinearLayout llcangku;
	TextView red_storename;
	TextView red_foodname;
	TextView red_level;
	TextView red_water;
	LinearLayout llwrap;
	TextView tv_reselect;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_chuku, container,false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
