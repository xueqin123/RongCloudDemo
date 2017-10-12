package com.example.qinxue.lean;

import android.content.Context;
import android.view.View;

import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

/**
 * Created by qinxue on 2017/10/12.
 */

public class MyConversationListAdapter extends ConversationListAdapter {
    public MyConversationListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
        if (data.getConversationType().equals(Conversation.ConversationType.DISCUSSION)) {
            data.setUnreadType(UIConversation.UnreadRemindType.REMIND_WITH_COUNTING);
        }

        super.bindView(v, position, data);
    }
}
