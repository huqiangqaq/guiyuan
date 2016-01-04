package com.example.guiyuan.Utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NetUtil {
	private static final int REQ_TIIME=2000;
	private static HttpUtils http;
	static{
		http=new HttpUtils(REQ_TIIME);
	}
	public static void sendNetReqByGet(String path,RequestCallBack<String> callback){
		http.send(HttpMethod.GET,path,callback);
	}
	public static void sendNetReqByPost(String path,RequestParams params,RequestCallBack<String> callback){
		http.send(HttpMethod.POST,path,params,callback);
	}
}
