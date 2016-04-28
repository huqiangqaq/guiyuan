package com.example.guiyuan.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.guiyuan.R;
import com.example.guiyuan.entity.Detail;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2016/4/12 10:16.
 */
public class WeightAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Detail> list = new ArrayList<Detail>();
    private OnListDel onListDel;
    public interface OnListDel{
        void OnDel(int size);
    }

    public void setOnListDel(OnListDel onListDel) {
        this.onListDel = onListDel;
    }

    public WeightAdapter(Context mcontext, List<Detail> list) {
        this.mcontext = mcontext;
        this.list = list;
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;
        if (convertView ==null){
            holder = new viewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.custom_dialog_item,parent,false);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_single = (TextView) convertView.findViewById(R.id.tv_single);
            holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            holder.btn_del = (ImageView) convertView.findViewById(R.id.btn_del);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        Detail detail = list.get(position);
        holder.tv_num.setText(detail.getNum()+"");
        holder.tv_single.setText(detail.getSingle_count());
        holder.tv_weight.setText(detail.getWeight());
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                AlertDialog dialog = builder.setTitle("提示信息")
                        .setMessage("确定删除此条称重记录吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                int j = list.get(position).getNum();
                                int i = DataSupport.delete(Detail.class,list.get(position).getNum()-1);
                                dialog.dismiss();
                                onListDel.OnDel(list.size());
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

            }
        });
        return convertView;
    }
    class viewHolder{
        TextView tv_num;
        TextView tv_single;
        TextView tv_weight;
        ImageView btn_del;
    }
}
