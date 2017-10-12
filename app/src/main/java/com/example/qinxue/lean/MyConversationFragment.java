package com.example.qinxue.lean;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.adapter.MessageListAdapter;

/**
 * Created by qinxue on 2017/9/27.
 */

public class MyConversationFragment extends ConversationFragment {
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = super.onCreateView(inflater, container, savedInstanceState);
//        listView = (ListView) v.findViewById(R.id.rc_list);
//        TextView headView = new TextView(getContext());
//        headView.setText("head View");
//        headView.setTextColor(Color.BLACK);
//        headView.setTextSize(100);
//        listView.addHeaderView(headView);
        return v;

    }

    @Override
    public MessageListAdapter onResolveAdapter(Context context) {
        return new MyMessageListAdapter(context);
    }

}
