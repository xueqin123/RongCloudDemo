package com.example.qinxue.lean;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by qinxue on 2017/9/20.
 */

public class DemoReceiver extends PushMessageReceiver {
    private static final String TAG = "DemoReceiver";


    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        Log.i(TAG, "onNotificationMessageArrived()");
//        Log.i(TAG, "onNotificationMessageArrived() getPushData = " + pushNotificationMessage.getPushData());
//        Log.i(TAG, "onNotificationMessageArrived() getExtra  = " + pushNotificationMessage.getExtra());

        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        Log.i(TAG, "onNotificationMessageClicked()");
        return false;
    }
}
