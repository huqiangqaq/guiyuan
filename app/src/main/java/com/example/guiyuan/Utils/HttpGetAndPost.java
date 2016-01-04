package com.example.guiyuan.Utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 83916 on 2016/1/2.
 */
public class HttpGetAndPost
{
    // 定义变量
    private HttpParams httpParams;
    private HttpClient httpClient;

    private String baseUrl="";

    public HttpGetAndPost(String _url,String _extent)
    {
        baseUrl=_url;
        baseUrl+=File.separatorChar+_extent;

        Log.i("mylog", baseUrl);
    }
    public void HttpGetAndPost(JSONObject _jsonParam)
    {
        String jsonString = "";
        JSONArray jsonArray=new JSONArray();

        try {
            // if(_jsonParam.size()>0){

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name", _jsonParam.get("name"));
            jsonArray.put(jsonObject);

            jsonString=jsonArray.toString();
            // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  baseUrl=_url;
        //  baseUrl+=File.separatorChar+_extent;

        //  Log.i("mylog", baseUrl);
    }

    /**
     * Get获取
     *
     * @param baseUrl
     *            域名
     * @param params
     *            参数集合
     * @return
     * 返回的字符串
     */
    public String doGet(Map params)
    {
		/* 建立HTTPGet对象 */
        String paramStr = "";
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            paramStr += paramStr =File.separatorChar + key.toString() + /*File.separatorChar + */val.toString();
            Log.i("mylog1111111111", paramStr);
        }
        if (!paramStr.equals(""))
        {
            baseUrl += paramStr;

            Log.i("mylog", baseUrl);
        }
        HttpGet httpRequest = new HttpGet(baseUrl);
        String strResult = "doGetError";
        try
        {
			/* 发送请求并等待响应 */
            HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
				/* 读返回数据 */
                strResult = EntityUtils.toString(httpResponse.getEntity());

            }
            else
            {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            }
        }
        catch (ClientProtocolException e)
        {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }
        catch (IOException e)
        {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }
        catch (Exception e)
        {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }

        Log.v("strResult", strResult);

        return strResult;
    }

    /**
     * Post方式访问页面
     * @param baseUrl
     * 地址
     * @param params
     * 参数
     * @return
     */
    //public String doPost(List<NameValuePair> params)
    public String doPost(/*String params*/)
    {
		/* 建立HTTPPost对象 */
        HttpPost httpRequest = new HttpPost(baseUrl);
        String strResult = "doPostError";
        try
        {
			/* 添加请求参数到请求对象 */
            //httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//			String s="{{\"userName\",\"bgy1\"},{\"password\",\"123456\"}}";
//			httpRequest.setEntity(new StringEntity(s));
//			Log.i("CT_PDA_POST_DEMO", "REQ:"+s);
            //httpRequest.setEntity(new UrlEncodedFormEntity("",HTTP.UTF_8));
			/* 发送请求并等待响应 */
            HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
				/* 读返回数据 */
                strResult = EntityUtils.toString(httpResponse.getEntity());

            } else
            {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            }
        }
        catch (ClientProtocolException e)
        {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }
        catch (IOException e)
        {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }
        catch (Exception e)
        {
            strResult = e.getMessage().toString();
            e.printStackTrace();
        }

        Log.v("strResult", strResult);

        return strResult;
    }

    public HttpClient getHttpClient()
    {

        // 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
        this.httpParams = new BasicHttpParams();

        // 设置连接超时和 Socket 超时，以及 Socket 缓存大小
        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

        // 设置重定向，缺省为 true

        HttpClientParams.setRedirecting(httpParams, true);

        // 设置 user agent

        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams, userAgent);

        // 创建一个 HttpClient 实例

        // 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient

        // 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient

        httpClient = new DefaultHttpClient(httpParams);

        return httpClient;
    }
}
