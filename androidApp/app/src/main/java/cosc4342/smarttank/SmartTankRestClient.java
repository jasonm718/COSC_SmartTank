package cosc4342.smarttank;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.client.HttpClient;

public class SmartTankRestClient {
    
    private static final String BASE_URL = "https://smarttank.herokuapp.com/";
    
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();
    private static boolean async;
    
    public static void setAsync(boolean status){
        async = status;
        
    }
    
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        
        if(async) {
            asyncHttpClient.get(getAbsoluteUrl(url), responseHandler);
        }else{
            syncHttpClient.get(getAbsoluteUrl(url), responseHandler);
        }
    }
    
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if(SmartTankRestClient.async) {
            asyncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
        }else {
            syncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
        }
    }
    
    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
