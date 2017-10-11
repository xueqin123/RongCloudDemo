package com.example.qinxue.lean;


import android.support.multidex.MultiDexApplication;

import io.rong.imkit.RongIM;

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
        RongIM.registerMessageType(CustomMessage.class);

    }
}
