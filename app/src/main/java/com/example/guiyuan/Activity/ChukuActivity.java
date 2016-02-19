package com.example.guiyuan.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guiyuan.Application.MyApplication;
import com.example.guiyuan.R;
import com.example.guiyuan.R.id;
import com.example.guiyuan.R.layout;
import com.example.guiyuan.R.menu;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.MyCallBack;
import com.example.guiyuan.Utils.NetUtil;
import com.ichoice.nfcHandler.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import cilico.tools.Nfcreceive;

public class ChukuActivity extends Activity {
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_chepai)
    TextView tv_chepai;
    @ViewInject(R.id.tv_idenfi)
    TextView tv_idenfi;
    @ViewInject(R.id.tv_foodname)
    TextView tv_foodname;
    @ViewInject(R.id.tv_level)
    TextView tv_level;
    @ViewInject(R.id.tv_water)
    TextView tv_water;
    @ViewInject(R.id.tv_price)
    TextView tv_price;
    @ViewInject(R.id.tv_weight)
    TextView tv_weight;
    @ViewInject(R.id.tv_left)
    TextView tv_left;
    @ViewInject(R.id.shuaka)
    TextView shuaka;
    @ViewInject(R.id.tv_confirm)
    TextView tv_confirm;
    @ViewInject(R.id.llcangku)
    LinearLayout llcangku;
    @ViewInject(R.id.red_storename)
    TextView red_storename;
    @ViewInject(R.id.red_foodname)
    TextView red_foodname;
    @ViewInject(R.id.red_level)
    TextView red_level;
    @ViewInject(R.id.red_water)
    TextView red_water;
    @ViewInject(R.id.llwrap)
    LinearLayout llwrap;
    @ViewInject(R.id.tv_reselect)
    TextView tv_reselect;
    @ViewInject(R.id.tv_cancel)
    TextView tv_cancel;
    private static final int REQUEST_CODE = 1;
    private static String mCode = "";
    private static String mStroeNum = "";
    private static String UserName;
    private static boolean flag = false;
    private static MyApplication application;
    private static AlertDialog alertDialog = null;
    private static AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chuku);
        builder = new AlertDialog.Builder(ChukuActivity.this);
        ViewUtils.inject(this);
        application = MyApplication.getInstance();
        shuaka.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = Nfcreceive.readSigOneBlock(Constants.PASSWORD,
                        Constants.ADD);
