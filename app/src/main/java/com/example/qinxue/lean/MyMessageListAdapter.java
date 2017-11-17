package com.example.qinxue.lean;

import android.content.Context;
import android.speech.tts.Voice;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by qinxue on 2017/10/12.
 */

public class MyMessageListAdapter extends MessageListAdapter {
    public MyMessageListAdapter(Context context) {
        super(context);
    }

    @Override
    public void add(UIMessage uiMessage) {
//        MessageContent messageContent = uiMessage.getContent();
//        if (!(messageContent instanceof VoiceMessage)) {
            super.add(uiMessage);
//        }
    }
}
