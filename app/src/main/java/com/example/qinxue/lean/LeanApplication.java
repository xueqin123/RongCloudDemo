package com.example.qinxue.lean;


import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by qinxue on 2017/8/30.
 */

public class LeanApplication extends Application {
    private static final String TAG = "LeanApplication";

    public static final String APP_KEY = "sfci50a7s4q5i";
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this, APP_KEY);
        RongIM.getInstance().registerConversationTemplate(new MyConversationProvider());
    }
}
