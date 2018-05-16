package com.example.qinxue.lean;


import android.support.multidex.MultiDexApplication;
import android.util.Log;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

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
        RongIM.getInstance().registerConversationTemplate(new MyConversationProvider());
        RongIM.registerMessageType(CustomMessage.class);
        RongIM.registerMessageType(VideoMessage.class);
        RongIM.registerMessageTemplate(new CustomMessageItemProvider());
//        RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
//        removeFilePlugin();
    }

    private void removeFilePlugin() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        Log.i(TAG, "moduleList.size() =  " + moduleList);
        if (moduleList != null) {
            IExtensionModule module = null;
            for (IExtensionModule extensionModule : moduleList) {
                Log.i(TAG, "extensionModule.getClass().getSimpleName() = " + extensionModule.getClass().getSimpleName());
                if (extensionModule instanceof DefaultExtensionModule) {
                    module = extensionModule;
                    break;
                }
            }
            RongExtensionManager.getInstance().unregisterExtensionModule(module);//注销之前的
            RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionMoudle());//注册新的
            List<IExtensionModule> moduleList2 = RongExtensionManager.getInstance().getExtensionModules();
            Log.i(TAG, "moduleList.size() = " + moduleList2);
        }
    }
}