//				String code="2";
                mCode = code;
                if (code != null && !code.equals("")) {
                    NetUtil.sendNetReqByGet(Constant.CONTACT_ADDRESS + "/" + code,
                            new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> req) {
                                    String str = req.result;
                                    JSONObject json;
                                    try {
                                        json = new JSONObject(str);
                                        String data = json
                                                .getString("getSaleInfomationResult");
                                        if (data.length() < 80) {
                                            alertDialog = builder.setTitle("提示信息:")
                                                    .setMessage("当前卡号无效，请换卡")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            alertDialog.dismiss();
                                                        }
                                                    }).create();
                                            alertDialog.show();
                                        } else {
                                            data = data.substring(1,
                                                    data.length() - 2);
                                            String[] arr = data.split("\\},");
                                            String s = arr[1].substring(1);
                                            String[] ary = s.split(",");
                                            if (("".equals(ary[0])) || ("null".equals(ary[0]))) {
                                                tv_name.setText("");
                                            } else {
                                                tv_name.setText(ary[0]);
                                            }
                                            if (("".equals(ary[1])) || ("null".equals(ary[1]))) {
                                                tv_chepai.setText("");
                                            } else {
                                                tv_chepai.setText(ary[1]);
                                            }
                                            if (("".equals(ary[2])) || ("null".equals(ary[2]))) {
                                                tv_idenfi.setText("");
                                            } else {
                                                tv_idenfi.setText(ary[2]);
                                            }
                                            if (("".equals(ary[3])) || ("null".equals(ary[3]))) {
                                                tv_foodname.setText("");
                                            } else {
                                                tv_foodname.setText(ary[3]);
                                            }
                                            if (("".equals(ary[4])) || ("null".equals(ary[4]))) {
                                                tv_level.setText("");
                                            } else {
                                                tv_level.setText(ary[4]);
                                            }
                                            if (("".equals(ary[5])) || ("null".equals(ary[5]))) {
                                                tv_water.setText("" + "%");
                                            } else {
                                                tv_water.setText(ary[5] + "%");
                                            }
                                            if (("".equals(ary[8])) || ("null".equals(ary[8]))) {
                                                tv_price.setText("" + "元");
                                            } else {
                                                tv_price.setText(ary[8] + "元");
                                            }
                                            if (("".equals(ary[9])) || ("null".equals(ary[9]))) {
                                                tv_weight.setText("" + "KG");
                                            } else {
                                                tv_weight.setText(ary[9] + "KG");
                                            }
                                            if (("".equals(ary[10])) || ("null".equals(ary[10]))) {
                                                tv_left.setText("" + "KG");
                                            } else {
                                                tv_left.setText(ary[10] + "KG");
                                            }
                                            tv_confirm.setVisibility(View.VISIBLE);
                                            shuaka.setVisibility(View.GONE);
                                            tv_cancel.setVisibility(View.VISIBLE);
                                            tv_reselect.setVisibility(View.VISIBLE);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(HttpException e,
                                                      String arg1) {
                                    e.printStackTrace();
                                }
                            });
                }
            }
        });
        tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = llcangku.isShown();
                if (!flag)
                    ChukuActivity.this.startActivityForResult(new Intent(ChukuActivity.this, SelectStoreActivity.class), REQUEST_CODE);
                else {
                    //确认提交
//					Intent intent = getIntent();
//					UserName = intent.getStringExtra("UserName");
                    UserName = application.getUserName();
                    System.out.println(mStroeNum);
                    System.out.println(mCode);
                    flag = false;
                    new MyThread().execute(Constant.UPDATE_ADDRESS);
//					NetUtil.sendNetReqByGet(
//							Constant.UPDATE_ADDRESS + "/"+UserName+"/" + mCode
//									+ "/"
//									+ mStroeNum,
//							new MyCallBack(ChukuActivity.this, 4,
//									null));
//					Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                System.out.println(mCode);
                //NetUtil.sendNetReqByGet(Constant.TERMINATE_ADDRESS+"/"+UserName+"/"+mCode, new MyCallBack(ChukuActivity.this, 4, null));
                new MyThread().execute(Constant.TERMINATE_ADDRESS);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chuku, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            llcangku.setVisibility(View.VISIBLE);
            tv_reselect.setVisibility(View.VISIBLE);
            tv_confirm.setText("确认");

            tv_reselect.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChukuActivity.this.startActivityForResult(new Intent(ChukuActivity.this, SelectStoreActivity.class), REQUEST_CODE);
                }
            });
            String storenum = data.getStringExtra("storename");
            mStroeNum = storenum;
            String name = data.getStringExtra("specials");
            String level = data.getStringExtra("level");
            String water = data.getStringExtra("water");
            red_storename.setText(storenum);
            red_foodname.setText("品种:" + name);
            red_level.setText("等级:" + level);
            red_water.setText("水分:" + water + "%");
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
            HttpGetAndPost myhttAndPost;
            if (flag) {
                myhttAndPost = new HttpGetAndPost(params[0]/*+File.separatorChar +"jj"*/, UserName + "/" + mCode);
            } else {
                myhttAndPost = new HttpGetAndPost(params[0]/*+File.separatorChar +"jj"*/, UserName + "/" + mCode + "/" + mStroeNum + "0");
            }


            myhttAndPost.getHttpClient();
            String jsonStr = myhttAndPost.doPost();
            Log.i("CT_PDA_POST_DEMO", "RESP:" + jsonStr.toString());
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String result = null;
            if (flag) {
                result = JsonUtil.parseLoginResult("terminateTrade", s);
            } else {
                result = JsonUtil.parseLoginResult("updateCargoNoAndStatus", s);
            }

            //String result = JsonUtil.parseLoginResult(s);
            if ("true".equals(result)) {
                alertDialog = builder.setTitle("提示信息")
                        .setMessage("操作成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        }).create();
                alertDialog.show();
                tv_name.setText("");
                tv_chepai.setText("");
                tv_idenfi.setText("");
                tv_foodname.setText("");
                tv_level.setText("" + "级");
                tv_water.setText("" + "%");
                tv_price.setText("" + "元");
                tv_weight.setText("" + "KG");
                tv_left.setText("" + "KG");
                llcangku.setVisibility(View.GONE);
                tv_reselect.setVisibility(View.GONE);
                tv_cancel.setVisibility(View.GONE);
                shuaka.setVisibility(View.VISIBLE);
                tv_confirm.setVisibility(View.VISIBLE);
                tv_reselect.setVisibility(View.GONE);
            } else {
                alertDialog = builder.setTitle("提示信息")
                        .setMessage("操作失败")
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
