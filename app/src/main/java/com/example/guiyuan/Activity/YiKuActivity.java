package com.example.guiyuan.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guiyuan.Adapter.HistoryAdapter;
import com.example.guiyuan.R;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.NetUtil;
import com.example.guiyuan.Utils.SpinnerDropDownList;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YiKuActivity extends Activity {
    @ViewInject(R.id.sp_FromchepaiNum)
    Spinner sp_FromchepaiNum;
    @ViewInject(R.id.sp_TargetChePai)
    Spinner sp_TargetChePai;
    @ViewInject(R.id.ykWeight)
    EditText ykWeight;
    @ViewInject(R.id.ykSubmit)
    Button ykSubmit;
    public static final int STORE = 1;
    private static String operationPersonID, liangWeight, cargoNo, desStoreHouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_yi_ku);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        operationPersonID = intent.getStringExtra("UserName");
        NetUtil.sendNetReqByGet(Constant.STORE_ADDRESS, new MyCallBack(
                STORE));
        ykSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liangWeight = ykWeight.getText().toString();
                cargoNo = sp_FromchepaiNum.getSelectedItem().toString();
                desStoreHouse = sp_TargetChePai.getSelectedItem().toString();
                new MyThread().execute(Constant.URL);

            }
        });


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
                        .getString("getCargoNoListResult");
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
                    case STORE:
                        sp_FromchepaiNum
                                //.setAdapter(new ArrayAdapter<String>(
                                .setAdapter(new ArrayAdapter<SpinnerDropDownList>(
                                        YiKuActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,
									/*list*/spDDL));
                        sp_FromchepaiNum.setSelection(0);
                        sp_TargetChePai.setAdapter(new ArrayAdapter<SpinnerDropDownList>(YiKuActivity.this, android.R.layout.simple_spinner_dropdown_item
                                , spDDL));
                       sp_TargetChePai.setSelection(0);

                        break;

                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            HttpGetAndPost myhttAndPost = new HttpGetAndPost(params[0], "PDA" + File.separatorChar + "CreateNbyk/" + operationPersonID + "/" + liangWeight + "/" + cargoNo + "/" + desStoreHouse);
            Map<String, String> newCreateNbyk = new HashMap<String, String>();
            newCreateNbyk.put("operationPersonID", operationPersonID);
            newCreateNbyk.put("liangWeight", liangWeight);
            newCreateNbyk.put("cargoNo", cargoNo);
            newCreateNbyk.put("desStoreHouse", desStoreHouse);


            myhttAndPost.getHttpClient();
            String jsonStr = myhttAndPost.doPut(newCreateNbyk);
            Log.i("CT_PDA_POST_DEMO", "RESP:" + jsonStr.toString());
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //String result = JsonUtil.parseLoginResult(s);

            String result = JsonUtil.parseLoginResult("CreateNbyk", s);
            if (result.equals("true")) {
                Toast.makeText(getApplicationContext(), "操作成功", Toast.LENGTH_SHORT).show();

			/*	water.setText("");
				rongliang.setText("");
				zazhi.setText("");*/
            } else {
                Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
