package com.example.qinxue.lean;

import android.content.Context;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.adapter.MessageListAdapter;

/**
 * Created by qinxue on 2017/10/12.
 */

public class MyMessageListAdapter extends MessageListAdapter {
    public MyMessageListAdapter(Context context) {
        super(context);
    }

    @Override
    public void add(UIMessage uiMessage) {


        super.add(uiMessage);

    }
}
