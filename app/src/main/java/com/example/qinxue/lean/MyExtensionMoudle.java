package com.example.qinxue.lean;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by qinxue on 2017/10/13.
 */

public class MyExtensionMoudle extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {

        List<IPluginModule> list = super.getPluginModules(conversationType);
        IPluginModule temp = null;
        for (IPluginModule module : list) {
            if (module instanceof FilePlugin) {
                temp = module;
                break;
            }
        }
        list.remove(temp);
        return list;
    }
}
