package com.example.qinxue.lean;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.message.RecallNotificationMessage;

/**
 * Created by qinxue on 2017/11/17.
 */
@ProviderTag(messageContent = CustomMessage.class)
public class CustomMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomMessage> {
    private static final String TAG = "MessageItemProvider";

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_message, null);
        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.text);
        view.setTag(holder);
        return view;
    }


    @Override
    public void bindView(View view, int i, CustomMessage customMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String content = customMessage.getString();
        Log.i(TAG, "content = " + content);
        holder.message.setText(content);
    }

    @Override
    public Spannable getContentSummary(CustomMessage customMessage) {
        return new SpannableString("这是一条自定义消息CustomizeMessage");
    }

    @Override
    public void onItemClick(View view, int i, CustomMessage customMessage, UIMessage uiMessage) {
        Log.i(TAG, "onItemClick()");
    }

    @Override
    public void onItemLongClick(View view, int position, CustomMessage content, UIMessage message) {
        super.onItemLongClick(view, position, content, message);
//        RongIMClient.getInstance().recallMessage(message.getMessage(), "pushContent", new RongIMClient.ResultCallback<RecallNotificationMessage>() {
//            @Override
//            public void onSuccess(RecallNotificationMessage recallNotificationMessage) {
//                Log.i(TAG, "onSuccess()");
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.i(TAG, "onError() errorCode = " + errorCode);
//            }
//        });

    }

    class ViewHolder {
        TextView message;
    }

}
