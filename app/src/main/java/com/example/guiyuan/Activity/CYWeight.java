package com.example.guiyuan.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guiyuan.Adapter.WeightAdapter;
import com.example.guiyuan.Base.BaseActivity;
import com.example.guiyuan.R;
import com.example.guiyuan.Service.LongRunningService;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.entity.Detail;
import com.example.guiyuan.entity.Item;
import com.ichoice.nfcHandler.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cilico.tools.Nfcreceive;
import okhttp3.Call;

public class CYWeight extends BaseActivity implements View.OnClickListener,WeightAdapter.OnListDel {
    private TextView tv_num,tv_category,tv_count,tv_averageWieght,/*tv_pizhong,*/tv_averageAure,detail_num,detail_maozhong,detail_chupi,detail_jingzhong/*,tv_curr_Weight*/;
    private EditText et_singlecount,tv_curr_Weight,tv_pizhong;
    private Button btn_single,btn_end,btn_detail;
    private ImageView iv_cyWeight_back;
    private ListView lv_detail;
    private WeightAdapter adapter;
    private List<Detail> list = new ArrayList<Detail>();
    private List<Item> item_list = new ArrayList<Item>();
    private Intent intent;
    private int i = 1;
    String rfidGuid=null;//寻卡成功标志
    private String rfidCode = null;   //接受的卡号
    private String data = null;//后台轮询查到的重量
    private String end = null;
    private ProgressDialog progressDialog = null;
    double averageWeight = 0;
    double averageWeightAure = 0;
    double detail_baoshu = 0;
    // 定义接收的hander
    private Handler mnfcHandler = new MainNfcHandler();
//    private PreferenceService preferenceService;
//    private Map<String, String> map;
//    private String ip1,ip2,ip3,ip4,duankouip,IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cy_weight_update);
        Nfcreceive.m_handler = mnfcHandler;
        init();
        if (null==rfidGuid){
            Toast.makeText(CYWeight.this,"请放入IC卡", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void OpenService() {
        intent = new Intent(this,LongRunningService.class);
        intent.putExtra("carnum",rfidCode);
        startService(intent);
    }

    private void openReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.broadcast.RECEIVER_ACTION");
        registerReceiver(myReceiver, filter);
    }
    private void getNetConn(){
        OkHttpUtils.get().url(Constant.ITEM_URL+rfidCode).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(String response) {
                item_list = JsonUtil.pareJson(CYWeight.this,response);
                if (item_list.size()>0){
                    Log.d("cyweight",item_list.size()+"");
                    tv_num.setText(item_list.get(0).getItem_carnum());
                    tv_category.setText(item_list.get(0).getItem_category());
                    et_singlecount.setText(item_list.get(0).getItem_singlecount());
                    tv_pizhong.setText(item_list.get(0).getItem_pizhong());
                }

            }
        });
    }

    private void init() {
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_averageWieght = (TextView) findViewById(R.id.tv_averageWieght);
        tv_pizhong = (EditText) findViewById(R.id.tv_pizhong);
        tv_averageAure = (TextView) findViewById(R.id.tv_averageAure);
        detail_num = (TextView) findViewById(R.id.detail_num);
        detail_maozhong = (TextView) findViewById(R.id.detail_maozhong);
        detail_chupi = (TextView) findViewById(R.id.detail_chupi);
        detail_jingzhong = (TextView) findViewById(R.id.detail_jingzhong);
        et_singlecount = (EditText) findViewById(R.id.et_singlecount);
        btn_single = (Button) findViewById(R.id.btn_single);
        btn_end = (Button) findViewById(R.id.btn_end);
        btn_detail = (Button) findViewById(R.id.btn_detail);
        iv_cyWeight_back = (ImageView) findViewById(R.id.iv_cyWeight_back);
        iv_cyWeight_back.setOnClickListener(this);
        btn_single.setOnClickListener(this);
        btn_end.setOnClickListener(this);
        btn_detail.setOnClickListener(this);
        //后台轮询查询当前重量
        tv_curr_Weight = (EditText) findViewById(R.id.tv_curr_Weight);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail:
                if (rfidGuid==null){
                    Toast.makeText(CYWeight.this,"请先扫描IC卡", Toast.LENGTH_SHORT).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(CYWeight.this);
                builder.setIcon(R.drawable.ic_launcher);
                //通过layoutinflater来加载一个布局
                View view = LayoutInflater.from(CYWeight.this).inflate(R.layout.custom_dialog,null);
                builder.setView(view);
                lv_detail = (ListView) view.findViewById(R.id.lvcontent);
                adapter = new WeightAdapter(CYWeight.this,list);
                //适配器中list size的回调，当list remove时，通知ui刷新
                adapter.setOnListDel(this);
                lv_detail.setAdapter(adapter);
                builder.show();
                break;
            case R.id.btn_single:

                if (rfidGuid==null){
                    Toast.makeText(CYWeight.this,"请先扫描IC卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (item_list.size()==0){
                    Toast.makeText(CYWeight.this,"当前卡号无效，请更换IC卡",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog = new ProgressDialog(CYWeight.this);
                progressDialog.setMessage("提交称重结果....");
                progressDialog.show();
                int count1 = Integer.parseInt(tv_count.getText().toString());
                int count2 = Integer.parseInt(et_singlecount.getText().toString());
                //int count =(Integer.parseInt(tv_count.getText().toString()))/(Integer.parseInt(et_singlecount.getText().toString()));
                    Log.d("huqiang",i+"");
                //异步提交单次称重
                data = tv_curr_Weight.getText().toString();
                    OkHttpUtils.get().url(Constant.WEIGHT_SINGLE+rfidCode+"/"+i+"/"+data+"/"+et_singlecount.getText().toString()).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            String result = JsonUtil.parseResult_return(response);
                            if (TextUtils.equals("1",result)){
                                Toast.makeText(CYWeight.this,"称重成功", Toast.LENGTH_SHORT).show();
                                //由于设备未到位，先改成手工输入当前重量的值

                                double pizhong = Double.parseDouble(tv_pizhong.getText().toString());
                                //每次的操作记录存放到list中
                                Detail detail = new Detail(i+"",et_singlecount.getText().toString(),data);
                                list.add(detail);
                                double singlecount = Double.parseDouble(et_singlecount.getText().toString());
                                detail_baoshu = singlecount+detail_baoshu;
                                detail_num.setText(detail_baoshu+"");
                                tv_count.setText(list.size()+"");
                                detail_maozhong.setText(data+"KG");
                                double currentWeight = Double.parseDouble(data);
                                averageWeight = currentWeight+averageWeight;
                                BigDecimal b2 = new BigDecimal(averageWeight/detail_baoshu);
                                double f2 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                tv_averageWieght.setText(f2+"KG");
                                detail_chupi.setText(tv_pizhong.getText().toString()+"KG");
                                double jingzhong = currentWeight-(pizhong*detail_baoshu);
                                averageWeightAure = jingzhong+averageWeightAure;
                                detail_jingzhong.setText(parse2(currentWeight-(pizhong*(Integer.parseInt(et_singlecount.getText().toString()))))+"KG");
                                double averageAure = averageWeightAure/detail_baoshu;
                                BigDecimal b = new BigDecimal(averageAure);
                                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                tv_averageAure.setText(f1+"KG");
                                i++;
                            }else {
                                Toast.makeText(CYWeight.this,result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                break;
            case R.id.btn_end:
                if (rfidGuid==null){
                    Toast.makeText(CYWeight.this,"请先扫描IC卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i<list.size();i++){
                    StringBuilder sb = new StringBuilder();
                    String result=list.get(i).getId();
                    sb.append(result);
                    sb.append(",");
                    end+=sb.toString();

                }
                System.out.println(end);
                end = end.substring(4,end.length()-1);
                OkHttpUtils.get().url(Constant.WEIGHT_END+rfidCode+"/"+end).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        String result = JsonUtil.parseResut_end(response);
                        if (TextUtils.equals("1",result)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CYWeight.this);
                            AlertDialog dialog = builder.setTitle("提示信息")
                                    .setMessage("提交抽样称重结果成功")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();
                            dialog.show();
                            list.clear();
                            i = 1;
                        }else {
                            Toast.makeText(CYWeight.this,"提交结果失败，请重新提交", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.iv_back:
                this.finish();
                break;
        }
    }
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, LongRunningService.class);
            data = intent.getExtras().getString("data");
            Log.d("huqiang", data);
            tv_curr_Weight.setText(data);
            context.startService(i);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CYWeight","on destroy");
        if (rfidGuid!=null){
            unregisterReceiver(myReceiver);
            stopService(intent);
        }
        list.clear();
    }
    //回调通知ui刷新
    @Override
    public void OnDel(int size) {
        tv_count.setText(size+"");
        detail_num.setText((Integer.parseInt(et_singlecount.getText().toString()))*size+"");
    }

    /**
     * @author Administrator
     * 接收NFC消息：
     * dongqiwu在
     * */
    private class MainNfcHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==2) {
                rfidGuid=(String)msg.obj;
                Toast.makeText(CYWeight.this,"已刷卡", Toast.LENGTH_SHORT).show();
                rfidCode= Nfcreceive.readSigOneBlock(Constants.PASSWORD, Constants.ADD);    //获取卡号
                //拿到卡号开启后台轮询服务
//                openReceiver();
//                OpenService();
                Log.d("MainActivity",rfidCode);
                getNetConn();
                //txt_code.setText((String)msg.obj);
                //m_nCount++;
                //txt_scancount.setText("Scanning:"+String.valueOf(m_nCount));
                //将结果保存到数据库：
                //清空状态：
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        //txt_code.setText("");
//                    }
//                }, 1000);
            }
            super.handleMessage(msg);
        }
    }

    private double parse2(double d){
        BigDecimal b = new BigDecimal(d);
        double f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }
}
