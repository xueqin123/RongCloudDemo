package com.example.qinxue.lean;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.fragment.IHistoryDataResultCallback;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by qinxue on 2017/10/9.
 */

public class MyConversationListFragment extends ConversationListFragment {
    private static final String TAG = "MyFragment";
    private ConversationListAdapter mAdapter;

    @Override
    public void getConversationList(Conversation.ConversationType[] conversationTypes, final IHistoryDataResultCallback<List<Conversation>> callback) {
        super.getConversationList(conversationTypes, callback);
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                Log.i(TAG, "onSuccess()");
                if (callback != null) {
                    if (conversations == null) {
                        conversations = new ArrayList<>();
                    }
                    Conversation conversation = Conversation.obtain(Conversation.ConversationType.PRIVATE, "11111111", "测试");
                    conversation.setNotificationStatus(Conversation.ConversationNotificationStatus.DO_NOT_DISTURB);
                    conversation.setTop(true);
                    conversation.setSentTime(1);
                    conversations.add(conversation);
                    callback.onResult(conversations);
                    mAdapter.getCount();
                    for (int i = 0; i < mAdapter.getCount(); i++) {
                        UIConversation conversation1 = mAdapter.getItem(i);
                        Log.i(TAG, "i = " + i + "conversation1.setTop() = " + conversation1.isTop() + " onversation1.getUIConversationTime() =  " + conversation1.getUIConversationTime());
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                callback.onError();
            }
        }, conversationTypes);
    }

    @Override
    public ConversationListAdapter onResolveAdapter(Context context) {
        mAdapter = super.onResolveAdapter(context);
        return mAdapter;
    }


    //
//    onUIConversationCreated
}
