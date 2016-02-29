package com.example.guiyuan.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guiyuan.Application.MyApplication;
import com.example.guiyuan.Base.BaseActivity;
import com.example.guiyuan.R;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.NetUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HongGangActivity extends BaseActivity {
    @ViewInject(R.id.sp_hgchepai)
    Spinner sp_hgchepai;
    @ViewInject(R.id.hg_carno)
    EditText hg_carno;
    @ViewInject(R.id.hg_maozhong)
    EditText hg_maozhong;
    @ViewInject(R.id.hg_pizhong)
    EditText hg_pizhong;
    @ViewInject(R.id.hg_weight)
    TextView hg_weight;
    @ViewInject(R.id.hg_Name)
    EditText hg_name;
    @ViewInject(R.id.sp_hongganta)
    Spinner sp_hongganta;
    @ViewInject(R.id.tvhg_reselect)
    TextView tvhg_reselect;
    @ViewInject(R.id.llhgcangku)
    LinearLayout llhgcangku;
    @ViewInject(R.id.red_hgstorename)
    TextView red_hgstorename;
    @ViewInject(R.id.red_hgfoodname)
    TextView red_hgfoodname;
    @ViewInject(R.id.red_hglevel)
    TextView red_hglevel;
    @ViewInject(R.id.red_hgwater)
    TextView red_hgwater;
    @ViewInject(R.id.tv_hgsubmit)
    TextView tv_hgsubmit;
    @ViewInject(R.id.tv_hgconfirm)
    TextView tv_hgconfirm;
    private static final int REQUEST_CODE = 2;
    private static String mStroeNum = null;
    private static String UserName, carPerson, carNo, hgtNo, Water;
    private static double maoWeight, piWeight, liangWeight;
    private static boolean flag;
    private String[] shengfen = {"辽", "吉", "京", "蒙", "黑", "津", "冀", "浙", "沪", "粤", "鲁", "晋", "豫", "军"};
    public static final int HGTNAME = 1;
    private static Map<String, String> map;
    private static MyApplication application;
    private AlertDialog dialog = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hong_gang);
        ViewUtils.inject(this);
        builder = new AlertDialog.Builder(HongGangActivity.this);
        application = MyApplication.getInstance();
        hg_weight.setText(0 + "");
        hg_maozhong.setText(0 + "");
        hg_pizhong.setText(0 + "");
