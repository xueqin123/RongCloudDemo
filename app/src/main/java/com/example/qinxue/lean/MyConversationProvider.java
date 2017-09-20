package com.example.qinxue.lean;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * Created by qinxue on 2017/9/20.
 */
@ConversationProviderTag(conversationType = "private", portraitPosition = 1)
public class MyConversationProvider implements IContainerItemProvider.ConversationProvider<UIConversation> {

    protected class ViewHolder {
        public TextView time;
    }

    @Override
    public String getTitle(String s) {
        return null;
    }

    @Override
    public Uri getPortraitUri(String s) {
        return null;
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {

        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.conversation_list_item, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.time = (TextView) view.findViewById(R.id.custom);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, int i, UIConversation uiConversation) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.time.setText(String.valueOf(uiConversation.getUIConversationTime()));
    }
}
