package com.example.guiyuan.Activity;

import java.util.HashMap;
import java.util.Map;

import com.example.guiyuan.MyApplication;
import com.example.guiyuan.R;
import com.example.guiyuan.Utils.Constant;
import com.example.guiyuan.Utils.HttpGetAndPost;
import com.example.guiyuan.Utils.JsonUtil;
import com.example.guiyuan.Utils.NetCheckUtil;
import com.example.guiyuan.Utils.PreferenceService;


import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
    private EditText etUserName,etPassWord;
    private Button btnLogin;
    private CheckBox checkBox;
    //private static String UserName,PassWord;
    private PreferenceService preferenceService;
    private Map<String,String> map;
    private boolean isAutoLogin =false;
    private static String LOGINIP ="";
    ProgressDialog dialog = null;
    String url ="http://192.168.1.51:7000";
    private static String UserName ="";
    private static String PassWord="";
    private static String LoginResult;
    private static String[] CangHao = {};
    private static MyApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
        if (!NetCheckUtil.isConnected(this)){
            //btnLogin.setEnabled(false);
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
        dialog = new ProgressDialog(this);
        dialog.setMessage("登录中....");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                UserName = etUserName.getText().toString();
                PassWord = etPassWord.getText().toString();
                //判断是否勾选记住密码选项

                    preferenceService.save(etUserName.getText().toString(), etPassWord.getText().toString());


                //判断账户身份及判断登录状态
                new MyThread().execute(Constant.URL);


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
           dialog.dismiss();
           application = MyApplication.getInstance();
           application.setUserName(UserName);
           application.setPassWord(PassWord);
           if (/*"1".equals(LoginResult)||*/"2".equals(LoginResult)){
               Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
               intent.putExtra("UserName", UserName);
               startActivity(intent);
               LoginActivity.this.finish();
           }else if ("1".equals(LoginResult)){
               Intent intent = new Intent(LoginActivity.this, QianyangActivity.class);
               intent.putExtra("UserName", UserName);
               startActivity(intent);
               LoginActivity.this.finish();
           }else if ("3".equals(LoginResult)){
               //Toast.makeText(getApplicationContext(),"你是门卫",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(LoginActivity.this, MenWeiActivity.class);
               intent.putExtra("UserName", UserName);
               startActivity(intent);
               LoginActivity.this.finish();
           }else {
               Toast.makeText(LoginActivity.this, "登录失败,请检查网络", Toast.LENGTH_SHORT).show();
           }
           Log.i("123+++++++++++", LoginResult.toString());
       }
   }
}