//        Intent intent = getIntent();
//        UserName = intent.getStringExtra("UserName");
        UserName = application.getUserName();
        hg_maozhong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                maoWeight = Double.parseDouble(String.valueOf(hg_maozhong.getText()));
            }
        });
        hg_pizhong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                piWeight = Double.parseDouble(String.valueOf(hg_pizhong.getText()));
                double f = maoWeight - piWeight;
                BigDecimal b = new BigDecimal(f);
                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                hg_weight.setText(f1 + "");
            }
        });
        sp_hgchepai.setAdapter(new ArrayAdapter<String>(
                HongGangActivity.this,
                android.R.layout.simple_spinner_dropdown_item, shengfen));
        sp_hgchepai.setSelection(0);
        NetUtil.sendNetReqByGet(Constant.HGT_ARDRESS, new MyCallBack(
                HGTNAME));
        tv_hgconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = llhgcangku.isShown();
                if (!flag) {
                    HongGangActivity.this.startActivityForResult(new Intent(HongGangActivity.this, SelectStoreActivity.class), REQUEST_CODE);
                }
                tv_hgconfirm.setVisibility(View.GONE);

            }
        });
        tv_hgsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"122",Toast.LENGTH_SHORT).show();
                if (flag) {
                    //确认提交
                    map = new HashMap<String, String>();
                    carPerson = hg_name.getText().toString();
                    carNo = sp_hgchepai.getSelectedItem().toString() + hg_carno.getText().toString().toUpperCase();
                    hgtNo = sp_hongganta.getSelectedItem().toString();
                    for (int i = 0; i < sp_hongganta.getAdapter().getCount(); i++) {
                        map.put(sp_hongganta.getAdapter().getItem(i).toString(), String.valueOf(i + 1));

                    }
                    liangWeight = Double.parseDouble(String.valueOf(hg_maozhong.getText())) - Double.parseDouble(String.valueOf(hg_pizhong.getText()));
                    //maoWeight = Double.parseDouble(String.valueOf(hg_maozhong.getText()));
                    //piWeight = Double.parseDouble(String.valueOf(hg_pizhong.getText()));
                    //Water = red_hgwater.getText().toString();
                    //hg_weight.setText(liangWeight+"");
                    new MyThread().execute(Constant.URL);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            llhgcangku.setVisibility(View.VISIBLE);
            tvhg_reselect.setVisibility(View.VISIBLE);
            flag = llhgcangku.isShown();
            tvhg_reselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HongGangActivity.this.startActivityForResult(new Intent(HongGangActivity.this, SelectStoreActivity.class), REQUEST_CODE);
                }

            });
            String storenum = data.getStringExtra("storename");
            String name = data.getStringExtra("specials");
            String level = data.getStringExtra("level");
            Water = data.getStringExtra("water");
            mStroeNum = storenum;
            red_hgstorename.setText(storenum);
            red_hgfoodname.setText("品种：" + name);
            red_hglevel.setText("等级：" + level);
            red_hgwater.setText("水分：" + Water + "%");
            tv_hgsubmit.setVisibility(View.VISIBLE);
        }
    }

    class MyThread extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            //新建http对象
            HttpGetAndPost myhttAndPost = new HttpGetAndPost(params[0], "PDA" + File.separatorChar + "CreateHgrk/" + UserName + "/" + carPerson + "/" + carNo + "/" + map.get(hgtNo) + "/" + liangWeight
                    + "/" + maoWeight + "/" + piWeight + "/"
                    + Water + "/" + mStroeNum/* + ""*/);
            Map<String, String> newCreateHgrk = new HashMap<String, String>();
            newCreateHgrk.put("operationPersonID", UserName);
            newCreateHgrk.put("carPerson", carPerson);
            newCreateHgrk.put("carNo", carNo);
            newCreateHgrk.put("hgtNo", hgtNo);
            newCreateHgrk.put("liangWeight", String.valueOf(liangWeight));
            newCreateHgrk.put("maoWeight", String.valueOf(maoWeight));
            newCreateHgrk.put("piWeight", String.valueOf(piWeight));
            newCreateHgrk.put("water", Water);
            newCreateHgrk.put("desStoreHouse", mStroeNum);

            myhttAndPost.getHttpClient();
            String jsonStr = myhttAndPost.doPut(newCreateHgrk);
            Log.i("CT_PDA_POST_DEMO", "RESP:" + jsonStr.toString());
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //String result = JsonUtil.parseLoginResult(s);

            String result = JsonUtil.parseLoginResult("CreateHgrk", s);
            if (result.equals("true")) {
                dialog = builder.setTitle("提示信息")
                        .setMessage("操作成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                hg_carno.setText("");
                hg_maozhong.setText("");
                hg_pizhong.setText("");
                hg_weight.setText("");
                hg_name.setText("");
                llhgcangku.setVisibility(View.GONE);
                tvhg_reselect.setVisibility(View.GONE);
                tv_hgconfirm.setVisibility(View.VISIBLE);
            } else {
                dialog = builder.setTitle("提示信息")
                        .setMessage("操作成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        }
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
            List<String> list = new ArrayList<String>();
            //List<SpinnerDropDownList> spDDL=new ArrayList<SpinnerDropDownList>();
            try {
                JSONObject json = new JSONObject(data);
                String str = json
                        .getString("getHgtNoListResult");
                str = str.substring(1, str.length() - 2);
                String[] arr = str.split("\\},");
                for (int i = 1; i < arr.length; i++) {
                    String s = arr[i].substring(1);
                    String[] ary = s.split(",");
//                    if(!Constant.IS_CANGKUNUMBER_OPPWITH_CANGKUID) {
//                        spDDL.add(new SpinnerDropDownList(i,ary));
//                    }
//                    else {
//                        spDDL.add(new SpinnerDropDownList(i,type,ary));
//                    }
                    list.add(ary[1]);
                }
                switch (type) {
                    case HGTNAME:

                        sp_hongganta.setAdapter(new ArrayAdapter<String>(HongGangActivity.this, android.R.layout.simple_spinner_dropdown_item, list));
//                        sp_hongganta.setAdapter(new ArrayAdapter<SpinnerDropDownList>(
//                                HongGangActivity.this,
//                                android.R.layout.simple_spinner_dropdown_item,
//									/*list*/spDDL));
                        sp_hongganta.setSelection(0);

                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
