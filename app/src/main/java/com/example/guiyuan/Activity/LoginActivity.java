package com.example.guiyuan.Activity;

import java.util.HashMap;
import java.util.Map;

import com.example.guiyuan.Application.MyApplication;
import com.example.guiyuan.Base.BaseActivity;
import com.example.guiyuan.R;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.NetCheckUtil;
import com.example.guiyuan.Utils.PreferenceService;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends BaseActivity {
    private EditText etUserName,etPassWord;
    private Button btnLogin;
    private CheckBox checkBox;
    private PreferenceService preferenceService;
    private Map<String,String> map;
    private boolean isAutoLogin =false;
    private static boolean isExit = false;
    ProgressDialog progressDialog = null;
    private static String UserName ="";
    private static String PassWord="";
    private static String LoginResult;
    private static MyApplication application;
    private static AlertDialog alertDialog = null;
    private static AlertDialog.Builder builder = null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        builder = new AlertDialog.Builder(LoginActivity.this);
        init();
        /**
         * 测试2253397848
         *1223123
         * 测试提交
         */
        if (!NetCheckUtil.isConnected(this)){
            Toast.makeText(this,"当前无网络连接，请检查网络设置",Toast.LENGTH_SHORT).show();
        }

        preferenceService = new PreferenceService(getApplicationContext());
        map = new HashMap<String,String>();
        map = preferenceService.getPrefrences();
        etUserName.setText(map.get("UserName"));
        etPassWord.setText(map.get("PassWord"));
        if (map.size()>0){
            checkBox.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserName = etUserName.getText().toString();
                PassWord = etPassWord.getText().toString();
                if ("".equals(UserName)||"".equals(PassWord)){
                     alertDialog = builder.setTitle("提示信息:")
                            .setMessage("账号或密码不能为空！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    alertDialog.show();
                }else {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("登录中....");
                    progressDialog.show();
                    //判断是否勾选记住密码选项

                    preferenceService.save(etUserName.getText().toString(), etPassWord.getText().toString());


                    //判断账户身份及判断登录状态
                    new MyThread().execute(Constant.URL);
                }



//                HttpUtil.GetJsonFromNet(getApplicationContext(), LOGINIP, params, new HttpUtil.GetJsonCallBack() {
//                    @Override
//                    public void callback(String jsonStr) {
//                        Log.i("123++++++++++++++",jsonStr);
//                    }
//                });
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isAutoLogin = true;
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }
            }
        });

    }

    public void init(){
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassWord = (EditText) findViewById(R.id.etPassWord);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
    }

   class MyThread extends AsyncTask<String,Void,String>{
       @Override
       protected void onPreExecute() {
           super.onPreExecute();

       }

       @Override
       protected String doInBackground(String... params) {
           //新建http对象
           HttpGetAndPost  myhttAndPost=new HttpGetAndPost(params[0]/*+File.separatorChar +"jj"*/,"PDA/Login/"+UserName+"/"+PassWord);

           myhttAndPost.getHttpClient();
           String  jsonStr=myhttAndPost.doPost();
           Log.i("CT_PDA_POST_DEMO","RESP:"+jsonStr.toString());
           return jsonStr;
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
           LoginResult = JsonUtil.parseLoginResult(s);
           progressDialog.dismiss();
           application = MyApplication.getInstance();
           application.setUserName(UserName);
           application.setPassWord(PassWord);
           if (/*"1".equals(LoginResult)||*/"2".equals(LoginResult)){
               Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
               startActivity(intent);
               LoginActivity.this.finish();
           }else if ("1".equals(LoginResult)){
               Intent intent = new Intent(LoginActivity.this, QianyangActivity.class);
               startActivity(intent);
               LoginActivity.this.finish();
           }else if ("3".equals(LoginResult)){
               //Toast.makeText(getApplicationContext(),"你是门卫",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(LoginActivity.this, MenWeiActivity.class);
               startActivity(intent);
               LoginActivity.this.finish();
           }else {
              alertDialog = builder.setTitle("提示信息")
                       .setMessage("登陆失败，请检查网络")
                       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                           }
                       }).create();
               alertDialog.show();
           }
           Log.i("123+++++++++++", LoginResult.toString());
       }
   }
    //退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (!isExit){
                isExit = true;
                Toast.makeText(LoginActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(0, 2000);
            }else {
                finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}