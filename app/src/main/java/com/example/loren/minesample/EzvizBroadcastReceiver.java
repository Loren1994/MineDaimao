package com.example.loren.minesample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.videogo.androidpn.Constants;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.EZAccessToken;
import com.videogo.openapi.bean.EZPushAlarmMessage;
import com.videogo.openapi.bean.EZPushBaseMessage;
import com.videogo.openapi.bean.EZPushTransferMessage;
import com.videogo.util.LogUtil;
import com.videogo.util.Utils;

/**
 * 监听广播
 * 
 * @author fangzhihua
 * @data 2013-1-17
 */
public class EzvizBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "EzvizBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String message = intent.getStringExtra("message_extra");
        if (action.equals("com.ezviz.push.sdk.android.intent.action.MESSAGE")) {
            EZPushBaseMessage baseMessage =  EZOpenSDK.getInstance().parsePushMessage(message);
            if (baseMessage != null) {
                switch(baseMessage.getMessageType()) {
                    case 1:
                        EZPushAlarmMessage alarmMessage= (EZPushAlarmMessage) baseMessage;
                        Log.i(TAG, "onReceive: Push get alarm message:" + alarmMessage);
                        break;
                    case 99:
                        EZPushTransferMessage transferMessage = (EZPushTransferMessage) baseMessage;
                        Log.i(TAG, "onReceive: Push get transfer message:" + transferMessage);
                        break;
                    default:
                        Log.i(TAG, "onReceive: Push get other message:");
                }
            }
        }
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            EzvizAPI.getInstance().refreshNetwork();
        } else if(action.equals(Constant.ADD_DEVICE_SUCCESS_ACTION)) {
            String deviceId = intent.getStringExtra(IntentConsts.EXTRA_DEVICE_ID);
            Utils.showToast(context, context.getString(R.string.device_is_added, deviceId));
        } else if(action.equals(Constant.OAUTH_SUCCESS_ACTION)) {
            Intent toIntent = new Intent(context, YingshiActivity.class);
            toIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            //保存token及token超时时间
            EZOpenSDK openSdk = EZOpenSDK.getInstance();
            if(openSdk != null) {
                EZAccessToken token = openSdk.getEZAccessToken();
            	//保存token，获取超时时间，在token过期时重新获取
                LogUtil.infoLog(TAG, "t:" + token.getAccessToken().substring(0, 5) + " expire:" + token.getExpire());
            }
            context.startActivity(toIntent);

//            final Context fContext = context;
//            Thread thr = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {;
//                        final String app_key = "596372da86f84628945da2476960ed9a";
//                        final String push_secret = "cf5007cb-860f-424a-8536-cc95f75eb6ed";
//
//                        // push server地址从配置信息中拿到
//                        EZSDKConfiguration config = EzvizAPI.getInstance().getConfiguration();
//
//                        // init the push sdk
//                        if (config != null) {
//                            LogUtil.i(TAG, "initLibWithPush: get push addr " + config.getPushAddr());
//
//                            // 初始化push sdk
//                            EzvizPushSDK.setDeBug(true);
//                            EzvizPushSDK.initSDK(fContext, app_key, push_secret, config.getPushAddr());
//
//                            // start push service
//                            EZOpenSDK.getInstance().startPushService(fContext);
//                        }
//
//                    } catch (BaseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thr.start();

        } else if (Constants.NOTIFICATION_RECEIVED_ACTION.equals(action)) {
//            NotifierUtils.showNotification(context, intent);
        }
    }
}
