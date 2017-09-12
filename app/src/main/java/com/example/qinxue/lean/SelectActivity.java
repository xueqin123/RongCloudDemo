package com.example.qinxue.lean;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by qinxue on 2017/9/8.
 */

public class SelectActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "SelectActivity";
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity_layout);
        mListView = (ListView) findViewById(R.id.listview);
        Intent intent = getIntent();
        ArrayList<String> groupMembers = intent.getStringArrayListExtra("group_members");
        String action = intent.getAction();
        Log.i(TAG, "action = " + action);
        if(action!=null){
            switch (action){
                case "action_add":
                    mListView.setAdapter(new ContactsAdapter(this, getFriends(getRest(groupMembers)), ContactsAdapter.SELECT));
                    break;
                case "action_remove":
                    mListView.setAdapter(new ContactsAdapter(this, getFriends(groupMembers), ContactsAdapter.SELECT));
                    break;
                case "action_create":
                    mListView.setAdapter(new ContactsAdapter(this, ContactsAdapter.SELECT));
                    break;
                default:
                    mListView.setAdapter(new ContactsAdapter(this, ContactsAdapter.SELECT));
                    break;
            }
        }
        mListView.setOnItemClickListener(this);
    }

    ArrayList<Friend> getFriends(ArrayList<String> ids) {
        ArrayList<Friend> list = new ArrayList<>();
        for (String userId : ids) {
            for (Friend friend : MainActivity.userIdList) {
                if (userId.equals(friend.getUserId())) {
                    list.add(friend);
                }
            }
        }
        return list;
    }

    private ArrayList<String> getRest(ArrayList list) {
        ArrayList<String> temp = new ArrayList<>();
        for (Friend friend : MainActivity.userIdList) {
            temp.add(friend.getUserId());
        }
        temp.removeAll(list);
        return temp;

    }

    @Override
    void onBackClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    void onOKClick(View view) {
        Intent intent = new Intent();
        ArrayList<String> list = new ArrayList<>();
        for (Friend friend : MainActivity.userIdList) {
            if (friend.isCheck()) {
                list.add(friend.getUserId());
                friend.setCheck(false);
            }
        }
        intent.putStringArrayListExtra("selected_list", list);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick() position = " + position);
        ContactsAdapter adapter = (ContactsAdapter) mListView.getAdapter();
        boolean ischeck = adapter.getItem(position).isCheck();
        adapter.getItem(position).setCheck(!ischeck);
        getmTitle().setText(String.format("选中 (%d)", adapter.getcheckedCount()));
        adapter.notifyDataSetChanged();
    }
}
