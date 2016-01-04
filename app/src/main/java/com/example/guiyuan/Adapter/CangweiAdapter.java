package com.example.guiyuan.Adapter;

import java.util.List;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guiyuan.R;
import com.example.guiyuan.Adapter.HistoryAdapter.ViewHolder;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.entity.Cangku;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CangweiAdapter extends BaseAdapter{
	private Context context;
	private List<Cangku> list;
	public CangweiAdapter(Context context,List<Cangku> list){
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewholder;
		if(convertView==null){
			viewholder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_selectstore, null);
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)convertView.getTag();
		}
		ViewUtils.inject(viewholder,convertView);
		Cangku hi=list.get(position);
		viewholder.tv_storename.setText(hi.getStorenum());
		viewholder.tv_level.setText(hi.getLevel()+"等");
		viewholder.tv_water.setText(hi.getWater()+"%水分");
		viewholder.tv_foodname.setText(hi.getFoodname());
		return convertView;
	}
	static class ViewHolder{
		@ViewInject(R.id.tv_storename)TextView tv_storename;
		@ViewInject(R.id.tv_level)TextView tv_level;
		@ViewInject(R.id.tv_water)TextView tv_water;
		@ViewInject(R.id.tv_foodname)TextView tv_foodname;
	}
}
