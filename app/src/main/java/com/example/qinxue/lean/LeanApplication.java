package com.example.qinxue.lean;


import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by qinxue on 2017/8/30.
 */

public class LeanApplication extends MultiDexApplication {
    private static final String TAG = "LeanApplication";

    public static final String APP_KEY = "sfci50a7s4q5i";
//    public static final String APP_KEY = "x18ywvqfxn3nc";

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this, APP_KEY);
//        RongIM.getInstance().registerConversationTemplate(new MyConversationProvider());

        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                Log.i(TAG, "onReceived()");
                Log.i(TAG, "onReceived() message.getContent().getClass().getSimpleName() =" + message.getContent().getClass().getSimpleName());
                //刷新缓存
//                RongIM.getInstance().refreshUserInfoCache(getUserInfo(message.getUId()));

                //刷新讨论组缓存
//                RongIM.getInstance().refreshDiscussionCache();
//                刷新群组
//                RongIM.getInstance().refreshGroupInfoCache();
//                刷新群组成员
//                     RongIM.getInstance().refreshGroupUserInfoCache();
                return false;
            }
        });
    }

}
