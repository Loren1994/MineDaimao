package com.example.loren.minesample.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.example.loren.minesample.App;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Victor on 2017/2/13. (ง •̀_•́)ง
 */

public final class HttpUtil {
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();
    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * HttpUtil get请求
     *
     * @param url              请求地址
     * @param responseCallback 请求回调
     */
    public static void get(String url, final NetCallBack responseCallback) {
        if (!isNetworkConnected(App.mContext) || url.isEmpty()) {
            onNoNetwork(responseCallback);
            return;
        }
        Log.d("", "get_url: " + url);
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null && e.getMessage().contains("Canceled") && !e.getMessage().contains("Socket closed"))
                    failure("httpGet", e, responseCallback);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    response(response, responseCallback);
                } catch (Exception e) {
                    Log.e("", e.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onFailure("解析数据失败");
                        }
                    });
                }
            }
        });

    }

    /**
     * HttpUtil post请求
     *
     * @param url              请求地址
     * @param json             请求body
     * @param responseCallback 请求回调
     */
    public static void post(String url, JSONObject json, final NetCallBack responseCallback) {
        if (!isNetworkConnected(App.mContext) || url.isEmpty()) {
            onNoNetwork(responseCallback);
            return;
        }
//        try {
//            json.put("key", UserInfo.token());
//            json.put("client", "android");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Log.d("", "post_url: " + url);
        System.out.println(json.toString());
        Request request = new Request.Builder().url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null && !e.getMessage().contains("Canceled") && !e.getMessage().contains("Socket closed"))
                    failure("httpPost", e, responseCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    response(response, responseCallback);
                } catch (Exception e) {
                    Log.e("", e.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onFailure("解析数据失败");
                        }
                    });
                }
            }
        });
    }

    /**
     * HttpUtil httpGet/post请求失败统一处理
     *
     * @param type             请求类型
     * @param e                异常
     * @param responseCallback 请求回调
     */
    private static void failure(String type, IOException e, final NetCallBack responseCallback) {
        Log.e("", type + "_error - " + e.getMessage());
        String msg = e.getMessage() != null ? e.getMessage() : "网络错误";
        if (msg.contains("failed to connect to")) {
            msg = "网络连接超时";
        }
        final String finalMsg = msg;
        handler.post(new Runnable() {
            @Override
            public void run() {
                responseCallback.onFailure(finalMsg);
            }
        });
    }

    /**
     * HttpUtil httpGet/post请求成功统一处理
     *
     * @param response         请求回复
     * @param responseCallback 请求回调
     * @throws Exception 抛出异常
     */
    private static void response(Response response, final NetCallBack responseCallback) throws Exception {
        final String result = response.body().string();
        if (!TextUtils.isEmpty(result)) {
            final JSONObject jo = new JSONObject(result);
            System.out.println(result);
            if (jo.has("errorCode")) {
//                final String message = jo.optString("message");
                if ((jo.optInt("errorCode")) == 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onSuccess(jo.toString());
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onFailure("错误码：" + jo.optInt("errorCode"));
                        }
                    });
                }
            } else {
                Log.e("", result);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        responseCallback.onFailure("请求错误");
                    }
                });
            }
        }
    }

    /**
     * HttpUtil 无网络处理
     *
     * @param callBack 请求回调
     */
    private static void onNoNetwork(NetCallBack callBack) {
        callBack.onFailure("网络未连接，请检查网络设置！");
    }

    /**
     * HttpUtil 检测网络是否链接
     *
     * @param context Context
     * @return boolean
     */
    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * HttpUtil 请求回调
     */
    public interface NetCallBack {
        void onSuccess(String json);

        void onFailure(String reason);
    }
}
