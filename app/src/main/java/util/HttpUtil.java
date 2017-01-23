package util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.example.loren.minesample.App;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
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
 * Created by fionera on 16-4-25
 */
public class HttpUtil {
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void get(String url, final NetCallBack responseCallback) {
        if (!isNetworkConnected(App.mContext) || url.isEmpty()) {
            onNoNetwork(responseCallback);
            return;
        }
        Logger.d("get_url: " + url + "&client=android");
        Request request = new Request.Builder().url(url + "&client=android").build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null && e.getMessage().contains("Canceled") && !e.getMessage().contains("Socket closed"))
                    failure("get", e, responseCallback);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    response(response, responseCallback);
                } catch (Exception e) {
                    Logger.e(e.toString());
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

    public static void post(String url, JSONObject json, final NetCallBack responseCallback) {
        if (!isNetworkConnected(App.mContext) || url.isEmpty()) {
            onNoNetwork(responseCallback);
            Log.d("loren", "App.mContext?=null : " + (App.mContext == null));
            Log.d("loren", "url.isEmpty() : " + (url.isEmpty()));
            return;
        }
        try {
//            json.put("key", SPUtil.getString("key"));
            json.put("client", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Logger.d("post_url: " + url);
        Logger.json(json.toString());
        Request request = new Request.Builder().url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null && !e.getMessage().contains("Canceled") && !e.getMessage().contains("Socket closed"))
                    failure("post", e, responseCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    response(response, responseCallback);
                } catch (Exception e) {
                    Logger.e(e.toString());
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

    private static void failure(String type, IOException e, final NetCallBack responseCallback) {
        Logger.e(type + "_error - " + e.getMessage());
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

    private static void response(Response response, final NetCallBack responseCallback) throws Exception {
        final String result = response.body().string();
        if (!TextUtils.isEmpty(result)) {
            final JSONObject jo = new JSONObject(result);
            Logger.json(result);
            if (jo.has("statusCode")) {
                final String message = jo.optString("statusMsg");
                if ((jo.optInt("statusCode")) == 200) {
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
                            responseCallback.onFailure(message);
                        }
                    });
                }
            } else {
                Logger.e(result);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        responseCallback.onFailure("请求错误");
                    }
                });
            }
        }
    }

    private static void onNoNetwork(NetCallBack callBack) {
        callBack.onFailure("网络未连接，请检查网络设置！");
    }

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

    public interface NetCallBack {
        void onSuccess(String json);

        void onFailure(String reason);
    }
}
