package com.example.guiyuan.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guiyuan.R;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.entity.HistoryVertical;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HistoryAdapter extends BaseAdapter{
	private Context context;
	private List<HistoryVertical> list;

	public HistoryAdapter() {
	}

	public HistoryAdapter(Context context,List<HistoryVertical> list){
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
			convertView=LayoutInflater.from(context).inflate(R.layout.item_his, null);
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)convertView.getTag();
		}
		ViewUtils.inject(viewholder, convertView);
		HistoryVertical hi=list.get(position);
		viewholder.tv_vernum.setText(hi.getCarnum());
		viewholder.tv_storeNum.setText("运往："+hi.getStorenum());
		viewholder.tv_date.setText(hi.getTime());
		viewholder.tv_foodname.setText(hi.getFoodvari());
		viewholder.tv_foodtype.setText(hi.getFoodtype());
		viewholder.tv_level.setText(hi.getLevel()+"");

		viewholder.tv_waterper.setText(hi.getWaterper()+"%");
		viewholder.tv_rongzhong.setText(hi.getRongweight()+"G/L");
		viewholder.tv_zazhi.setText(hi.getZazhi()+"%");

		viewholder.tv_maozhong.setText(hi.getMaozhong()+"KG");
		viewholder.tv_storeman.setText(hi.getStoreman());
		viewholder.tv_qianyang.setText(hi.getQianyangman());
		return convertView;
	}
	static class ViewHolder{
		@ViewInject(R.id.tv_verNum)TextView tv_vernum;
		@ViewInject(R.id.tv_storeNum)TextView tv_storeNum;
		@ViewInject(id.tv_time) TextView tv_date;
		@ViewInject(R.id.tv_foodname)TextView tv_foodname;
		@ViewInject(R.id.tv_foodtype)TextView tv_foodtype;
		@ViewInject(R.id.tv_level)TextView tv_level;
		@ViewInject(R.id.tv_waterper)TextView tv_waterper;
		@ViewInject(R.id.tv_rongzhong)TextView tv_rongzhong;
		@ViewInject(id.tv_zazhi)TextView tv_zazhi;
		@ViewInject(R.id.tv_maozhong)TextView tv_maozhong;
		@ViewInject(R.id.tv_storeman)TextView tv_storeman;
		@ViewInject(R.id.tv_qianyang)TextView tv_qianyang;
	}
}
